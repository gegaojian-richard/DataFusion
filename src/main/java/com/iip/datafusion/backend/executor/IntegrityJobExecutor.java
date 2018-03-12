package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import com.iip.datafusion.redis.model.RedisTransform;
import com.iip.datafusion.redis.model.RedisHelper;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;

import net.sf.json.JSONObject;

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

        try{
            doJob(integrityJob);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(IntegrityJob job) throws Exception {
        // todo: 实现完整性检查工作

        //System.out.println("finished hahah");
        //job.setResult(new Result(0,"wawawawawa",null));

        DataSourceRouterManager.setCurrentDataSourceKey(job.getDataSourceId());

        try{
            if(job.getJobType().equals("query")) {
                SqlRowSet resRowset = jdbcTemplate.queryForRowSet(job.getSqlList().get(0));
                String json = job.rowSetToJson(resRowset);
                resRowset = jdbcTemplate.queryForRowSet(job.getSqlList().get(0));

                job.setResult(new Result(1,null,json));

                //rowsetToRedis(resRowset,job.getJobId());
                //RedisTransform.rowsetToRedis(resRowset,job.getJobId(),redisTemplate);
                //JobRegistry.getInstance().update(job.getJobId(),1);
            }else if(job.getJobType().equals("execute")){
                //todo: 更新任务
            }

        }catch (Exception e){
                e.printStackTrace();
                job.setResult(new Result(0,"出现内部错误",null));
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
                //System.out.println(columnName+" "+value+"\n");
            }
            lists.add(jsonObj.toString());
        }
        //System.out.println(lists);
        String id = jobId;
        //System.out.println(id);
        redisTemplate.opsForList().leftPushAll(id, lists);
        //System.out.println(redisTemplate.opsForList().range(id,0,2));

        return true;
    }








}
