package com.iip.datafusion.backend;

import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 工作注册表
 * 单例(饿汉)
 * Created by GeGaojian on 2018/01/19.
 */
public class JobRegistry {
    @Autowired DataSourceRouter dataSourceRouter;

    // 内存缓存CACHESIZE条job
    private final ConcurrentMap<Integer, List<Job>> userJobList; // key: userID, value: list of this user's job

    private final static JobRegistry singleInstance = new JobRegistry();


    private JobRegistry(){
        userJobList = new ConcurrentHashMap<>(Capabilities.REGISTRY_CACHE_SIZE);
    }

    public static JobRegistry getInstance(){
        return singleInstance;
    }

    /**
     * 调用注册之前需要更新相应Executor的terminationToken.reservations
     * @param job
     */
    public void regist(Job job) {
        //设置JOBID
        job.setJobID(dataSourceRouter.getJOB_COUNT().getAndIncrement());
        job.setStatus(JobStatusType.WAITING);

        //根据不同userID，入队
        Integer userID= job.getUserID();
        userJobList.getOrDefault(userID,new ArrayList<Job>()).add(job);

    }

    /**
     * 调用之前更新相应Executor的terminationToken.reservations
     * @param job
     * @param state
     */
    public void update(Job job, JobStatusType state){
        job.setStatus(state);
        if(state.equals(JobStatusType.ERROR) || state.equals(JobStatusType.SUCCESS)) {
            userJobList.get(job.getUserID()).remove(job);
            //TODO job信息入库存储
        }
    }

    public ConcurrentMap<Integer,List<Job>> getUserJobList(){
        return this.userJobList;
    }
}
