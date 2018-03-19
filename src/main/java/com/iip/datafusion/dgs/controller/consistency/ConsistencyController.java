package com.iip.datafusion.dgs.controller.consistency;

import com.iip.datafusion.dgs.model.consistency.ConsistencyConfiguration;
import com.iip.datafusion.dgs.service.consistency.ConsistencyService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ConsistencyController {
    @Autowired
    ConsistencyService ConsistencyService;
    @RequestMapping(path={"/if2"},method = RequestMethod.POST)
    @ResponseBody
    public Result commitJob(@RequestBody ConsistencyConfiguration ConsistencyConfiguration){

        Map map = ConsistencyService.commitJob(ConsistencyConfiguration);
        return new Result();
//        return checkBasicConsistencyConfiguration.toString();
    }
}
