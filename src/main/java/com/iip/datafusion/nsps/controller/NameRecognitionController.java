package com.iip.datafusion.nsps.controller;

import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.nsps.model.NameRecognitionConfiguration;
import com.iip.datafusion.nsps.service.NameRecognitionService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:13
 */
@Controller
public class NameRecognitionController {
    @Autowired
    NameRecognitionService nameRecognitionService;



    @RequestMapping(path = {"/nsps/NameRecognition"}, method = RequestMethod.POST)
    @ResponseBody
    public Result nameRecognition(@RequestBody NameRecognitionConfiguration configuration) {

        try{
            NameRecognitionJob job = nameRecognitionService.commitJob(configuration);
            System.out.println("Controller: " + job.getPath());
            while(job.getResult() == null){
                Thread.sleep(1000);
            }
            return job.getResult();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
    }
}
