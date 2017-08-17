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

import com.gy.hsxt.gpf.gcs.bean.Language;
import com.gy.hsxt.gpf.gcs.bean.PromptMsg;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IPromptMsgService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
		Language language = new Language(languageCode);
		languageService.addLanguage(language);
		
		promptMsg = new PromptMsg(languageCode,promptCode);
		promptMsg.setPromptMsg(promptmsg);
		promptMsg.setDelFlag(false);

	}
	
	
	@Test
	public void addPromptMsg(){
		int row = promptMsgService.addPromptMsg(promptMsg);
		Assert.isTrue(1==row);
		PromptMsg promptMsgDB = promptMsgService.queryPromptMsgWithPK(languageCode, promptCode);
		Assert.isTrue(languageCode.equals(promptMsgDB.getLanguageCode()));
		Assert.isTrue(promptCode.equals(promptMsgDB.getPromptCode()));
		Assert.isTrue(promptmsg.equals(promptMsgDB.getPromptMsg()));
		
		Version version = versionService.queryVersion(Constant.GL_PROMPT_MSG);
		Assert.isTrue(version.getVersion()==promptMsgDB.getVersion());
	}

	@Test
	public void deletePromptMsg(){
		promptMsgService.addPromptMsg(promptMsg);
		Assert.isTrue(promptMsgService.deletePromptMsg(languageCode, promptCode));
		PromptMsg promptMsgDB = promptMsgService.queryPromptMsgWithPK(languageCode, promptCode);
		Assert.notNull(promptMsgDB);
		Assert.isTrue(promptMsgDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_PROMPT_MSG);
		Assert.isTrue(version.getVersion()==promptMsgDB.getVersion());
	}

	@Test
	public void updatePromptMsg(){
		promptMsgService.addPromptMsg(promptMsg);
		PromptMsg promptMsgNew = new PromptMsg(languageCode,promptCode); 
		promptMsgNew.setPromptMsg(promptmsg02);
		Assert.isTrue(promptMsgService.updatePromptMsg(promptMsgNew));
		PromptMsg promptMsgDB = promptMsgService.queryPromptMsgWithPK(languageCode, promptCode);
		Assert.isTrue(languageCode.equals(promptMsgDB.getLanguageCode()));
		Assert.isTrue(promptCode.equals(promptMsgDB.getPromptCode()));
		Assert.isTrue(promptmsg02.equals(promptMsgDB.getPromptMsg()));
		
		Version version = versionService.queryVersion(Constant.GL_PROMPT_MSG);
		Assert.isTrue(version.getVersion()==promptMsgDB.getVersion());
	}

	@Test
	public void queryPromptMsgWithPK(){
		Assert.isTrue(null==promptMsgService.queryPromptMsgWithPK(languageCode, promptCode));
		promptMsgService.addPromptMsg(promptMsg);
		Assert.notNull(promptMsgService.queryPromptMsgWithPK(languageCode, promptCode));
	}

	@Test
	public void queryPromptMsg(){
		promptMsgService.addPromptMsg(promptMsg);
		List<PromptMsg> msgList = promptMsgService.queryPromptMsg(promptMsg);
		Assert.notEmpty(msgList);
		Assert.isTrue(1==msgList.size());
		Assert.isTrue(languageCode.equals(msgList.get(0).getLanguageCode()));
		
		PromptMsg promptMsg02 = new PromptMsg(languageCode,promptCode02); 
		promptMsg02.setPromptMsg(promptmsg02);
		List<PromptMsg> msgList02 = promptMsgService.queryPromptMsg(promptMsg02);
		Assert.isTrue(0==msgList02.size());
		
		promptMsgService.addPromptMsg(promptMsg02);
		
		PromptMsg promptMsg03 = new PromptMsg();
		promptMsg03.setPromptMsg("PROMPT");
		
		List<PromptMsg> msgList03 = promptMsgService.queryPromptMsg(promptMsg03);
		Assert.isTrue(2==msgList03.size());
	}

	@Test
	public void existPromptMsg(){
		Assert.isTrue(!promptMsgService.existPromptMsg(languageCode, promptCode));
		promptMsgService.addPromptMsg(promptMsg);
		Assert.isTrue(promptMsgService.existPromptMsg(languageCode, promptCode));
	}

	@Test
	public void queryPromptMsg4SyncData(){
		List<PromptMsg> msgList = promptMsgService.queryPromptMsg4SyncData(new Long(0));
		int count = 0 ;
		for(PromptMsg msg: msgList){
			if(msg.getVersion()<=10){
				count++;
			}
		}
		int bigThan10Count = msgList.size() - count;
		
		Assert.isTrue(bigThan10Count==promptMsgService.queryPromptMsg4SyncData(new Long(10)).size());
	}
	
}
