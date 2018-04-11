package com.iip.datafusion.backend.job;

import com.iip.datafusion.util.userutil.UserManager;
import javafx.print.PrinterJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class JobBase implements Job{

    Integer JobID=0;
    Integer UserID=0;
    JobStatusType status;
    JobType jobType;  //在各个manager中的commitJob中设置


    public void setUserID(Integer userID) {
        UserID = userID;
    }

    @Override
    public Integer getUserID() {
        return UserID;
    }

    @Override
    public Integer getJobID() {
        return JobID;
    }

    public void setJobID(Integer id){
        JobID = id;
    }

    @Override
    public JobType getJobType() {
        return jobType;
    }

    @Override
    public void setStatus(JobStatusType status) {
        this.status = status;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    //需要在各个job子类中重写
    @Override
    public String getDescription() {
        return null;
    }

    public JobStatusType getStatus() {
        return status;
    }

    public String toJson(){
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"jobID\" : \"").append(JobID).append("\"");
        sb.append(", \"userID\" : \"").append(UserID).append("\"");
        sb.append(", \"status\" : \"").append(status).append("\"");
        sb.append(", \"jobType\" : \"").append(jobType).append("\"");
        sb.append("}");

        return sb.toString();
    }
}
