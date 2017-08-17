/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.person.services.consumer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.common.AsNoCarderMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;


/***************************************************************************
 * <PRE>
 * Description 		: 非持卡用户的操作服务类
 *
 * Project Name   	: hsxt-access-web-person 
 *
 * Package Name     : com.gy.hsxt.access.web.person.services.consumer 
 *
 * File Name        : INoCardholderService 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2016-04-16 下午4:21:47
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2016-04-16 下午4:21:47
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/
@Service
public interface INoCardholderService extends IBaseService{
    
    /**
     * 获取持卡用户手机号码
     * @param custId
     * @return
     * @throws HsException
     */
    public Map<String, String> findMobileByCustId(String custId) throws  HsException;
    
    /**
     * 校验短信验证码
     * @param mobile 手机号码
     * @param smsCode 短信验证码
     * @return
     * @throws HsException
     */
    public String checkSmsCode(String  mobile, String  smsCode)throws HsException ;
    
    /**
     * 获取持卡人邮箱绑定状态
     * @param custId
     * @return
     * @throws HsException
     */
    public Map<String, String> findEamilByCustId(String custId) throws HsException;
    
    /**
     * 根据客户号查询网络信息详情
     * @param custId 客户号
     * @return
     * @throws HsException
     */
    public AsNetworkInfo findNetworkInfoByCustId(String custId)throws HsException;
    
    /**
     * 修改网络信息
     * @param netWorkInfo
     * @throws HsException
     */
    public void  updateNetworkInfo(AsNetworkInfo netWorkInfo) throws  HsException;
    
    /**
     * 发送手机验证码(重置登录密码) 
     * @param custId 客户好
     * @param mobile 手机号
     * @throws HsException
     */
	public void sendSmsCodeForResetPwd(String custId,String mobile) throws HsException;
	
	
	/**
	 * 重置交易密码身份验证
	 * @param perCustId 客户号
	 * @param email 邮件信息
	 * @param loginPassword 登录密码
	 * @param secretKey	随机token
	 * @return
	 * @throws HsException
	 */
	public void  changeBindEmail(String perCustId, String email,String loginPassword, String secretKey)  throws  HsException;
	
	/**
	 * 重置交易密码身份验证
	 * @param mainInfo
	 * @return
	 * @throws HsException
	 */
	public  String  checkNoCarderMainInfo(AsNoCarderMainInfo   mainInfo) throws  HsException;
	
	/**
	 * 6.1.17.10.重置非持卡人交易密码
	 * @param mobile 手机号
	 * @param random 随机数
	 * @param newTradePwd 新交易密码
	 * @param secretKey  AES秘钥
	 * @throws HsException
	 */
	public  void  resetNoCarderTradepwd(String  mobile,String  random,String  newTradePwd, String  secretKey )throws HsException;
	
	/**
	 * 查询收货地址
	 * @param custId 客户号
	 * @return
	 * @throws HsException
	 */
	public  List<AsReceiveAddr> findReceiveAddrByCustId(String custId ) throws  HsException;
	
	/**
	 * 查询收货地址详情
	 * @param custId 客户号
	 * @param addrId 收获地址ID
	 * @return
	 * @throws HsException
	 */
	public  AsReceiveAddr findReceiveAddrInfo(String custId ,Long  addrId) throws  HsException;
	
	/**
	 * 设置默认收货地址
	 * @param custId 客户号
	 * @param addrId 收获地址ID
	 * @return
	 * @throws HsException
	 */
	public  void setDefaultAddr(String custId ,Long  addrId) throws  HsException;
	
	/**
	 *  删除收货地址信息
	 * @param custId 客户号
	 * @param addrId 收获地址ID
	 * @return
	 * @throws HsException
	 */
	public  void deleteReceiveAddr (String custId ,Long  addrId) throws  HsException;
	
	/**
	 * 修改收货地址
	 * @param custId 客户号
	 * @param addr 收货地址实体
	 * @return
	 * @throws HsException
	 */
	public  void updateReceiveAddr(String custId ,AsReceiveAddr  addr) throws  HsException;
}
