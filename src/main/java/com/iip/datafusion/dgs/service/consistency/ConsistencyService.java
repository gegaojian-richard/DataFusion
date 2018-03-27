package com.iip.datafusion.dgs.service.consistency;

import com.iip.datafusion.backend.ConsistencyManager;
import com.iip.datafusion.backend.job.consistency.ConsistencyJob;
import com.iip.datafusion.dgs.model.consistency.ConsistencyConfiguration;
import com.iip.datafusion.backend.parser.ConsistencyParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConsistencyService {
    public Map<String, Object> commitJob(ConsistencyConfiguration ConsistencyConfiguration){
        Map<String,Object> map = new HashMap();

        ArrayList<ConsistencyJob> consistencyJoblist = ConsistencyParser.parse(ConsistencyConfiguration);

        for(int i = 0;i<consistencyJoblist.size();i++){
            ConsistencyManager.getInstance().commitJob(consistencyJoblist.get(i));
        }

        map.put("message", "检查多对一致性提交成功");

        return map;
    }
}
