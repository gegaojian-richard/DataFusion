package com.iip.datafusion.backend.executor;

/**
 * 工作执行器（消费者）
 * @param <J> 工作类型
 * @Author Ge GaoJian
 */
public interface JobExecutor<J> {
    void doJob(J job) throws Exception;
}
