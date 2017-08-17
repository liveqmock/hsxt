/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.parent;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gy.hsxt.gp.api.IGPNotifyService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl.parent
 * 
 *  File Name       : AbstractAppContextAwareServcie.java
 * 
 *  Creation Date   : 2016年5月6日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 实现spring的ApplicationContextAware
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractAppContextAwareServcie implements
		ApplicationContextAware {

	private static final Map<String, IGPNotifyService> SERVICE_CACHE = new HashMap<String, IGPNotifyService>(
			4);

	private ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

	/**
	 * 根据merType来源选择不同的dubbo服务进行通知
	 * 
	 * @param merType
	 * @return
	 */
	protected IGPNotifyService getNotifyService(String srcSubsysId) {

		IGPNotifyService notifyService = SERVICE_CACHE.get(srcSubsysId);

		// 如果为空, 则获取
		if (null == notifyService) {

			synchronized (SERVICE_CACHE) {
				notifyService = SERVICE_CACHE.get(srcSubsysId);

				if (null == notifyService) {
					// 获取IPGNotifyService对象
					notifyService = ctx.getBean(
							"gpNotifyService" + srcSubsysId.toUpperCase(),
							IGPNotifyService.class);

					SERVICE_CACHE.put(srcSubsysId, notifyService);
				}
			}
		}

		return notifyService;
	}
}
