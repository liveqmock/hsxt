/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.interfaces;

import com.gy.hsxt.gp.bean.PaymentOrderState;

/**
 * 支付结果业务处理接口类
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: IPaymentNotifyService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-8 下午6:47:04
 * @version V3.0.0
 */
public interface IPaymentNotifyService {

	/**
	 * 更新支付结果方法
	 * 
	 * @param paymentOrderState
	 *            支付结果实体
	 * @return true or false
	 */
	public boolean updatePayStatus(PaymentOrderState paymentOrderState);

}
