package com.iip.datafusion.dgs.service;

import com.iip.datafusion.dgs.dao.CommonDao;
import com.iip.datafusion.dgs.dao.IntegrityDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Service
public class IntegrityService {

    @Autowired
    private IntegrityDao integrityDao;
    @Autowired
    private CommonDao commonDao;

    public SqlRowSet tableAnyEmptyCheck(String dataSourceId,String tableName, String whereClause){
        return commonDao.doSelect(dataSourceId,tableName,whereClause);
    }

    public ArrayList<String> getTableColumnList(String dataSourceId, String tableName){
        return integrityDao.getTableColumnList(dataSourceId,tableName);
    }

    public boolean updateColumns(String dataSourceId, String tableName,String updateClause,String whereClause){
        return commonDao.doUpdate(dataSourceId,tableName,updateClause,whereClause);
    }

    public boolean doReplace(String dataSourceId, String tableName,String columnClause,String valueClause){
        return commonDao.doReplace(dataSourceId,tableName,columnClause,valueClause);
    }

}
