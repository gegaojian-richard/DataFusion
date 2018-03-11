package com.iip.datafusion.jvs.controller;

import com.iip.datafusion.jvs.service.JvsService;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JvsController {
    @Autowired
    JvsService tvsService;
    @Autowired
    UserManager userManager;


    @RequestMapping(path={"/tvs/allTasks"},method = RequestMethod.POST)
    @ResponseBody
    public Result allTasks(){ return tvsService.allTasks(); }

    @RequestMapping(path={"/tvs/privateTasks"},method = RequestMethod.POST)
    @ResponseBody
    public Result privateTasks(){ return tvsService.privateTasks(userManager.getUserId()); }
}
