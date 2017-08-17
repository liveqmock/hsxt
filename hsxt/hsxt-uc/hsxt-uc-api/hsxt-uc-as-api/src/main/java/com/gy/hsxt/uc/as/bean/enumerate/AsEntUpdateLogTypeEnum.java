/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.enumerate;

/**
 * 企业信息修改类型
 * 0删除银行账号，1新增银行账号，2修改注册信息，3修改工商信息，4修改联系信息，5修改上传资料
 * 
 * @Package: com.gy.hsxt.uc.as.bean.enumerate
 * @ClassName: AsEntUpdateLogTypeEnum
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-3-3 上午9:54:21
 * @version V1.0
 */
public enum AsEntUpdateLogTypeEnum {

	// 类型:0删除银行账号，1新增银行账号，2修改注册信息，3修改工商信息，4修改联系信息，5修改上传资料
	/** 0删除银行账号 */
	DEL_ACC,
	/** 1新增银行账号 */
	ADD_ACC,
	/** 2修改注册信息 */
	UPDATE_REG,
	/** 3修改工商信息 */
	UPDATE_IC,
	/** 4修改联系信息 */
	UPDATE_CONTACT,
	/** 5修改上传资料 */
	UPDATE_DOC;

}
