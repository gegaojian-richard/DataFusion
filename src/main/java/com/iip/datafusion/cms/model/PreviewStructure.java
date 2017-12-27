package com.iip.datafusion.cms.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jingwei on 2017/12/20.
 */
public class PreviewStructure {
    /*
    size: 每条记录中
    items: 预览的记录组成的list,每条记录是一个map，其中key为列名，value为对应列的值
     */
    private int size;
    private int columnNum;
    private List<Map<String,String>> items;

    public String toString(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = "";
            if(items.size()==0){
                return "empty body of table preview!";
            }
            else if(items.size()>=1){
                jsonStr = "{\"size\":\""+size+"\",\"columnNum\":\""+columnNum+"\",\"items\":[" ;
                jsonStr += ""+objectMapper.writeValueAsString(items.get(0));
                for (int i=1;i<items.size();i++) {
                    String item = objectMapper.writeValueAsString(items.get(i));
                    jsonStr += "," +item;
                }
                jsonStr += "]}";
            }
            return jsonStr;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public PreviewStructure(){
        items = new LinkedList<Map<String,String>>();
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Map<String, String>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, String>> items) {
        this.items = items;
    }
}
