package com.iip.datafusion.backend;

import com.iip.datafusion.backend.channel.ChannelManager;
import com.iip.datafusion.backend.channel.WorkStealingChannel;
import com.iip.datafusion.backend.common.TerminationToken;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.executor.IntegrityJobExecutor;
import com.iip.datafusion.backend.executor.TestJobExecutor;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.test.TestJob;
import org.apache.catalina.Manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午2:36
 */
public class TestManager{
    private final TerminationToken token= new TerminationToken();

    // 关闭标识
    private volatile boolean shutdownRequested = false;

    private final static TestManager singleInstance = new TestManager();

    private TestJobExecutor[] testJobExecutors = new TestJobExecutor[Capabilities.JOBEXECUTOR_COUNT];

    private TestManager(){
        @SuppressWarnings("unchecked")
        BlockingQueue<TestJob>[] managedQueues = new LinkedBlockingQueue[Capabilities.JOBEXECUTOR_COUNT];

        ChannelManager.getInstance().setTestChannel(new WorkStealingChannel<>(managedQueues));

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            managedQueues[i] = new LinkedBlockingQueue<>();
            testJobExecutors[i] = new TestJobExecutor(token, managedQueues[i]);
        }
    }

    public static TestManager getInstance(){
        return singleInstance;
    }

    public void init(){
        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            testJobExecutors[i].start();
        }
    }

    public synchronized void shutdown(){
        if (shutdownRequested) {
            throw new IllegalStateException("shutdown already requested!");
        }

        for (int i = 0; i < Capabilities.JOBEXECUTOR_COUNT; ++i){
            testJobExecutors[i].terminate();
        }

        shutdownRequested = true;
    }

    public void commitJob(TestJob testJob){
        try {
            ChannelManager.getInstance().getTestChannel().put(testJob);
            token.reservations.incrementAndGet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
