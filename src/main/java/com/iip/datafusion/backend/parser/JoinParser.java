package com.iip.datafusion.backend.parser;

import com.iip.datafusion.backend.job.join.JoinJob;
import com.iip.datafusion.backend.job.join.JoinUnit;
import com.iip.datafusion.dfs.model.FieldMapEntry;
import com.iip.datafusion.dfs.model.JoinConfiguration;
import com.iip.datafusion.dfs.model.Relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, JoinUnit> joinUnits = new HashMap<>();
        for (String joinUnit : joinConfiguration.getJoinUnits()
                ) {
            // todo: split函数的参数是正则表达式，而.是正则表达式中的关键字，所以如果要以.作为分隔符需要使用转义\\.
            String[] temp = joinUnit.split(":");
            joinUnits.put(joinUnit, new JoinUnit(temp[0], temp[1]));
        }
        // 设置主要JoinUnit
        job.setPrimaryJoinUnitKey(joinConfiguration.getJoinUnits().get(0));

        String[] temp;
        // 遍历关系组织joinUnits关系
        for (Relation relation : joinConfiguration.getRelations()
             ) {
            temp = relation.getLeft().split(":");
            String parentJUId = temp[0] + ":" + temp[1];
            String parentJoinField = temp[2];
            temp = relation.getRight().split(":");
            String childJUId = temp[0] + ":" + temp[1];
            String joinField = temp[2];
            joinUnits.get(parentJUId).addJoinUnit(joinUnits.get(childJUId));
            joinUnits.get(parentJUId).addSelectField(parentJoinField);
            joinUnits.get(childJUId).setParentJoinUnit(joinUnits.get(parentJUId));
            joinUnits.get(childJUId).addSelectField(joinField);
            joinUnits.get(childJUId).setParentJoinField(parentJoinField);
            joinUnits.get(childJUId).setJoinField(joinField);
        }


        // 遍历字段映射表
        for (FieldMapEntry fieldMapEntry : joinConfiguration.getFieldMapEntries()
             ) {
            temp = fieldMapEntry.getSourceFieldName().split(":");
            String jUId = temp[0] + ":" + temp[1];
            String selectField = temp[2];
            joinUnits.get(jUId).addSelectField(selectField);
            joinUnits.get(jUId).addFieldMap(fieldMapEntry.getTargetFieldName(), selectField);
            job.addFieldMap(fieldMapEntry.getTargetFieldName(), fieldMapEntry.getSourceFieldName());
        }

        job.setJoinUnits(joinUnits);

        return job;
    }
}
