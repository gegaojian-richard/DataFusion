package com.iip.datafusion.backend.job.algorithm;

import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.util.jsonutil.Result;


/**
 * @Author YLX
 * @Date 2018/2/1
 */
public class Doc2VecJob implements Job {

    private String dataSourceId;

    private String path;

    private Result result;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Result getResult() { return result; }

    public void setResult(Result result) {
        this.result = result;
    }

}
