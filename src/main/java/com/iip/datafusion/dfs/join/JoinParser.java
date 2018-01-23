package com.iip.datafusion.dfs.join;

import com.iip.datafusion.dfs.model.JoinConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeGaojian on 2017/12/22.
 */

public class JoinParser {

    /**
     * 解析JoinRule得到JoinJob
     * @param joinConfiguration
     * @return JoinJob对象
     */
    public static JoinJob parse(JoinConfiguration joinConfiguration){
        // 根据创建JoinJob
        JoinJob job = new JoinJob(joinConfiguration.getTargetTableName(), joinConfiguration.getTargetDataSourceID());

        // 创建JoinUnits
        List<JoinUnit> joinUnits = new ArrayList<>();
        for (String joinUnit : joinConfiguration.getJoinUnits()
             ) {
            String[] temp = joinUnit.split(".");
            joinUnits.add(new JoinUnit(temp[0], temp[1]));
        }
        //

        return job;
    }
}
