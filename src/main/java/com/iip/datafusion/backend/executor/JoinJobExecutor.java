package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.join.JoinJob;
import com.iip.datafusion.backend.job.join.SQLTask;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * 数据整合工作执行器（消费者）
 * @Author Ge GaoJian
 */
public class JoinJobExecutor extends AbstractTerminatableThread implements JobExecutor<JoinJob>{
    private final BlockingQueue<JoinJob> workQueue;

    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();

    public JoinJobExecutor(TerminationToken token, BlockingQueue<JoinJob> workQueue){
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        JoinJob joinJob = ChannelManager.getInstance().getJoinChannel().take(workQueue);
        JobRegistry.getInstance().update(joinJob, JobStatusType.EXECUTING);

        try{
            doJob(joinJob);
            JobRegistry.getInstance().update(joinJob, JobStatusType.SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            JobRegistry.getInstance().update(joinJob, JobStatusType.ERROR);
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(JoinJob job) throws Exception {
        // todo: 实现整合工作

        // 1. 获取sqltasks
        List<SQLTask> sqlTasks = job.getSQLTasks();
        // jdbcTemplate.query
        DataSourceRouterManager.setCurrentDataSourceKey(sqlTasks.get(0).getDatasourceID());
        List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sqlTasks.get(0).getSql());

        for (Map<String, Object> instance : resultSet
             ) {
            for (int i = 1; i < sqlTasks.size(); i++) {
                Map<String, Object> tempResult;
                DataSourceRouterManager.setCurrentDataSourceKey(sqlTasks.get(i).getDatasourceID());
                // todo:int转string有问题
                String value;
                if (i == 1){
                    value = String.valueOf(instance.get(sqlTasks.get(i).getWhereFieldName()));
                }else{
                    if(instance.get(sqlTasks.get(i).getParentJoinUnit()+":"+sqlTasks.get(i).getWhereFieldName()) == null){
                        value = String.valueOf(instance.get(sqlTasks.get(i).getWhereFieldName()));
                    }else
                        value = String.valueOf(instance.get(sqlTasks.get(i).getParentJoinUnit()+":"+sqlTasks.get(i).getWhereFieldName()));
                }
                try { // 有左值 无右值
                    tempResult = jdbcTemplate.queryForMap(sqlTasks.get(i).getSql(), value);
                    for (String key : tempResult.keySet()
                            ) {
                        // todo:将key加上joinunit标识
                        instance.put(sqlTasks.get(i).getCurrentJoinUnit() + ":" + key, tempResult.get(key));
                    }
                }catch (EmptyResultDataAccessException e){
                     for(String key : sqlTasks.get(i).getSelectedFields()){
                         instance.put(sqlTasks.get(i).getCurrentJoinUnit() + ":" + key, "");
                     }
                }
            }
        }

        // insert
        List<Object[]> joinedResult = new ArrayList<Object[]>();
        Set<String> sourceFields = job.getS2tMap().keySet();
        DataSourceRouterManager.setCurrentDataSourceKey(job.getTargetDatasourceID());
        jdbcTemplate.batchUpdate(job.getInsertSQL(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                int j = 1;
                for (String key : sourceFields
                     ) {
                    String value;
                    if (resultSet.get(i).get(key)!=null)
                        value = String.valueOf(resultSet.get(i).get(key));
                    else {
                        value = String.valueOf(resultSet.get(i).get(key.split(":")[2]));
                    }
                    preparedStatement.setString(j,value);
                    j++;
                }
            }

            @Override
            public int getBatchSize() {
                return resultSet.size();
            }
        });
    }
}
