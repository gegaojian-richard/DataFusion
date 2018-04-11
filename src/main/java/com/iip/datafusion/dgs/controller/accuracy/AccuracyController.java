package com.iip.datafusion.dgs.controller.accuracy;

import com.iip.datafusion.dgs.model.accuracy.AccuracyConfiguration;
import com.iip.datafusion.dgs.service.CommonService;
import com.iip.datafusion.dgs.service.accuracy.AccuracyService;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dgs/accuracy")
public class AccuracyController {
    @Autowired
    private AccuracyService accuracyService;
    @Autowired
    UserManager userManager;

    @RequestMapping(path={"/commitjob"},method = RequestMethod.POST)
    @ResponseBody
    public Result commitjob(@RequestBody AccuracyConfiguration accuracyConfiguration){
        try{
        accuracyService.commitJob(accuracyConfiguration,userManager.getUserId());
        Result res = new Result(1,"Task Submitted successfully",null);;
        //res.setMsg(integrityJob.getJobId());
        return res;
    }catch (Exception e){
        return new Result(0,e.getMessage(),null);
    }
//        return new Result();
    }
}
