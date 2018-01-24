package com.iip.datafusion.backend;

import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.config.RegistryConfig;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 工作注册表
 * 单例(饿汉)
 * Created by GeGaojian on 2018/01/19.
 */
public class JobRegistry {
    // 内存缓存CACHESIZE条job
    private final ConcurrentMap<String, Integer> submittedJobRegistry; // key: JobId, value: Job完成度 -1-等待 0-进行中 1-已完成

    private final static JobRegistry singleInstance = new JobRegistry();

    private JobRegistry(){
        submittedJobRegistry = new ConcurrentHashMap<>(Capabilities.REGISTRY_CACHE_SIZE);
    }

    public static JobRegistry getInstance(){
        return singleInstance;
    }

    /**
     * 调用注册之前需要更新相应Executor的terminationToken.reservations
     * @param job
     */
    public void regist(Job job) {
        String key = "userid - jobtype - job id and decription"; // 从job中获得
        submittedJobRegistry.put(key, -1);
    }

    /**
     * 调用之前更新相应Executor的terminationToken.reservations
     * @param job
     * @param state
     */
    public void update(Job job, int state){
        String key = "userid - jobtype - job id and decription"; // 从job中获得
        submittedJobRegistry.put(key, state);
    }

    public ConcurrentMap<String,Integer> getSubmittedJobRegistry(){
        return this.submittedJobRegistry;
    }
}
