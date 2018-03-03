package com.iip.datafusion.nsps.dao;

import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Author Junnor.G
 * @Date 2018/2/3 上午2:26
 */
public class MySqlDAO {
    /*
    默认创建第一列是filepath，第二列是wordName的表
     dataSourceId: 数据库数据源
     tableName: 创建的表名
      */
    public static int createWordsTable(String wordName , JdbcTemplate jdbcTemplate , String dataSourceId , String tableName){
        try{
            DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);
            StringBuilder sql = new StringBuilder("create table if not exists `" + tableName +"` (" +
                    "`filepath` varchar(1000) CHARACTER SET utf8 NOT NULL, `" + wordName +
                    "` varchar(5000) CHARACTER SET utf8, primary key (`filepath`))");
            jdbcTemplate.execute(sql.toString());
            return 0;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return -1;
        }

    }

    /*
    表第一列名：filepath，第二列名：wordName变量
    默认存储的是表filepath列为 words的key ，wordName表示特征、关键词...,为words的value
     */
    public static int insertWordsToTable(String wordName , JdbcTemplate jdbcTemplate ,
                                         String dataSourceKey , String tableName,
                                         Map<String , List<String>> words){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceKey);
        try {
            StringBuilder sql = new StringBuilder("insert into " + tableName + " (`filepath`,`" +
                    wordName + "`)" + " values");
            int i = 0;
            for (String filePath: words.keySet()) {
                if(i>0) sql.append(',');
                String data = "";
                for (String keyword : words.get(filePath)) {
                    data += ";" + keyword;
                }
                if(data.length() > 0) sql.append("('" + filePath + "', '" + data.substring(1) + "')");
                else sql.append("('" + filePath + "', '')"); // 不存在关键词
                i++;
            }
            int changes = jdbcTemplate.update(sql.toString());
            if(changes == 0){
                System.out.println(sql.toString() + "sql add data failed");
            }
            return 0;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return 1;
        }

    }
}
