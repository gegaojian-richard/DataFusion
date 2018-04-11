package com.iip.datafusion.jvs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JvsService {

    public Result allTasks() {
        String res = "";
        try{
            res += JsonParse.getMapper().writeValueAsString(JobRegistry.getInstance().getUserJobList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(1,null,res);
    }

    public Result privateTasks(int userId) {
        List<JobBase> list = JobRegistry.getInstance().getUserJobList().get(userId);
        if(list==null || list.isEmpty())
            return new Result(0,null,null);
        try {
            StringBuilder json = new StringBuilder().append("[");
            for(JobBase job : list){
                json.append(job.toJson()).append(",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("]");
//            String json = JsonParse.getMapper().writeValueAsString(list);
            return new Result(1,null,json.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            return new Result(0,"json处理出错",null);
        }
    }
}
