package com.iip.datafusion.nsps.service;

import com.iip.datafusion.backend.NameRecognitionManager;
import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.backend.parser.NameRecognitionParser;
import com.iip.datafusion.nsps.model.NameRecognitionConfiguration;
import org.springframework.stereotype.Service;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:14
 */
@Service
public class NameRecognitionService {

    public NameRecognitionJob commitJob(NameRecognitionConfiguration configuration)throws Exception{
        NameRecognitionJob job = NameRecognitionParser.parse(configuration);
        NameRecognitionManager.getInstance().commitJob(job);

        return job;
    }
}
