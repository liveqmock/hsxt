/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean;

/***
 * 验证码类型
 * 
 * @Package: com.gy.hsxt.access.web.person.common
 * @ClassName: VerificationCodeType
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-30 上午11:43:27
 * @version V1.0
 */
public final class VerificationCodeType {
	/** 交易密码重置申请 */
	public final static String tradePwdResetApply = "tradePwd";

	/** 支付限额设置 */
	public final static String payLimitSetting = "payLimist";

	/** 实名认证 */
	public final static String realNameAuth = "realNameAuth";

	/** 重要信息变更 */
	public final static String importInfoAuth = "importInfoAuth";

	/** 企业报备 */
	public final static String entFiling = "entFiling";

	/** 企业申报 */
	public final static String entApply = "entApply";
}
