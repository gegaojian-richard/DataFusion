package com.iip.datafusion.nsps.controller;

import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.nsps.model.TFIDFConfiguration;
import com.iip.datafusion.nsps.model.TestConfiguration;
import com.iip.datafusion.nsps.service.TFIDFService;
import com.iip.datafusion.nsps.service.TestService;
import com.iip.datafusion.util.jsonutil.Result;
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



    @RequestMapping(path = {"/nsps/TFIDF"}, method = RequestMethod.POST)
    @ResponseBody
    public Result tfidf(@RequestBody TFIDFConfiguration configuration) {

        try{
            TFIDFJob job = service.commitJob(configuration);
//            System.out.println("Controller: " + job.getCorpusPath());
            while(job.getResult() == null){
                Thread.sleep(1000);
            }
            return job.getResult();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
    }
}
