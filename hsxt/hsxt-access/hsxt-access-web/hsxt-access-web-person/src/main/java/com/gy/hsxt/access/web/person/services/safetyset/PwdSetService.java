/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.safetyset;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdReset;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdResetCheck;
import com.gy.hsxt.access.web.person.common.VerificationCodeType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsResetTradePwdConsumer;
import com.gy.hsxt.uc.as.bean.common.AsUpdateTradePwd;

/***
 * 系统安全设置-密码设置service类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.safetyset
 * @ClassName: PwdSetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-10 下午3:38:07
 * @version V1.0
 */
@Service(value = "pwdSetService")
public class PwdSetService extends BaseServiceImpl implements IPwdSetService {

    // 密码服务接口类
    @Resource
    private IUCAsPwdService ucAsPwdService;
    
    @Resource
    private RedisUtil<String> changeRedisUtil;

    /**
     * 验证交易密码
     * @param custId 客户号
     * @param tradePwd 交易密码(AES加密后的密文)
     * @param userType 1：非持卡人 2：持卡人 3：企业 4：系统用户
     * @param secretKey AES秘钥（随机报文校验token）
     * @throws HsException
     */
    @Override
    public void checkTradePwd(String custId, String tradePwd, String userType, String secretKey) throws HsException {
        this.ucAsPwdService.checkTradePwd(custId, tradePwd, userType, secretKey);
    }
    
    /**  
     * @param custId
     * @param userType
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService#isSetTradePwd(java.lang.String, java.lang.String) 
     */
    @Override
    public boolean isSetTradePwd(String custId, String userType) throws HsException {
       return ucAsPwdService.isSetTradePwd(custId, userType);
    }
    
    /**
     * 修改登录密码
     * 
     * @param custId
     * @param oldPwd
     * @param newsPwd
     * @param secretKey
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService#changeLoginPasswd(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public void changeLoginPasswd(String custId, String oldPwd, String newsPwd, String secretKey) {
        ucAsPwdService.updateLoginPwd(custId, UserTypeEnum.CARDER.getType(), oldPwd, newsPwd, secretKey);
    }

   

    /**
     * 查询用户是否实名认证
     */
    public String checkUserStatus(String custId, String pointNo, String name, String certCode) {
        String returnJsonStr = "{\"code\":0,\"data\":{\"resultMsg\":\"成功!\",\"resultCode\":0}}";
        return returnJsonStr;
    }

    /**
     * 交易密码是否已存在
     * 
     * @param custId
     * @param pointNo
     * @return
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService#existTradePwd(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public Map<String, Boolean> existTradePwd(String custId, String pointNo) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("existTradePwd", true);
        return map;
    }

    /**
     * 重置交易密码===用户信息验证
     * @param dprc
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService#checkUserinfo(com.gy.hsxt.access.web.person.bean.safetySet.DealPwdResetCheck)
     */
    @Override
    public String checkUserinfo(DealPwdResetCheck dprc) throws HsException {
        //验证验证码
        this.verificationCode(dprc);
        //创建交易密码申请类
        AsMainInfo ami= dprc.createMainInfo();
        //验证重置密码信息
        return ucAsPwdService.checkMainInfo(ami);
    }
    
    /**
     * 验证码验证
     */
    public void verificationCode(DealPwdResetCheck dprc) {
        // 获取验证码
        String vCode = changeRedisUtil.get(dprc.getCustId().trim()+"_"+VerificationCodeType.tradePwdResetApply, String.class);
        // 判断为空
        if (StringUtils.isEmpty(vCode))
        {
            throw new HsException(RespCode.PW_VERIFICATION_CODE_NULL.getCode(), RespCode.PW_VERIFICATION_CODE_NULL
                    .getDesc());
        }
        // 判断相等
        if (!dprc.getSmsCode().toUpperCase().equals(vCode.toUpperCase()))
        {
            throw new HsException(RespCode.PW_VERIFICATION_CODE_ERROR.getCode(), RespCode.PW_VERIFICATION_CODE_ERROR
                    .getDesc());
        }
    }

    /**
     * 重置交易密码===提交重置密码
     * 
     * @param dpr
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.safetyset.ISafetySetService#resetTradePwd(com.gy.hsxt.access.web.person.bean.safetySet.DealPwdReset)
     */
    @Override
    public void resetTradePwd(DealPwdReset dpr) throws HsException {
        // 1、获取修改参数类
        AsResetTradePwdConsumer artpc = dpr.createARTPC();
        // 2、重置交易密码
        ucAsPwdService.resetTradePwd(artpc);
    }

    /**  
     * 执行修改操作
     * @param custId
     * @param userType
     * @param oldTradePwd
     * @param newTradePwd
     * @param secretKey
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService#updateLoginPwd(java.lang.String, com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum, java.lang.String, java.lang.String, java.lang.String) 
     */
    @Override
    public void updateLoginPwd(String custId, String userType, String oldTradePwd, String newTradePwd,String secretKey) throws HsException {
       this.ucAsPwdService.updateLoginPwd(custId, userType, oldTradePwd, newTradePwd, secretKey);
    }

    /**
     * 修改交易密码
     * @param custId
     * @param userType
     * @param oldTradePwd
     * @param newTradePwd
     * @param randomToken
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService#updateTradePwd(java.lang.String, com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void updateTradePwd(String custId, String userType, String oldTradePwd, String newTradePwd,String secretKey) throws HsException {
        //创建交易密码参数类
        AsUpdateTradePwd autp = this.createAUTP(custId, userType, oldTradePwd, newTradePwd, secretKey);
        //交易密码修改
        this.ucAsPwdService.updateTradePwd(autp);
    }
    
    /**
     * 创建交易密码修改类
     * @param custId
     * @param userType
     * @param oldTradePwd
     * @param newTradePwd
     * @param secretKey
     * @return
     */
     AsUpdateTradePwd createAUTP(String custId, String userType, String oldTradePwd, String newTradePwd, String secretKey){
         AsUpdateTradePwd autp=new AsUpdateTradePwd();
         autp.setCustId(custId);
         autp.setUserType(userType);
         autp.setOldTradePwd(oldTradePwd);
         autp.setNewTradePwd(newTradePwd);
         autp.setSecretKey(secretKey);
         autp.setOperCustId(custId);
         return autp;
     }

    /**
     * 设置交易密码
     * @param custId 客户号
     * @param secretKey 随便token 秘钥
     * @param tradePwd 交易密码
     * @param userType 用户类型
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService#setTradePwd(java.lang.String, java.lang.String, java.lang.String, com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
     */
    @Override
    public void setTradePwd(String custId, String secretKey, String tradePwd, String userType) throws HsException {
        this.ucAsPwdService.setTradePwd(custId, tradePwd, userType, secretKey,custId);
    }
}
