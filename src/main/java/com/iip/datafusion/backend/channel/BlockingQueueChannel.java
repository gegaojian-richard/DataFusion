package com.iip.datafusion.backend.channel;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueChannel<J> implements Channel<J>{
    private final BlockingQueue<J> queue;

    public BlockingQueueChannel(BlockingQueue<J> queue){
        this.queue = queue;
    }

    @Override
    public J take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void put(J job) throws InterruptedException {
        queue.put(job);
    }
}
