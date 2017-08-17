/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service.impl.parent;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gy.hsxt.pg.api.IPGNotifyService;
import com.gy.hsxt.pg.common.constant.Constant.MerTypeMapping;
import com.gy.hsxt.pg.constant.PGConstant.MerType;
import com.gy.hsxt.pg.mapper.TPgChinapayOrderMapper;
import com.gy.hsxt.pg.mapper.TPgPinganOrderMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service.impl.parent
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

	private static final Map<Integer, IPGNotifyService> SERVICE_CACHE = new HashMap<Integer, IPGNotifyService>(
			4);

	private ApplicationContext ctx;

	@Autowired
	private TPgChinapayOrderMapper payOrderMapper;

	@Autowired
	private TPgPinganOrderMapper pinganOrderMapper;

	static {

	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

	/**
	 * 根据银行订单号获取IPGNotifyService对象
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	protected IPGNotifyService getNotifyServiceForTrans(String bankOrderNo) {

		// 查询商户类型
		int merType = pinganOrderMapper.selectMerTypeByBankOrderNo(bankOrderNo);

		return getNotifyService(merType);
	}

	/**
	 * 根据银行订单号获取IPGNotifyService对象
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	protected IPGNotifyService getNotifyServiceForPay(String bankOrderNo) {

		// 查询商户类型
		int merType = payOrderMapper.selectMerTypeByBankOrderNo(bankOrderNo);

		return getNotifyService(merType);
	}

	/**
	 * 根据merType来源选择不同的dubbo服务进行通知
	 * 
	 * @param merType
	 * @return
	 */
	protected IPGNotifyService getNotifyService(Integer merType) {

		// 如果为空, 默认为互生
		if (null == merType) {
			merType = MerType.HSXT;
		}

		IPGNotifyService pgNotifyService = SERVICE_CACHE.get(merType);

		// 如果为空, 则获取
		if (null == pgNotifyService) {

			synchronized (SERVICE_CACHE) {
				pgNotifyService = SERVICE_CACHE.get(merType);

				if (null == pgNotifyService) {
					// 获取IPGNotifyService对象
					pgNotifyService = ctx.getBean("pgNotifyService_"
							+ MerTypeMapping.getDesc(merType),
							IPGNotifyService.class);

					SERVICE_CACHE.put(merType, pgNotifyService);
				}
			}
		}

		return pgNotifyService;
	}
}
