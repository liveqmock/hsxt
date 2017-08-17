/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.EnumItem;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IEnumItemService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.EnumItemMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
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
    public List<EnumItem> queryEnumItemByGroupExclude(String languageCode, String groupCode,
            List<String> excludeItemCodes) {
        if(null != excludeItemCodes && excludeItemCodes.size()>0){
            return enumItemMapper.queryEnumItemByGroupExclude(languageCode, groupCode,excludeItemCodes);
        }else{
            return enumItemMapper.queryEnumItemByGroup(languageCode, groupCode);
        }
        
    }

    @Override
    public List<EnumItem> queryEnumItemByGroupInclude(String languageCode, String groupCode,
            List<String> includeItemCodes) {
        if(null != includeItemCodes && includeItemCodes.size()>0){
            return enumItemMapper.queryEnumItemByGroupInclude(languageCode, groupCode,includeItemCodes);
        }else{
            return enumItemMapper.queryEnumItemByGroup(languageCode, groupCode);
        }
    }
    
    @Override
    @Transactional
    public int addOrUpdateEnumItem(List<EnumItem> list, long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_ENUM_ITEM,version,true));
        List<EnumItem> enumItemListAdd = new ArrayList<EnumItem>();
        List<EnumItem> enumItemListUpdate = new ArrayList<EnumItem>();
        for(EnumItem obj : list){
            if("1".equals(enumItemMapper.existEnumItem(obj.getLanguageCode(), obj.getGroupCode(), obj.getItemCode()))){
                enumItemListUpdate.add(obj);
            }else{
                enumItemListAdd.add(obj);
            }
        }
        
        if(enumItemListUpdate.size()>0){
            enumItemMapper.batchUpdate(enumItemListUpdate);
        }
        
        if(enumItemListAdd.size()>0){
            enumItemMapper.batchInsert(enumItemListAdd);
        }
        
        return 1;
    }

}
