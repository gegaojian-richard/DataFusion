package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.consistency.UpdateConsistencyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.data.redis.core.RedisTemplate;
import com.iip.datafusion.redis.model.RedisTransform;
import com.iip.datafusion.redis.model.RedisHelper;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class UpdateConsistencyJobExecutor extends AbstractTerminatableThread implements JobExecutor<UpdateConsistencyJob> {
    @Autowired
    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();
    private final BlockingQueue<UpdateConsistencyJob> workQueue;

    private RedisTemplate<String, String> redisTemplate = RedisHelper.getRedisTemplate();


    public UpdateConsistencyJobExecutor(TerminationToken token, BlockingQueue<UpdateConsistencyJob> workQueue){
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        UpdateConsistencyJob updateconsistencyJob = ChannelManager.getInstance().getUpdateConsistencyChannel().take(workQueue);
        JobRegistry.getInstance().update(updateconsistencyJob, JobStatusType.EXECUTING);
        System.out.println("1111");
        try{
            doJob(updateconsistencyJob);
            JobRegistry.getInstance().update(updateconsistencyJob, JobStatusType.SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            JobRegistry.getInstance().update(updateconsistencyJob, JobStatusType.ERROR);
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(UpdateConsistencyJob job) throws Exception {
        System.out.println("heere");
        DataSourceRouterManager.setCurrentDataSourceKey(job.getmainDataSourceID());
        System.out.println(job.getSqlList().size());
        int i=0;
        try{
            while(job.getSqlList().size()>0) {
                if(job.getInnerJobType().equals("update")) {
                    String sql_oringinal=job.getSqlList().get(i++);
                    System.out.println(sql_oringinal);
                    String[] temp = sql_oringinal.split(",");
                    String updatetype = temp[0];
                    String sql = temp[1];
                    if(updatetype.equals("right"))
                    {DataSourceRouterManager.setCurrentDataSourceKey(job.getfollowDataSourceID());}
                    if(updatetype.equals("left"))
                    {DataSourceRouterManager.setCurrentDataSourceKey(job.getmainDataSourceID());}
                    jdbcTemplate.execute(sql);
                }
                if(i>=job.getSqlList().size()) break;
            }
        }catch (Exception e){
            e.printStackTrace();
//            return new Result(0,"出现内部错误",null);
        }
//        return new Result();
    }
}
