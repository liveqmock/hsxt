package com.gy.hsxt.access.web.aps.services.userpassword.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.userpassword.ConsumerPwdService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.consumer.AsConsumerInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.userpassword.impl
 * @ClassName: ConsumerPwdServiceImpl
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:24:59
 * @version V3.0
 */
@Service
public class ConsumerPwdServiceImpl implements ConsumerPwdService {

	@Autowired
	private IUCAsCardHolderService uCAsCardHolderService;

	@Autowired
	private IUCAsPwdService uCAsPwdService;

	@Override
	public void checkVerifiedCode(String arg0, String arg1) throws HsException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object findById(Object arg0) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageData<AsConsumerInfo> findScrollResult(Map paramMap, Map sortMap,
			Page page) throws HsException {
		AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
		String perResNo = paramMap.get("perResNo").toString();
		String realName = paramMap.get("realName").toString();
		if(StringUtils.isNotBlank(perResNo)){
			condition.setPerResNo(perResNo);
		}
		if(StringUtils.isNotBlank(realName)){
			condition.setRealName(realName);
		}
		PageData<AsConsumerInfo> data = uCAsCardHolderService.listAllConsumerInfo(condition, page);
		return data;
//		return uCAsCardHolderService.listConsumerInfo(condition, page);
	}

	public String save(String arg0) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetLogPwdForCarderByReChecker(String regionalResNo,
			String userName, String loginPwd, String secretKey, String perCustId,String operCustId)
			throws HsException {
		uCAsPwdService.resetLogPwdForCarderByReChecker(regionalResNo, userName,
				loginPwd, secretKey, perCustId,operCustId);
	}

	public void resetTradePwdForCarderByReChecker(String regionalResNo,
			String userName, String loginPwd, String secretKey, String perCustId,String operCustId)
			throws HsException {
		uCAsPwdService.resetTradePwdForCarderByReChecker(regionalResNo,
				userName, loginPwd, secretKey, perCustId,operCustId);
	}

}
