/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.bean;

/**
 * 搜索使用的用户类型枚举
 * 
 * @Package: com.gy.hsxt.uc.search.bean
 * @ClassName: SearchUserTypeEnum
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-1-6 下午6:20:04
 * @version V1.0
 */
public enum SearchUserTypeEnum {
	/** 非持卡人 */
	NO_CARDER(0), 
	/** 持卡人 */
	CARDER(1), 
	/** 成员企业 */
	ENT_MEMEBER(2), 
	/** 托管企业 */
	ENT_TRUSTTEE(3),
	/** 服务公司 */
	ENT_SVR(4), 
	/** 管理公司 */
	ENT_MGT(5), 
	/** 所有类型的用户，包括消费者；成员企业、托管企业、服务公司、管理公司的超级管理员 */
	ALL(-1),
	/** 消费者，包括持卡人和非持卡人 */
	CONSUMER(-2),
	/** 成员企业和托管企业 */
	ENT(-3),
	;
	private int type;

	SearchUserTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
