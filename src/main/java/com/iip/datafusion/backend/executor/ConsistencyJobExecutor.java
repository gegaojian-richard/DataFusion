package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.concurrent.BlockingQueue;

public class ConsistencyJobExecutor extends AbstractTerminatableThread implements JobExecutor<ConsistencyJob> {
    @Autowired
    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();
    private final BlockingQueue<ConsistencyJob> workQueue;

    public ConsistencyJobExecutor(TerminationToken token, BlockingQueue<ConsistencyJob> workQueue){
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        ConsistencyJob consistencyJob = ChannelManager.getInstance().getConsistencyChannel().take(workQueue);

        try{
            doJob(consistencyJob);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(ConsistencyJob job) throws Exception {
        System.out.println("heere");
        DataSourceRouterManager.setCurrentDataSourceKey(job.getmainDataSourceID());
        System.out.println(job.getSqlList().size());
        try{
            if(job.getSqlList().size()>0) {
                if(job.getJobType().equals("Consistency")) {

                    SqlRowSet sqlRowSet1 = jdbcTemplate.queryForRowSet(job.getSqlList().get(0));
                    SqlRowSet sqlRowSet2 = jdbcTemplate.queryForRowSet(job.getSqlList().get(1));
                    System.out.println(job.getSqlList().get(0));
                    System.out.println(job.getSqlList().get(1));
                    JSONArray json = new JSONArray();
//                    while (sqlRowSet2.next()) {
//                        System.out.println(sqlRowSet2.isLast());
//                        System.out.println(sqlRowSet2.getString(1));}
                    while (sqlRowSet1.next()) {
                        int count=0;
                        String key1=sqlRowSet1.getString(1);
                        String value1=sqlRowSet1.getString(2);
                        System.out.println("key1："+key1);
                        System.out.println("value1："+value1);
                        while (sqlRowSet2.next()) {
                            String key2=sqlRowSet2.getString(1);
                            System.out.println("key2："+key2);
                            String value2=sqlRowSet2.getString(2);
                            System.out.println("value2："+value2);
                            if(value1==null||value2==null) break;
                            if(key1.compareTo(key2)!=0) ;
                            if(key1.compareTo(key2)==0){
                                if(value1.compareTo(value2)!=0){
                                    JSONObject jo = new JSONObject();
                                    jo.put("dataSourceId1",job.getmainDataSourceID());
                                    jo.put("tableName1",job.getmainTableName());
                                    jo.put("primary_key1",job.getPrimary_key1());
                                    jo.put("key1",key1);
                                    jo.put("ColumnName1",job.getmainColumnName());
                                    jo.put("value1",value1);
                                    jo.put("dataSourceId2",job.getfollowDataSourceID());
                                    jo.put("tableName2",job.getfollowTableName());
                                    jo.put("primary_key2",job.getPrimary_key2());
                                    jo.put("key2",key2);
                                    jo.put("ColumnName2",job.getfollowColumnName());
                                    jo.put("value2",value2);
                                    json.add(jo);
                                }
                            }
                            if (sqlRowSet2.isLast()) {
                                sqlRowSet2.beforeFirst();
                                break;}
                        }
                    }
                    Result result = new Result(0,"数据不一致",json.toString());
                    System.out.println("aaaa");
                    System.out.println(result);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
//            return new Result(0,"出现内部错误",null);
        }
//        return new Result();
    }
}
