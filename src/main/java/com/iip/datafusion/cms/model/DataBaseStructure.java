package com.iip.datafusion.cms.model;

import org.springframework.jdbc.support.DatabaseStartupValidator;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/18.
 */
public class DataBaseStructure {


//    private String dbname;
    private ArrayList<TableStructure> tableStructures;

    public DataBaseStructure(){
        tableStructures = new ArrayList<>();
    }

    public void addTable(TableStructure t){
        tableStructures.add(t);
    }

    public ArrayList<TableStructure> getTableStructures() {
        return tableStructures;
    }

    //    public TableStructure getTable(String tablename){
//        return tableStructures.get(tablename);
//    }

//    public String getDbname() {
//        return dbname;
//    }
//
//    public void setDbname(String dbname) {
//        this.dbname = dbname;
//    }



}
