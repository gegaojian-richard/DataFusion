package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.backend.job.test.TestJob;
import com.iip.datafusion.nsps.model.TestConfiguration;
import com.iip.datafusion.nsps.model.TextRankConfiguration;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午3:18
 */
public class TextRankParser implements Parser{
    public static TextRankJob parse(TextRankConfiguration configuration)throws Exception{

        TextRankJob job = new TextRankJob();

        job.setCorpusPath(configuration.getCorpusPath());
        job.setTopK(configuration.getTopK());
        job.setTableName(configuration.getTableName());
        job.setDataSourceId(configuration.getDataSourceId());
        return job;

    }
}
