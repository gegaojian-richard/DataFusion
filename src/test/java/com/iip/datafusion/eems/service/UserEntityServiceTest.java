package com.iip.datafusion.eems.service;

import com.iip.datafusion.eems.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by tsy on 2017/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityServiceTest {
    private static Logger logger = LoggerFactory.getLogger(UserEntityService.class);
    @Autowired
    private UserEntityService userEntityService;
    @Test
    public void insertUserEntity() throws Exception{
        UserEntity testUserEntity=new UserEntity();
        testUserEntity.setUserId(1);
        testUserEntity.setEntityId(3);
        boolean success=userEntityService.insertUserEntity(testUserEntity);
        if(success){
            logger.info("insert successfully");
        }
    }

}