package com.iip.datafusion.nsps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iip.datafusion.backend.IntegrityManager;
import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.TestManager;
import com.iip.datafusion.backend.job.JobStatusType;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.integrity.IntegrityJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.backend.parser.IntegrityParser;
import com.iip.datafusion.backend.parser.TestParser;
import com.iip.datafusion.dgs.model.integrity.IntegrityConfiguration;
import com.iip.datafusion.nsps.model.TestConfiguration;
import com.iip.datafusion.util.jsonutil.JsonParse;
import org.springframework.stereotype.Service;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午2:19
 */
@Service
public class TestService {
    public TestJob commitJob(TestConfiguration testConfiguration,int userID)throws Exception{
        TestJob testJob = TestParser.parse(testConfiguration);
        testJob.setJobType(JobType.TOPIC_MODEL);
        testJob.setUserID(userID);
        JobRegistry.getInstance().regist(testJob);
//        try{
//            System.out.println(JsonParse.getMapper().writeValueAsString(testJob));
//        }
//        catch (JsonProcessingException e){
//            e.printStackTrace();
//        }
        TestManager.getInstance().commitJob(testJob);

        return testJob;
    }
}
