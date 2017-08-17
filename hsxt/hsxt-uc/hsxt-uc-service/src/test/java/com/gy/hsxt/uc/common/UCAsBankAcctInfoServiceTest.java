/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.as.bean.consumer.AsNoCardHolder;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.common.bean.EntQkAccount;
import com.gy.hsxt.uc.common.mapper.EntQkAccountMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UCAsBankAcctInfoServiceTest {

	@Autowired
	IUCAsBankAcctInfoService acctInfoService;
	String custId = "12";
	String entCustId = "13";

	@Autowired
	IUCAsEntService entService;
	
	final String resNo = "05001081815";
	final String entResNo = "05001080000";

	@Autowired
	EntQkAccountMapper entQkAccountMapper;
	@Autowired
	IUCAsNoCardHolderService nocardHolderService;
	

//	@Test
//	public void bindCustBank() {
//		String custId = "0600211814220151207";
//		String resNo = "06002118142";
//		CardHolder ch = new CardHolder();
//		ch.setPerCustId(custId);
//		ch.setPerResNo(resNo);
//		cacheUtil.getChangeRedisUtil().add(custId, ch);
//
//		AsBankAcctInfo acctInfo = new AsBankAcctInfo();
//		acctInfo.setCustId(custId);
//		acctInfo.setResNo(resNo);
//		acctInfo.setBankBranch("梅林_tianxh_支行");
//		acctInfo.setBankCardType("2");
//		acctInfo.setBankAccNo("12345698799");
//		acctInfo.setBankCode("1234");
//		acctInfo.setBankName("测试银行");
//		acctInfo.setCountryNo("Cn");
//		acctInfo.setCityNo("sz");
//		acctInfo.setProvinceNo("gd");
//		acctInfo.setIsDefaultAccount("1");
//		acctInfo.setIsValidAccount(1);
//		acctInfo.setUsedFlag("1");
//
//		acctInfoService.bindBank(acctInfo, "2");
//	}

	@SuppressWarnings("unused")
	@Test
	public void bindBank(){
		AsBankAcctInfo acctInfo = new AsBankAcctInfo();
		acctInfo.setBankAccName("李智服务公司开发联调3");
		acctInfo.setBankAccNo("62270072015113213");
		acctInfo.setBankCode("1002");
		acctInfo.setBankName("中国进出口银行");
		acctInfo.setCityNo("130100");
		acctInfo.setCountryNo("156");
		acctInfo.setCustId("999882958095751352");
		acctInfo.setResNo("06007000000");
		acctInfo.setIsDefaultAccount("0");
		acctInfo.setProvinceNo("13");
//		acctInfo.setAccId(accId);
		acctInfoService.bindBank(acctInfo, "1");
		
		AsNoCardHolder   noCardHolder  = nocardHolderService.searchNoCardHolderInfoByCustId("999882958095751352");
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void showStatus(){
		AsEntStatusInfo entStatusInfo  = entService.searchEntStatusInfo("0600400000020160122");
	}
	
//	@Test
//	public void bindEntBank() {
//		EntExtendInfo extendInfo = new EntExtendInfo();
//		String entCustId = "0601000000020151231";
//		String entResNo = "06010000000";
//		extendInfo.setEntCustId(entCustId);
//		extendInfo.setEntResNo(entResNo);
//		cacheUtil.getFixRedisUtil().add(entCustId, extendInfo);
//
//		AsBankAcctInfo acctInfo = new AsBankAcctInfo();
//		acctInfo.setCustId(entCustId);
//		acctInfo.setResNo(entResNo);
//		acctInfo.setBankBranch("KSL龙华支行");
//		acctInfo.setBankCardType("3");
//		acctInfo.setBankAccNo("622500000029");
//		acctInfo.setBankCode("CNB");
//		acctInfo.setBankName("中国_KSL_银行");
//		acctInfo.setBankAccName("KSL2015");
//		acctInfo.setCountryNo("Cn");
//		acctInfo.setCityNo("sz");
//		acctInfo.setProvinceNo("gd");
//		acctInfo.setIsDefaultAccount("1");
//		acctInfo.setIsValidAccount(1);
//		acctInfo.setUsedFlag("1");
//
//		acctInfoService.bindBank(acctInfo, "4");
//	}

	@Test
	public void setDefaultBank() {
		// clean();
		String custId = "06010110000000020160109";
		acctInfoService.setDefaultBank(custId, UserTypeEnum.ENT.getType(), 212L);
	}

	// @Test
	public void setUpdateTransStatus() {
		acctInfoService.updateTransStatus(2L, "0",
				UserTypeEnum.CARDER.getPrefix());
	}

	@Test
	public void listBanksByCustId() {
		String custId = "0600102000020151215";
		String userType = "4";
		List<AsBankAcctInfo> list = acctInfoService.listBanksByCustId(custId,
				userType);
	}


	// @Test
	public void setQkBank() {
		AsQkBank qkBank = new AsQkBank();
		qkBank.setBankName("农业银行");
		qkBank.setBankCode("CYB");
		qkBank.setBankCardNo("622700001");
		qkBank.setCustId(custId);
		qkBank.setResNo(resNo);
		qkBank.setSignNo("21452110");
		qkBank.setAmountSingleLimit("1000.250");
		qkBank.setAmountTotalLimit("500000.440");
		qkBank.setSmallPayExpireDate("2016-10-08 20:08:07");
		acctInfoService.setQkBank(qkBank, UserTypeEnum.CARDER.getPrefix());
	}

	@Test
	public void listQkBanksByCustId() {
		String custId = "0600211171520151207";
		List<AsQkBank> list = acctInfoService.listQkBanksByCustId(custId,
				UserTypeEnum.CARDER.getType());
	}

	@Test
	public void unbindBank() {
		acctInfoService.unBindBank(446L, "4");
	}

	@Test
	public void unBindQkBank() {
		acctInfoService.unBindQkBank(7L, "4");
	}

	@Test
	public void findBankAccByAccId() {
		long accId = 12;
		String userType = UserTypeEnum.CARDER.getType();
		// 持卡人
		// long accId = 8;
		// String userType = UserTypeEnum.CARDER.getType();
		AsBankAcctInfo bankAcctInfo = acctInfoService.findBankAccByAccId(accId,
				userType);

	}

	@Test
	public void updateBankAcctDefaultInfo() {
		AsBankAcctInfo acctInfo = new AsBankAcctInfo();
		Long accId = 192L;
		String cust = "0601011000020160109";
		Integer isDefaultAccount= 1;
		String userType = "4";
		 acctInfoService.updateBankAcctDefaultInfo(cust, accId,
		 isDefaultAccount, userType);
	}

	@Test
	public void deleteByPrimaryKey() {
		entQkAccountMapper.deleteByPrimaryKey(5L);
	}

	@SuppressWarnings("unused")
	@Test
	public void insertSelective() {
		EntQkAccount qk = new EntQkAccount();
		qk.setEntCustId(entCustId);
		qk.setBankCardNo("13");
		qk.setEntResNo("66554411224");
		qk.setBankName("华夏银行");
		qk.setBankSignNo("1414");
		qk.setBankCode("1212");
		entQkAccountMapper.insertSelective(qk);
	}

	@SuppressWarnings("unused")
	@Test
	public void selectByPrimaryKey() {
		EntQkAccount qkAccount = entQkAccountMapper.selectByPrimaryKey(5L);
	}

	@SuppressWarnings("unused")
	@Test
	public void updateByPrimaryKeySelective() {
		EntQkAccount qk = new EntQkAccount();
		qk.setEntCustId("13");
		qk.setBankCardNo("13");
		qk.setEntResNo("66554411224");
		qk.setBankName("中国男孩华夏银行");
		qk.setBankSignNo("1414");
		qk.setBankCode("1212");
		qk.setAccId(4L);
		entQkAccountMapper.updateByPrimaryKeySelective(qk);
	}

	@SuppressWarnings("unused")
	@Test
	public void listAccountByCustId() {

		List<EntQkAccount> list = entQkAccountMapper.listAccountByCustId("13");
	}

}
