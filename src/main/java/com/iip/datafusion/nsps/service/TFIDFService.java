package com.iip.datafusion.nsps.service;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.TFIDFManager;
import com.iip.datafusion.backend.TextRankManager;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.parser.TFIDFParser;
import com.iip.datafusion.backend.parser.TextRankParser;
import com.iip.datafusion.nsps.model.TFIDFConfiguration;
import com.iip.datafusion.nsps.model.TextRankConfiguration;
import org.springframework.stereotype.Service;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午9:39
 */
@Service
public class TFIDFService {
    public TFIDFJob commitJob(TFIDFConfiguration configuration,int userID)throws Exception{
        TFIDFJob job = TFIDFParser.parse(configuration);
        job.setJobType(JobType.TF_IDF);
        job.setUserID(userID);
        JobRegistry.getInstance().regist(job);
        TFIDFManager.getInstance().commitJob(job);

        return job;
    }
}
