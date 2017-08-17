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
import com.gy.hsxt.gpf.gcs.bean.Unit;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IUnitService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
		
		language = new Language(languageCode);
		language.setDelFlag(false);
		languageService.addLanguage(language);
	}
	
	
	@Test
	public void addUnit(){
		int row = unitService.addUnit(unit);
		Assert.isTrue(1==row);
		Unit unitDB = unitService.queryUnitWithPK(languageCode, unitCode);
		Assert.isTrue(languageCode.equals(unitDB.getLanguageCode()));
		Assert.isTrue(unitCode.equals(unitDB.getUnitCode()));
		Assert.isTrue(unitName.equals(unitDB.getUnitName()));
		Assert.isTrue(!unitDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_UNIT);
		Assert.isTrue(version.getVersion()==unitDB.getVersion());
	}

	@Test
	public void deleteUnit(){
		unitService.addUnit(unit);
		Assert.isTrue(unitService.deleteUnit(languageCode, unitCode));
		Unit unitDB = unitService.queryUnitWithPK(languageCode, unitCode);
		Assert.isTrue(unitDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_UNIT);
		Assert.isTrue(version.getVersion()==unitDB.getVersion());
	}

	@Test
	public void updateUnit(){
		unitService.addUnit(unit);
		Unit unit02 = new Unit(languageCode,unitCode);
		unit02.setUnitName(unitName02);
		Assert.isTrue(unitService.updateUnit(unit02));
		Unit unitDB = unitService.queryUnitWithPK(languageCode, unitCode);
		Assert.isTrue(unitName02.equals(unitDB.getUnitName()));
		
		Version version = versionService.queryVersion(Constant.GL_UNIT);
		Assert.isTrue(version.getVersion()==unitDB.getVersion());
	}

	@Test
	public void queryUnitWithPK(){
		unitService.addUnit(unit);
		Unit unitDB = unitService.queryUnitWithPK(languageCode, unitCode);
		Assert.isTrue(languageCode.equals(unitDB.getLanguageCode()));
		Assert.isTrue(unitCode.equals(unitDB.getUnitCode()));
		Assert.isTrue(unitName.equals(unitDB.getUnitName()));
		Assert.isTrue(!unitDB.isDelFlag());
	}

	@Test
	public void queryUnit(){
		unitService.addUnit(unit);
		List<Unit> list = unitService.queryUnit(unit);
		Assert.isTrue(1==list.size());
	}

	@Test
	public void existUnit(){
		Assert.isTrue(!unitService.existUnit(languageCode, unitCode));
		unitService.addUnit(unit);
		Assert.isTrue(unitService.existUnit(languageCode, unitCode));
	}
	
	@Test
	public void queryUnitByLanguage(){
		
		unitService.addUnit(unit);
		Unit unit02 = new Unit(languageCode,unitCode02);
		unit02.setUnitName(unitName02);
		unitService.addUnit(unit02);
		
		List<Unit> list = unitService.queryUnitByLanguage(languageCode);
		Assert.isTrue(2==list.size());
	}

	@Test
	public void queryUnit4SyncData(){
		List<Unit> list = unitService.queryUnit4SyncData(new Long(0));
		int count=0;
		for(Unit unit:list){
			if(unit.getVersion()<=10){
				count++;
			}
		}
		int bigThan10Count = list.size() - count;
		Assert.isTrue(bigThan10Count==unitService.queryUnit4SyncData(new Long(10)).size());
	}

}
