/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tempacct;

/**
 * 付款方式 枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tempacct
 * @ClassName: PayType
 * @Description: 付款方式 枚举类
 * 
 *               1：POS机刷卡 2：银行汇款 3：现金付款
 * @author: liuhq
 * @date: 2015-9-8 上午10:26:01
 * @version V1.0
 */
public enum PayType {
	/**
	 * POS机刷卡
	 */
	POS(1),
	/**
	 * 银行汇款
	 */
	REMITTANCE(2),
	/**
	 * 现金付款
	 */
	CASH(3);
	private int code;

	public int getCode() {
		return code;
	}

	PayType(int code) {
		this.code = code;
	}

	/**
	 * 判断是否为支付类型
	 *
	 * @param code 类型代码
	 * @return boolean
	 */
	public static boolean checkType(Integer code) {
		if (code == null) return false;
		for (PayType payType : PayType.values()) {
			if (payType.getCode() == code) {
				return true;
			}
		}
		return false;
	}
}
