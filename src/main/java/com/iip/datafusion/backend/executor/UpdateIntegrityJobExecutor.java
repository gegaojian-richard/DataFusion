package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.dgs.model.UpdateIntegrityJob;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * @author zengc
 * @date 2018/6/26 16:15
 */
public class UpdateIntegrityJobExecutor extends AbstractTerminatableThread implements JobExecutor<UpdateIntegrityJob> {
    private final BlockingQueue<UpdateIntegrityJob> workQueue;

    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();


    public UpdateIntegrityJobExecutor(TerminationToken token, BlockingQueue<UpdateIntegrityJob> workQueue) {
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        UpdateIntegrityJob integrityJob = ChannelManager.getInstance().getUpdateIntegrityChannel().take(workQueue);
        JobRegistry.getInstance().update(integrityJob, JobStatusType.EXECUTING);

        try {
            doJob(integrityJob);
            JobRegistry.getInstance().update(integrityJob, JobStatusType.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            JobRegistry.getInstance().update(integrityJob, JobStatusType.ERROR);
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(UpdateIntegrityJob job) throws Exception {
        // todo: 实现完整性检查工作

        System.out.println("UpdateIntegrityJob  here");
        DataSourceRouterManager.setCurrentDataSourceKey(job.getDataSourceId());
        SqlRowSet resRowset = jdbcTemplate.queryForRowSet("select * from "+job.getTableName()+" limit 0");
        SqlRowSetMetaData sqlRsmd = resRowset.getMetaData();
        HashMap<String,String> nameType = new HashMap<>();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){
            nameType.put(sqlRsmd.getColumnName(i),sqlRsmd.getColumnTypeName(i));
        }
        if(job.getType() == 0){
            List<Map<String,String>> valueMaps = job.getMapEntries();
            if (valueMaps == null || valueMaps.size() <= 0) {
                throw new Exception("传入记录不能为空或不完整");
                //return null;
            }

            ArrayList<String> columnList = new ArrayList<>();
            String columnClause = "(";
            String totalValueClause = "";
            ArrayList<String> sqlList = new ArrayList<>();
            for(int i = 0;i<valueMaps.size();i++) {
                Map<String,String> valueMap = valueMaps.get(i);

                String valueClause = "(";
                int index = -1;
                for (String key : valueMap.keySet()) {
                    index ++;
                    if (key != null && key.trim() !="") {
                        if(i == 0 ) {
                            columnList.add(key);
                            columnClause += key + ",";
                        }
                        else if(columnList.size()<=index || !columnList.get(index).equals(key)){
                            throw new Exception("传入记录属性顺序不一致");
                        }
                        String value = job.sqlChangeType(valueMap.get(key),nameType.get(key));
                        valueClause += value + ",";
                    } else{
                        throw new Exception("属性不能为空");
                    }
                }
                if(i == 0 ) {
                    columnClause = columnClause.substring(0, columnClause.length() - 1);
                    columnClause += ") ";
                }
                valueClause = valueClause.substring(0, valueClause.length() - 1);
                valueClause += ")";

                totalValueClause += valueClause+",";
            }
            totalValueClause = totalValueClause.substring(0,totalValueClause.length()-1)+";";
            String total = String.format("REPLACE INTO %s  %s VALUES %s",job.getTableName(),columnClause,totalValueClause);
            jdbcTemplate.execute(total);

        }else if(job.getType() == 1){
            Map<String,String> unifyMap = job.getUnifyMap();
            List<String> sqlList = new ArrayList<>();
            for(String key:unifyMap.keySet()){
                key = key.trim();
                String setClause  = "";
                if (key != null && key.trim() !="" && nameType.containsKey(key.trim())) {
                    setClause = job.sqlChangeType(unifyMap.get(key),nameType.get(key));
                    setClause = " "+ key + "="+setClause;
                }else {
                    throw new Exception("属性不能为空或不存在");
                }
                String whereClause = String.format(" WHERE ISNULL(%s) or %s=\"\" ", key,key);
                String clause = "UPDATE "+job.getTableName()+" SET "+setClause +whereClause;
                sqlList.add(clause);
            }


            for (String sql:sqlList)
                jdbcTemplate.execute(sql);

            sqlList.clear();

            for(Map<String,String> maps:job.getMapEntries()){

                for(String key:maps.keySet()){
                    String columnName = key.trim().split(",")[0];
                    String primaryKey = key.trim().split(",")[1];
                    String querySql = "SELECT "+key.trim()+" FROM "+job.getTableName()+String.format(" WHERE ISNULL(%s) or %s=\"\" ", columnName,columnName);
                    //System.out.println(querySql);
                    SqlRowSet sqlRowSet1 = jdbcTemplate.queryForRowSet(querySql);
                    String[] values = maps.get(key).split(",");
                    DataSourceRouterManager.setCurrentDataSourceKey(values[0]);
                    String querySql2 = "SELECT "+values[2]+","+values[3] +" FROM "+values[1];
                    //System.out.println(querySql2);
                    SqlRowSet sqlRowSet2 = jdbcTemplate.queryForRowSet(querySql2);
                    DataSourceRouterManager.setCurrentDataSourceKey(job.getDataSourceId());
                    sqlRsmd = sqlRowSet1.getMetaData();
                    nameType = new HashMap<>();
                    for(int i=1;i<=sqlRsmd.getColumnCount();i++){
                        nameType.put(sqlRsmd.getColumnName(i),sqlRsmd.getColumnTypeName(i));
                    }

                    while (sqlRowSet1.next()) {
                        String column = sqlRowSet1.getString(1);
                        String key1 = sqlRowSet1.getString(2);
                        while (sqlRowSet2.next()) {
                            String reference = sqlRowSet2.getString(1);
                            String key2 = sqlRowSet2.getString(2);
                            if(key1==null || key2==null)
                                continue;

                            if(key2.equals(key1)){
                                String sql = "UPDATE "+job.getTableName()+" SET "+columnName+"="+job.sqlChangeType(reference,nameType.get(columnName))+String.format(" WHERE ( ISNULL(%s) or %s=\"\" ) and %s=%s", columnName,columnName,primaryKey,job.sqlChangeType(key1,primaryKey));
                                //System.out.println(sql);
                                sqlList.add(sql);
                                break;
                            }
                            if (sqlRowSet2.isLast()) {
                                sqlRowSet2.beforeFirst();
                                break;
                            }
                        }
                    }



                }
            }
            for (String sql:sqlList)
                jdbcTemplate.execute(sql);

        }

    }

}