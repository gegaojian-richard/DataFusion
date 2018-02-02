package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TopicModelJob;
import com.iip.datafusion.nsps.model.TFIDFConfiguration;
import com.iip.datafusion.nsps.model.TopicModelConfiguration;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:52
 */
public class TopicModelParser implements Parser{
    public static TopicModelJob parse(TopicModelConfiguration configuration)throws Exception{

        TopicModelJob job = new TopicModelJob();

        job.setCorpusPath(configuration.getCorpusPath());
        job.setTopicNum(configuration.getTopicNum());
        job.setTableName(configuration.getTableName());
        job.setDataSourceId(configuration.getDataSourceId());
        return job;
    }
}
