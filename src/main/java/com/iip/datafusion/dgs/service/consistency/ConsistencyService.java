package com.iip.datafusion.dgs.service.consistency;

import com.iip.datafusion.backend.ConsistencyManager;
import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.dgs.model.consistency.ConsistencyConfiguration;
import com.iip.datafusion.backend.parser.ConsistencyParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConsistencyService {
    public Map<String, Object> commitJob(ConsistencyConfiguration ConsistencyConfiguration,int userId){
        Map<String,Object> map = new HashMap();

        ConsistencyJob consistencyJob = ConsistencyParser.parse(ConsistencyConfiguration);


        consistencyJob.setUserID(userId);
        consistencyJob.setJobType(JobType.CONSISTENCY);
        JobRegistry.getInstance().regist(consistencyJob);
        ConsistencyManager.getInstance().commitJob(consistencyJob);


        map.put("message", "检查多对一致性提交成功");

        return map;
    }
}
