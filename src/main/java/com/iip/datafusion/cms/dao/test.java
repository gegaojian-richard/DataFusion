package com.iip.datafusion.cms.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.TableStructure;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by jingwei on 2017/12/18.
 */
public class test {
    public static void main(String [] args) {

//        String url = "jdbc:mysql://localhost:3306/test";
//        String type = "com.mysql.jdbc.Driver";
//        String user = "root";
//        String pwd = "123456";
//        Connection connection = null;
//
//        try {
//            Class.forName(type);
//            connection = DriverManager.getConnection(url, user, pwd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(desCon(connection));

        Result result = new Result(1,null,"{\"name\":\"jingwei\"}");
        System.out.println(result.toString());
    }

    public static String  desCon(Connection connection){
        DataBaseStructure dataBaseStructure = new DataBaseStructure();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
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

            JSONObject jsonObject = JSONObject.fromObject(dataBaseStructure);

            return jsonObject.toString();

        }
        catch (Exception e){
            return "false";
        }
    }


}
