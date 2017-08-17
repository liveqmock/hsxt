package com.gy.hsxt.bs.annualfee.service;

import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfoQuery;
import com.gy.hsxt.common.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeInfoServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/14 10:32
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class AnnualFeeInfoServiceTest {

    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;

    @Test
    public void testSaveBean() throws Exception {
        AnnualFeeInfo annualFeeInfo = new AnnualFeeInfo();

        annualFeeInfoService.saveBean(annualFeeInfo);

    }

    @Test
    public void testModifyBean() throws Exception {

    }

    @Test
    public void testModifyBeanForPaid() throws Exception {

    }

    @Test
    public void testQueryBeanById() throws Exception {

    }

    @Test
    public void testQueryListByBean() throws Exception {

    }


    @Test
    public void testQueryListByQuery() throws Exception {
        // 设置查询条件
        AnnualFeeInfoQuery query = new AnnualFeeInfoQuery();
        query.setEndLineDate(DateUtil.getCurrentDate2String());// 截至日期小于今天
        // 查询所有欠费的年费信息
        List<AnnualFeeInfo> annualFeeInfos = annualFeeInfoService.queryListByQuery(query);

        System.out.println(annualFeeInfos);
    }

    @Test
    public void testQueryListForPage() throws Exception {

    }

    @Test
    public void testIsArrear() throws Exception {

        String custId = "0600102000020151215";
        System.out.println(annualFeeInfoService.isArrear(custId));
    }
}