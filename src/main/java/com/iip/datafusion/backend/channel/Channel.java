package com.iip.datafusion.backend.channel;

/**
 * 通道
 * @param <J> "Job"
 */
public interface Channel<J> {
    J take() throws InterruptedException;
    void put(J job) throws InterruptedException;
}
