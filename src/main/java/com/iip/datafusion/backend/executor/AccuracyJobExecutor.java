package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.job.accuracy.AccuracyJob;

import java.util.concurrent.BlockingQueue;

public class AccuracyJobExecutor extends AbstractTerminatableThread implements JobExecutor<AccuracyJob> {
    private final BlockingQueue<AccuracyJob> workQueue;

    public AccuracyJobExecutor(TerminationToken token, BlockingQueue<AccuracyJob> workQueue){
        super(token);
        this.workQueue = workQueue;
    }

    @Override
    protected void doRun() throws Exception {
        AccuracyJob accuracyJob = ChannelManager.getInstance().getAccuracyChannel().take(workQueue);

        try{
            doJob(accuracyJob);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            terminationToken.reservations.decrementAndGet();
        }
    }

    @Override
    public void doJob(AccuracyJob job) throws Exception {
        // todo: 实现准确性检查工作
    }
}
