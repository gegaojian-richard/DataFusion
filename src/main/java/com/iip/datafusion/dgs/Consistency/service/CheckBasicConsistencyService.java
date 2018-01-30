package com.iip.datafusion.dgs.Consistency.service;

import com.iip.datafusion.backend.ConsistencyManager;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.dgs.Consistency.model.CheckBasicConsistencyConfiguration;
import com.iip.datafusion.dgs.Consistency.parser.CheckBasicConsistencyParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//import com.iip.datafusion.backend.parser.ConsistencyParser;
//import com.iip.datafusion.dgs.Consistency.parser.ConsistencyJob;

@Service
public class CheckBasicConsistencyService {
    public Map<String, Object> commitJob(CheckBasicConsistencyConfiguration checkBasicConsistencyConfiguration){
        Map<String,Object> map = new HashMap();

        ConsistencyJob consistencyJob = CheckBasicConsistencyParser.parse(checkBasicConsistencyConfiguration);

        ConsistencyManager.getInstance().commitJob(consistencyJob);

        map.put("message", "检查一致性提交成功");

        return map;
    }
}
