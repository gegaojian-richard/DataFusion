package com.iip.datafusion.cms.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.iip.datafusion.cms.model.ColumnStructure;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.model.TableStructure;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.dbutil.DataType;
import com.iip.datafusion.util.jsonutil.Result;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Created by jingwei on 2017/12/18.
 */
public class test {
    public static void main(String [] args) {
        Scanner input=new Scanner(System.in);
        String flag = "";
        while (!flag.equals("%")) {
            System.out.println("在primary中删除表，输入表名：");
            String tableName = input.next();
            CmsDao c = new CmsDao();
            c.deleteTable("primary", tableName);
            System.out.println("表格已删除请检查，输入%结束：");
            flag = input.next();
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
