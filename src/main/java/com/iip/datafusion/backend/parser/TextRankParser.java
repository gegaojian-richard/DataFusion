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

        String path = configuration.getPath();
        int topK = configuration.getTopK();
        String tableName = configuration.getTableName();

        TextRankJob job = new TextRankJob();

        job.setPath(path);
        job.setTopK(topK);
        job.setTableName(tableName);
        return job;

    }
}
