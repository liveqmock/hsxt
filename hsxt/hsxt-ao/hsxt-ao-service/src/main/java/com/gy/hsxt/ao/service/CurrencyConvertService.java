/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.api.IAOCurrencyConvertService;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.interfaces.IAccountRuleService;
import com.gy.hsxt.ao.interfaces.IAccountingService;
import com.gy.hsxt.ao.interfaces.ICommonService;
import com.gy.hsxt.ao.interfaces.IDubboInvokService;
import com.gy.hsxt.ao.mapper.HsbToCashMapper;
import com.gy.hsxt.ao.mapper.PvToHsbMapper;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 货币转换接口实现类
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: CurrencyConvertService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-11-26 下午5:28:13
 * @version V3.0.0
 */
@Service
public class CurrencyConvertService implements IAOCurrencyConvertService {

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
    IAccountRuleService accoountRuleService;

    /**
     * 注入积分转互生币mapper
     */
    @Autowired
    PvToHsbMapper pvToHsbMapper;

    /**
     * 注入互生币转货币mapper
     */
    @Autowired
    HsbToCashMapper hsbToCashMapper;

    /**
     * 实现保存积分转互生币外部接口方法
     * 
     * @param pvToHsb
     *            积分转互生币信息
     * @return 交易流水号
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOCurrencyConvertService#savePvToHsb(com.gy.hsxt.ao.bean.PvToHsb)
     */
    @Override
    @Transactional
    public String savePvToHsb(PvToHsb pvToHsb) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存积分转互生币,params[" + pvToHsb + "]");
        // 成功记录数
        int returnNum = 0;
        // 实体参数为空
        HsAssert.notNull(pvToHsb, AOErrorCode.AO_PARAMS_NULL, "保存积分转互生币：实体参数为空");
        // 帐户信息
        AccountBalance accountBalance = null;
        try
        {
            /**
             * 校验积分帐户余额{
             */
            // 调用AC获取积分帐户余额
            accountBalance = accountSearchService.searchAccNormal(pvToHsb.getCustId(), AccountType.ACC_TYPE_POINT10110
                    .getCode());
            // 未查询到数据
            HsAssert.notNull(accountBalance, AOErrorCode.AO_INVOKE_AC_NOT_QUERY_DATA, "保存积分转互生币：调用帐务系统查询余额未查询到记录");
            // 帐户余额小于转帐金额
            HsAssert.isTrue(BigDecimalUtils.compareTo(pvToHsb.getAmount(), accountBalance.getAccBalance()) <= 0,
                    AOErrorCode.AO_ACC_BALANCE_NOT_ENOUGH, "保存积分转互生币：积分帐户余额不足");
            /**
             * }
             */

            // 积分转互生币限制
            boolean isAllowPvToHsb = accoountRuleService.checkPvToHsbRule(pvToHsb.getCustId(), pvToHsb.getCustType(),
                    pvToHsb.getAmount());

            // 保底积分规则校验（申请积分数 <= 积分账户余数 - 保底积分数）//16.2.15改为由帐务校验保底

            // 允许积分转互生币
            if (isAllowPvToHsb)
            {
                // 生成交易流水号
                pvToHsb.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
                // 转入互生币帐户
                pvToHsb.setToAccType(AccountType.ACC_TYPE_POINT20110.getCode());
                // 生成交易时间
                pvToHsb.setReqTime(DateUtil.getCurrentDateTime());
                // 插入数据
                returnNum = pvToHsbMapper.insertPvToHsb(pvToHsb);
                // 大于0表示插入成功
                if (returnNum > 0)
                {
                    // 积分转互生币后续处理
                    accoountRuleService.afterPvToHsb(pvToHsb.getCustId(), pvToHsb.getCustType(), pvToHsb.getAmount());
                    // 记帐分解
                    accountingService.saveAccounting(pvToHsb);
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_PV_TO_HSB_ERROR
                    .getCode()
                    + "保存积分转互生币异常,params[" + pvToHsb + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_PV_TO_HSB_ERROR.getCode(), "保存积分转互生币异常,params[" + pvToHsb + "]"
                    + e);
        }
        return pvToHsb.getTransNo();
    }

    /**
     * 保存互生币转货币
     * 
     * @param hsbToCash
     *            互生币转货币信息
     * @return 交易流水号
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOCurrencyConvertService#saveHsbToCash(com.gy.hsxt.ao.bean.HsbToCash)
     */
    @Override
    @Transactional
    public String saveHsbToCash(HsbToCash hsbToCash) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存互生币转货币,params[" + hsbToCash + "]");
        // 成功记录数
        int returnNum = 0;
        // 帐户信息
        AccountBalance accountBalance = null;
        // 实体参数为空
        HsAssert.notNull(hsbToCash, AOErrorCode.AO_PARAMS_NULL, "保存互生币转货币：实体参数为空");
        try
        {
            /**
             * 校验积分帐户余额{
             */
            // 调用AC获取积分帐户余额
            accountBalance = accountSearchService.searchAccNormal(hsbToCash.getCustId(),
                    AccountType.ACC_TYPE_POINT20110.getCode());
            // 未查询到数据
            HsAssert.notNull(accountBalance, AOErrorCode.AO_INVOKE_AC_NOT_QUERY_DATA, "保存互生币转货币：调用帐务系统查询余额未查询到记录");
            // 帐户余额小于转帐金额
            HsAssert.isTrue(BigDecimalUtils.compareTo(hsbToCash.getFromHsbAmt(), accountBalance.getAccBalance()) <= 0,
                    AOErrorCode.AO_ACC_BALANCE_NOT_ENOUGH, "保存互生币转货币：互生币帐户余额不足");
            /**
             * }
             */

            /**
             * 初始化bean信息 {
             */
            // 生成交易流水号
            hsbToCash.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
            // 货币转换费比例
            hsbToCash.setFeeRate(accoountRuleService.getHsbToCashFeeRate());
            // 货币转换费金额（算法：货币转换费比例 * 申请互生币数量）
            hsbToCash.setFeeHsbAmt(BigDecimalUtils.ceiling(
                    BigDecimalUtils.ceilingMul(hsbToCash.getFeeRate(), hsbToCash.getFromHsbAmt()).toString(), 2)
                    .toString());
            // 货币转换比率
            hsbToCash.setExRate(commonService.getLocalInfo().getExchangeRate());
            // 实收货币金额(算法：（申请互生币金额 -货币转换费金额）/ 货币转换比率)
            hsbToCash.setToCashAmt(BigDecimalUtils.floor(
                    BigDecimalUtils.floorDiv(
                            BigDecimalUtils.ceilingSub(hsbToCash.getFromHsbAmt(), hsbToCash.getFeeHsbAmt()).toString(),
                            hsbToCash.getExRate()).toString(), 2).toString());
            // 生成交易时间
            hsbToCash.setReqTime(DateUtil.getCurrentDateTime());
            /**
             * }
             */

            // 互生币转货币限制
            boolean isAllowHsbToCash = accoountRuleService.checkHsbToCashRule(hsbToCash.getCustId(), hsbToCash
                    .getCustType(), hsbToCash.getFromHsbAmt());

            // 校验互生币转货币是否符合保底规则（申请互生币金额 <= 互生币账户余数（流通币） -
            // 互生币保底金额）//16.2.15改为由帐务校验

            // 允许互生币转货币
            if (isAllowHsbToCash)
            {
                // 插入数据
                returnNum = hsbToCashMapper.insertHsbToCash(hsbToCash);
                // 大于0表示插入成功
                if (returnNum > 0)
                {
                    // 互生币转货币后续处理
                    accoountRuleService.afterHsbToCash(hsbToCash.getCustId(), hsbToCash.getCustType(), hsbToCash
                            .getFromHsbAmt());
                    // 记帐分解
                    accountingService.saveAccounting(hsbToCash);
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), AOErrorCode.AO_SAVE_HSB_TO_CASH_ERROR
                    .getCode()
                    + "保存互生币转货币异常,params[" + hsbToCash + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_HSB_TO_CASH_ERROR.getCode(), "保存互生币转货币异常,params[" + hsbToCash
                    + "]" + e);
        }
        return hsbToCash.getTransNo();
    }

    /**
     * 查询积分转互生币
     * 
     * @param transNo
     *            交易流水号
     * @return 积分转互生币详情
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOCurrencyConvertService#getPvToHsbInfo(java.lang.String)
     */
    @Override
    public PvToHsb getPvToHsbInfo(String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询积分转互生币,params[transNo:" + transNo + "]");
        // 交易流水号为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "查询积分转互生币详情：交易流水号为空");
        try
        {
            // 查询
            return pvToHsbMapper.findPvToHsb(transNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_PV_TO_HSB_DETAIL_ERROR.getCode() + "查询积分转互生币详情异常,params[transNo:" + transNo
                            + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_PV_TO_HSB_DETAIL_ERROR.getCode(), "查询积分转互生币详情异常,params[transNo:"
                    + transNo + "]" + e);
        }
    }

    /**
     * 查询互生币转货币
     * 
     * @param transNo
     *            交易流水号
     * @return 互生币转货币信息
     * 
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOCurrencyConvertService#getHsbToCashInfo(java.lang.String)
     */
    @Override
    public HsbToCash getHsbToCashInfo(String transNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询互生币转货币,params[transNo:" + transNo + "]");
        // 交易流水号为空
        HsAssert.hasText(transNo, AOErrorCode.AO_PARAMS_NULL, "查询互生币转货币：交易流水号为空");
        try
        {
            // 查询
            return hsbToCashMapper.findHsbToCash(transNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_HSB_TO_CASH_DETAIL_ERROR.getCode() + "查询互生币转货币异常,params[transNo:" + transNo
                            + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_HSB_TO_CASH_DETAIL_ERROR.getCode(), "查询互生币转货币异常,params[transNo:"
                    + transNo + "]" + e);
        }
    }

    /**
     * 销户积分转互生币
     * 
     * @param pvToHsb
     *            积分转互生币信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOCurrencyConvertService#closeAccountPvToHsb(com.gy.hsxt.ao.bean.PvToHsb)
     */
    @Override
    public void closeAccountPvToHsb(PvToHsb pvToHsb) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户积分转互生币,params[" + pvToHsb + "]");
        // 成功记录数
        int returnNum = 0;
        // 实体参数为空
        HsAssert.notNull(pvToHsb, AOErrorCode.AO_PARAMS_NULL, "销户积分转互生币：实体参数为空");
        try
        {
            // 生成交易流水号
            pvToHsb.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
            // 转入互生币帐户
            pvToHsb.setToAccType(AccountType.ACC_TYPE_POINT20110.getCode());
            // 生成交易时间
            pvToHsb.setReqTime(DateUtil.getCurrentDateTime());
            // 插入数据
            returnNum = pvToHsbMapper.insertPvToHsb(pvToHsb);
            // 大于0表示插入成功
            if (returnNum > 0)
            {
                // 记帐分解
                accountingService.closeAccountAccounting(pvToHsb);
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_CLOSE_ACCOUNT_PV_TO_HSB_ERROR.getCode() + "销户积分转互生币异常,params[" + pvToHsb + "]", e);
            throw new HsException(AOErrorCode.AO_CLOSE_ACCOUNT_PV_TO_HSB_ERROR.getCode(), "销户积分转互生币异常,params["
                    + pvToHsb + "]" + e);
        }
    }

    /**
     * 销户互生币转货币
     * 
     * @param hsbToCash
     *            互生币转货币信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOCurrencyConvertService#closeAccountHsbToCash(com.gy.hsxt.ao.bean.HsbToCash)
     */
    @Override
    public void closeAccountHsbToCash(HsbToCash hsbToCash) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "销户互生币转货币,params[" + hsbToCash + "]");
        // 成功记录数
        int returnNum = 0;
        // 实体参数为空
        HsAssert.notNull(hsbToCash, AOErrorCode.AO_PARAMS_NULL, "销户互生币转货币：实体参数为空");
        try
        {
            /**
             * 初始化bean信息 {
             */
            // 生成交易流水号
            hsbToCash.setTransNo(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
            // 货币转换费比例
            hsbToCash.setFeeRate(accoountRuleService.getHsbToCashFeeRate());
            // 货币转换费金额（算法：货币转换费比例 * 申请互生币数量）
            hsbToCash.setFeeHsbAmt(BigDecimalUtils.ceiling(
                    BigDecimalUtils.ceilingMul(hsbToCash.getFeeRate(), hsbToCash.getFromHsbAmt()).toString(), 2)
                    .toString());
            // 货币转换比率
            hsbToCash.setExRate(commonService.getLocalInfo().getExchangeRate());
            // 实收货币金额(算法：（申请互生币金额 -货币转换费金额）/ 货币转换比率)
            hsbToCash.setToCashAmt(BigDecimalUtils.floor(
                    BigDecimalUtils.floorDiv(
                            BigDecimalUtils.ceilingSub(hsbToCash.getFromHsbAmt(), hsbToCash.getFeeHsbAmt()).toString(),
                            hsbToCash.getExRate()).toString(), 2).toString());
            // 生成交易时间
            hsbToCash.setReqTime(DateUtil.getCurrentDateTime());
            /**
             * }
             */

            // 插入数据
            returnNum = hsbToCashMapper.insertHsbToCash(hsbToCash);
            // 大于0表示插入成功
            if (returnNum > 0)
            {
                // 记帐分解
                accountingService.closeAccountAccounting(hsbToCash);
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_CLOSE_ACCOUNT_HSB_TO_CASH_ERROR.getCode() + "销户互生币转货币异常,params[" + hsbToCash + "]",
                    e);
            throw new HsException(AOErrorCode.AO_CLOSE_ACCOUNT_HSB_TO_CASH_ERROR.getCode(), "销户互生币转货币异常,params["
                    + hsbToCash + "]" + e);
        }
    }
}
