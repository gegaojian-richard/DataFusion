package com.iip.datafusion.dgs.controller.integrity;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.integrity.IntegrityConfiguration;
import com.iip.datafusion.dgs.service.integrity.IntegrityService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class IntegrityController {

    @Autowired
    IntegrityService integrityService;



    @RequestMapping(path = {"/dgs/checkIntegrity"}, method = RequestMethod.POST)
    @ResponseBody
    public Result checkIntegrity(@RequestBody IntegrityConfiguration integrityConfiguration) {

        try{

            IntegrityJob integrityJob = integrityService.commitJob(integrityConfiguration);
            while (integrityJob.getResult()==null){

            }
            return integrityJob.getResult();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
        //return new Result();
    }
    /*
    @RequestMapping(path = {"/dgs/updateIntegrity"}, method = RequestMethod.POST)
    @ResponseBody
    public Result updateIntegrity(@RequestBody UpdateIntegrityConfiguration updateIntegrityConfiguration) {
        try{
            IntegrityJob integrityJob = UpdateIntegrityParser.parse(updateIntegrityConfiguration);
            return integrityJob.run();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
        //return new Result();
    }
    */


}
