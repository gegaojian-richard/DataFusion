package com.iip.datafusion.dgs.service;

import com.iip.datafusion.dgs.dao.AccuracyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class AccuracyService {

    @Autowired
    private AccuracyDao accuracyDao;

    public SqlRowSet selectData(String dataSourceId,String tableName,String whereClause){
        return accuracyDao.selectData(dataSourceId,tableName,whereClause);
    }

    public boolean updateData(String dataSourceId,String tableName,String columnName,String newValue,String whereClause){
        return accuracyDao.updateData(dataSourceId,tableName,columnName,newValue,whereClause);
    }

    public boolean updateData2(String dataSourceId,String tableName,String setClause,String whereClause){
        return accuracyDao.updateData2(dataSourceId,tableName,setClause,whereClause);
    }

    public SqlRowSet getPrimaryKey(String dataSourceId,String tableName){
        return accuracyDao.getPrimaryKey(dataSourceId,tableName);
    }

}
