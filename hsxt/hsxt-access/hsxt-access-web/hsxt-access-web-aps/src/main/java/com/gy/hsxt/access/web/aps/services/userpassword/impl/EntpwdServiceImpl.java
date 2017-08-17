package com.gy.hsxt.access.web.aps.services.userpassword.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.userpassword.EntpwdService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.userpassword.impl
 * @ClassName: EntpwdServiceImpl
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:25:30
 * @version V3.0
 */
@Service
public class EntpwdServiceImpl implements EntpwdService {

	@Autowired
	private IUCAsEntService uCAsEntService;

	@Autowired
	private IUCAsPwdService uCAsPwdService;

	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		AsQueryBelongEntCondition condition = new AsQueryBelongEntCondition();
		condition.setEntResNo(filterMap.get("pointNo").toString());
		condition.setBelongEntResNo(filterMap.get("belongEntResNo").toString());
		condition.setBelongEntName(filterMap.get("belongEntName").toString());
		return uCAsEntService.listBelongEntInfo(condition, page);
	}

	@Override
	public Object findById(Object id) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(String entityJsonStr) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkVerifiedCode(String custId, String verifiedCode)
			throws HsException {
		// TODO Auto-generated method stub

	}

	public void resetLogPwdForOperatorByReChecker(String regionalResNo,
			String userName, String loginPwd, String secretKey, String resetEntResNo,String operCustId)
			throws HsException {
		uCAsPwdService.resetLogPwdForOperatorByReChecker(regionalResNo,
				userName, loginPwd, secretKey, resetEntResNo,operCustId);
	}
}
