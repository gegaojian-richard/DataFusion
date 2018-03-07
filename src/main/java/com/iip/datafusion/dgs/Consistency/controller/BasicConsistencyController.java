package com.iip.datafusion.dgs.Consistency.controller;

import com.iip.datafusion.dgs.Consistency.model.CheckBasicConsistencyConfiguration;
import com.iip.datafusion.dgs.Consistency.service.CheckBasicConsistencyService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class BasicConsistencyController {
    @Autowired
    CheckBasicConsistencyService checkBasicConsistencyService;

    @RequestMapping(path={"/if"},method = RequestMethod.POST)
    @ResponseBody
    public String commitJob(@RequestBody CheckBasicConsistencyConfiguration checkBasicConsistencyConfiguration){

        Map map = checkBasicConsistencyService.commitJob(checkBasicConsistencyConfiguration);
//        return new Result();
        return checkBasicConsistencyConfiguration.toString();
    }

    @RequestMapping(path={"/dgs/consistency/testmyc"},method = RequestMethod.POST)
    @ResponseBody
    public Result commitJobs(@RequestParam String e){
        System.out.println(e);
        return new Result(0,e,e);
    }
}
