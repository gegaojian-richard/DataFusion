package com.iip.datafusion.backend.job.algorithm;

import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.util.jsonutil.Result;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午9:41
 */
public class TFIDFJob implements Job{
    private String path;
    private int topK;
    private String tableName;
    private String dataSourceId;
    private Result result;

    public TFIDFJob(){
        path = "";
        topK = 0;
    }

    public TFIDFJob(String path, int topK){
        this.path = path;
        this.topK = topK;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTopK(){
        return topK;
    }

    public void setTopK(int topK){
        this.topK = topK;
    }

    public String getTableName(){return tableName;}

    public void setTableName(String tableName) {this.tableName = tableName;}

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
