/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.bs.api.common;

import com.gy.hsxt.common.exception.HsException;

/**
 * 密码管理处理接口
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsPwdService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-25 上午11:55:32
 * @version V1.0
 */
public interface IUCBsPwdService {
	
	
	/**
	 * 地区平台重置企业的交易密码
	 * @param apsResNo	地区平台互生号
	 * @param userName	复核员的账号
	 * @param loginPwd	复核员的AES登录密码
	 * @param secretKey	AES秘钥
	 * @param entResNo	被重置交易密码的企业互生号
	 * @param operCustId  当前登录的地区平台的操作员
	 * @throws HsException
	 */
	public void resetEntTradePwdByReChecker(String apsResNo,
			String userName, String loginPwd, String secretKey,
			String entResNo, String operCustId) throws HsException;
}
