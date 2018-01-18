package com.iip.datafusion.eems.dao;

import com.iip.datafusion.eems.model.UserEntity;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by tsy on 2017/12/16.
 */
@Repository
public class UserEntityDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public boolean insertUserEntityDao(UserEntity userentity){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        int i=jdbcTemplate.update("INSERT INTO userentity(userId,entityId) values (?,?)",
                new Object[]{userentity.getUserId(),userentity.getEntityId()});
        if (i>0){
            flag=true;
        }
        return flag;
    }
    public boolean deleteUserEntityByUser(int del_userId){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        int i=jdbcTemplate.update("delete from userentity where userId=?",new Object[]{del_userId});
        if(i>0){
            flag=true;
        }
        return flag;
    }
    public boolean deleteUserEntityByEntity(int del_entityId){
        DataSourceRouterManager.setCurrentDataSourceKey("primary");
        boolean flag=false;
        int i=jdbcTemplate.update("delete from userentity where userId=?",new Object[]{del_entityId});
        if(i>=0){
            flag=true;
        }
        return flag;

    }
}
