package com.iip.datafusion.eems.service;

import com.iip.datafusion.eems.dao.EntityDao;
import com.iip.datafusion.eems.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EntityService{
    @Autowired
    private EntityDao entityDAO;
    public int insertEntity(Entity entity){
        return entityDAO.insertEntity(entity);
    }
    public boolean updateEntity(int update_id,String property,String newValue){
         return entityDAO.updateEntity(update_id,property,newValue);
    }
    public boolean deleteEntity(int  del_id){
        return entityDAO.deleteEntity(del_id);
    }
    public List<Entity> getEntityByUserId(int userid){return entityDAO.getEntityByUserId(userid);}
}