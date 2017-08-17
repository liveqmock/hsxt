package com.gy.hsi.nt.api.common;

/**
 * 
 * @className:RespCode
 * @author:likui
 * @date:2015年7月28日
 * @desc:调用通知系统返回代码
 * @company:gyist
 */
public enum RespCode
{
	/** 成功 **/
	SUCCESS,

	/** 失败 **/
	FAIL,

	/** 错误 **/
	ERROR,

	/** 参数为空 **/
	RARAM_IS_NULL,

	/** 通知系统异常 **/
	SYSTEM_ERROR,

	/** 参数错误 **/
	RARAM_ERROR,

	/** 未获取到模板 **/
	TEMP_NOT_FIND,

	/**  **/
	REPLACE_PLACEHOLDER_ERROR,

	/** 模板内容为空 **/
	TEMP_CONTENT_NULL,

	/** 没有配置参数 **/
	NOT_CONFIG_SYSPAR,

	/** 互动消息类型错误 **/
	DYNAMIC_MSG_TYEP_ERROR,

	/** 手机错误 **/
	PHONE_ERROR,

	/** 邮箱错误 **/
	EMAIL_ERROR,

	/** 接收人为空 **/
	RECEIVERS_IS_NULL;
}
