package com.gy.hsxt.access.web.company.services.systemBusiness;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 
 * @projectName : hsxt-access-web-company
 * @package : com.gy.hsxt.access.web.company.services.systemBusiness
 * @className : ICompanyInforService.java
 * @description : 企业信息查询接口
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface ICompanyInforService extends IBaseService {

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
	 * 根据 resNo 查询企业基础信息
	 * 
	 * @param entCustId
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	public AsEntBaseInfo findBaseInfoCustId(String entCustId) throws HsException;

	/**
	 * 通过消费者互生号查询消费者客户号
	 * 
	 * @param perResNo
	 *            :消费者互生号
	 * @param request
	 * @return
	 */
	public String findCustIdByResNo(String perResNo) throws HsException;

	/**
	 * 
	 * @param custId
	 *            :客户号
	 * @param loginPwd
	 *            :登陆密码(AES加密后的密文)
	 * @param secretKey
	 *            :AES秘钥（随机报文校验token)
	 */
	public void checkLoginPwd(String custId, String loginPwd, String secretKey)
			throws HsException;
	/**
	 * 根据 resNo 查询企业的重要信息
	 * 
	 * @param resNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	public AsEntMainInfo findMainByResNo(String resNo) throws HsException;
}
