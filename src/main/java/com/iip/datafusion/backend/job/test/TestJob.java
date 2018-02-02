package com.iip.datafusion.backend.job.test;

import com.iip.datafusion.backend.job.Job;

/**
 * @Author Junnor.G
 * @Date 2018/1/31 下午2:25
 */
public class TestJob implements Job{
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
