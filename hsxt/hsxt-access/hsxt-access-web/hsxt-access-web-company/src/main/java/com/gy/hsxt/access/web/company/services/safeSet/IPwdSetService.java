/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.safeSet;

import java.util.Map;

import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean;
import com.gy.hsxt.access.web.bean.safeSet.RequestResetTradPwdBean;
import com.gy.hsxt.access.web.bean.safeSet.TradePwdBean;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.common.exception.HsException;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.company.services.safeSet
 * @ClassName: IPwdSetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-30 下午3:36:08
 * @version V1.0
 */
public interface IPwdSetService extends IBaseService {
    /**
     * 验证交易密码
     * 
     * @param custId
     *            客户号
     * @param tradePwd
     *            交易密码(AES加密后的密文)
     * @param userType
     *            1：非持卡人 2：持卡人 3：企业 4：系统用户
     * @param secretKey
     *            AES秘钥（随机报文校验token）
     * @throws HsException
     */
    public void checkTradePwd(String custId, String tradePwd, String userType, String secretKey) throws HsException;

    /**
     * 登录密码修改
     * 
     * @param lpb
     */
    public void updateLoginPassword(LoginPasswordBean lpb) throws HsException;

    /**
     * 新增交易密码
     * 
     * @param tpb
     * @throws HsException
     */
    public void addTradingPassword(TradePwdBean tpb) throws HsException;

    /**
     * 修改交易密码
     * 
     * @param tpb
     * @throws HsException
     */
    public void updateTradingPassword(TradePwdBean tpb) throws HsException;

    /**
     * 重置交易密码
     * 
     * @param tpb
     * @throws HsException
     */
    public void resetTradingPassword(TradePwdBean tpb) throws HsException;

    /**
     * 查询企业重要信息
     * 
     * @param companyBase
     * @return
     * @throws HsException
     */
    public Map<String, Object> getEntInfo(CompanyBase companyBase) throws HsException;

    /**
     * 申请重置交易密码
     * 
     * @param rrtpb
     * @throws HsException
     */
    public void requestedResetTradingPassword(RequestResetTradPwdBean rrtpb) throws HsException;

    /**
     * 获取交易密码和密保问题已设置
     * 
     * @param companyBase
     * @return
     */
    public Map<String, Object> getIsSafeSet(CompanyBase companyBase);

    /**
     * 据企业客户号查询最新交易密码申请记录
     * 
     * @param companyBase
     * @return
     */
    public Map<String, Object> queryTradPwdLastApply(CompanyBase companyBase);
}
