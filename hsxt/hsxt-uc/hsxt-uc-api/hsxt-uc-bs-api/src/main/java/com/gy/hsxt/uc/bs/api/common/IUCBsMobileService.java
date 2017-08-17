/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.api.common;

import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 手机短信操作处理类
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsMobileService
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午2:29:43
 * @version V1.0 
 */
public interface IUCBsMobileService {

	/**
	 * 企业重置交易密码（重置申请成功）,发送短信验证码（发送授权码）
	 * @param entResNo 企业互生号
	 * @throws HsException
	 */
	public void sendAuthCodeSuccess(String entResNo,Integer custType) throws HsException;
}
