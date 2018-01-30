package datafusion;

import com.iip.datafusion.util.dbutil.DataSourceProperties;
import com.iip.datafusion.util.dbutil.DataSourceRouter;
import com.iip.datafusion.util.dbutil.DataSourceRouterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CMSController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DataSourceRouterManager dataSourceRouterManager;


    @RequestMapping("test")
    @ResponseBody
    public String test(HttpSession session){
        return "qq, this sessionid is: " + session.getId();
    }

    @RequestMapping("/addConnection1")
    @ResponseBody
    public String addConnection1(){
        DataSourceProperties properties1 = new DataSourceProperties();
        properties1.setId("ds1");
        properties1.setDisplayName("ds1");
        properties1.setDriverClassName("com.mysql.jdbc.Driver");
        properties1.setUrl("jdbc:mysql://localhost:3306/education_system?useUnicode=true&characterEncoding=gbk&serverTimezone=GMT");
        properties1.setUsername("root");
        dataSourceRouterManager.addDataSource(properties1);
        return "addConnection1 ok";
    }

    @RequestMapping("/addConnection2")
    @ResponseBody
    public String addConnection2(){
        DataSourceProperties properties2 = new DataSourceProperties();
        properties2.setId("ds2");
        properties2.setDisplayName("ds2");
        properties2.setDriverClassName("com.mysql.jdbc.Driver");
        properties2.setUrl("jdbc:mysql://localhost:3306/peopleDB?useUnicode=true&characterEncoding=gbk&serverTimezone=GMT");
        properties2.setUsername("root");
        dataSourceRouterManager.addDataSource(properties2);
        return "addConnection2 ok";
    }

    @RequestMapping("/addto1")
    @ResponseBody
    public String addto1(){
        dataSourceRouterManager.setCurrentDataSourceKey("ds1");
        jdbcTemplate.execute("INSERT INTO courses_info VALUE ('1802', '马克思2', '马克思思想')");
        return "addto1 ok";
    }

    @RequestMapping("/addto2")
    @ResponseBody
    public String addto2(){
        dataSourceRouterManager.setCurrentDataSourceKey("ds2");
        jdbcTemplate.execute("INSERT INTO person(name, phoneNum) VALUE ('Richard2', '1300000000')");
        return "addto2 ok";
    }
}
