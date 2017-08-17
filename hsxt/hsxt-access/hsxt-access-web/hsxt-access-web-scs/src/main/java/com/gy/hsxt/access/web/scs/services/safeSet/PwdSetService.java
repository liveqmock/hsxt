/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.safeSet;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.bean.safeSet.LoginPasswordBean;
import com.gy.hsxt.access.web.bean.safeSet.RequestResetTradPwdBean;
import com.gy.hsxt.access.web.bean.safeSet.TradePwdBean;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.common.SCSConfigService;
import com.gy.hsxt.bs.api.pwd.IBSTransPwdService;
import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.safeSet
 * @ClassName: PwdSetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-2 下午8:18:05
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Service(value = "pwdSetService")
public class PwdSetService extends BaseServiceImpl implements IPwdSetService {

    @Autowired
    private SCSConfigService scsConfigService; // 全局配置文件

    @Resource
    private IUCAsPwdService ucAsPwdService;

    @Resource
    private IUCAsEntService iuCAsEntService;

    @Resource
    private ISecuritySetService securitySetService;

    @Resource
    private IBSTransPwdService bsTransPwdService;

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
    @Override
    public void checkTradePwd(String custId, String tradePwd, String userType, String secretKey) throws HsException {
        this.ucAsPwdService.checkTradePwd(custId, tradePwd, userType, secretKey);
    }

    /**
     * 修改登录密码
     * 
     * @param lpb
     */
    @Override
    public void updateLoginPassword(LoginPasswordBean lpb) throws HsException {

        // 1、验证新密相等
        if (!lpb.validateNewPasswordEquals())
        {
            throw new HsException(RespCode.SW_LOGIN_PWD_NOT_EQUALS.getCode(), RespCode.SW_LOGIN_PWD_NOT_EQUALS
                    .getDesc());
        }

        // 2、修改登录密码
        ucAsPwdService.updateLoginPwd(lpb.getCustId(), UserTypeEnum.OPERATOR.getType(), lpb.getOldPassword(), lpb
                .getNewPassword(), lpb.getRandomToken());

    }

    /**
     * 新增交易密码
     * 
     * @param tpb
     * @throws HsException
     * @see com.gy.hsxt.access.web.ISecuritySetService.service.ISafeSetService#addTradingPassword(com.gy.hsxt.access.web.bean.safeSet.TradePwdBean)
     */
    @Override
    public void addTradingPassword(TradePwdBean tpb) throws HsException {

        // 1、验证交易密码规则
        if (!tpb.validateNewPasswordEquals())
        {
            throw new HsException(RespCode.SW_TRADE_PWD_NOT_EQUALS);
        }

        // 2、验证交易密码已设置,若已设置则终止操作,否则继续
        if (this.isSetTradePwd(tpb.getEntCustId()))
        {
            throw new HsException(RespCode.SW_TRADE_PWD_EXIST);
        }

        // 3、新增交易密码
        ucAsPwdService.setTradePwd(tpb.getEntCustId(), tpb.getNewTradingPassword(), UserTypeEnum.ENT.getType(), tpb
                .getRandomToken(), tpb.getCustId());
    }

    /**
     * 修改交易密码
     * 
     * @param tpb
     * @throws HsException
     * @see com.gy.hsxt.access.web.ISecuritySetService.service.ISafeSetService#updateTradingPassword(com.gy.hsxt.access.web.bean.safeSet.TradePwdBean)
     */
    @Override
    public void updateTradingPassword(TradePwdBean tpb) throws HsException {

        // 1、验证交易密码规则
        if (!tpb.validateNewPasswordEquals())
        {
            throw new HsException(RespCode.SW_TRADE_PWD_NOT_EQUALS.getCode(), RespCode.SW_TRADE_PWD_NOT_EQUALS
                    .getDesc());
        }

        // 2、验证交易密码已设置,若已设置则终止操作,否则继续
        if (!this.isSetTradePwd(tpb.getEntCustId()))
        {
            throw new HsException(RespCode.SW_TRADE_PWD_NO_EXIST.getCode(), RespCode.SW_TRADE_PWD_NO_EXIST.getDesc());
        }

        // 3、调用接口修改交易密码
        ucAsPwdService.updateTradePwd(tpb.crateUpdateTradePwd());
    }

    /**
     * 是否设置交易密码
     * 
     * @param custId
     * @return
     */
    private boolean isSetTradePwd(String entCustId) {
        return ucAsPwdService.isSetTradePwd(entCustId, UserTypeEnum.ENT.getType());
    }

    /**
     * 重置交易密码
     * 
     * @param tpb
     * @throws HsException
     * @see com.gy.hsxt.access.web.scs.services.safeSet.IPwdSetService#resetTradingPassword(com.gy.hsxt.access.web.bean.safeSet.TradePwdBean)
     */
    @Override
    public void resetTradingPassword(TradePwdBean tpb) throws HsException {

        // 1、验证交易密码规则
        if (!tpb.validateNewPasswordEquals())
        {
            throw new HsException(RespCode.SW_TRADE_PWD_NOT_EQUALS.getCode(), RespCode.SW_TRADE_PWD_NOT_EQUALS
                    .getDesc());
        }

        // 2、获取企业明细
        // AsEntMainInfo aem =
        // iuCAsEntService.searchEntMainInfo(tpb.getEntCustId());

        // 3、重置交易密码
        ucAsPwdService.resetTradePwdByAuthCode(tpb.createARTPAC());
    }

    /**
     * 查询企业重要信息
     * 
     * @param scsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.scs.services.safeSet.IPwdSetService#getEntInfo(com.gy.hsxt.access.web.bean.SCSBase)
     */
    @Override
    public Map<String, Object> getEntInfo(SCSBase scsBase) throws HsException {

        // 1、获取企业明细
        AsEntMainInfo aem = iuCAsEntService.searchEntMainInfo(scsBase.getEntCustId());

        // 2、转换map
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("entResNo", aem.getEntResNo());
        retMap.put("entName", aem.getEntName());
        retMap.put("contactPerson", aem.getContactPerson());
        retMap.put("contactPhone", aem.getContactPhone());

        return retMap;
    }

    /**
     * 申请重置交易密码
     * 
     * @param rrtpb
     * @throws HsException
     * @see com.gy.hsxt.access.web.ISecuritySetService.service.ISafeSetService#requestedResetTradingPassword(com.gy.hsxt.access.web.bean.safeSet.RequestResetTradPwdBean)
     */
    @Override
    public void requestedResetTradingPassword(RequestResetTradPwdBean rrtpb) throws HsException {

        // 1、获取企业信息
        AsEntMainInfo aem = iuCAsEntService.searchEntMainInfo(rrtpb.getEntCustId());
        // 2、构建bean
        ResetTransPwd trp = createTranPwd(aem, rrtpb);
        // 3、创建申请记录
        bsTransPwdService.createResetTransPwdApply(trp);

    }
    
    /**
     * 据企业客户号查询最新交易密码申请记录
     * @param scsBase
     * @return 
     * @see com.gy.hsxt.access.web.scs.services.safeSet.IPwdSetService#queryTradPwdLastApply(com.gy.hsxt.access.web.bean.SCSBase)
     */
    @Override
    public Map<String, Object> queryTradPwdLastApply(SCSBase scsBase) {
        // 获取最新申请记录
        ResetTransPwd rtp = bsTransPwdService.queryLastApplyBean(scsBase.getEntCustId());
        if (null != rtp)
        {
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("applyDate", rtp.getApplyDate());
            retMap.put("updatedDate", rtp.getUpdatedDate());
            retMap.put("status", rtp.getStatus());
            retMap.put("apprRemark", rtp.getApprRemark());
            return retMap;
        }
        return null;
    }

    /**
     * 构建交易密码重置申请 实体类
     * 
     * @param aem
     * @param rrtpb
     * @return
     */
    private ResetTransPwd createTranPwd(AsEntMainInfo aem, RequestResetTradPwdBean rrtpb) {
        ResetTransPwd retTP = new ResetTransPwd();

        retTP.setEntResNo(aem.getEntResNo());
        retTP.setEntCustId(aem.getEntCustId());
        retTP.setCustType(CustType.MANAGE_CORP.getCode());
        retTP.setEntCustName(aem.getEntName());
        retTP.setLinkman(aem.getContactPerson());
        retTP.setMobile(aem.getContactPhone());
        retTP.setApplyPath(rrtpb.getApplyID());
        retTP.setApplyReason(rrtpb.getExplain());
        retTP.setCreatedby(rrtpb.getCustId());

        return retTP;
    }

    /**
     * 获取交易密码和密保问题已设置
     * 
     * @param companyBase
     * @return
     * @see com.gy.hsxt.access.web.company.services.safeSet.IPwdSetService#getIsSafeSet(com.gy.hsxt.access.web.bean.CompanyBase)
     */
    @Override
    public Map<String, Object> getIsSafeSet(SCSBase scsBase) {
        Map<String, Object> retMap = new HashMap<String, Object>();

        // 1、获取预留信息
        String reserveInfo = securitySetService.getReserveInfo(scsBase);

        // 2、拼接返回map
        retMap.put("tradPwd", this.isSetTradePwd(scsBase.getEntCustId()));
        retMap.put("reserveInfo",StringUtils.trimToBlank(reserveInfo));

        return retMap;
    }
}
