package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.jdbchelper.JDBCHelper;
import com.iip.datafusion.backend.job.join.JoinJob;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.concurrent.BlockingQueue;

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

        try{
            doJob(joinJob);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(JoinJob job) throws Exception {
        // todo: 实现整合工作



    }
}
