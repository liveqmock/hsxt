/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.EnumItem;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IEnumItemService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 枚举信息接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IEnumItemService {
	
	/**
	 * 查询枚举信息
	 * @param languageCode
	 * @param groupCode
	 * @param itemCode
	 * @return
	 */
	public EnumItem queryEnumItemWithPK(String languageCode,String groupCode,String itemCode);
	
	/**
	 * 通过组查询枚举信息
	 * @param languageCode
	 * @param groupCode
	 * @return
	 */
	public List<EnumItem> queryEnumItemByGroup(String languageCode,String groupCode);
	
	/**
     * 通过组查询枚举信息,过滤掉excludeItemCodes
     * @param languageCode
     * @param groupCode
     * @param excludeItemCodes
     * @return
     */
    public List<EnumItem> queryEnumItemByGroupExclude(String languageCode, String groupCode,
            List<String> excludeItemCodes);
    
    /**
     * 通过组查询枚举信息，只包含includeItemCodes
     * @param languageCode
     * @param groupCode
     * @param includeItemCodes
     * @return
     */
    public List<EnumItem> queryEnumItemByGroupInclude(String languageCode, String groupCode,
            List<String> includeItemCodes);
	
	/**
	 * 插入或更新枚举信息
	 * @param list
	 * @param version
	 * @return
	 */
	public int addOrUpdateEnumItem(List<EnumItem> list,long version);

}
