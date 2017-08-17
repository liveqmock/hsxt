/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.interfaces;

import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-api
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.api
 * 
 *  File Name       : ISyncDataService.java
 * 
 *  Creation Date   : 2015-7-2
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 增量配置数据查询服务接口，全局配置中心提供
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface ISyncDataService {
	/**
	 * 
	 * @param tableCode
	 *            配置表代码
	 * @param version
	 *            本地版本号
	 * @return 返回结果数据的Json字符串
	 * @throws HsException
	 */
	String querySyncData(String tableCode, Long version) throws HsException;
}
