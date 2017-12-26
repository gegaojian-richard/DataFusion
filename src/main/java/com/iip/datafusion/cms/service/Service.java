package com.iip.datafusion.cms.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.TableStructure;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import jdk.management.resource.internal.inst.DatagramSocketRMHooks;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;


import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by jingwei on 2017/12/13.
 */
@org.springframework.stereotype.Service
public class Service {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSourceRouterManager dataSourceRouterManager;

//    public static class conBean{
//
//        @JsonProperty("host")
//        private String host;
//        @JsonProperty("port")
//        private String port;
//        @JsonProperty("name")
//        private String name;   //数据库名
//        @JsonProperty("user")
//        private String user;
//        @JsonProperty("pwd")
//        private String pwd;
//        @JsonProperty("type")
//        private String type;
//        @JsonProperty("nick")
//        private String nick;   //数据库昵称，用以在系统中区分连接的数据库
//
//        @JsonCreator
//        public conBean(@JsonProperty("host") String host,
//                       @JsonProperty("port") String port,
//                       @JsonProperty("name") String name,
//                       @JsonProperty("user") String user,
//                       @JsonProperty("pwd") String pwd,
//                       @JsonProperty("type") String type,
//                       @JsonProperty("nick") String nick){
//            this.host = host;
//            this.port = port;
//            this.name = name;
//            this.user = user;
//            this.pwd = pwd;
//            this.type = type;
//            this.nick = nick;
//        }
//
//        @Override
//        public String toString(){
//            return this.host+":"+this.port+"/"+this.name+"   "+this.user+"+"+this.pwd;
//        }
//
//        public String getHost(){
//            return this.host;
//        }
//
//        public String getPort() {
//            return port;
//        }
//
//        public String getName(){
//            return name;
//        }
//
//        public String getUser() {
//            return user;
//        }
//
//        public String getPwd() {
//            return pwd;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public String getNick() {
//            return nick;
//        }
//    }

    public String  creCon(DataSourceProperties c){

//        //pojo -> json
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String jsonStr = mapper.writeValueAsString(c);
//            DataSourceRouterManager.addDataSource(c);
//            return jsonStr;
//        }
//        catch (IOException e){
//            e.printStackTrace();
//            return null;
//        }
        if(dataSourceRouterManager.containsDataSource(c.getId()))
            return "id existed!";

        dataSourceRouterManager.addDataSource(c);
        return "ok!";
    }

    public String delCon(String id){
        dataSourceRouterManager.dataSourceIds.remove(id);
        // todo: 并没有真正删除datasource
        return " ok! ";
    }

    public String  desCon(String id){
        DataBaseStructure dataBaseStructure = new DataBaseStructure();
        try {
            DataSourceRouterManager.setCurrentDataSourceKey(id);
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet table = metaData.getTables(null,"%",
                    "%",new String[]{"TABLE"});
            while(table.next()){
                String tmp = table.getString("TABLE_NAME");
                TableStructure tableStructure = new TableStructure(tmp);

                ResultSet column = metaData.getColumns(null,"%",tmp,"%");
                while (column.next()){
                    ColumnStructure columnStructure = new ColumnStructure();
                    columnStructure.setColumnName(column.getString("COLUMN_NAME"));
                    columnStructure.setColumnType(column.getString("TYPE_NAME"));
                    columnStructure.setDataSize(column.getInt("COLUMN_SIZE"));
                    columnStructure.setDigits(column.getInt("DECIMAL_DIGITS"));
                    columnStructure.setNullable(column.getInt("NULLABLE"));
                    tableStructure.addColumn(columnStructure);
                }

                dataBaseStructure.addTable(tableStructure);
            }

//            ObjectMapper mapper = new ObjectMapper();
//            String jsonStr = mapper.writeValueAsString(dataBaseStructure);
//            return jsonStr;
            JSONObject jsonObject = JSONObject.fromObject(dataBaseStructure);
            return jsonObject.toString();

        }
        catch (Exception e){
            return "false";
        }
    }

    public String getCurrentConnection(){
//        ArrayList conList = new ArrayList();
//        for(String key:DataSourceRouterManager.dataSourceIds){
//            conList.add(DataSourceRouterManager.dataSourceIds.get(key))
//        }
        String jsonStr = "{\n\"databases\":[";
        if(!dataSourceRouterManager.dataSourceIds.isEmpty())
            jsonStr += "\n{\"name\":\"" + dataSourceRouterManager.dataSourceIds.get(0) + "\"}";
        for(int i=1;i< dataSourceRouterManager.dataSourceIds.size();i++){
            jsonStr += ",\n{\"name\":\"" +dataSourceRouterManager.dataSourceIds.get(i)+"\"}";
        }
        jsonStr += "\n]\n}";
        return jsonStr;
    }

}
