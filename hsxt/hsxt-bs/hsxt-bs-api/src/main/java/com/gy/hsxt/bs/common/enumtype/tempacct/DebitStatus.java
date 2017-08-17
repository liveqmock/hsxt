/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tempacct;

/**
 * 临帐状态
 * 
 * @Package : com.gy.hsxt.bs.common.enumtype.tempacct
 * @ClassName : DebitStatus
 * @Description : 临帐状态
 * 
 *               0：待审核 1：待关联 2：关联待审核 3：已关联 4：审核驳回 5：待退款 6：已退款 7：不动款
 * @Author  : liuhq
 * @Date  : 2015-9-8 上午9:40:00
 * @version V1.0
 */
public enum DebitStatus {
	/**
	 * 待审核
	 */
	PENDING,
	/**
	 * 待关联-- 有金额可用
	 */
	LINKABLE,
	/**
	 * 关联待审核
	 */
	LINK_PENDING,
	/**
	 * 已关联-- 金额全部用完
	 */
	OVER,
	/**
	 * 审核驳回
	 */
	REJECTED,
	/**
	 * 待退款
	 */
	REFUNDING,
	/**
	 * 已退款
	 */
	REFUNDED,
	/**
	 * 不动款
	 */
	FROZEN;


	/**
	 * 检查是否为临账状态
	 *
	 * @param code 状态代码
	 * @return boolean
	 */
	public static boolean checkStatus(int code) {
		for (DebitStatus status : DebitStatus.values()) {
			if (status.ordinal() == code) {
				return true;
			}
		}
		return false;
	}

}
