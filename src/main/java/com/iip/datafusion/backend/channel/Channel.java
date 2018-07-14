package com.iip.datafusion.backend.channel;

/**
 * 管道
 * @param <J> "Job"
 * @Author Ge GaoJian
 */
public interface Channel<J> {
    J take() throws InterruptedException;
    void put(J job) throws InterruptedException;
}
