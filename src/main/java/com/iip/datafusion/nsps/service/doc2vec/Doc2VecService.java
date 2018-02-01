package com.iip.datafusion.nsps.service.doc2vec;

import com.iip.datafusion.backend.Doc2VecManager;
import com.iip.datafusion.backend.job.algorithm.Doc2VecJob;
import com.iip.datafusion.backend.parser.Doc2VecParser;
import com.iip.datafusion.nsps.model.doc2vec.Doc2VecConfiguration;
import org.springframework.stereotype.Service;


/**
 * @Author YLX
 * @Date 2018/2/1
 */
@Service
public class Doc2VecService {

    public Doc2VecJob commitJob(Doc2VecConfiguration doc2vecConfiguration)throws Exception{
        Doc2VecJob doc2vecJob = Doc2VecParser.parse(doc2vecConfiguration);
        Doc2VecManager.getInstance().commitJob(doc2vecJob);
        return doc2vecJob;
    }

}
