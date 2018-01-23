package com.iip.datafusion.eems.service;

import com.iip.datafusion.eems.model.Entity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tsy on 2017/12/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityServiceTest {
    @Autowired
    private EntityService entityService;
    @Test
    public void insertEntity() throws Exception{
        Entity  testEntity=new Entity();
        testEntity.setDisplayName("people");
        testEntity.setTableName("people");
        testEntity.setDbPosition("jdbc:mysql://localhost:3306/");
        testEntity.setEntityType(0);
        int success=entityService.insertEntity(testEntity);
        if(success>0){
            System.out.println("insert successfully");
        }
    }
    @Test
    public void deleteEntity() throws Exception{
       // String deleteName=2;
        boolean success=entityService.deleteEntity(2);
        if(success){
            System.out.println("delete successfully");
        }
    }
    @Test
    public void getEntityByUserId() throws Exception{
       List<Entity> list= entityService.getEntityByUserId(1);
       Iterator it=list.iterator();
       while(it.hasNext()){
           Entity en=(Entity)it.next();
           System.out.println(en.getDisplayName());
       }
    }

}