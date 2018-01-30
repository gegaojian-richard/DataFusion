package com.iip.datafusion.backend.channel;

import java.util.concurrent.BlockingQueue;

public interface WorkStealingEnabledChannel<J> extends Channel<J> {
    J take(BlockingQueue<J> preferredQueue) throws InterruptedException;
}
