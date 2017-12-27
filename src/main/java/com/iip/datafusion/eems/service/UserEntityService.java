package com.iip.datafusion.eems.service;

import com.iip.datafusion.eems.dao.UserEntityDao;
import com.iip.datafusion.eems.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tsy on 2017/12/16.
 */
@Service
public class UserEntityService {
    @Autowired
    private UserEntityDao userEntityDao;
    public boolean insertUserEntity(UserEntity userEntity){return userEntityDao.insertUserEntityDao(userEntity);}
    public boolean deleteUser(int userId){return userEntityDao.deleteUserEntityByUser(userId);}
    public boolean deleteEntity(int entityId){return userEntityDao.deleteUserEntityByEntity(entityId);}


}
