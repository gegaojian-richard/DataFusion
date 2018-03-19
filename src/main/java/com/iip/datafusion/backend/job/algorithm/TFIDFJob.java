package com.iip.datafusion.backend.job.algorithm;

import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.jsonutil.Result;

import javax.swing.*;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午9:41
 */
public class TFIDFJob extends JobBase implements Job{
    private String corpusPath;
    private int topK;
    private String tableName;
    private String dataSourceId;
    private Result result;

    public TFIDFJob(){
        corpusPath = "";
        topK = 0;
    }

    public TFIDFJob(String corpusPath, int topK){
        this.corpusPath = corpusPath;
        this.topK = topK;
    }

    public String getCorpusPath() {
        return corpusPath;
    }

    public void setCorpusPath(String corpusPath) {
        this.corpusPath = corpusPath;
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

    public String getDescription() {
        return "TFIDFJob: "+ this.getJobID();
    }
}
