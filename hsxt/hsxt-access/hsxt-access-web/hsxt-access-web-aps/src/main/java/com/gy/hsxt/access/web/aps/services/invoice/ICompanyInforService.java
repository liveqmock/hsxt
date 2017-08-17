package com.gy.hsxt.access.web.aps.services.invoice;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.invoice
 * @className : ICompanyInforService.java
 * @description : 企业信息查询，银行账号查询接口类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface ICompanyInforService extends IBaseService {
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
	public AsBankAcctInfo searchDefaultBankAcc(String custId)
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
	/**
     * 查询企业状态信息
     * @param custId 企业id
     * @return
     * @throws HsException
     */
	public AsEntStatusInfo searchEntStatusInfo(String custId) throws HsException;

	public AsEntMainInfo findMainByResNo(String resNo) throws HsException;
}
