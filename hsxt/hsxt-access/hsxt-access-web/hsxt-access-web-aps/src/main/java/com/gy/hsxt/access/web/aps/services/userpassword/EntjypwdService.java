package com.gy.hsxt.access.web.aps.services.userpassword;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.userpassword
 * @ClassName: EntjypwdService
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:26:07
 * @version V3.0
 */
public interface EntjypwdService extends IBaseService {

	/**
	 * 根据 custId 查询企业的所有信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	public AsEntAllInfo findAllByCustId(String custId) throws HsException;

	/**
	 * 根据 custId 查询企业的重要信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	public AsEntMainInfo findMainByCustId(String custId) throws HsException;

	/**
	 * 根据 custId 查询企业的基本信息
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	public AsEntBaseInfo findBaseByCustId(String custId) throws HsException;

	/**
	 * 根据 custId 查询默认银行账户
	 * 
	 * @param custId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	public List<AsBankAcctInfo> searchListBankAcc(String custId)
			throws HsException;

	/**
	 * 通过企业互生号查询企业客户号
	 * 
	 * @param entResNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	public String findEntCustIdByEntResNo(String entResNo) throws HsException;
}
