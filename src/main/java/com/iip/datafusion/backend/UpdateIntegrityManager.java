package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.IntegrityJobExecutor;
import com.iip.datafusion.backend.executor.UpdateIntegrityJobExecutor;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.UpdateIntegrityJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zengc
 * @date 2018/6/26 16:12
 */
public class UpdateIntegrityManager {

    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static UpdateIntegrityManager singleInstance = new UpdateIntegrityManager();

    private UpdateIntegrityJobExecutor[] updateIntegrityJobExecutors = new UpdateIntegrityJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private UpdateIntegrityManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<UpdateIntegrityJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setUpdateIntegrityChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            updateIntegrityJobExecutors[i] = new UpdateIntegrityJobExecutor(token, managedQueues[i]);
        }
    }

    public static UpdateIntegrityManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            updateIntegrityJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            updateIntegrityJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(UpdateIntegrityJob updateIntegrityJob){
        try {
            ChannelManager.getInstance().getUpdateIntegrityChannel().put(updateIntegrityJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
