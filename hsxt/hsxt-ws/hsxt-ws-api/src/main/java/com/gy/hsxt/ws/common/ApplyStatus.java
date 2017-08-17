/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.common;

/**
 * 积分福利申请状态
 * 
 * @Package: com.gy.hsxt.ws.common
 * @ClassName: ApplyStatus
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-9 下午2:52:00
 * @version V1.0
 */
public enum ApplyStatus {

	/** 申请状态 0 受理中 1受理成 2 驳回 */

	PROCESSING(0),

	SUCCESS(1),

	REJECT(2);

	int status;

	private ApplyStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
