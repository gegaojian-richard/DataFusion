package com.iip.datafusion.nsps.service;

import com.iip.datafusion.backend.JobRegistry;
import com.iip.datafusion.backend.TestManager;
import com.iip.datafusion.backend.TextRankManager;
import com.iip.datafusion.backend.job.JobType;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.backend.parser.TestParser;
import com.iip.datafusion.backend.parser.TextRankParser;
import com.iip.datafusion.nsps.model.TestConfiguration;
import com.iip.datafusion.nsps.model.TextRankConfiguration;
import org.springframework.stereotype.Service;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午3:14
 */
@Service
public class TextRankService {
    public TextRankJob commitJob(TextRankConfiguration textRankConfiguration,int userID)throws Exception{
        TextRankJob textRankJob = TextRankParser.parse(textRankConfiguration);
        textRankJob.setJobType(JobType.TEXT_RANK);
        textRankJob.setUserID(userID);
        JobRegistry.getInstance().regist(textRankJob);
        TextRankManager.getInstance().commitJob(textRankJob);

        return textRankJob;
    }
}
