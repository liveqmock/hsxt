/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.common;

/**
 * 错误码枚举
 * 
 * @Package: com.gy.hsxt.ws.common
 * @ClassName: WsErrorCode
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-7 下午2:17:06
 * @version V1.0
 */
public enum WsErrorCode {
	DATA_COVERT_ERROR(24000, "数据转换错误"),

	PARAM_IS_NULL(24001, "必传参数为空"),

	TASK_NOT_EXIST(24002, "工单不存在"),

	APPROVAL_NOT_EXIST(24003, "审批单不存在"),

	GRANT_NOT_EXIST(24004, "福利发放单不存在"),

	FILE_NOT_FOUND(24005, "文件不存在"),

	READ_FILE_FAIL(24006, "读取文件失败"),

	FILE_COTENT_FOMART_ERROR(24007, "文件内容格式不正确"),

	NO_QUALIFY_TO_APPLY(24008, "没有资格申请福利"),

	INVOKE_TM_SYS_ERROR(24009, "调用工单系统出错"),

	INVOKE_AC_SYS_ERROR(24010, "调用账务系统出错"),

	APPLY_NOT_EXIST(24011, "申请单不存在"),

	APPLYING_EXIST(24012, "已存在相同的申请单"),

	APPLY_PERSON_IS_SAME_OF_DEATH_PERSION(24013, "申请人跟身故人为同一人"),

	SELF_APPLYING_DEATH(24014, "您已经在帮身故人申请身故保障金了，申请单在受理中"),

	OTHER_APPLYING_DEATH(24015, "已有其他人在帮身故人申请身故保障金了，申请单在受理中"),

	DEATH_APPLY_PASS(24016, "身故人的保障金已经受理成功，不能重复受理"),

	APPROVAL_AMOUNT_MORE_THAN_THRESHOLD(24017, "发放金额超过阀值"),

	;
	int code;
	String desc;

	private WsErrorCode(int code, String desc) {
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
