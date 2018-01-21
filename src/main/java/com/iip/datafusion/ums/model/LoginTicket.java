package com.iip.datafusion.ums.model;

import java.util.Date;

/**
 * Created by jingwei on 2017/12/25.
 */
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private String ticket;
    private int status;  //0表示有效，1表示无效

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
