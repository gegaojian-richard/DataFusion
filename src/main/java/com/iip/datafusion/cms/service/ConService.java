package com.iip.datafusion.cms.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by jingwei on 2017/12/13.
 */
public class ConService {

    public static class conBean{

        @JsonProperty("host")
        private String host;
        @JsonProperty("port")
        private String port;
        @JsonProperty("name")
        private String name;   //数据库名
        @JsonProperty("user")
        private String user;
        @JsonProperty("pwd")
        private String pwd;
        @JsonProperty("type")
        private String type;
        @JsonProperty("nick")
        private String nick;   //数据库昵称，用以在系统中区分连接的数据库

        @JsonCreator
        public conBean(@JsonProperty("host") String host,
                       @JsonProperty("port") String port,
                       @JsonProperty("name") String name,
                       @JsonProperty("user") String user,
                       @JsonProperty("pwd") String pwd,
                       @JsonProperty("type") String type,
                       @JsonProperty("nick") String nick){
            this.host = host;
            this.port = port;
            this.name = name;
            this.user = user;
            this.pwd = pwd;
            this.type = type;
            this.nick = nick;
        }

        @Override
        public String toString(){
            return this.host+":"+this.port+"/"+this.name+"   "+this.user+"+"+this.pwd;
        }

        public String getHost(){
            return this.host;
        }

        public String getPort() {
            return port;
        }

        public String getName(){
            return name;
        }

        public String getUser() {
            return user;
        }

        public String getPwd() {
            return pwd;
        }

        public String getType() {
            return type;
        }

        public String getNick() {
            return nick;
        }
    }


    //TODO 调用util实现
    public static String  creCon(conBean c){

        //pojo -> json
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(c);
            return jsonStr;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String delCon(String nick){
        return " deleting "+ nick + " ... ";
    }

    public static String  desCon(String nick){
        return "description of"+nick;
    }

}
