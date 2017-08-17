/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.pos.service.AcApiService;
import com.gy.hsxt.common.exception.HsException;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service.impl  
 * @ClassName: AcApiServiceImpl 
 * @Description: 
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:13:25 
 * @version V1.0
 */
@Service("acApiService")
public class AcApiServiceImpl implements AcApiService {
	
	@Autowired
	@Qualifier("accountSearchService")
	private IAccountSearchService accountSearchService;

	@Override
	public AccountBalance searchAccBalance(String custId, String itemType)
			throws HsException {
	    SystemLog.debug("POS 账户查询接口", "POS accountSearchService.searchAccBalance 请求参数", custId+"  "+itemType);
	    AccountBalance ab = accountSearchService.searchAccBalance(custId, itemType);
        SystemLog.debug("POS 账户查询接口", "POS accountSearchService.searchAccBalance 返回结果", JSON.toJSONString(ab));
		return ab;
	}

	@Override
	public AccountBalance searchAccNormal(String custID, String accType)
			throws HsException {
	    SystemLog.debug("POS 账户查询接口", "POS accountSearchService.searchAccNormal 请求参数", custID+"  "+accType);
        AccountBalance ab = accountSearchService.searchAccNormal(custID, accType);
        SystemLog.debug("POS 账户查询接口", "POS accountSearchService.searchAccNormal 返回结果", JSON.toJSONString(ab));
		return ab;
	}

    @Override
    public Map<String,AccountBalance> searchAccBalanceByAccCategory(String custId, int accCategory) throws HsException {
        SystemLog.debug("POS 账户查询接口", "POS accountSearchService.searchAccBalanceByAccCategory 请求参数", custId+"  "+accCategory);
        Map<String,AccountBalance> map = accountSearchService.searchAccBalanceByAccCategory(custId, accCategory);
        SystemLog.debug("POS 账户查询接口", "POS accountSearchService.searchAccBalanceByAccCategory 返回结果", JSON.toJSONString(map));
        return map;
    }
	
	
}

	