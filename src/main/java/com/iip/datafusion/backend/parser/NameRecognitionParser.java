package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.algorithm.NameRecognitionJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.nsps.model.NameRecognitionConfiguration;
import com.iip.datafusion.nsps.model.TextRankConfiguration;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:19
 */
public class NameRecognitionParser implements Parser{
    public static NameRecognitionJob parse(NameRecognitionConfiguration nameRecognitionConfiguration)throws Exception{

        String path = nameRecognitionConfiguration.getPath();

        NameRecognitionJob job = new NameRecognitionJob();

        job.setPath(path);

        return job;

    }
}
