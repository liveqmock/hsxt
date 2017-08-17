/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.enumerate;

/**
 * 企业基本状态 枚举
 * 
 * 基本状态 1：正常 成员企业、托管企业 2：预警 成员企业、托管企业 3：休眠 成员企业 4：长眠 成员企业 5：已注销 成员企业 6：申请停止积分活动中
 * 托管企业 7：停止积分活动 托管企业 8：注销申请中 成员企业
 * 
 * @Package:
 * @ClassName: BsEntBaseStatusEumn
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-26 下午4:12:43
 * @version V1.0
 */
public enum BsEntBaseStatusEumn {
	/** 正常 */
	NORMAL(1),
	/** 预警 */
	WARN(2),
	/** 休眠 */
	DORMANT(3),
	/** 正常 */
	SLEEP(4),
	/** 已注销 */
	CANCELED(5),
	/** 申请停止积分活动中 */
	APPLY_POINT(6),
	/** 停止积分活动 */
	STOP_POINT(7),
	/** 注销申请中 */
	APPLY_CANCEL(8);

	int status;

	BsEntBaseStatusEumn(int status) {
		this.status = status;
	}

	public int getstatus() {
		return status;
	}

}
