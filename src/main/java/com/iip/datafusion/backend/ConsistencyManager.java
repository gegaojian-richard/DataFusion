package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.ConsistencyJobExecutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsistencyManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static ConsistencyManager singleInstance = new ConsistencyManager();

    private ConsistencyJobExecutor[] consistencyJobExecutors = new ConsistencyJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private ConsistencyManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<ConsistencyJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setConsistencyChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            consistencyJobExecutors[i] = new ConsistencyJobExecutor(token, managedQueues[i]);
        }
    }

    public static ConsistencyManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            consistencyJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            consistencyJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(ConsistencyJob consistencyJob){
        try {
            ChannelManager.getInstance().getConsistencyChannel().put(consistencyJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
