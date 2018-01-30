package com.iip.datafusion.dfs.join;

import com.iip.datafusion.executor.Job;

/**
 * Created by GeGaojian on 2017/12/31.
 */
public class JoinJob implements Job{
    String targetTableName;
    String targetDatasourceID;
    JoinUnit joinUnits;


    public JoinJob(String targetTableName, String targetDatasourceID) {
        this.targetTableName = targetTableName;
        this.targetDatasourceID = targetDatasourceID;
    }
}
