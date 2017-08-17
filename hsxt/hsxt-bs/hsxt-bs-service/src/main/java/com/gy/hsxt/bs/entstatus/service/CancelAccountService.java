/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.api.IAOBankTransferService;
import com.gy.hsxt.ao.api.IAOCurrencyConvertService;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.enumtype.TransReason;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bm.interfaces.IBsUfRegionPackService;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.quota.ResStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IEntResNoService;
import com.gy.hsxt.bs.entstatus.bean.CancelAccountParam;
import com.gy.hsxt.bs.entstatus.enumtype.MemberQuitProgress;
import com.gy.hsxt.bs.entstatus.interfaces.ICancelAccountService;
import com.gy.hsxt.bs.entstatus.mapper.MemberQuitMapper;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.enumerate.UserTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 成员企业销户过程管理
 *
 * @Package : com.gy.hsxt.bs.entstatus.service
 * @ClassName : CancelAccount
 * @Description : 成员企业销户过程管理
 * @Author : xiaofl
 * @Date : 2015-11-17 上午9:21:45
 * @Version V1.0
 */
@Service
public class CancelAccountService implements ICancelAccountService {

    @Resource
    private MemberQuitMapper memberQuitMapper;

    @Resource
    private IEntResNoService entResNoService;

    @Resource
    private ICommonService commonService;

    @Resource
    private IUCBsEntService bsEntService;

    @Resource
    private IAccountSearchService accountSearchService;

    @Resource
    private IBsUfRegionPackService bsUfRegionPackService;

    @Resource
    private IAOCurrencyConvertService aoCurrencyConvertService;

    @Resource
    private IAOBankTransferService aoBankTransferService;

    @Resource
    private IUCBsBankAcctInfoService ucBankAcctInfoService;

    @Resource
    private IGPTransCashService gpTransCashService;

    /**
     * 注销增值系统账户--成员企业销户
     *
     * @param ca 销户参数
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean cancelBmAccount(CancelAccountParam ca) throws HsException {
        BizLog.biz(this.getClass().getName(), "cancelBmAccount", "input param:", ca + "");
        HsAssert.notNull(ca, RespCode.PARAM_ERROR, "方法cancelBmAccount参数对象不能为空");
        try {
            // 调用增值系统销户
            bsUfRegionPackService.stopResourceNo(ca.getEntResNo());
            // 更新销户进度为1：增值系统已销户
            memberQuitMapper.updateProgress(ca.getApplyId(), MemberQuitProgress.BM_ACCOUNT_CANCELED.getCode(), ca
                    .getOptCustId());// 1：增值系统已销户
            return true;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "cancelBmAccount", "成员企业销户:注销增值系统账户失败[param=" + ca + "]", e);
            return false;
        }
    }

    /**
     * 互生币转货币--成员企业销户
     *
     * @param ca 销户参数
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean hsb2hb(CancelAccountParam ca) throws HsException {
        BizLog.biz(this.getClass().getName(), "hsb2hb", "input param:", ca + "");
        HsAssert.notNull(ca, BSRespCode.BS_PARAMS_NULL, "方法hsb2hb参数对象[CancelAccountParam]不能为null");
        try {
            // 调用账务系统，查询企业流通互生币
            AccountBalance hsbAcc = accountSearchService.searchAccNormal(ca.getEntCustId(),
                    AccountType.ACC_TYPE_POINT20110.getCode());// 流通互生币

            // 流通互生币大于0
            if (hsbAcc != null && BigDecimalUtils.compareTo(hsbAcc.getAccBalance(), "0") > 0) {
                // 调用账户操作系统把流通互生币全部转成货币
                HsbToCash hsbToCash = new HsbToCash();
                hsbToCash.setCustId(ca.getEntCustId());
                hsbToCash.setHsResNo(ca.getEntResNo());
                hsbToCash.setCustName(ca.getEntCustName());
                hsbToCash.setCustType(CustType.MEMBER_ENT.getCode());
                hsbToCash.setFromHsbAmt(hsbAcc.getAccBalance());
                hsbToCash.setOptCustId(ca.getOptCustId());
                hsbToCash.setOptCustName(ca.getOptCustName());
                hsbToCash.setChannel(Channel.WEB.getCode());
                aoCurrencyConvertService.closeAccountHsbToCash(hsbToCash);
            }

            // 更新销户进度为 2：互生币已转成货币
            memberQuitMapper.updateProgress(ca.getApplyId(), MemberQuitProgress.HSB_TO_HB_COMPLETED.getCode(), ca
                    .getOptCustId());// 2：互生币已转成货币

            return true;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "hsb2hb", "成员企业销户:互生币转货币失败[param=" + ca + "]", e);
            return false;
        }
    }

    /**
     * 银行转账--成员企业销户
     *
     * @param ca 销户参数
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean bankTransOut(CancelAccountParam ca) throws HsException {
        BizLog.biz(this.getClass().getName(), "bankTransOut", "input param:", ca + "");
        HsAssert.notNull(ca, RespCode.PARAM_ERROR, "方法bankTransOut参数对象不能为空");
        try {

            // 查询本地区平台信息
            LocalInfo localInfo = commonService.getAreaPlatInfo();

            // 调用账务系统，查询企业货币信息
            AccountBalance hbAcc = accountSearchService.searchAccNormal(ca.getEntCustId(),
                    AccountType.ACC_TYPE_POINT30110.getCode());// 货币

            // 货币大于0
            if (hbAcc != null && BigDecimalUtils.compareTo(hbAcc.getAccBalance(), "0") > 0) {
                // 调用用户中心,查询银行账户
                BsBankAcctInfo bsBankAcctInfo = ucBankAcctInfoService.findBankAccByAccId(Long.valueOf(ca
                        .getBankAcctId()), UserTypeEnum.ENT.getType());

                // 货币提现手续费
                String bankTransFee = gpTransCashService.getBankTransFee(hbAcc.getAccBalance(), bsBankAcctInfo
                        .getBankCode(), bsBankAcctInfo.getCityNo(), "N");

                // 可转货币金额= 货币金额-货币提现手续费
                String amount = BigDecimalUtils.floorSub(hbAcc.getAccBalance(), bankTransFee).toString();

                TransOut transOut = new TransOut();
                transOut.setCustId(ca.getEntCustId());
                transOut.setHsResNo(ca.getEntResNo());
                transOut.setCustName(ca.getEntCustName());
                transOut.setCustType(CustType.MEMBER_ENT.getCode());
                transOut.setTransReason(TransReason.CLOSE_ACCOUNT.getCode());
                transOut.setAmount(BigDecimalUtils.floor(amount, 2).toString());
                transOut.setCurrencyCode(localInfo.getCurrencyCode());
                transOut.setBankAcctName(bsBankAcctInfo.getBankName());
                transOut.setBankAcctNo(bsBankAcctInfo.getBankAccNo());
                transOut.setBankNo(bsBankAcctInfo.getBankCode());
                transOut.setBankBranch(bsBankAcctInfo.getBankBranch());
                transOut.setBankProvinceNo(bsBankAcctInfo.getProvinceNo());
                transOut.setBankCityNo(bsBankAcctInfo.getCityNo());
                transOut.setVerify(bsBankAcctInfo.getIsValidAccount() == 1);
                transOut.setReqOptId(ca.getOptCustId());
                transOut.setReqOptName(ca.getOptCustName());
                transOut.setReqRemark("成员企业注销");
                transOut.setChannel(Channel.WEB.getCode());

                // 调用账户操作系统,提交银行转账
                aoBankTransferService.saveTransOut(transOut, Long.valueOf(ca.getBankAcctId()));
            }

            // 更新销户进度为 3：银行转账已提交
            memberQuitMapper.updateProgress(ca.getApplyId(), MemberQuitProgress.BANK_TRANS_SUBMITTED.getCode(), ca
                    .getOptCustId());// 3：银行转账已提交
            return true;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "bankTransOut", "成员企业注销:提交银行转账失败[param=" + ca + "]", e);
            return false;
        }

    }

    /**
     * 注销用户中心账户--成员企业销户
     *
     * @param ca 注销参数
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean cancelUcAccount(CancelAccountParam ca) throws HsException {
        BizLog.biz(this.getClass().getName(), "cancelUcAccount", "input param:", ca + "");
        HsAssert.notNull(ca, RespCode.PARAM_ERROR, "方法cancelUcAccount参数对象不能为空");
        try {
            // 调用用户中心，更新企业状态为已注销
            //新增接口 IUCBsEntService.entCancel(String entCustId)  2016-04-11
//            BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
//            bsEntStatusInfo.setEntCustId(ca.getEntCustId());
//            bsEntStatusInfo.setBaseStatus(BsEntBaseStatusEumn.CANCELED.getstatus());// 已注销
            bsEntService.entCancel(ca.getEntCustId(), ca.getOptCustId());

            // 更新销户进度为 4:用户中心已销户
            memberQuitMapper.updateProgress(ca.getApplyId(), MemberQuitProgress.UC_ACCOUNT_CANCELED.getCode(), ca
                    .getOptCustId());// 4:用户中心已销户
            return true;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "cancelUcAccount", "成员企业销户:注销用户中心账户失败[param=" + ca + "]", e);
            return false;
        }
    }

    /**
     * 释放互生号--成员企业销户
     *
     * @param ca 注销参数
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void releaseResNo(CancelAccountParam ca) throws HsException {
        BizLog.biz(this.getClass().getName(), "releaseResNo", "input param:", ca + "");
        HsAssert.notNull(ca, RespCode.PARAM_ERROR, "方法releaseResNo参数对象不能为空");
        try {
            // 释放互生号，更新成员企业互生号状态为未使用
            entResNoService.updateEntResNoStatus(CustType.MEMBER_ENT.getCode(), ca.getEntResNo(), ResStatus.NOT_USED
                    .getCode());

            // 更新销户进度为 5:互生号已释放
            memberQuitMapper.updateProgress(ca.getApplyId(), MemberQuitProgress.RES_NO_RELEASED.getCode(), ca
                    .getOptCustId());// 5:互生号已释放

        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "releaseResNo", "成员企业销户:释放互生号失败 [param=" + ca + "]", e);
        }
    }

    /**
     * 查询未完成的销户（STATUS=4,PROGRESS<>5）
     *
     * @return 未完成的销户列表
     */
    @Override
    public List<CancelAccountParam> getUnCompletedCancelAcList() {
        return memberQuitMapper.getUnCompletedCancelAcList();
    }

    /**
     * 成员企业销户--调度任务
     *
     * @param ca 销户信息
     * @throws HsException
     */
    @Override
    public void cancelAccount4Batch(CancelAccountParam ca) throws HsException {
        if (MemberQuitProgress.APPR_PASS.getCode().equals(ca.getProgress()))// 销户审批通过
        {
            if (this.cancelBmAccount(ca)) {
                if (this.hsb2hb(ca)) {
                    if (this.bankTransOut(ca)) {
                        if (this.cancelUcAccount(ca)) {
                            this.releaseResNo(ca);
                        }
                    }
                }
            }
        } else if (MemberQuitProgress.BM_ACCOUNT_CANCELED.getCode().equals(ca.getProgress()))// 增值系统已销户
        {
            if (this.hsb2hb(ca)) {
                if (this.bankTransOut(ca)) {
                    if (this.cancelUcAccount(ca)) {
                        this.releaseResNo(ca);
                    }
                }
            }
        } else if (MemberQuitProgress.HSB_TO_HB_COMPLETED.getCode().equals(ca.getProgress()))// 互生币已转成货币
        {
            if (this.bankTransOut(ca)) {
                if (this.cancelUcAccount(ca)) {
                    this.releaseResNo(ca);
                }
            }
        } else if (MemberQuitProgress.BANK_TRANS_SUBMITTED.getCode().equals(ca.getProgress()))// 银行转账已提交
        {
            if (this.cancelUcAccount(ca)) {
                this.releaseResNo(ca);
            }
        } else if (MemberQuitProgress.UC_ACCOUNT_CANCELED.getCode().equals(ca.getProgress()))// 用户中心已销户
        {
            this.releaseResNo(ca);
        }
    }

    /**
     * 注销账户
     * @param optInfo 操作信息
     * @return boolean
     * @throws HsException
     * @see com.gy.hsxt.bs.entstatus.interfaces.ICancelAccountService#cancelAccount(String, OptInfo)
     */
    @Override
    public boolean cancelAccount(String quitApplyId, OptInfo optInfo) throws HsException {
        try {
            MemberQuit memberQuit = memberQuitMapper.queryMemberQuitById(quitApplyId);
            CancelAccountParam ca = new CancelAccountParam();
            ca.setApplyId(memberQuit.getApplyId());
            ca.setEntCustId(memberQuit.getEntCustId());
            ca.setEntResNo(memberQuit.getEntResNo());
            ca.setEntCustName(memberQuit.getEntCustName());
            ca.setOptCustId(optInfo.getOptCustId());
            ca.setOptCustName(optInfo.getOptName());
            ca.setBankAcctId(memberQuit.getBankAcctId());

            // 2.注销增值系统账户
            if (cancelBmAccount(ca)) {
                // 3.互生币转货币
                if (hsb2hb(ca)) {
                    // 4.提交银行转账
                    if (bankTransOut(ca)) {
                        // 5.注销用户中心账户
                        if (cancelUcAccount(ca)) {
                            // 6.释放互生号
                            releaseResNo(ca);
                        }
                    }
                }
            }
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "cancelAccount", "成员企业销户处理失败[quitApplyId=" + quitApplyId
                    + ", optInfo=" + optInfo + "]", e);
        }

        return false;
    }

}
