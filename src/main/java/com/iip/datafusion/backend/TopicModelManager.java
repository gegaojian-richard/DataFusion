package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.TopicModelExecutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.algorithm.TopicModelJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:52
 */
public class TopicModelManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static TopicModelManager singleInstance = new TopicModelManager();

    private TopicModelExecutor[] executors = new TopicModelExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private TopicModelManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<TopicModelJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setTopicModeChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            executors[i] = new TopicModelExecutor(token, managedQueues[i]);
        }
    }

    public static TopicModelManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            executors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            executors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(TopicModelJob job){
        try {
//            System.out.println("TopicManager: " + job.getCorpusPath() + " " + job.getTopicNum());
            ChannelManager.getInstance().getTopicModeChannel().put(job);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
