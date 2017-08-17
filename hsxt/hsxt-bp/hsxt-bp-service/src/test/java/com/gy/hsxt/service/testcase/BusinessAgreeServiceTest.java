/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.service.testcase;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bp.api.IBusinessAgreeService;
import com.gy.hsxt.bp.bean.BusinessAgree;
import com.gy.hsxt.bp.bean.BusinessAgreeFee;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/spring-global.xml"})
public class BusinessAgreeServiceTest {

    @Autowired
    public IBusinessAgreeService ibusinessAgreeService;
    
    @Test
    public void test() throws java.text.ParseException{
        try{
            BusinessAgree businessAgree = new BusinessAgree();
           //  businessAgree.setAgreeId();
            businessAgree.setAgreeCode("B_ANNUAL_FEE");
            businessAgree.setAgreeName("成员企业使用");
            businessAgree.setCreatedby("wxz");
            businessAgree.setIsActive("Y");
            businessAgree.setUpdatedby("wxz");
            Page page = new Page(1,3);
            
//            ibusinessAgreeService.addAgree(businessAgree);
//            ibusinessAgreeService.updateAgree(businessAgree);
//            ibusinessAgreeService.deleteAgree("1");
//            ibusinessAgreeService.getAgreeById("1");
//            ibusinessAgreeService.searchAgreePage(businessAgree, page);
            
            BusinessAgreeFee businessAgreeFee = new BusinessAgreeFee();
            businessAgreeFee.setAgreeFeeCode("B_ANNUAL_FEE");
            businessAgreeFee.setAgreeFeeName("成员企业使用年费");
            businessAgreeFee.setAgreeCode("B_ANNUAL_FEE");
            businessAgreeFee.setBillingType("ANNUAL");
            businessAgreeFee.setChargingType("ANNUAL");
            businessAgreeFee.setCreatedby("BOOTUP");
            businessAgreeFee.setCurrencyCode("156");
            businessAgreeFee.setFeeAmount("2000");
            businessAgreeFee.setFromAmount("2000");
            businessAgreeFee.setToAmount("2000");
            businessAgreeFee.setFeeRatio("80");
            businessAgreeFee.setFeeType("22");
            businessAgreeFee.setIsActive("Y");
            businessAgreeFee.setPayPeriod(12);
            String agreeFeeId = "2";
            String agreeCode = "B_ANNUAL_FEE";
            String agreeCodeFee = "B_ANNUAL_FEE";
            
//            ibusinessAgreeService.addAgreeFee(businessAgreeFee);
//            ibusinessAgreeService.updateAgreeFee(businessAgreeFee);
            ibusinessAgreeService.deleteAgreeFee(agreeFeeId,agreeCode,agreeCodeFee);
//            ibusinessAgreeService.getAgreeFeeById(agreeFeeId);
//            ibusinessAgreeService.searchAgreeFeePage(businessAgreeFee, page);
            
            
            
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
