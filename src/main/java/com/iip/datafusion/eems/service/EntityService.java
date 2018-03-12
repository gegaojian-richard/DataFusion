package com.iip.datafusion.eems.service;

import com.iip.datafusion.cms.dao.CmsDao;
import com.iip.datafusion.cms.model.DataBaseStructure;
import com.iip.datafusion.cms.service.CmsService;
import com.iip.datafusion.eems.dao.EntityDao;
import com.iip.datafusion.eems.dao.UserEntityDao;
import com.iip.datafusion.eems.model.Entity;
import com.iip.datafusion.eems.model.UserEntity;
import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import com.iip.datafusion.util.jsonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EntityService{
    @Autowired
    private EntityDao entityDAO;
    @Autowired
    private CmsDao cmsDao;
    @Autowired
    private UserEntityDao userEntityDao;
    @Autowired
    private EntityDao entityDao;
    @Autowired
    private DataSourceRouterManager dataSourceRouterManager;

    public int insertEntity(Entity entity){
        return entityDAO.insertEntity(entity);
    }

    public boolean updateEntity(int update_id,String property,String newValue){
         return entityDAO.updateEntity(update_id,property,newValue);
    }
    public boolean deleteEntity(int  del_id){
        return entityDAO.deleteEntity(del_id);
    }

    public List<Entity> getEntityByUserId(int userid){
        List<Entity> list = entityDAO.getEntityByUserId(userid);
        //检查entity列表中，已连接的与未连接的
        for(Entity e:list){
            for(DataSourceProperties d:dataSourceRouterManager.getDataSourceProperties()){
                if(d.getUrl().equals(e.getDbPosition())){
                    e.setDbID(d.getId());
                }
            }
        }
        return list;
    }

    //在目标数据库中，创建实体表
    public Result createEntityTable(Entity entity,int id) {
        //检查用户设置表名是否与已有表名重合
        DataBaseStructure structure =(DataBaseStructure)cmsDao.getDatabaseStructure(entity.getDbPosition()).get("success");
        if(structure.getTableNames().contains(entity.getTableName())){
            return new Result(0,"目标数据库中"+entity.getTableName()+"表已存在",null);
        }

        //检查实体/事件名是否与已有实体/事件名重合
        List<Entity> list = entityDao.getEntityByUserId(id);
        for(Entity item:list){
            if(item.getDisplayName().equals(entity.getDisplayName()) && item.getDisplayName().equals(entity.getDisplayName())){
                return new Result(0,"您已创建过实体/事件:"+entity.getDisplayName(),null);
            }
        }

        return cmsDao.createTable(entity);
    }

    public boolean delEntDB(int entityId) {
        //找到id对应的数据库，表
        Entity entity = getEntityByEntityID(entityId);
        if (entity == null)  return false;

        cmsDao.deleteTable(entity.getDbID(),entity.getTableName());
        return true;
    }

    public Entity getEntityByEntityID(int entityId) {
        Entity entity = entityDao.getEntityById(entityId);
        if(entity==null) return entity;

        for(DataSourceProperties d:dataSourceRouterManager.getDataSourceProperties()){
            if(d.getUrl().equals(entity.getDbPosition())){
                entity.setDbID(d.getId());
            }
        }
        return entity;
    }
}