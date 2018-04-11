package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.TFIDFJobExecutor;
import com.iip.datafusion.backend.executor.TextRankJobExcutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午9:41
 */
public class TFIDFManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static TFIDFManager singleInstance = new TFIDFManager();

    private TFIDFJobExecutor[] executors = new TFIDFJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private TFIDFManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<TFIDFJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setTfidfChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            executors[i] = new TFIDFJobExecutor(token, managedQueues[i]);
        }
    }

    public static TFIDFManager getInstance(){
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

    public void commitJob(TFIDFJob job){
        try {
//            System.out.println("TFIDFManager: " + job.getCorpusPath() + " " + job.getTopK());
            ChannelManager.getInstance().getTfidfChannel().put(job);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
