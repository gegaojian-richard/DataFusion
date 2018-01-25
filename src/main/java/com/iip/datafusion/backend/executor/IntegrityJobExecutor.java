package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.integrity.IntegrityDao;
import com.iip.datafusion.dgs.integrity.IntegrityService;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.concurrent.BlockingQueue;


public class IntegrityJobExecutor extends AbstractTerminatableThread implements JobExecutor<IntegrityJob> {
    private final BlockingQueue<IntegrityJob> workQueue;


    private final JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate();

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
                String json = job.rowSetToJson(jdbcTemplate.queryForRowSet(job.getSqlList().get(0)));
                job.setResult(new Result(1,null,json));
            }else if(job.getJobType().equals("execute")){
                //todo: 更新任务
            }

        }catch (Exception e){
                e.printStackTrace();
                job.setResult(new Result(0,"出现内部错误",null));
        }


    }
}
