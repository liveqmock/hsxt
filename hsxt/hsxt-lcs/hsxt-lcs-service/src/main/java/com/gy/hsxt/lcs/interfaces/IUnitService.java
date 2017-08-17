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

import com.gy.hsxt.lcs.bean.Unit;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IUnitService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 计量单位接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IUnitService {

	/**
	 * 查询计量单位
	 * 
	 * @param unit
	 * @return
	 */
	public Unit queryUnitWithPK(String languageCode,String unitCode);
	
	/**
	 * 查询一种语言下的所有计量单位
	 * @param languageCode
	 * @return
	 */
	public List<Unit> queryUnitByLanguage(String languageCode);
	
	/**
	 * 插入或更新计量单位
	 * @param list
	 * @param version
	 * @return
	 */
	public int addOrUpdateUnit(List<Unit> list,Long version);

}
