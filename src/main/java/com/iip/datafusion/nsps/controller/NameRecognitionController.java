package com.iip.datafusion.nsps.controller;

import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.nsps.model.NameRecognitionConfiguration;
import com.iip.datafusion.nsps.service.NameRecognitionService;
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
 * @Date 2018/2/1 下午4:13
 */
@Controller
public class NameRecognitionController {
    @Autowired
    NameRecognitionService nameRecognitionService;

    @Autowired
    UserManager userManager;

    @RequestMapping(path = {"/nsps/NameRecognition"}, method = RequestMethod.POST)
    @ResponseBody
    public Result nameRecognition(@RequestBody NameRecognitionConfiguration configuration) {

        try{
            NameRecognitionJob job = nameRecognitionService.commitJob(configuration,userManager.getUserId());
//            System.out.println("Controller: " + job.getCorpusPath());
            Result result = new Result(1,"Task Submitted successfully",null);

            return result;
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
    }
}
