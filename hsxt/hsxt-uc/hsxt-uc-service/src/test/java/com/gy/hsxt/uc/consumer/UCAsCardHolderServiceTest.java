/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsConsumerInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLog;
import com.gy.hsxt.uc.as.bean.consumer.AsHsCard;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsUserActionLog;
import com.gy.hsxt.uc.cache.CacheKeyGen;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.service.UCAsDeviceSignInService;
import com.gy.hsxt.uc.consumer.mapper.CardHolderMapper;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.ent.mapper.EntStatusInfoMapper;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.StringEncrypt;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer
 * @ClassName: UCAsCardHolderServiceTest
 * @Description: TODO
 * 
 * @author: tianxhss
 * @date: 2015-10-21 上午11:46:46
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class UCAsCardHolderServiceTest {
	@Autowired
	private UCAsCardHolderService cardHolderService;
	@Autowired
	SysConfig config;

	@Autowired
	EntStatusInfoMapper entStatusInfoMapper;
	@Autowired
	CommonCacheService commonCacheService;
	
	@Autowired
	IUCAsPwdService IUCAsPwdService;
	
	@Autowired
	CardHolderMapper cardHolderMapper;

	@Autowired
	UCAsDeviceSignInService deviceSignInService;
	// @Test
	public void closeAccout() {
		String perCustId = "0500108181620151105";
		String operCustId = "09186630000162706727874560";
		cardHolderService.closeAccout(perCustId, operCustId);

	}

	@Test
	public void findCustIdByResNo() {
		String resNo = "06007010541";
		String custId = cardHolderService.findCustIdByResNo(resNo);
	}

	// @Test
	public void findEmailByCustId() {
		String custId = "0500108181620151105";
		Map<String, String> email = cardHolderService
				.findEmailByCustId(custId);
	}

	// @Test
	public void findMobileByCustId() {
		String custId = "0500108181620151105";
		String mobile = cardHolderService.findMobileByCustId(custId);
	}

	// @Test
	public void updateLoginInfo() {
		String custId = "0500108181620151105";
		String loginIp = "192.168.1.138";
		cardHolderService.updateLoginInfo(custId, loginIp,
				"2015-11-11 12:12:12");
	}
	// @Test
	
	
	@Test
	public void searchHsCardInfoByCustId() {
		String custId = "0600211172120151207";
		String hsCard_key = CacheKeyGen.genCardKey(custId);
		AsHsCard asHsCard = cardHolderService
				.searchHsCardInfoByCustId(custId);
	}

	 @Test
	public void changeBindEmail() {
		String custId = "0603311011720160416";
		String email = "tianxh@gyist.com";
		String secretKey = "7ed570d9876dc18e";
		String loginPwd = "XDAdIWjNeuglQgHMJONcxw==";
		cardHolderService.changeBindEmail(custId, email, loginPwd,
				secretKey);
	}

	@Test
	public void searchCardHolderInfoByResNo() {
		String resNo = "06002111722";
		AsCardHolder holder = cardHolderService
				.searchCardHolderInfoByResNo(resNo);
	}

	 @Test
	public void searchCardHolderInfoByCustId() {
		String custId = "0500108181620151111";
		AsCardHolder asCardHolder = cardHolderService
				.searchCardHolderInfoByCustId(custId);
	}

	@Test
	public void searchHsCardInfoByResNo() {
		String resNo = "06002113099";
		AsHsCard asHsCard = cardHolderService
				.searchHsCardInfoByResNo(resNo);
	}

	@Test
	public void pageConsumerInfo() {
		AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
		condition.setEntResNo("06002110000");
		PageData<AsConsumerInfo> list = cardHolderService
				.listConsumerInfo(condition, new Page(1));
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void searAllInfo() {
		AsCardHolderAllInfo searchAllInfo = cardHolderService
				.searchAllInfo("0500108123820151217");
		System.out.println(JSON.toJSONString(searchAllInfo));
	}

	@Test
	public void pageUserActionInfo() {
		String custId = "0500108123420151217";
		String action = "1";
		int curPage = 1;
		int pageSize = 3;
		Page page = new Page(curPage, pageSize);
		PageData<AsUserActionLog> result = cardHolderService
				.listUserActionLog(custId, action, "2016-01-28", "2016-01-28",
						page);
		System.out.println("ahaha");
	}

	@Test
	public void listConsumerInfo() {
		AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
		condition.setPerResNo("");
		condition.setRealName("");
		Page page = new Page(1, 10);
		PageData<AsConsumerInfo> data = cardHolderService
				.listConsumerInfo(condition, page);
	}
	
	@Test
	public void listAllConsumerInfo() {
		AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
		Page page = new Page(1, 10);
		PageData<AsConsumerInfo> data = cardHolderService.listAllConsumerInfo(condition, page);
	}

	@Test
	public void countAll() {
		AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
		condition.setPerResNo("");
		condition.setRealName("");
		condition.setRealnameStatus(1);
		
		int count  = cardHolderMapper.countAllConsumerInfo(condition);
	
	}
	
	@Test
	public void UpdateCustAndLogAndCheckPwd() {
		String operCustId = "lv";
		String regionalResNo = "00000000156";
		String userName = "0000";
		String pwd = "666666";
		String secretKey = "1111111111111111";
		// aes
		String loginPwd = StringEncrypt.encrypt(pwd, secretKey);
		String perCustId = "0600211813520151207";
		//复核员双签密码验证，返回复核员客户号
		String confirmerCustId=IUCAsPwdService
				.checkLoginPwdForCarderByReChecker(regionalResNo, userName,
						loginPwd, secretKey, operCustId);
		
		
		//待修改的数据
		AsRealNameAuth asRealNameAuth= new AsRealNameAuth();
		
		asRealNameAuth.setCustId(perCustId);
		asRealNameAuth.setUserName("信仰L");
		// ...其他修改字段

		//修改字段记录日志
		AsCustUpdateLog asCustUpdateLog = new AsCustUpdateLog();
		asCustUpdateLog.setUpdatedby(operCustId);
		asCustUpdateLog.setConfirmId(confirmerCustId);
		asCustUpdateLog.setPerCustId(perCustId);
		asCustUpdateLog.setUpdateField("userName");
		asCustUpdateLog.setUpdateFieldName("姓名");
		asCustUpdateLog.setUpdateValueOld("信仰");
		asCustUpdateLog.setUpdateValueNew("信仰L");
		
		List<AsCustUpdateLog> logList =new ArrayList<>();
		logList.add(asCustUpdateLog);
		// ...若有多个字段被修改，增加多条修改日志
		
		//调用uc 接口修改数据及记录修改日志
		String pId=cardHolderService.UpdateCustAndLog( asRealNameAuth,
				 logList,  operCustId,
				 confirmerCustId);
		
		Object ret = null;
		System.out.println(pId);		
		//获取修改记录列表
		ret = cardHolderService.listCustUpdateLog(perCustId,"姓", null);
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		System.out.println(JSON.toJSONString(ret));		
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
	}
	
	@Test
	public void deviceSignInService(){
		deviceSignInService.checkCardCode("06002118120", "");
	}
	
	@SuppressWarnings("unused")
	@Test
	public void searchAll(){
		 AsCardHolderAllInfo   cardHolderAllInfo  = cardHolderService.searchAllInfo("0600211820220151207");
	}


}
