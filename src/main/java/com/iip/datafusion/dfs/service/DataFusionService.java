package com.iip.datafusion.dfs.service;


import com.iip.datafusion.backend.JoinManager;
import com.iip.datafusion.backend.job.join.JoinJob;
import com.iip.datafusion.backend.parser.JoinParser;
import com.iip.datafusion.dfs.model.JoinConfiguration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataFusionService {

    public Map<String, Object> commitJob(JoinConfiguration joinConfiguration){
        Map<String,Object> map = new HashMap();

        // 1. 解析JoinRule为包含多个SQLTask的JoinJob
        JoinJob joinJob = JoinParser.parse(joinConfiguration);
        // 2. 向Join后台管理员提交JoinJob
        JoinManager.getInstance().commitJob(joinJob);

        map.put("msg", "整合任务提交成功");
        return map;
    }

//    JoinJob doParse(JoinConfiguration joinConfiguration){
//        return JoinParser.parse(joinConfiguration);
//    };
    // 1. 解析JoinRule为包含多个SQLTask的JoinJob

    // 2. 提交JoinJob进入JoinJob队列

    // 3. JoinExecutor观察JoinJob队列，JoinJob队列有job进入后执行job

    // 4. JoinExecutor根据JoinJob中的SQLTask，按需向异步和同步SQLExcutor提交SQLTask
}
