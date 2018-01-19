package com.iip.datafusion.cms.dao;

import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.PreviewStructure;
import com.iip.datafusion.cms.model.TableStructure;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.sql.RowSetMetaData;
import javax.swing.text.TabSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/13.
 */
@Service
public class CmsDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;


    public Result addDatabase(DataSourceProperties c) {
        dataSourceRouterManager.addDataSource(c);
        return new Result(1,null,null);
    }

    public DataBaseStructure getDatabaseStructure(String id) {
        DataBaseStructure dataBaseStructure = new DataBaseStructure();
        Connection connection = null;
        try {
            dataSourceRouterManager.setCurrentDataSourceKey(id);
            connection = jdbcTemplate.getDataSource().getConnection();
//            DatabaseMetaData metaData = connection.getMetaData();
//            ResultSet table = metaData.getTables(null,null,
//                    "%",new String[]{"TABLE"});
//            String dataBaseName = connection.getCatalog();
            PreparedStatement statement = connection.prepareStatement("show tables from "+connection.getCatalog());
            ResultSet resultSet = statement.executeQuery();
            connection.close();
//            SqlRowSet resultSet = jdbcTemplate.queryForRowSet("show tables from " +dataBaseName);
//            SqlRowSet resultSet = jdbcTemplate.queryForRowSet("show tables");

//            while(table.next()){
//                String tmp = table.getString("TABLE_NAME");
//                TableStructure tableStructure = new TableStructure(tmp);
//                ResultSet column = metaData.getColumns(null,"%",tmp,"%");
//                while (column.next()) {
//                    ColumnStructure columnStructure = new ColumnStructure();
//                    columnStructure.setColumnName(column.getString("COLUMN_NAME"));
//                    columnStructure.setColumnType(column.getString("TYPE_NAME"));
//                    columnStructure.setDataSize(column.getInt("COLUMN_SIZE"));
//                    columnStructure.setDigits(column.getInt("DECIMAL_DIGITS"));
//                    columnStructure.setNullable(column.getInt("NULLABLE"));
//                    tableStructure.addColumn(columnStructure);
//                }
//                dataBaseStructure.addTable(tableStructure);
//            }

            while (resultSet.next()){
                String tmpTable = resultSet.getString(1);
//                SqlRowSet column = jdbcTemplate.queryForRowSet("select * from "+tmpTable+" limit 1");
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
            return dataBaseStructure;
        }
        catch (NullPointerException e){
//            return getDatabaseStructure(id);
            e.printStackTrace();
            return null;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            }
            catch (Exception e){}
        }
    }

    public Result getCurrentConnection() {
        String jsonStr = "{\"databases\":[";
        List<String > displayNames = dataSourceRouterManager.getDataSourceDisplayNames();
        if(!displayNames.isEmpty())
            jsonStr += "{\"name\":\"" + displayNames.get(0) + "\"}";
        for(int i=1;i< displayNames.size();i++){
            jsonStr += ",{\"name\":\"" +displayNames.get(i)+"\"}";
        }
        jsonStr += "]}";
        Result result = new Result(1,null,jsonStr);
        return result;
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
}
