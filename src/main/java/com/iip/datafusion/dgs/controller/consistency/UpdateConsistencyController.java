package com.iip.datafusion.dgs.controller.consistency;

import com.iip.datafusion.dgs.model.consistency.UpdateConsistencyConfiguration;
import com.iip.datafusion.dgs.service.consistency.UpdateConsistencyService;
import com.iip.datafusion.util.jsonutil.Result;
import com.iip.datafusion.util.userutil.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UpdateConsistencyController {
    @Autowired
    UpdateConsistencyService UpdateConsistencyService;
    @Autowired
    UserManager userManager;
    @RequestMapping(path={"/dgs/consistency/CommitUpdateJob"},method = RequestMethod.POST)
    @ResponseBody
    public Result commitUpdateJob(@RequestBody UpdateConsistencyConfiguration UpdateConsistencyConfiguration){
        try{
            Map map = UpdateConsistencyService.CommitUpdateJob(UpdateConsistencyConfiguration,userManager.getUserId());
            Result res = new Result(1,"Task Submitted successfully",null);;
            //res.setMsg(integrityJob.getJobId());
            return res;
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
//        return checkBasicConsistencyConfiguration.toString();
    }
}
