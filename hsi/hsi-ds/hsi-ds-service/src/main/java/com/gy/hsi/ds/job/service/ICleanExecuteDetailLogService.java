/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.service;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-param
 * 
 *  Package Name    : com.gy.hsi.ds.job.service
 * 
 *  File Name       : ICleanExecuteDetailLogService.java
 * 
 *  Creation Date   : 2016年1月29日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : 清理过期日志
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface ICleanExecuteDetailLogService {
	public void cleanGarbageDatas();
}
