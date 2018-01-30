package com.iip.datafusion.dgs.model.accuracy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RangeParam extends Param {

    private String whereClause;

    public RangeParam(){}

    public RangeParam(int type,String whereClause){
        super(type);
        this.whereClause = whereClause;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }
}
