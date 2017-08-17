/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : ILCSBaseInfoCacheService.java
 * 
 *  Creation Date   : 2016年6月13日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : LCS基础
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface ILCSBaseInfoCacheService {

	/**
	 * 根据bankId查询银行名称
	 * 
	 * @param bankId
	 * @return
	 */
	public String queryBankNameFromLCS(String bankId);

	/**
	 * 查询城市名称
	 * 
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	public String queryCityNameFromLCS(String provinceCode, String cityCode);
}
