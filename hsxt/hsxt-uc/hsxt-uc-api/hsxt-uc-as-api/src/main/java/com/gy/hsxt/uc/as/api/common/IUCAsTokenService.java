/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 随机token
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsTokenService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午2:27:48
 * @version V1.0
 */
public interface IUCAsTokenService {
	/**
	 * 获取随机token
	 * 
	 * @param custId
	 *            客户号，如果已登录该值必填，如果未登录，该值为空
	 * @return
	 * @throws HsException
	 */
	public String getRandomToken(String custId) throws HsException;

	/**
	 * 验证随机token
	 * @param custId
	 *            客户号，如果已登录该值必填，如果未登录，该值为空
	 * @return
	 */
	public boolean checkRandomToken(String custId, String random);
	
	/**
	 * 验证登录token
	 * @param channelType 渠道类型
	 * @param custId 客户号
	 * @param loginToken 登录token
	 * @return
	 */
	public boolean checkLoginToken(String channelType, String custId, String loginToken);
}
