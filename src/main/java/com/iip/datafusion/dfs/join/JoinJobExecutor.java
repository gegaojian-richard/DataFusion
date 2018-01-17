package com.iip.datafusion.dfs.join;

import com.iip.datafusion.executor.Job;
import com.iip.datafusion.executor.JobExecutor;

import java.util.concurrent.ConcurrentLinkedQueue;

public class JoinJobExecutor implements JobExecutor{
    private ConcurrentLinkedQueue<JoinJob> waitingJobs = new ConcurrentLinkedQueue<>();
    @Override
    public void commit(Job job) {
        waitingJobs.add((JoinJob) job);
    }
}
