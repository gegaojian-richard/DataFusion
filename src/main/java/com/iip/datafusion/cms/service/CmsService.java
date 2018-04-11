package com.iip.datafusion.cms.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iip.datafusion.cms.dao.CmsDao;
import com.iip.datafusion.cms.model.*;
import com.iip.datafusion.ums.dao.LoginTicketDao;
import com.iip.datafusion.ums.dao.UserDao;
import com.iip.datafusion.ums.model.LoginTicket;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;


import javax.sql.RowSet;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by jingwei on 2017/12/13.
 */
@org.springframework.stereotype.Service
public class CmsService {
    @Autowired
    CmsDao cmsDao;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //添加数据源
    public Result  creCon(DataSourceProperties c){
        //判断数据源类型 加载驱动，拼装成不同url
        switch (c.getDriverClassName()){
            case "mysql":{
                c.setDriverClassName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://"+c.getUrl();
                c.setUrl(url);
                break;
            }
            case "oracle":{
                c.setDriverClassName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@" + c.getUrl().split("/")[0]+":"+c.getUrl().split("/")[1];
                c.setUrl(url);
                break;
            }
        }
        return dataSourceRouterManager.addDataSource(c);
    }

    //删除数据源
    public Result delCon(String id){

        dataSourceRouterManager.deleteConnection(id);
        return new Result(1,null,null);
    }

    //得到数据库的表结构，及每列的结构
    public Result  desCon(String id){
        Map<String,Object> map = cmsDao.getDatabaseStructure(id);
        //如果含有错误信息直接返回错误
        if(map.containsKey("msg")){
            return new Result(0,map.get("msg").toString(),null);
        }
        //否则获取database结构，并转化成json格式
        DataBaseStructure dataBaseStructure =(DataBaseStructure) map.get("success");
        try {
            String json = JsonParse.getMapper().writeValueAsString(dataBaseStructure);
            return new Result(1, null, json);
        }
        catch (JsonProcessingException e){
            return new Result(0,"json解析失败",null);
        }
    }

    //得到当前用户拥有的数据源
    public Result getCurrentConnection(){
        StringBuilder jsonStr = new StringBuilder();
        List<DataSourceProperties > properties = dataSourceRouterManager.getDataSourceProperties();
        if(properties.isEmpty())
            return new Result(1,null,"空");

        List<DBTable> dbTableList = new ArrayList<>();
        for(DataSourceProperties item:properties){
            if(item.getId().equals("primary")) continue; //隐藏主数据库
            DBTable dbTable = new DBTable();
            dbTable.setId(item.getId());
            dbTable.setDisplayName(item.getDisplayName());
            Connection connection = null;
            List<TableStructure> list = new ArrayList<>();
            try {
                dataSourceRouterManager.setCurrentDataSourceKey(item.getId());
                connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement("show tables from " + connection.getCatalog());
                ResultSet resultSet = statement.executeQuery();
                connection.close();
                while (resultSet.next()){
                    TableStructure tableStructure = new TableStructure(resultSet.getString(1));
                    list.add(tableStructure);
                }
            }catch (SQLException e ){
                e.printStackTrace();
                return new Result(0,"sql执行错误",null);
            }finally {
                try{
                    connection.close();
                }catch (Exception e){}
            }
            dbTable.setTables(list);
            dbTableList.add(dbTable);
        }

        try {
            jsonStr.append(JsonParse.getMapper().writeValueAsString(dbTableList));
        }catch (JsonProcessingException e){
            return new Result(0,"json转化失败",null);
        }

        return new Result(1,null,jsonStr.toString());
    }

    //得到某个id的数据库的某张表的预览信息
    public Result previewCon(String id, String table,String num) {
        PreviewStructure previewStructure = cmsDao.previewCon(id,table,num);
        if(previewStructure == null){
            return new Result(0,"previewStructure is null",null);
        }
        return new Result(1,null,previewStructure.toString());
    }

}
