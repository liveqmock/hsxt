/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.person.services.consumer;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsNoCardHolderLoginResult;


/***************************************************************************
 * <PRE>
 * Description 		: 持卡用户的操作服务类
 *
 * Project Name   	: hsxt-access-web-person 
 *
 * Package Name     : com.gy.hsxt.access.web.person.services.hsc  
 *
 * File Name        : ICardholderService 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-11-16 下午6:38:47
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-11-16 下午6:38:47
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/
@Service
public interface ICardholderService extends IBaseService{
    /**
     * 查询重要信息变更状态
     * @param custId
     * @return
     * @throws HsException
     */
    public boolean findImportantInfoChangeStatus (String custId) throws  HsException;
    
    /**
     * 获取持卡用户手机号码
     * @param custId
     * @return
     * @throws HsException
     */
    public Map<String, String> findMobileByCustId(String custId) throws  HsException;
    /**
     * 发送手机验证码
     * @param mobile 手机号
     * @return
     * @throws HsException
     */
    public void mobileSendCode(String custId,String mobile,int  custTypeCod) throws  HsException;
    
    /**
     * 绑定手机号码
     * @param custId 客户号码
     * @param mobile 手机号码
     * @param smsCode 验证码
     * @param userType 用户类型com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum
     * @throws HsException
     */
    public void addBindMobile(String custId,String mobile,String smsCode, String userType) throws  HsException;
    
    /**
     * 获取持卡用户邮箱
     * @param custId
     * @return
     * @throws HsException
     */
    public Map<String, String> findEamilByCustId(String custId) throws  HsException;
    
    /**
     * 添加邮箱信息
     * @param custId 客户号
     * @param email  邮箱信息
     * @param userType 用户类型
     * @throws HsException
     */
    public void addBindEmail(PersonBase personBase,String email,String userType,int custTypeCode) throws HsException;
    /**
     * 持卡人登陆
     * @param loginInfo 登录信息实体
     * @return 
     * @throws HsException
     */
    public AsCardHolderLoginResult  cardHolderLogin(AsConsumerLoginInfo loginInfo) throws  HsException;
    
    /**
     * 非持卡人登陆
     * @param loginInfo 登录信息实体
     * @return 
     * @throws HsException
     */
    public AsNoCardHolderLoginResult  noCardHolderLogin(AsConsumerLoginInfo loginInfo) throws  HsException;
    
   
    /**
     * 根据客户号获取持卡人实名注册信息
     * @return
     * @throws HsException
     */
    public AsRealNameReg findRealNameRegInfo(String custId)throws HsException;
    
    /**
     * 获取实名认证状态
     * @param custId
     * @return
     * @throws HsException
     */
    public String findAuthStatusByCustId(String custId)throws HsException;
    
    /**
     * 根据客户号获取持卡人实名认证信息
     * @return
     * @throws HsException
     */
    public AsRealNameAuth findRealNameAuthInfo(String custId)throws HsException;
    
    /**
     * 根据客户号获取持卡人网络信息详情
     * @return
     * @throws HsException
     */
    public AsNetworkInfo  findNetworkInfo(String custId)throws HsException;
    
    /**
     * 校验邮件
     * @param custId  客户号
     * @param randomToken 邮箱验证token
     * @param email 邮箱地址
     * @param userType 用户类型
     * @return
     * @throws HsException
     */
    public void checkEmailCode(String param) throws HsException;
    
    
    /**
     * 修改邮箱绑定
     * @param personBase
     * @param loginPwd
     * @param email
     * @param userType
     * @throws HsException
     */
    public void mailModify(PersonBase personBase,String loginPwd, String email,String userType) throws HsException;
    
}
