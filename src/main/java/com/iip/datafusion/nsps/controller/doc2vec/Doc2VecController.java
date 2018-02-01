package com.iip.datafusion.nsps.controller.doc2vec;

import com.iip.datafusion.backend.job.algorithm.Doc2VecJob;
import com.iip.datafusion.nsps.model.doc2vec.Doc2VecConfiguration;
import com.iip.datafusion.nsps.service.doc2vec.Doc2VecService;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author YLX
 * @Date 2018/2/1
 */
@Controller
public class Doc2VecController {

    @Autowired
    Doc2VecService doc2vecService;

    @RequestMapping(path = {"/nsps/Doc2Vec"}, method = RequestMethod.POST)
    @ResponseBody
    public Result checkDoc2Vec(@RequestBody Doc2VecConfiguration doc2vecConfiguration) {
        try{
            Doc2VecJob doc2vecJob = doc2vecService.commitJob(doc2vecConfiguration);
            System.out.println("controller: " + doc2vecJob.getDataSourceId());
//            while(doc2vecJob.getResult() == null) { Thread.sleep(1000);}
            return doc2vecJob.getResult();
        }catch (Exception e){
            return new Result(0,e.getMessage(),null);
        }
    }
    
}
