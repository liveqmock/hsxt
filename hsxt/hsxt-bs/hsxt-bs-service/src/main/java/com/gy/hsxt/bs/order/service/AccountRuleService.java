/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.order.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.order.interfaces.IAccountRuleService;
import com.gy.hsxt.common.constant.BoNameEnum;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 账户操作限额限次等规则检查，及操作后续处理，比如累计金额、累计次数更新等
 * 
 * @Package: com.gy.hsxt.ao.testcase
 * @ClassName: AccountRuleService
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-17 下午6:19:22
 * @version V1.0
 */
@Service("accountRuleService")
public class AccountRuleService implements IAccountRuleService {

    @Autowired
    public BusinessParamSearch businessParamSearch;

    @SuppressWarnings("rawtypes")
    @Resource(name = "fixRedisUtil")
    public RedisUtil fixRedisUtil;

    /**
     * 判断指定客户是否允许指定业务操作
     * 
     * @param custId
     *            客户号
     * @param opName
     *            业务操作类别
     * @return true:禁止 false：允许
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private boolean isForbidden(String custId, BoNameEnum opName) {
        String key = "UC.BO_SET_" + custId;
        // opName=BoNameEnum.HB2BANK.name();
        // String key = CacheKeyGen.genBoSettingKey(custId);
        return fixRedisUtil.redisTemplate.opsForHash().hasKey(key, opName.name());
    }

    /**
     * 检查兑换互生币是否符合限额限次规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            兑换互生币金额
     * @param isReg
     *            是否已注册，消费者需要区分
     * @return
     * @see com.gy.hsxt.ao.interfaces.IAccountRuleService#checkBuyHsbRule(java.lang.String,
     *      java.lang.Integer, java.lang.String, boolean)
     */
    @Override
    public boolean checkBuyHsbRule(String custId, Integer custType, String amount, boolean isReg) {
        // 获取消费者积分转互生币规则参数
        Map<String, BusinessSysParamItemsRedis> itemMap = businessParamSearch
                .searchSysParamItemsByGroup(BusinessParam.EXCHANGE_HSB.getCode());
        if (CustType.PERSON.getCode() == custType || CustType.NOT_HS_PERSON.getCode() == custType)
        {
            /********** 校验消费者兑换互生币限制 **********/
            if (!isReg)
            { // 未实名注册消费者校验
                /** 最小额限制校验 **/
                String itemMinValue = getSysItemsValue(itemMap, BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN);
                if (BigDecimalUtils.compareTo(amount, itemMinValue) < 0)
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额小于单笔最小金额");
                }

                /** 最大额限制校验 **/
                String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX);
                if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单笔最大金额");
                }

                /** 单日限额校验 **/
                // 单日最大兑换互生币金额
                String dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.P_NOT_REGISTERED_SINGLE_DAY_BUY_HSB_MAX);
                // 单日已兑换互生币金额
                BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                        BusinessParam.SINGLE_DAY_HAD_BUY_HSB);
                // 判断是否超过单日限额
                if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单日最大限额");
                }

                /** 单日限次校验 **/
                // 单日最大兑换互生币次数
                String dailyLimitTimes = getSysItemsValue(itemMap, BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_NUMBER);
                // 单日已兑换互生币次数
                BusinessCustParamItemsRedis dailyHadTimesItem = searchCustParamItemsByIdKey(custId,
                        BusinessParam.SINGLE_HAD_BUY_HSB_NUMBER);
                // 判断是否超过单日限次数
                if (isGreaterDailyTimesLimit(dailyHadTimesItem, Integer.parseInt(dailyLimitTimes)))
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币次数超出单日最大次数");
                }
            }
            else
            {// 已注册消费者兑换互生币校验
                /** 最小额限制校验 **/
                String itemMinValue = getSysItemsValue(itemMap, BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN);
                if (BigDecimalUtils.compareTo(amount, itemMinValue) < 0)
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额小于单笔最小金额");
                }

                /** 最大额限制校验 **/
                String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX);
                if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单笔最大金额");
                }

                /** 单日限额校验 **/
                // 单日最大兑换互生币金额
                String dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.P_REGISTERED_SINGLE_DAY_BUY_HSB_MAX);
                // 单日已兑换互生币金额
                BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                        BusinessParam.SINGLE_DAY_HAD_BUY_HSB);
                // 判断是否超过单日限额
                if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单日最大限额");
                }

                /** 单日限次校验 **/
                // 单日最大兑换互生币次数
                String dailyLimitTimes = getSysItemsValue(itemMap, BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_NUMBER);
                // 单日已兑换互生币次数
                BusinessCustParamItemsRedis dailyHadTimesItem = searchCustParamItemsByIdKey(custId,
                        BusinessParam.SINGLE_HAD_BUY_HSB_NUMBER);
                // 判断是否超过单日限次
                if (isGreaterDailyTimesLimit(dailyHadTimesItem, Integer.parseInt(dailyLimitTimes)))
                {
                    throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币次数超出单日最大次数");
                }
            }
        }
        else if (CustType.TRUSTEESHIP_ENT.getCode() == custType)
        {
            /********** 校验托管企业兑换互生币限制 **********/
            /** 最小额限制校验 **/
            String itemMinValue = getSysItemsValue(itemMap, BusinessParam.T_SINGLE_BUY_HSB_MIN);
            if (BigDecimalUtils.compareTo(amount, itemMinValue) < 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额小于单笔最小金额");
            }

            /** 最大额限制校验 **/
            String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.T_SINGLE_BUY_HSB_MAX);
            if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单笔最大金额");
            }

            /** 单日限额校验 **/
            // 单日最大兑换互生币金额
            String dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.T_SINGLE_DAY_BUY_HSB_MAX);
            // 单日已兑换互生币金额
            BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.SINGLE_DAY_HAD_BUY_HSB);
            // 判断是否超过单日限额
            if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单日最大限额");
            }

            /** 单日限次校验 **/
            // 单日最大兑换互生币次数
            String dailyLimitTimes = getSysItemsValue(itemMap, BusinessParam.T_SINGLE_BUY_HSB_NUMBER);
            // 单日已兑换互生币次数
            BusinessCustParamItemsRedis dailyHadTimesItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.SINGLE_HAD_BUY_HSB_NUMBER);
            // 判断是否超过单日限次
            if (isGreaterDailyTimesLimit(dailyHadTimesItem, Integer.parseInt(dailyLimitTimes)))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币次数超出单日最大次数");
            }
        }
        else if (CustType.MEMBER_ENT.getCode() == custType)
        {
            /********** 校验成员企业兑换互生币限制 **********/
            /** 最小额限制校验 **/
            String itemMinValue = getSysItemsValue(itemMap, BusinessParam.B_SINGLE_BUY_HSB_MIN);
            if (BigDecimalUtils.compareTo(amount, itemMinValue) < 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额小于单笔最小金额");
            }

            /** 最大额限制校验 **/
            String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.B_SINGLE_BUY_HSB_MAX);
            if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单笔最大金额");
            }

            /** 单日限额校验 **/
            // 单日最大兑换互生币金额
            String dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.B_SINGLE_DAY_BUY_HSB_MAX);
            // 单日已兑换互生币金额
            BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.SINGLE_DAY_HAD_BUY_HSB);
            // 判断是否超过单日限额
            if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单日最大限额");
            }

            /** 单日限次校验 **/
            // 最大兑换互生币次数
            String dailyLimitTimes = getSysItemsValue(itemMap, BusinessParam.B_SINGLE_BUY_HSB_NUMBER);
            // 单日已兑换互生币次数
            BusinessCustParamItemsRedis dailyHadTimesItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.SINGLE_HAD_BUY_HSB_NUMBER);
            // 判断是否超过单日限次
            if (isGreaterDailyTimesLimit(dailyHadTimesItem, Integer.parseInt(dailyLimitTimes)))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币次数超出单日最大次数");
            }
        }
        else if (CustType.SERVICE_CORP.getCode() == custType)
        {
            /********** 校验服务兑换互生币限制 **********/
            /** 最小额限制校验 **/
            String itemMinValue = getSysItemsValue(itemMap, BusinessParam.S_SINGLE_BUY_HSB_MIN);
            if (BigDecimalUtils.compareTo(amount, itemMinValue) < 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额小于单笔最小金额");
            }

            /** 最大额限制校验 **/
            String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.S_SINGLE_BUY_HSB_MAX);
            if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单笔最大金额");
            }

            /** 单日限额校验 **/
            // 单日最大兑换互生币金额
            String dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.S_SINGLE_DAY_BUY_HSB_MAX);
            // 单日已兑换互生币金额
            BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.SINGLE_DAY_HAD_BUY_HSB);
            // 判断是否超过单日限额
            if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币金额超出单日最大限额");
            }

            /** 单日限次校验 **/
            // 最大兑换互生币次数
            String dailyLimitTimes = getSysItemsValue(itemMap, BusinessParam.S_SINGLE_BUY_HSB_NUMBER);
            // 单日已兑换互生币次数
            BusinessCustParamItemsRedis dailyHadTimesItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.SINGLE_HAD_BUY_HSB_NUMBER);
            // 判断是否超过单日限次数
            if (isGreaterDailyTimesLimit(dailyHadTimesItem, Integer.parseInt(dailyLimitTimes)))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "兑换互生币次数超出单日最大次数");
            }
        }
        return true;
    }

    /**
     * 兑换互生币后续处理，，比如更新累计兑换互生币金额、累计兑换次数等
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            兑换互生币金额
     * @see com.gy.hsxt.ao.interfaces.IAccountRuleService#afterBuyHsb(java.lang.String,
     *      java.lang.Integer, java.lang.String)
     */
    @Override
    public void afterBuyHsb(String custId, Integer custType, String amount) {
        // 单日已兑换互生币金额
        BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                BusinessParam.SINGLE_DAY_HAD_BUY_HSB);
        if (DateUtil.getCurrentDateNoSign().equals(dailyHadAmtItem.getDueDate()))
        {
            // 如果缓存单日累计数据未过期，累加金额后更新
            dailyHadAmtItem.setSysItemsValue(BigDecimalUtils.ceilingAdd(dailyHadAmtItem.getSysItemsValue(), amount)
                    .toString());
        }
        else
        {
            // 如果缓存单日累计数据已过期，累计金额重置，过期日期过期日期重设
            dailyHadAmtItem.setSysItemsValue(amount);
            // 设置缓存过期日期
            dailyHadAmtItem.setDueDate(DateUtil.getCurrentDateNoSign());
        }
        businessParamSearch.setBusinessCustParamItemsRed(dailyHadAmtItem);

        // 单日已兑换互生币次数
        BusinessCustParamItemsRedis dailyHadTimes = searchCustParamItemsByIdKey(custId,
                BusinessParam.SINGLE_HAD_BUY_HSB_NUMBER);
        if (DateUtil.getCurrentDateNoSign().equals(dailyHadTimes.getDueDate()))
        {
            // 如果缓存单日累计数据未过期，累加后更新
            dailyHadTimes.setSysItemsValue(String.valueOf(Integer.parseInt(dailyHadTimes.getSysItemsValue()) + 1));
        }
        else
        {
            // 如果缓存单日累计数据已过期，累计次数重置，过期日期过期日期重设
            dailyHadTimes.setSysItemsValue(String.valueOf(1));
            // 设置缓存过期日期
            dailyHadTimes.setDueDate(DateUtil.getCurrentDateNoSign());
        }
        businessParamSearch.setBusinessCustParamItemsRed(dailyHadTimes);
    }

    /**
     * 检查互生币支付是否符合限额限次规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            互生币支付金额
     * @return
     * @see com.gy.hsxt.ao.interfaces.IAccountRuleService#checkHsbToPayRule(java.lang.String,
     *      java.lang.Integer, java.lang.String)
     */
    @Override
    public boolean checkHsbToPayRule(String custId, Integer custType, String amount) {
        // 获取互生币支付规则参数
        Map<String, BusinessSysParamItemsRedis> itemMap = businessParamSearch
                .searchSysParamItemsByGroup(BusinessParam.HSB_PAYMENT.getCode());
        if (CustType.PERSON.getCode() == custType || CustType.NOT_HS_PERSON.getCode() == custType)
        {
            /** 最大额限制校验 **/
            // 先取客户自定义单笔最大金额
            BusinessCustParamItemsRedis custSingleAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.CONSUMER_PAYMENT_MAX);
            String itemMaxValue = custSingleAmtItem.getSysItemsValue();
            if (StringUtils.isBlank(itemMaxValue))
            {
                itemMaxValue = getSysItemsValue(itemMap, BusinessParam.CONSUMER_PAYMENT_MAX);
            }
            if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付金额大于单笔最大金额");
            }

            /** 单日限额校验 **/
            // 先取客户自定义单日最大金额
            BusinessCustParamItemsRedis custDailyLimitAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.CONSUMER_PAYMENT_DAY_MAX);
            String dailyLimitAmt = custDailyLimitAmtItem.getSysItemsValue();
            // 如果客户未定义，取系统默认单日最大限额
            if (StringUtils.isBlank(dailyLimitAmt))
            {
                dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.CONSUMER_PAYMENT_DAY_MAX);
            }
            // 单日已发生金额
            BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.HAD_PAYMENT_DAY);
            // 判断是否超过单日限额
            if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付金额超出单日最大限额");
            }

            /** 单日限次校验--16.2.5业务取消限次 **/
            // 先取客户自定义单日最大次数
            /*
             * BusinessCustParamItemsRedis custDailyLimitTimesItem =
             * searchCustParamItemsByIdKey(custId,
             * BusinessParam.CONSUMER_PAYMENT_DAY_NUMBER); String
             * dailyLimitTimes = custDailyLimitTimesItem.getSysItemsValue(); //
             * 如果客户未定义，取系统默认单日最大限次 if (dailyLimitTimes == null) {
             * dailyLimitTimes = getSysItemsValue(itemMap,
             * BusinessParam.CONSUMER_PAYMENT_DAY_NUMBER); } // 单日已发生次数
             * BusinessCustParamItemsRedis dailyHadTimesItem =
             * searchCustParamItemsByIdKey(custId,
             * BusinessParam.HAD_PAYMENT_DAY_NUMBER); // 判断是否超过单日限次数 if
             * (isGreaterDailyTimesLimit(dailyHadTimesItem,
             * Integer.parseInt(dailyLimitTimes))) { throw new
             * HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付次数超出单日最大次数"); }
             */
        }
        else if (CustType.TRUSTEESHIP_ENT.getCode() == custType || CustType.MEMBER_ENT.getCode() == custType)
        {
            /** 最大额限制校验 **/
            String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.ENT_PAYMENT_MAX);
            if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付金额小于单笔最小金额");
            }

            /** 单日限额校验 **/
            // 先取客户自定义单日最大金额
            BusinessCustParamItemsRedis custDailyLimitAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.ENT_PAYMENT_DAY_MAX);
            String dailyLimitAmt = custDailyLimitAmtItem.getSysItemsValue();
            // 如果客户未定义，取系统默认单日最大限额
            if (StringUtils.isBlank(dailyLimitAmt))
            {
                dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.ENT_PAYMENT_DAY_MAX);
            }
            // 单日已发生金额
            BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.HAD_PAYMENT_DAY);
            // 判断是否超过单日限额
            if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付金额超出单日最大限额");
            }

            /** 单日限次校验 **/
            // 先取客户自定义单日最大次数
            /*
             * BusinessCustParamItemsRedis custDailyLimitTimesItem =
             * searchCustParamItemsByIdKey(custId,
             * BusinessParam.ENT_PAYMENT_DAY_NUMBER); String dailyLimitTimes =
             * custDailyLimitTimesItem.getSysItemsValue(); //
             * 如果客户未定义，取系统默认单日最大限次 if (dailyLimitTimes == null) {
             * dailyLimitTimes = getSysItemsValue(itemMap,
             * BusinessParam.ENT_PAYMENT_DAY_NUMBER); } // 单日已发生次数
             * BusinessCustParamItemsRedis dailyHadTimesItem =
             * searchCustParamItemsByIdKey(custId,
             * BusinessParam.HAD_PAYMENT_DAY_NUMBER); // 判断是否超过单日限次数 if
             * (isGreaterDailyTimesLimit(dailyHadTimesItem,
             * Integer.parseInt(dailyLimitTimes))) { throw new
             * HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付次数超出单日最大次数"); }
             */
        }
        else if (CustType.SERVICE_CORP.getCode() == custType)
        {
            /** 最大额限制校验 **/
            String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.COMPANY_PAYMENT_MAX);
            if (BigDecimalUtils.compareTo(amount, itemMaxValue) > 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付金额小于单笔最小金额");
            }

            /** 单日限额校验 **/
            // 先取客户自定义单日最大金额
            BusinessCustParamItemsRedis custDailyLimitAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.COMPANY_PAYMENT_DAY_MAX);
            String dailyLimitAmt = custDailyLimitAmtItem.getSysItemsValue();
            // 如果客户未定义，取系统默认单日最大限额
            if (StringUtils.isBlank(dailyLimitAmt))
            {
                dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.COMPANY_PAYMENT_DAY_MAX);
            }
            // 单日已发生金额
            BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId,
                    BusinessParam.HAD_PAYMENT_DAY);
            // 判断是否超过单日限额
            if (isGreaterDailyAmtLimit(dailyHadAmtItem, amount, dailyLimitAmt))
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付金额超出单日最大限额");
            }

            /** 单日限次校验 **/
            // 先取客户自定义单日最大次数
            /*
             * BusinessCustParamItemsRedis custDailyLimitTimesItem =
             * searchCustParamItemsByIdKey(custId,
             * BusinessParam.COMPANY_PAYMENT_DAY_NUMBER); String dailyLimitTimes
             * = custDailyLimitTimesItem.getSysItemsValue(); //
             * 如果客户未定义，取系统默认单日最大限次 if (dailyLimitTimes == null) {
             * dailyLimitTimes = getSysItemsValue(itemMap,
             * BusinessParam.COMPANY_PAYMENT_DAY_NUMBER); }
             * 
             * // 单日已发生次数 BusinessCustParamItemsRedis dailyHadTimesItem =
             * searchCustParamItemsByIdKey(custId,
             * BusinessParam.HAD_PAYMENT_DAY_NUMBER); // 判断是否超过单日限次数 if
             * (isGreaterDailyTimesLimit(dailyHadTimesItem,
             * Integer.parseInt(dailyLimitTimes))) { throw new
             * HsException(RespCode.PARAM_ERROR.getCode(), "互生币支付次数超出单日最大次数"); }
             */
        }
        return true;
    }

    /**
     * 互生币支付后续处理，比如更新累计互生币支付金额、支付次数等
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            支付互生币金额
     * @see com.gy.hsxt.ao.interfaces.IAccountRuleService#afterHsbToPay(java.lang.String,
     *      java.lang.Integer, java.lang.String)
     */
    @Override
    public void afterHsbToPay(String custId, Integer custType, String amount) {
        // 更新单日已互生币支付金额
        BusinessCustParamItemsRedis dailyHadAmtItem = searchCustParamItemsByIdKey(custId, BusinessParam.HAD_PAYMENT_DAY);
        if (DateUtil.getCurrentDateNoSign().equals(dailyHadAmtItem.getDueDate()))
        {
            // 如果缓存单日累计数据未过期，累加金额后更新
            dailyHadAmtItem.setSysItemsValue(BigDecimalUtils.ceilingAdd(dailyHadAmtItem.getSysItemsValue(), amount)
                    .toString());
        }
        else
        {
            // 如果缓存单日累计数据已过期，累计金额重置，过期日期过期日期重设
            dailyHadAmtItem.setSysItemsValue(amount);
            // 设置缓存过期日期
            dailyHadAmtItem.setDueDate(DateUtil.getCurrentDateNoSign());
        }
        businessParamSearch.setBusinessCustParamItemsRed(dailyHadAmtItem);

        // 更新单日已互生币支付次数
        /*
         * BusinessCustParamItemsRedis dailyHadTimes =
         * searchCustParamItemsByIdKey(custId,
         * BusinessParam.HAD_PAYMENT_DAY_NUMBER); if
         * (DateUtil.getCurrentDateNoSign().equals(dailyHadTimes.getDueDate()))
         * { // 如果缓存单日累计数据未过期，累加后更新
         * dailyHadTimes.setSysItemsValue(String.valueOf
         * (Integer.parseInt(dailyHadTimes.getSysItemsValue()) + 1)); } else {
         * // 如果缓存单日累计数据已过期，累计次数重置，过期日期过期日期重设
         * dailyHadTimes.setSysItemsValue(String.valueOf(1)); // 设置缓存过期日期
         * dailyHadTimes.setDueDate(DateUtil.getCurrentDateNoSign()); }
         * businessParamSearch.setBusinessCustParamItemsRed(dailyHadTimes);
         */
    }

    /**
     * 校验积分投资是否符合规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            投资积分数
     * @return true/false
     * @see com.gy.hsxt.bs.order.interfaces.IAccountRuleService#checkPvToInvestRule(java.lang.String,
     *      java.lang.Integer, java.lang.String)
     */
    @Override
    public boolean checkPvToInvestRule(String custId, Integer custType, String amount) {
        // 是否开启了业务
        BusinessCustParamItemsRedis isOpenBiz = searchCustParamItemsByIdKey(custId, BusinessParam.PV_INVEST);
        if (isOpenBiz != null && StringUtils.isNotBlank(isOpenBiz.getSysItemsValue()))
            // 如果为1则禁止业务
            HsAssert.isTrue(isOpenBiz.getSysItemsValue().equals("0"), BSRespCode.BS_CUST_BIZ_FORBIDDEN, isOpenBiz
                    .getSettingRemark());

        // 获取积分投资规则参数
        Map<String, BusinessSysParamItemsRedis> itemMap = businessParamSearch
                .searchSysParamItemsByGroup(BusinessParam.JF_INVEST.getCode());
        if (CustType.PERSON.getCode() == custType)
        {
            /********** 校验消费者积分投资限制 **********/
            /** 最小额限制校验 **/
            // 获取个人单笔积分投资最小限额
            String itemMinValue = getSysItemsValue(itemMap, BusinessParam.P_SINGLE_INVEST_POINT_MIN);
            if (BigDecimalUtils.compareTo(amount, itemMinValue) < 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "积分投资金额小于单笔最小金额");
            }
        }
        else
        {
            /********** 校验企业积分投资限制 **********/
            /** 最小额限制校验 **/
            // 获取企业单笔积分投资最小限额
            String itemMinValue = getSysItemsValue(itemMap, BusinessParam.C_SINGLE_INVEST_POINT_MIN);
            if (BigDecimalUtils.compareTo(amount, itemMinValue) < 0)
            {
                throw new HsException(RespCode.PARAM_ERROR.getCode(), "积分投资金额小于单笔最小金额");
            }
        }
        return true;
    }

    /**
     * 校验积分是否符合保底规则
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param amount
     *            帐户余额
     * @return true/false
     * @see com.gy.hsxt.bs.order.interfaces.IAccountRuleService#checkPvLowRule(java.lang.String,
     *      java.lang.Integer, java.lang.String)
     */
    @Override
    public boolean checkPvLowRule(String custId, Integer custType, String amount) {
        // 获取消费者积分转互生币规则参数
        Map<String, BusinessSysParamItemsRedis> itemMap = businessParamSearch
                .searchSysParamItemsByGroup(BusinessParam.JF_CHANGE_HSB.getCode());
        if (CustType.PERSON.getCode() == custType)
        {
            /** 积分保底限制校验 **/
            String lowPvValue = getSysItemsValue(itemMap, BusinessParam.P_SAVING_POINT);
            if (BigDecimalUtils.compareTo(amount, lowPvValue) <= 0)
            {
                throw new HsException(BSRespCode.BS_PV_TO_INVEST_MORE_THAN_LOW, "积分投资金额大于积分保底金额[" + lowPvValue + "]");
            }
        }
        else
        {
            /** 积分保底限制校验 **/
            String lowPvValue = getSysItemsValue(itemMap, BusinessParam.C_SAVING_POINT);
            if (BigDecimalUtils.compareTo(amount, lowPvValue) <= 0)
            {
                throw new HsException(BSRespCode.BS_PV_TO_INVEST_MORE_THAN_LOW, "积分投资金额大于积分保底金额[" + lowPvValue + "]");
            }
        }
        return true;
    }

    /**
     * 获取保底积分数
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @return 保底积分数
     * @see com.gy.hsxt.bs.order.interfaces.IAccountRuleService#getPvSaveAmount(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public String getPvSaveAmount(String custId, Integer custType) {
        // 获取消费者积分转互生币规则参数
        Map<String, BusinessSysParamItemsRedis> itemMap = businessParamSearch
                .searchSysParamItemsByGroup(BusinessParam.JF_CHANGE_HSB.getCode());
        String lowPvValue = "";
        if (CustType.PERSON.getCode() == custType)
        {
            /** 积分保底限制校验 **/
            lowPvValue = getSysItemsValue(itemMap, BusinessParam.P_SAVING_POINT);
            return lowPvValue;
        }
        else
        {
            /** 积分保底限制校验 **/
            lowPvValue = getSysItemsValue(itemMap, BusinessParam.C_SAVING_POINT);
            return lowPvValue;
        }
    }

    /**
     * 获取互生币转货币手续费比率
     * 
     * @return
     */
    @Override
    public String getHsbToCashFeeRate() {
        return getSysItemsValue(BusinessParam.HSB_CHANGE_HB, BusinessParam.HSB_CHANGE_HB_RATIO);
    }

    /**
     * 判断银行转账申请是否需要复核
     * 
     * @param custType
     *            客户类型
     * @param amount
     *            申请金额
     * @return true：需要复核 fasle： 不需要复核
     * @see com.gy.hsxt.ao.interfaces.IAccountRuleService#isNeedReviewForTransOut(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public boolean isNeedReviewForTransOut(Integer custType, String amount) {
        String reviewLimit = null;
        if (CustType.PERSON.getCode() == custType || CustType.NOT_HS_PERSON.getCode() == custType)
        {
            // 个人银行转账金额复核阀值
            reviewLimit = getSysItemsValue(BusinessParam.HB_CHANGE_BANK,
                    BusinessParam.PERSON_SINGLE_TRANSFER_CHECK_LIMIT);
        }
        else
        {
            // 企业银行转账金额复核阀值
            reviewLimit = getSysItemsValue(BusinessParam.HB_CHANGE_BANK,
                    BusinessParam.COMPANY_SINGLE_TRANSFER_CHECK_LIMIT);
        }
        // 如果有设置银行转账复核阀值并且转账金额>=复核阀值，需要复核，返回true
        if (StringUtils.isNotBlank(reviewLimit) && BigDecimalUtils.compareTo(amount, reviewLimit) >= 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 获取个人货币转银行复核阀值
     * 
     * @return
     */
    @Override
    public String getPerCheckLimitForTransOut() {
        return getSysItemsValue(BusinessParam.HB_CHANGE_BANK, BusinessParam.PERSON_SINGLE_TRANSFER_CHECK_LIMIT);
    }

    /**
     * 获取企业货币转银行复核阀值
     * 
     * @return
     */
    @Override
    public String getEntCheckLimitForTransOut() {
        return getSysItemsValue(BusinessParam.HB_CHANGE_BANK, BusinessParam.COMPANY_SINGLE_TRANSFER_CHECK_LIMIT);
    }

    /**
     * 获取代兑互生币手续费比例
     * 
     * @return
     */
    @Override
    public String getEntFeeRateForProxyBuyHsb() {
        return getSysItemsValue(BusinessParam.EXCHANGE_HSB, BusinessParam.BUY_HSB_FEE);
    }

    /**
     * 获取个人投资分红流通币比率
     * 
     * @return
     */
    @Override
    public String getPerInvesDividHsbScale() {
        return getSysItemsValue(BusinessParam.JF_INVEST, BusinessParam.P_EXCHENGEABLE_HSB_SCALE);
    }

    /**
     * 获取企业投资分红流通币比率
     * 
     * @return
     */
    @Override
    public String getEntInvesDividHsbScale() {
        return getSysItemsValue(BusinessParam.JF_INVEST, BusinessParam.C_EXCHENGEABLE_HSB_SCALE);
    }

    /**
     * 获取个人投资分红定向消费币比率
     * 
     * @return
     */
    @Override
    public String getPerInvesDirectHsbScale() {
        return getSysItemsValue(BusinessParam.JF_INVEST, BusinessParam.P_CONSUMABLE_HSB_SCALE);
    }

    /**
     * 获取企业投资分红慈善救助基金比率
     * 
     * @return
     */
    @Override
    public String getEntInvesDirectHsbScale() {
        return getSysItemsValue(BusinessParam.JF_INVEST, BusinessParam.C_DIRECTIONAL_SUPPORT_HSB_SCALE);
    }

    /**
     * 获取互生币保底值
     * 
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @return 互生币保底值
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IAccountRuleService#getHsbSaveAmount(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public String getHsbSaveAmount(String custId, Integer custType) throws HsException {
        // 获取消费者积分转互生币规则参数
        Map<String, BusinessSysParamItemsRedis> itemMap = businessParamSearch
                .searchSysParamItemsByGroup(BusinessParam.PUB_ACCOUNT_THRESHOLD.getCode());
        String lowHsbValue = "";
        if (CustType.PERSON.getCode() == custType || CustType.NOT_HS_PERSON.getCode() == custType)
        {
            /** 个人互生币保底限制校验 **/
            lowHsbValue = getSysItemsValue(itemMap, BusinessParam.P_SAVING_HSB);
            return lowHsbValue;
        }
        else
        {
            /** 企业互生币保底限制校验 **/
            lowHsbValue = getSysItemsValue(itemMap, BusinessParam.C_SAVING_HSB);
            return lowHsbValue;
        }
    }

    /**
     * 判断是否超过单日累计限额
     * 
     * @param dailyHadAmtItem
     *            单日累计金额缓存值
     * @param amount
     *            本次发生金额
     * @param dailyLimitAmt
     *            单日最大允许金额
     * @return true：超过单日限额 false：未超单日限额
     */
    private boolean isGreaterDailyAmtLimit(BusinessCustParamItemsRedis dailyHadAmtItem, String amount,
            String dailyLimitAmt) {
        String dailyHadAmt = null;
        // 判断dailyHadAmtItem是否过期，日期与当前日期相同则未过期，否则过期
        if (dailyHadAmtItem != null && DateUtil.getCurrentDateNoSign().equals(dailyHadAmtItem.getDueDate()))
        {
            // 如果累计金额未过期，比较时以累计金额+本次金额之和与最大单日允许金额进行比较
            dailyHadAmt = BigDecimalUtils.ceilingAdd(dailyHadAmtItem.getSysItemsValue(), amount).toPlainString();
        }
        else
        {
            // 如果累计金额已过期，比较时以本次金额与最大单日允许金额进行比较
            dailyHadAmt = amount;
        }
        return BigDecimalUtils.compareTo(dailyHadAmt, dailyLimitAmt) > 0;
    }

    /**
     * 判断是否超过单日累计限次
     * 
     * @param dailyHadTimesItem
     *            单日累计次数缓存值
     * @param dailyLimitAmt
     *            单日最大允许次数
     * @return true：超过单日限次 false：未超单日限次
     */
    private boolean isGreaterDailyTimesLimit(BusinessCustParamItemsRedis dailyHadTimesItem, int dailyLimitTimes) {
        int dailyHadTimes = 1;
        // 判断dailyHadTimesItem是否过期，日期与当前日期相同则未过期，否则过期
        if (dailyHadTimesItem != null && DateUtil.getCurrentDateNoSign().equals(dailyHadTimesItem.getDueDate()))
        {
            // 如果累计次数未过期，比较时以累计次数+本次之和与最大单日允许次数进行比较
            dailyHadTimes = Integer.parseInt(dailyHadTimesItem.getSysItemsValue()) + 1;
        }
        return dailyHadTimes > dailyLimitTimes;
    }

    /**
     * 获取客户私有参数项
     * 
     * 因为BP查询接口有可能返回一个空的参数项对象或者返回的参数对象属性为空， 所以统一包一下这个查询接口，对空对象或者空属性处理一下
     * 
     * @param custId
     *            客户号
     * @param businessParam
     *            参数枚举值
     * @return
     */
    private BusinessCustParamItemsRedis searchCustParamItemsByIdKey(String custId, BusinessParam businessParam) {
        BusinessCustParamItemsRedis custParamItems = businessParamSearch.searchCustParamItemsByIdKey(custId,
                businessParam.getCode());
        if (custParamItems == null)
        {
            // 如果BP返回的对象为空，新建一个
            custParamItems = new BusinessCustParamItemsRedis();
        }
        // 因为BP可能返回的参数对象属性为空，所以重新设置一下值
        custParamItems.setCustId(custId);
        custParamItems.setSysItemsKey(businessParam.getCode());
        return custParamItems;
    }

    /**
     * 获取业务参数中的公共系统参数值,单独根据组代码及项代码查询
     * 
     * @param groupKey
     *            参数组代码
     * @param itemKey
     *            参数项代码
     * @return
     */
    private String getSysItemsValue(BusinessParam groupKey, BusinessParam itemKey) {
        BusinessSysParamItemsRedis item = businessParamSearch.searchSysParamItemsByCodeKey(groupKey.getCode(), itemKey
                .getCode());
        if (item == null)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "获取业务参数项返回对象为空：groupKey=" + groupKey.getCode()
                    + ",itemKey=" + itemKey.getCode());
        }
        String value = item.getSysItemsValue();
        if (StringUtils.isBlank(value) || value.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "获取业务参数项的参数值为空：groupKey=" + groupKey.getCode()
                    + ",itemKey=" + itemKey.getCode());
        }
        return value;
    }

    /**
     * 获取业务参数中的公共系统参数值,从参数组集合中获取指定参数项
     * 
     * @param itemMap
     *            参数组Map, 其中key为参数项key， value为参数项对象
     * @param itemKey
     *            参数项代码
     * @return
     */
    private String getSysItemsValue(Map<String, BusinessSysParamItemsRedis> itemMap, BusinessParam itemKey) {
        BusinessSysParamItemsRedis item = itemMap.get(itemKey.getCode());
        if (item == null)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "获取业务参数项返回对象为空：itemMap=" + itemMap + ",itemKey="
                    + itemKey.getCode());
        }
        String value = item.getSysItemsValue();
        if (StringUtils.isBlank(value) || value.isEmpty())
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "获取业务参数项的参数值为空：itemMap=" + itemMap + ",itemKey="
                    + itemKey.getCode());
        }
        return value;
    }

    /**
     * 清空缓存：测试用
     * 
     * @param custId
     *            客户号
     * @see com.gy.hsxt.ao.interfaces.IAccountRuleService#setSysItemsValue(java.lang.String)
     */
    @Override
    public void setSysItemsValue(String custId) {
        BusinessCustParamItemsRedis bcpir = new BusinessCustParamItemsRedis();
        bcpir.setCustId(custId);
        bcpir.setDueDate("123");
        bcpir.setSysItemsKey(BusinessParam.SINGLE_TRANSFER_HAD_CHECK_NUMBER.getCode());
        businessParamSearch.setBusinessCustParamItemsRed(bcpir);
    }
}
