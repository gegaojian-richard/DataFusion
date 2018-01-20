package com.iip.datafusion.cms.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iip.datafusion.cms.dao.CmsDao;
import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.PreviewStructure;
import com.iip.datafusion.cms.model.TableStructure;
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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        //为了适配传递id为displayName的情况
        if(!id.equals("primary") && (id.length()<4 || !id.substring(0,3).equals("db_"))) {
            List<DataSourceProperties> list = dataSourceRouterManager.getDataSourceProperties();
            for (DataSourceProperties item : list) {
                if (item.getDisplayName().equals(id))
                    id = item.getId();
            }
        }

        dataSourceRouterManager.deleteConnection(id);
        return new Result(1,null,null);
    }

    //得到数据库的表结构，及每列的结构
    public Result  desCon(String id){
        //为了适配传递id为display
        if(!id.equals("primary") && (id.length()<4 || !id.substring(0,3).equals("db_"))) {
            List<DataSourceProperties> list = dataSourceRouterManager.getDataSourceProperties();
            for (DataSourceProperties item : list) {
                if (item.getDisplayName().equals(id))
                    id = item.getId();
            }
        }


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
        String jsonStr = "{\"databases\":[";
        List<String > displayNames = dataSourceRouterManager.getDataSourceDisplayNames();
        if(!displayNames.isEmpty())
            jsonStr += "{\"name\":\"" + displayNames.get(0) + "\"}";
        for(int i=1;i< displayNames.size();i++){
            jsonStr += ",{\"name\":\"" +displayNames.get(i)+"\"}";
        }
        jsonStr += "]}";
        return new Result(1,null,jsonStr);
    }

    //得到某个id的数据库的某张表的预览信息
    public Result previewCon(String id, String table,String num) {
        //为了适配传递id为display
        if(!id.equals("primary") && (id.length()<4 || !id.substring(0,3).equals("db_"))) {
            List<DataSourceProperties> list = dataSourceRouterManager.getDataSourceProperties();
            for (DataSourceProperties item : list) {
                if (item.getDisplayName().equals(id))
                    id = item.getId();
            }
        }

        PreviewStructure previewStructure = cmsDao.previewCon(id,table,num);
        if(previewStructure == null){
            return new Result(0,"previewStructure is null",null);
        }
        return new Result(1,null,previewStructure.toString());
    }

}
