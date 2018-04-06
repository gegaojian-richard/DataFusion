package com.iip.datafusion.backend.job.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    public String getCorpusPath() {
        return corpusPath;
    }

    public void setCorpusPath(String corpusPath) {
        this.corpusPath = corpusPath;
    }
    @JsonIgnore
    public int getTopK(){
        return topK;
    }

    public void setTopK(int topK){
        this.topK = topK;
    }
    @JsonIgnore
    public String getTableName(){return tableName;}

    public void setTableName(String tableName) {this.tableName = tableName;}
    @JsonIgnore
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    @JsonIgnore
    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDescription() {
        return ""+dataSourceId+"."+tableName+": "+corpusPath;
    }
}
