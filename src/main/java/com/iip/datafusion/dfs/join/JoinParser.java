package com.iip.datafusion.dfs.join;

import com.iip.datafusion.dfs.model.JoinRule;

import java.util.LinkedHashMap;

public class JoinParser {

    public static JoinUnit parse(JoinRule joinRule){
        JoinUnit root = new JoinUnit(joinRule.getTargetDataSourceID(), joinRule.getTargetTableName());

        for (String joinUnit:joinRule.getJoinUnits()
             ) {
            joinUnit.split(".");
        }

        return root;
    }
}
