/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.api.common.IUCBsMobileService;

/**
 * 
 * 手机相关接口管理类（供BS业务系统调用）
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCBsMobileService
 * @Description: 手机相关接口管理类（供BS业务系统调用）
 * 
 * @author: tianxh
 * @date: 2015-12-14 下午8:25:39
 * @version V1.0
 */
@Service
public class UCBsMobileService implements IUCBsMobileService {

	@Autowired
	private UCAsMobileService mobileService;

	/**
	 * 发送企业授权码
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.common.IUCBsMobileService#sendAuthCodeSuccess(java.lang.String)
	 */
	@Override
	public void sendAuthCodeSuccess(String entResNo,Integer custType) throws HsException {
		mobileService.sendAuthCodeSuccess(entResNo, custType);
	}

}
