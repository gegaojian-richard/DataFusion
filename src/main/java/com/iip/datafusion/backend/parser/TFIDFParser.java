package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.algorithm.TFIDFJob;
import com.iip.datafusion.backend.job.algorithm.TextRankJob;
import com.iip.datafusion.nsps.model.TFIDFConfiguration;
import com.iip.datafusion.nsps.model.TextRankConfiguration;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午9:41
 */
public class TFIDFParser implements Parser{
    public static TFIDFJob parse(TFIDFConfiguration configuration)throws Exception{

        TFIDFJob job = new TFIDFJob();

        job.setCorpusPath(configuration.getCorpusPath());
        job.setTopK(configuration.getTopK());
        job.setTableName(configuration.getTableName());
        job.setDataSourceId(configuration.getDataSourceId());
        return job;
    }
}
