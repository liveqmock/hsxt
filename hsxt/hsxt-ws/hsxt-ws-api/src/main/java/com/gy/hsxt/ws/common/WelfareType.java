/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.common;

/**
 * 福利类型 枚举
 * 
 * @Package: com.gy.hsxt.ws.common
 * @ClassName: WelfareType
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-7 下午4:55:41
 * @version V1.0
 */
public enum WelfareType {

	/** 福利类型 0 意外伤害 1 免费医疗 2 他人身故 */

	ACCIDENT_SECURITY(0),

	MEDICAL_SUBSIDIES(1),

	DIE_SECURITY(2);

	int type;

	private WelfareType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
