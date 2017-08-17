package com.gy.hsxt.access.web.person.services.hsc;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;

		

/***************************************************************************
 * <PRE>
 * Description 		:  查询实名注册信息服务类
 *
 * Project Name   	: hsxt-access-web-person
 *
 * Package Name     : com.gy.hsxt.access.web.person.hsc.service
 *
 * File Name        : IAccountService
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-8-7 下午6:24:55  
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-8-7 下午6:24:55  
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
@Service
public interface ICardHolderAuthInfoService extends IBaseService{
	/**
	 * 查询实名注册信息
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	public AsRealNameReg searchRealNameRegInfo(String custId) throws HsException;
	
	
	/**
	 * 实名注册
	 * @param asRealNameReg 实名注册实体
	 * @throws HsException
	 */
	public void saveRealNameReg(AsRealNameReg asRealNameReg) throws HsException;
	
	/**
	 *  查询实名认证状态
	 * @param custId 客户号
	 * @return (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
	 * @throws HsException
	 */
	public String findAuthStatusByCustId(String custId) throws  HsException;
	
	/**
	 *  查询UC实名认证信息
	 * @param custId 客户号
	 * @return 实名认证信息
	 * @throws HsException
	 */
	public  AsRealNameAuth  searchRealNameAuthInfo (String custId) throws  HsException ;
	
	/**
     *  实名认证-新增
     * @param  perRealnameAuth 实名认证实体
     * @return 
     * @throws HsException
     */
	public void createPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException;
	
	/**
     *  实名认证-修改
     * @param  perRealnameAuth 实名认证实体
     * @return 
     * @throws HsException
     */
	public void modifyPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException;
	
	/**
	 *  查询审批的实名认证信息
	 * @param perCustId
	 * @return
	 */
	public PerRealnameAuth queryPerRealnameAuthByCustId(String perCustId)throws  HsException ;
}

	