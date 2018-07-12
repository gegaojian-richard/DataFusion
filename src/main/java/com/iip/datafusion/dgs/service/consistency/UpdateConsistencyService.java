package com.iip.datafusion.dgs.service.consistency;

import com.iip.datafusion.backend.UpdateConsistencyManager;
import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.consistency.UpdateConsistencyJob;
import com.iip.datafusion.dgs.model.consistency.UpdateConsistencyConfiguration;
import com.iip.datafusion.backend.parser.UpdateConsistencyParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateConsistencyService {
    public Map<String, Object> CommitUpdateJob(UpdateConsistencyConfiguration UpdateConsistencyConfiguration,int userId){
        Map<String,Object> map = new HashMap();

        UpdateConsistencyJob UpdateConsistencyJob = UpdateConsistencyParser.parse(UpdateConsistencyConfiguration);


        UpdateConsistencyJob.setUserID(userId);
        UpdateConsistencyJob.setJobType(JobType.CONSISTENCY);
        JobRegistry.getInstance().regist(UpdateConsistencyJob);
        UpdateConsistencyManager.getInstance().commitJob(UpdateConsistencyJob);


        map.put("message", "一致性更新任务提交成功");

        return map;
    }
}
