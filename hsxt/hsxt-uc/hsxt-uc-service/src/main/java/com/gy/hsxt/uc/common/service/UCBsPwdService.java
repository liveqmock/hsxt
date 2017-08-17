package com.gy.hsxt.uc.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.api.common.IUCBsPwdService;

public class UCBsPwdService implements IUCBsPwdService {
	@Autowired
	UCAsPwdService pwdService;
	@Override
	public void resetEntTradePwdByReChecker(String apsResNo, String userName,
			String loginPwd, String secretKey, String entResNo,
			String operCustId) throws HsException {
		pwdService.resetEntTradePwdByReChecker(apsResNo, userName, loginPwd, secretKey, entResNo, operCustId);
	}
}
