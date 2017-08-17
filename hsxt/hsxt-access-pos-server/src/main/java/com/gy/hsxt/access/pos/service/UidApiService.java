/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service;

import com.gy.hsxt.common.exception.HsException;

		
/**
 * 获取11位序列号，用于业务流水一部分。
 * @author kend
 *
 */
public interface UidApiService {
	
	/**
	 * 获取11位序列号，用于业务流水一部分。
	 * @param entResNo 企业互生号
	 * @return 11位长的序列号字符串
	 * @throws HsException
	 */
	public String getUid(String entResNo) throws HsException;
}

	