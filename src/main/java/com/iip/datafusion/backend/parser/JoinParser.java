package com.iip.datafusion.backend.parser;

import com.iip.datafusion.dfs.join.JoinJob;
import com.iip.datafusion.dfs.join.JoinUnit;
import com.iip.datafusion.dfs.model.JoinConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据整合工作描述解析器
 * Created by GeGaojian on 2018/01/18.
 */
public class JoinParser implements Parser{
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
