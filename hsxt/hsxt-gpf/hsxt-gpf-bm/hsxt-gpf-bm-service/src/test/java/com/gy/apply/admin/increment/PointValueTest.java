package com.gy.apply.admin.increment;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.service.PointValueService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 赠送积分测试类
 *
 * @author zhucy
 * @version v0.0.1
 * @category 赠送积分测试类
 * @projectName apply-incurement
 * @package com.gy.apply.admin.increment.PointValueTest.java
 * @className PointValueTest
 * @description 赠送积分测试类
 * @createDate 2014-6-24 上午11:56:10
 * @updateUser zhucy
 * @updateDate 2014-6-24 上午11:56:10
 * @updateRemark 新增
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class PointValueTest {
    @Resource
    private PointValueService pointValueService;

    @Test
    public void getValues() throws Exception {
        Query query = new Query();
        String dateStr = DateFormatUtils.format(DateUtil.StringToDate("2016-01-01"), Constants.DATE_TIME_FORMAT_SSS);
        String dateStr1 = DateFormatUtils.format(DateUtil.StringToDate("2016-02-25"), Constants.DATE_TIME_FORMAT_SSS);
        query.setStartDate(dateStr);
        query.setEndDate(dateStr1);
//        query.setResNo("06002110000");
        Collection<PointValue> c = pointValueService.queryPointValueList(query);
        for (PointValue pv : c) {
            System.out.println(JSON.toJSONString(pv));
        }
    }
}

	