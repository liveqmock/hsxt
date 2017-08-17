package com.gy.hsxt.bs.deduction.service;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.bs.bean.deduction.HsbDeductionQuery;
import com.gy.hsxt.bs.common.BSConstants;
import com.gy.hsxt.bs.common.enumtype.deduction.DeductionStatus;
import com.gy.hsxt.bs.deduction.interfaces.IHsbDeductionService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Package : com.gy.hsxt.bs.deduction.service
 * @ClassName : HsbDeductionServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/3/26 9:38
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
public class HsbDeductionServiceTest {

    @Resource
    private IHsbDeductionService hsbDeductionService;

    @Test
    public void applyHsbDeduction() throws Exception {
        HsbDeduction deduction = new HsbDeduction();
        deduction.setAmount("321");
        deduction.setApplyOper(BSConstants.SYS_OPERATOR);
        deduction.setEntCustId("0603199000220160416");
        deduction.setEntCustName("星爷");
        deduction.setEntResNo("06031990002");
        hsbDeductionService.applyHsbDeduction(deduction);
    }


    @Test
    public void queryHsbDeductionListPage() throws Exception {
        Page page = new Page(1,10);
        HsbDeductionQuery query = new HsbDeductionQuery();
//        query.setStatus(1);
        PageData<HsbDeduction> pageData = hsbDeductionService.queryHsbDeductionListPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void queryTaskListPage() throws Exception {
        Page page = new Page(1,10);
        HsbDeductionQuery query = new HsbDeductionQuery();
        query.setOptCustId("00000000156000220160105");
        PageData<HsbDeduction> pageData = hsbDeductionService.queryTaskListPage(page, query);
        System.out.println(pageData);
    }

    @Test
    public void queryDetailById() throws Exception {

        HsbDeduction deduction = hsbDeductionService.queryDetailById("110120160419104338000000");
        System.out.println(deduction);

    }

    @Test
    public void apprHsbDeduction() throws Exception {
        HsbDeduction deduction = new HsbDeduction();
        //110120160331144049000000  110120160331143608000000
        deduction.setApplyId("110120160419121752000000");
        deduction.setStatus(DeductionStatus.APPR_PASS.ordinal());
        deduction.setApprOper("0000000015630060000");
        deduction.setApprRemark("应该扣款");
        hsbDeductionService.apprHsbDeduction(deduction);
    }
}