/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service.testcase;

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

import com.gy.hsxt.gpf.gcs.bean.ErrorMsg;
import com.gy.hsxt.gpf.gcs.bean.Language;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IErrorMsgService;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
	IErrorMsgService errorMsgService;
	
	@Autowired
	ILanguageService languageService;
	
	@Autowired
	IVersionService versionService;
	
	ErrorMsg errorMsg;
	
	String languageCode = "LANCODE_T";
	int errorCode= 	888;
    String errormsg="ERROR_MSG_T";

    int errorCode02=999;
    String errormsg02="ERROR_MSG_T_02";
	
	
	@Before
	public void setUp() throws Exception {
		Language language = new Language(languageCode);
		languageService.addLanguage(language);
		
		errorMsg = new ErrorMsg(languageCode,errorCode);
		errorMsg.setErrorMsg(errormsg);
		errorMsg.setDelFlag(false);
	}
	

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void addErrorMsg(){
		int row = errorMsgService.addErrorMsg(errorMsg);
		Assert.isTrue(1==row);
		ErrorMsg errorMsgDB = errorMsgService.queryErrorMsgWithPK(languageCode, errorCode);
		Assert.isTrue(languageCode.equals(errorMsgDB.getLanguageCode()));
		Assert.isTrue(errorCode==errorMsgDB.getErrorCode());
		
		Version version = versionService.queryVersion(Constant.GL_ERROR_MSG);
		Assert.isTrue(version.getVersion()==errorMsgDB.getVersion());
		
	}
	@Test
	public void deleteErrorMsg(){
		errorMsgService.addErrorMsg(errorMsg);
		Assert.isTrue(errorMsgService.deleteErrorMsg(languageCode, errorCode));
		ErrorMsg errorMsgDB = errorMsgService.queryErrorMsgWithPK(languageCode, errorCode);
		Assert.notNull(errorMsgDB);
		Assert.isTrue(errorMsgDB.isDelFlag());
	}
	@Test
	public void updateErrorMsg(){
		errorMsgService.addErrorMsg(errorMsg);
		ErrorMsg errorMsgNew = new ErrorMsg(languageCode,errorCode);
		errorMsgNew.setErrorMsg(errormsg02);
		Assert.isTrue(errorMsgService.updateErrorMsg(errorMsgNew));
		ErrorMsg errorMsgDB = errorMsgService.queryErrorMsgWithPK(languageCode, errorCode);
		Assert.isTrue(errormsg02.equals(errorMsgDB.getErrorMsg()));
	}
	@Test
	public void queryErrorMsgWithPK(){
		Assert.isNull(errorMsgService.queryErrorMsgWithPK(languageCode, errorCode));
		errorMsgService.addErrorMsg(errorMsg);
		Assert.notNull(errorMsgService.queryErrorMsgWithPK(languageCode, errorCode));
	}
	@Test
	public void queryErrorMsg(){
		errorMsgService.addErrorMsg(errorMsg);
		List<ErrorMsg> msgList = errorMsgService.queryErrorMsg(errorMsg);
		Assert.notEmpty(msgList);
		Assert.isTrue(1==msgList.size());
		Assert.isTrue(languageCode.equals(msgList.get(0).getLanguageCode()));
		
		ErrorMsg errorMsg02 = new ErrorMsg(languageCode,errorCode02); 
		errorMsg02.setErrorMsg(errormsg02);
		List<ErrorMsg> msgList02 = errorMsgService.queryErrorMsg(errorMsg02);
		Assert.isTrue(0==msgList02.size());
		
		errorMsgService.addErrorMsg(errorMsg02);
		
		ErrorMsg errorMsg03 = new ErrorMsg();
		errorMsg03.setErrorMsg("ERROR");
		
		List<ErrorMsg> msgList03 = errorMsgService.queryErrorMsg(errorMsg03);
		Assert.isTrue(2==msgList03.size());
	}
	@Test
	public void existErrorMsg(){
		Assert.isTrue(!errorMsgService.existErrorMsg(languageCode, errorCode));
		errorMsgService.addErrorMsg(errorMsg);
		Assert.isTrue(errorMsgService.existErrorMsg(languageCode, errorCode));
		
	}
	@Test
	public void queryErrorMsg4SyncData(){
		List<ErrorMsg> msgList = errorMsgService.queryErrorMsg4SyncData(new Long(0));
		int count = 0 ;
		for(ErrorMsg msg: msgList){
			if(msg.getVersion()<=10){
				count++;
			}
		}
		int bigThan10Count = msgList.size() - count;
		
		Assert.isTrue(bigThan10Count==errorMsgService.queryErrorMsg4SyncData(new Long(10)).size());
		
	}
}
