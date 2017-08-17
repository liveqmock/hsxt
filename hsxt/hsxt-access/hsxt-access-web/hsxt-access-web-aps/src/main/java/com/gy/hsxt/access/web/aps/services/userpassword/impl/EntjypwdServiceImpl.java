package com.gy.hsxt.access.web.aps.services.userpassword.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.aps.services.userpassword.EntjypwdService;
import com.gy.hsxt.bs.api.pwd.IBSTransPwdService;
import com.gy.hsxt.bs.bean.pwd.TransPwdQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

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
public class EntjypwdServiceImpl implements EntjypwdService {

	@Autowired
	private IBSTransPwdService bSTransPwdService;

	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		TransPwdQuery condition = new TransPwdQuery();
		 condition.setOptCustId(filterMap.get("custId").toString());
		
		 condition.setEntResNo(filterMap.get("entResNo").toString());
		  condition.setEntCustName(filterMap.get("entCustName").toString());
		 
		return bSTransPwdService.queryTaskListPage(page,
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

	@Autowired
	private IUCAsEntService iuCAsEntService;
	@Autowired
	private IUCAsBankAcctInfoService iuCAsBankInfoService;

	/**
	 * 根据 custId 查询企业的所有信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public AsEntAllInfo findAllByCustId(String custId) throws HsException {

		return iuCAsEntService.searchEntAllInfo(custId);
	}

	/**
	 * 根据 custId 查询企业的重要信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public AsEntMainInfo findMainByCustId(String custId) throws HsException {

		return iuCAsEntService.searchEntMainInfo(custId);
	}

	/**
	 * 根据 custId 查询企业的基本信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public AsEntBaseInfo findBaseByCustId(String custId) throws HsException {
		return iuCAsEntService.searchEntBaseInfo(custId);
	}

	/**
	 * 根据 custId 查询默认银行账户
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@Override
	public List<AsBankAcctInfo> searchListBankAcc(String custId)
			throws HsException {
		String userType = "4";
		return iuCAsBankInfoService.listBanksByCustId(custId, userType);
	}

	/**
	 * 通过企业互生号查询企业客户号
	 * 
	 * @param entResNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	@Override
	public String findEntCustIdByEntResNo(String entResNo) throws HsException {
		return iuCAsEntService.findEntCustIdByEntResNo(entResNo);
	}

}
