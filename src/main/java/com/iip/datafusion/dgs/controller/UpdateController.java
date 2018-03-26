package com.iip.datafusion.dgs.controller;

import com.iip.datafusion.dgs.model.UpdateConfiguration;
import com.iip.datafusion.dgs.model.UpdateJob;
import com.iip.datafusion.dgs.model.UpdateParser;
import com.iip.datafusion.dgs.service.CommonService;
import com.iip.datafusion.dgs.service.integrity.IntegrityService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zengc
 * @date 2018/3/26 19:11
 */
@Controller
public class UpdateController {


    @Autowired
    CommonService commonService;

    @RequestMapping(path = {"/dgs/update"}, method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody UpdateConfiguration updateIntegrityConfiguration) {
        try{
            UpdateJob updateJob = UpdateParser.parse(updateIntegrityConfiguration);
            List<String> sqlList = updateJob.getSqlList();
            for(String sql:sqlList){
                System.out.println(sql);
                commonService.doExecute(updateJob.getDataSourceId(),sql);
            }
            return new Result(1,"Task finished successfully!",null);
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
        //return new Result();
    }


}
