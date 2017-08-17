/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.common;

/**
 * 积分福利发放状态
 * 
 * @Package: com.gy.hsxt.ws.common
 * @ClassName: GrantStatus
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-15 下午6:45:40
 * @version V1.0
 */
public enum GrantStatus {
	/** 申请状态 0 未发放 1、已发放 */

	PENDING(0),

	GRANT_OVER(1);

	int status;

	private GrantStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
