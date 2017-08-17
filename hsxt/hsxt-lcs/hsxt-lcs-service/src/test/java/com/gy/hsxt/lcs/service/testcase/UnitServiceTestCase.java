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
import com.gy.hsxt.lcs.bean.Unit;
import com.gy.hsxt.lcs.interfaces.ILanguageService;
import com.gy.hsxt.lcs.interfaces.IUnitService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : UnitServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试UnitService
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
public class UnitServiceTestCase {
	
	@Autowired
	IVersionService versionService;
	
	@Autowired
	IUnitService unitService;
	
	@Autowired
	ILanguageService languageService;
	
	Unit unit;
	Language language;
	
	String languageCode="LANCODE_T";
	String unitCode="CODE_T";
	String unitName="UNIT_NAME_T";
	
	String unitCode02="CODE_T02";
	String unitName02="UNIT_NAME_T02";
	
	
	@Before
	public void setUp() throws Exception {
		unit = new Unit(languageCode,unitCode);
		unit.setUnitName(unitName);
		unit.setDelFlag(false);
		
		List<Language> languageList = new ArrayList<Language>();
		languageList.add(new Language(languageCode));
		languageService.addOrUpdateLanguage(languageList, new Long(99));
	}
	
	

	@Test
	public void queryUnitWithPK(){
		List<Unit> list = new ArrayList<Unit>();
		list.add(unit);
		unitService.addOrUpdateUnit(list, new Long(99));
		Unit unitDB = unitService.queryUnitWithPK(languageCode, unitCode);
		Assert.isTrue(languageCode.equals(unitDB.getLanguageCode()));
		Assert.isTrue(unitCode.equals(unitDB.getUnitCode()));
		Assert.isTrue(unitName.equals(unitDB.getUnitName()));
		Assert.isTrue(!unitDB.isDelFlag());
	}

	
	@Test
	public void queryUnitByLanguage(){
		List<Unit> list = new ArrayList<Unit>();
		list.add(unit);
		Unit unit02 = new Unit(languageCode,unitCode02);
		unit02.setUnitName(unitName02);
		list.add(unit02);
		unitService.addOrUpdateUnit(list, new Long(99));
		List<Unit> retList = unitService.queryUnitByLanguage(languageCode);
		Assert.isTrue(2==retList.size());
	}

	
	@Test
	public void addOrUpdateUnit(){
		List<Unit> list = new ArrayList<Unit>();
		list.add(unit);
		Unit unit02 = new Unit(languageCode,unitCode02);
		unit02.setUnitName(unitName02);
		list.add(unit02);
		
		int flag = unitService.addOrUpdateUnit(list, new Long(99));
		Assert.isTrue(1==flag);
		Assert.notNull(unitService.queryUnitWithPK(languageCode, unitCode));
		Assert.notNull(unitService.queryUnitWithPK(languageCode, unitCode02));
	}

}
