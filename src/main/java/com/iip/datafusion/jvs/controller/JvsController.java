package com.iip.datafusion.jvs.controller;

import com.iip.datafusion.jvs.dao.RedisConfiguration;
import com.iip.datafusion.jvs.service.JvsService;
import com.iip.datafusion.jvs.service.RedisService;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JvsController {
    @Autowired
    JvsService tvsService;
    @Autowired
    private RedisService redisService;


    @RequestMapping(path={"/tvs/allTasks"},method = RequestMethod.POST)
    @ResponseBody
    public Result setCon(){ return tvsService.allTasks(); }

    @RequestMapping(path = "/tvs/redisData",method = RequestMethod.POST)
    @ResponseBody
    public Result getPage(@RequestBody RedisConfiguration redisConfiguration){
        JSONObject wholeJsonObj = new JSONObject();
        wholeJsonObj.put("items",redisService.getLrange(redisConfiguration.getKey(),redisConfiguration.getStart(),redisConfiguration.getEnd()).toString());
        return new Result(0,null,wholeJsonObj.toString());
    }


}
