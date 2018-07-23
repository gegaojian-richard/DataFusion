package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.Doc2VecJobExecutor;
import com.iip.datafusion.backend.job.algorithm.Doc2VecJob;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * @Author YLX
 * @Data 2018/2/1
 */
public class Doc2VecManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static Doc2VecManager singleInstance = new Doc2VecManager();

    private Doc2VecJobExecutor[] doc2vecJobExecutors = new Doc2VecJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private Doc2VecManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<Doc2VecJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setDoc2VecChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            doc2vecJobExecutors[i] = new Doc2VecJobExecutor(token, managedQueues[i]);
        }
    }

    public static Doc2VecManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            doc2vecJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            doc2vecJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(Doc2VecJob doc2vecJob){
        try {
            ChannelManager.getInstance().getDoc2VecChannel().put(doc2vecJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
