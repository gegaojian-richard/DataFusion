package com.iip.datafusion.backend.channel;

import java.util.concurrent.BlockingQueue;

public class WorkStealingChannel<J> implements WorkStealingEnabledChannel<J> {
    private final BlockingQueue<J>[] managedQueues;

    public WorkStealingChannel(BlockingQueue<J>[] managedQueues) {
        this.managedQueues = managedQueues;
    };
    @Override
    public J take(BlockingQueue<J> preferredQueue) throws InterruptedException {
        BlockingQueue<J> targetQueue = preferredQueue;
        J job = null;

        if(targetQueue != null){
            job = targetQueue.poll();
        }

        int queueIndex = -1;
        while (job == null){
            queueIndex = (queueIndex + 1) % managedQueues.length;
            targetQueue = managedQueues[queueIndex];
            job = targetQueue.poll();
            if (preferredQueue == targetQueue){
                break;
            }
        }

        if (job == null){
            job = preferredQueue.take();
        }
        return job;
    }

    @Override
    public J take() throws InterruptedException {
        return take(null);
    }

    @Override
    public void put(J job) throws InterruptedException {
        int targetIndex = (job.hashCode() % managedQueues.length);
        BlockingQueue<J> targetQueue = managedQueues[targetIndex];
        targetQueue.put(job);
    }
}
