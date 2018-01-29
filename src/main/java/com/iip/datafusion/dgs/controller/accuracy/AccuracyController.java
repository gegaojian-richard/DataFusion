package com.iip.datafusion.dgs.controller.accuracy;

import com.iip.datafusion.dgs.model.accuracy.AccuracyConfiguration;
import com.iip.datafusion.dgs.service.CommonService;
import com.iip.datafusion.dgs.service.accuracy.AccuracyService;
import com.iip.datafusion.util.jsonutil.Result;
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
    private CommonService commonService;
    @RequestMapping(path={"/commitjob"},method = RequestMethod.POST)
    @ResponseBody
    public Result commitjob(@RequestBody AccuracyConfiguration accuracyConfiguration){
        accuracyService.commitJob(accuracyConfiguration);
        commonService.doSelect("primary","person_info","*","id = '0123'");
        return new Result();
    }
}
