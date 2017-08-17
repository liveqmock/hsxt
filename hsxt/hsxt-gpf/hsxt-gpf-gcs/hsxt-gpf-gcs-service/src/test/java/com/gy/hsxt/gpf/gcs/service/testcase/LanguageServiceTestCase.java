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
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
	public void addLanguage(){
		int row = languageService.addLanguage(language);
		Assert.isTrue(1==row);
		List<Language> languageList = languageService.queryLanguage(language);
		Language languageDB = languageList.get(0);
		Assert.isTrue(languageCode.equals(languageDB.getLanguageCode()));
		Assert.isTrue(chineseName.equals(languageDB.getChineseName()));
		Assert.isTrue(localName.equals(languageDB.getLocalName()));
		Assert.isTrue(!languageDB.isDelFlag());

		Version version = versionService.queryVersion(Constant.GL_LANGUAGE);
		Assert.isTrue(version.getVersion()==languageDB.getVersion());
	}

	@Test
	public void deleteLanguage(){
		languageService.addLanguage(language);
		Assert.isTrue(languageService.deleteLanguage(languageCode));
		Language languageNew = new Language(languageCode); 
		languageNew.setDelFlag(true);
		List<Language> languageList = languageService.queryLanguage(languageNew);
		Language languageDB = languageList.get(0);
		Assert.isTrue(languageCode.equals(languageDB.getLanguageCode()));
		Assert.isTrue(languageDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_LANGUAGE);
		Assert.isTrue(version.getVersion()==languageDB.getVersion());
	}

	@Test
	public void updateLanguage(){
		languageService.addLanguage(language);
		Language langeuageNew = new Language(languageCode);
		langeuageNew.setChineseName(chineseName02);
		langeuageNew.setLocalName(localName02);
		Assert.isTrue(languageService.updateLanguage(langeuageNew));
		
		List<Language> languageList = languageService.queryLanguage(langeuageNew);
		Language languageDB = languageList.get(0);
		Assert.isTrue(languageCode.equals(languageDB.getLanguageCode()));
		Assert.isTrue(chineseName02.equals(languageDB.getChineseName()));
		Assert.isTrue(localName02.equals(languageDB.getLocalName()));
		
		Version version = versionService.queryVersion(Constant.GL_LANGUAGE);
		Assert.isTrue(version.getVersion()==languageDB.getVersion());
	}

	@Test
	public void queryLanguage(){
		languageService.addLanguage(language);
		List<Language> languageList = languageService.queryLanguage(language);
		Assert.isTrue(1==languageList.size());
		Assert.isTrue(languageCode.equals(languageList.get(0).getLanguageCode()));
		
		Language language02 = new Language(languageCode02);
		language02.setChineseName(chineseName02);
		language02.setLocalName(localName02);
		List<Language> languageList02 = languageService.queryLanguage(language02);
		Assert.isTrue(0==languageList02.size());
		
		languageService.addLanguage(language02);
		
		Language language03 = new Language(languageCode02);
		List<Language> languageList03 = languageService.queryLanguage(language03);
		Assert.isTrue(languageCode02.equals(languageList03.get(0).getLanguageCode()));
		
		Language language04 = new Language();
		language04.setChineseName("NAME");
		List<Language> languageList04 = languageService.queryLanguage(language04);
		Assert.isTrue(2==languageList04.size());
		
		Language language05 = new Language();
		language05.setLocalName(("NAME"));
		List<Language> languageList05 = languageService.queryLanguage(language05);
		Assert.isTrue(2==languageList05.size());
	}

	@Test
	public void existLanguage(){
		Assert.isTrue(!languageService.existLanguage(languageCode));
		languageService.addLanguage(language);
		Assert.isTrue(languageService.existLanguage(languageCode));
	}
	
	@Test
	public void  language4DropDownList(){
		
		languageService.addLanguage(language);
		
		Language language02 = new Language(languageCode02);
		language02.setChineseName(chineseName02);
		language02.setLocalName(localName02);
		
		languageService.addLanguage(language02);
		
		Assert.isTrue(languageService.language4DropDownList().size()>=2);
	}

	@Test
	public void queryLanguage4SyncData(){
		List<Language> languageList = languageService.queryLanguage4SyncData(new Long(0));
		int count = 0;
		for(Language lan:languageList){
			if(lan.getVersion()<=20){
				count++;
			}
		}
		int bigThan20Count = languageList.size() - count;
		Assert.isTrue(bigThan20Count==languageService.queryLanguage4SyncData(new Long(20)).size());
	}
	
}
