package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import com.iip.datafusion.redis.model.RedisTransform;
import com.iip.datafusion.redis.model.RedisHelper;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;

import com.iip.datafusion.util.userutil.UserManager;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;


public class IntegrityJobExecutor extends AbstractTerminatableThread implements JobExecutor<IntegrityJob> {
    private final BlockingQueue<IntegrityJob> workQueue;

    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();

    private RedisTemplate<String, String> redisTemplate = RedisHelper.getRedisTemplate();



    public IntegrityJobExecutor(TerminationToken token, BlockingQueue<IntegrityJob> workQueue){
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        IntegrityJob integrityJob = ChannelManager.getInstance().getIntegrityChannel().take(workQueue);
        JobRegistry.getInstance().update(integrityJob, JobStatusType.EXECUTING);

        try{
            doJob(integrityJob);
            JobRegistry.getInstance().update(integrityJob, JobStatusType.SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            JobRegistry.getInstance().update(integrityJob, JobStatusType.ERROR);
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(IntegrityJob job) throws Exception {
        // todo: 实现完整性检查工作



        DataSourceRouterManager.setCurrentDataSourceKey(job.getDataSourceId());


        try{
            if(job.getInnerJobType().equals("query")) {
                SqlRowSet resRowset = jdbcTemplate.queryForRowSet(job.getSqlList().get(0));
                String json = job.rowSetToJson(resRowset);
                resRowset = jdbcTemplate.queryForRowSet(job.getSqlList().get(0));

                String key = job.getUserID() + "-" + job.getJobID();

                ArrayList<String> names = (ArrayList<String>)job.getColumnNames();
                StringBuilder columns = new StringBuilder("");
                if(names!=null && names.size()>0){
                    for (String s:names)
                        columns.append(",").append(s);
                }

                RedisTransform.rowsetToRedis(resRowset,key,redisTemplate);
//                redisTemplate.opsForList().rightPush(key,columns.toString().substring(1));
            }else if(job.getInnerJobType().equals("execute")){
                //todo: 更新任务
            }

        }catch (Exception e){
                e.printStackTrace();
        }


    }

    public boolean rowsetToRedis(SqlRowSet sqlRowSet,String jobId)throws Exception{
        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        ArrayList<String> trueColumnNames = new ArrayList<>();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){
            trueColumnNames.add(sqlRsmd.getColumnName(i));
        }

        ArrayList<String> lists = new ArrayList<>();

        // 遍历ResultSet中的每条数据
        while (sqlRowSet.next()) {
            JSONObject jsonObj = new JSONObject();

            // 遍历每一列
            for (int i = 0; i < trueColumnNames.size(); i++) {
                String columnName =trueColumnNames.get(i);
                String value = sqlRowSet.getString(columnName);
                if(value !=null)
                    jsonObj.put(columnName, value);
                else
                    jsonObj.put(columnName, "NULL");

            }
            lists.add(jsonObj.toString());
        }
        String id = jobId;
        redisTemplate.opsForList().rightPushAll(id, lists);

        return true;
    }








}
