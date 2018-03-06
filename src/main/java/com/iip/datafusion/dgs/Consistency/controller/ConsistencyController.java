package com.iip.datafusion.dgs.Consistency.controller;

import com.iip.datafusion.dgs.Consistency.model.CheckConsistencyConfiguration;
import com.iip.datafusion.dgs.Consistency.service.CheckConsistencyService;
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
    CheckConsistencyService checkConsistencyService;
    @RequestMapping(path={"/if2"},method = RequestMethod.POST)
    @ResponseBody
    public Result commitJob(@RequestBody CheckConsistencyConfiguration checkConsistencyConfiguration){

        Map map = checkConsistencyService.commitJob(checkConsistencyConfiguration);
        return new Result();
//        return checkBasicConsistencyConfiguration.toString();
    }
}
