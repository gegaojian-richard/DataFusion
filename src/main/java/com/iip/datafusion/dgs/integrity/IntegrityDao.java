package com.iip.datafusion.dgs.integrity;


import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IntegrityDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /*
    public ArrayList<String> getTableColumnList(String dataSourceId, String tableName){

        ArrayList<String> result = new ArrayList<>();
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("SELECT * FROM %s",tableName);

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
        for(int i=1;i<=sqlRsmd.getColumnCount();i++){
            result.add(sqlRsmd.getColumnName(i));
            //System.out.println(sqlRsmd.getColumnTypeName(i));
        }

        return result;


    }

    public SqlRowSet doSelect(String dataSourceId,String tableName, String whereClause){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("SELECT * FROM %s where %s",tableName,whereClause);

        return jdbcTemplate.queryForRowSet(sql);

    }

    public boolean doUpdate(String dataSourceId,String tableName,String updateClause,String whereClause){

        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("update %s SET %s where %s",tableName,updateClause,whereClause);
        //System.out.println(sql);
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean doReplace(String dataSourceId, String tableName,String columnClause,String valueClause){
        DataSourceRouterManager.setCurrentDataSourceKey(dataSourceId);

        String sql = String.format("REPLACE INTO %s  %s VALUES %s",tableName,columnClause,valueClause);
        //System.out.println(sql);
        try {
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /*
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
    */
}
