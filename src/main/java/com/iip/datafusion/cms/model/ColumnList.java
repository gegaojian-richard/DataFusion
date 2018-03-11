package com.iip.datafusion.cms.model;

import com.iip.datafusion.util.dbutil.DataType;

import java.util.List;

public class ColumnList {
    private List<ColumnStructure> list;

    public List<ColumnStructure> getList() {
        return list;
    }

    public void setList(List<ColumnStructure> list) {
        this.list = list;
    }

    public String toString(){
        String res = "";
        for(ColumnStructure structure:list){
            res+= structure.getColumnName();
        }
        return res;
    }

    public void pretreatment() {
        for(ColumnStructure item:this.list){
//            item.setColumnType("INT");
            item.setColumnName("`"+item.getColumnName()+"`");
        }
    }
}
