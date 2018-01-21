package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;

import java.util.concurrent.BlockingQueue;

public class ConsistencyJobExecutor extends AbstractTerminatableThread implements JobExecutor<ConsistencyJob> {
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
        // todo: 实现一致性检查工作
    }
}
