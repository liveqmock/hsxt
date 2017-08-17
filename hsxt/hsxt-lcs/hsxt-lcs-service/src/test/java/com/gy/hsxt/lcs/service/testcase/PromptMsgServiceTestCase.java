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

import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.bean.PromptMsg;
import com.gy.hsxt.lcs.interfaces.ILanguageService;
import com.gy.hsxt.lcs.interfaces.IPromptMsgService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : PromptMsgServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-17
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试PromptMsgService
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@FixMethodOrder(MethodSorters.DEFAULT) 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PromptMsgServiceTestCase {
	
	@Autowired
	ILanguageService languageService;
	
	@Autowired
	IVersionService versionService;
	
	@Autowired
	IPromptMsgService promptMsgService;
	
	
	PromptMsg promptMsg;
	
	String languageCode = "LANCODE_T";
	String promptCode="PROMPT_CODE_T";
    String promptmsg="PROMPT_MSG_T";

    String promptCode02="PROMPT_CODE_T_02";
    String promptmsg02="PROMPT_MSG_T_02";
	
	
	@Before
	public void setUp() throws Exception {
		List<Language> languageList = new ArrayList<Language>();
		languageList.add(new Language(languageCode));
		languageService.addOrUpdateLanguage(languageList, new Long(99));
		
		promptMsg = new PromptMsg(languageCode,promptCode);
		promptMsg.setPromptMsg(promptmsg);
		promptMsg.setDelFlag(false);

	}

	@Test
	public void queryPromptMsgWithPK(){
		Assert.isTrue(null==promptMsgService.queryPromptMsgWithPK(languageCode, promptCode));
		List<PromptMsg> msgList = new ArrayList<PromptMsg>();
		msgList.add(promptMsg);
		promptMsgService.addOrUpdatePromptMsg(msgList, new Long(99));
		
		Assert.notNull(promptMsgService.queryPromptMsgWithPK(languageCode, promptCode));
	}

	@Test
	public void addOrUpdatePromptMsg(){
		List<PromptMsg> msgList = new ArrayList<PromptMsg>();
		msgList.add(promptMsg);
		
		PromptMsg msg = new PromptMsg(languageCode,promptCode02); 
		msg.setPromptMsg(promptmsg02);
		msgList.add(msg);
		
		int flag = promptMsgService.addOrUpdatePromptMsg(msgList, new Long(99));
		Assert.isTrue(1==flag);
		
		Assert.notNull(promptMsgService.queryPromptMsgWithPK(languageCode, promptCode));
		Assert.notNull(promptMsgService.queryPromptMsgWithPK(languageCode, promptCode02));
	}

}
