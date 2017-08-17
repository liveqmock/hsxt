package com.gy.hsxt.bs.annualfee.service;

import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeePriceService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeePrice;
import com.gy.hsxt.bs.common.enumtype.annualfee.PriceStatus;
import com.gy.hsxt.common.constant.CustType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeePriceServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/14 10:35
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class AnnualFeePriceServiceTest {

    @Resource
    private IAnnualFeePriceService annualFeePriceService;

    @Test
    public void testSaveBean() throws Exception {

        AnnualFeePrice service = new AnnualFeePrice();
        service.setCustType(CustType.SERVICE_CORP.getCode());
        service.setPrice("1000");
        service.setProgramName("服务公司年费价格");
        service.setReqOperator("system");
        annualFeePriceService.saveBean(service);

        AnnualFeePrice trust = new AnnualFeePrice();
        trust.setCustType(CustType.TRUSTEESHIP_ENT.getCode());
        trust.setPrice("499");
        trust.setProgramName("托管企业年费价格");
        trust.setReqOperator("system");
        annualFeePriceService.saveBean(trust);

        AnnualFeePrice member = new AnnualFeePrice();
        member.setCustType(CustType.MEMBER_ENT.getCode());
        member.setPrice("0");
        member.setProgramName("成员企业年费价格");
        member.setReqOperator("system");
        annualFeePriceService.saveBean(member);

    }

    @Test
    public void testModifyBean() throws Exception {

    }

    @Test
    public void testQueryBeanById() throws Exception {

    }

    @Test
    public void testQueryListByBean() throws Exception {

    }

    @Test
    public void testApprAnnualFeePrice() throws Exception {
        AnnualFeePrice service = new AnnualFeePrice();
        service.setPriceId("110120151214123640000000");
        service.setStatus(PriceStatus.ENABLE.ordinal());
        service.setApprOperator("system");
        service.setApprRemark("pass");
        annualFeePriceService.apprAnnualFeePrice(service);

        AnnualFeePrice trust = new AnnualFeePrice();
        trust.setPriceId("110120151214123640000001");
        trust.setStatus(PriceStatus.ENABLE.ordinal());
        trust.setApprOperator("system");
        trust.setApprRemark("pass");
        annualFeePriceService.apprAnnualFeePrice(trust);

        AnnualFeePrice member = new AnnualFeePrice();
        member.setPriceId("110120151214123640000002");
        member.setStatus(PriceStatus.ENABLE.ordinal());
        member.setApprOperator("system");
        member.setApprRemark("pass");
        annualFeePriceService.apprAnnualFeePrice(member);
    }

    @Test
    public void testQueryPriceForEnabled() throws Exception {

        String price = annualFeePriceService.queryPriceForEnabled(CustType.SERVICE_CORP.getCode());
        System.out.println(price);
    }
}