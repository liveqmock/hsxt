/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.common;

import com.gy.hsxt.ao.enumtype.ProxyTransMode;
import com.gy.hsxt.ao.enumtype.TransStatus;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.TransType;

/**
 * 交易类型代码工具类
 * 
 * @Package: com.gy.hsxt.ao.common
 * @ClassName: TransCodeUtil
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-2 上午10:41:45
 * @version V3.0.0
 */
public class TransCodeUtil {

    /**
     * 转帐银行退票交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String getBankBackTransCode(int custType) {
        // 持卡人
        if (custType == CustType.PERSON.getCode() // 持卡人
                || custType == CustType.NOT_HS_PERSON.getCode()// 非持卡人
        )
        {
            // 个人转账银行退票退回
            return TransType.P_TRANS_BANK_REFUND.getCode();
        }
        // 企业
        else
        {
            // 企业转账银行退票退回
            return TransType.C_TRANS_BANK_REFUND.getCode();
        }
    }

    /**
     * 转帐失败交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String getTransFailCode(int custType) {
        // 持卡人
        if (custType == CustType.PERSON.getCode() // 持卡人
                || custType == CustType.NOT_HS_PERSON.getCode()// 非持卡人
        )
        {
            // 个人转账失败退回
            return TransType.P_TRANS_REFUND.getCode();
        }
        // 企业
        else
        {
            // 企业转账失败退回
            return TransType.C_TRANS_REFUND.getCode();
        }
    }

    /**
     * 获取银行转帐交易类型代码
     * 
     * @param transStatus
     *            转账结果
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String getBankTransCode(int transStatus, int custType) {
        // 银行转账预转出
        if (transStatus == TransStatus.APPLYING.getCode()// 申请中
                || transStatus == TransStatus.PAYING.getCode()// 付款中
        )
        {
            return getTransPreCode(custType);
        }
        // 转帐成功
        if (transStatus == TransStatus.TRANS_SUCCESS.getCode())
        {
            return getTransSuccessCode(custType);
        }
        // 撤单交易类型
        if (transStatus == TransStatus.REVOKED.getCode())
        {
            return getTransRevokeCode(custType);
        }
        // 转帐失败
        if (transStatus == TransStatus.TRANS_FAILED.getCode()// 转帐失败
                || transStatus == TransStatus.REVERSED.getCode()// 交易错误
        )
        {
            return getTransFailCode(custType);
        }
        // 转帐银行退票
        if (transStatus == TransStatus.BANK_BACK.getCode())
        {
            return getBankBackTransCode(custType);
        }
        return null;
    }

    private static String getTransRevokeCode(int custType) {
        // 个人
        if (custType == CustType.PERSON.getCode() // 持卡人
                || custType == CustType.NOT_HS_PERSON.getCode()// 非持卡人
        )
        {
            return TransType.P_PRETRANS_CASH_CANCEL.getCode();
        }
        else
        // 企业
        {
            return TransType.C_PRETRANS_CASH_CANCEL.getCode();
        }
    }

    /**
     * 转帐成功交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String getTransSuccessCode(int custType) {
        // 持卡人
        if (custType == CustType.PERSON.getCode() // 持卡人
                || custType == CustType.NOT_HS_PERSON.getCode()// 非持卡人
        )
        {
            // 个人转账转出
            return TransType.P_TRANS_CASH.getCode();
        }
        // 企业
        else
        {
            // 企业转账转出
            return TransType.C_TRANS_CASH.getCode();
        }
    }

    /**
     * 转帐预转出交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String getTransPreCode(int custType) {
        // 持卡人
        if (custType == CustType.PERSON.getCode() // 持卡人
                || custType == CustType.NOT_HS_PERSON.getCode()// 非持卡人
        )
        {
            // 个人转账预转出
            return TransType.P_PRETRANS_CASH.getCode();
        }
        // 企业
        else
        {
            // 企业转账预转出
            return TransType.C_PRETRANS_CASH.getCode();
        }
    }

    /**
     * 积分投资分红交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String checkPointDividendTransCode(Integer custType) {
        // 持卡人
        if (custType.intValue() == CustType.PERSON.getCode())
        {
            // 个人投资分红分配
            return TransType.P_INVEST_BONUS.getCode();
        }
        // 企业
        else
        {
            // 企业投资分红分配
            return TransType.C_INVEST_BONUS.getCode();
        }
    }

    /**
     * 积分投资交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String checkPointInvestTransCode(Integer custType) {
        // 持卡人
        if (custType.intValue() == CustType.PERSON.getCode())
        {
            // 个人积分投资
            return TransType.P_POINT_INVEST.getCode();
        }
        // 企业
        else
        {
            // 企业积分投资
            return TransType.C_POINT_INVEST.getCode();
        }
    }

    /**
     * 互生币转货币交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String checkHsbToCurrencyTransCode(Integer custType) {
        if (custType.intValue() == CustType.PERSON.getCode() // 持卡人
                || custType.intValue() == CustType.NOT_HS_PERSON.getCode()// 非持卡人
        )
        {
            // 个人互生币转货币
            return TransType.P_HSB_TO_CASH.getCode();
        }
        // 企业
        else
        {
            // 企业互生币转货币
            return TransType.C_HSB_TO_CASH.getCode();
        }
    }

    /**
     * 冲正兑换互生币交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String getReverseBuyHsbTransCode(Integer custType, Integer payModel) {
        // 持卡人
        if (custType.intValue() == CustType.PERSON.getCode())
        {
            // 货币支付
            if (PayChannel.MONEY_PAY.getCode().intValue() == payModel)
                // 个人货币兑换互生币冲正
                return TransType.P_INAL_HSB_RECHARGE_REVERSE.getCode();
        }
        // 企业
        else
        {
            // 货币支付
            if (PayChannel.MONEY_PAY.getCode().intValue() == payModel)
                // 企业货币兑换互生币冲正
                return TransType.C_INAL_HSB_RECHARGE_REVERSE.getCode();
        }
        return "";
    }

    /**
     * 冲正代兑互生币交易码
     * 
     * @param transMode
     *            代兑方式
     * @return 冲正交易码
     */
    public static String getReverseProxyBuyHsbCode(Integer transMode) {
        // 本地企业异地消费者
        if (ProxyTransMode.LOCAL_ENT_TO_DIFF_CUST.getCode().intValue() == transMode)
        {
            return TransType.C_HSB_P_ACROSS_RECHARGE_FOR_C_REVERSE.getCode();
        }
        else // 异地企业本地消费者
        if (ProxyTransMode.DIFF_ENT_TO_LOCAL_CUST.getCode().intValue() == transMode)
        {
            return TransType.C_HSB_P_ACROSS_RECHARGE_FOR_P_REVERSE.getCode();
        }
        else
            // 企业代兑互生币冲正
            return TransType.C_HSB_P_HSB_RECHARGE_REVERSE.getCode();
    }

    /**
     * 根据交易码转换出冲正交易码
     * 
     * @param transCode
     *            交易码
     * @return 冲正交易码
     */
    public static String getReverseCode(String transCode) {
        StringBuilder sb = new StringBuilder(transCode);
        return sb.replace(transCode.length() - 2, transCode.length() - 1, "1").toString();
    }

    /**
     * 积分转互生币交易码
     * 
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String checkPvToHsbTransCode(Integer custType) {
        // 持卡人
        if (custType.intValue() == CustType.PERSON.getCode())
        {
            // 个人积分转互生币
            return TransType.P_POINT_TO_HSB.getCode();
        }
        // 企业
        else
        {
            // 企业积分转互生币
            return TransType.C_POINT_TO_HSB.getCode();
        }
    }

    /**
     * 兑换互生币付方式
     * 
     * @param payChannel
     *            支付方式
     * @param custType
     *            客户类型
     * @return 交易代码
     */
    public static String checkBuyHsbTransCode(Integer payChannel, Integer custType) {
        // 持卡人
        if (custType.intValue() == CustType.PERSON.getCode() || custType.intValue() == CustType.NOT_HS_PERSON.getCode())
        {
            // 网银支付
            if (payChannel.intValue() == PayChannel.E_BANK_PAY.getCode() // 网银支付
                    || payChannel.intValue() == PayChannel.QUICK_PAY.getCode()// 快捷支付
                    || payChannel.intValue() == PayChannel.MOBILE_PAY.getCode()// 手机移动支付
                    || payChannel.intValue() == PayChannel.CARD_PAY.getCode()// 银行卡支付
            )
            {
                return TransType.P_INET_HSB_RECHARGE.getCode();
            }
            // 货币支付
            if (payChannel.intValue() == PayChannel.MONEY_PAY.getCode())
            {
                return TransType.P_INAL_HSB_RECHARGE.getCode();
            }
            // 转帐汇款
            if (payChannel.intValue() == PayChannel.TRANSFER_REMITTANCE.getCode())
            {
                return TransType.P_TEMP_HSB_RECHARGE.getCode();
            }
        }
        // 企业
        else
        {
            // 网银支付
            if (payChannel.intValue() == PayChannel.E_BANK_PAY.getCode() // 网银支付
                    || payChannel.intValue() == PayChannel.QUICK_PAY.getCode()// 快捷支付
                    || payChannel.intValue() == PayChannel.MOBILE_PAY.getCode()// 手机移动支付
                    || payChannel.intValue() == PayChannel.CARD_PAY.getCode()// 银行卡支付
            )
            {
                return TransType.C_INET_HSB_RECHARGE.getCode();
            }
            // 货币支付
            if (payChannel.intValue() == PayChannel.MONEY_PAY.getCode())
            {
                return TransType.C_INAL_HSB_RECHARGE.getCode();
            }
            // 转帐汇款
            if (payChannel.intValue() == PayChannel.TRANSFER_REMITTANCE.getCode())
            {
                return TransType.C_TEMP_HSB_RECHARGE.getCode();
            }
        }
        return null;
    }
}
