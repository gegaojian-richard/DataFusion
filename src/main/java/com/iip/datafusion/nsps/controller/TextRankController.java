package com.iip.datafusion.nsps.controller;

import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.nsps.model.TestConfiguration;
import com.iip.datafusion.nsps.model.TextRankConfiguration;
import com.iip.datafusion.nsps.service.TestService;
import com.iip.datafusion.nsps.service.TextRankService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午3:10
 */
@Controller
public class TextRankController {
    @Autowired
    TextRankService textRankService;



    @RequestMapping(path = {"/nsps/TextRank"}, method = RequestMethod.POST)
    @ResponseBody
    public Result checkIntegrity(@RequestBody TextRankConfiguration textRankConfiguration) {

        try{
            TextRankJob textRankJob = textRankService.commitJob(textRankConfiguration);
            System.out.println("TextRankController: " + textRankJob.getPath());
            while(textRankJob.getResult() == null){
                Thread.sleep(1000);
            }
            return textRankJob.getResult();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
    }
}
