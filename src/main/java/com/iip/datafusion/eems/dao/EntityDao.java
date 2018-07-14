package com.iip.datafusion.eems.dao;

import com.iip.datafusion.eems.model.Entity;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
//import javax.annotation.Resource;

@Repository
public class EntityDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertEntity(Entity entity){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update((new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO entity(displayName,tableName,dbPosition,entityType,properties) values (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getDisplayName());
                ps.setString(2, entity.getTableName());
                ps.setString(3, entity.getDbPosition());
                ps.setInt(4, entity.getEntityType());
                ps.setString(5, entity.getProperties());
                return ps;
            }
        }),keyHolder);
        return keyHolder.getKey().intValue();
    }

    public boolean deleteEntity(int del_id){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        int i=jdbcTemplate.update("delete from entity where id=?",new Object[]{del_id});
        return i>0;
    }
    public boolean updateEntity(int update_id,String property,String newValue){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        int i=jdbcTemplate.update("update entity set "+property+" = ? "+" where id = ?",
                new Object[]{newValue,update_id});
        if(i>0){
            flag=true;
        }
        return flag;

    }

    public Entity  getEntityById(Integer id){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        Entity result=null;
        result=(Entity)jdbcTemplate.queryForObject(
                "select id,displayName,tableName,dbPosition,entityType,properties from entity where id=? ",
                new Object[]{id},new RowMapper(){
                    @Override
                    public Object mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                        Entity entity=new Entity();
                        entity.setId(rs.getInt("id"));
                        entity.setDisplayName((rs.getString("displayName")));
                        entity.setTableName(rs.getString("tableName"));
                        entity.setDbPosition(rs.getString("dbPosition"));
                        entity.setEntityType(rs.getInt("entityType"));
                        entity.setProperties(rs.getString("properties"));
                        return entity;
                    }
                }
        );
        return result;
    }
    public List<Entity> getEntityByUserId(Integer userId){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        StringBuilder sbstr=new StringBuilder("SELECT e.* from entity e left join userentity u on (e.id=u.entityId) where u.userId=?");
        List<Entity> list=jdbcTemplate.query(sbstr.toString(),
                new Object[]{userId},new BeanPropertyRowMapper(Entity.class));
        return list;
    }

}