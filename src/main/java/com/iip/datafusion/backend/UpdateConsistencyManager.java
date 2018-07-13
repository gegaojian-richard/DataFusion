package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.UpdateConsistencyJobExecutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.UpdateConsistencyJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UpdateConsistencyManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static UpdateConsistencyManager singleInstance = new UpdateConsistencyManager();

    private UpdateConsistencyJobExecutor[] updateConsistencyJobExecutors = new UpdateConsistencyJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private UpdateConsistencyManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<UpdateConsistencyJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setUpdateConsistencyChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            updateConsistencyJobExecutors[i] = new UpdateConsistencyJobExecutor(token, managedQueues[i]);
        }
    }

    public static UpdateConsistencyManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            updateConsistencyJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            updateConsistencyJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(UpdateConsistencyJob UpdateConsistencyJob){
        try {
            ChannelManager.getInstance().getUpdateConsistencyChannel().put(UpdateConsistencyJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}