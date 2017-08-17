/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tc.enums;

/**
 * 
 * 
 * @Package: com.gy.hsxt.tc.enums
 * @ClassName: TcErrorCode
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 上午10:48:30
 * @version V1.0
 */
public enum TcErrorCode {

	PARAM_IS_REQUIRED(34002, "必传参数为空或null"),

	PARAM_IS_ILLEGAL(34003, "非法参数值"),

	DATA_NO_FOUND(34004, "未查询到相关记录"),

	DATE_IS_NOT_FORMAT(34005, "日期格式不正确"),

	RES_NO_IS_WRONG(34006, "互生号错误，无法判断其类型"),

	USER_NOT_FOUND(34007, "用户未找到"),

	CHECK_BALANCE_STATUS_NOT_SUCCESS(34008, "调账申请的状态不为可复核状态，不能进行复核"), 
	
	CHECK_BALANCE_STATUS_NOT_INIT(
			34009, "调账申请的状态不为待初审状态，不能进行初审"), ;

	int code;
	String desc;

	private TcErrorCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
