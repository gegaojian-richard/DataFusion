package com.iip.datafusion.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iip.datafusion.backend.config.Capabilities;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.jsonutil.JsonParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 工作注册表
 * 单例(饿汉)
 * Created by GeGaojian on 2018/01/19.
 */
public class JobRegistry {

    // 内存缓存CACHESIZE条job
    private final ConcurrentMap<Integer, List<JobBase>> userJobList; // key: userID, value: list of this user's job

    private final static JobRegistry singleInstance = new JobRegistry();

    private AtomicInteger JOB_COUNT = new AtomicInteger();

    private JobRegistry(){
        userJobList = new ConcurrentHashMap<Integer, List<JobBase>>(Capabilities.REGISTRY_CACHE_SIZE);

    }

    public static JobRegistry getInstance(){
        return singleInstance;
    }

    /**
     * 调用注册之前需要更新相应Executor的terminationToken.reservations
     * @param job
     */
    public void regist(JobBase job) {
        //设置JOBID
        job.setJobID(JOB_COUNT.getAndIncrement());
        job.setStatus(JobStatusType.WAITING);

        //根据不同userID，入队
        Integer userID= job.getUserID();
        userJobList.computeIfAbsent(userID, k -> new ArrayList<>());
        userJobList.get(userID).add(job);

        //test
        try {
            System.out.print(JsonParse.getMapper().writeValueAsString(userJobList.get(job.getUserID())));
        }
        catch (JsonProcessingException e ){
            e.printStackTrace();
        }

    }

    /**
     * 调用之前更新相应Executor的terminationToken.reservations
     * @param job
     * @param state
     */
    public void update(JobBase job, JobStatusType state){
        job.setStatus(state);
//        if(state.equals(JobStatusType.ERROR) || state.equals(JobStatusType.SUCCESS)) {
//            userJobList.get(job.getUserID()).remove(job);
//            //TODO job信息入库存储
//        }
    }

    public ConcurrentMap<Integer,List<JobBase>> getUserJobList(){
        return this.userJobList;
    }

    public JobBase getJob(int userID, int jobID){
        List<JobBase> userJobs = this.userJobList.get(userID);
        if (userJobs != null){
            System.out.println("Jobs");
            for(int i=0;i<userJobs.size();i++) {
                System.out.println(userJobs.get(i).getJobID()+""+jobID+userID);
                if (userJobs.get(i).getJobID() == jobID)
                    return userJobs.get(i);
            }
        }
        System.out.println("null get");
        return null;
    }

}
