package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.TestJobExecutor;
import com.iip.datafusion.backend.executor.TextRankJobExcutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午3:20
 */
public class TextRankManager {
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static TextRankManager singleInstance = new TextRankManager();

    private TextRankJobExcutor[] textRankJobExecutors = new TextRankJobExcutor[Capabilities.JOBEXECUTOR_COUNT];

    private TextRankManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<TextRankJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setTextRankChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            textRankJobExecutors[i] = new TextRankJobExcutor(token, managedQueues[i]);
        }
    }

    public static TextRankManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            textRankJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            textRankJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(TextRankJob textRankJob){
        try {
//            System.out.println("TextRankManager: " + textRankJob.getCorpusPath() + " " + textRankJob.getTopK());
            ChannelManager.getInstance().getTextRankChannel().put(textRankJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
