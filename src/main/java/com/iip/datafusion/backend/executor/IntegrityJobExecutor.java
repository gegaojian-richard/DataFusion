package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.common.AbstractTerminatableThread;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import java.util.concurrent.BlockingQueue;

public class IntegrityJobExecutor extends AbstractTerminatableThread implements JobExecutor<IntegrityJob> {
    private final BlockingQueue<IntegrityJob> workQueue;

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
    }
}
