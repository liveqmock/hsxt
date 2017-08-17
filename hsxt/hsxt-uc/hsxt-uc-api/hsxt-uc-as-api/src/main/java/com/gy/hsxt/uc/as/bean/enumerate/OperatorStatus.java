/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.enumerate;

/**
 * 操作员状态
 * 
 * @Package: com.gy.hsxt.uc.as.bean.enumerate
 * @ClassName: OperatorStatus
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-13 上午11:04:33
 * @version V1.0
 */
public enum OperatorStatus {

	// 操作员状态 0：启用，1：禁用 2:已删除

	ACTIVE(0), DISABLE(1), DELETED(2);

	int status;

	OperatorStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
