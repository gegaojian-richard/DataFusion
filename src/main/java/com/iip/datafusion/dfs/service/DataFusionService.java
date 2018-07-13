package com.iip.datafusion.dfs.service;


import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.JoinManager;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.join.JoinJob;
import com.iip.datafusion.backend.parser.JoinParser;
import com.iip.datafusion.dfs.model.JoinConfiguration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeGaojian on 2017/12/22.
 */

@Service
public class DataFusionService {

    public Map<String, Object> commitJob(JoinConfiguration joinConfiguration,int userID){
        Map<String,Object> map = new HashMap();

        // 1. 解析JoinRule为包含多个SQLTask的JoinJob
        JoinJob joinJob = JoinParser.parse(joinConfiguration);
        joinJob.setJobType(JobType.JOIN);
        joinJob.setUserID(userID);
        JobRegistry.getInstance().regist(joinJob);
        // 2. 向Join后台管理员提交JoinJob
        JoinManager.getInstance().commitJob(joinJob);

        map.put("msg", "整合任务提交成功");
        return map;
    }
}
