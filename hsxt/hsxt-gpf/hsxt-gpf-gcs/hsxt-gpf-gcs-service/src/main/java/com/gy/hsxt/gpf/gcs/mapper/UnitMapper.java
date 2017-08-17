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

import com.gy.hsxt.gpf.gcs.bean.Unit;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : UnitMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 计量单位mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("UnitMapper")
public interface UnitMapper {
	
	public int addUnit(Unit unit);

	public boolean deleteUnit(Unit unit);

	public boolean updateUnit(Unit unit);

	public Unit queryUnitWithPK(@Param("languageCode")String languageCode,@Param("unitCode")String unitCode);

	public String existUnit(@Param("languageCode")String languageCode,@Param("unitCode")String unitCode);

	public List<Unit> queryUnitListPage(Unit unit);
	
	public List<Unit> queryUnitByLanguage(@Param("languageCode")String languageCode);

	public List<Unit> queryUnit4SyncData(@Param("version")long version);

}
