package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.algorithm.Doc2VecJob;
import com.iip.datafusion.nsps.model.doc2vec.Doc2VecConfiguration;


/**
 * @Author YLX
 * @Date 2018/2/1
 */
public class Doc2VecParser implements Parser{

    public static Doc2VecJob parse(Doc2VecConfiguration doc2vecConfiguration)throws Exception{
        String dataSourceId = doc2vecConfiguration.getDataSourceId();
        String path = doc2vecConfiguration.getPath();
        Doc2VecJob doc2VecJob = new Doc2VecJob();
        doc2VecJob.setDataSourceId(dataSourceId);
        doc2VecJob.setPath(path);
//        doc2VecJob.setDataSourceId("primary");
        System.out.println("parser:" + doc2VecJob.getDataSourceId());
        return doc2VecJob;
    }

}
