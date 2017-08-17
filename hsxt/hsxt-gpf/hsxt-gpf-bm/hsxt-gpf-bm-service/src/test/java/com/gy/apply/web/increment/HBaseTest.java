package com.gy.apply.web.increment;

import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class HBaseTest {


    @Resource
    private HBaseDB hBaseDb;

    @Test
    public void testQueryList() throws Exception {

        GridPage page = new GridPage();
        page.setPageSize(7);
        page.setCurPage(3);
        Query query = new Query();
        query.setStartDate("20160510");
        query.setEndDate("20160514");
        GridData<PointValue> pointValues = hBaseDb.queryPageFilterList(Constants.T_APP_POINT_VALUE, PointValue.class,page,query);

        System.out.println(pointValues);

    }


}
