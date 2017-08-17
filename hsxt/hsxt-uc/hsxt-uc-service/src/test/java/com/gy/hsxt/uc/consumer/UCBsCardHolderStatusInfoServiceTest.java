///*
//* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
//*
//* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
//*/
///*
//* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
//*
//* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
//*/
//
//package com.gy.hsxt.uc.consumer;
//
//import java.util.Map;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.gy.hsi.redis.client.api.RedisUtil;
//import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService;
//import com.gy.hsxt.uc.cache.CacheKeyGen;
//import com.gy.hsxt.uc.consumer.bean.CardHolder;
//import com.gy.hsxt.uc.consumer.bean.HsCard;
//import com.gy.hsxt.uc.util.CacheUtilTest;
//
///** 
// * 
// * @Package: com.gy.hsxt.uc.consumer  
// * @ClassName: UCBsCardHolderStatusInfoServiceTest 
// * @Description: TODO
// *
// * @author: tianxh
// * @date: 2015-10-27 下午5:33:21 
// * @version V1.0  
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
//public class UCBsCardHolderStatusInfoServiceTest {
//	@Autowired IUCBsCardHolderStatusInfoService iUCBsCardHolderStatusInfoService;
//	 
//    private CacheUtilTest tools = new CacheUtilTest();
////	@Test
//	public void searchHsCardStatusInfoBycustId(){
//		String custId = "0500108181520151105";
//		Map<String,String> map = iUCBsCardHolderStatusInfoService.searchHsCardStatusInfoBycustId(custId);
//				
//	}
////	@Test
//	public void isMainInfoApplyChanging(){
//		String custId = "0500108181620151105";
//		boolean status = iUCBsCardHolderStatusInfoService.isMainInfoApplyChanging(custId);
//		
//	}
//	
////	@Test
//	public void changeApplyMainInfoStatus(){
//		String custId = "0500108181620151105";
//		String status = "1";
//		iUCBsCardHolderStatusInfoService.changeApplyMainInfoStatus(custId, status);
//	}
//	
//	
//}
