package com.iip.datafusion.backend.job.join;

import com.iip.datafusion.backend.job.Job;

/**
 * 数据整合工作描述
 * Created by GeGaojian on 2018/01/18.
 */
public class JoinJob implements Job {
    String targetTableName;
    String targetDatasourceID;
    JoinUnit joinUnits;


    public JoinJob(String targetTableName, String targetDatasourceID) {
        this.targetTableName = targetTableName;
        this.targetDatasourceID = targetDatasourceID;
    }
}
