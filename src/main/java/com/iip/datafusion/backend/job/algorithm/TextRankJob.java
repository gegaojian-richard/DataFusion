package com.iip.datafusion.backend.job.algorithm;

import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.util.jsonutil.Result;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午3:16
 */
public class TextRankJob implements Job{
    private String path;
    private int topK;
    private String tableName;
    private Result result;

    public TextRankJob(){
        path = "";
        topK = 0;
    }

    public TextRankJob(String path, int topK){
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
}
