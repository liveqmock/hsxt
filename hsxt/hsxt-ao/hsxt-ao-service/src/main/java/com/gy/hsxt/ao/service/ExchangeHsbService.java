/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.BuyHsbCancel;
import com.gy.hsxt.ao.bean.PaymentResult;
import com.gy.hsxt.ao.bean.PosBuyHsbCancel;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.bean.ProxyBuyHsbCancel;
import com.gy.hsxt.ao.common.BizType;
import com.gy.hsxt.ao.common.TransCodeUtil;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.enumtype.AccountingStatus;
import com.gy.hsxt.ao.enumtype.BuyHsbPayStatus;
import com.gy.hsxt.ao.enumtype.BuyHsbTransResult;
import com.gy.hsxt.ao.enumtype.ProxyTransMode;
import com.gy.hsxt.ao.interfaces.IAccountRuleService;
import com.gy.hsxt.ao.interfaces.IAccountingService;
import com.gy.hsxt.ao.interfaces.ICommonService;
import com.gy.hsxt.ao.interfaces.IDubboInvokService;
import com.gy.hsxt.ao.interfaces.IExchangeHsbService;
import com.gy.hsxt.ao.interfaces.IPaymentNotifyService;
import com.gy.hsxt.ao.mapper.AccountingMapper;
import com.gy.hsxt.ao.mapper.BuyHsbCancelMapper;
import com.gy.hsxt.ao.mapper.BuyHsbMapper;
import com.gy.hsxt.ao.mapper.ProxyBuyHsbCancelMapper;
import com.gy.hsxt.ao.mapper.ProxyBuyHsbMapper;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.WriteBack;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

/**
 * 兑换互生币接口实现类
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: ExchangeHsbService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-11-27 下午6:25:16
 * @version V3.0.0
 */
@Service
public class ExchangeHsbService implements IAOExchangeHsbService, IPaymentNotifyService, IExchangeHsbService {
    /**
     * 帐户操作私有配置参数
     **/
    @Resource
    AoConfig aoConfig;

    /**
     * 注入记帐分解mapper
     */
    @Resource
    AccountingMapper accountingMapper;

    /**
     * 注入记帐分解对象
     */
    @Resource
    IAccountingService accountingService;

    /**
     * 兑换互生币mapper
     */
    @Resource
    BuyHsbMapper buyHsbMapper;

    /**
     * 冲正兑换互生币mapper
     */
    @Resource
    BuyHsbCancelMapper buyHsbCancelMapper;

    /**
     * dubbo调用服务对象
     */
    @Resource
    IDubboInvokService dubboInvokService;

    /**
     * 公共服务，获取全局公共数据
     */
    @Resource
    ICommonService commonService;

    /**
     * 参数配置系统：帐户规则服务
     */
    @Resource
    IAccountRuleService accountRuleService;

    /**
     * 代兑互生币mapper
     */
    @Resource
    ProxyBuyHsbMapper proxyBuyHsbMapper;

    /**
     * 冲正代兑互生币mapper
     */
    @Resource
    ProxyBuyHsbCancelMapper proxyBuyHsbCancelMapper;

    @Autowired
    LcsClient lcsClient;

    @Autowired
    IUFRegionPacketService ufRegionPacketService;

    // 声明锁对象，主要为了解决支付通知重复记帐问题
    Lock lock = new ReentrantLock();

    /**
     * 保存兑换互生币记录
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param jumpUrl
     *            网银支付时的回跳地址
     * @return 货币兑换：交易流水号、网银兑换：网银支付地址
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#saveBuyHsb(com.gy.hsxt.ao.bean.BuyHsb)
     */
    @Override
    @Transactional
    public String saveBuyHsb(BuyHsb buyHsb) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "保存兑换互生币,params[" + buyHsb + "]");
        // 实体参数为空
        HsAssert.notNull(buyHsb, AOErrorCode.AO_PARAMS_NULL, "保存兑换互生币：实体参数为空");
        HsAssert.hasText(buyHsb.getCustId(), AOErrorCode.AO_PARAMS_NULL, "保存兑换互生币：客户号为空");
        boolean isReg = false;
        try
        {
            // 校验是否已实名认证确定兑换互生币限制
            isReg = dubboInvokService.isPassRealName(buyHsb.getCustType(), buyHsb.getCustId());

            // 校验兑换互生币是否符合限额限次规则
            boolean isAllowBuyHsb = accountRuleService.checkBuyHsbRule(buyHsb.getCustId(), buyHsb.getCustType(), buyHsb.getBuyHsbAmt(), isReg);

            if (isAllowBuyHsb)
            {
                /**
                 * 设置数据{
                 */
                String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
                // 生成交易流水号
                buyHsb.setTransNo(guid);
                // 货币转换比率
                buyHsb.setExRate(commonService.getLocalInfo().getExchangeRate());
                // 计算需要支付多少本币金额（算法：兑换互生币数量 / 货币转换比率）
                buyHsb.setCashAmt(BigDecimalUtils.ceiling(BigDecimalUtils.ceilingDiv(buyHsb.getBuyHsbAmt(), buyHsb.getExRate()).toString(), 2).toString());
                // 生成交易时间
                buyHsb.setReqTime(DateUtil.getCurrentDateTime());
                // 交易结果（默认为待付款）
                buyHsb.setTransResult(BuyHsbTransResult.WAIT_PAY.getCode());
                // 设置支付状态（默认为-1等付款）
                buyHsb.setPayStatus(BuyHsbPayStatus.READY.getCode());
                // 非POS渠道设置POS流水号为交易流水号，用于客户号与POS流水号生成唯一索引
                if (buyHsb.getChannel() != null && Channel.POS.getCode() != buyHsb.getChannel())
                {
                    buyHsb.setOriginNo(guid);
                }
                else
                {
                    buyHsb.setChannel(Channel.WEB.getCode());// 设置默认值
                    buyHsb.setOriginNo(guid);
                }
                /**
                 * }
                 */

                // 插入兑换互生币记录
                buyHsbMapper.insertBuyHsb(buyHsb);
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_BUY_HSB_ERROR.getCode() + "保存兑换互生币异常,params[" + buyHsb + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_BUY_HSB_ERROR.getCode(), "保存兑换互生币异常,params[" + buyHsb + "]" + e);
        }
        // 交易流水号
        return buyHsb.getTransNo();
    }

    /**
     * 保存企业代兑互生币记录
     * 
     * @param proxyBuyHsb
     *            代兑互生币信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#saveEntProxyBuyHsb(com.gy.hsxt.ao.bean.ProxyBuyHsb)
     */
    @Override
    @Transactional
    public void saveEntProxyBuyHsb(ProxyBuyHsb proxyBuyHsb) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "保存企业代兑互生币,params[" + proxyBuyHsb + "]");
        // 成功记录数
        int returnNum = 0;
        // 实体参数为空
        HsAssert.notNull(proxyBuyHsb, AOErrorCode.AO_PARAMS_NULL, "保存企业代兑互生币：实体参数为空");
        boolean isReg = false;
        try
        {
            /**
             * 校验企业是否欠年费、重要信息变更{
             */
            dubboInvokService.checkCustInfo(proxyBuyHsb.getEntCustType(), proxyBuyHsb.getEntCustId(), BizType.PROXY_BUY_HSB.getCode(), true, true, false);
            /**
             * }
             */

            // 校验是否已实名认证确定兑换互生币限制
            isReg = dubboInvokService.isPassRealName(proxyBuyHsb.getPerCustType(), proxyBuyHsb.getPerCustId());

            // 兑换互生币金额
            proxyBuyHsb.setBuyHsbAmt(BigDecimalUtils.floor(BigDecimalUtils.ceilingMul(proxyBuyHsb.getCashAmt(), commonService.getLocalInfo().getExchangeRate()).toString(), 2).toString());

            // 校验企业互生币支付是否答限额限次规则
            boolean isAlloHsbPay = accountRuleService.checkHsbToPayRule(proxyBuyHsb.getEntCustId(), proxyBuyHsb.getEntCustType(), proxyBuyHsb.getBuyHsbAmt());
            // 校验兑换互生币是否符合限额限次规则
            boolean isAllowBuyHsb = accountRuleService.checkBuyHsbRule(proxyBuyHsb.getPerCustId(), proxyBuyHsb.getPerCustType(), proxyBuyHsb.getBuyHsbAmt(), isReg);

            if (isAlloHsbPay && isAllowBuyHsb)
            {
                String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
                // 校验代兑方式是本地/异地
                if (proxyBuyHsb.getTransMode() == null)
                {
                    // 本地企业本地消费者
                    proxyBuyHsb.setTransMode(ProxyTransMode.LOCAL_ENT_TO_LOCAL_CUST.getCode());
                }
                // 生成交易流水号
                proxyBuyHsb.setTransNo(guid);
                // 生成交易时间
                proxyBuyHsb.setReqTime(DateUtil.getCurrentDateTime());
                // 货币转换比率
                proxyBuyHsb.setExRate(commonService.getLocalInfo().getExchangeRate());

                // 非POS渠道设置POS流水号为交易流水号
                if (proxyBuyHsb.getChannel() != null && Channel.POS.getCode() != proxyBuyHsb.getChannel())
                {
                    proxyBuyHsb.setOriginNo(guid);
                }
                else
                {
                    proxyBuyHsb.setChannel(Channel.WEB.getCode());// 设置渠道默认值
                    proxyBuyHsb.setOriginNo(guid);
                }

                // 保存代兑互生币记录
                returnNum = proxyBuyHsbMapper.insertProxyBuyHsb(proxyBuyHsb);

                // 大于0表示插入成功
                if (returnNum > 0)
                {
                    // 记帐分解
                    accountingService.saveAccounting(proxyBuyHsb);
                    // 兑换互生币后续处理规则
                    accountRuleService.afterBuyHsb(proxyBuyHsb.getPerCustId(), proxyBuyHsb.getPerCustType(), proxyBuyHsb.getBuyHsbAmt());
                    // 企业代兑互生币后续处理规则
                    accountRuleService.afterHsbToPay(proxyBuyHsb.getEntCustId(), proxyBuyHsb.getEntCustType(), proxyBuyHsb.getBuyHsbAmt());
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_ENT_PROXY_BUY_HSB_ERROR.getCode() + "保存企业代兑互生币异常,params[" + proxyBuyHsb + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_ENT_PROXY_BUY_HSB_ERROR.getCode(), "保存企业代兑互生币异常,params[" + proxyBuyHsb + "]" + e);
        }
    }

    /**
     * 保存POS兑换互生币记录
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @return 实收互生币金额
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#savePosBuyHsb(com.gy.hsxt.ao.bean.BuyHsb)
     */
    @Override
    @Transactional
    public String savePosBuyHsb(BuyHsb buyHsb) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "保存POS兑换互生币记录,params[" + buyHsb + "]");
        // 成功记录数
        int returnNum = 0;
        // 实体参数为空
        HsAssert.notNull(buyHsb, AOErrorCode.AO_PARAMS_NULL, "保存POS兑换互生币记录：实体参数为空");
        HsAssert.hasText(buyHsb.getCustId(), AOErrorCode.AO_PARAMS_NULL, "保存POS兑换互生币记录：客户号为空");
        boolean isReg = false;
        BuyHsb isExists = null;

        try
        {
            // 查询是否存在相同的客户号和POS流水号记录
            returnNum = buyHsbMapper.findInfoByCustIdOriginNo(buyHsb.getCustId(), buyHsb.getOriginNo());
            HsAssert.isTrue(returnNum == 0, AOErrorCode.AO_EXISTS_SAME_DATA, "保存POS兑换互生币记录：存在相同的记录,params[custId:" + buyHsb.getCustId() + ",origiinNo:" + buyHsb.getOriginNo() + "]");
            // 添加校验
            isExists = buyHsbMapper.findInfoByTermRuncode(buyHsb.getHsResNo(), buyHsb.getTermNo(), buyHsb.getBatchNo(), buyHsb.getTermRuncode());
            HsAssert.isTrue(isExists == null, AOErrorCode.AO_EXISTS_SAME_DATA, "保存POS兑换互生币记录：存在相同的记录,params[hsResNo:" + buyHsb.getHsResNo() + ",termNo:" + buyHsb.getTermNo() + ",batchNo:" + buyHsb.getBatchNo() + ",termRuncode:" + buyHsb.getTermRuncode() + "]");

            // 校验是否已实名认证确定兑换互生币限制
            isReg = dubboInvokService.isPassRealName(buyHsb.getCustType(), buyHsb.getCustId());

            // 货币转换比率
            buyHsb.setExRate(commonService.getLocalInfo().getExchangeRate());
            // 计算实收互生币金额(兑换互生币：支付货币金额=兑换互生币数量 / 货币转换比率)
            buyHsb.setBuyHsbAmt(BigDecimalUtils.floor(BigDecimalUtils.ceilingMul(buyHsb.getCashAmt(), buyHsb.getExRate()).toString(), 2).toString());
            // 校验兑换互生币是否符合限额限次规则
            boolean isAllowBuyHsb = accountRuleService.checkBuyHsbRule(buyHsb.getCustId(), buyHsb.getCustType(), buyHsb.getBuyHsbAmt(), isReg);

            if (isAllowBuyHsb)
            {
                /**
                 * 设置数据{
                 */
                String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
                // 生成交易流水号
                buyHsb.setTransNo(guid);
                // 支付方式：POS兑换互生币默认为货币支付
                buyHsb.setPayModel(PayChannel.MONEY_PAY.getCode());
                // 生成交易时间
                buyHsb.setReqTime(DateUtil.getCurrentDateTime());
                // 交易结果（默认为交易成功）
                buyHsb.setTransResult(BuyHsbTransResult.TRANS_SUCCESS.getCode());
                // 支付状态（默认为100支付成功）
                buyHsb.setPayStatus(BuyHsbPayStatus.PAY_SUCCESS.getCode());
                // 更新时间
                buyHsb.setUpdateTime(DateUtil.getCurrentDateTime());
                // 设置渠道来源
                if (buyHsb.getChannel() == null)
                    buyHsb.setChannel(Channel.POS.getCode());
                /**
                 * }
                 */

                // 插入兑换互生币记录
                returnNum = buyHsbMapper.insertBuyHsb(buyHsb);
                // 大于0表示插入成功
                if (returnNum > 0)
                {
                    // 实时记帐
                    accountingService.saveAccounting(buyHsb);
                    // 兑换互生币后续处理规则
                    accountRuleService.afterBuyHsb(buyHsb.getCustId(), buyHsb.getCustType(), buyHsb.getBuyHsbAmt());
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_POS_BUY_HSB_ERROR.getCode() + "保存POS兑换互生币记录异常,params[" + buyHsb + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_POS_BUY_HSB_ERROR.getCode(), "保存POS兑换互生币记录异常,params[" + buyHsb + "]" + e);
        }
        // 实收互生币金额
        return buyHsb.getBuyHsbAmt();
    }

    /**
     * 保存POS、刷卡器代兑互生币记录
     * 
     * @param proxyBuyHsb
     *            兑换互生币信息
     * @return 实收互生币金额
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#savePosProxyBuyHsb(com.gy.hsxt.ao.bean.ProxyBuyHsb)
     */
    @Override
    @Transactional
    public String savePosProxyBuyHsb(ProxyBuyHsb proxyBuyHsb) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "保存设备代兑互生币,params[" + proxyBuyHsb + "]");
        String channelName = "设备";
        // 成功记录数
        int returnNum = 0;
        // 实体参数为空
        HsAssert.notNull(proxyBuyHsb, AOErrorCode.AO_PARAMS_NULL, "保存POS代兑互生币：实体参数为空");
        boolean isReg = false;
        ProxyBuyHsb isExists = null;
        try
        {
            /**
             * 校验企业是否欠年费、重要信息变更{
             */
            dubboInvokService.checkCustInfo(proxyBuyHsb.getEntCustType(), proxyBuyHsb.getEntCustId(), BizType.PROXY_BUY_HSB.getCode(), true, true, false);
            /**
             * }
             */

            // 查询是否存在相同的企业客户号和POS流水号
            returnNum = proxyBuyHsbMapper.findInfoByEntCustIdOriginNo(proxyBuyHsb.getEntCustId(), proxyBuyHsb.getOriginNo());
            HsAssert.isTrue(returnNum == 0, AOErrorCode.AO_EXISTS_SAME_DATA, "保存POS代兑互生币：存在相同的记录,params[entCustId:" + proxyBuyHsb.getEntCustId() + ",originNo:" + proxyBuyHsb.getOriginNo() + "]");
            // 添加校验
            isExists = proxyBuyHsbMapper.findInfoByTermRuncode(proxyBuyHsb.getEntResNo(), proxyBuyHsb.getTermNo(), proxyBuyHsb.getBatchNo(), proxyBuyHsb.getTermRuncode());
            HsAssert.isTrue(isExists == null, AOErrorCode.AO_EXISTS_SAME_DATA, "保存POS代兑互生币：存在相同的记录,params[entResNo:" + proxyBuyHsb.getEntResNo() + ",termNo:" + proxyBuyHsb.getTermNo() + ",batchNo:" + proxyBuyHsb.getBatchNo() + ",termRuncode:" + proxyBuyHsb.getTermRuncode() + "]");

            // 校验是否已实名认证确定兑换互生币限制
            isReg = dubboInvokService.isPassRealName(proxyBuyHsb.getPerCustType(), proxyBuyHsb.getPerCustId());

            // 兑换互生币金额
            if (ProxyTransMode.DIFF_ENT_TO_LOCAL_CUST.getCode().intValue() == proxyBuyHsb.getTransMode())
            {
                // 跨平台代兑消费者端不需要计算代兑互生币数量，而是由企业端计算好传过来的，因为支付是按照企业端货币兑换比率进行换算的
            }
            else
            {
                // 本地代兑业务和跨平台代兑企业端需要计算代兑互生币数量
                proxyBuyHsb.setBuyHsbAmt(BigDecimalUtils.floor(BigDecimalUtils.ceilingMul(proxyBuyHsb.getCashAmt(), commonService.getLocalInfo().getExchangeRate()).toString(), 2).toString());
            }

            // 校验企业互生币支付是否答限额限次规则
            boolean isAlloHsbPay = accountRuleService.checkHsbToPayRule(proxyBuyHsb.getEntCustId(), proxyBuyHsb.getEntCustType(), proxyBuyHsb.getBuyHsbAmt());

            // 校验兑换互生币是否符合限额限次规则
            boolean isAllowBuyHsb = accountRuleService.checkBuyHsbRule(proxyBuyHsb.getPerCustId(), proxyBuyHsb.getPerCustType(), proxyBuyHsb.getBuyHsbAmt(), isReg);
            if (isAlloHsbPay && isAllowBuyHsb)
            {
                /**
                 * 初始化数据{
                 */
                String guid = GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode());
                // 校验代兑方式是本地/异地
                if (proxyBuyHsb.getTransMode() == null)
                {
                    // 本地企业本地消费者
                    proxyBuyHsb.setTransMode(ProxyTransMode.LOCAL_ENT_TO_LOCAL_CUST.getCode());
                }
                // 生成交易流水号
                proxyBuyHsb.setTransNo(guid);
                // 生成交易时间
                proxyBuyHsb.setReqTime(DateUtil.getCurrentDateTime());
                // 货币转换比率
                proxyBuyHsb.setExRate(commonService.getLocalInfo().getExchangeRate());
                // 交易结果
                proxyBuyHsb.setTransResult(BuyHsbTransResult.TRANS_SUCCESS.getCode());
                // 渠道来源
                if (proxyBuyHsb.getChannel() == null)
                {
                    proxyBuyHsb.setChannel(Channel.POS.getCode());
                }
                /**
                 * }
                 */
                // 保存POS代兑互生币记录
                returnNum = proxyBuyHsbMapper.insertPosBuyHsb(proxyBuyHsb);

                // 大于0表示插入成功
                if (returnNum > 0)
                {
                    // 记帐分解
                    accountingService.saveAccounting(proxyBuyHsb);
                    // 兑换互生币后续处理规则
                    accountRuleService.afterBuyHsb(proxyBuyHsb.getPerCustId(), proxyBuyHsb.getPerCustType(), proxyBuyHsb.getBuyHsbAmt());
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_POS_PROXY_BUY_HSB_ERROR.getCode() + "保存" + channelName + "代兑互生币异常,params[" + proxyBuyHsb + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_POS_PROXY_BUY_HSB_ERROR.getCode(), "保存" + channelName + "代兑互生币异常,params[" + proxyBuyHsb + "]" + e);
        }
        // 实收互生币金额
        return proxyBuyHsb.getBuyHsbAmt();
    }

    /**
     * 冲正货币兑换互生币
     * 
     * @param buyHsbCancel
     *            冲正信息
     * @param remark
     *            备注
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#reverseBuyHsb(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void reverseBuyHsb(BuyHsbCancel buyHsbCancel, String remark) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正兑换互生币,params[" + buyHsbCancel + ",remark:" + remark + "]");
        // 实体参数为空
        HsAssert.notNull(buyHsbCancel, AOErrorCode.AO_PARAMS_NULL, "冲正兑换互生币：实体参数为空");
        // 原交易流水号为空
        HsAssert.hasText(buyHsbCancel.getOrigTransNo(), AOErrorCode.AO_PARAMS_NULL, "冲正兑换互生币：原交易流水号为空");
        try
        {
            // 获取兑换互生币信息
            BuyHsb buyHsb = buyHsbMapper.findBuyHsbInfo(buyHsbCancel.getOrigTransNo());
            HsAssert.notNull(buyHsb, AOErrorCode.AO_NOT_QUERY_DATA, "冲正兑换互生币：未查询到原业务单");

            // 支付成功的单才可能有冲正发起冲正
            if (BuyHsbPayStatus.PAY_SUCCESS.getCode().intValue() == buyHsb.getPayStatus())
            {
                HsAssert.isTrue(buyHsb.getTransResult().intValue() != 2, AOErrorCode.AO_REPET_COMMIT_REVERSE, "冲正兑换互生币：该交易已冲正");
                // 生成交易流水号
                buyHsbCancel.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
                // 保存冲正记录
                buyHsbCancelMapper.insertBuyHsbCancel(buyHsbCancel);
                // 更新原交易结果
                buyHsbMapper.updateTransResult(buyHsbCancel.getOrigTransNo(), BuyHsbTransResult.REVERSE.getCode(), remark);
                // 更新记帐状态
                accountingMapper.updateStatus(buyHsbCancel.getOrigTransNo(), AccountingStatus.IS_REVERSE.getCode());
                // 调账务单笔冲正接口
                dubboInvokService.correctSingleAccount(WriteBack.AUTO_REVERSE.getCode(), TransCodeUtil.getReverseBuyHsbTransCode(buyHsb.getCustType(), buyHsb.getPayModel()), buyHsbCancel.getOrigTransNo());
            }
            else
            {
                HsAssert.isTrue(false, AOErrorCode.AO_ILLEGAL_REVERSE_REQUEST, "冲正兑换互生币：未支付订单不需要冲正");
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_REVERSE_BUY_HSB_ERROR.getCode() + "冲正兑换互生币异常,params[" + buyHsbCancel + "]", e);
            throw new HsException(AOErrorCode.AO_REVERSE_BUY_HSB_ERROR.getCode(), "冲正兑换互生币异常,params[" + buyHsbCancel + "]" + e);
        }
    }

    /**
     * 冲正POS兑换互生币
     * 
     * @param proxyBuyHsbCancel
     *            冲正信息
     * @param remark
     *            备注
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#reversePosBuyHsb(com.gy.hsxt.ao.bean.ProxyBuyHsbCancel,
     *      java.lang.String)
     */
    @Override
    @Transactional
    public void reversePosBuyHsb(PosBuyHsbCancel posBuyHsbCancel) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正POS兑换互生币,params[" + posBuyHsbCancel + "]");
        // 声明兑换互生币对象
        BuyHsbCancel buyHsbCancel = new BuyHsbCancel();
        // 检验输入参数
        verifyPosReverseParam(posBuyHsbCancel);

        try
        {
            // 获取原兑换互生币信息
            BuyHsb buyHsb = buyHsbMapper.findInfoByTermRuncode(posBuyHsbCancel.getHsResNo(), posBuyHsbCancel.getTermNo(), posBuyHsbCancel.getBatchNo(), posBuyHsbCancel.getTermRuncode());
            // 原始交易记录为空
            if (buyHsb == null)
            {
                SystemLog.warn(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正POS兑换互生币,原业务记录不存在,params[" + posBuyHsbCancel + "]");
                return;
            }
            if (buyHsb.getTransResult().intValue() != BuyHsbTransResult.REVERSE.getCode())
            {
                SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正POS兑换互生币,原业务已被冲正,params[" + posBuyHsbCancel + "]");
                return;
            }
            /**
             * 初始化冲正数据{
             */
            // 生成交易流水号
            buyHsbCancel.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
            // 原交易流水号
            buyHsbCancel.setOrigTransNo(buyHsb.getTransNo());
            // 操作员编号
            buyHsbCancel.setOptCustId(posBuyHsbCancel.getOptCustId());
            // 操作员名称
            buyHsbCancel.setOptCustName(posBuyHsbCancel.getOptCustName());
            /**
             * }
             */

            // 保存冲正记录
            buyHsbCancelMapper.insertBuyHsbCancel(buyHsbCancel);
            // POS冲正更新原交易结果
            buyHsbMapper.updateTransResult(buyHsb.getTransNo(), BuyHsbTransResult.REVERSE.getCode(), posBuyHsbCancel.getRemark());
            // 更新记帐状态
            accountingMapper.updateStatus(buyHsb.getTransNo(), AccountingStatus.IS_REVERSE.getCode());
            // 调账务单笔冲正接口
            dubboInvokService.correctSingleAccount(WriteBack.AUTO_REVERSE.getCode(), TransCodeUtil.getReverseBuyHsbTransCode(buyHsb.getCustType(), buyHsb.getPayModel()), buyHsbCancel.getOrigTransNo());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_REVERSE_POS_BUY_HSB_ERROR.getCode() + "冲正POS兑换互生币异常,params[" + posBuyHsbCancel + "]", e);
            throw new HsException(AOErrorCode.AO_REVERSE_POS_BUY_HSB_ERROR.getCode(), "冲正POS兑换互生币异常,params[" + posBuyHsbCancel + "]" + e);
        }
    }

    /**
     * 冲正企业代兑互生币
     * 
     * @param proxyBuyHsbCancel
     *            冲正信息
     * @param remark
     *            备注
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#reverseBuyHsb(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void reverseProxyBuyHsb(ProxyBuyHsbCancel proxyBuyHsbCancel, String remark) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正企业代兑互生币,params[" + proxyBuyHsbCancel + ",remark:" + remark + "]");
        // 实体参数为空
        HsAssert.notNull(proxyBuyHsbCancel, AOErrorCode.AO_PARAMS_NULL, "冲正企业代兑互生币：实体参数为空");
        // 原交易流水号为空
        HsAssert.hasText(proxyBuyHsbCancel.getOrigTransNo(), AOErrorCode.AO_PARAMS_NULL, "冲正企业代兑互生币：原交易流水号为空");
        try
        {
            // 获取代兑互生币信息
            ProxyBuyHsb proxyBuyHsb = proxyBuyHsbMapper.findProxyBuyHsbInfo(proxyBuyHsbCancel.getOrigTransNo());
            HsAssert.notNull(proxyBuyHsb, AOErrorCode.AO_NOT_QUERY_DATA, "冲正企业代兑互生币：未查询到原始业务单");
            // 成功的交易才可能发起冲正
            if (proxyBuyHsb.getTransResult() == BuyHsbTransResult.TRANS_SUCCESS.getCode())
            {
                // 生成交易流水号
                proxyBuyHsbCancel.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
                // 插入代兑冲正记录
                proxyBuyHsbCancelMapper.insertProxyBuyHsbCancel(proxyBuyHsbCancel);
                // 更新原交易结果
                proxyBuyHsbMapper.updateTransResult(proxyBuyHsbCancel.getOrigTransNo(), BuyHsbTransResult.REVERSE.getCode(), remark);
                // 更新记帐状态
                accountingMapper.updateStatus(proxyBuyHsbCancel.getOrigTransNo(), AccountingStatus.IS_REVERSE.getCode());
                // 调账务单笔冲正接口
                dubboInvokService.correctSingleAccount(WriteBack.AUTO_REVERSE.getCode(), TransCodeUtil.getReverseProxyBuyHsbCode(proxyBuyHsb.getTransMode()), proxyBuyHsbCancel.getOrigTransNo());
            }
            else
            {
                HsAssert.isTrue(proxyBuyHsb.getTransResult().intValue() != 2, AOErrorCode.AO_REPET_COMMIT_REVERSE, "冲正企业代兑互生币：该交易已冲正");
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_REVERSE_ENT_PROXY_BUY_HSB_ERROR.getCode() + "代兑互生币冲正异常,params[" + proxyBuyHsbCancel + "]", e);
            throw new HsException(AOErrorCode.AO_REVERSE_ENT_PROXY_BUY_HSB_ERROR.getCode(), "代兑互生币冲正异常,params[" + proxyBuyHsbCancel + "]" + e);
        }
    }

    /**
     * 冲正POS代兑互生币
     * 
     * @param posBuyHsbCancel
     *            pos冲正信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#reversePosProxyBuyHsb(com.gy.hsxt.ao.bean.PosBuyHsbCancel,
     *      java.lang.String)
     */
    @Override
    @Transactional
    public void reversePosProxyBuyHsb(PosBuyHsbCancel posBuyHsbCancel) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正设备代兑互生币,params[" + posBuyHsbCancel + "]");
        // 声明代兑互生币对象
        ProxyBuyHsbCancel proxyBuyHsbCancel = new ProxyBuyHsbCancel();
        ProxyBuyHsb proxyBuyHsb = null;
        String channelName = "设备";
        // 检验输入参数
        verifyPosReverseParam(posBuyHsbCancel);
        try
        {
            // 获取原兑换互生币信息
            proxyBuyHsb = proxyBuyHsbMapper.findInfoByTermRuncode(posBuyHsbCancel.getHsResNo(), posBuyHsbCancel.getTermNo(), posBuyHsbCancel.getBatchNo(), posBuyHsbCancel.getTermRuncode());
            // 原始交易记录为空
            if (proxyBuyHsb == null)
            {
                SystemLog.warn(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正POS代兑互生币,原业务记录不存在,params[" + posBuyHsbCancel + "]");
            }
            // 成功的交易才可能发起冲正
            if (BuyHsbTransResult.TRANS_SUCCESS.getCode() == proxyBuyHsb.getTransResult())
            {
                // 如果是跨平台业务，先做远程冲正，把给消费者的互生币收回来，再做本地冲正，把互生币给到企业
                // 如果是跨平台代兑互生币冲正，在进行本地冲正时还要发消息进行异地冲正
                if (ProxyTransMode.LOCAL_ENT_TO_DIFF_CUST.getCode().intValue() == proxyBuyHsb.getTransMode())
                {
                    JSONObject result = null;
                    try
                    {
                        // 获取目标地区平台代码
                        String remotePlatNo = lcsClient.getPlatByResNo(proxyBuyHsb.getPerResNo());

                        // 发送跨地区平台报文,跨平台代兑互生币消费者端冲正
                        RegionPacketHeader packetHeader = RegionPacketHeader.build().setDestPlatformId(remotePlatNo).setDestBizCode(AcrossPlatBizCode.PROXY_HSB_FOR_REMOTE_P_REVERSE.name());
                        // 组装报文体
                        RegionPacketBody packetBody = RegionPacketBody.build(posBuyHsbCancel);
                        result = (JSONObject) ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
                    }
                    catch (Exception e)
                    {
                        SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), "通知异地代兑互生币冲正(消费者)出现异常,param=" + posBuyHsbCancel, e);
                        throw new HsException(AOErrorCode.AO_REVERSE_POS_PROXY_BUY_HSB_NOTIFY_ERROR.getCode(), "冲正" + channelName + "代兑互生币无响应,params[" + posBuyHsbCancel + "]" + e);
                    }
                    if (result == null)
                    {
                        SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), "通知异地代兑互生币冲正(消费者)返回结果为空,param=" + posBuyHsbCancel, null);
                        throw new HsException(AOErrorCode.AO_REVERSE_POS_PROXY_BUY_HSB_NOTIFY_ERROR.getCode(), "冲正" + channelName + "代兑互生币无响应,params[" + posBuyHsbCancel + "]");
                    }
                    else
                    {
                        if (RespCode.FAIL.getCode() == (int) result.get("AOErrorCode"))
                        {
                            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), "通知异地代兑互生币冲正(消费者)返回失败,param=" + posBuyHsbCancel + ",错误描述：" + result.get("respDesc"), null);
                            throw new HsException(AOErrorCode.AO_REVERSE_POS_PROXY_BUY_HSB_NOTIFY_RES_FAIL.getCode(), "冲正" + channelName + "代兑互生币失败,params[" + posBuyHsbCancel + "]");
                        }
                    }
                }

                /**
                 * 初始化冲正数据{
                 */
                // 生成交易流水号
                proxyBuyHsbCancel.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
                // 原交易流水号
                proxyBuyHsbCancel.setOrigTransNo(proxyBuyHsb.getTransNo());
                // 操作员编号
                proxyBuyHsbCancel.setOptCustId(posBuyHsbCancel.getOptCustId());
                // 操作员名称
                proxyBuyHsbCancel.setOptCustName(posBuyHsbCancel.getOptCustName());
                /**
                 * }
                 */

                // 保存冲正记录
                proxyBuyHsbCancelMapper.insertProxyBuyHsbCancel(proxyBuyHsbCancel);
                // POS冲正更新原交易结果为冲正
                proxyBuyHsbMapper.updateTransResult(proxyBuyHsb.getTransNo(), BuyHsbTransResult.REVERSE.getCode(), posBuyHsbCancel.getRemark());
                // 更新记帐状态
                accountingMapper.updateStatus(proxyBuyHsb.getTransNo(), AccountingStatus.IS_REVERSE.getCode());
                // 调账务单笔冲正接口
                dubboInvokService.correctSingleAccount(WriteBack.AUTO_REVERSE.getCode(), TransCodeUtil.getReverseProxyBuyHsbCode(proxyBuyHsb.getTransMode()), proxyBuyHsbCancel.getOrigTransNo());
            }
            else
            {

                if (BuyHsbTransResult.REVERSE.getCode() == proxyBuyHsb.getTransResult().intValue())
                {
                    SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "冲正POS代兑互生币,原业务已被冲正,params[" + posBuyHsbCancel + "]");
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_REVERSE_POS_PROXY_BUY_HSB_ERROR.getCode() + "冲正POS代兑互生币异常,params[" + posBuyHsbCancel + "]", e);
            throw new HsException(AOErrorCode.AO_REVERSE_POS_PROXY_BUY_HSB_ERROR.getCode(), "冲正POS代兑互生币异常,params[" + posBuyHsbCancel + "]" + e);
        }
    }

    /**
     * 查询兑换互生币详情
     * 
     * @param transNo
     *            交易流水号
     * @return 兑换互生币信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getBuyHsbInfo(java.lang.String)
     */
    @Override
    public BuyHsb getBuyHsbInfo(String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "查询兑换互生币详情,params[transNo:" + transNo + "]");
        // 实体参数为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "查询兑换互生币详情：交易流水号为空");
        try
        {
            // 查询兑换互生币详情
            return buyHsbMapper.findBuyHsbInfo(transNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_QUERY_BUY_HSB_DETAIL_ERROR.getCode() + "查询兑换互生币详情异常,params[transNo:" + transNo + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_BUY_HSB_DETAIL_ERROR.getCode(), "查询兑换互生币详情异常,params[transNo:" + transNo + "]" + e);
        }
    }

    /**
     * 查询企业代兑互生币详情
     * 
     * @param transNo
     *            交易流水号
     * @return 企业代兑互生币信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getEntProxyBuyHsbInfo(java.lang.String)
     */
    @Override
    public ProxyBuyHsb getEntProxyBuyHsbInfo(String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "查询企业代兑互生币详情,params[transNo:" + transNo + "]");
        // 实体参数为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "查询企业代兑互生币详情：交易流水号为空");
        try
        {
            // 查询代兑互生币详情
            return proxyBuyHsbMapper.findProxyBuyHsbInfo(transNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_QUERY_ENT_PROXY_BUY_HSB_DETAIL_ERROR.getCode() + "查询企业代兑互生币详情异常,params[transNo:" + transNo + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_ENT_PROXY_BUY_HSB_DETAIL_ERROR.getCode(), "查询企业代兑互生币详情异常,params[transNo:" + transNo + "]" + e);
        }
    }

    /**
     * 查询POS兑换互生币详情
     * 
     * @param hsResNo
     *            互生号
     * @param originNo
     *            POS流水号
     * @return 兑换互生币信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getPosBuyHsbInfo(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public BuyHsb getPosBuyHsbInfo(String hsResNo, String originNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "查询POS兑换互生币详情,params[hsResNo:" + hsResNo + ",originNo:" + originNo + "]");
        try
        {
            // 互生号为空
            HsAssert.hasText(hsResNo, AOErrorCode.AO_PARAMS_NULL, "查询POS兑换互生币详情：互生号参数为空");
            // POS流水号为空
            HsAssert.hasText(originNo, AOErrorCode.AO_PARAMS_NULL, "查询POS兑换互生币详情：POS流水号为空");
            // 查询POS兑换互生币详情
            return buyHsbMapper.findPosBuyHsbInfo(hsResNo, originNo);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_QUERY_POS_BUY_HSB_DETAIL_ERROR.getCode() + "查询POS兑换互生币详情异常,params[hsResNo:" + hsResNo + ",originNo:" + originNo + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_POS_BUY_HSB_DETAIL_ERROR.getCode(), "查询POS兑换互生币详情异常,params[hsResNo:" + hsResNo + ",originNo:" + originNo + "]" + e);
        }
    }

    /**
     * 查询POS代兑互生币详情
     * 
     * @param hsResNo
     *            互生号
     * @param originNo
     *            POS流水号
     * @return 代兑互生币信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getPosProxyBuyHsbInfo(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public ProxyBuyHsb getPosProxyBuyHsbInfo(String hsResNo, String originNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "查询POS代兑互生币详情,params[hsResNo:" + hsResNo + ",originNo:" + originNo + "]");
        try
        {
            // 互生号为空
            HsAssert.hasText(hsResNo, AOErrorCode.AO_PARAMS_NULL, "查询POS代兑互生币详情：互生号参数为空");
            // POS流水号为空
            HsAssert.hasText(originNo, AOErrorCode.AO_PARAMS_NULL, "查询POS代兑互生币详情：POS流水号为空");
            // 查询POS兑换互生币详情
            return proxyBuyHsbMapper.findPosBuyHsbInfo(hsResNo, originNo);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_QUERY_POS_PROXY_BUY_HSB_DETAIL_ERROR.getCode() + "查询POS代兑互生币详情异常,params[hsResNo:" + hsResNo + ",originNo:" + originNo + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_POS_PROXY_BUY_HSB_DETAIL_ERROR.getCode(), "查询POS代兑互生币详情异常,params[hsResNo:" + hsResNo + ",originNo:" + originNo + "]" + e);
        }
    }

    /**
     * 获取网银支付URL
     * 
     * @param transNo
     *            交易流水号
     * @param jumpUrl
     *            回跳地址
     * @param privObligate
     *            私有数据
     * @return 网银支付URL
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getNetPayUrl(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public String getNetPayUrl(String transNo, String jumpUrl, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取网银支付URL,params[transNo:" + transNo + ",jumpUrl:" + jumpUrl + ",privObligate:" + privObligate + "]");
        String payUrl = "";
        try
        {
            // 交易流水号为空
            HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "获取网银支付URL：交易流水号为空");
            // 回跳地址为空12.30由于APP不需要加跳地址注释
            // HsAssert.hasText(jumpUrl, AOErrorCode.AO_PARAMS_NULL,
            // "获取网银支付URL：回跳地址为空");

            // 查询未支付且未过期的记录
            BuyHsb buyHsb = findUnPayBuyHsb(transNo);
            // 更新支付方式
            updatePayModel(transNo, PayChannel.E_BANK_PAY.getCode());
            // 获取网银支付URL
            payUrl = dubboInvokService.getNetPayUrl(buyHsb, jumpUrl, privObligate);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_GET_NET_PAY_URL_ERROR.getCode() + "获取网银支付URL异常,params[transNo:" + transNo + ",jumpUrl:" + jumpUrl + ",privObligate:" + privObligate + "]", e);
            throw new HsException(AOErrorCode.AO_GET_NET_PAY_URL_ERROR.getCode(), "获取网银支付URL异常,params[transNo:" + transNo + ",jumpUrl:" + jumpUrl + ",privObligate:" + privObligate + "]" + e);
        }
        return payUrl;
    }

    /**
     * 获取支付URL并开通快捷支付
     * 
     * @param openQuickPayBean
     *            开通快捷支付实体类
     * @return 首次快捷支付URL
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getOpenQuickPayUrl(com.gy.hsxt.ao.bean.OpenQuickPayBean)
     */
    @Override
    @Transactional
    public String getOpenQuickPayUrl(OpenQuickPayBean openQuickPayBean) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "开通快捷支付并获取支付URL,params[" + openQuickPayBean + "]");
        String payUrl = "";
        // 参数对象为空
        HsAssert.notNull(openQuickPayBean, AOErrorCode.AO_PARAMS_NULL, "开通快捷支付并获取支付URL：输入参数对象为空");
        try
        {
            // 查询未支付且未过期的记录
            BuyHsb buyHsb = findUnPayBuyHsb(openQuickPayBean.getOrderNo());
            // 更新支付方式
            updatePayModel(openQuickPayBean.getOrderNo(), PayChannel.QUICK_PAY.getCode());

            /**
             * 初始化bean{
             */
            openQuickPayBean.setOrderNo(buyHsb.getTransNo());// 交易流水号
            openQuickPayBean.setOrderDate(DateUtil.StringToDate(buyHsb.getReqTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 订单日期
            openQuickPayBean.setCustId(buyHsb.getCustId());// 客户号
            openQuickPayBean.setTransAmount(buyHsb.getBuyHsbAmt());// 交易金额
            /**
             * }
             */

            // 获取网银支付URL
            payUrl = dubboInvokService.getOpenQuickPayUrl(openQuickPayBean);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_OPEN_QUICK_PAY_URL_ERROR.getCode() + "获取支付URL并开通快捷支付异常,params[" + openQuickPayBean + "]", e);
            throw new HsException(AOErrorCode.AO_OPEN_QUICK_PAY_URL_ERROR.getCode(), " 获取支付URL并开通快捷支付异常,params[" + openQuickPayBean + "]" + e);
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
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getQuickPayUrl(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public String getQuickPayUrl(String transNo, String bindingNo, String smsCode) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付URL,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]");
        String payUrl = "";
        // 交易流水号为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "获取快捷支付URL：交易流水号为空");
        // 签约号为空
        HsAssert.hasText(bindingNo, AOErrorCode.AO_PARAMS_NULL, "获取快捷支付URL：签约号为空");
        // 短信验证码为空
        HsAssert.hasText(smsCode, AOErrorCode.AO_PARAMS_NULL, "获取快捷支付URL：短信验证码为空");
        try
        {
            // 查询未支付且未过期的记录
            findUnPayBuyHsb(transNo);
            // 更新支付方式
            updatePayModel(transNo, PayChannel.QUICK_PAY.getCode());
            // 获取网银支付URL
            payUrl = dubboInvokService.getQuickPayUrl(transNo, bindingNo, smsCode);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_GET_QUICK_PAY_URL_ERROR.getCode() + "获取快捷支付URL异常", e);
            throw new HsException(AOErrorCode.AO_GET_QUICK_PAY_URL_ERROR.getCode(), " 获取快捷支付URL异常" + e);
        }
        return payUrl;
    }

    /**
     * 获取手机支付TN码
     * 
     * @param transNo
     *            交易流水号
     * @param privObligate
     *            私有数据
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getPaymentByMobileTN(java.lang.String,
     *      java.lang.String)
     */
    @Override
    @Transactional
    public String getPaymentByMobileTN(String transNo, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取手机支付TN码,params[transNo:" + transNo + ",privObligate:" + privObligate + "]");
        String payUrl = "";
        // 交易流水号为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "兑换互生币获取手机支付TN码：交易流水号为空");
        try
        {
            // 查询未支付且未过期的记录
            BuyHsb buyHsb = findUnPayBuyHsb(transNo);
            // 更新支付方式
            updatePayModel(transNo, PayChannel.MOBILE_PAY.getCode());
            // 获取网银支付URL
            payUrl = dubboInvokService.getMobilePayTn(buyHsb, privObligate);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_GET_MOBILE_PAY_TN_CODE_ERROR.getCode() + "兑换互生币获取手机支付TN码异常", e);
            throw new HsException(AOErrorCode.AO_GET_MOBILE_PAY_TN_CODE_ERROR.getCode(), "兑换互生币获取手机支付TN码异常" + e);
        }
        return payUrl;
    }

    /**
     * 获取快捷支付短信验证码
     * 
     * @param bindingNo
     *            bindingNo
     * @param privObligate
     *            私有数据
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#getQuickPaySmsCode(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void getQuickPaySmsCode(String transNo, String bindingNo, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付短信验证码,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]");
        // 交易流水号为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "获取快捷支付短信验证码：交易流水号为空");
        // 签约号为空
        HsAssert.hasText(bindingNo, AOErrorCode.AO_PARAMS_NULL, "获取快捷支付短信验证码：签约号为空");
        try
        {
            // 查询未支付且未过期的记录
            BuyHsb buyHsb = findUnPayBuyHsb(transNo);
            // 获取网银支付URL
            dubboInvokService.getQuickPaySmsCode(buyHsb, bindingNo, privObligate);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_GET_QUICK_PAY_SMS_CODE_ERROR.getCode() + "获取快捷支付短信验证码异常,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]", e);
            throw new HsException(AOErrorCode.AO_GET_QUICK_PAY_SMS_CODE_ERROR.getCode(), "获取快捷支付短信验证码异常,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]" + e);
        }
    }

    /**
     * 货币支付
     * 
     * @param transNo
     *            交易流水号
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#paymentByCurrency(java.lang.String)
     */
    @Override
    @Transactional
    public void paymentByCurrency(String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "兑换互生币货币支付,params[transNo:" + transNo + "]");
        try
        {
            // 交易流水号为空
            HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "兑换互生币货币支付：交易流水号为空");
            // 查询未支付且未过期的记录
            BuyHsb buyHsb = findUnPayBuyHsb(transNo);
            // 设置支付方式
            buyHsb.setPayModel(PayChannel.MONEY_PAY.getCode());
            // 更新交易结果
            buyHsbMapper.updateBuyHsbStatus(transNo, buyHsb.getPayModel(), BuyHsbTransResult.TRANS_SUCCESS.getCode(), BuyHsbPayStatus.PAY_SUCCESS.getCode(), "货币账户支付兑换互生币", "");
            // 记帐分解
            accountingService.saveAccounting(buyHsb);
            // 兑换互生币后续处理规则
            accountRuleService.afterBuyHsb(buyHsb.getCustId(), buyHsb.getCustType(), buyHsb.getBuyHsbAmt());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_BUY_HSB_PAY_BY_CURRENCY_ERROR.getCode() + "兑换互生币货币支付异常,params[transNo:" + transNo + "]", e);
            throw new HsException(AOErrorCode.AO_BUY_HSB_PAY_BY_CURRENCY_ERROR.getCode(), "兑换互生币货币支付异常,params[transNo:" + transNo + "]" + e);
        }
    }

    /**
     * 更新支付结果方法
     * 
     * @param paymentOrderState
     *            支付结果实体
     * @see com.gy.hsxt.ao.interfaces.IPaymentNotifyService#updatePayStatus(com.gy.hsxt.gp.bean.PaymentOrderState)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean updatePayStatus(PaymentOrderState paymentOrderState) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "兑换互生币更新支付状态,params[" + JSON.toJSONString(paymentOrderState) + "]");
        try
        {
            lock.lock();
            // 查询业务订单
            BuyHsb buyHsb = buyHsbMapper.findBuyHsbInfo(paymentOrderState.getOrderNo());
            HsAssert.notNull(buyHsb, AOErrorCode.AO_NOT_QUERY_DATA, "兑换互生币更新支付状态：未查询到原始交易单");

            String remark = "";
            // 根据支付方式确定备注信息
            if (PayChannel.E_BANK_PAY.getCode().intValue() == buyHsb.getPayModel())
            {// 网支付支付
                remark = "网银支付兑换互生币";
            }
            else if (PayChannel.QUICK_PAY.getCode().intValue() == buyHsb.getPayModel())
            {// 快捷支付
                remark = "快捷支付兑换互生币";
            }
            else if (PayChannel.MOBILE_PAY.getCode().intValue() == buyHsb.getPayModel())
            {// 手机网银支付
                remark = "手机网银支付兑换互生币";
            }

            // 支付成功的情况
            if (GPConstant.PaymentStateCode.PAY_SUCCESS == paymentOrderState.getTransStatus() && buyHsb.getTransResult() == BuyHsbTransResult.WAIT_PAY.getCode())
            {
                // 更新状态
                buyHsbMapper.updateBuyHsbStatus(buyHsb.getTransNo(), buyHsb.getPayModel(), BuyHsbTransResult.TRANS_SUCCESS.getCode(), paymentOrderState.getTransStatus(), remark, paymentOrderState.getBankOrderNo());
                // 分解记账
                accountingService.saveAccounting(buyHsb);
                // 兑换互生币后续处理规则
                accountRuleService.afterBuyHsb(buyHsb.getCustId(), buyHsb.getCustType(), buyHsb.getBuyHsbAmt());
            }
            else
            // 支付处理中
            if ((BuyHsbPayStatus.PAY_HANDLING.getCode().intValue() == paymentOrderState.getTransStatus()// 支付处理中
                    || BuyHsbPayStatus.READY.getCode().intValue() == paymentOrderState.getTransStatus())// 待支付
                    && buyHsb.getTransResult() == BuyHsbTransResult.WAIT_PAY.getCode()// 等待付款
            )
            {
                // 更新支付状态
                buyHsbMapper.updatePayStatus(buyHsb.getTransNo(), paymentOrderState.getTransStatus(), remark + "支付处理中");
            }
            else
            // 其他状态
            if (paymentOrderState.getTransStatus() == BuyHsbPayStatus.INVALID.getCode().intValue() || paymentOrderState.getTransStatus() == BuyHsbPayStatus.PAY_FAILED.getCode().intValue())
            {
                // 更新交易结果
                buyHsbMapper.updateTransResult(buyHsb.getTransNo(), BuyHsbTransResult.TRANS_FAILED.getCode(), remark + "失败");
                // 更新支付状态
                buyHsbMapper.updatePayStatus(buyHsb.getTransNo(), paymentOrderState.getTransStatus(), remark + "失败");

                // 支付失败更新缓存16.2.18变更为支付成功才累加已兑换金额
                // accountRuleService.reverseSysItemsValue(buyHsb.getCustId(),
                // BusinessParam.SINGLE_DAY_HAD_BUY_HSB
                // .getCode(), buyHsb.getBuyHsbAmt());
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_BUY_HSB_UPDATE_PAY_STATUS_ERROR.getCode() + "兑换互生币：更新支付结果异常,params[" + JSON.toJSONString(paymentOrderState) + "]", e);
            throw new HsException(AOErrorCode.AO_BUY_HSB_UPDATE_PAY_STATUS_ERROR.getCode(), "兑换互生币：更新支付结果异常,params[" + JSON.toJSONString(paymentOrderState) + "]" + e);
        }
        finally
        {
            lock.unlock();
        }
        return true;
    }

    /**
     * 同步更新支付状态
     * 
     * @param paymentResult
     *            支付结果实体
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOExchangeHsbService#synPayStatus(com.gy.hsxt.ao.bean.PaymentResult)
     */
    @Override
    public void synPayStatus(PaymentResult paymentResult) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "同步更新兑换互生币支付状态,params[" + JSON.toJSONString(paymentResult) + "]");
        if (paymentResult != null)
        {
            PaymentOrderState paymentOrderState = new PaymentOrderState();
            BeanUtils.copyProperties(paymentResult, paymentOrderState);
            updatePayStatus(paymentOrderState);
        }
    }

    /**
     * 查询未支付且未付款的兑换互生币信息
     * 
     * @param transNo
     *            交易流水号
     * @return 兑换互生币信息
     */
    public BuyHsb findUnPayBuyHsb(String transNo) {
        BuyHsb buyHsb = null;
        try
        {
            // 查询兑换互生币信息
            buyHsb = buyHsbMapper.findUnPayBuyHsb(transNo);
            HsAssert.notNull(buyHsb, AOErrorCode.AO_NOT_QUERY_DATA, "兑换互生币：未查询到原业务数据");
            int date = DateUtil.compare_date(DateUtil.getCurrentDateTime(), buyHsb.getExpireTime());
            HsAssert.isTrue(date != 1, AOErrorCode.AO_BUY_HSB_ORDER_TIME_OUT, "兑换互生币：订单已超时");
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_QUERY_UNPAY_BUY_HSB_ORDER_ERROR.getCode() + "兑换互生币：查询未支付且未付款的兑换互生币信息异常,param=[transNo:" + transNo + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_UNPAY_BUY_HSB_ORDER_ERROR.getCode(), "兑换互生币：查询未支付且未付款的兑换互生币信息异常,param=[transNo:" + transNo + "]" + e);
        }
        return buyHsb;
    }

    /**
     * 更新支付方式
     * 
     * @param transNo
     *            交易流水号
     * @param payModel
     *            支付方式
     */
    public void updatePayModel(String transNo, int payModel) {
        try
        {
            // 更新支付方式
            buyHsbMapper.updatePayModel(transNo, payModel);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_UPDATE_BUY_HSB_PAY_MODEL_ERROR.getCode() + "兑换互生币：更新支付状态异常,param=[transNo:" + transNo + ",payModel:" + payModel + "]", e);
            throw new HsException(AOErrorCode.AO_UPDATE_BUY_HSB_PAY_MODEL_ERROR.getCode(), "兑换互生币：更新支付状态异常,param=[transNo:" + transNo + ",payModel:" + payModel + "]" + e);
        }
    }

    /**
     * 校验POS冲正参数
     * 
     * @param posBuyHsbCancel
     *            POS兑换互生币冲正信息
     */
    private void verifyPosReverseParam(PosBuyHsbCancel posBuyHsbCancel) {
        // 实体参数为空
        HsAssert.notNull(posBuyHsbCancel, AOErrorCode.AO_PARAMS_NULL, "冲正POS兑换互生币：实体参数为空params[" + posBuyHsbCancel + "]");
        // 终端凭证号为空
        HsAssert.hasText(posBuyHsbCancel.getHsResNo(), AOErrorCode.AO_PARAMS_NULL, "冲正POS兑换互生币：互生号为空params[" + posBuyHsbCancel.getHsResNo() + "]");
        // 终端编号为空
        HsAssert.hasText(posBuyHsbCancel.getTermNo(), AOErrorCode.AO_PARAMS_NULL, "冲正POS兑换互生币：终端编号为空params[" + posBuyHsbCancel.getTermNo() + "]");
        // 批次号为空
        HsAssert.hasText(posBuyHsbCancel.getBatchNo(), AOErrorCode.AO_PARAMS_NULL, "冲正POS兑换互生币：批次号为空params[" + posBuyHsbCancel.getBatchNo() + "]");
        // 终端凭证号为空
        HsAssert.hasText(posBuyHsbCancel.getTermRuncode(), AOErrorCode.AO_PARAMS_NULL, "冲正POS兑换互生币：终端凭证号为空params[" + posBuyHsbCancel.getTermRuncode() + "]");
    }
}
