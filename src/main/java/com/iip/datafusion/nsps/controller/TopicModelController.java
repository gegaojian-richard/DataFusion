package com.iip.datafusion.nsps.controller;

import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TopicModelJob;
import com.iip.datafusion.nsps.model.TFIDFConfiguration;
import com.iip.datafusion.nsps.model.TopicModelConfiguration;
import com.iip.datafusion.nsps.service.TFIDFService;
import com.iip.datafusion.nsps.service.TopicModelService;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:50
 */
@Controller
public class TopicModelController {
    @Autowired
    TopicModelService service;
    @Autowired
    UserManager userManager;

    @RequestMapping(path = {"/nsps/TopicModel"}, method = RequestMethod.POST)
    @ResponseBody
    public Result topicModel(@RequestBody TopicModelConfiguration configuration) {

        try{
            TopicModelJob job = service.commitJob(configuration,userManager.getUserId());
            Result result = new Result(1,"Task Submitted successfully",null);

            return result;
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
    }
}
