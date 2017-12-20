package com.iip.datafusion.eems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EntityService{
    @Autowired
    private EntityDao entityDAO;
    public List<Entity> getAllEntity(String entityClass){
        return null;
    }
    public void insertEntity(Entity entity){entityDAO.insertEntity(entity);}
    public void updateEntity(Entity entity){
         entityDAO.updateEntity(entity);
    }
    public void deleteEntity(Entity entity){
        entityDAO.deleteEntity(entity);
    }
}