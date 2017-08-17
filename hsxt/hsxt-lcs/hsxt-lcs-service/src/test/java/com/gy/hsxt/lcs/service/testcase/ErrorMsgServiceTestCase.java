/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service.testcase;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gy.hsxt.lcs.bean.ErrorMsg;
import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.interfaces.IErrorMsgService;
import com.gy.hsxt.lcs.interfaces.ILanguageService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : ErrorMsgServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-8
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ErrorMsgServiceTestCase {
	
	@Autowired
	ILanguageService languageService;
	@Autowired
	IErrorMsgService errorMsgService;
	
	ErrorMsg errorMsg;
	
	String languageCode = "LANCODE_T";
	int errorCode= 	888;
    String errormsg="ERROR_MSG_T";

    int errorCode02=999;
    String errormsg02="ERROR_MSG_T_02";
	
	
	@Before
	public void setUp() throws Exception {
		Language language = new Language(languageCode);
		List<Language> list = new ArrayList<Language>();
		list.add(language);
		languageService.addOrUpdateLanguage(list, new Long(99));
		
		errorMsg = new ErrorMsg(languageCode,errorCode);
		errorMsg.setErrorMsg(errormsg);
		errorMsg.setDelFlag(false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void queryErrorMsgWithPK(){
		Assert.isNull(errorMsgService.queryErrorMsgWithPK(languageCode, errorCode));
		List<ErrorMsg> list = new ArrayList<ErrorMsg>();
		list.add(errorMsg);
		errorMsgService.addOrUpdateErrorMsg(list, new Long(99));
		Assert.notNull(errorMsgService.queryErrorMsgWithPK(languageCode, errorCode));
		
	}
	
	@Test
	public void addOrUpdateErrorMsg(){
		List<ErrorMsg> msgList = new ArrayList<ErrorMsg>();
		msgList.add(errorMsg);
		
		ErrorMsg msg = new ErrorMsg(languageCode,errorCode02); 
		msg.setErrorMsg(errormsg02);
		msgList.add(msg);
		
		int flag = errorMsgService.addOrUpdateErrorMsg(msgList, new Long(99));
		Assert.isTrue(1==flag);
		
		Assert.notNull(errorMsgService.queryErrorMsgWithPK(languageCode, errorCode));
		Assert.notNull(errorMsgService.queryErrorMsgWithPK(languageCode, errorCode02));
	}
}
