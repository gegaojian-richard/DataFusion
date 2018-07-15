package com.iip.datafusion.redis.controller;

import com.iip.datafusion.redis.model.RedisConfiguration;
import com.iip.datafusion.redis.service.RedisService;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zengc
 * @date 2018/3/11 16:42
 */
@Controller
public class RedisController {
    private Logger logger = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    private RedisService redisService;

    @RequestMapping(path = "/tvs/redisData",method = RequestMethod.POST)
    @ResponseBody
    public Result getPage(@RequestBody RedisConfiguration redisConfiguration){
        JSONObject wholeJsonObj = new JSONObject();
        wholeJsonObj.put("items",redisService.getLrange(redisConfiguration.getKey(),redisConfiguration.getStart(),redisConfiguration.getEnd()).toString());
        return new Result(1,null,wholeJsonObj.toString());
    }
    @RequestMapping(path = "/tvs/redisLen",method = RequestMethod.POST)
    @ResponseBody
    public Result getLength(@RequestBody RedisConfiguration redisConfiguration){

        return new Result(1,null,String.valueOf(redisService.getLlen(redisConfiguration.getKey())));
    }



}
