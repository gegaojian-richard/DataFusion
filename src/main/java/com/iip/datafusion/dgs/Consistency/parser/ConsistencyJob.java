package com.iip.datafusion.dgs.Consistency.parser;

import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;

public class ConsistencyJob extends JobBase implements Job {

    String mainDatasourceID;
    String mainTableName;


    public ConsistencyJob(String mainDatasourceID, String mainTableName) {
        this.mainDatasourceID = mainDatasourceID;
        this.mainTableName = mainTableName;
    }
}
