package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.ConsistencyJobExecutor;
import com.iip.datafusion.backend.executor.IntegrityJobExecutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IntegrityManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static IntegrityManager singleInstance = new IntegrityManager();

    private IntegrityJobExecutor[] integrityJobExecutors = new IntegrityJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private IntegrityManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<IntegrityJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setIntegrityChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            integrityJobExecutors[i] = new IntegrityJobExecutor(token, managedQueues[i]);
        }
    }

    public static IntegrityManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            integrityJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            integrityJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(IntegrityJob integrityJob){
        try {
            ChannelManager.getInstance().getIntegrityChannel().put(integrityJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
