package com.gy.hsxt.access.web.aps.services.userpassword.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.userpassword.EntjylistpwdService;
import com.gy.hsxt.bs.api.pwd.IBSTransPwdService;
import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.bs.bean.pwd.TransPwdQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.userpassword.impl
 * @ClassName: EntjypwdServiceImpl
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:25:17
 * @version V3.0
 */
@Service
public class EntjylistpwdServiceImpl implements EntjylistpwdService {

	@Autowired
	private IBSTransPwdService bSTransPwdService;

	@SuppressWarnings("rawtypes")
    @Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		TransPwdQuery condition = new TransPwdQuery();
		// condition.setOptCustId(filterMap.get("custId").toString());
		
		 condition.setEntResNo(filterMap.get("entResNo").toString());
		 condition.setEntCustName(filterMap.get("entCustName").toString());
		
		return bSTransPwdService.queryResetTransPwdApplyListPage(page,
				condition);
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

	public void apprResetTranPwdApply(ResetTransPwd resetTransPwd) throws HsException {
		bSTransPwdService.apprResetTranPwdApply(resetTransPwd);
	}

}
