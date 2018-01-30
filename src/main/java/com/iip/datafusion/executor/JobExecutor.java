package com.iip.datafusion.executor;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface JobExecutor {
    public void commit(Job job);
}
