package com.iip.datafusion.cms.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.TableStructure;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by jingwei on 2017/12/18.
 */
public class test {
    public static void main(String [] args) {

//        String url = "jdbc:mysql://localhost:3306/qq";
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

        DataBaseStructure structure = new DataBaseStructure();
        for(int i=0;i<9;i++) {
            TableStructure tableStructure = new TableStructure("t"+i);
            for(int j=0;j<9;j++) {
                tableStructure.addColumn(new ColumnStructure("c"+j));
            }
            structure.addTable(tableStructure);
        }

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(structure);
            System.out.println(json);
        }catch (Exception e){
            e.printStackTrace();
        }
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

            ObjectMapper mapper = new ObjectMapper();

            String jsonObject = mapper.writeValueAsString(dataBaseStructure);


            return jsonObject;

        }
        catch (Exception e){
            return "false";
        }
    }


}
