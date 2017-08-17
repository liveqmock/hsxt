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

package com.gy.hsxt.ac.common.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountService;
import com.gy.hsxt.ac.mapper.AccountBalanceMapper;
import com.gy.hsxt.ac.mapper.AccountCustTypeMapper;
import com.gy.hsxt.ac.mapper.AccountEntryMapper;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.NumbericUtils;

/**
 * 校验方法
 * 
 * @Package: com.gy.hsxt.ac.common.bean
 * @ClassName: ValidateInfo
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-14 下午4:09:12
 * @version V1.0
 */
@Service
public class ValidateInfo {

    /** 账务分录的接口服务 */
    @Autowired
    AccountEntryMapper accountEntryMapper;

    /** 账务余额的接口服务 */
    @Autowired
    AccountBalanceMapper accountBalanceMapper;

    /** 账户和客户类型关系的接口服务 */
    @Autowired
    AccountCustTypeMapper accountCustTypeMapper;

    /**
     * 封装或者获取数据方法
     */
    @Autowired
    private SetAndGetDataMethod setAndGetDataMethod;
    
    /**
     * 校验AccountEntry对象及对象中的条件
     * 
     * @param accountEntry
     *            (分录对象) correctFlag 是否冲正红冲标识(false 非冲正红冲，true 冲正红冲)
     * @throws HsException
     *             异常处理类
     */
    public void checkAccountEntryInfo(AccountEntry accountEntry, Boolean correctFlag) throws HsException {
        BizLog.biz("HSXT_AC", "方法：checkAccountEntryInfo", "accountEntry", "验证记账分录参数值");

        // 校验对象是否为空
        if (accountEntry == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数:AccountEntry,分录对象为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "AccountEntry:分录对象为空");
        }

        // 分录增向金额。如果为空则初始化为0,String转BigDecimal类型
        String addAmt = accountEntry.getAddAmount();// 增向金额
        if (addAmt == null || "".equals(addAmt) || "null".equals(addAmt))
        {
            addAmt = "0";
            accountEntry.setAddAmount(addAmt);
        }
        BigDecimal addAmount = new BigDecimal(addAmt);// 增向金额

        // 分录减向金额。如果为空则初始化为0,String转BigDecimal类型
        String subAmt = accountEntry.getSubAmount();// 减向金额
        if (subAmt == null || "".equals(subAmt) || "null".equals(subAmt))
        {
            subAmt = "0";
            accountEntry.setSubAmount(subAmt);
        }
        BigDecimal subAmount = new BigDecimal(subAmt);// 减向金额

        // 账务分录中不能同时增向金额为0，并且减向金额也为0
        if (addAmount.compareTo(BigDecimal.valueOf(0)) == 0 && subAmount.compareTo(BigDecimal.valueOf(0)) == 0)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "分录记账中增向金额和减向金额不能同时为空或者同时为0");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "分录记账中增向金额和减向金额不能同时为空或者同时为0");
        }

        // 账务分录中不能同时增向金额大于0，并且减向金额也大于0
        if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1 && subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_AMOUNT_POSITIVE.getCode()
                    + "分录记账金额不能同时有增向金额（正数）和减向金额（正数）");
            throw new HsException(RespCode.AC_AMOUNT_POSITIVE.getCode(), "分录记账金额不能同时有增向金额（正数）和减向金额（正数）");
        }

        // 账务分录中不能同时增向金额小于0，并且减向金额也小于0
        if (addAmount.compareTo(BigDecimal.valueOf(0)) == -1 && subAmount.compareTo(BigDecimal.valueOf(0)) == -1)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_AMOUNT_NEGATIVE.getCode()
                    + "记账金额不能同时有增向金额（负数）和减向金额（负数）");
            throw new HsException(RespCode.AC_AMOUNT_NEGATIVE.getCode(), "记账金额不能同时有增向金额（负数）和减向金额（负数）");
        }

        // 客户全局编码,不能为空
        String custID = accountEntry.getCustID();
        if (custID == null || "".equals(custID))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custID:客户全局编码为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
        }

        // 账户类型编码,不能为空
        String accType = accountEntry.getAccType();
        if (accType == null || "".equals(accType))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accType:账户类型编码为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
        }

        // 批次号,不能为空
        String batchNo = accountEntry.getBatchNo();
        if (batchNo == null || "".equals(batchNo))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "batchNo:批次号为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "batchNo:批次号为空");
        }

        // 客户类型,不能为空
        Integer custType = accountEntry.getCustType();
        if (custType == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custType:客户类型编码为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:客户类型编码为空");
        }

        // 交易流水号,不能为空
        String transNo = accountEntry.getTransNo();
        if (transNo == null || "".equals(transNo))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "transNo:交易流水号为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "transNo:交易流水号为空");
        }

        // 交易类型,不能为空
        String transType = accountEntry.getTransType();
        if (transType == null || "".equals(transType))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "transType:交易类型为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "transType:交易类型为空");
        }

        // 各系统分录序号,不能为空
        String sysEntryNo = accountEntry.getSysEntryNo();
        if (sysEntryNo == null || "".equals(sysEntryNo))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "sysEntryNo:系统分录序号为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "sysEntryNo:系统分录序号为空");
        }

        // 红冲标识,不能为空
        Integer writeBack = accountEntry.getWriteBack();
        if (writeBack == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数:writeBack:红冲标识为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "writeBack:红冲标识为空");
        }

        // 冲正红冲分录
        if (correctFlag)
        {
            // 冲正红冲账务分录中的红冲标识不能为0
            if (writeBack == AcConstant.WRICKBACK_FLAG)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                        + "writeBack = " + writeBack + "冲正红冲分录中红冲标识不能为正常状态0");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "writeBack = " + writeBack
                        + "冲正红冲分录中红冲标识不能为正常状态0");
            }

            // 关联交易流水号
            String relTransNo = accountEntry.getRelTransNo();
            if (relTransNo == null || "".equals(relTransNo))
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                        + "relTransNo：冲正红冲关联交易流水号不能为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo：冲正红冲关联交易流水号不能为空");
            }

            // 关联系统分录序列号
            String relSysEntryNo = accountEntry.getRelSysEntryNo();
            if (relSysEntryNo == null || "".equals(relSysEntryNo))
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                        + "relSysEntryNo：关联系统分录序列号不能为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relSysEntryNo：关联系统分录序列号不能为空");
            }
        }
        else
        {
            // 正常账务分录中的红冲标识应该为0
            if (writeBack.intValue() != AcConstant.WRICKBACK_FLAG.intValue())
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                        + "writeBack = " + writeBack + "非冲正红冲分录中红冲标识应该为为正常状态0");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "writeBack = " + writeBack
                        + "非冲正红冲分录中红冲标识应该为为正常状态0");
            }
        }

        // 正常分录中记账增向金额不能为负数
        if (writeBack.intValue() == 0)
        {
            if (addAmount.compareTo(BigDecimal.valueOf(0)) == -1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_AMOUNT_NEGATIVE.getCode()
                        + "addAmount = " + addAmount + "正常分录中记账增向金额不能为负数");
                throw new HsException(RespCode.AC_AMOUNT_NEGATIVE.getCode(), "addAmount = " + addAmount
                        + "正常分录中记账增向金额不能为负数");
            }
        }
        else
        {
            // 冲正红冲分录中增向金额不能为正数
            if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_AMOUNT_POSITIVE.getCode()
                        + "addAmount = " + addAmount + "冲正红冲分录中增向金额不能为正数");
                throw new HsException(RespCode.AC_AMOUNT_POSITIVE.getCode(), "addAmount = " + addAmount
                        + "冲正红冲分录中增向金额不能为正数");
            }
        }

        // 正常分录中记账减向金额不能为负数
        if (writeBack.intValue() == 0)
        {
            if (subAmount.compareTo(BigDecimal.valueOf(0)) == -1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_AMOUNT_NEGATIVE.getCode()
                        + "subAmount = " + subAmount + "正常分录中记账减向金额不能为负数");
                throw new HsException(RespCode.AC_AMOUNT_NEGATIVE.getCode(), "subAmount = " + subAmount
                        + "正常分录中记账减向金额不能为负数");
            }
        }
        else
        {
            // 冲正红冲分录中记账减向金额不能为正数
            if (subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_AMOUNT_POSITIVE.getCode()
                        + "subAmount = " + subAmount + "冲正红冲分录中记账减向金额不能为正数");
                throw new HsException(RespCode.AC_AMOUNT_POSITIVE.getCode(), "subAmount = " + subAmount
                        + "冲正红冲分录中记账减向金额不能为正数");
            }
        }

        // 转换日期类型String-->TimeStamp，并检查日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 交易日期,不能为空,正确日期格式 yyyy-MM-dd HH:mm:ss
        String transDate = accountEntry.getTransDate();
        if (transDate != null && !"".equals(transDate))
        {
            try
            {
                Date date = sdf.parse(transDate);
                accountEntry.setTransDateTim(Timestamp.valueOf(sdf.format(date)));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                        + "TransDate = " + transDate + " ,日期格式错误，正确格式 yyyy-MM-dd HH:mm:ss");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "TransDate = " + transDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd HH:mm:ss");
            }
        }
        else
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "TransDate = " + transDate + " ,交易日期不能为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "TransDate = " + transDate + " ,交易日期不能为空");
        }

        // 会计日期,不能为空,正确日期格式 yyyy-MM-dd HH:mm:ss
        String fiscalDate = accountEntry.getFiscalDate();
        if (fiscalDate != null && !"".equals(fiscalDate))
        {
            try
            {
                Date date = sdf.parse(fiscalDate);
                accountEntry.setFiscalDateTim(Timestamp.valueOf(sdf.format(date)));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                        + "fiscalDate = " + fiscalDate + " ,日期格式错误，正确格式 yyyy-MM-dd HH:mm:ss");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "fiscalDate = " + fiscalDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd HH:mm:ss");
            }
        }
        else
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "fiscalDate = " + fiscalDate + " ,会计日期不能为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "fiscalDate = " + fiscalDate + " ,会计日期不能为空");
        }

    }

    /**
     * 检验账户余额记录信息是否存在、当前余额账户状态和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
     * 
     * @param accountEntry
     *            分录对象
     * @param methodFlag
     *            correctFlag 是否冲正红冲标识(false 非冲正红冲，true 冲正红冲)
     * @return Map<String,Object> 账户余额相关信息集合
     * @throws HsException
     */
    public Map<String, Object> checkAccountEntryAmountInfo(Map<String, BigDecimal> accBalanceMap,
            AccountEntry accountEntry, Boolean correctFlag) throws HsException {
        BizLog.biz("HSXT_AC", "方法：checkAccountEntryAmountInfo", "accountEntry",
                "检验账户余额记录信息是否存在和该分录的变化金额是否在账户和客户关系中最大值和最小值之间");
        try
        {
        	String positiveNegative = accountEntry.getPositiveNegative();
        	
            String addAmt = accountEntry.getAddAmount();// 增向金额
            BigDecimal addAmount = new BigDecimal(addAmt);

            String subAmt = accountEntry.getSubAmount();// 减向金额
            BigDecimal subAmount = new BigDecimal(subAmt);

            String accType = accountEntry.getAccType();// 账户类型编码
            String custID = accountEntry.getCustID();// 客户全局编码
            Integer custType = accountEntry.getCustType();// 客户类型

            Boolean accountOpenFlag = true;// 账户开户标识（对应的账务余额记录存在(已开户)为true,不存在（没开户）为false）

            // 账户余额的信息验证（是否存在账户，当前账户状态的验证）
            AccountBalance accountBalance = checkBalanceInfo(custID, accType, addAmount, subAmount, correctFlag, custType);

            // 当前账户余额
            BigDecimal accBalance = BigDecimal.valueOf(0);
            String createdDate = null;
            String updatedDate = null;
            if (accountBalance == null)
            {
                accountOpenFlag = false;// 账户没开户
            }
            else
            {
                accBalance = new BigDecimal(accountBalance.getAccBalance());
                createdDate = accountBalance.getCreatedDate();// 创建时间
                updatedDate = accountBalance.getUpdatedDate();// 更新时间
            }

            // 分录当前记账/红冲金额(单笔)
            BigDecimal accEntryAmount = addAmount.subtract(subAmount);

            // 账户余额旧余额值
            BigDecimal oldAccBalance = BigDecimal.valueOf(0);

            // 账户余额新余额值
            BigDecimal newAccBalance = BigDecimal.valueOf(0);

            // 查找账户和客户类型关系信息
            AccountCustType accountCustType = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType,
                    accType);
            if (accountCustType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_NO_RELATION.getCode()
                        + "对应的账号类型:" + accType + "和客户类型:" + custType + "关系信息不存在");
                throw new HsException(RespCode.AC_NO_RELATION.getCode(), "对应的账号类型:" + accType + "和客户类型:" + custType
                        + "关系信息不存在");
            }

            String balanceMin = accountCustType.getBalanceMin();// 最小值
            String balanceMax = accountCustType.getBalanceMax();// 最大值

            // 根据客户ID和账户类型组成的KEY值，在余额记录Map中查找，没有就插入一条记录，有就累加余额值。
            String key = custID + accType;
            BigDecimal curAccbalance = accBalanceMap.get(key);
            if (curAccbalance == null)
            {
                newAccBalance = accBalance.add(accEntryAmount);
                oldAccBalance = accBalance;
            }
            else
            {
                newAccBalance = curAccbalance.add(accEntryAmount);
                oldAccBalance = curAccbalance;
            }
            accBalanceMap.put(key, newAccBalance);

            String integralStr = accountEntry.getGuaranteeIntegral();// 保底积分
            // 如果有保底積分，則需比較當前分錄金額減少后是否滿足保底要求
            if (null != integralStr && NumbericUtils.isNumeric(integralStr))
            {
                BigDecimal integral = new BigDecimal(integralStr);
                if (newAccBalance.compareTo(integral) == -1)
                {
                    
                    if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                    {
                        int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[0]);
                        SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                +",客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    }
                    else
                    {
                        int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[1]);
                        SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo",code
                                +",客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    }
                    
                }
            }

            // 非冲正红冲
            if (!correctFlag)
            {
                // 新余额在客户和账户关系表中最大值和最小值区间比较
                if (balanceMax != null && !"".equals(balanceMax)&&!"2".equals(positiveNegative))
                {
                    BigDecimal balMax = new BigDecimal(balanceMax);

                    // 增向金额大于0，（增向金额+账户余额）后的总额大于客户和账户关心设置中的最大值
                    if (newAccBalance.compareTo(balMax) == 1 && addAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                    {
                        if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                        {
                            int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[0]);
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                    +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                        else
                        {
                            int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[1]);
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                    +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                    }

                }
                if (balanceMin != null && !"".equals(balanceMin)&&!"2".equals(positiveNegative))
                {
                    BigDecimal balMin = new BigDecimal(balanceMin);

                    // 减向金额大于0，（账户余额-减向余额）后的总额小于客户和账户关心设置中的最小值
                    if (newAccBalance.compareTo(balMin) == -1 && subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                    {
                        if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                        {
                            int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[0]);
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                    +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                        else
                        {
                            int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[1]);
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                    +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("entryAmount", accEntryAmount);//变更金额
            map.put("accountOpenFlag", accountOpenFlag);// 账户标识
            map.put("accBalance", accBalance);// 当前账户余额的余额
            map.put("oldAccBalance", oldAccBalance);// 当前账户余额的旧余额
            map.put("newAccBalance", newAccBalance);// 当前账户余额的新余额
            map.put("createdDate", createdDate);// 当前账户余额表信息的创建时间
            map.put("updatedDate", updatedDate);// 当前账户余额表信息的更新时间
            return map;
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 校验关联交易流水号对应的原账务分录是否能进行冲正/红冲
     * 
     * @param pre_accountEntry
     *            通过关联交易流水号找到的原账务分录
     * @param cur_accountEntry
     *            当前冲正/红冲的分录信息对象
     * @return
     * @throws HsException
     */
    public Map<String, Object> checkWriteBackedAmount(Map<String, BigDecimal> correctedMap,
            AccountEntry pre_accountEntry, AccountEntry cur_accountEntry) throws HsException {
        BizLog.biz("HSXT_AC", "方法：checkWriteBackedAmount", "pre_accountEntry,cur_accountEntry", "校验原交易流水是否冲正/红冲过");

        Integer writeBack = pre_accountEntry.getWriteBack();
        if (writeBack.intValue() == 3)
        {
            SystemLog.debug("HSXT_AC", "方法：checkWriteBackedAmount", RespCode.AC_CORRECTED.getCode() + "该交易已红冲/冲正");
            throw new HsException(RespCode.AC_CORRECTED.getCode(), "该交易已红冲/冲正");

        }
        String writeBackedAmt = pre_accountEntry.getWriteBackedAmount();// 原纪录的红冲金额
        if (writeBackedAmt == null || "".equals(writeBackedAmt) || "null".equals(writeBackedAmt))
        {
            writeBackedAmt = "0";
        }
        BigDecimal writeBackedAmount = new BigDecimal(writeBackedAmt);// 原纪录的红冲金额

        String perAddAmt = pre_accountEntry.getAddAmount();// 原记账分录增向金额
        if (perAddAmt == null || "".equals(perAddAmt) || "null".equals(writeBackedAmt))
        {
            perAddAmt = "0";
        }
        BigDecimal perAddAmount = new BigDecimal(perAddAmt);// 原记账分录增向金额

        String perSubAmt = pre_accountEntry.getSubAmount();// 原记账分录减向金额
        if (perSubAmt == null || "".equals(perSubAmt) || "null".equals(writeBackedAmt))
        {
            perSubAmt = "0";
        }
        BigDecimal perSubAmount = new BigDecimal(perSubAmt);// 原记账分录减向金额

        String curAddAmt = cur_accountEntry.getAddAmount();// 当前记账分录增向金额
        if (curAddAmt == null || "".equals(curAddAmt) || "null".equals(curAddAmt))
        {
            curAddAmt = "0";
        }
        BigDecimal curAddAmount = new BigDecimal(curAddAmt);// 当前记账分录增向金额

        String curSubAmt = cur_accountEntry.getSubAmount();// 当前记账分录减向金额
        if (curSubAmt == null || "".equals(curSubAmt) || "null".equals(curAddAmt))
        {
            curSubAmt = "0";
        }
        BigDecimal curSubAmount = new BigDecimal(curSubAmt);// 当前记账分录减向金额

        String accType = cur_accountEntry.getAccType();// 账户类型编码
        String custID = cur_accountEntry.getCustID();// 客户全局编码
        String transNo = cur_accountEntry.getTransNo();// 流水号
        String key = transNo + accType + custID;// key值
        BigDecimal curCorrectKeyAmount = null;// 当前key值金额，保存每次红冲冲正金额，相同key值累加
        BigDecimal curCorrectEntryAmount = BigDecimal.valueOf(0).subtract(curAddAmount.add(curSubAmount));// 当前分录红冲金额
        BigDecimal curCorrectEntryAmounts = curCorrectEntryAmount;// 当前相同key值累加后的红冲金额,初始化为当前分录红冲金额

        curCorrectKeyAmount = correctedMap.get(key);
        if (curCorrectKeyAmount == null)
        {
            correctedMap.put(key, curCorrectEntryAmount);
        }
        else
        {
            curCorrectEntryAmounts = curCorrectKeyAmount.add(curCorrectEntryAmount);
            correctedMap.put(key, curCorrectEntryAmounts);
        }

        // 原分录红冲金额+当前分录（相同key值累加）红冲金额
        BigDecimal newWriteBackedAmount = writeBackedAmount.add(curCorrectEntryAmounts);

        // 当前红冲冲正分录中记账金额为减向金额，并且小于0
        if (curSubAmount.compareTo(BigDecimal.valueOf(0)) == -1)
        {
            // 原分录冲正红冲金额等于原分录减向金额
            if (writeBackedAmount.compareTo(BigDecimal.valueOf(0)) == 1
                    && writeBackedAmount.compareTo(perSubAmount) == 0)
            {
                SystemLog.debug("HSXT_AC", "方法：checkWriteBackedAmount", RespCode.AC_CORRECTED.getCode() + "该交易已经完全红冲");
                throw new HsException(RespCode.AC_CORRECTED.getCode(), "该交易已经完全红冲");
            }

            // 原分录记账金额为增向金额
            if (perAddAmount.compareTo(BigDecimal.valueOf(0)) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkWriteBackedAmount", RespCode.AC_CORRECTED.getCode()
                        + "当前红冲冲正分录中记账金额为减向金额,但是原分录记账金额为增向金额");
                throw new HsException(RespCode.AC_CORRECTED.getCode(), "当前红冲冲正分录中记账金额为减向金额,但是原分录记账金额为增向金额");
            }

            // 新的冲正红冲金额大于原分录减向金额
            if (newWriteBackedAmount.compareTo(perSubAmount) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkWriteBackedAmount", RespCode.AC_AMOUNT_BEYOND.getCode()
                        + "红冲金额超过分录金额");
                throw new HsException(RespCode.AC_AMOUNT_BEYOND.getCode(), "红冲金额超过分录金额");
            }
        }

        // 当前红冲冲正分录中记账金额为增向金额，并且小于0
        if (curAddAmount != null && curAddAmount.compareTo(BigDecimal.valueOf(0)) == -1)
        {
            // 原分录冲正红冲金额等于原分录增向金额
            if (writeBackedAmount.compareTo(BigDecimal.valueOf(0)) == 1
                    && writeBackedAmount.compareTo(perAddAmount) == 0)
            {
                SystemLog.debug("HSXT_AC", "方法：checkWriteBackedAmount", RespCode.AC_CORRECTED.getCode() + "该交易已经完全红冲");
                throw new HsException(RespCode.AC_CORRECTED.getCode(), "该交易已经完全红冲");
            }

            // 原分录记账金额为减向金额
            if (perSubAmount.compareTo(BigDecimal.valueOf(0)) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkWriteBackedAmount", RespCode.AC_CORRECTED.getCode()
                        + "当前红冲冲正分录中记账金额为增向金额,但是原分录记账金额为减向金额");
                throw new HsException(RespCode.AC_CORRECTED.getCode(), "当前红冲冲正分录中记账金额为增向金额,但是原分录记账金额为减向金额");
            }

            // 新的冲正红冲金额大于原分录增向金额
            if (newWriteBackedAmount.compareTo(perAddAmount) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：checkWriteBackedAmount", RespCode.AC_AMOUNT_BEYOND.getCode()
                        + "红冲金额超过分录金额");
                throw new HsException(RespCode.AC_AMOUNT_BEYOND.getCode(), "红冲金额超过分录金额");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("curCorrectEntryAmount", curCorrectEntryAmount);// 当前分录红冲金额
        map.put("newWriteBackedAmount", newWriteBackedAmount);// 冲正红冲新金额（原红冲金额+当前红冲金额）
        return map;
    }

    /**
     * 检验账户余额记录信息是否存在、当前余额账户状态和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
     * 
     * @param accountEntry
     *            分录对象
     * @return Map<String,Object> 账户余额相关信息集合
     * @throws HsException
     */
    public Map<String, Object> checkAccEntryAmountForSingal(AccountEntry accountEntry) throws HsException {
        BizLog.biz("HSXT_AC", "方法：checkAccEntryAmountForSingal", "accountEntry",
                "检验账户余额记录信息是否存在和该分录的变化金额是否在账户和客户关系中最大值和最小值之间");
        try
        {
        	String positiveNegative = accountEntry.getPositiveNegative();
        	
            // 原纪录的红冲金额
            String writeBackedAmt = accountEntry.getWriteBackedAmount();
            if (writeBackedAmt == null || "".equals(writeBackedAmt))
            {
                writeBackedAmt = "0";
            }
            BigDecimal writeBackedAmount = new BigDecimal(writeBackedAmt);

            String addAmt = accountEntry.getAddAmount();// 增向金额
            BigDecimal addAmount = new BigDecimal(addAmt);
            String subAmt = accountEntry.getSubAmount();// 减向金额
            BigDecimal subAmount = new BigDecimal(subAmt);

            String accType = accountEntry.getAccType();// 账户类型编码
            String custID = accountEntry.getCustID();// 客户全局编码
            Integer custType = accountEntry.getCustType();// 客户类型

            // 查找账户和客户类型关系信息
            AccountCustType accountCustType = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType,
                    accType);
            if (accountCustType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccEntryAmountForSingal", RespCode.AC_NO_RELATION.getCode()
                        + "对应的账号类型:" + accType + "和客户类型:" + custType + "关系信息不存在");
                throw new HsException(RespCode.AC_NO_RELATION.getCode(), "对应的账号类型:" + accType + "和客户类型:" + custType
                        + "关系信息不存在");
            }

            // 通过（账户类型编码：accType,客户全局编码:custID）查找账户余额记录是否存在
            AccountBalance accountBalance = (AccountBalance) accountBalanceMapper.searchAccountBalanceInfo(custID,
                    accType);
            if (accountBalance == null)
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccEntryAmountForSingal", RespCode.AC_PARAMETER_NULL.getCode()
                        + "单笔冲正红冲中对应的账户为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "单笔冲正红冲中对应的账户为空");
            }

            String createdDate = accountBalance.getCreatedDate();// 当前账户余额的创建时间
            String updatedDate = accountBalance.getUpdatedDate();// 当前账户余额的更新时间

            // 当前账户余额信息中的余额（如果为空则初始化为0，并进行String-->BigDecimal的转换）
            String curAccBalance = accountBalance.getAccBalance() == null ? "0" : accountBalance.getAccBalance();
            BigDecimal accBalance = new BigDecimal(curAccBalance);// 当前账户余额表中的余额

            int accStatus = accountBalance.getAccStatus();// 当前账户余额中的账户状态

            // 根据当前账户状态判断当前账户是否允许变更金额
            if (accStatus == AcConstant.ACC_STATUS[3] || accStatus == AcConstant.ACC_STATUS[4])
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态不允许变更金额");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态不允许变更金额");
            }

            // 单笔冲正红冲分录增向金额小于0,当前账户状态为(1:许增不许减)
            if (addAmount.compareTo(BigDecimal.valueOf(0)) == -1 && accStatus == AcConstant.ACC_STATUS[1])
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态为许增不许减，记账金额不能红冲增向金额");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许增不许减，记账金额不能红冲增向金额");
            }

            // 单笔冲正红冲分录减向金额小于0,当前账户状态为(2:许减不许增)
            if (subAmount.compareTo(BigDecimal.valueOf(0)) == -1 && accStatus == AcConstant.ACC_STATUS[2])
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态为许减不许增，记账金额不能红冲减向金额");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许减不许增，记账金额不能红冲减向金额");
            }

            // 原分录记账金额
            BigDecimal accEntryAmount = addAmount.add(subAmount);
            accEntryAmount = BigDecimal.valueOf(0).subtract(accEntryAmount);

            // 单笔冲正红冲金额(初始化为0)
            BigDecimal singalWriteBackedAmt = BigDecimal.valueOf(0);

            // 单笔冲正红冲中完全红冲冲正的金额（原账务分录记账金额-原账务分录红冲金额）
            singalWriteBackedAmt = accEntryAmount.subtract(writeBackedAmount);

            // 账户余额旧余额值
            BigDecimal oldAccBalance = accBalance;

            // 账户余额新余额值
            BigDecimal newAccBalance = BigDecimal.valueOf(0);

            if (addAmount.compareTo(BigDecimal.valueOf(0)) == -1)
            {
                newAccBalance = accBalance.subtract(singalWriteBackedAmt);
            }
            if (subAmount.compareTo(BigDecimal.valueOf(0)) == -1)
            {
                newAccBalance = accBalance.add(singalWriteBackedAmt);
            }

            // 新余额在客户和账户关系表中最大值和最小值区间比较
            String balanceMin = accountCustType.getBalanceMin();// 最小值
            String balanceMax = accountCustType.getBalanceMax();// 最大值

            if (balanceMax != null && !"".equals(balanceMax)&&!"2".equals(positiveNegative))
            {
                BigDecimal balMax = new BigDecimal(balanceMax);

                // 冲正红冲中的减向金额小于0，（账户余额+红冲金额）超过账户和关系设置中的最大值
                if (subAmount.compareTo(BigDecimal.valueOf(0)) == -1 && balMax.compareTo(newAccBalance) == -1)
                {
                    if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                    {
                        int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[0]);
                        SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    }
                    else
                    {
                        int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[1]);
                        SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    }
                }
            }
            if (balanceMin != null && !"".equals(balanceMin)&&!"2".equals(positiveNegative))
            {
                BigDecimal balMin = new BigDecimal(balanceMin);

                // 冲正红冲的增向金额小于0，（账户余额-红冲金额）超过账户和关系设置中的最小值
                if (addAmount.compareTo(BigDecimal.valueOf(0)) == -1 && balMin.compareTo(newAccBalance) == 1)
                {
                    if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                    {
                        int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[0]);
                        SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    }
                    else
                    {
                        int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[1]);
                        SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                +"客户号："+custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        throw new HsException(code, custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    }
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("accBalance", accBalance);// 当前账户余额的余额
            map.put("oldAccBalance", oldAccBalance);// 当前账户余额的旧余额
            map.put("newAccBalance", newAccBalance);// 当前账户余额的新余额
            map.put("createdDate", createdDate);// 当前账户余额表信息的创建时间
            map.put("updatedDate", updatedDate);// 当前账户余额表信息的更新时间
            return map;
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 校验单笔冲正红冲交易
     * 
     * @param accountEntry
     *            通过关联交易流水号找到的原交易分录
     * @return BigDecimal 完全冲正红冲金额
     * @throws HsException
     */
    public BigDecimal checkSingalWriteBackedAmount(AccountEntry accountEntry) throws HsException {
        BizLog.biz("HSXT_AC", "方法：checkSingalWriteBackedAmount", "accountEntry", "校验单笔冲正红冲交易");

        // 原纪录的红冲金额
        String writeBackedAmt = accountEntry.getWriteBackedAmount();
        if (writeBackedAmt == null || "".equals(writeBackedAmt))
        {
            writeBackedAmt = "0";
        }
        BigDecimal writeBackedAmount = new BigDecimal(writeBackedAmt);

        // 原记账分录增向金额
        String perAddAmt = accountEntry.getAddAmount();
        if (perAddAmt == null || "".equals(perAddAmt))
        {
            perAddAmt = "0";
        }
        BigDecimal perAddAmount = new BigDecimal(perAddAmt);

        // 原记账分录减向金额
        String perSubAmt = accountEntry.getSubAmount();
        if (perSubAmt == null || "".equals(perSubAmt))
        {
            perSubAmt = "0";
        }
        BigDecimal perSubAmount = new BigDecimal(perSubAmt);

        // 原分录记账金额
        BigDecimal perCorrectEntryAmount = perAddAmount.add(perSubAmount);

        // 把记账金额变为正数
        perCorrectEntryAmount = BigDecimal.valueOf(0).subtract(perCorrectEntryAmount);

        // 单笔冲正红冲金额(初始化为0)
        BigDecimal singalWriteBackedAmt = BigDecimal.valueOf(0);

        // 原分录冲正红冲金额等于原分录记账金额
        if (writeBackedAmount.compareTo(perCorrectEntryAmount) == 0)
        {
            SystemLog
                    .debug("HSXT_AC", "方法：checkSingalWriteBackedAmount", RespCode.AC_CORRECTED.getCode() + "该交易已经完全红冲");
            throw new HsException(RespCode.AC_CORRECTED.getCode(), "该交易已经完全红冲");
        }

        // 原分录冲正红冲金额大于原分录记账金额
        if (writeBackedAmount.compareTo(perCorrectEntryAmount) == 1)
        {
            SystemLog.debug("HSXT_AC", "方法：checkSingalWriteBackedAmount", RespCode.AC_CORRECTED.getCode()
                    + "原分录冲正红冲金额大于原分录记账金额");
            throw new HsException(RespCode.AC_CORRECTED.getCode(), "原分录冲正红冲金额大于原分录记账金额");
        }

        // 单笔冲正红冲中完全红冲冲正的金额（原账务分录记账金额-原账务分录红冲金额）
        singalWriteBackedAmt = perCorrectEntryAmount.subtract(writeBackedAmount);
        return singalWriteBackedAmt;
    }

    /**
     * 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
     * 
     * @param custType
     *            客户类型
     * @return 客户类型分类。如果为空，则说明该客户类型不存在
     * @throws HsException
     *             异常处理类
     */
    public Integer checkCustTypeCategory(Integer custType) throws HsException {

        String[] perTypes = PropertyConfigurer.getProperty("ac.custType.perType").split("\\|");// 消费者客户类型
        String[] entTypes = PropertyConfigurer.getProperty("ac.custType.entType").split("\\|");// 企业客户类型
        String[] pfTypes = PropertyConfigurer.getProperty("ac.custType.pfType").split("\\|");// 平台客户类型

        Integer custTypeCategory = null;// 客户类型分类

        // 消费者客户类型
        for (String perType : perTypes)
        {
            if (perType.equals(String.valueOf(custType)))
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[0];
            }
        }

        // 企业客户类型
        for (String entType : entTypes)
        {
            if (entType.equals(String.valueOf(custType)))
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[1];
            }
        }

        // 平台客户类型
        for (String pfType : pfTypes)
        {
            if (pfType.equals(String.valueOf(custType)))
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[2];
            }
        }

        // 为空，则说明该客户类型不存在
        if (custTypeCategory == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkCustTypeCategory", RespCode.AC_PARAMETER_NULL.getCode() + "custType:"
                    + custType + ",不存在该客户类型");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:" + custType + ",不存在该客户类型");
        }
        return custTypeCategory;
    }

    /**
     * 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
     * 
     * @param custType
     *            客户类型
     * @return 客户类型分类。如果为空，则说明该客户类型不存在
     * @throws HsException
     *             异常处理类
     */
    public Integer checkCustTypesCategory(AccountEntry accountEntry, Map<Integer, Integer> perMap,
            Map<Integer, Integer> entMap, Map<Integer, Integer> pfMap) throws HsException {

        Integer custTypeCategory = null;// 客户类型分类
        Integer custType = accountEntry.getCustType();// 客户类型

        // 消费者客户类型
        if (perMap.get(custType) != null)
        {
            custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[0];
        }

        // 企业客户类型
        if (entMap.get(custType) != null)
        {
            custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[1];
        }

        // 平台客户类型
        if (pfMap.get(custType) != null)
        {
            String hsResNo = accountEntry.getHsResNo();// 互生号
            String custId = accountEntry.getCustID();// 客户全局编码
            if (hsResNo == null || "".equals(hsResNo))
            {
                SystemLog.debug("HSXT_AC", "方法：checkCustTypeCategory", RespCode.AC_PARAMETER_NULL.getCode()
                        + "hsResNo:" + hsResNo + ",平台客户:" + custId + "互生号不能为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "hsResNo:" + hsResNo + ",平台客户:" + custId
                        + "互生号不能为空");
            }
            custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[2];
        }

        // 为空，则说明该客户类型不存在
        if (custTypeCategory == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkCustTypeCategory", RespCode.AC_PARAMETER_NULL.getCode() + "custType:"
                    + custType + ",不存在该客户类型");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:" + custType + ",不存在该客户类型");
        }
        return custTypeCategory;
    }

    /**
     * 扣款账户余额的信息验证（是否存在账户，当前账户状态的验证）
     * 
     * @param custId
     *            客户全局编码
     * @param accType
     *            账户类型编码
     * @param correctFlag
     *            (true:冲正红冲标识,false:非冲正红冲)
     * @return AccountBalance 账户余额对象
     * @throws SQLException
     *             异常处理
     * @throws HsException
     *             异常处理
     */
    public AccountBalance checkBalanceInfo(String custId, String accType, BigDecimal addAmount, BigDecimal subAmount,
            boolean correctFlag, Integer custType) throws SQLException, HsException {

        // 通过（账户类型编码：accType,客户全局编码:custID）查找账户余额记录是否存在
        AccountBalance accountBalance = (AccountBalance) accountBalanceMapper.searchAccountBalance(custId, accType, custType);
        if (accountBalance != null)
        {
            int accStatus = accountBalance.getAccStatus();// 当前账户余额中的账户状态

            // 根据当前账户状态判断当前账户是否允许变更金额
            if (accStatus == AcConstant.ACC_STATUS[3] || accStatus == AcConstant.ACC_STATUS[4])
            {
                SystemLog.debug("HSXT_AC", "方法：checkBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态不允许变更金额");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态不允许变更金额");
            }
            // 非冲正红冲
            if (!correctFlag)
            {
                // 分录增向金额大于0,当前账户状态为(2:许减不许增)
                if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1 && accStatus == AcConstant.ACC_STATUS[2])
                {
                    SystemLog.debug("HSXT_AC", "方法：checkBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                            + "账户状态为许减不许增，记账金额不能为增向金额");
                    throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许减不许增，记账金额不能为增向金额");
                }

                // 分录减向金额大于0,当前账户状态为(1:许增不许减)
                if (subAmount.compareTo(BigDecimal.valueOf(0)) == 1 && accStatus == AcConstant.ACC_STATUS[1])
                {
                    SystemLog.debug("HSXT_AC", "方法：checkBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                            + "账户状态为许增不许减，记账金额不能为减向金额");
                    throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许增不许减，记账金额不能为减向金额");
                }
            }
            else
            {
                // 冲正红冲分录增向金额小于0,当前账户状态为(1:许增不许减)
                if (addAmount.compareTo(BigDecimal.valueOf(0)) == -1 && accStatus == AcConstant.ACC_STATUS[1])
                {
                    SystemLog.debug("HSXT_AC", "方法：checkBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                            + "账户状态为许增不许减，记账金额不能红冲增向金额");
                    throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许增不许减，记账金额不能红冲增向金额");
                }

                // 冲正红冲分录减向金额小于0,当前账户状态为(2:许减不许增)
                if (subAmount.compareTo(BigDecimal.valueOf(0)) == -1 && accStatus == AcConstant.ACC_STATUS[2])
                {
                    SystemLog.debug("HSXT_AC", "方法：checkBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                            + "账户状态为许减不许增，记账金额不能红冲减向金额");
                    throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许减不许增，记账金额不能红冲减向金额");
                }
            }
        }
        return accountBalance;// 返回当前账户余额
    }

    /**
     * 扣款账户余额的信息验证（是否存在账户，当前账户状态的验证）
     * 
     * @param custId
     *            客户全局编码
     * @param accType
     *            账户类型编码
     * @return AccountBalance 账户余额对象
     * @throws SQLException
     *             异常处理
     * @throws HsException
     *             异常处理
     */
    public AccountBalance checkDeductBalanceInfo(String custId, String accType, Integer custType) throws SQLException, HsException {
        // 账户余额信息查询
        AccountBalance accBalance = accountBalanceMapper.searchAccountBalance(custId, accType, custType);
        if (accBalance != null)
        {
            Integer accStatus = accBalance.getAccStatus();// 账户当前状态

            // 根据当前账户状态判断当前账户是否允许变更金额
            if (accStatus.intValue() == AcConstant.ACC_STATUS[3].intValue() || accStatus == AcConstant.ACC_STATUS[4].intValue())
            {
                SystemLog.debug("HSXT_AC", "方法：checkDeductBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态不允许变更金额");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态不允许变更金额");
            }

            // 扣款分录减向金额大于0,当前账户状态为(1:许增不许减)
            if (accStatus.intValue() == AcConstant.ACC_STATUS[1].intValue())
            {
                SystemLog.debug("HSXT_AC", "方法：checkDeductBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态为许增不许减，记账金额不能为减向金额");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许增不许减，记账金额不能为减向金额");
            }
        }

        return accBalance;// 返回当前账户余额
    }

    /**
     * 变化后的账户余额和账户关系表中最小值最大值比较
     * 
     * @param custType
     *            客户类型编码
     * @param accType
     *            账户类型编码
     * @param amount
     *            变化后的账户余额
     * @throws SQLException
     * @throws HsException
     */
    public void checkCustAccTypeInfo(String custId, Integer custType, String accType, BigDecimal amount, String positiveNegative) throws SQLException,
            HsException {

        // 查找账户和客户类型关系信息
        AccountCustType accountCustType = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType, accType);
        if (accountCustType == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkCustAccTypeInfo", RespCode.AC_NO_RELATION.getCode() + custId+"对应的账号类型:"
                    + accType + "和客户类型:" + custType + "关系信息不存在");
            throw new HsException(RespCode.AC_NO_RELATION.getCode(), custId+"对应的账号类型:"
                    + accType + "和客户类型:" + custType +  "关系信息不存在");
        }
        String balanceMin = accountCustType.getBalanceMin();// 最小值
        String balanceMax = accountCustType.getBalanceMax();// 最大值

        
        
        // 变化后的账户余额和账户关系表中最小值比较
        if (balanceMin != null && !"".equals(balanceMin)&&!"2".equals(positiveNegative))
        {
            BigDecimal balMin = new BigDecimal(balanceMin);

            // 变化的账户余额小于客户和账户关心设置中的最小值
            if (amount.compareTo(balMin) == -1)
            {
                if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                {
                    int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[0]);
                    SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                            +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                }
                else
                {
                    int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[1]);
                    SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                            +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                }
            }
        }

        // 变化的账户余额和账户关系表中最小值比较
        if (balanceMax != null && !"".equals(balanceMax)&&!"2".equals(positiveNegative))
        {
            BigDecimal balMax = new BigDecimal(balanceMax);

            // 变化的账户余额大于客户和账户关心设置中的最大值
            if (amount.compareTo(balMax) == 1)
            {
                if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                {
                    int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[0]);
                    SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                            +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                }
                else
                {
                    int code = setAndGetDataMethod.getResCodeByAccType(accType,AcConstant.CUST_TYPE_CATEGORY[1]);
                    SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                            +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                    throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                }
            }
        }
    }

    /**
     * 检查服务记账对象中的条件信息
     * 
     * @param accountService
     *            服务记账对象
     * @throws HsException
     */
    public void checkAccountServiceInfo(AccountService accountService) throws HsException {

        String custID = accountService.getCustID();// 客户全局编码
        if (custID == null || "".equals(custID))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custID : 客户全局编码为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID : 客户全局编码为空");
        }
        String hsResNo = accountService.getHsResNo();// 互生号
        if (hsResNo == null || "".equals(hsResNo))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "hsResNo : 互生号为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "hsResNo : 互生号为空");
        }
        String accTypes = accountService.getAccTypes();// 账户类型编码集
        if (accTypes == null || "".equals(accTypes))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                    + "accTypes = " + accTypes + "账户类型编码集为空");
            throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "accTypes = " + accTypes + "账户类型编码集为空");
        }
        String accType[] = accTypes.split("\\|");// 以分隔符"|"拆分扣款账户字符串集
        if (accType.length != 2)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accTypes:账户类型编码集应该为两个账户");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accTypes:账户类型编码集应该为两个账户");
        }
        String transType = accountService.getTransType();// 交易类型
        if (transType == null || "".equals(transType))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "transType:交易类型为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "transType:交易类型为空");
        }
        String amount = accountService.getAmount();// 服务金额
        if (amount == null || "".equals(amount))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "amount:服务金额为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "amount:服务金额为空");
        }
        Integer custType = accountService.getCustType();// 客户类型
        if (custType == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custType:客户类型为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:客户类型为空");
        }
        String transNo = accountService.getTransNo();// 交易流水号
        if (transNo == null || "".equals(transNo))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountServiceInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "transNo:交易流水号为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "transNo:交易流水号为空");
        }

    }

    /**
     * 校验退单对应的原单信息。并返回可退单的金额
     * 
     * @param accountEntry
     * @param accountBalance
     * @return
     * @throws SQLException
     */
    public BigDecimal validateChargeBackInfo(AccountEntry accountEntry, AccountBalance accountBalance)
            throws SQLException {

        Integer writeBack = 0;// 正常分录
        String custId = accountEntry.getCustID();// 客户全局编码
        String relTransNo = accountEntry.getRelTransNo();// 关联交易流水号
        String addAmt = accountEntry.getAddAmount();// 增向金额
        String relSysEntryNo = accountEntry.getRelSysEntryNo();// 关联系统分录号
        BigDecimal addAmount = BigDecimal.valueOf(0);
        if (addAmt != null && !"".equals(addAmt))
        {
            addAmount = new BigDecimal(addAmt);
        }
        String subAmt = accountEntry.getSubAmount();// 减向金额
        BigDecimal subAmount = BigDecimal.valueOf(0);
        if (subAmt != null && !"".equals(subAmt))
        {
            subAmount = new BigDecimal(subAmt);
        }

        // 退单账务分录的退款账户集，账户以"|"分割，退款账户顺序由左往右
        String accTypes = accountEntry.getAccTypes();
        String accType[] = accTypes.split("\\|");// 以分隔符"|"拆分退款账户字符串集

        // 退款对应的原记账分录
        AccountEntry preAccountEntry = accountEntryMapper.seachAccountEntryByTrsNo(relTransNo, writeBack, custId,
                accType[0]);
        if (preAccountEntry == null)
        {
            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode() + "relTransNo:"
                    + relTransNo + ",custId:" + custId + ",accType:" + accType[0] + ",找不到退款对应的原扣款记账记录");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:" + relTransNo + ",custId:"
                    + custId + ",accType:" + accType[0] + ",找不到退款对应的原扣款记账记录");
        }

        String preAddAmt = preAccountEntry.getAddAmount();// 退款对应的原记账分录的增向金额
        BigDecimal preAddAmount = new BigDecimal(preAddAmt);
        String preSubAmt = preAccountEntry.getSubAmount();// 退款对应的原记账分录的减向金额
        BigDecimal preSubAmount = new BigDecimal(preSubAmt);

        // 当前账户余额
        BigDecimal accBalance = accountBalance.getAccBalance() == null ? BigDecimal.valueOf(0) : new BigDecimal(
                accountBalance.getAccBalance());

        // 查看对应的原记账分录是否做过退单
        List<AccountEntry> acctEntry_List = accountEntryMapper.seachChargeBackAccEntryList(relTransNo, writeBack,
                custId, accType[0], relSysEntryNo);

        BigDecimal newAccBalance = BigDecimal.valueOf(0);// 退款之后的账户新余额
        BigDecimal preChargeBackbAmt = BigDecimal.valueOf(0);// 对应的原记账分录做过退单的总金额

        // 当前退款金额为增向金额
        if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1)
        {
            // 对应的原记账分录做过退单
            if (!acctEntry_List.isEmpty())
            {
                for (AccountEntry accEntry : acctEntry_List)
                {
                    String preCbSubAmt = accEntry.getSubAmount();// 做过退单的金额
                    BigDecimal preCbSubAmount = new BigDecimal(preCbSubAmt);
                    preChargeBackbAmt = preChargeBackbAmt.add(preCbSubAmount);
                }
            }

            // 当前原记账分录可以退单的剩余金额
            preSubAmount = preSubAmount.subtract(preChargeBackbAmt);

            // 对应的原扣款记账记录退款的金额小于0或者等于0
            if (preSubAmount.compareTo(BigDecimal.valueOf(0)) == 0
                    || preSubAmount.compareTo(BigDecimal.valueOf(0)) == -1)
            {
                SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode() + "relTransNo:"
                        + relTransNo + ",custId:" + custId + ",accType:" + accType[0] + ",对应的原扣款记账记录退款的金额已经退完");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:" + relTransNo + ",custId:"
                        + custId + ",accType:" + accType[0] + ",对应的原扣款记账记录退款的金额已经退完");
            }
            // 当前退款的金额大于原扣款的金额
            if (addAmount.compareTo(preSubAmount) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode() + "relTransNo:"
                        + relTransNo + ",custId:" + custId + ",accType:" + accType[0] + ",当前退款的金额大于原扣款的金额");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:" + relTransNo + ",custId:"
                        + custId + ",accType:" + accType[0] + ",当前退款的金额大于原扣款的金额");
            }
            newAccBalance = accBalance.add(addAmount);// 把退款金额加到对应的账户余额
            accountBalance.setAccBalance(String.valueOf(addAmount));// 当前账户余额更新值为增向金额值
        }

        // 当前退款金额为减向金额
        if (subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
        {
            // 对应的原记账分录做过退单
            if (!acctEntry_List.isEmpty())
            {
                for (AccountEntry accEntry : acctEntry_List)
                {
                    String preCbAddAmt = accEntry.getAddAmount();// 做过退单的金额
                    BigDecimal preCbAddAmount = new BigDecimal(preCbAddAmt);
                    preChargeBackbAmt = preChargeBackbAmt.add(preCbAddAmount);
                }
            }

            // 当前原记账分录可以退单的剩余金额
            preAddAmount = preAddAmount.subtract(preChargeBackbAmt);

            // 对应的原扣款记账记录退款的金额小于0或者等于0
            if (preAddAmount.compareTo(BigDecimal.valueOf(0)) == 0
                    || preAddAmount.compareTo(BigDecimal.valueOf(0)) == -1)
            {
                SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode() + "relTransNo:"
                        + relTransNo + ",custId:" + custId + ",accType:" + accType[0] + ",对应的原扣款记账记录退款的金额已经退完");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:" + relTransNo + ",custId:"
                        + custId + ",accType:" + accType[0] + ",对应的原扣款记账记录退款的金额已经退完");
            }

            // 当前退款的金额大于原新增的金额
            if (subAmount.compareTo(preAddAmount) == 1)
            {
                SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode() + "relTransNo:"
                        + relTransNo + ",custId:" + custId + ",accType:" + accType[0] + ",当前退款的金额大于原增加的金额");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:" + relTransNo + ",custId:"
                        + custId + ",accType:" + accType[0] + ",当前退款的金额大于原增加的金额");
            }
            newAccBalance = accBalance.subtract(subAmount);// 对应的账户余额减去退款金额
            accountBalance.setAccBalance(String.valueOf(BigDecimal.valueOf(0).subtract(subAmount)));// 当前账户余额更新值为减向金额值（负数）
        }
        return newAccBalance;
    }

    /**
     * 校验退单参数
     * 
     * @param relTransNo
     *            关联交易流水号
     * @param relSysEntryNo
     *            关联系统分路号
     * @param accTypes
     *            账户集
     */
    public void checkChargeBackInfo(String relTransNo, String relSysEntryNo, String accTypes) {
        if (relTransNo == null || "".equals(relTransNo))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "relTransNo：退款关联交易流水号不能为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo：退款关联交易流水号不能为空");
        }

        if (relSysEntryNo == null || "".equals(relSysEntryNo))
        {
            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "relSysEntryNo：关联系统分录序列号不能为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relSysEntryNo：关联系统分录序列号不能为空");
        }
        if (accTypes == null || "".equals(accTypes))
        {
            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode() + "accTypes:"
                    + accTypes + ",退单账务分录的退款账户集不能为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accTypes:" + accTypes + ",扣款账务分录的扣款账户集不能为空");
        }
    }

    
    
    public BigDecimal getAvailableChargeBackAmount() {
        return null;
    }
}
