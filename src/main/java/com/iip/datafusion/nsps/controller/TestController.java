package com.iip.datafusion.nsps.controller;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.dgs.model.integrity.IntegrityConfiguration;
import com.iip.datafusion.dgs.service.integrity.IntegrityService;
import com.iip.datafusion.nsps.model.TestConfiguration;
import com.iip.datafusion.nsps.service.TestService;
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
 * @Date 2018/1/31 下午2:18
 */
@Controller
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    UserManager userManager;



    @RequestMapping(path = {"/nsps/Test"}, method = RequestMethod.POST)
    @ResponseBody
    public Result checkTest(@RequestBody TestConfiguration testConfiguration) {

        try{
            TestJob testJob = testService.commitJob(testConfiguration,userManager.getUserId());
            System.out.println("Controller: " + testJob.getPath());
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
        return new Result();
    }
}
