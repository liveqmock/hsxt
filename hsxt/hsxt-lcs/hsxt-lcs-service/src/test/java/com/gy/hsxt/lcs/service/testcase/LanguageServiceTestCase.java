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
import com.gy.hsxt.lcs.interfaces.ILanguageService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : LanguageServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-17
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试LanguageService
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
public class LanguageServiceTestCase {
	
	@Autowired
	ILanguageService languageService;
	
	@Autowired
	IVersionService versionService;
	
	Language language;
	
	
	String languageCode = "LANCODE_T";
	String chineseName = "NAME_CN";
	String localName = "NAME_LC";
	
	String languageCode02 = "LANC_T_02";
	String chineseName02 = "NAME_CN_02";
	String localName02 = "NAME_LC_02";
	
	@Before
	public void setUp() throws Exception {
		language = new Language(languageCode);
		language.setChineseName(chineseName);
		language.setLocalName(localName);
		language.setDelFlag(false);
	}
	
	
	
	@Test
	public void addOrUpdateLanguage(){
		List<Language> languageList = new ArrayList<Language>();
		languageList.add(language);
		
		Language language02 = new Language(languageCode02);
		language02.setChineseName(chineseName02);
		language02.setLocalName(localName02);
		languageList.add(language02);
		
		int flag = languageService.addOrUpdateLanguage(languageList, new Long(99));
		Assert.isTrue(1==flag);
	}
}
