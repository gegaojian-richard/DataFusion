package com.iip.datafusion.backend.job.algorithm;

import com.iip.datafusion.util.jsonutil.Result;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:18
 */
public class NameRecognitionJob {
    private String path;
    private String tableName;
    private Result result;

    public NameRecognitionJob(){
        path = "";
    }

    public NameRecognitionJob(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
