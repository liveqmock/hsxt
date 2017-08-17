/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.safetyset;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdReset;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdResetCheck;
import com.gy.hsxt.common.exception.HsException;

/***
 * 系统安全设置-密码设置接口类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.safetyset
 * @ClassName: IPwdSetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-10 下午3:29:52
 * @version V1.0
 */
@Service
public interface IPwdSetService extends IBaseService {

    /**
     * 验证交易密码
     * @param custId 客户号
     * @param tradePwd 交易密码(AES加密后的密文)
     * @param userType 1：非持卡人 2：持卡人 3：企业 4：系统用户
     * @param secretKey AES秘钥（随机报文校验token）
     * @throws HsException
     */
    public void checkTradePwd(String custId, String tradePwd, String userType, String secretKey) throws HsException;

    /**
     * 查询交易密码是否已经设置状态
     * 
     * @param custId
     *            客户编号
     * @param userType
     *            客户类型
     * @return true=设置，false=未设置
     * @throws HsException
     */
    public boolean isSetTradePwd(String custId, String userType) throws HsException;

    /**
     * 修改登录密码
     * 
     * @param custId
     * @param oldPwd
     * @param newsPwd
     * @param secretKey
     * @throws HsException
     */

    public void updateLoginPwd(String custId, String userType, String oldTradePwd, String newTradePwd, String secretKey)
            throws HsException;

    /**
     * 修改交易密码
     * 
     * @param custId
     * @param oldTradePwd
     * @param newTradePwd
     * @param secretKey
     * @throws HsException
     */
    public void updateTradePwd(String custId, String userType, String oldTradePwd, String newTradePwd, String secretKey)
            throws HsException;

    /**
     * 设置交易密码
     * 
     * @param custId
     *            客户号
     * @param secretKey
     *            随机报文 秘钥
     * @param tradePwd
     *            交易密码
     * @param userType
     *            用户类型
     * @throws HsException
     */
    public void setTradePwd(String custId, String secretKey, String tradePwd, String userType) throws HsException;

    /**
     * 查询用户是否实名认证
     * 
     * @param custId
     * @param pointNo
     * @param name
     * @param certCode
     * @return
     * @throws HsException
     */
    public String checkUserStatus(String custId, String pointNo, String name, String certCode) throws HsException; // 传入客户号
                                                                                                                   // certCode
                                                                                                                   // 证件号码

    /**
     * 重置交易密码===用户信息验证
     * 
     * @return identifyCode
     * @param dprc
     * @throws HsException
     */
    public String checkUserinfo(DealPwdResetCheck dprc) throws HsException;

    /**
     * 重置交易密码===提交重置密码
     * 
     * @param dpr
     * @throws HsException
     */
    public void resetTradePwd(DealPwdReset dpr) throws HsException;

    /**
     * 交易密码是否已存在
     * 
     * @param custId
     * @param pointNo
     * @return
     * @throws HsException
     */
    public Map<String, Boolean> existTradePwd(String custId, String pointNo) throws HsException;

}
