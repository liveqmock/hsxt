/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsNoCarderMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;
import com.gy.hsxt.uc.as.bean.common.AsResetTradePwdAuthCode;
import com.gy.hsxt.uc.as.bean.common.AsResetTradePwdConsumer;
import com.gy.hsxt.uc.as.bean.common.AsUpdateTradePwd;

/**
 * 密码管理处理接口
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsPwdService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-25 上午11:55:32
 * @version V1.0
 */
public interface IUCAsPwdService {
	
	/**
	 * 修改登录密码（自己修改）
	 * 
	 * @param custId
	 *            持卡人：持卡人客户号, 非持卡人：非持卡人客户号 ,操作员：操作员客户号 ，系统用户：系统操作员客户号
	 * @param userType
	 *            用户类型枚举类
	 * @param oldLoginPwd
	 *            旧登录密码，使用MD5加密
	 * @param newLoginPwd
	 *            新登录密码，使用MD5加密
	 * @param secretKey
	 *            随机token AES128 加密的key
	 * @throws HsException
	 */
	public void updateLoginPwd(String custId, String userType,
			String oldLoginPwd, String newLoginPwd, String secretKey)
			throws HsException;



	/**
	 * 修改交易密码
	 * @param params
	 * @throws HsException
	 */
	public void updateTradePwd(AsUpdateTradePwd params)
			throws HsException;

	/**
	 * 重置持卡人交易密码
	 * @param params
	 * @throws HsException
	 */
	public void resetTradePwd(AsResetTradePwdConsumer params) throws HsException;

	/**
	 * 重置非持卡人交易密码
	 * @param newTradePwd
	 * @param secretKey
	 * @param random
	 * @throws HsException
	 */
	public void resetNoCarderTradepwd(String mobile,String random,String newTradePwd, String secretKey  )throws HsException;
	/**
	 * 重置交易密码（企业）
	 * @param resetTradePwdAuthCode
	 * @throws HsException
	 */
	public void resetTradePwdByAuthCode(AsResetTradePwdAuthCode resetTradePwdAuthCode)
	
			throws HsException;

	/**
	 * 验证交易密码
	 * 
	 * @param custId
	 * @param tradePwd
	 *            AES加密后的密码
	 * @param userType
	 * @param secretKey
	 *            随机token AES128 加密的key
	 * @throws HsException
	 */
	public void checkTradePwd(String custId, String tradePwd, String userType,
			String secretKey) throws HsException;

	/**
	 * 验证登陆密码密码
	 * 
	 * @param custId
	 * @param tradePwd
	 *            AES加密后的密码
	 * @param userType
	 * @param secretKey
	 *            随机token AES128 加密的key
	 * @throws HsException
	 */
	public void checkLoginPwd(String custId, String loginPwd, String userType,
			String secretKey) throws HsException;
	/**
	 * 设置交易密码（初始化）
	 * 
	 * @param custId
	 *            企业或消费者客户号
	 * @param tradePwd
	 *            交易密码
	 * @param userType
	 * 
	 * @param secretKey
	 *            随机token AES128 加密的key
	 * @throws HsException
	 */
	public void setTradePwd(String custId, String tradePwd, String userType,
			String secretKey,String operCustId) throws HsException;
	


	/**
	 * 重置持卡人交易密码身份验证
	 * @param mainInfo
	 * @return
	 * @throws HsException
	 */
	public String checkMainInfo(AsMainInfo mainInfo)
			throws HsException;

	/**
	 * 检查持卡人由POS机渠道过来的登录密码
	 * 
	 * @param resNo
	 *            互生号
	 * @param loginPwd
	 *            登录密码
	 */
	public void checkLoginPwdForPOS(String resNo, String loginPwd)
			throws HsException;

	/**
	 * 检查持卡人由POS机渠道过来的交易密码
	 * 
	 * @param resNo
	 *            互生号
	 * @param tradePwd
	 *            交易密码
	 */
	public void checkTradePwdForPOS(String resNo, String tradePwd)
			throws HsException;

	/**
	 * 检查用户的交易密码是否已设置（用户仅包含企业、持卡人、非持卡人）
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型（ 1：非持卡人 2：非持卡人 4：企业 ）
	 * @return true:已设置 false: 未设置
	 * @throws HsException
	 */
	public boolean isSetTradePwd(String custId, String userType)
			throws HsException;
	
	
	/**
	 * 通过手机方式重置登录密码
	 * @param resetLoginPwdMobile
	 * @throws HsException
	 */
	public void resetLoginPwdByMobile(AsResetLoginPwd resetParams) throws HsException;
	
	
	/**
	 * 通过邮箱的方式重置登录密码
	 * @param resetLoginPwdEmail
	 * @throws HsException
	 */
	public void resetLoginPwdByEmail(AsResetLoginPwd resetParams) throws HsException;
	
	/**
	 * 通过密保的方式重置登录密码
	 * @param resetLoginPwdEmail
	 * @throws HsException
	 */
	public void resetLoginPwdByCrypt(AsResetLoginPwd resetParams) throws HsException;
	
	/**
	 * 通过用户名查询客户号
	 * @param resNo 用户名	非持卡人填手机号，持卡人填互生号，操作员填企业互生号
	 * @param userType	非持卡人：1 持卡人:2 操作员：3
	 * @return	非持卡人和持卡人返回各自的客户号，操作员返回操作员客户号
	 * @throws HsException
	 */
	public String findCustIdByUserName(String resNo,String userType)throws HsException;
	
	/**
	 * 通过用户名查询客户号
	 * @param entResNo	企业互生号（操作员必填，其他不填）
	 * @param userName	非持卡人填手机号，持卡人填互生号，操作员填操作员账号
	 * @param userType	非持卡人：1 持卡人:2 操作员：3
	 * @return	非持卡人和持卡人返回各自的客户号，操作员返回操作员客户号
	 * @throws HsException
	 */
	public String findCustIdByUserName(String entResNo,String userName,String userType)throws HsException;
	
	/**
	 * 
	 * @param operNo	待重置登录密码的普通操作员账号
	 * @param adminCustId	超级管理员的客户号
	 * @param secretKey		AES秘钥
	 * @param newLoginPwd	AES新密码
	 * @param perCustId	待修改登录密码的持卡人客户号
	 */
	public void resetLogPwdByAdmin(String operNo,String adminCustId,String secretKey,String newLoginPwd)throws HsException;
	
	/**
	 * 地区平台操作员重置持卡人登录密码
	 * @param entResNo	地区平台的互生号
	 * @param userName	地区平台操作员的账号
	 * @param loginPwd	地区平台操作员的登录密码（AES登录密码）
	 * @param secretKey	 AES秘钥
	 * @param perCustId	 待重置登录密码的持卡人客户号
	 * @throws HsException
	 */
	public void resetLogPwdForCarderByReChecker(String regionalResNo,String userName, String loginPwd,
			String secretKey,String perCustId,String operCustId)throws HsException;
	
	/**
	 * 地区平台操作员重置企业管理员登录密码
	 * @param entResNo	地区平台的互生号
	 * @param userName	地区平台操作员的账号
	 * @param loginPwd	地区平台操作员的登录密码（AES登录密码）
	 * @param secretKey	 AES秘钥
	 * @param entResNo	 待重置管理员登录密码的企业互生号
	 * @throws HsException
	 */
	public void resetLogPwdForOperatorByReChecker(String regionalResNo,String userName, String loginPwd,
			String secretKey,String entResNo,String operCustId)throws HsException;
	
	/**
	 * 地区平台操作员重置持卡人交易密码
	 * @param entResNo	地区平台的互生号
	 * @param userName	地区平台操作员的账号
	 * @param loginPwd	地区平台操作员的登录密码（AES登录密码）
	 * @param secretKey	 AES秘钥
	 * @param operCustId	 待重置登录密码的操作员客户号
	 * @throws HsException
	 */
	public void resetTradePwdForCarderByReChecker(String regionalResNo,String userName, String loginPwd,
			String secretKey,String perCustId,String operCustId)throws HsException;

	@Deprecated
	/**可以使用该方法，但是推荐使用checkApsReCheckerLoginPwd替代
     * 检查复核员的登录密码
     * @param regionalResNo 地区平台的互生号
     * @param userName 地区平台操作员的账号
     * @param loginPwd 地区平台操作员的登录密码（AES登录密码）
     * @param secretKey AES秘钥
     * @param operCustId 管理员客户
     * @throws HsException
     */
    public String checkLoginPwdForCarderByReChecker(String regionalResNo,
                    String userName, String loginPwd, String secretKey,
                     String operCustId) throws HsException;
    
   
    /**
     * 检查地区平台复核员的登录密码
     * @param apsResNo 地区平台的互生号
     * @param userName 地区平台操作员的账号
     * @param loginPwd 地区平台操作员的登录密码（AES登录密码）
     * @param secretKey AES秘钥
     * @param operCustId 管理员客户
     * @throws HsException
     */
    public String checkApsReCheckerLoginPwd(String apsResNo,
                    String userName, String loginPwd, String secretKey,
                     String operCustId) throws HsException;
    
    
    /**
     * 检查管理平台复核员的登录密码
     * @param apsResNo 地区平台的互生号
     * @param userName 地区平台操作员的账号
     * @param loginPwd 地区平台操作员的登录密码（AES登录密码）
     * @param secretKey AES秘钥
     * @param operCustId 管理员客户
     * @throws HsException
     */
    public String checkMcsReCheckerLoginPwd(String mscResNo,
                    String userName, String loginPwd, String secretKey,
                     String operCustId) throws HsException;
    
    /**
     * 修改系统管理员第二个密码
     * @param custId
     * @param oldPwd
     * @param newPwd
     * @param secretKey
     * @throws HsException
     */
    public void updateSysOperatorSecondPwd(String custId, String oldPwd,
			String newPwd, String secretKey) throws HsException;
    
    /**
     * 修改系统管理员登录密码（第一个密码）
     * @param custId
     * @param oldPwd
     * @param newPwd
     * @param secretKey
     * @throws HsException
     */
    public void updateSysOperatorLoginPwd(String custId, String oldPwd,
			String newPwd, String secretKey) throws HsException;
	/**
	 * 添加第2个密码
	 * @param custId 客户号
	 * @param pwd 密码
	 * @param secretKey AES秘钥
	 */
	public void addSysOperatorSecondPwd(String custId, String wd, String secretKey);
	
	/**
	 * 验证系统管理员第2个密码
	 * @param custId 客户号
	 * @param pwd AES密码
	 * @param secretKey AES秘钥
	 * @return
	 */
	public boolean checkSysOperatorSecondPwd(String custId, String pwd, String secretKey);
	
	/**
	 * 
	 * @param custId
	 * @param oldPwd
	 * @param newLoginPwd
	 * @param secretKey
	 * @throws HsException
	 */
	public void updateSysAdminSecondPwd(String custId, String oldPwd,
			String newLoginPwd, String secretKey) throws HsException;
	
	/**
	 * POS专用检查企业交易密码接口
	 * @param entCustId	企业客户号
	 * @param tradePwd	企业交易密码
	 * @throws HsException
	 */
	public void checkEntTradePwd(String entCustId, String tradePwd)throws HsException;
	/**
	 * 地区平台重置企业的交易密码
	 * @param apsResNo	地区平台互生号
	 * @param userName	复核员的账号
	 * @param loginPwd	复核员的AES登录密码
	 * @param secretKey	AES秘钥
	 * @param entResNo	被重置交易密码的企业互生号
	 * @param operCustId  当前登录的地区平台的操作员
	 * @throws HsException
	 */
	public void resetEntTradePwdByReChecker(String apsResNo,
			String userName, String loginPwd, String secretKey,
			String entResNo, String operCustId) throws HsException;
	/**
	 * 重置非持卡人交易密码身份验证
	 * @param mainInfo
	 * @return
	 * @throws HsException
	 */
	public String checkNoCarderMainInfo(AsNoCarderMainInfo mainInfo) throws HsException;
}
