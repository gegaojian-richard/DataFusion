package com.iip.datafusion.dgs.Consistency.service;

import com.iip.datafusion.backend.ConsistencyManager;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.dgs.Consistency.model.CheckConsistencyConfiguration;
import com.iip.datafusion.dgs.Consistency.parser.CheckConsistencyParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CheckConsistencyService {
    public Map<String, Object> commitJob(CheckConsistencyConfiguration checkConsistencyConfiguration){
        Map<String,Object> map = new HashMap();

        ArrayList<ConsistencyJob> consistencyJoblist = CheckConsistencyParser.parse(checkConsistencyConfiguration);

        for(int i = 0;i<consistencyJoblist.size();i++){
            ConsistencyManager.getInstance().commitJob(consistencyJoblist.get(i));
        }

        map.put("message", "检查多对一致性提交成功");

        return map;
    }
}
