package com.iip.datafusion.cms.dao;

import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.PreviewStructure;
import com.iip.datafusion.cms.model.TableStructure;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
        try {
            DataBaseStructure dataBaseStructure = new DataBaseStructure();

            DataSourceRouterManager.setCurrentDataSourceKey(id);
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet table = metaData.getTables(null,"%",
                    "%",new String[]{"TABLE"});

            while(table.next()){
                String tmp = table.getString("TABLE_NAME");
                TableStructure tableStructure = new TableStructure(tmp);
                ResultSet column = metaData.getColumns(null,"%",tmp,"%");
                while (column.next()) {
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

            return dataBaseStructure;
        }
        catch (SQLException e){
            return null;
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
        DataSourceRouterManager.setCurrentDataSourceKey(id);
        List rows= jdbcTemplate.queryForList("select * from "+table+" limit "+num);
        previewStructure.setSize(rows.size());
        Iterator iterator = rows.iterator();
        while(iterator.hasNext()){
            Map map=(Map) iterator.next();
            previewStructure.getItems().add(map);
            previewStructure.setColumnNum(map.size());
        }
        return previewStructure;
    }
}
