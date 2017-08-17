/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.common;

import java.math.BigDecimal;

/**
 * @Package: com.gy.hsxt.ps.common
 * @ClassName: Constants
 * @Description: 常量類
 * 
 * @author: chenhongzhi
 * @date: 2015-11-18 下午6:01:34
 * @version V3.0
 */

public class Constants
{
	/** 业务类型  正常交易 */
	public static final int BUSINESS_TYPE0 = 0;
	/** 业务类型  撤单 */
	public static final int BUSINESS_TYPE1 = 1;
	/** 业务类型  退货 */
	public static final int BUSINESS_TYPE2 = 2;
	
	/** 第一次与POS交互 跨地区交易 */
	public static final int POS_TO_PS_COUNT1 = 1;
	/** 第二次与POS交互 跨地区交易 */
	public static final int POS_TO_PS_COUNT2 = 2;

	/** 1 减向入账消费者互生币账户应扣除的消费金额。并作为待向A平台转移的的清算资金入账 */
	public static final int PS_TO_POS_RESULT100100 = 100100;
	/** 2 增向入账消费者应得积分(包含1) */
	public static final int PS_TO_POS_RESULT100120 = 100120;
	/** 3 其余待分配积分纳入日结分配计划(包含1,2) */
	public static final int PS_TO_POS_RESULT100123 = 100123;

	/** 是否扣商业服务费 0是 否1 */
	public static final int IS_DEDUCTION0 = 0;
	/** 是否扣商业服务费 0是 否1 */
	public static final int IS_DEDUCTION1 = 1;

	/** 是否网上积分登记 0是 否1 */
	public static final int IS_ONLINE_REGISTER0 = 0;
	/** 是否网上积分登记 0是 否1 */
	public static final int IS_ONLINE_REGISTER1 = 1;
	
	/** 支付状态 0未支付 */
    public static final int PAY_STATUS0 = 0;
    
    /** 支付状态 1已支付 */
    public static final int PAY_STATUS1 = 1;
    
    /** 支付状态 2非支付类业务 */
    public static final int PAY_STATUS2 = 2;
    
    /** 支付状态 3支付处理中 */
    public static final int PAY_STATUS3 = 3;

	/** 服务费类型 1、商业服务费 2、其他服务费 */
	public static final int CSC_TYPE_SERVICE_FEE1 = 1;
	/** 服务费类型 1、商业服务费 2、其他服务费 */
	public static final int CSC_TYPE_OTHER2 = 2;

	/** 是否结算  0、已结算 1、未结算 2、已分配 */
	public static final int IS_SETTLE0 = 0;
	/** 是否结算  0、已结算 1、未结算 2、已分配  */
	public static final int IS_SETTLE1 = 1;
	/** 是否结算  0、已结算 1、未结算 2、已分配  */
	public static final int IS_SETTLE2 = 2;
	
	/** 结算类型 1、日结 2、月结 */
	public static final int SETTLE_TYPE_DAY1 = 1;
	/** 结算类型 1、日结 2、月结 */
	public static final int SETTLE_TYPE_MONTH2 = 2;

	/** 成功标记 */
	public static final int SUCCESS = 0;
	/** 失败标记 */
	public static final int FAIL = 1;

	/** 成功标记 */
	public static final String SUCCESS_FLAG = "success";
	/** 失败标记 */
	public static final String FAIL_FLAG = "fail";

	/** 互生平台 */
	public static final int HS_PAAS = 1;
	/** 管理公司 */
	public static final int HS_MANAGE = 2;
	/** 服务公司 */
	public static final int HS_SERVICE = 3;
	/** 托管企业 */
	public static final int HS_TRUSTEE = 4;
	/** 成员企业 */
	public static final int HS_ENTERPRISE = 5;

	/** 交易系统 */
	public static final String TRANS_SYSTEM = "PS";
	/** 会计时间 */
	public static final String ACCOUNTANT_DATE = "23:59:59";

	/** 积分数据文件前缀 */
	public static final String SETTLEMENT_POINT = "PV";
	/** 税收文件前缀 */
	public static final String SETTLEMENT_TAX = "TAX";
	/** 互生币数据文件前缀 */
	public static final String SETTLEMENT_HSB = "HSB";
	/** 商业服务费文件前缀 */
	public static final String SETTLEMENT_CSC = "CSC";
	/** 电商结算结果文件前缀 */
	public static final String SETTLEMENT_EC = "EC";
	
	/** PS目录 */
	public static final String SETTLEMENT_PS = "PS";
	/** 电商结算文件路径 */
	public static final String EC_SETTLEMENT_FILE_PATH = PropertyConfigurer
			.getProperty("ps.filePath");
	
	/** 账务系统结算文件路径 */
	public static final String AC_SETTLEMENT_FILE_PATH = PropertyConfigurer
			.getProperty("ps.filePath");
	
	

	/** 最小积分 */
	public static final double MIN_POINT = 0.1;
	/** 最小积分 */
	public static final double MIN_POINT2 = 0.05;
	/** 保留小数点2位数 */
	public static final int SURPLUS_TWO = 2;
	/** 保留小数点6位数 */
	//public static final int SURPLUS_SIX = 6;

	/** 批量插入线程数(线程) */
	public static final int BAT_NTHREADS = 10;
	/** 批量插入最大数(线程) */
	public static final int BAT_MAXSUM =1500;

	/** 电商批结算 批量更新最大数 */
	public static final int EC_BAT_UPDATE_MAXSUM = 1000;
	/** 电商批结算 批量插入最大数 */
	public static final int EC_BAT_INSERT_MAXSUM = 2000;

	/** 生成的每个积分文件的数据量 */
	public static final int DATA_COUNT = 1000000;

	/** 货币转换率 */
	public static final double CURRENCY_RATE = 1;

	/** 积分纳税率 */
	public static final BigDecimal POINT_TAX_RATE = new BigDecimal(1);
	
	/** 互生币收入费率 */
	public static final BigDecimal HSB_CSC_INCOME_RATE = new BigDecimal(1);

	/** 互生币服务费率 */
	//public static final String HSB_CSC_RATE = "0.01";

	/** 消费者积分比例 */
	public static final BigDecimal PER_POINT_RATE = new BigDecimal(Double.toString(0.5));

	/** 积分分配服务 */
	public static final String ALLOC_SERVIER = "pointAllocService";
	/** 对账文件服务 */
	public static final String BALANCE_SERVIER = "reconciliationService";

	/** A-持卡人本地消费 */
	public static final String POINT_LOCAL_A = "A";
	/** B-非持卡人本地消费 */
	public static final String POINT_LOCAL_NOT_CARD_B = "B";
	/** C-本地持卡人异地消费 */
	public static final String POINT_CARD_ALLOPATRY_C = "C";
	/** E-异地持卡人本地消费 */
	public static final String POINT_ALLOPATRY_CARD_E = "E";

	/** 持卡人本地消费 */
	public static final String POINT_LOCAL = "A0";
	/** 持卡人本地消费 预留 */
	public static final String POINT_LOCAL3 = "A3";
	/** 持卡人本地消费 预留结单 */
	public static final String POINT_LOCAL4 = "A4";
	/** 预付定金 */
	public static final String POINT_LOCAL7="A7";

	/** 预付定金结单 */
	public static final String POINT_LOCAL8="A8";

	/** 非持卡人本地消费 */
	public static final String POINT_LOCAL_NOT_CARD = "B0";
	/** 非持卡人本地消费 预留 */
	public static final String POINT_LOCAL_NOT_CARD3 = "B3";
	/** 非持卡人本地消费 预留结单 */
	public static final String POINT_LOCAL_NOT_CARD4 = "B4";

	/** 本地持卡人异地消费 */
	public static final String POINT_CARD_ALLOPATRY = "C0";
	/** 本地持卡人异地消费 预留 */
	public static final String POINT_CARD_ALLOPATRY3 = "C3";
	/** 本地持卡人异地消费 预留结单 */
	public static final String POINT_CARD_ALLOPATRY4 = "C4";

	/** 异地持卡人本地消费 */
	public static final String POINT_ALLOPATRY_CARD = "E0";
	/** 异地持卡人本地消费 预留 */
	public static final String POINT_ALLOPATRY_CARD3 = "E3";
	/** 异地持卡人本地消费 预留结单 */
	public static final String POINT_ALLOPATRY_CARD4 = "E4";

	/** 持卡人本地退货 */
	public static final String BACK_LOCAL = "A2";
	/** 本地持卡人异地退货 */
	public static final String BACK_CARD_ALLOPATRY = "C2";
	/** 异地持卡人本地退货 */
	public static final String BACK_ALLOPATRY_CARD = "E2";

	/** 持卡人本地撤单 */
	public static final String CANCEL_LOCAL = "A1";
	/** 持卡人本地预留撤单 */
	public static final String CANCEL_LOCAL5 = "A5";

	/** 非持卡人本地撤单 */
	public static final String CANCEL_LOCAL_NOT_CARD = "B1";
	/** 非持卡人本地预留撤单 */
	public static final String CANCEL_LOCAL_NOT_CARD5 = "B5";

	/** 本地持卡人异地撤单 */
	public static final String CANCEL_CARD_ALLOPATRY = "C1";
	/** 本地持卡人异地预留撤单 */
	public static final String CANCEL_CARD_ALLOPATRY5 = "C5";

	/** 异地持卡人本地撤单 */
	public static final String CANCEL_ALLOPATRY_CARD = "E1";
	/** 异地持卡人本地预留撤单 */
	public static final String CANCEL_ALLOPATRY_CARD5 = "E5";

	/** 互生币 */
	public static final String POINT_HSB = "1";
	/** 网银 */
	public static final String POINT_CYBER = "2";
	/** 现金 */
	public static final String POINT_CASH = "3";

	/** 订单状态 正常 */
	public static final String POINT_BUSS_TYPE0 = "0";
	/** 订单状态 已撤单 */
	public static final String POINT_BUSS_TYPE1 = "1";
	/** 订单状态 全部退货 */
	public static final String POINT_BUSS_TYPE2 = "2";
	/** 订单状态 预留 */
	public static final String POINT_BUSS_TYPE3 = "3";
	/** 订单状态 预留结单 */
	public static final String POINT_BUSS_TYPE4 = "4";
	/** 订单状态 退货预扣 */
	public static final String POINT_BUSS_TYPE5 = "5";
	/** 订单状态 部分退货 */
	public static final String POINT_BUSS_TYPE6 = "6";
	/** 预付定金*/
	public static final String POINT_BUSS_TYPE7 = "7";
	/** 预付定金结单*/
	public static final String POINT_BUSS_TYPE8 = "8";
	/** 预付定金撤销*/
	public static final String POINT_BUSS_TYPE9 = "9";

	/** 订单状态 无效 */
	public static final String ALREADY_NULLITY10 = "10";
	/** 订单状态 自动冲正 */
	public static final String ALREADY_CORRECT11 = "11";
	/** 订单状态 手工冲正 */
	public static final String ALREADY_HAND_CORRECT12 = "12";
	/** 订单状态 手工补单 */
	public static final String ALREADY_HAND_WORK13 = "13";

	/** 交易流水号 积分 */
	public static final String TRANS_NO_POINT10 = "10";
	/** 交易流水号 撤单 */
	public static final String TRANS_NO_CANCEL11 = "11";
	/** 交易流水号 退货 */
	public static final String TRANS_NO_BACK12 = "12";
	/** 交易流水号 沖正 */
	public static final String TRANS_NO_CORRECT13 = "13";

	/** 流水号 积分分配 */
	public static final String NO_POINT_ALLOC20 = "20";
	/** 流水号 积分汇总 */
	public static final String NO_POINT_SUM21 = "21";
	/** 流水号 税收(积分税收) */
	public static final String NO_POINT_TAX22 = "22";

	/** 流水号 互生币汇总 线上 */
	public static final String NO_HSB_ONLINE_SUM30 = "30";
	/** 流水号 互生币汇总 线下 */
	public static final String NO_HSB_OFFLINE_SUM31 = "31";
	/** 流水号 服务费(商业、包月) */
	public static final String NO_SERVICE_FEE32 = "32";

	/** 批结算 */
	public static final String NO_SETTLE40 = "40";

//	/** 渠道类型 POS */
//	public static final int CHANNEL_TYPE_POS = 1;
//	/** 渠道类型 刷卡器 */
//	public static final int CARD_READER = 2;
//	/** 渠道类型 移动平板 */
//	public static final int MOVE_PLATE = 3;
//	/** 渠道类型 互商 */
//	public static final int HU_BUSINESS = 4;
//	/** 渠道类型 第三方 */
//	public static final int THIRD_PARTY = 5;

	/** 设备类型 WEB */
	public static final int EQUIPMENT_WEB = 1;
	/** 设备类型 POS */
	public static final int EQUIPMENT_POS = 2;
	/** 设备类型 MCR(连接刷卡器) */
	public static final int EQUIPMENT_MCR = 3;
	/** 设备类型 手机 */
	public static final int EQUIPMENT_MOBILE = 4;
	/** 设备类型 互生平板 */
	public static final int EQUIPMENT_HSPAD = 5;
	/** 设备类型 平板 */
	public static final int EQUIPMENT_PAD = 6;
	/** 设备类型 IVR(电话) */
	public static final int EQUIPMENT_IVR = 7;
	/** 设备类型 其他设备 */
	public static final int EQUIPMENT_SHOP = 8;

	/** 服务费 商业 */
	public static final int SERVICE_FEE_BUSINESS = 1;
	/** 服务费 包月 */
	public static final int SERVICE_FEE_MONTHLY = 2;

	/** 数据库 消费积分明细表名称 */
	public static final String T_PS_NDETAIL = "T_PS_NDETAIL";
	/** 数据库 撤单明细表名称 */
	public static final String T_PS_CDETAIL = "T_PS_CDETAIL";
	/** 数据库 退货明细表名称 */
	public static final String T_PS_BDETAIL = "T_PS_BDETAIL";
	/** 数据库 冲正明细表名称 */
	public static final String T_PS_RDETAIL = "T_PS_RDETAIL";

	/** 红冲标识(0 普通) */
	public static final int WRITE_BACK_0 = 0;
	/** 红冲标识(1 自动冲正) */
	public static final int WRITE_BACK_1 = 1;
	/** 红冲标识(2 手工红冲) */
	public static final int WRITE_BACK_2 = 2;
	/** 红冲标识(3 手工补单) */
	public static final int WRITE_BACK_3 = 3;

	/** 对账结果1 一致 ，2 不一致 */
	public static final int SETTLE_RESULT1 = 1;
	/** 对账结果1 一致 ，2 不一致 */
	public static final int SETTLE_RESULT2 = 2;

	// 日终分配交易类型
	/** 1：企业互生币收入(商城)       G11000 */
	public static final String HSB_BUSINESS_ONLINE_CSC = "G11000";
	/** 2：企业互生币收入(线下)       G12000 */
	public static final String HSB_BUSINESS_OFFLINE_CSC = "G12000";
	/** 3：互生币商业收入(商业服务费暂存)    G13000 */
	public static final String HSB_BUSINESS_TEMPORARY_CSC = "G13000";
	/** 4：互生币商业收入(商业服务费月结)    G14000 */
	public static final String HSB_BUSINESS_MONTH_ALLOC_CSC = "G14000";
	/** 5：互生币商业收入(商业服务费税收)    G15000 */
	public static final String HSB_BUSINESS_CSC_TAX = "G15000";
	/** 6：消费积分分配  G20000 */
	public static final String HSB_BUSINESS_POINT = "G20000";
	/** 7：积分分配收入纳税  G21000 */
	public static final String HSB_BUSINESS_POINT_TAX = "G21000";
	/** 8：退积分税收(根据退货日终批量退税)  G22000 */
	public static final String HSB_BUSINESS_BACK_TAX = "G22000";


	/** 0：HSB币支付、消费积分 */
	public static final int  HSB_POINT= 0;
	/** 1:撤单 */
	public static final int HSB_POINT_CANCEL=1;
	/** 2：退货 */
	public static final int HSB_POINT_BACK=2;
	/** 3：冲正 */
	public static final int HSB_POINT_CORRECT=3;
}
