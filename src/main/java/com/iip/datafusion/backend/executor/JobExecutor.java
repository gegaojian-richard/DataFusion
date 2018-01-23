package com.iip.datafusion.backend.executor;

import com.iip.datafusion.backend.common.AbstractTerminatableThread;

/**
 * 工作执行器（消费者）
 * @param <J> 工作类型
 */
public interface JobExecutor<J> {
    void doJob(J job) throws Exception;
}
