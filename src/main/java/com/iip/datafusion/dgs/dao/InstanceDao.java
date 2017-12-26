package com.iip.datafusion.dgs.dao;

import com.iip.datafusion.dgs.model.InstanceEntity;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InstanceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getEmptyEntity(String tableName,String columnName){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        //return jdbcTemplate.update("")>0;
        //String row = jdbcTemplate.queryForObject("SELECT * FROM test where name is null", String.class);
        String sql = String.format("SELECT * FROM %s where %s is null",tableName,columnName);
        System.out.println(sql);
        try{
            List<InstanceEntity> instanceEntity = (List<InstanceEntity>) jdbcTemplate.query(sql,
                    new RowMapper() {

                        @Override
                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            InstanceEntity user = new InstanceEntity();
                            System.out.println(rsmd.getColumnCount());
                            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                                String name = rsmd.getColumnName(i);
                                System.out.println(name);
                                user.getKv().put(name, rs.getString(name));
                            }
                            return user;
                        }
                    }
            );
            return "hello,jdbc\n" + instanceEntity.toString();
        }catch (Exception e){
            return "hello,jdbc\n" + "there are something wrong in your input tablename or columnname";
        }
    }
}
