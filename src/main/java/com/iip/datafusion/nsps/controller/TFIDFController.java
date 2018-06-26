package com.iip.datafusion.nsps.controller;

import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.nsps.model.TFIDFConfiguration;
import com.iip.datafusion.nsps.model.TestConfiguration;
import com.iip.datafusion.nsps.service.TFIDFService;
import com.iip.datafusion.nsps.service.TestService;
import com.iip.datafusion.ums.model.User;
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
 * @Date 2018/2/1 下午9:38
 */
@Controller
public class TFIDFController {
    @Autowired
    TFIDFService service;

    @Autowired
    UserManager userManager;

    @RequestMapping(path = {"/nsps/TFIDF"}, method = RequestMethod.POST)
    @ResponseBody
    public Result tfidf(@RequestBody TFIDFConfiguration configuration) {

        try{
            TFIDFJob job = service.commitJob(configuration,userManager.getUserId());
//            System.out.println("Controller: " + job.getCorpusPath());
            Result result = new Result(1,"Task Submitted successfully",null);

            return result;
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
    }
}
