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

import com.gy.hsxt.gpf.gcs.bean.EnumItem;
import com.gy.hsxt.gpf.gcs.bean.Language;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IEnumItemService;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service.testcase
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
		
		languageService.addLanguage(new Language(languageCode));
	}


	@Test
	public void addEnumItem(){
		int row  = enumItemService.addEnumItem(enumItem);
		Assert.isTrue(1==row);
		EnumItem enumItemDB = enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode);
		Assert.notNull(enumItemDB);
		Assert.isTrue(languageCode.equals(enumItemDB.getLanguageCode()));
		Assert.isTrue(groupCode.equals(enumItemDB.getGroupCode()));
		Assert.isTrue(itemCode.equals(enumItemDB.getItemCode()));
		Assert.isTrue(itemName.equals(enumItemDB.getItemName()));
		Assert.isTrue(itemValue.equals(enumItemDB.getItemValue()));
		Assert.isTrue(!enumItemDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_ENUM_ITEM);
		Assert.isTrue(version.getVersion()==enumItemDB.getVersion());
	}

	@Test
	public void deleteEnumItem(){
		enumItemService.addEnumItem(enumItem);
		Assert.isTrue(enumItemService.deleteEnumItem(languageCode, groupCode, itemCode));
		EnumItem enumItemDB = enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode);
		Assert.notNull(enumItemDB);
		Assert.isTrue(enumItemDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_ENUM_ITEM);
		Assert.isTrue(version.getVersion()==enumItemDB.getVersion());
	}

	@Test
	public void updateEnumItem(){
		enumItemService.addEnumItem(enumItem);
		EnumItem enumItemNew = new EnumItem(languageCode, groupCode, itemCode);
		enumItemNew.setItemName(itemName02);
		enumItemNew.setItemValue(itemValue02);
		enumItemNew.setDelFlag(true);
		Assert.isTrue(enumItemService.updateEnumItem(enumItemNew));
		EnumItem enumItemDB = enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode);
		Assert.isTrue(itemName02.equals(enumItemDB.getItemName()));
		Assert.isTrue(itemValue02.equals(enumItemDB.getItemValue()));
		Assert.isTrue(enumItemDB.isDelFlag());
		
		Version version = versionService.queryVersion(Constant.GL_ENUM_ITEM);
		Assert.isTrue(version.getVersion()==enumItemDB.getVersion());
	}

	@Test
	public void queryEnumItem(){
		enumItemService.addEnumItem(enumItem);
		List<EnumItem> itemList = enumItemService.queryEnumItem(enumItem);
		Assert.isTrue(1==itemList.size());
		Assert.isTrue(languageCode.equals(itemList.get(0).getLanguageCode()));
		
		EnumItem enumItem02 = new EnumItem(languageCode, groupCode02, itemCode02); 
		enumItem02.setItemName(itemName02);
		enumItem02.setItemValue(itemValue02);
		List<EnumItem> itemList02 = enumItemService.queryEnumItem(enumItem02);
		Assert.isTrue(0==itemList02.size());
		
		enumItemService.addEnumItem(enumItem02);
		
		EnumItem enumItem03 = new EnumItem();
		enumItem03.setLanguageCode(languageCode);
		List<EnumItem> itemList03 = enumItemService.queryEnumItem(enumItem03);
		Assert.isTrue(2==itemList03.size());
		
		EnumItem enumItem04 = new EnumItem();
		enumItem04.setGroupCode(groupCode);
		List<EnumItem> itemList04 = enumItemService.queryEnumItem(enumItem04);
		Assert.isTrue(1==itemList04.size());
		
		EnumItem enumItem05 = new EnumItem();
		enumItem05.setItemName("NAME");
		List<EnumItem> itemList05 = enumItemService.queryEnumItem(enumItem05);
		Assert.isTrue(2==itemList05.size());
		
		EnumItem enumItem06 = new EnumItem();
		enumItem06.setItemValue("VALUE");
		List<EnumItem> itemList06 = enumItemService.queryEnumItem(enumItem06);
		Assert.isTrue(2==itemList06.size());
	}

	@Test
	public void existEnumItem(){
		Assert.isTrue(!enumItemService.existEnumItem(languageCode, groupCode, itemCode));
		enumItemService.addEnumItem(enumItem);
		Assert.isTrue(enumItemService.existEnumItem(languageCode, groupCode, itemCode));
	}
	
	@Test
	public void queryEnumItemWithPK(){
		Assert.isTrue(null==enumItemService.queryEnumItemWithPK(languageCode, groupCode, itemCode));
		enumItemService.addEnumItem(enumItem);
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
		enumItemService.addEnumItem(enumItem);
		EnumItem enumItemNew = new EnumItem(languageCode, groupCode, itemCode02);
		enumItemService.addEnumItem(enumItemNew);
		
		List<EnumItem> itemList = enumItemService.queryEnumItemByGroup(languageCode, groupCode);
		Assert.isTrue(2==itemList.size());
	}
	
	@Test
	public void queryEnumItem4SyncData(){
		List<EnumItem> itemList = enumItemService.queryEnumItem4SyncData(0);
		int count = 0;
		for(EnumItem item: itemList){
			if(item.getVersion()<=20){
				count++;
			}
		}
		int bigThan20Count = itemList.size() - count;
		
		Assert.isTrue(bigThan20Count==enumItemService.queryEnumItem4SyncData(20).size());
	}
	
}
