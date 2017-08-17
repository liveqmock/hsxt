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

package com.gy.hsxt.uc.consumer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameReg;
import com.gy.hsxt.uc.cache.CacheKeyGen;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer
 * @ClassName: UCBsCardHolderAuthInfoServiceTest
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-11-5 下午4:40:30
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCBsCardHolderAuthInfoServiceTest {
	@Autowired
	private IUCBsCardHolderAuthInfoService iUCBsCardHolderAuthInfoService;
	 
	@Test
	public void authByRealName(){
	   String custId =  "0603901026920160418";
		String operCustId = "05001080000000120151105";
		List<String>  list = new ArrayList<String>();
		BsRealNameAuth realNameAuth = new BsRealNameAuth();
		realNameAuth.setJob("猪头猎人");
		realNameAuth.setCustId(custId);
		realNameAuth.setCerNo("B12345687");
		realNameAuth.setCerType(2);
		realNameAuth.setUserName("阿木");
		realNameAuth.setCountryCode("86");
		realNameAuth.setCerPica("/opt1");
		realNameAuth.setCerPicb("/opt2");
		realNameAuth.setCerPich("/opt3");
		realNameAuth.setIssuingOrg("猪头城市");
		realNameAuth.setSex(1);
		realNameAuth.setValidDate("2015-01-01");
		realNameAuth.setBirthPlace("龙岗");
		realNameAuth.setIssuePlace("龙岗123");
		iUCBsCardHolderAuthInfoService.authByRealName(realNameAuth, operCustId);
		    
		
		
		
//		regRealNameForPassPort(custId, operCustId);//护照
//		regRealNameForBusiLicen(custId, operCustId);//营业执照
//		RealNameAuth realNameAuth = (RealNameAuth) fixRedisUtil.get(realNameKey,
//				RealNameAuth.class);
	}
	
//	@Test
	public void searchRealNameRegInfo(){
		String custId = "0500108181620151105";
		String realNameAuthKey = CacheKeyGen.genRealAuthKey(custId);
		BsRealNameReg bsRealNameReg = iUCBsCardHolderAuthInfoService.searchRealNameRegInfo(custId);
	}
	
//	@Test
	public void searchRealNameAuthInfo(){
		String custId = "0500108181620151105";
		BsRealNameAuth bsRealNameAuth = iUCBsCardHolderAuthInfoService.searchRealNameAuthInfo(custId);
	}
	
//	@Test
	public void updateRealNameAuthInfo(){
		String custId = "0500108181620151105";
		String operCustId = "0500108181620151105";
		BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
		bsRealNameAuth.setBirthAddress("19870601");
		bsRealNameAuth.setCerNo("433127198706015811");
		bsRealNameAuth.setCerPica("/opt/789");
		bsRealNameAuth.setCerPicb("/opt/456");
		bsRealNameAuth.setCerPich("/opt/123");
		bsRealNameAuth.setCerType(1);
		bsRealNameAuth.setCountryCode("86");
		bsRealNameAuth.setCustId(custId);
		bsRealNameAuth.setIssuingOrg("中华民国历史");
		bsRealNameAuth.setSex(1);
		bsRealNameAuth.setUserName("猪头三");
		bsRealNameAuth.setValidDate("2050-12-12 11:11:11");
		iUCBsCardHolderAuthInfoService.updateRealNameAuthInfo(bsRealNameAuth, operCustId);
	}
	
	/**
	 * 身份证
	 */
	private void regRealNameForIdCard(String custId,String operCustId){
	    BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
        bsRealNameAuth.setCerNo("433127198706015811");
        bsRealNameAuth.setCerPica("/opt/789");
        bsRealNameAuth.setCerPich("/opt/123");
        bsRealNameAuth.setCerType(1);
        bsRealNameAuth.setCountryCode("86");
        bsRealNameAuth.setCustId(custId);
        bsRealNameAuth.setUserName("黄高扬");
        bsRealNameAuth.setSex(1);
        bsRealNameAuth.setValidDate("2050-11-11 12:12:12");
        bsRealNameAuth.setBirthAddress("太原");
        bsRealNameAuth.setIssuingOrg("深圳福田派出所");
        bsRealNameAuth.setJob("教授");
        bsRealNameAuth.setCerPicb("/opt/456");
        bsRealNameAuth.setBirthPlace("河田村");
        bsRealNameAuth.setIssuePlace("河田派出所");
        iUCBsCardHolderAuthInfoService.authByRealName(bsRealNameAuth, operCustId);
	}
	
	/**
	 * 护照
	 */
    private void regRealNameForPassPort(String custId,String operCustId ){
        BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
        bsRealNameAuth.setCerNo("533127198706015811");
        bsRealNameAuth.setCerPica("/opt/789");
        bsRealNameAuth.setCerPich("/opt/123");
        bsRealNameAuth.setCerType(2);
        bsRealNameAuth.setCountryCode("86");
        bsRealNameAuth.setCustId(custId);
        bsRealNameAuth.setUserName("张恒");
        bsRealNameAuth.setSex(1);
        bsRealNameAuth.setValidDate("2050-11-11 12:12:12");
        bsRealNameAuth.setIssuingOrg("北京福田派出所");
        bsRealNameAuth.setJob("教授");
        bsRealNameAuth.setBirthPlace("蒙古");
        bsRealNameAuth.setIssuePlace("太原");
        iUCBsCardHolderAuthInfoService.authByRealName(bsRealNameAuth, operCustId);
     }
    
    /**
     * 营业执照
     */
    private void regRealNameForBusiLicen(String custId,String operCustId){
        BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
        bsRealNameAuth.setCerNo("633127198706015811");
        bsRealNameAuth.setCerPica("/opt/789");
        bsRealNameAuth.setCerPich("/opt/123");
        bsRealNameAuth.setCerType(3);
        bsRealNameAuth.setCountryCode("86");
        bsRealNameAuth.setCustId(custId);
        bsRealNameAuth.setUserName("黄高扬");
        bsRealNameAuth.setEntBuildDate("2011-01-01 10:10:10");
        bsRealNameAuth.setEntRegAddr("浦东");
        bsRealNameAuth.setEntName("外贸公司");
        bsRealNameAuth.setEntType("1");
        iUCBsCardHolderAuthInfoService.authByRealName(bsRealNameAuth, operCustId);
    }
    @Test
    public void updateMainInfoApplyInfo(){
    	 String custId = "0600211814520151207";
    	 String operCustId = "9876543210112134567";
    	 BsRealNameAuth bsRealNameAuth = new BsRealNameAuth();
         bsRealNameAuth.setCerNo("633127198706015811");
         bsRealNameAuth.setCerPica("/opt/789");
         bsRealNameAuth.setCerPich("/opt/123");
         bsRealNameAuth.setCerType(3);
         bsRealNameAuth.setSex(1);
         bsRealNameAuth.setCountryCode("86");
         bsRealNameAuth.setCustId(custId);
         bsRealNameAuth.setUserName("李璇1231");
         bsRealNameAuth.setEntBuildDate("2011-01-01 10:10:10");
         bsRealNameAuth.setEntRegAddr("浦东");
         bsRealNameAuth.setEntName("外贸公司");
         bsRealNameAuth.setEntType("1");
    	iUCBsCardHolderAuthInfoService.updateMainInfoApplyInfo(bsRealNameAuth, operCustId);
    }
   
}
