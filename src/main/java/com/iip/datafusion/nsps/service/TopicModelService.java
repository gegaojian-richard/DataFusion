package com.iip.datafusion.nsps.service;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.TFIDFManager;
import com.iip.datafusion.backend.TopicModelManager;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TopicModelJob;
import com.iip.datafusion.backend.parser.TFIDFParser;
import com.iip.datafusion.backend.parser.TopicModelParser;
import com.iip.datafusion.nsps.model.TFIDFConfiguration;
import com.iip.datafusion.nsps.model.TopicModelConfiguration;
import org.springframework.stereotype.Service;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:51
 */
@Service
public class TopicModelService {
    public TopicModelJob commitJob(TopicModelConfiguration configuration,int userID)throws Exception{
        TopicModelJob job = TopicModelParser.parse(configuration);
        job.setJobType(JobType.TOPIC_MODEL);
        job.setUserID(userID);
        JobRegistry.getInstance().regist(job);
        TopicModelManager.getInstance().commitJob(job);

        return job;
    }
}
