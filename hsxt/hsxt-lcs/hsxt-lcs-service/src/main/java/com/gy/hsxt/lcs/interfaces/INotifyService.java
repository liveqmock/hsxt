/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.interfaces;

import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-api
 * 
 *  Package Name    : com.gy.hsxt.lcs.api
 * 
 *  File Name       : INotifyService.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 公共参数变更通知接口,由地区配置中心提供，用于全局配置中心通知地区配置中心数据已变化,需要同步更新
 * 
 * 
 *  History         :  
 * 
 * </PRE>
 ***************************************************************************/
public interface INotifyService {
	/**
	 * 公共参数变更通知
	 * 
	 * @param tableCode
	 *            配置表代码
	 * @param version
	 *            配置表新版本号
	 * @throws HsException
	 */
	void notifyChange(String tableCode, Long version) throws HsException;
}
