package com.iip.datafusion.dgs.service;

import com.iip.datafusion.dgs.dao.InstanceDao;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class DgsService {

    @Autowired
    private InstanceDao instanceDao;

    public String getEmptyEntity(String tableName,String columnName){
        return instanceDao.getEmptyEntity(tableName,columnName);
    }

}
