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

        ArrayList<ConsistencyJob> consistencyJoblist = ConsistencyParser.parse(ConsistencyConfiguration);

        for(int i = 0;i<consistencyJoblist.size();i++){
            consistencyJoblist.get(i).setUserID(userId);
            consistencyJoblist.get(i).setJobType(JobType.CONSISTENCY);
            JobRegistry.getInstance().regist(consistencyJoblist.get(i));
            ConsistencyManager.getInstance().commitJob(consistencyJoblist.get(i));
        }

        map.put("message", "检查多对一致性提交成功");

        return map;
    }
}
