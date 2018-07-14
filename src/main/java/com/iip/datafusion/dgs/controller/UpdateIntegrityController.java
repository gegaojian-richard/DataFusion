package com.iip.datafusion.dgs.controller;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.UpdateIntegrityService;
import com.iip.datafusion.dgs.model.UpdateIntegrityConfiguration;
import com.iip.datafusion.dgs.model.UpdateIntegrityJob;
import com.iip.datafusion.dgs.model.UpdateIntegrityParser;
import com.iip.datafusion.dgs.service.CommonService;
import com.iip.datafusion.util.jsonutil.Result;
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
 * @date 2018/3/26 19:11
 */
@Controller
public class UpdateIntegrityController {

    protected static Logger logger= LoggerFactory.getLogger(UpdateIntegrityController.class);
    @Autowired
    UpdateIntegrityService updateIntegrityService;

    @RequestMapping(path = {"/integrity/updateIntegrity"}, method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody UpdateIntegrityConfiguration updateIntegrityConfiguration) {
        try{
            logger.info("enter UpdateIntegrityController.update()");
            UpdateIntegrityJob updateIntegrityJob = updateIntegrityService.commitJob(updateIntegrityConfiguration);

            return new Result(1,"Task finished successfully!",null);
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
        //return new Result();
    }


}
