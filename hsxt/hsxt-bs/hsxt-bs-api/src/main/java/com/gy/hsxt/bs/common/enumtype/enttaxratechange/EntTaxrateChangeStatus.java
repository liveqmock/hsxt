/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.enttaxratechange;

/**
 * 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.enttaxratechange
 * @ClassName: EntTaxrateChangeStatus
 * @Description: 企业税率调整申请状态 枚举类
 * 
 * @author: liuhq
 * @date: 2015-9-6 下午5:53:12
 * @version V1.0
 */
public enum EntTaxrateChangeStatus {
	/**
	 * 未审批
	 */
	WITHOUT(0),
	/**
	 * 地区平台初审通过
	 */
	INITIAL(1),
	/**
	 * 地区平台复核通过
	 */
	REVIEW(2),
	/**
	 * 驳回（初审/复核未通过）
	 */
	REJECT(3);
	private int code;

	public int getCode() {
		return code;
	}

	EntTaxrateChangeStatus(int code) {
		this.code = code;
	}

	/**
	 * 校验审批状态类型
	 *
	 * @param code 状态代码
	 * @return boolean
	 */
	public static boolean checkStatus(int code) {
		for (EntTaxrateChangeStatus apprStatus : EntTaxrateChangeStatus.values()) {
			if (apprStatus.getCode() == code) {
				return true;
			}
		}
		return false;
	}
}
