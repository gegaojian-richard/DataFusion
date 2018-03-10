package com.iip.datafusion.jvs.service;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.stereotype.Service;

@Service
public class JvsService {

    public Result allTasks() {
        String res = "";
        try{
            res += JsonParse.getMapper().writeValueAsString(JobRegistry.getInstance().getSubmittedJobRegistry());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(1,null,res);
    }


}
