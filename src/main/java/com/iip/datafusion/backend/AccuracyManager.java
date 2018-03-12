package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.AccuracyJobExecutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.accuracy.AccuracyJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AccuracyManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static AccuracyManager singleInstance = new AccuracyManager();

    private AccuracyJobExecutor[] accuracyJobExecutors = new AccuracyJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private AccuracyManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<AccuracyJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setAccuracyChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            accuracyJobExecutors[i] = new AccuracyJobExecutor(token, managedQueues[i]);
        }
    }

    public static AccuracyManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            accuracyJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            accuracyJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(AccuracyJob accuracyJob){
        try {
            ChannelManager.getInstance().getAccuracyChannel().put(accuracyJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
