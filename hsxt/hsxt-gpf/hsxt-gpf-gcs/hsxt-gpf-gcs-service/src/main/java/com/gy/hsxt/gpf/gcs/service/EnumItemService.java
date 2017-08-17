/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.EnumItem;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IEnumItemService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.EnumItemMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : EnumItemService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 枚举信息接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("enumItemService")
public class EnumItemService implements IEnumItemService {
	
	@Autowired
	private EnumItemMapper enumItemMapper;
	
	@Autowired
	private IVersionService veresionService;
	

	@Override
	@Transactional
	public int addEnumItem(EnumItem enumItem) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_ENUM_ITEM);
		enumItem.setVersion(version);
		
		return enumItemMapper.addEnumItem(enumItem);
	}

	@Override
	@Transactional
	public boolean deleteEnumItem(String languageCode,String groupCode,String itemCode) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_ENUM_ITEM);
		EnumItem enumItem = new EnumItem(languageCode,groupCode,itemCode);
		enumItem.setVersion(version);
		
		return enumItemMapper.deleteEnumItem(enumItem);
	}

	@Override
	@Transactional
	public boolean updateEnumItem(EnumItem enumItem) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_ENUM_ITEM);
		enumItem.setVersion(version);
		return enumItemMapper.updateEnumItem(enumItem);
	}

	@Override
	public List<EnumItem> queryEnumItem(EnumItem enumItem) {
		String itemName = enumItem.getItemName();
		String itemValue = enumItem.getItemValue();
		if(null != itemName && !"".equals(itemName)){
			itemName = itemName.replaceAll("_", "/_").replaceAll("%", "/%");
			enumItem.setItemName(itemName);
		}
		if(null != itemValue &&  !"".equals(itemValue)){
			itemValue = itemValue.replaceAll("_", "/_").replaceAll("%", "/%");
			enumItem.setItemValue(itemValue);
		}
		return enumItemMapper.queryEnumItemListPage(enumItem);
	}

	@Override
	public boolean existEnumItem(String languageCode,String groupCode,String itemCode) {
		boolean isExist = "1".equals(enumItemMapper.existEnumItem(languageCode,groupCode,itemCode));
		return isExist;
	}

	@Override
	public EnumItem queryEnumItemWithPK(String languageCode, String groupCode,
			String itemCode) {
		return enumItemMapper.queryEnumItemWithPK(languageCode, groupCode, itemCode);
	}

	@Override
	public List<EnumItem> queryEnumItemByGroup(String languageCode,
			String groupCode) {
		return enumItemMapper.queryEnumItemByGroup(languageCode, groupCode);
	}

	@Override
	public List<EnumItem> queryEnumItem4SyncData(long version) {
		return enumItemMapper.queryEnumItem4SyncData(version);
	}
}
