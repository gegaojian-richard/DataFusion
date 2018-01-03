package com.iip.datafusion.dfs.service;

import com.iip.datafusion.dfs.join.JoinJob;
import com.iip.datafusion.dfs.join.JoinParser;
import com.iip.datafusion.dfs.model.JoinRule;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataFusionService {

    public Map<String, Object> commitJob(JoinRule joinRule){
        Map<String,Object> map = new HashMap();

        // 1. 解析JoinRule为包含多个SQLTask的JoinJob
        JoinJob joinJob = JoinParser.parse(joinRule);
        // 2. 提交JoinJob进入JoinJob队列

        // 3. JoinExecutor观察JoinJob队列，JoinJob队列有job进入后执行job

        // 4. JoinExecutor根据JoinJob中的SQLTask，按需向异步和同步SQLExcutor提交SQLTask

        map.put("msg", "解析成功");
        return map;
    }

    JoinJob doParse(JoinRule joinRule){
        return JoinParser.parse(joinRule);
    };
    // 1. 解析JoinRule为包含多个SQLTask的JoinJob

    // 2. 提交JoinJob进入JoinJob队列

    // 3. JoinExecutor观察JoinJob队列，JoinJob队列有job进入后执行job

    // 4. JoinExecutor根据JoinJob中的SQLTask，按需向异步和同步SQLExcutor提交SQLTask
}
