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

import com.gy.hsxt.lcs.bean.EnumItem;
import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.interfaces.IEnumItemService;
import com.gy.hsxt.lcs.interfaces.ILanguageService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service.testcase
 * 
 *  File Name       : EnumItemServiceTestCase.java
 * 
 *  Creation Date   : 2015-7-17
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 测试EnumItemService
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
public class EnumItemServiceTestCase {
	
	@Autowired
	IEnumItemService enumItemService;
	
	@Autowired
	ILanguageService languageService;
	
	@Autowired
	IVersionService versionService;
	
	EnumItem enumItem;
	Language language;
	
	String languageCode="LANCODE_T";
	String groupCode="GROUP_CODE_T";
	String itemCode="ITEM_CODE_T";
	String itemName="ITEM_NAME_T";
	String itemValue="ITEM_VALUE_T";
	
	String groupCode02="GROUP_CODE_T_02";
	String itemCode02="ITEM_CODE_T_02";
	String itemName02="ITEM_NAME_T_02";
	String itemValue02="ITEM_VALUE_T_02";
	
	@Before
	public void setUp() throws Exception {
		enumItem = new EnumItem(languageCode,groupCode,itemCode);
		enumItem.setItemName(itemName);
		enumItem.setItemValue(itemValue);
		enumItem.setDelFlag(false);
		
		List<Language> languageList = new ArrayList<Language>();
		languageList.add(new Language(languageCode));
		languageService.addOrUpdateLanguage(languageList, new Long(99));
	}
	
	@Test
	public void queryEnumItemWithPK(){
		Assert.isTrue(null==enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode));
		List<EnumItem> itemList = new ArrayList<EnumItem>();
		itemList.add(enumItem);
		enumItemService.addOrUpdateEnumItem(itemList, new Long(99));
		
		EnumItem enumItemDB = enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode);
		Assert.isTrue(languageCode.equals(enumItemDB.getLanguageCode()));
		Assert.isTrue(groupCode.equals(enumItemDB.getGroupCode()));
		Assert.isTrue(itemCode.equals(enumItemDB.getItemCode()));
		Assert.isTrue(itemName.equals(enumItemDB.getItemName()));
		Assert.isTrue(itemValue.equals(enumItemDB.getItemValue()));
		Assert.isTrue(!enumItemDB.isDelFlag());
	}
	
	@Test
	public void queryEnumItemByGroup(){
		List<EnumItem> itemList = new ArrayList<EnumItem>();
		itemList.add(enumItem);
		
		EnumItem enumItem02 = new EnumItem(languageCode, groupCode, itemCode02);
		itemList.add(enumItem02);
		
		enumItemService.addOrUpdateEnumItem(itemList, new Long(99));
		List<EnumItem> retItemList = enumItemService.queryEnumItemByGroup(languageCode, groupCode);
		Assert.isTrue(2==retItemList.size());
	}
	
	@Test
	public void addOrUpdateEnumItem(){
		List<EnumItem> itemList = new ArrayList<EnumItem>();
		itemList.add(enumItem);
		
		EnumItem enumItem02 = new EnumItem(languageCode, groupCode02, itemCode02);
		itemList.add(enumItem02);
		
		int flag = enumItemService.addOrUpdateEnumItem(itemList, new Long(99));
		Assert.isTrue(1==flag);
		Assert.notNull(enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode));
		Assert.notNull(enumItemService.queryEnumItemWithPK(languageCode, groupCode02, itemCode02));
	}
}
