package com.iip.datafusion.backend.job.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;
import com.iip.datafusion.util.jsonutil.Result;

import java.util.ResourceBundle;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:52
 */
public class TopicModelJob extends JobBase implements Job{
    String corpusPath;
    int topicNum;
    String tableName;
    String dataSourceId;
    Result result;
    @JsonIgnore
    public String getCorpusPath() {
        return corpusPath;
    }

    public void setCorpusPath(String corpusPath) {
        this.corpusPath = corpusPath;
    }
    @JsonIgnore
    public int getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(int topicNum) {
        this.topicNum = topicNum;
    }
    @JsonIgnore
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    @JsonIgnore
    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    @JsonIgnore
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getDescription() {
        return ""+dataSourceId+"."+tableName+": "+corpusPath;
    }
}
