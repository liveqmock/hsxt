/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.consumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.bean.UpdateEmailBean;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.uc.as.api.common.IUCAsEmailService;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderStatusInfoService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
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
 * File Name        : CardholderService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-11-16 下午6:40:59
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-11-16 下午6:40:59
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class CardholderService extends BaseServiceImpl implements ICardholderService {

    /**
     * 用户中心-持卡用户dubbo
     */
    @Autowired
    private IUCAsCardHolderService ucAsCardHolderService;

    /**
     * 用户中心-实名注册信息dubbo
     */
    @Autowired
    private IUCAsCardHolderAuthInfoService ucAsCardHolderAuthInfoService;

    /**
     * 用户中心-实名注册信息dubbo
     */
    @Autowired
    private IUCAsCardHolderStatusInfoService ucAsCardHolderStatusInfoService;

    /**
     * 用户中心-手机服务dubbo
     */
    @Autowired
    private IUCAsMobileService ucAsMobileService;
    
    /**
     * 用户中心-登录dubbo
     */
    @Autowired
    private IUCLoginService ucLoginService;
    
    /**
     * 用户中心-邮件操作
     */
    @Autowired
    private IUCAsEmailService ucAsEmailService ;

    /**
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findMobileByCustId(java.lang.String)
     */
    @Override
    public Map<String, String> findMobileByCustId(String custId) throws HsException {
        Map<String, String> retMap = new HashMap<String, String>();
    	String   mobile = ucAsCardHolderService.findMobileByCustId(custId);
        retMap.put("mobile", mobile);
        return retMap;
    }

    /**
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findRealNameRegInfo(java.lang.String)
     */
    @Override
    public AsRealNameReg findRealNameRegInfo(String custId) throws HsException {
        return this.ucAsCardHolderAuthInfoService.searchRealNameRegInfo(custId);
    }

    /**
     * 发送手机验证码
     * 
     * @param mobile 手机号
     * @return
     * @throws HsException
     */
    public void mobileSendCode(String custId,String mobile,int custTypeCode) throws HsException {
        this.ucAsMobileService.sendSmsCodeForValidMobile(custId,mobile,custTypeCode);
    }
    
    /**
     * 绑定手机号码
     * @param custId 客户号码
     * @param mobile 手机号码
     * @param smsCode 验证码
     * @param userType 用户类型com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum
     * @throws HsException
     */
    public void addBindMobile(String custId,String mobile,String smsCode, String userType) throws  HsException {
        this.ucAsMobileService.bindMobile(custId, mobile, smsCode, userType);
    }

    /**
     * 持卡人登陆
     * @param loginInfo 登录信息实体
     * @return 
     * @throws HsException
     */
    public AsCardHolderLoginResult cardHolderLogin(AsConsumerLoginInfo loginInfo) throws  HsException{
        return this.ucLoginService.cardHolderLogin(loginInfo);
    }
    
    /**
     * 持卡人登陆
     * @param loginInfo 登录信息实体
     * @return 
     * @throws HsException
     */
    public AsNoCardHolderLoginResult  noCardHolderLogin(AsConsumerLoginInfo loginInfo) throws  HsException{
    	return this.ucLoginService.noCardHolderLogin(loginInfo);
    }
    
    /**
     * 获取持卡人邮箱绑定状态
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findEamilByCustId(java.lang.String)
     */
    @Override
    public Map<String, String> findEamilByCustId(String custId) throws HsException {
        return this.ucAsCardHolderService.findEmailByCustId(custId);
    }

    /**
     * 添加邮箱信息
     * @param custId 客户号
     * @param email  邮箱信息
     * @param userType 用户类型
     * @throws HsException
     */
    public void addBindEmail(PersonBase personBase,String email,String userType,int custTypeCode) throws HsException{
        //给绑定的邮箱发验证码
        this.ucAsEmailService.sendMailForValidEmail(email, personBase.getPointNo(), null, userType, custTypeCode);
    }
    
    /**
     * 修改邮箱绑定
     * @param personBase
     * @param loginPwd
     * @param email
     * @param userType
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#mailModify(com.gy.hsxt.access.web.person.bean.PersonBase, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void mailModify(PersonBase personBase, String loginPwd, String email, String userType) throws HsException {
        //给绑定的邮箱发验证码
        this.ucAsCardHolderService.changeBindEmail(personBase.getCustId(),email,loginPwd,personBase.getRandomToken());
    }

    /**
     * 校验邮件
     * @param randomToken
     * @param email
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#checkEmailCode(java.lang.String, java.lang.String)
     */
    @Override
    public void checkEmailCode(String param) throws HsException {
        try {
			byte[] paramByte = Base64Utils.decode(param);
			if (paramByte == null){
				throw new HsException(ASRespCode.AS_PARAM_INVALID);
			}
		    String paramStr = new String(paramByte);
		    UpdateEmailBean ueb =  JSON.parseObject(paramStr, UpdateEmailBean.class);
		    ucAsEmailService.bindEmail(ueb.getCustId(), ueb.getRandom(), ueb.getEmail(), ueb.getUserType());
		}catch(HsException e){
			throw e;
		} catch (Exception e) {
			throw new HsException(ASRespCode.AS_PARAM_INVALID);
		}
    }
    
    /**
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findRealNameAuthInfo(java.lang.String)
     */
    @Override
    public AsRealNameAuth findRealNameAuthInfo(String custId) throws HsException {
        return this.ucAsCardHolderAuthInfoService.searchRealNameAuthInfo(custId);
    }

    /**
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findNetworkInfo(java.lang.String)
     */
    @Override
    public AsNetworkInfo findNetworkInfo(String custId) throws HsException {
        AsNetworkInfo networkInfo = new AsNetworkInfo();
        networkInfo.setNickname("昵称");
        networkInfo.setSex(0);
        networkInfo.setAge(33);
        networkInfo.setArea("福田区");
        networkInfo.setCountry("中国");
        networkInfo.setCountryNo("001");
        networkInfo.setProvince("广东");
        networkInfo.setProvinceNo("001001");
        networkInfo.setCity("深圳");
        networkInfo.setCityNo("00100101");
        networkInfo.setAddress("勘察研究院7栋归一科技一楼前台 ");
        networkInfo.setIndividualSign("走好");
        networkInfo.setJob("软件工程师 ");
        networkInfo.setHobby("加班");
        networkInfo.setBlood(3);
        networkInfo.setPostcode("邮编");
        networkInfo.setEmail("email@163.com");
        networkInfo.setGraduateSchool("华南理工 ");
        networkInfo.setWeixin("weixinhao");
        networkInfo.setQqNo("265315");
        networkInfo.setTelNo("13588888888");
        networkInfo.setMobile("13588888888");
        networkInfo.setBirthday("1987-08-28");
        networkInfo.setHomeAddr("福田区");
        return networkInfo;
    }

    /**
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findAuthStatusByCustId(java.lang.String)
     */
    @Override
    public String findAuthStatusByCustId(String custId) throws HsException {
        return this.ucAsCardHolderAuthInfoService.findAuthStatusByCustId(custId);
    }

    /**
     * @param custId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.consumer.ICardholderService#findImportantInfoChangeStatus(java.lang.String)
     */
    @Override
    public boolean findImportantInfoChangeStatus(String custId) throws HsException {
        return this.ucAsCardHolderStatusInfoService.isMainInfoApplyChanging(custId);
    }

}
