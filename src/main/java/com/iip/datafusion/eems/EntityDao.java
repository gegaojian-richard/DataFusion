package com.iip.datafusion.eems;

import com.iip.datafusion.eems.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
//import javax.annotation.Resource;
import java.util.List;
@Repository
public class EntityDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insertEntity(Entity entity){}
    public void deleteEntity(Entity entity){}
    public void updateEntity(Entity entity){}
    public void  getAllEntity(String entityClass){}

}