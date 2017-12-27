package com.iip.datafusion.util.jsonutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jingwei on 2017/12/20.
 */
public class Result {
    /*
    restful 接口返回消息类型。
    正确执行：
    status: 1
    msg: null
    data: 返回的json数据

    错误执行：
    status: 0
    msg: 错误信息
    data: null
     */
    private int status;
    private String msg;
    private String data;

    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString ;
        try {
            jsonString = mapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e){
            jsonString = "{\n\"status\":\""+status+"\",\n\"msg\":\""+msg+"\",\n\"data\":\""+data+"\"";
        }
        return jsonString;
    }

    public Result(){
        super();
    }

    public Result(int status, String msg, String data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
