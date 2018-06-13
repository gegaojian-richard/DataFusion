package com.iip.datafusion.cms.dao;

import com.iip.datafusion.cms.model.*;
import com.iip.datafusion.eems.model.Entity;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.dbutil.DataType;
import com.iip.datafusion.util.jsonutil.JsonParse;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.sql.RowSetMetaData;
import javax.swing.text.TabSet;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by jingwei on 2017/12/13.
 */
@Service
public class CmsDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;

    //得到id的数据库的表结构及列结构
    public Map<String,Object> getDatabaseStructure(String id) {
        Map<String,Object> map= new HashMap<>();
        DataBaseStructure dataBaseStructure = new DataBaseStructure();
        Connection connection = null;
        try {
            dataSourceRouterManager.setCurrentDataSourceKey(id);
            connection = jdbcTemplate.getDataSource().getConnection();

            //得到数据库的表名
            PreparedStatement statement = connection.prepareStatement("show tables from "+connection.getCatalog());
            ResultSet resultSet = statement.executeQuery();
            connection.close();

            //对于每一个表，用一次查询得到所有的列名及列类型
            while (resultSet.next()){
                String tmpTable = resultSet.getString(1);
                connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement statement1 = connection.prepareStatement("select * from "+tmpTable+" limit 0");
                ResultSet column = statement1.executeQuery();
                ResultSetMetaData rowSetMetaData = column.getMetaData();
                connection.close();

                TableStructure table = new TableStructure(tmpTable);
                for(int i=1;i<=rowSetMetaData.getColumnCount();i++){
                    ColumnStructure columnStructure = new ColumnStructure();
                    columnStructure.setColumnName(rowSetMetaData.getColumnName(i));
                    columnStructure.setColumnType(rowSetMetaData.getColumnTypeName(i));
                    table.addColumn(columnStructure);
                }
                dataBaseStructure.addTable(table);
            }
            map.put("success",dataBaseStructure);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("msg",e.getMessage());
        }finally {
            //防止多次在Connection.close()之前就报错跳出，导致连接池overflow
            try {
                connection.close();
            }
            catch (Exception e){}
            return map;
        }
    }


    public PreviewStructure previewCon(String id, String table, String num) {
        PreviewStructure previewStructure = new PreviewStructure();
//            List<DataSourceProperties> list = new ArrayList<>();
//            String id = "";
//            for(DataSourceProperties item:list){
//                if(item.getDisplayName().equals(display))
//                    id = item.getId();
//            }
//        try {
            dataSourceRouterManager.setCurrentDataSourceKey(id);
            List rows = jdbcTemplate.queryForList("select * from " + table + " limit " + num);
            previewStructure.setSize(rows.size());
            Iterator iterator = rows.iterator();
            while (iterator.hasNext()) {
                Map map = (Map) iterator.next();
                previewStructure.getItems().add(map);
                previewStructure.setColumnNum(map.size());
            }
//            jdbcTemplate.getDataSource().getConnection().close();
//        }catch (SQLException e){
//            return null;
//        }
        return previewStructure;
    }

    public Result createTable(Entity entity) {
        dataSourceRouterManager.setCurrentDataSourceKey(entity.getDbPosition());
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE `");
        stringBuilder.append(entity.getTableName());
        stringBuilder.append("`(");

        //获取entity的property中的列信息
        try {
            ColumnList list = JsonParse.getMapper().readValue("{\"list\":"+entity.getProperties()+"}", ColumnList.class);

            //对columnList中的每个column的类型等进行预处理
            list.pretreatment();

            for(ColumnStructure item:list.getList()){
                stringBuilder.append(item.getColumnName()+" "+DataType.values()[Integer.parseInt(item.getColumnType())].toString()+",");
                stringBuilder.append(item.getIsPrime()==1 ? "PRIMARY KEY ("+item.getColumnName()+")," : "");
            }

            stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
            stringBuilder.append(") DEFAULT CHARSET=utf8;");
            jdbcTemplate.execute(stringBuilder.toString());

//            //检查目标表是否成功建立
//            DataBaseStructure structure =(DataBaseStructure) this.getDatabaseStructure(entity.getDbPosition()).get("success");

            return new Result(1,null,"成功创建实体/事件库");
        }catch (IOException e){
            e.printStackTrace();
            return new Result(0,"列信息解析失败@createTable",null);
        }catch (Exception e1){
            return new Result(0,"SQL语句执行错误@createTable",null);
        }
    }

    public void deleteTable(String dbID, String tableName) {
        dataSourceRouterManager.setCurrentDataSourceKey(dbID);
        jdbcTemplate.execute("DROP TABLE IF EXISTS "+tableName);
    }
}
