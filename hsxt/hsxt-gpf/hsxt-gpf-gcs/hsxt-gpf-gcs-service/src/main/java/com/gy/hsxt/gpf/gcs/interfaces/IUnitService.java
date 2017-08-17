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

import com.gy.hsxt.gpf.gcs.bean.Unit;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : UnitService.java
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
	 * 添加计量单位
	 * 
	 * @param unit
	 * @return
	 */
	@Transactional
	public int addUnit(Unit unit);

	/**
	 * 删除计量单位，delFlag = 1
	 * 
	 * @param unit
	 * @return
	 */
	@Transactional
	public boolean deleteUnit(String languageCode,String unitCode);

	/**
	 * 修改计量单位
	 * 
	 * @param unit
	 * @return
	 */
	@Transactional
	public boolean updateUnit(Unit unit);

	/**
	 * 查询计量单位
	 * 
	 * @param unit
	 * @return
	 */
	public Unit queryUnitWithPK(String languageCode,String unitCode);

	/**
	 * 查询计量单位
	 * 
	 * @param unit
	 * @return
	 */
	public List<Unit> queryUnit(Unit unit);

	/**
	 * 判断是否存一样的计量单位
	 * 
	 * @param unit
	 * @return
	 */
	public boolean existUnit(String languageCode,String unitCode);
	
	/**
	 * 查询一种语言下的所有计量单位
	 * @param languageCode
	 * @return
	 */
	public List<Unit> queryUnitByLanguage(String languageCode);

	/**
	 * 查询出版本号大于子平台的所有计量单位
	 * @param version
	 * @return
	 */
	public List<Unit> queryUnit4SyncData(Long version);
	
}
