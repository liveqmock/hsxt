/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.EnumItem;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : EnumItemService.java
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
	 * 添加枚举信息
	 * 
	 * @param EnumItem
	 * @return
	 */
	@Transactional
	public int addEnumItem(EnumItem enumItem);

	/**
	 * 删除枚举信息，delFlag = 1
	 * 
	 * @param EnumItem
	 * @return
	 */

	@Transactional
	public boolean deleteEnumItem(String languageCode,String groupCode,String itemCode);

	/**
	 * 修改枚举信息
	 * 
	 * @param EnumItem
	 * @return
	 */
	@Transactional
	public boolean updateEnumItem(EnumItem enumItem);

	/**
	 * 查询枚举信息
	 * 
	 * @param EnumItem
	 * @return
	 */
	public List<EnumItem> queryEnumItem(EnumItem enumItem);

	/**
	 * 判断是否存一样的枚举信息
	 * 
	 * @param EnumItem
	 * @return
	 */
	public boolean existEnumItem(String languageCode,String groupCode,String itemCode);
	
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
	 * 查询出版本号大于子平台的所有枚举信息
	 * @param version
	 * @return
	 */
	public List<EnumItem> queryEnumItem4SyncData(long version);
	
}
