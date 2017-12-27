package com.iip.datafusion.dfs.controller;

import com.iip.datafusion.dfs.model.JoinRule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by GeGaojian on 2017/12/22.
 */

@Controller
@RequestMapping("/dfs")
public class DfsController {
    @RequestMapping(path={"/commitjob"},method = RequestMethod.POST)
    @ResponseBody
    public String commitJob(@RequestBody JoinRule joinRule){

        return joinRule.toString();
    }
}
