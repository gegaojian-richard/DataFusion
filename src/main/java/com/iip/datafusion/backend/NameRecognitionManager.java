package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.NameRecognitionJobExcutor;
import com.iip.datafusion.backend.executor.TextRankJobExcutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
//import com.sun.codemodel.internal.JType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:27
 */
public class NameRecognitionManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static NameRecognitionManager singleInstance = new NameRecognitionManager();

    private NameRecognitionJobExcutor[] excutors = new NameRecognitionJobExcutor[Capabilities.JOBEXECUTOR_COUNT];

    private NameRecognitionManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<NameRecognitionJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setNameRecognitionChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            excutors[i] = new NameRecognitionJobExcutor(token, managedQueues[i]);
        }
    }

    public static NameRecognitionManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            excutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            excutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(NameRecognitionJob job){
        try {
//            System.out.println("NameRecognitionManager: " + job.getCorpusPath());
            ChannelManager.getInstance().getNameRecognitionChannel().put(job);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
