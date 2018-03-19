package com.iip.datafusion.backend.job.test;

import com.iip.datafusion.backend.job.Job;
import com.iip.datafusion.backend.job.JobBase;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午2:25
 */
public class TestJob extends JobBase implements Job{
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getDescription(){
        return "this is TestJob";
    }
}
