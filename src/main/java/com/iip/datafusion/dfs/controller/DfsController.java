package com.iip.datafusion.dfs.controller;

import com.iip.datafusion.dfs.model.JoinRule;
import com.iip.datafusion.dfs.service.DataFusionService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by GeGaojian on 2017/12/22.
 */

@Controller
@RequestMapping("/dfs")
public class DfsController {
    @Autowired
    DataFusionService dataFusionService;
    @RequestMapping(path={"/commitjob"},method = RequestMethod.POST)
    @ResponseBody
    public Result commitJob(@RequestBody JoinRule joinRule){

        Map map = dataFusionService.commitJob(joinRule);
        return new Result();
//        return joinRule.toString();
    }
}
