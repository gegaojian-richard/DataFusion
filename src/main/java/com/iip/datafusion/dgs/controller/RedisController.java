package com.iip.datafusion.dgs.controller;

import com.iip.datafusion.dgs.model.RedisParam;
import com.iip.datafusion.dgs.service.AccuracyService;
import com.iip.datafusion.dgs.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengc
 * @date 2018/1/25 17:16
 */
@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;


    @RequestMapping(path = "/dgs/redisTest",method = RequestMethod.POST)
    @ResponseBody
    public String getCache(@RequestBody RedisParam redisParam){
        return redisService.getLrange(redisParam.getKey(),redisParam.getStart(),redisParam.getEnd()).toString();
    }
}
