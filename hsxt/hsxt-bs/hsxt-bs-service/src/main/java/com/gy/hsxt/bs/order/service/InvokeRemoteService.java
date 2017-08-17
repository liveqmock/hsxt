/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.gy.hsxt.bs.common.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.TransCodeUtil;
import com.gy.hsxt.bs.common.bean.PayUrl;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.WriteBack;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.FirstQuickPayBean;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.NetPayBean;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganNetPayBean;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;

/**
 * 远程调用服务类
 * 
 * @Package: com.gy.hsxt.bs.common
 * @ClassName: InvokeRemoteMethod
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-15 上午9:49:00
 * @version V3.0.0
 */
@Service(value = "invokeRemoteService")
public class InvokeRemoteService implements IInvokeRemoteService {

    /**
     * 帐户操作私有配置参数
     **/
    @Resource
    BsConfig bsConfig;

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
     * 全局配置接口
     */
    @Autowired
    ICommonService commonService;

    /**
     * 用户中心企业Service
     */
    @Autowired
    IUCBsEntService bsEntService;

    /**
     * 支付系统：网银支付
     */
    @Resource
    IGPPaymentService gpPaymentService;

    /**
     * 实时记帐：数据拼装
     * 
     * @param accountDetails
     *            记帐分解对象列表
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService#actualAccount(java.util.List)
     */
    @Override
    public void actualAccount(List<AccountDetail> accountDetails) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "实时记帐,params[" + JSON.toJSONString(accountDetails) + "]");
        // 帐务实时记帐集合对象
        List<AccountEntry> accountEntryList = new ArrayList<AccountEntry>();
        // 记帐数据对象
        AccountEntry accountEntry = null;
        try
        {
            // 记帐分解对象集合不为空
            if (accountDetails != null && accountDetails.size() > 0)
            {
                // 循环记帐分解对象集合
                for (Iterator<AccountDetail> iterator = accountDetails.iterator(); iterator.hasNext();)
                {
                    AccountDetail accEntry = (AccountDetail) iterator.next();
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
                    accountEntry.setTransNo(StringUtils.isBlank(accEntry.getAccountTransNo()) ? accEntry.getBizNo() : accEntry.getAccountTransNo());// 交易流水号
                    accountEntry.setCustType(accEntry.getCustType());// 客户类型
                    accountEntry.setSysEntryNo(accEntry.getAccountNo());// 系统分录序号
                    accountEntry.setBatchNo(DateUtil.getCurrentDatetimeNoSign());// 批次号
                    accountEntry.setTransDate(accEntry.getFiscalDate());// 交易时间
                    accountEntry.setFiscalDate(accEntry.getFiscalDate());// 会计时间
                    // accountEntry.setRelTransType(relTransType);// 关联交易类型
                    accountEntry.setRelTransNo(accEntry.getBizNo());// 关联交易流水号
                    accountEntry.setRemark(accEntry.getRemark());// 备注
                    accountEntry.setTransSys(bsConfig.getSysName());// 交易系统名称

                    // 如果帐户类型为积分，需要传递保底值到帐务
                    if (accEntry.getAccType().equals(AccountType.ACC_TYPE_POINT10110.getCode()))
                    {
                        // 设置积分保底值
                        accountEntry.setGuaranteeIntegral(accEntry.getGuaranteedValue());
                    }
                    /**
                     * }
                     */

                    // 将记帐数据添加到集合
                    accountEntryList.add(accountEntry);
                }
            }

            // 调用帐务系统实时记帐
            accountEntryService.actualAccount(accountEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            try
            {
                // 调用AC单笔冲正
                correctSingleAccount(WriteBack.AUTO_REVERSE.getCode(), TransCodeUtil.getReverseCode(accountDetails.get(0).getTransType()), StringUtils.isBlank(accountDetails.get(0).getAccountTransNo()) ? accountDetails.get(0).getBizNo() : accountDetails.get(0).getAccountTransNo());
            }
            catch (Exception ex)
            {
                // 冲正如果再出现异常，记录告警日志，
                SystemLog.warn("1", this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR.getCode() + ":调用账务记账时出现异常后再调用账务冲正还是出现异常->" + ex.getMessage());
            }
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR.getCode() + ":调用账务实时记账异常,params[" + JSON.toJSONString(accountDetails) + "]", e);
            throw new HsException(BSRespCode.BS_INVOKE_ACCOUNT_ACTUAL_ACCOUNT_ERROR.getCode(), "调用账务实时记账异常,params[" + JSON.toJSONString(accountDetails) + "]" + e);
        }
    }

    /**
     * 获取网银支付URL
     * 
     * @param order
     *            兑换互生币信息
     * @param jumpUrl
     *            回跳地址
     * @return 网银支付URL
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService#getNetPayUrl(com.gy.hsxt.bs.bean.order.Order,
     *      java.lang.String)
     */
    @Override
    public String getNetPayUrl(Order order, String jumpUrl) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取网银支付URL,params[" + order + ",jumpUrl:" + jumpUrl + "]");
        // 支付URL
        String payUrl = "";
        try
        {
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            // 本地信息为空
            HsAssert.notNull(localInfo, BSRespCode.BS_NOT_QUERY_DATA, "获取网银支付URL：未获取到本地信息");

            // 银联支付实体类
            NetPayBean netPayBean = new NetPayBean();
            netPayBean.setMerId(bsConfig.getMerchantNo());// 商户号
            netPayBean.setOrderNo(order.getOrderNo());// 业务订单号
            netPayBean.setOrderDate(DateUtil.StringToDate(order.getPayingTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            netPayBean.setTransAmount(order.getOrderCashAmount());// 交易金额
            netPayBean.setTransDesc("获取网银支付URL");// 交易描述
            netPayBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            netPayBean.setJumpUrl(jumpUrl);// 回跳地址
            netPayBean.setPrivObligate("");// 私有数据

            // 调用支付系统：获取网银支付URL接口
            payUrl = gpPaymentService.getNetPayUrl(netPayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + ":获取网银支付URL异常,params[" + order + ",jumpUrl:" + jumpUrl + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "获取网银支付URL异常,params[" + order + ",jumpUrl:" + jumpUrl + "]" + e);
        }
        return payUrl;
    }

    /**
     * 获取银行卡支付URL
     * 
     * @param order
     *            兑换互生币信息
     * @param jumpUrl
     *            回跳地址
     * @return 银行卡支付URL
     * @throws HsException
     */
    /**
     * @param order
     * @param jumpUrl
     * @return
     * @throws HsException
     */
    @Override
    public String getCardPayUrl(PayUrl payInfo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取银行卡支付URL,params[" + payInfo + "]");
        // 支付URL
        String payUrl = "";
        PinganNetPayBean netPayBean = null;
        try
        {
            // 获取本地信息
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            // 银联支付实体类
            netPayBean = new PinganNetPayBean();
            netPayBean.setMerId(bsConfig.getMerchantNo());// 商户号
            netPayBean.setOrderNo(payInfo.getOrder().getOrderNo());// 业务订单号
            netPayBean.setOrderDate(DateUtil.StringToDate(payInfo.getOrder().getPayingTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            netPayBean.setTransAmount(payInfo.getOrder().getOrderCashAmount());// 交易金额
            netPayBean.setTransDesc(payInfo.getTransDesc());// 交易描述
            netPayBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            if(StringUtils.isNoneBlank(payInfo.getCallbackUrl()))
            {
                netPayBean.setJumpUrl(payInfo.getCallbackUrl());// 回跳地址
            }else
            {
                LocalInfo info = commonService.getAreaPlatInfo();
                netPayBean.setJumpUrl(info.getWebPayJumpUrl());// 回跳地址
            }
            netPayBean.setPrivObligate(payInfo.getPrivObligate());// 私有数据
            netPayBean.setGoodsName(payInfo.getGoodsName());// 交易购买商品名称
            netPayBean.setPaymentUIType(2);// 支付页面显示类型
            // 调用支付系统：获取银行卡支付URL接口
            payUrl = gpPaymentService.getPinganNetPayUrl(netPayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + ":获取银行卡支付URL异常,params[" + payInfo + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "获取银行卡支付URL异常,params[" + payInfo + "]" + e);
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
     * @see com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService#getOpenQuickPayUrl()
     */
    @Override
    public String getOpenQuickPayUrl(OpenQuickPayBean openQuickPayBean) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "开通快捷支付并获取URL,params[" + openQuickPayBean + "]");
        String payUrl = "";

        try
        {
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            // 银联快捷支付
            FirstQuickPayBean firstQuickPayBean = new FirstQuickPayBean();
            // 复制属性
            BeanUtils.copyProperties(openQuickPayBean, firstQuickPayBean);
            // 设置商户号
            firstQuickPayBean.setMerId(bsConfig.getMerchantNo());
            firstQuickPayBean.setCurrencyCode(localInfo.getCurrencyCode());

            // 调用支付系统：获取首次快捷支付URL接口
            payUrl = gpPaymentService.getFirstQuickPayUrl(firstQuickPayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + ":开通快捷支付并获取URL异常,params[" + openQuickPayBean + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "开通快捷支付并获取URL异常,params[" + openQuickPayBean + "]" + e);
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
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付URL,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]");
        String payUrl = "";
        try
        {
            // 银联支付bean
            QuickPayBean quickPayBean = new QuickPayBean();
            quickPayBean.setMerId(bsConfig.getMerchantNo());// 商户号
            quickPayBean.setOrderNo(transNo);// 业务订单号
            quickPayBean.setBindingNo(bindingNo);// 签约号
            quickPayBean.setSmsCode(smsCode);// 短信验证码
            // 调用支付系统：获取快捷支付URL接口
            payUrl = gpPaymentService.getQuickPayUrl(quickPayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + ":获取快捷支付URL异常,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "获取快捷支付URL异常,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]" + e);
        }
        return payUrl;
    }

    /**
     * 获取快捷支付短信验证码
     * 
     * @param order
     *            订单信息
     * @param bindingNo
     *            签约号
     * @param privObligate
     *            签约号
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService#getQuickPaySmsCode(com.gy.hsxt.bs.bean.order.Order,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void getQuickPaySmsCode(Order order, String bindingNo, String privObligate) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付短信验证码,params[" + order + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]");

        // 银联快捷支付实体
        QuickPaySmsCodeBean quickPaySmsCodeBean = new QuickPaySmsCodeBean();

        try
        {
            // 获取本地区平台信息
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            // 从配置文件中取互生商户号
            quickPaySmsCodeBean.setMerId(bsConfig.getMerchantNo());
            quickPaySmsCodeBean.setOrderNo(order.getOrderNo());// 业务订单号
            quickPaySmsCodeBean.setOrderDate(DateUtil.StringToDate(order.getOrderTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            quickPaySmsCodeBean.setBindingNo(bindingNo);// 签约号
            quickPaySmsCodeBean.setTransAmount(order.getOrderCashAmount());// 交易金额
            quickPaySmsCodeBean.setTransDesc(order.getOrderRemark());// 交易描述
            quickPaySmsCodeBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            quickPaySmsCodeBean.setPrivObligate(privObligate);// 私有数据
            // 调用支付系统：获取首次快捷支付URL接口
            gpPaymentService.getQuickPaySmsCode(quickPaySmsCodeBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + "调用支付系统：获取快捷支付短信验证码异常,params[" + order + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "调用支付系统：获取快捷支付短信验证码异常,params[" + order + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]" + e);
        }
    }

    /**
     * 获取手机支付TN
     * 
     * @param order
     *            兑换互生币信息
     * @param privObligate
     *            私有数据
     * @return TN交易流水号
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getMobilePayTn(com.gy.hsxt.ao.bean.Order,
     *      java.lang.String)
     */
    @Override
    public String getMobilePayTn(Order order, String privObligate) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取手机支付TN码,params[" + order + ",privObligate:" + privObligate + "]");

        String payUrl = "";
        try
        {
            // 获取本地区平台信息
            LocalInfo localInfo = commonService.getAreaPlatInfo();

            MobilePayBean mobilePayBean = new MobilePayBean();
            mobilePayBean.setMerId(bsConfig.getMerchantNo());// 商户号
            mobilePayBean.setOrderNo(order.getOrderNo());// 业务订单号
            mobilePayBean.setOrderDate(DateUtil.StringToDate(order.getPayingTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            mobilePayBean.setTransAmount(order.getOrderCashAmount());// 交易金额
            mobilePayBean.setTransDesc("获取手机支付TN");// 交易描述
            mobilePayBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            mobilePayBean.setPrivObligate(privObligate);// 私有数据
            // 调用支付系统：获取手机支付TN码接口
            payUrl = gpPaymentService.getMobilePayTn(mobilePayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + ":获取手机支付TN码异常,params[" + order + ",privObligate:" + privObligate + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "获取手机支付TN码异常,params[" + order + ",privObligate:" + privObligate + "]" + e);
        }
        return payUrl;
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
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "调用帐务系统获取帐务余额,params[custId:" + custId + ",accountType:" + accountType + "]");
        // 客户号为空
        HsAssert.hasText(custId, BSRespCode.BS_PARAMS_NULL, "获取帐务余额：客户号参数为空");
        // 帐户类型为空
        HsAssert.hasText(custId, BSRespCode.BS_PARAMS_NULL, "获取帐务余额：帐户类型参数为空");

        // 调用帐务系统查询帐务货币帐户余额
        AccountBalance accountBalance = null;
        try
        {
            // 调用帐务系统查询帐户余额
            accountBalance = accountSearchService.searchAccNormal(custId, accountType);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + ":调用帐务系统：获取帐务余额异常,params[custId:" + custId + ",accountType:" + accountType + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "调用帐务系统：获取帐务余额异常,params[custId:" + custId + ",accountType:" + accountType + "]" + e);
        }
        return accountBalance;
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
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "单笔冲正红冲记账,params[writeBack:" + writeBack + ",transType:" + transType + ",transNo:" + transNo + "]");
        try
        {
            // 交易流水号为空
            HsAssert.hasText(transNo, BSRespCode.BS_PARAMS_NULL, "调用AC单笔冲正红冲记账：交易流水号为空");

            // 初始化冲正数据
            AccountWriteBack accountWriteBack = new AccountWriteBack();
            accountWriteBack.setWriteBack(writeBack);// 红冲标识:0 正常，1 自动冲正
                                                     // 2（实时记账都是正常的0）
            accountWriteBack.setTransType(transType);// 交易类型
            accountWriteBack.setRelTransNo(transNo);// 业务编号

            // 调用帐务冲正
            accountEntryService.correctSingleAccount(accountWriteBack);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + "调用AC单笔冲正红冲记账异常,params[writeBack:" + writeBack + ",transType:" + transType + ",transNo:" + transNo + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode(), "调用AC单笔冲正红冲记账异常,params[writeBack:" + writeBack + ",transType:" + transType + ",transNo:" + transNo + "]" + e);
        }
    }

    /**
     * 检验帐户余额是否足够
     * 
     * @param custId
     *            客户号
     * @param accountType
     *            帐户类型
     * @param appAmount
     *            申请金额
     * @return true/false
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IInvokeRemoteService#isBlanceInsufficient(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public int isBlanceInsufficient(String custId, String accountType, String appAmount) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "检验帐户余额是否足够,params[custId:" + custId + ",accountType:" + accountType + ",appAmount:" + appAmount + "]");
        // 调用帐务系统查询帐务货币帐户余额
        AccountBalance accountBalance = null;
        try
        {
            // 客户号为空
            HsAssert.hasText(custId, BSRespCode.BS_PARAMS_NULL, "获取帐务余额：客户号参数为空");
            // 帐户类型为空
            HsAssert.hasText(custId, BSRespCode.BS_PARAMS_NULL, "获取帐务余额：帐户类型参数为空");

            // 调用帐务系统查询帐户余额
            accountBalance = accountSearchService.searchAccNormal(custId, accountType);
            // 帐户余额小于申请金额
            return BigDecimalUtils.compareTo(appAmount, accountBalance.getAccBalance());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_DUBBO_INVOKE_ERROR.getCode() + ":调用帐务系统：获取帐务余额异常,params[custId:" + custId + ",accountType:" + accountType + ",appAmount:" + appAmount + "]", e);
            throw new HsException(BSRespCode.BS_DUBBO_INVOKE_ERROR, "调用帐务系统：获取帐务余额异常,params[custId:" + custId + ",accountType:" + accountType + ",appAmount:" + appAmount + "]" + e);
        }
    }
}
