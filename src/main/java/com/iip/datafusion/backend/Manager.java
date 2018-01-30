package com.iip.datafusion.backend;

import com.iip.datafusion.backend.configuration.Configuration;
import com.iip.datafusion.backend.job.JobType;

/**
 * 后台功能模块的唯一入口，业务层访问后台都通过此类
 * 管理所有的工作列表，是各种类型的执行器线程的拥有者
 * 单例(饿汉)
 */
public class Manager {

    // 关闭标识
    private volatile boolean shutdownRequested = false;
    // 各个执行器线程

    private final static Manager singleInstance = new Manager();

    private Manager(){
        // 创建各个执行器
    }

    public static Manager getInstance(){
        return singleInstance;
    }

    public void init(){
        // 启动各个执行器线程
    }


    public void commitJob(JobType jobType, Configuration configuration){
        // 1. 根据jobType,调用各自的解析器解析Configuration,生成相应的job
        // 2. 将job放入对应的通道中
    }

}
