package com.iip.datafusion.backend.job.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.jsonutil.Result;

/**
 * @Author Junnor.G
 * @Date 2018/2/1 下午4:18
 */
public class NameRecognitionJob extends JobBase {
    private String corpusPath;
    private String tableName;
    private Result result;
    private String dataSourceId;

    public NameRecognitionJob(){
        corpusPath = "";
    }

    public NameRecognitionJob(String corpusPath){
        this.corpusPath = corpusPath;
    }
    @JsonIgnore
    public String getCorpusPath() {
        return corpusPath;
    }

    public void setCorpusPath(String corpusPath) {
        this.corpusPath = corpusPath;
    }
    @JsonIgnore
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

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
