package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.JoinJobExecutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.join.JoinJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 数据整合后台功能入口
 * 单例(饿汉)
 * Created by GeGaojian on 2018/01/19.
 */
public class JoinManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static JoinManager singleInstance = new JoinManager();

    private JoinJobExecutor[] joinJobExecutors = new JoinJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private JoinManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<JoinJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setJoinChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            joinJobExecutors[i] = new JoinJobExecutor(token, managedQueues[i]);
        }
    }

    public static JoinManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            joinJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            joinJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(JoinJob joinJob){
        try {
            ChannelManager.getInstance().getJoinChannel().put(joinJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
