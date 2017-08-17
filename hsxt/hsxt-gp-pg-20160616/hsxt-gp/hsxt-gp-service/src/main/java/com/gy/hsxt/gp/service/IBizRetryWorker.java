/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service;

import com.gy.hsxt.gp.common.bean.GPCountDownLatch;
import com.gy.hsxt.gp.mapper.vo.TGpRetry;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service
 * 
 *  File Name       : IBizRetryWorker.java
 * 
 *  Creation Date   : 2015年12月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 重试工作者接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IBizRetryWorker {

	/**
	 * 处理重试任务
	 * 
	 * @param retry
	 * @param latch
	 * @return
	 */
	public boolean handleRetryTask(TGpRetry retry, GPCountDownLatch latch);
}
