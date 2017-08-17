/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.bean.AccountingEntry;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.common.BizType;
import com.gy.hsxt.ao.common.TransCodeUtil;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.interfaces.IAccountRuleService;
import com.gy.hsxt.ao.interfaces.IAccountingService;
import com.gy.hsxt.ao.interfaces.ICommonService;
import com.gy.hsxt.ao.interfaces.IDubboInvokService;
import com.gy.hsxt.ao.interfaces.IPaymentNotifyService;
import com.gy.hsxt.ao.interfaces.ITransOutNotifyService;
import com.gy.hsxt.ao.mapper.BuyHsbMapper;
import com.gy.hsxt.ao.mapper.TransOutMapper;
import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.WriteBack;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.api.IGPNotifyService;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.api.IGPTransCashService;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.bean.TransCash;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;

/**
 * Dubbo调用服务
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: DubboInvokService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-11-30 下午3:40:48
 * @version V3.0.0
 */
@Service
public class DubboInvokService implements IDubboInvokService, IGPNotifyService {
    /**
     * 帐户操作私有配置参数
     **/
    @Resource
    AoConfig aoConfig;

    /**
     * 注入记帐分解对象
     */
    @Resource
    IAccountingService accountingService;

    /**
     * 帐务系统：帐户查询
     */
    @Resource
    IAccountSearchService accountSearchService;

    /**
     * 帐务系统：实时记帐
     */
    @Resource
    IAccountEntryService accountEntryService;

    /**
     * 兑换互生币mapper
     */
    @Resource
    BuyHsbMapper buyHsbMapper;

    /**
     * 公共服务，获取全局公共数据
     */
    @Resource
    ICommonService commonService;

    /**
     * 银行转帐mapper
     */
    @Resource
    TransOutMapper transOutMapper;

    /**
     * 支付结果业务处理接口
     */
    @Resource
    IPaymentNotifyService paymentNotifyService;

    /**
     * 支付系统：网银支付
     */
    @Resource
    IGPPaymentService gpPaymentService;

    /**
     * 支付系统：单笔提现接口
     */
    @Resource
    IGPTransCashService gpTransCashService;

    /**
     * 提现通知业务处理接口
     */
    @Resource
    ITransOutNotifyService transOutNotifyService;

    @Resource
    IAccountRuleService accoountRuleService;

    /**
     * 用户中心：查询企业重要信息
     */
    @Resource
    IUCBsEntService ucEntService;

    /**
     * 用户中心：查询重要信息变更状态
     */
    @Resource
    IUCBsCardHolderStatusInfoService ucCardHolderStatusInfoService;

    /**
     * 用户中心：实名认证
     */
    @Resource
    IUCBsCardHolderAuthInfoService ucCardAuthService;

    /**
     * 用户中心：查询非持卡人信息
     */
    @Resource
    IUCAsNoCardHolderService ucNoCardHolderServie;

    /**
     * 用户中心：查询非持卡人网络信息
     */
    @Resource
    IUCAsNetworkInfoService ucNetworkInfoService;

    /**
     * 业务系统：年费信息
     */
    @Autowired
    IBSAnnualFeeService bsAnnualFeeService;

    /** 地区平台配送Service **/
    @Autowired
    private LcsClient baseDataService;

    /**
     * 实时记帐：数据拼装
     * 
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#actualAccount()
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void actualAccount(List<AccountingEntry> accountingEntrys) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "实时记帐：数据拼装,params[transNo:" + accountingEntrys + "]");
        // 帐务实时记帐集合对象
        List<AccountEntry> accountEntryList = new ArrayList<AccountEntry>();
        // 记帐数据对象
        AccountEntry accountEntry = null;
        // 记帐分解对象集合不为空
        if (accountingEntrys != null && accountingEntrys.size() > 0)
        {
            // 批次号
            String batchNumber = DateUtil.getCurrentDatetimeNoSign();
            // 循环记帐分解对象集合
            for (Iterator<AccountingEntry> iterator = accountingEntrys.iterator(); iterator.hasNext();)
            {
                AccountingEntry accEntry = (AccountingEntry) iterator.next();
                /**
                 * 初始化记帐数据{
                 */
                accountEntry = new AccountEntry();
                accountEntry.setCustID(accEntry.getCustId());// 客户全局编号
                accountEntry.setHsResNo(accEntry.getHsResNo());// 互生号
                accountEntry.setAccType(accEntry.getAccType());// 账户类型编码
                accountEntry.setAddAmount(accEntry.getAddAmount());// 增向金额
                accountEntry.setSubAmount(accEntry.getDecAmount());// 减向金额
                // 红冲标识:0 正常，1 自动冲正 2（实时记账都是正常的0）
                accountEntry.setWriteBack(WriteBack.NORMAL.getCode());
                accountEntry.setTransType(accEntry.getTransType());// 交易类型
                accountEntry.setTransNo(StringUtils.isNotBlank(accEntry.getAccountTransNo()) ? accEntry.getAccountTransNo() : accEntry.getTransNo());// 交易流水号
                accountEntry.setCustType(accEntry.getCustType());// 客户类型
                accountEntry.setSysEntryNo(accEntry.getAccountingEntryNo());// 系统分录序号
                accountEntry.setBatchNo(batchNumber);// 批次号
                accountEntry.setTransDate(accEntry.getFiscalDate());// 交易时间
                accountEntry.setFiscalDate(accEntry.getFiscalDate());// 会计时间
                // accountEntry.setRelTransType(relTransType);// 关联交易类型
                accountEntry.setRelTransNo(accEntry.getTransNo());// 关联交易流水号
                accountEntry.setSourceTransNo(accEntry.getSourceTransNo());
                accountEntry.setRemark(accEntry.getRemark());// 备注
                accountEntry.setTransSys(aoConfig.getSysName());// 交易系统名称

                // 货币转银行手续费可为负值
                if (StringUtils.isNotBlank(accEntry.getPositiveNegative()) && accEntry.getPositiveNegative().equals("2"))
                {
                    accountEntry.setPositiveNegative(accEntry.getPositiveNegative());
                }

                // 是否传递保底值到帐务
                if (StringUtils.isNotBlank(accEntry.getGuaranteedValue()))
                {
                    // 设置保底值
                    accountEntry.setGuaranteeIntegral(accEntry.getGuaranteedValue());
                }
                /**
                 * }
                 */

                // 将记帐数据添加到集合
                accountEntryList.add(accountEntry);
            }
        }
        try
        {
            // 调用帐务系统实时记帐
            accountEntryService.actualAccount(accountEntryList);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            try
            {
                // 调用AC单笔冲正
                correctSingleAccount(WriteBack.AUTO_REVERSE.getCode(), TransCodeUtil.getReverseCode(accountingEntrys.get(0).getTransType()), accountingEntrys.get(0).getTransNo());
            }
            catch (Exception ex)
            {
                // 冲正如果再出现异常，记录告警日志，
                SystemLog.warn("1", this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR.getCode() + ":调用账务记账时出现异常后再调用账务冲正还是出现异常->" + ex.getMessage());
            }

            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR + ":调用账务实时记账异常：" + accountEntryService.getClass().getName() + "." + "actualAccount(" + JSON.toJSONString(accountEntryList) + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR, "调用账务实时记账异常：" + accountEntryService.getClass().getName() + "." + "actualAccount(" + JSON.toJSONString(accountEntryList) + ")\n" + e);
        }
    }

    /**
     * 支付结果异步通知
     * 
     * @param paymentState
     * @return
     * @throws HsException
     * @see com.gy.hsxt.gp.api.IGPNotifyService#notifyPaymentOrderState(com.gy.hsxt.gp.bean.PaymentOrderState)
     */
    @Override
    public boolean notifyPaymentOrderState(PaymentOrderState paymentState) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "支付结果异步通知,params[" + paymentState + "]");
        // 是否通知成功
        boolean isOk = false;
        try
        {
            // 参数为空
            HsAssert.notNull(paymentState, AOErrorCode.AO_PARAMS_NULL, "支付结果异步通知：实体参数为空");
            // 获取业务订单号
            String orderNo = paymentState.getOrderNo();
            // 业务订单号为空
            HsAssert.hasText(orderNo, AOErrorCode.AO_PARAMS_NULL, "支付结果异步通知：支付系统异步通知结果中的业务订单号为空");
            // 查询业务订单
            BuyHsb buyHsb = buyHsbMapper.findBuyHsbInfo(orderNo);
            // 校验业务订单：业务订单不存在
            HsAssert.notNull(buyHsb, AOErrorCode.AO_NOT_QUERY_DATA, "支付结果异步通知：业务订单号对应的兑换互生币交易单不存在");
            // 返回处理结果
            isOk = paymentNotifyService.updatePayStatus(paymentState);
        }
        catch (HsException e)
        {
            // 因为支付系统不接受异常，所以只进行日志记录
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
        }
        catch (Exception e)
        {
            // 因为支付系统不接受异常，所以只进行日志记录
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), "AO处理支付结果通知异常：" + paymentNotifyService.getClass().getName() + "." + "updatePayStatus(" + JSON.toJSONString(paymentState) + ")\n", e);
        }
        return isOk;
    }

    /**
     * 转账结果异步通知
     * 
     * @param transCashOrderState
     * @return
     * @throws HsException
     * @see com.gy.hsxt.gp.api.IGPNotifyService#notifyTransCashOrderState(com.gy.hsxt.gp.bean.TransCashOrderState)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean notifyTransCashOrderState(TransCashOrderState transCashOrderState) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "转账结果异步通知,params[" + JSON.toJSONString(transCashOrderState) + "]");
        // 是否通知成功
        boolean isOk = false;
        try
        {
            // 实体参数为空
            HsAssert.notNull(transCashOrderState, AOErrorCode.AO_PARAMS_NULL, "转账结果异步通知：实体参数为空");
            // 获取业务订单号
            String transNo = transCashOrderState.getOrderNo();
            // 交易单号为空
            HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "转账结果异步通知：交易单号为空");
            // 校验原交易单是否存在
            TransOut transOut = transOutMapper.findTransOutInfo(transNo);
            // 校验业务订单：业务订单不存在
            HsAssert.notNull(transOut, AOErrorCode.AO_NOT_QUERY_DATA, "转账结果异步通知：业务订单号对应的银行转帐交易单不存在");
            // 转帐通知更新业务
            isOk = transOutNotifyService.updateTransStatus(transCashOrderState);
        }
        catch (HsException e)
        {
            // 因为支付系统不接受异常，所以只进行日志记录
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
        }
        catch (Exception e)
        {
            // 因为支付系统不接受异常，所以只进行日志记录
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), "AO处理转账结果通知异常：" + transOutNotifyService.getClass().getName() + "." + "updateTransStatus(" + JSON.toJSONString(transCashOrderState) + ")\n", e);
        }
        return isOk;
    }

    /**
     * 获取网银支付URL
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param jumpUrl
     *            回跳地址
     * @param privObligate
     *            私有数据
     * @return 网银支付URL
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getNetPayUrl(com.gy.hsxt.ao.bean.BuyHsb,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public String getNetPayUrl(BuyHsb buyHsb, String jumpUrl, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取网银支付URL,params[" + buyHsb + ",jumpUrl:" + jumpUrl + ",privObligate:" + privObligate + "]");
        // 支付URL
        String payUrl = "";
        NetPayBean netPayBean = null;
        try
        {
            // 获取本地信息
            LocalInfo localInfo = commonService.getLocalInfo();
            // 银联支付实体类
            netPayBean = new NetPayBean();
            netPayBean.setMerId(aoConfig.getMerchantNo());// 商户号
            netPayBean.setOrderNo(buyHsb.getTransNo());// 业务订单号
            netPayBean.setOrderDate(DateUtil.StringToDate(buyHsb.getReqTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            netPayBean.setTransAmount(StringUtils.isBlank(buyHsb.getCashAmt()) ? "0" : buyHsb.getCashAmt());// 交易金额
            netPayBean.setTransDesc(buyHsb.getRemark());// 交易描述
            netPayBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            netPayBean.setJumpUrl(jumpUrl);// 回跳地址
            netPayBean.setPrivObligate(privObligate);// 私有数据

            // 调用支付系统：获取网银支付URL接口
            payUrl = gpPaymentService.getNetPayUrl(netPayBean, aoConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_GP_GET_PAY_URL_ERROR + ":调用支付系统获取网银支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getNetPayUrl(" + JSON.toJSONString(netPayBean) + "," + aoConfig.getSysName() + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_GP_GET_PAY_URL_ERROR, "调用支付系统获取网银支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getNetPayUrl(" + JSON.toJSONString(netPayBean) + "," + aoConfig.getSysName() + ")\n" + e);
        }
        return payUrl;
    }

    /**
     * 开通快捷支付并获取URL
     * 
     * @param openQuickPayBean
     *            开通快捷支付实体类
     * @return 首次快捷支付URL
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getOpenQuickPayUrl(com.gy.hsxt.ao.bean.OpenQuickPayBean)
     */
    @Override
    public String getOpenQuickPayUrl(OpenQuickPayBean openQuickPayBean) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "开通快捷支付并获取URL,params[" + JSON.toJSONString(openQuickPayBean) + "]");
        String payUrl = "";
        // 银联快捷支付
        FirstQuickPayBean firstQuickPayBean = null;

        try
        {
            firstQuickPayBean = new FirstQuickPayBean();
            // 从配置文件中取互生商户号
            openQuickPayBean.setMerId(aoConfig.getMerchantNo());
            // 复制属性
            BeanUtils.copyProperties(openQuickPayBean, firstQuickPayBean);
            // 货币代码
            firstQuickPayBean.setCurrencyCode(commonService.getLocalInfo().getCurrencyCode());
            // 调用支付系统：获取首次快捷支付URL接口
            payUrl = gpPaymentService.getFirstQuickPayUrl(firstQuickPayBean, aoConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_GP_GET_PAY_URL_ERROR + ":调用支付系统开通快捷支付并获取URL异常：" + gpPaymentService.getClass().getName() + "." + "getFirstQuickPayUrl(" + JSON.toJSONString(firstQuickPayBean) + "," + aoConfig.getSysName() + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_GP_GET_PAY_URL_ERROR, "调用支付系统开通快捷支付并获取URL异常：" + gpPaymentService.getClass().getName() + "." + "getFirstQuickPayUrl(" + JSON.toJSONString(firstQuickPayBean) + "," + aoConfig.getSysName() + ")\n" + e);
        }
        return payUrl;
    }

    /**
     * 获取快捷支付URL
     * 
     * @param transNo
     *            交易流水号
     * @param bindingNo
     *            签约号
     * @param smsCode
     *            短信验证码
     * @return 快捷支付URL
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getQuickPayUrl(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public String getQuickPayUrl(String transNo, String bindingNo, String smsCode) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付URL,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]");
        String payUrl = "";
        // 银联支付实体
        QuickPayBean quickPayBean = new QuickPayBean();
        try
        {
            // 从配置文件中取互生商户号
            quickPayBean.setMerId(aoConfig.getMerchantNo());
            quickPayBean.setOrderNo(transNo);// 业务订单号
            quickPayBean.setBindingNo(bindingNo);// 签约号
            quickPayBean.setSmsCode(smsCode);// 短信验证码

            // 调用支付系统：获取快捷支付URL接口
            payUrl = gpPaymentService.getQuickPayUrl(quickPayBean, aoConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_GP_GET_PAY_URL_ERROR + ":调用支付系统获取快捷支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPayUrl(" + JSON.toJSONString(quickPayBean) + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_GP_GET_PAY_URL_ERROR, "调用支付系统获取快捷支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPayUrl(" + JSON.toJSONString(quickPayBean) + "," + aoConfig.getSysName() + ")\n" + e);
        }
        return payUrl;
    }

    /**
     * 获取手机支付TN
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param privObligate
     *            私有数据
     * @return TN交易流水号
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getMobilePayTn(com.gy.hsxt.ao.bean.BuyHsb,
     *      java.lang.String)
     */
    @Override
    public String getMobilePayTn(BuyHsb buyHsb, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取手机支付TN码,params[" + buyHsb + ",privObligate:" + privObligate + "]");
        String tnTransNo = "";
        // 手机支付实体
        MobilePayBean mobilePayBean = null;
        try
        {
            mobilePayBean = new MobilePayBean();
            // 从配置文件中取互生商户号
            mobilePayBean.setMerId(aoConfig.getMerchantNo());
            mobilePayBean.setOrderNo(buyHsb.getTransNo());// 业务订单号
            mobilePayBean.setOrderDate(DateUtil.StringToDate(buyHsb.getReqTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            mobilePayBean.setTransAmount(StringUtils.isBlank(buyHsb.getBuyHsbAmt()) ? "0" : buyHsb.getBuyHsbAmt());// 交易金额
            mobilePayBean.setTransDesc(buyHsb.getRemark());// 交易描述
            mobilePayBean.setCurrencyCode(commonService.getLocalInfo().getCurrencyCode());// 币种
            mobilePayBean.setPrivObligate(privObligate);// 私有数据
            // 调用支付系统：获取手机支付TN码接口
            tnTransNo = gpPaymentService.getMobilePayTn(mobilePayBean, aoConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_GP_GET_MOBILE_PAY_TN_CODE_ERROR + ":调用支付系统获取手机支付TN码异常：" + gpPaymentService.getClass().getName() + "." + "getMobilePayTn(" + JSON.toJSONString(mobilePayBean) + "," + aoConfig.getSysName() + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_GP_GET_MOBILE_PAY_TN_CODE_ERROR, "调用支付系统获取手机支付TN码异常：" + gpPaymentService.getClass().getName() + "." + "getMobilePayTn(" + JSON.toJSONString(mobilePayBean) + "," + aoConfig.getSysName() + ")\n" + e);
        }
        return tnTransNo;
    }

    /**
     * 获取快捷支付短信验证码
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param merId
     *            商户号
     * @param bindingNo
     *            签约号
     * @param privObligate
     *            私有数据
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getQuickPaySmsCode(com.gy.hsxt.ao.bean.BuyHsb,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void getQuickPaySmsCode(BuyHsb buyHsb, String bindingNo, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付短信验证码,params[" + buyHsb + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]");
        // 银联快捷支付实体
        QuickPaySmsCodeBean quickPaySmsCodeBean = null;
        try
        {
            quickPaySmsCodeBean = new QuickPaySmsCodeBean();
            quickPaySmsCodeBean.setMerId(aoConfig.getMerchantNo());// 商户号
            quickPaySmsCodeBean.setOrderNo(buyHsb.getTransNo());// 业务订单号
            quickPaySmsCodeBean.setOrderDate(DateUtil.StringToDate(buyHsb.getReqTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            quickPaySmsCodeBean.setBindingNo(bindingNo);// 签约号
            quickPaySmsCodeBean.setTransAmount(StringUtils.isBlank(buyHsb.getBuyHsbAmt()) ? "0" : buyHsb.getBuyHsbAmt());// 交易金额
            quickPaySmsCodeBean.setTransDesc(buyHsb.getRemark());// 交易描述
            quickPaySmsCodeBean.setCurrencyCode(commonService.getLocalInfo().getCurrencyCode());// 币种
            quickPaySmsCodeBean.setPrivObligate(privObligate);// 私有数据
            // 调用支付系统：获取首次快捷支付URL接口
            gpPaymentService.getQuickPaySmsCode(quickPaySmsCodeBean, aoConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_GP_GET_QUICK_PAY_SMS_CODE_ERROR + ":调用支付系统：获取快捷支付短信验证码异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPaySmsCode(" + JSON.toJSONString(quickPaySmsCodeBean) + "," + aoConfig.getSysName() + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_GP_GET_QUICK_PAY_SMS_CODE_ERROR, "调用支付系统：获取快捷支付短信验证码异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPaySmsCode(" + JSON.toJSONString(quickPaySmsCodeBean) + "," + aoConfig.getSysName() + ")\n" + e);
        }
    }

    /**
     * 获取帐务余额
     * 
     * @param custId
     *            客户号
     * @param accountType
     *            帐户类型
     * @return 账户余额实体
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getAccountBlance(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public AccountBalance getAccountBlance(String custId, String accountType) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取帐户余额,params[custId:" + custId + ",accountType:" + accountType + "]");
        // 调用帐务系统查询帐务货币帐户余额
        AccountBalance accountBalance = null;
        try
        {
            // 客户号为空
            HsAssert.hasText(custId, AOErrorCode.AO_PARAMS_NULL, "获取帐务余额：客户号参数为空");
            // 帐户类型为空
            HsAssert.hasText(accountType, AOErrorCode.AO_PARAMS_NULL, "获取帐务余额：帐户类型参数为空");

            // 调用帐务系统查询帐户余额
            accountBalance = accountSearchService.searchAccNormal(custId, accountType);
            // 未查询到数据
            HsAssert.notNull(accountBalance, AOErrorCode.AO_INVOKE_AC_NOT_QUERY_DATA, "获取帐务余额：调用帐务系统查询余额未查询到记录");
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_AC_QUERY_BALANCE_ERROR + ":调用帐务系统获取帐务余额异常：" + accountSearchService.getClass().getName() + "." + "searchAccNormal(" + custId + "," + accountType + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_AC_QUERY_BALANCE_ERROR, "调用帐务系统获取帐务余额异常：" + accountSearchService.getClass().getName() + "." + "searchAccNormal(" + custId + "," + accountType + ")\n" + e);
        }
        return accountBalance;
    }

    /**
     * 查询客户状态信息
     * 
     * @param custType
     *            客户类型
     * @param custId
     *            客户号
     * @return 客户状态信息
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getCustInfo(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public BsEntStatusInfo getCustInfo(Integer custType, String custId) {
        // 企业状态信息
        BsEntStatusInfo entStatusInfo = null;
        // 校验用户状态：持卡人或企业是否重要信息变更期间
        try
        {
            // 调用用户中心查询企业状态信息
            entStatusInfo = ucEntService.searchEntStatusInfo(custId);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage() + ":" + ucEntService.getClass().getName() + "." + "searchEntStatusInfo(" + custId + ")", e);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_TO_UC_QUERY_ENT_INFO_ERROR.getCode() + "查询客户状态信息：调用用户中心查询企业状态信息异常|" + ucEntService.getClass().getName() + "." + "searchEntStatusInfo(" + custId + ")", e);
            throw new HsException(AOErrorCode.AO_TO_UC_QUERY_ENT_INFO_ERROR.getCode(), "查询客户状态信息：调用用户中心查询企业状态信息异常|" + ucEntService.getClass().getName() + "." + "searchEntStatusInfo(" + custId + ")" + "\n" + e);
        }
        return entStatusInfo;
    }

    /**
     * 单笔冲正红冲记账
     * 
     * @param writeBack
     *            红冲标识
     * @param transType
     *            交易类型
     * @param transNo
     *            交易流水号
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#correctSingleAccount(com.gy.hsxt.ac.bean.AccountEntry)
     */
    @Override
    public void correctSingleAccount(Integer writeBack, String transType, String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "单笔冲正红冲记账,params[writeBack:" + writeBack + ",transType:" + transType + ",transNo:" + transNo + "]");
        AccountWriteBack accountWriteBack = null;
        try
        {
            // 交易流水号为空
            HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "单笔冲正红冲记账：交易流水号为空");
            // 初始化冲正数据
            accountWriteBack = new AccountWriteBack();
            accountWriteBack.setWriteBack(writeBack);// 红冲标识:0 正常，1 自动冲正
                                                     // 2（实时记账都是正常的0）
            accountWriteBack.setTransType(transType);// 交易类型
            accountWriteBack.setRelTransNo(transNo);// 业务编号

            // 调用帐务冲正
            accountEntryService.correctSingleAccount(accountWriteBack);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_AC_CORRECT_SINGAL_ERROR + ":调用帐务系统单笔冲正红冲记账异常：" + accountEntryService.getClass().getName() + "." + "correctSingleAccount(" + JSON.toJSONString(accountWriteBack) + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_AC_CORRECT_SINGAL_ERROR, "调用帐务系统单笔冲正红冲记账异常：" + accountEntryService.getClass().getName() + "." + "correctSingleAccount(" + JSON.toJSONString(accountWriteBack) + ")\n" + e);
        }
    }

    /**
     * 提交单笔转帐
     * 
     * @param transOutInfo
     *            转帐信息
     */
    @Override
    public boolean commitTransCash(TransOut transOutInfo) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "提交单笔转帐,params[" + transOutInfo + "]");
        // 参数为空
        HsAssert.notNull(transOutInfo, AOErrorCode.AO_PARAMS_NULL, "提交单笔转帐：实体参数为空");
        TransCash transCash = null;
        try
        {
            // 初始化支付系统提现参数实体类
            transCash = new TransCash();
            transCash.setOrderNo(transOutInfo.getTransNo());// 业务流水号
            transCash.setOrderDate(DateUtil.StringToDate(transOutInfo.getReqTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务申请时间
            transCash.setTransAmount(transOutInfo.getAmount());// 交易金额
            transCash.setCurrencyCode(transOutInfo.getCurrencyCode()); // 币种
            transCash.setInAccNo(transOutInfo.getBankAcctNo());// 收款人账户
            transCash.setInAccName(transOutInfo.getBankAcctName()); // 收款人账户名
            transCash.setInAccBankName(transOutInfo.getBankBranch());// 收款人开户行名称
            transCash.setInAccBankNode(transOutInfo.getBankNo());// 收款人开户银行代码
            transCash.setInAccProvinceCode(transOutInfo.getBankProvinceNo()); // 收款账户银行开户省代码或省名称
            transCash.setInAccCityCode(transOutInfo.getBankCityNo());// 收款账户开户城市代码
            // 添加城市名称到支付系统 16.6.12
            LocalInfo localInfo = baseDataService.getLocalInfo();
            if (localInfo != null)
            {
                City city = baseDataService.getCityById(localInfo.getCountryNo(), transOutInfo.getBankProvinceNo(), transOutInfo.getBankCityNo());
                if (city != null)
                {
                    transCash.setInAccCityName(city.getCityName());
                }
            }

            // 调用支付系统：单笔提现
            return gpTransCashService.transCash(aoConfig.getMerchantNo(), transCash, aoConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_GP_SINGAL_TRANS_ERROR + ":调用支付系统提交单笔转帐异常：" + gpTransCashService.getClass().getName() + "." + "transCash(" + aoConfig.getMerchantNo() + "," + JSON.toJSONString(transCash) + "," + aoConfig.getSysName() + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_GP_SINGAL_TRANS_ERROR, "调用支付系统提交单笔转帐异常：" + gpTransCashService.getClass().getName() + "." + "transCash(" + aoConfig.getMerchantNo() + "," + JSON.toJSONString(transCash) + "," + aoConfig.getSysName() + ")\n" + e);
        }
    }

    /**
     * 检查客户信息
     * 
     * @param custType
     *            客户类型
     * @param custId
     *            客户号
     * @param bizType
     *            业务类型
     * @param isImportant
     *            是否校验重要信息变更
     * @param isOwnFee
     *            是否校验年费
     * @param isTrueNameReg
     *            是否校验实名注册
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#checkCustInfo(java.lang.Integer,
     *      java.lang.String, java.lang.Integer, boolean, boolean, boolean)
     */
    @Override
    public void checkCustInfo(Integer custType, String custId, Integer bizType, boolean isImportant, boolean isOwnFee, boolean isTrueNameReg) {
        // 变更状态
        boolean isChanging = false;
        // 企业状态信息
        BsEntStatusInfo entStatusInfo = null;
        // 货币转银行
        if (BizType.CASH_TO_BANK.getCode() == bizType)
        {
            // 校验用户状态：持卡人或企业是否重要信息变更期间
            if (CustType.PERSON.getCode() == custType)// 持卡人
            {
                try
                {
                    // 查询持卡人重要信息变更状态
                    isChanging = ucCardHolderStatusInfoService.isMainInfoApplyChanging(custId);
                    // 重要信息变更期
                    HsAssert.isTrue(!isChanging, AOErrorCode.AO_CHANGING_IMPORTENT_INFO, "重要信息变更期");
                    // 是否验证实名注册
                    if (isTrueNameReg)
                    {
                        if (!isPassRealName(custType, custId))
                        {
                            HsAssert.isTrue(false, AOErrorCode.AO_TO_UC_NO_REAL_NAME_REG, "未实名注册");
                        }
                    }
                    return;
                }
                catch (HsException e)
                {
                    throw e;
                }
                catch (Exception e)
                {
                    SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_TO_UC_QUERY_PERSON_INFO_ERROR.getCode() + "查询持卡人重要信息变更状态异常|" + ucCardHolderStatusInfoService.getClass().getName() + "." + "isMainInfoApplyChanging(" + custId + ")", e);
                    throw new HsException(AOErrorCode.AO_TO_UC_QUERY_PERSON_INFO_ERROR.getCode(), "查询持卡人重要信息变更状态异常|" + ucCardHolderStatusInfoService.getClass().getName() + "." + "isMainInfoApplyChanging(" + custId + ")" + "\n" + e);
                }
            }
            if (CustType.SERVICE_CORP.getCode() == custType// 服务公司
                    || CustType.TRUSTEESHIP_ENT.getCode() == custType// 托管企业
                    || CustType.MEMBER_ENT.getCode() == custType // 成员企业
            )
            {
                try
                {
                    // 调用用户中心查询企业状态信息
                    entStatusInfo = ucEntService.searchEntStatusInfo(custId);

                    // 未查询到企业信息
                    HsAssert.notNull(entStatusInfo, AOErrorCode.AO_NOT_QUERY_DATA, "未查询到企业状态信息");
                    // 企业状态为重要信息变更期
                    HsAssert.isTrue(entStatusInfo.getIsMaininfoChanged() == 1 ? false : true, AOErrorCode.AO_CHANGING_IMPORTENT_INFO, "重要信息变更期");

                    // 是否校验欠年费
                    if (isOwnFee)
                    {
                        // 欠年费不可货币转银行
                        if (CustType.SERVICE_CORP.getCode() == custType// 服务公司
                                || CustType.TRUSTEESHIP_ENT.getCode() == custType// 托管企业
                        )
                        {
                            HsAssert.isTrue(entStatusInfo.getIsOweFee() == 1 ? false : true, AOErrorCode.AO_OWE_FEE_CAN_NOT_PROXY_HSB, "尚有年费未缴清");
                        }
                    }
                    return;
                }
                catch (HsException e)
                {
                    throw e;
                }
                catch (Exception e)
                {
                    SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_TO_UC_QUERY_ENT_INFO_ERROR.getCode() + "调用用户中心查询企业状态信息异常|" + ucEntService.getClass().getName() + "." + "searchEntStatusInfo(" + custId + ")", e);
                    throw new HsException(AOErrorCode.AO_TO_UC_QUERY_ENT_INFO_ERROR.getCode(), "调用用户中心查询企业状态信息异常|" + ucEntService.getClass().getName() + "." + "searchEntStatusInfo(" + custId + ")" + "\n" + e);
                }
            }
        }

        // 代兑互生币
        if (BizType.PROXY_BUY_HSB.getCode() == bizType)
        {
            if (CustType.TRUSTEESHIP_ENT.getCode() == custType// 托管企业
                    || CustType.MEMBER_ENT.getCode() == custType // 成员企业
            )
            {
                // 调用用户中心查询企业状态信息
                entStatusInfo = ucEntService.searchEntStatusInfo(custId);

                // 未查询到企业信息
                HsAssert.notNull(entStatusInfo, AOErrorCode.AO_NOT_QUERY_DATA, "未查询到企业状态信息");
                // 企业状态为重要信息变更期
                HsAssert.isTrue(entStatusInfo.getIsMaininfoChanged() == 1 ? false : true, AOErrorCode.AO_CHANGING_IMPORTENT_INFO, "重要信息变更期");

                // 欠年费不可代兑互生币
                if (isOwnFee)
                {
                    HsAssert.isTrue(entStatusInfo.getIsOweFee() == 1 ? false : true, AOErrorCode.AO_OWE_FEE_CAN_NOT_PROXY_HSB, "尚有年费未缴清");
                }
                return;
            }
        }
    }

    /**
     * 校验帐户余额是否足够
     * 
     * @param custId
     *            客户号
     * @param accountType
     *            帐户类型
     * @param appAmount
     *            申请金额
     * @return 结果等于0 为相等 结果大于0(正数) appAmount大 结果小于0(负数) appAmount小
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#isBlanceInsufficient(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public int isBlanceInsufficient(String custId, String accountType, String appAmount) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "校验帐户余额是否足够,params[custId:" + custId + ",accountType:" + accountType + ",appAmount:" + appAmount + "]");
        // 调用帐务系统查询帐务货币帐户余额
        AccountBalance accountBalance = null;
        try
        {
            // 客户号为空
            HsAssert.hasText(custId, AOErrorCode.AO_PARAMS_NULL, "获取帐务余额：客户号参数为空");
            // 帐户类型为空
            HsAssert.hasText(custId, AOErrorCode.AO_PARAMS_NULL, "获取帐务余额：帐户类型参数为空");

            // 调用帐务系统查询帐户余额
            accountBalance = accountSearchService.searchAccNormal(custId, accountType);
            HsAssert.notNull(accountBalance, AOErrorCode.AO_INVOKE_AC_NOT_QUERY_DATA, "获取帐务余额：调用帐务系统未查询到数据");
            // 帐户余额小于申请金额
            return BigDecimalUtils.compareTo(appAmount, accountBalance.getAccBalance());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_AC_QUERY_BALANCE_ERROR.getCode() + ":调用帐务系统：获取帐务余额异常," + accountSearchService.getClass().getName() + "." + "searchAccNormal(" + custId + "," + accountType + ")\n", e);
            throw new HsException(AOErrorCode.AO_INVOKE_AC_QUERY_BALANCE_ERROR, "调用帐务系统：获取帐务余额异常," + accountSearchService.getClass().getName() + "." + "searchAccNormal(" + custId + "," + accountType + ")\n" + e);
        }
    }

    /**
     * 校验是否通过实名认证
     * 
     * @param custType
     *            客户类型
     * @param custId
     *            客户号
     * @return true 通过,false 未通过
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#isPassRealName(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public boolean isPassRealName(Integer custType, String custId) {
        BsRealNameAuth realNameAuth = null;
        try
        {
            // 校验消费者兑换互生币限制
            if (CustType.PERSON.getCode() == custType)
            {
                // 调用用户中心：查询持卡人认证信息
                realNameAuth = ucCardAuthService.searchRealNameAuthInfo(custId);
                // 未查询到企业信息
                HsAssert.notNull(realNameAuth, AOErrorCode.AO_NOT_QUERY_DATA, "未查询到认证信息");

                // 实名认证状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
                return Integer.parseInt(realNameAuth.getRealNameStatus()) > 1 ? true : false;
            }
            if (CustType.NOT_HS_PERSON.getCode() == custType)
            {
                // 非持卡人注册信息
                // AsNoCardHolder noCardHolder =
                // ucNoCardHolderServie.searchNoCardHolderInfoByCustId(custId);
                // 非持卡人网络信息
                AsNetworkInfo networkInfo = ucNetworkInfoService.searchByCustId(custId);
                // 未查询到企业信息
                HsAssert.notNull(networkInfo, AOErrorCode.AO_NOT_QUERY_DATA, "未查询到非持卡人信息");
                // 未填写真实姓名
                if (StringUtils.isBlank(networkInfo.getName()))
                {
                    return false;
                }
                else
                // 已填写真实姓名
                {
                    return true;
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            String funStr = CustType.PERSON.getCode() == custType ? "searchRealNameAuthInfo(" + custId + ")" : "searchNoCardHolderInfoByMobile(" + custId + ")";
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_TO_UC_QUERY_PERSON_INFO_ERROR.getCode() + ":调用用户中心：查询用户信息异常," + ucCardAuthService.getClass().getName() + "." + funStr, e);
            throw new HsException(AOErrorCode.AO_TO_UC_QUERY_PERSON_INFO_ERROR, "调用帐务系统：获取帐务余额异常," + ucCardAuthService.getClass().getName() + "." + funStr + e);
        }
        return false;
    }
}
