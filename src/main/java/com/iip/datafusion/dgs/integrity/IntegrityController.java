package com.iip.datafusion.dgs.integrity;

import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.dgs.model.*;
import com.iip.datafusion.dgs.integrity.UpdateIntegrityConfiguration;
import com.iip.datafusion.dgs.integrity.IntegrityConfiguration;
import com.iip.datafusion.dgs.integrity.UpdateIntegrityParser;
import com.iip.datafusion.dgs.integrity.IntegrityService;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Map;


@Controller
public class IntegrityController {

    @Autowired
    IntegrityService integrityService;

    private int maxThreads = 3;


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
