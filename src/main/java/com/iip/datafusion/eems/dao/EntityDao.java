package com.iip.datafusion.eems.dao;

import com.iip.datafusion.eems.model.Entity;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.InterruptibleBatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
//import javax.annotation.Resource;

@Repository
public class EntityDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insertEntity(Entity entity){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        return jdbcTemplate.update("INSERT INTO entity(displayName,tableName,dbPosition,entityType) values (?,?,?,?)",
                new Object[]{entity.getDisplayName(),entity.getTableName(),entity.getDbPosition(),entity.getEntityType()})>0;
    }
    public boolean deleteEntity(int del_id){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        int i=jdbcTemplate.update("delete from entity where id=?",new Object[]{del_id});
        if(i>0){
            flag=true;
        }
        return flag;
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
                "select id,displayName.tableName,dbPosition,entityType,properties from entity where id=? ",
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
        List<Entity> list=jdbcTemplate.query(sbstr.toString(),new Object[]{userId},new BeanPropertyRowMapper(Entity.class));
        return list;
    }

}