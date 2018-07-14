package com.iip.datafusion.backend.channel;

import java.util.concurrent.BlockingQueue;

/**
 * 支持偷窃算法的管道
 * @param <J> "Job"
 * @Author Ge GaoJian
 */
public interface WorkStealingEnabledChannel<J> extends Channel<J> {
    J take(BlockingQueue<J> preferredQueue) throws InterruptedException;
}
