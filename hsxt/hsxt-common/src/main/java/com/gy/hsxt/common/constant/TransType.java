/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * 定义全局交易类型
 * 
 * 后缀REVERSE为冲正，交易代码倒数第2位等于1
 * 
 * 后缀CANCEL为撤单，交易代码倒数第3位等于1
 * 
 * @version V1.0
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: TransType
 * @Description: TODO
 * @author: yangjianguo
 * @date: 2015-10-15 下午8:20:29
 */
public enum TransType {
    /**
     * 平台扣款
     */
    DEDUCT_HSB_FROM_CUST("S72000"),
    /**
     * 企业互生币缴年费
     **/
    C_HSB_PAY_ANNUAL_FEE("S52000"),
    /**
     * 企业互生币缴年费冲正
     **/
    C_HSB_PAY_ANNUAL_FEE_REVERSE("S52010"),
    /**
     * 企业网银缴年费
     **/
    C_INET_PAY_ANNUAL_FEE("S54000"),
    /**
     * 企业网银缴年费冲正
     **/
    C_INET_PAY_ANNUAL_FEE_REVERSE("S54010"),
    /**
     * 企业缴年费临账支付
     **/
    C_TEMP_PAY_ANNUAL_FEE("S55000"),
    /**
     * 企业缴年费临账支付冲正
     **/
    C_TEMP_PAY_ANNUAL_FEE_REVERSE("S55010"),

    /**
     * 企业互生币购买工具
     **/
    C_HSB_SALES_PAY("S12000"),
    /**
     * 企业互生币购买工具冲正
     **/
    C_HSB_SALES_PAY_REVERSE("S12010"),
    /**
     * 企业网银申购工具
     **/
    C_INET_SALES_PAY("S14000"),
    /**
     * 企业网银申购工具冲正
     **/
    C_INET_SALES_PAY_REVERSE("S14010"),
    /**
     * 企业临账购买工具
     **/
    C_TEMP_SALES_PAY("S15000"),
    /**
     * 企业临账购买工具冲正
     **/
    C_TEMP_SALES_PAY_REVERSE("S15010"),
    /**
     * 企业互生币购买工具撤单
     **/
    C_HSB_SALES_PAY_CANCEL("S12100"),
    /**
     * 企业互生币购买工具撤单冲正
     **/
    C_HSB_SALES_PAY_CANCEL_REVERSE("S12110"),
    /**
     * 企业网银申购工具撤单
     **/
    C_INET_SALES_PAY_CANCEL("S14100"),
    /**
     * 企业网银申购工具撤单冲正
     **/
    C_INET_SALES_PAY_CANCEL_REVERSE("S14110"),
    /**
     * 企业临账购买工具撤单
     **/
    C_TEMP_SALES_PAY_CANCEL("S15100"),
    /**
     * 企业临账购买工具撤单冲正
     **/
    C_TEMP_SALES_PAY_CANCEL_REVERSE("S15110"),

    /**
     * 企业货币兑换互生币
     **/
    C_INAL_HSB_RECHARGE("K23000"),
    /**
     * 企业货币兑换互生币冲正
     **/
    C_INAL_HSB_RECHARGE_REVERSE("K23010"), // 不要手工红冲，都改成自动冲正
    /**
     * 企业网银兑换互生币
     **/
    C_INET_HSB_RECHARGE("K24000"),
    /**
     * 企业网银兑换互生币冲正
     **/
    C_INET_HSB_RECHARGE_REVERSE("K24010"),
    /**
     * 企业兑换互生币临账支付
     **/
    C_TEMP_HSB_RECHARGE("K25000"),
    /**
     * 企业兑换互生币临账支付冲正
     **/
    C_TEMP_HSB_RECHARGE_REVERSE("K25010"),
    /**
     * 个人货币兑换互生币
     **/
    P_INAL_HSB_RECHARGE("K13000"),
    /**
     * 个人货币兑换互生币冲正
     **/
    P_INAL_HSB_RECHARGE_REVERSE("K13010"),
    /**
     * 个人网银兑换互生币
     **/
    P_INET_HSB_RECHARGE("K14000"),
    /**
     * 个人网银兑换互生币冲正
     **/
    P_INET_HSB_RECHARGE_REVERSE("K14010"),
    /**
     * 个人兑换互生币临账支付
     **/
    P_TEMP_HSB_RECHARGE("K15000"),
    /**
     * 个人兑换互生币临账支付冲正
     **/
    P_TEMP_HSB_RECHARGE_REVERSE("K15010"),
    /** 个人互生币补互生卡撤单 **/
    P_HSB_SALES_PAY_CANCEL("S22100"),
    /** 个人网银补互生卡撤单 **/
    P_INET_SALES_PAY_CANCEL("S24100"),

    /**
     * 个人互生币补互生卡
     **/
    P_HSB_SALES_PAY("S22000"),
    /**
     * 个人互生币补互生卡冲正
     **/
    P_HSB_SALES_PAY_REVERSE("S22010"),
    /**
     * 个人网银补互生卡
     **/
    P_INET_SALES_PAY("S24000"),
    /**
     * 个人网银补互生卡冲正
     **/
    P_INET_SALES_PAY_REVERSE("S24010"),
    /**
     * 企业重做卡互生币支付
     **/
    C_HSB_REMAKE_CARD("S32000"),
    /**
     * 企业重做卡互生币支付冲正
     **/
    C_HSB_REMAKE_CARD_REVERSE("S32010"),
    /**
     * 企业重做卡网银支付
     **/
    C_INET_REMAKE_CARD("S34000"),
    /**
     * 企业重做卡网银支付冲正
     **/
    C_INET_REMAKE_CARD_REVERSE("S34010"),
    /**
     * 企业重做卡临账支付
     **/
    C_TEMP_REMAKE_CARD("S35000"),
    /**
     * 企业重做卡临账支付冲正
     **/
    C_TEMP_REMAKE_CARD_REVERSE("S35010"),

    /**
     * 企业重做卡互生币支付撤单
     **/
    C_HSB_REMAKE_CARD_CANCEL("S32100"),
    /**
     * 企业重做卡互生币支付撤单冲正
     **/
    C_HSB_REMAKE_CARD_CANCEL_REVERSE("S32110"),
    /**
     * 企业重做卡网银支付撤单
     **/
    C_INET_REMAKE_CARD_CANCEL("S34100"),
    /**
     * 企业重做卡网银支付撤单冲正
     **/
    C_INET_REMAKE_CARD_CANCEL_REVERSE("S34110"),
    /**
     * 企业重做卡临账支付撤单
     **/
    C_TEMP_REMAKE_CARD_CANCEL("S35100"),
    /**
     * 企业重做卡临账支付撤单冲正
     **/
    C_TEMP_REMAKE_CARD_CANCEL_REVERSE("S35110"),

    /**
     * 企业售后服务费互生币支付
     **/
    C_HSB_AFTER_SALES("S42000"),
    /**
     * 企业售后服务费互生币支付冲正
     **/
    C_HSB_AFTER_SALES_REVERSE("S42010"),
    /**
     * 企业售后服务费网银支付
     **/
    C_INET_AFTER_SALES("S44000"),
    /**
     * 企业售后服务费网银支付冲正
     **/
    C_INET_AFTER_SALES_REVERSE("S44010"),
    /**
     * 企业售后服务费临账支付
     **/
    C_TEMP_AFTER_SALES("S45000"),
    /**
     * 企业售后服务费临账支付冲正
     **/
    C_TEMP_AFTER_SALES_REVERSE("S45010"),

    /**
     * 个性卡定制互生币支付
     **/
    C_HSB_PRI_CARD_STYLE("S62000"),
    /**
     * 个性卡定制互生币支付冲正
     **/
    C_HSB_PRI_CARD_STYLE_REVERSE("S62010"),
    /**
     * 个性卡定制网银支付
     **/
    C_INET_PRI_CARD_STYLE("S64000"),
    /**
     * 个性卡定制网银支付冲正
     **/
    C_INET_PRI_CARD_STYLE_REVERSE("S64010"),
    /**
     * 个性卡定制临账支付
     **/
    C_TEMP_PRI_CARD_STYLE("S65000"),
    /**
     * 个性卡定制临账支付冲正
     **/
    C_TEMP_PRI_CARD_STYLE_REVERSE("S65010"),
    
    /**
     * 互生币申购系统资源段
     **/
    C_HSB_BUY_RES_RANGE("S82000"),
    /**
     * 网银申购系统资源段
     **/
    C_INET_BUY_RES_RANGE("S84000"),
    /**
     * 临账申购系统资源段
     **/
    C_TEMP_BUY_RES_RANGE("S85000"),
    /**
     * 互生币申购系统资源段撤单
     **/
    C_HSB_BUY_RES_RANGE_CANCEL("S82100"),
    /**
     * 网银申购系统资源段撤单
     **/
    C_INET_BUY_RES_RANGE_CANCEL("S84100"),
    /**
     * 临账申购系统资源段撤单
     **/
    C_TEMP_BUY_RES_RANGE_CANCEL("S85100"),
    
    /**
     * 托管企业首段资源网银申报
     **/
    T_INET_PAY_FIRST_RES_FEE("K34000"),
    /**
     * 托管企业首段资源网银申报冲正
     **/
    T_INET_PAY_FIRST_RES_FEE_REVERSE("K34010"),
    /**
     * 托管企业创业资源网银申报
     **/
    T_INET_PAY_FOUND_RES_FEE("K44000"),
    /**
     * 托管企业创业资源网银申报冲正
     **/
    T_INET_PAY_FOUND_RES_FEE_REVERSE("K44010"),
    /**
     * 托管企业全部资源网银申报
     **/
    T_INET_PAY_WHOLE_RES_FEE("K54000"),
    /**
     * 托管企业全部资源网银申报冲正
     **/
    T_INET_PAY_WHOLE_RES_FEE_REVERSE("K54010"),
    /**
     * 托管企业首段资源临账申报
     **/
    T_TEMP_PAY_FIRST_RES_FEE("K35000"),
    /**
     * 托管企业首段资源临账申报冲正
     **/
    T_TEMP_PAY_FIRST_RES_FEE_REVERSE("K35010"),
    /**
     * 托管企业创业资源临账申报
     **/
    T_TEMP_PAY_FOUND_RES_FEE("K45000"),
    /**
     * 托管企业创业资源临账申报冲正
     **/
    T_TEMP_PAY_FOUND_RES_FEE_REVERSE("K45010"),
    /**
     * 托管企业全部资源临账申报
     **/
    T_TEMP_PAY_WHOLE_RES_FEE("K55000"),
    /**
     * 托管企业全部资源临账申报冲正
     **/
    T_TEMP_PAY_WHOLE_RES_FEE_REVERSE("K55010"),
    /**
     * 成员企业网银申报
     **/
    B_INET_PAY_RES_FEE("K64000"),
    /**
     * 成员企业网银申报冲正
     **/
    B_INET_PAY_RES_FEE_REVERSE("K64010"),
    /**
     * 成员企业临账申报
     **/
    B_TEMP_PAY_RES_FEE("K65000"),
    /**
     * 成员企业临账申报冲正
     **/
    B_TEMP_PAY_RES_FEE_REVERSE("K65010"),
    /**
     * 服务公司申报网银支付
     **/
    S_INET_PAY_RES_FEE("K74000"),
    /**
     * 服务公司申报网银支付冲正
     **/
    S_INET_PAY_RES_FEE_REVERSE("K74010"),
    /**
     * 服务公司临账申报
     **/
    S_TEMP_PAY_RES_FEE("K75000"),
    /**
     * 服务公司临账申报冲正
     **/
    S_TEMP_PAY_RES_FEE_REVERSE("K75010"),

    /**
     * 劳务费收入分配
     */
    DECLARE_FEE_ALLOC("U36000"),
    /**
     * 管理费收入分配
     */
    MANAGE_FEE_ALLOC("U46000"),

    /**
     * 申报兑换互生币分配
     */
    DECLARE_BUYHSB_FEE_ALLOC("U56000"),

    /**
     * 平台资源费收入
     */
    PLAT_RES_FEE_ALLOC("U66000"),

    // /////////兼容旧数据///////////////////
    /**
     * 企业互生币预付积分充值
     **/
    C_HSB_DISPOSE_PAY_POINT("K82000"),
    /**
     * 企业网银预付积分充值
     **/
    C_INET_DISPOSE_PAY_POINT("K84000"),
    /**
     * 企业临帐预付积分充值
     **/
    C_TEMP_DISPOSE_PAY_POINT("K85000"),
    /**
     * 企业积分转货币
     **/
    C_POINT_TO_CASH("M71000"),
    /**
     * 个人积分转货币
     **/
    P_POINT_TO_CASH("M61000"),
    // /////////兼容旧数据///////////////////

    // // //////////扣税交易类型/////////////////
    // /*** 资源费分配扣税 ***/
    // C_TAX_FOR_RES_FEE_ALLOC("V36000"),
    // /*** 再增值分配扣税 ***/
    // C_TAX_FOR_DIST_EXT_POINT("V26000"),
    // /*** 互生积分分配扣税 ***/
    // C_TAX_FOR_DIST_HS_POINT("V16000"),
    // // //////////扣税交易类型/////////////////

    /**
     * 企业互生币转货币
     **/
    C_HSB_TO_CASH("M42000"),
    /**
     * 企业互生币转货币冲正
     **/
    C_HSB_TO_CASH_REVERSE("M42010"),
    /**
     * 个人互生币转货币
     **/
    P_HSB_TO_CASH("M32000"),
    /**
     * 个人互生币转货币冲正
     **/
    P_HSB_TO_CASH_REVERSE("M32010"),
    /**
     * 企业代兑互生币
     **/
    C_HSB_P_HSB_RECHARGE("M52000"),
    /**
     * 企业代兑互生币冲正
     **/
    C_HSB_P_HSB_RECHARGE_REVERSE("M52010"),
    /**
     * 跨平台代兑互生币(本地企业)
     **/
    C_HSB_P_ACROSS_RECHARGE_FOR_C("M82000"),
    /**
     * 跨平台代兑互生币(本地企业)冲正
     **/
    C_HSB_P_ACROSS_RECHARGE_FOR_C_REVERSE("M82010"),
    /**
     * 跨平台代兑互生币(本地消费者)
     **/
    C_HSB_P_ACROSS_RECHARGE_FOR_P("M92000"),
    /**
     * 跨平台代兑互生币(本地消费者)冲正
     **/
    C_HSB_P_ACROSS_RECHARGE_FOR_P_REVERSE("M92010"),
    // 代兑互生币时企业服务费收入应该在代兑互生币这个交易里面，不应该是一个单独的交易类型
    // /** 企业代消费者兑互生币赚取费用 **/
    // C_HSB_P_HSB_EARN_FEE("C00229"),

    /**
     * 企业积分转互生币
     **/
    C_POINT_TO_HSB("M21000"),
    /**
     * 企业积分转互生币冲正
     **/
    C_POINT_TO_HSB_REVERSE("M21010"),
    /**
     * 个人积分转互生币
     **/
    P_POINT_TO_HSB("M11000"),
    /**
     * 个人积分转互生币冲正
     **/
    P_POINT_TO_HSB_REVERSE("M11010"),

    /**
     * 企业积分投资
     **/
    C_POINT_INVEST("T21000"),
    /**
     * 企业积分投资冲正
     **/
    C_POINT_INVEST_REVERSE("T21010"),
    /**
     * 个人积分投资
     **/
    P_POINT_INVEST("T11000"),
    /**
     * 个人积分投资冲正
     **/
    P_POINT_INVEST_REVERSE("T11010"),

    /**
     * 企业投资分红分配
     **/
    C_INVEST_BONUS("T46000"),
    /**
     * 企业投资分红分配冲正
     **/
    C_INVEST_BONUS_REVERSE("T46010"),
    /**
     * 个人投资分红分配
     **/
    P_INVEST_BONUS("T36000"),
    /**
     * 个人投资分红分配冲正
     **/
    P_INVEST_BONUS_REVERSE("T36010"),
    /**
     * 积分再增值分配
     **/
    F_DIST_EXT_POINT("U26000"),
    /**
     * 积分再增值分配冲正
     **/
    F_DIST_EXT_POINT_REVERSE("U26010"),
    /**
     * 互生积分分配
     **/
    F_DIST_HS_POINT("U16000"),
    /**
     * 互生积分分配冲正
     **/
    F_DIST_HS_POINT_REVERSE("U16010"),

    /**
     * 企业转账预转出
     **/
    C_PRETRANS_CASH("L23000"),
    /**
     * 企业转账预转出冲正
     **/
    C_PRETRANS_CASH_REVERSE("L23010"),
    /**
     * 个人转账预转出
     **/
    P_PRETRANS_CASH("L13000"),
    /**
     * 个人转账预转出冲正
     **/
    P_PRETRANS_CASH_REVERSE("L13010"),
    /**
     * 企业转账预转出撤单
     **/
    C_PRETRANS_CASH_CANCEL("L23100"),
    /**
     * 企业转账预转出撤单冲正
     **/
    C_PRETRANS_CASH_CANCEL_REVERSE("L23110"),
    /**
     * 个人转账预转出撤单
     **/
    P_PRETRANS_CASH_CANCEL("L13100"),
    /**
     * 个人转账预转出撤单冲正
     **/
    P_PRETRANS_CASH_CANCEL_REVERSE("L13110"),
    /**
     * 企业转账转出
     **/
    C_TRANS_CASH("L43000"),
    /**
     * 企业转账转出冲正
     **/
    C_TRANS_CASH_REVERSE("L43010"),
    /**
     * 个人转账转出
     **/
    P_TRANS_CASH("L33000"),
    /**
     * 个人转账转出冲正
     **/
    P_TRANS_CASH_REVERSE("L33010"),
    /**
     * 企业转账失败退回
     **/
    C_TRANS_REFUND("L23200"),
    /**
     * 企业转账失败退回冲正
     **/
    C_TRANS_REFUND_REVERSE("L23210"),
    /**
     * 个人转账失败退回
     **/
    P_TRANS_REFUND("L13200"),
    /**
     * 个人转账失败退回
     **/
    P_TRANS_REFUND_REVERSE("L13210"),

    /**
     * 企业转账银行退票退回
     * 
     * 转账第一次通知结果是成功，对账查询实际是失败的，进行退票
     **/
    C_TRANS_BANK_REFUND("L43200"),
    /**
     * 企业转账银行退票退回冲正
     * 
     **/
    C_TRANS_BANK_REFUND_REVERSE("L43210"),
    /**
     * 个人转账银行退票退回
     **/
    P_TRANS_BANK_REFUND("L33200"),

    /**
     * 个人转账银行退票退回冲正
     **/
    P_TRANS_BANK_REFUND_REVERSE("L33210"),

    /**
     * 积分福利 申请意外伤害补贴
     **/
    W_ACCIDENT_SECURITY_APPLY("W10000"),
    /**
     * 积分福利 医疗补贴申请
     **/
    W_MEDICAL_SUBSIDIES_APPLY("W30000"),
    /**
     * 积分福利 身故保障申请
     **/
    W_DIE_SECURITY_APPLY("W20000"),

    /**
     * 本地持卡人本地现金消费积分
     **/
    LOCAL_CARD_LOCAL_POINT("A23000"),

    /**
     * 本地持卡人本地现金消费积分自动冲正
     **/
    LOCAL_CARD_LOCAL_POINT_AUTO_REVERSE("A23010"),

    /**
     * 本地持卡人本地现金消费积分撤消
     **/
    LOCAL_CARD_LOCAL_POINT_CANCEL("A23100"),

    /**
     * 本地持卡人本地现金消费积分撤消自动冲正
     **/
    LOCAL_CARD_LOCAL_POINT_CANCEL_AUTO_REVERSE("A23110"),

    /**
     * 持卡人本地互生币消费
     **/
    LOCAL_CARD_LOCAL_HSB("A21000"),

    /**
     * 持卡人本地互生币消费自动冲正
     **/
    LOCAL_CARD_LOCAL_HSB_AUTO_REVERSE("A21010"),

    /**
     * 持卡人本地互生币消费撤单
     **/
    LOCAL_CARD_LOCAL_HSB_CANCEL("A21100"),

    /**
     * 持卡人本地互生币消费撤单自动冲正
     **/
    LOCAL_CARD_LOCAL_HSB_CANCEL_AUTO_REVERSE("A21110"),

    /**
     * 持卡人本地互生币消费退货
     **/
    LOCAL_CARD_LOCAL_HSB_BACK("A21200"),

    /**
     * 持卡人本地互生币消费退货自动冲正
     **/
    LOCAL_CARD_LOCAL_HSB_BACK_AUTO_REVERSE("A21210"),
    
    /**
     * 持卡人本地网银消费
     **/
    LOCAL_CARD_LOCAL_BANK("A22000"),
    
    /**
     * 持卡人本地网银消费自动冲正
     **/
    LOCAL_CARD_LOCAL_BANK_AUTO_REVERSE("A22010"),
    
    /**
     * 持卡人本地网银消费撤单
     **/
    LOCAL_CARD_LOCAL_BANK_CANCEL("A22100"),
    
    /**
     * 持卡人本地互生币消费退货自动冲正
     **/
    LOCAL_CARD_LOCAL_BANK_CANCEL_AUTO_REVERSE("A22110"),
    
    /**
     * 持卡人本地网银消费退货
     **/
    LOCAL_CARD_LOCAL_BANK_BACK("A22200"),
    
    /**
     * 持卡人本地互生币消费退货自动冲正
     **/
    LOCAL_CARD_LOCAL_BANK_BACK_AUTO_REVERSE("A22210"),

    /** -------------------------- 跨平台交易 交易平台业务 begin-------------------------- */

    /** 异地持卡人本地现金消费积分 ----------------积分------------ **/

    /**
     * ------------pos冲正特别处理 ------------begin---------------------------
     * 引入跨平台交易后，按照现有8583报文协议，对于冲正业务后台无法在第一步区分本地、异地交易，为保证语境准确，单独创建冲正类型 代兑互生币另有定义
     */
    /**
     * 本地现金消费积分自动冲正
     **/
    LOCAL_POINT_AUTO_REVERSE("F23010"),
    /**
     * 本地现金消费积分撤消自动冲正
     **/
    LOCAL_POINT_CANCEL_AUTO_REVERSE("F23110"),
    /**
     * 本地互生币消费自动冲正
     **/
    LOCAL_HSB_AUTO_REVERSE("F21010"),
    /**
     * 本地互生币消费撤单自动冲正
     **/
    LOCAL_HSB_CANCEL_AUTO_REVERSE("F21110"),
    /**
     * 本地互生币消费退货自动冲正
     **/
    LOCAL_HSB_BACK_AUTO_REVERSE("F21210"),

    /** ------------pos冲正特别处理 ------------end--------------------------- */

    /** -------------------------- 跨平台交易 交易平台业务 begin-------------------------- */

    /**
     * 异地持卡人本地现金消费积分 ----------------积分------------
     **/
    REMOTE_CARD_LOCAL_POINT("E23000"),

    /**
     * 异地持卡人本地现金消费积分自动冲正
     **/
    REMOTE_CARD_LOCAL_POINT_AUTO_REVERSE("E23010"),

    /**
     * 异地持卡人本地现金消费积分撤消
     **/
    REMOTE_CARD_LOCAL_POINT_CANCEL("E23100"),

    /**
     * 异地持卡人本地现金消费积分撤消自动冲正 ----------------积分end------------
     **/
    REMOTE_CARD_LOCAL_POINT_CANCEL_AUTO_REVERSE("E23110"),

    /**
     * 异地持卡人本地互生币消费 ----------------互生币------------
     **/
    REMOTE_CARD_LOCAL_HSB("E21000"),

    /**
     * 异地持卡人本地互生币消费自动冲正
     **/
    REMOTE_CARD_LOCAL_HSB_AUTO_REVERSE("E21010"),

    /**
     * 异地持卡人本地互生币消费撤单
     **/
    REMOTE_CARD_LOCAL_HSB_CANCEL("E21100"),

    /**
     * 异地持卡人本地互生币消费撤单自动冲正
     **/
    REMOTE_CARD_LOCAL_HSB_CANCEL_AUTO_REVERSE("E21110"),

    /**
     * 异地持卡人本地互生币消费退货
     **/
    REMOTE_CARD_LOCAL_HSB_BACK("E21200"),

    /**
     * 异地持卡人本地互生币消费退货自动冲正 ----------------互生币end------------
     **/
    REMOTE_CARD_LOCAL_HSB_BACK_AUTO_REVERSE("E21210"),

    /** -------------------------- 跨平台交易 交易平台业务 end-------------------------- */

    /** -------------------------- 跨平台交易 发卡平台业务 begin-------------------------- */
    /**
     * 本地持卡人异地现金消费积分 ----------------积分end------------
     **/
    LOCAL_CARD_REMOTE_POINT("C23000"),

    /**
     * 本地持卡人异地现金消费积分自动冲正
     **/
    LOCAL_CARD_REMOTE_POINT_AUTO_REVERSE("C23010"),

    /**
     * 本地持卡人异地现金消费积分撤消
     **/
    LOCAL_CARD_REMOTE_POINT_CANCEL("C23100"),

    /**
     * 本地持卡人异地现金消费积分撤消自动冲正 ----------------积分end------------
     **/
    LOCAL_CARD_REMOTE_POINT_CANCEL_AUTO_REVERSE("C23110"),

    /**
     * 本地持卡人异地互生币消费 ----------------互生币------------
     **/
    LOCAL_CARD_REMOTE_HSB("C21000"),

    /**
     * 本地持卡人异地互生币消费自动冲正
     **/
    LOCAL_CARD_REMOTE_HSB_AUTO_REVERSE("C21010"),

    /**
     * 本地持卡人异地互生币消费撤单
     **/
    LOCAL_CARD_REMOTE_HSB_CANCEL("C21100"),

    /**
     * 本地持卡人异地互生币消费撤单自动冲正
     **/
    LOCAL_CARD_REMOTE_HSB_CANCEL_AUTO_REVERSE("C21110"),

    /**
     * 本地持卡人异地互生币消费退货
     **/
    LOCAL_CARD_REMOTE_HSB_BACK("C21200"),

    /**
     * 本地持卡人异地互生币消费退货自动冲正 ----------------互生币end------------
     **/
    LOCAL_CARD_REMOTE_HSB_BACK_AUTO_REVERSE("C21210"),

    /** -------------------------- 跨平台交易 发卡平台业务 end-------------------------- */

    /**
     * 外卖包月服务费
     **/
    TAKEAWAY_MONTHLY_SERVICE_CHARGE("510000"),

    /**
     * ps区域
     */

    // 日终分配交易类型
    /** 1：企业互生币收入(商城) G11000 */
    HSB_BUSINESS_ONLINE_CSC("G11000"),
    /** 2：企业互生币收入(线下) G12000 */
    HSB_BUSINESS_OFFLINE_CSC("G12000"),
    /** 3：互生币商业收入(商业服务费暂存) G13000 */
    HSB_BUSINESS_TEMPORARY_CSC("G13000"),
    /** 4：互生币商业收入(商业服务费月结) G14000 */
    HSB_BUSINESS_MONTH_ALLOC_CSC("G14000"),
    /** 5：互生币商业收入(商业服务费税收) G15000 */
    HSB_BUSINESS_CSC_TAX("G15000"),
    /** 6：消费积分分配收入 G20000 */
    HSB_BUSINESS_POINT("G20000"),
    /** 7：消费积分分配收入纳税 G21000 */
    HSB_BUSINESS_POINT_TAX("G21000"),
    /** 8：退消费积分税收(根据退货日终批量退税) G22000 */
    HSB_BUSINESS_BACK_TAX("G22000"),
    /** 9：消费积分撤单 G23000 */
    HSB_BUSINESS_POINT_CANCEL("G23000"),
    /** 9：消费积分退货 G24000 */
    HSB_BUSINESS_POINT_BACK("G24000"),

    /** ps区域 */

    /**------定金业务------------begin--------*/
    //预付定金
    LOCAL_CARD_LOCAL_EARNEST("A21700"),
    REMOTE_CARD_LOCAL_EARNEST("E21700"),
    LOCAL_CARD_REMOTE_EARNEST("C21700"),
    //定金结算
    LOCAL_CARD_LOCAL_EARNEST_SETTLE("A21800"),
    REMOTE_CARD_LOCAL_EARNESTT_SETTLE("E21800"),
    LOCAL_CARD_REMOTE_EARNESTT_SETTLE("C21800"),
    //定金冲正：
    LOCAL_CARD_LOCAL_EARNEST_REVERSE("A21710"),
    REMOTE_CARD_LOCAL_EARNEST_REVERSE("E21710"),
    LOCAL_CARD_REMOTE_EARNEST_REVERSE("C21710"),
    //定金结算冲正：
    LOCAL_CARD_LOCAL_EARNEST_SETTLE_REVERSE("A21810"),
    REMOTE_CARD_LOCAL_EARNESTT_SETTLE_REVERSE("E21810"),
    LOCAL_CARD_REMOTE_EARNESTT_SETTLE_REVERSE("C21810"),
    //定金撤销    
    LOCAL_CARD_LOCAL_EARNEST_CANCEL("A21900"),
    REMOTE_CARD_LOCAL_EARNEST_CANCEL("E21900"),
    LOCAL_CARD_REMOTE_EARNEST_CANCEL("C21900"),
    //撤销冲正
    LOCAL_CARD_LOCAL_EARNEST_CANCEL_REVERSE("A21910"),
    REMOTE_CARD_LOCAL_EARNEST_CANCEL_REVERSE("E21910"),
    LOCAL_CARD_REMOTE_EARNEST_CANCEL_REVERSE("C21910"),
    //查询无需定义
    
    /**------定金业务------------end--------*/
    /**
     * 透支预付积分账户平账
     */
    F_TICK_UP_PREPAY_POINT("X11000"),
    /**
     * 企业预付积分转货币
     */
    C_PREPAY_POINT_TO_CASH("X12000"),
    /**
     * 企业预付款退回
     */
    C_PREPAY_POINT_TRANS("X13000"),
    /**
     * 货币转银行对账平账
     */
    BANK_RECONCILIATION_ACCOUNT("X14000"),
    /**
     * 代兑手续费收入
     */
    C_HSB_P_HSB_EARN_FEE("X15000"),
    /**
     * 企业预付款转入
     */
    C_PREPAY_POINT_TRANS_IN("X16000"),
    /** 调账收入 */
    CHECK_BALANCE_IN("X10530"),
    /** 调账支出 */
    CHECK_BALANCE_OUT("X10630"),
    ;

    /**
     * 枚举类型代码
     **/
    private String code;

    TransType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    /**
     * 根据枚举类型代码反过来获取枚举对象
     * 
     * @param code
     *            枚举类型代码
     * @return
     */
    public static TransType getTransType(String code) {
        for (TransType item : TransType.values())
        {
            if (item.getCode().equals(code))
            {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        /**
         * 检查代码是否有重复
         */
        Set<String> codeSet = new HashSet<String>();
        for (TransType item : TransType.values())
        {
            if (codeSet.contains(item.getCode()))
            {
                System.err.println(item.name() + ":" + item.getCode() + " 代码有重复冲突，请修改！");
            }
            else
            {
                codeSet.add(item.getCode());
            }
        }
    }
}
