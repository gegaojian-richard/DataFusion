package com.iip.datafusion.cms.model;

import java.util.ArrayList;

/**
 * Created by jingwei on 2017/12/19.
 */
public class TableStructure {
    private ArrayList<ColumnStructure> colmnuStructures;
//    private String[] dataPreview;
    private String tablename;


    public TableStructure(String tablename){
        colmnuStructures = new ArrayList<>();
        this.tablename = tablename;
    }

    public void addColumn(ColumnStructure c){
        this.colmnuStructures.add(c);
    }

    public String getTablename() {
        return tablename;
    }

    public ArrayList<ColumnStructure> getColmnuStructures() {
        return colmnuStructures;
    }
}

