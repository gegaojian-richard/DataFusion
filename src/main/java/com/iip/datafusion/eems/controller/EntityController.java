package com.iip.datafusion.eems.controller;

import java.util.Map;
import com.iip.datafusion.eems.model.Entity;
import com.iip.datafusion.eems.model.UserEntity;
import com.iip.datafusion.eems.service.EntityService;
import com.iip.datafusion.eems.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by tsy on 2017/12/14.
 */
@Controller
public class EntityController {
    @Autowired
    EntityService entityService;
    @Autowired
    UserEntityService userEntityService;

    @RequestMapping(path={"/entity/show"},method={RequestMethod.GET})
    @ResponseBody
    public List<Entity> getUserEnitities(@RequestParam(value="userId",required=false) int userId){
        List<Entity> list = entityService.getEntityByUserId(userId);
        return list;
    }
    @RequestMapping(path={"/entity/delete"},method={RequestMethod.GET})
    @ResponseBody
    public Boolean  deleteEntity(@RequestParam(value="entityId") int entityId){
        return  entityService.deleteEntity(entityId) && userEntityService.deleteEntity(entityId);
    }
    @RequestMapping(path={"/entity/insert"},method={RequestMethod.POST})
    @ResponseBody
    public Boolean insertEntity(@RequestBody Map<String,String> map){
        Entity entity=null;
        for(String key:map.keySet()){
            entity=new Entity();
            entity.setDisplayName(map.get("displayName"));
            entity.setTableName(map.get("tableName"));
            entity.setDbPosition(map.get("dbPosition"));
            entity.setEntityType(Integer.parseInt(map.get("entityType")));
            entity.setProperties(map.get("properties"));
        }
        Boolean addEntity=entityService.insertEntity(entity);
        UserEntity newUserEntity=new UserEntity();
        newUserEntity.setEntityId(entity.getId());
 //       newUserEntity.setUserId(userId);
        newUserEntity.setUserId(1);
        Boolean addUserEntity=userEntityService.insertUserEntity(newUserEntity);
        return (addEntity && addUserEntity);
    }

    @RequestMapping(path={"/entity/update"},method={RequestMethod.GET})
    @ResponseBody
    public boolean updateEntity(@RequestParam(value="entityId") int entityId,@RequestParam(value="property")String property,
                               @RequestParam (value="newValue")String newValue){
        return entityService.updateEntity(entityId,property,newValue);
    }

}
