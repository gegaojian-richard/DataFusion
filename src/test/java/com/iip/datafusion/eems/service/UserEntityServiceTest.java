package com.iip.datafusion.eems.service;

import com.iip.datafusion.eems.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by tsy on 2017/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityServiceTest {
    @Autowired
    private UserEntityService userEntityService;
    @Test
    public void insertUserEntity() throws Exception{
        UserEntity testUserEntity=new UserEntity();
        testUserEntity.setUserId(1);
        testUserEntity.setEntityId(3);
        boolean success=userEntityService.insertUserEntity(testUserEntity);
        if(success){
            System.out.println("insert successfully");
        }
    }

}