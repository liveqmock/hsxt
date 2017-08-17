/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.EnumItem;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : EnumItemMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 枚举信息mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("EnumItemMapper")
public interface EnumItemMapper {
	
	public int addEnumItem(EnumItem enumItem);

	public boolean deleteEnumItem(EnumItem enumItem);

	public boolean updateEnumItem(EnumItem enumItem);

	public String existEnumItem(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode,@Param("itemCode")String itemCode);

	public EnumItem queryEnumItemWithPK(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode,@Param("itemCode")String itemCode);
	
	public List<EnumItem> queryEnumItemListPage(EnumItem enumItem);
	
	public List<EnumItem> queryEnumItemByGroup(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode);
	
	public List<EnumItem> queryEnumItem4SyncData(@Param("version")long version);

}
