/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.gy.hsxt.lcs.bean.EnumItem;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.gl.mapper
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
public interface EnumItemMapper {
	
	public String existEnumItem(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode,@Param("itemCode")String itemCode);

	public EnumItem queryEnumItemWithPK(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode,@Param("itemCode")String itemCode);
	
	public List<EnumItem> queryEnumItemByGroup(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode);
	
	public List<EnumItem> queryEnumItemByGroupInclude(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode,@Param("includeTtemCodes")List<String> includeTtemCodes);
	
    public List<EnumItem> queryEnumItemByGroupExclude(@Param("languageCode")String languageCode,@Param("groupCode")String groupCode,@Param("excludeItemCodes")List<String> excludeItemCodes);
	
	public void batchUpdate(List<EnumItem> enumItemListUpdate);

	public void batchInsert(List<EnumItem> enumItemListAdd);

}
