package com.iip.datafusion.backend.job.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.jsonutil.Result;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午3:16
 */
public class TextRankJob extends JobBase implements Job{
    private String corpusPath;
    private int topK;
    private String tableName;
    private Result result;
    private String dataSourceId;

    public TextRankJob(){
        corpusPath = "";
        topK = 0;
    }

    public TextRankJob(String corpusPath, int topK){
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

    @Override
    public String getDescription() {
        return ""+dataSourceId+"."+tableName+": "+corpusPath;
    }
}
