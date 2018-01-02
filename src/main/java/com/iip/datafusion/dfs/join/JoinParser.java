package com.iip.datafusion.dfs.join;

import com.iip.datafusion.dfs.model.JoinRule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by GeGaojian on 2017/12/22.
 */

public class JoinParser {

    /**
     * 解析JoinRule得到JoinJob
     * @param joinRule
     * @return JoinJob对象
     */
    public static JoinJob parse(JoinRule joinRule){
        // 根据创建JoinJob
        JoinJob job = new JoinJob(joinRule.getTargetTableName(), joinRule.getTargetDataSourceID());

        // 创建JoinUnits
        List<JoinUnit> joinUnits = new ArrayList<>();
        for (String joinUnit : joinRule.getJoinUnits()
             ) {
            String[] temp = joinUnit.split(".");
            joinUnits.add(new JoinUnit(temp[0], temp[1]));
        }
        //

        return job;
    }
}
