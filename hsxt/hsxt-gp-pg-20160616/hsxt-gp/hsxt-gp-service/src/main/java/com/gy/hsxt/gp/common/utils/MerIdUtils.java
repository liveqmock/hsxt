/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.utils;

import com.gy.hsxt.gp.constant.GPConstant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.check
 * 
 *  File Name       : MerIdCheck.java
 * 
 *  Creation Date   : 2015-10-13
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 商户号转换工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class MerIdUtils {

	/**
	 * 将merId的商户号转为商户类型
	 * 
	 * @param merId
	 * @return
	 */
	public static int merId2Mertype(String merId) {
		int merType = GPConstant.MerType.HSXT;
		
		if (merId.equalsIgnoreCase(GPConstant.MerId.HSEC)) {
			merType = GPConstant.MerType.HSEC;
		}
		
		if (merId.equalsIgnoreCase(GPConstant.MerId.HS)) {
			merType = GPConstant.MerType.HSXT;
		}
		
		return merType;
	}
}
