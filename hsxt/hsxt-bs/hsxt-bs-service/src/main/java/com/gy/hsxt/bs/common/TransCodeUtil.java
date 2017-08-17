/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common;

import org.apache.commons.lang3.StringUtils;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.order.TransInnerType;
import com.gy.hsxt.bs.order.enumtype.InvestSatus;
import com.gy.hsxt.bs.order.enumtype.TransOutResult;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;

/**
 * 交易类型代码工具类
 * 
 * @Package: com.gy.hsxt.bs.common
 * @ClassName: TransTypeUtil
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-2 上午10:41:45
 * @version V3.0.0
 */
public class TransCodeUtil {

	/**
	 * 获取资源费分配交易类型代码
	 * 
	 * @param bizType
	 *            业务类型
	 * @param custType
	 *            客户类型
	 * @param payChannel
	 *            支付方式
	 * @param bizNo
	 *            原业务编号
	 * 
	 * @return 交易代码
	 */
	public static String getResFeeTransCode(String bizType, int custType, int payChannel, int resType)
	{
		SystemLog.info("BS", "获取交易类型代码：getResFeeTransCode()", "begin");

		// 资源费分配
		if (bizType.equals(OrderType.RES_FEE_ALLOT.getCode()))
		{
			return checkResFeeAllotTransCode(payChannel, custType, resType);
		}
		return null;
	}

	/**
	 * 获取积分投资交易类型代码
	 * 
	 * @param bizType
	 *            业务类型
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	public static String getInvestTransCode(String bizType, int custType)
	{
		SystemLog.info("BS", "获取交易类型代码：getInvestTransCode()", "begin");
		// 交易码
		String transCode = "";

		// 投资
		if (bizType.equals(InvestSatus.POINT_INVEST.getCode()))
		{
			transCode = checkPointInvestTransCode(custType);
		}
		// 分红--在存储过程中绑定或传参到存储过程
		if (bizType.equals(InvestSatus.POINT_DIVIDEND.getCode()))
		{
			transCode = checkPointDividendTransCode(custType);
		}
		return transCode;
	}

	/**
	 * 获取银行转帐交易类型代码
	 * 
	 * @param bizType
	 *            业务类型
	 * @param custType
	 *            客户类型
	 * @param payChannel
	 *            支付方式
	 * @param bizNo
	 *            原业务编号
	 * 
	 * @return 交易代码
	 */
	public static String getBankTransCode(int transResult, int custType)
	{
		SystemLog.info("BS", "获取交易类型代码：getBankTransCode()", "begin");
		if (custType <= 0 || transResult < 0)
		{
			SystemLog.debug("BS", "获取交易类型代码：getBankTransCode()", "参数为空");
			throw new HsException(RespCode.PARAM_ERROR, "获取交易类型代码：参数为空");
		}

		// 银行转账预转出
		if (transResult == TransOutResult.TRANS_CASH_PRE.getCode())
		{
			return checkTransPreTransCode(custType);
		}

		// 转帐成功
		if (transResult == TransOutResult.TRANS_CASH_OUT.getCode())
		{
			return checkTransCashTransCode(custType);
		}

		// 转帐失败
		if (transResult == TransOutResult.TRANS_CASH_BACK.getCode())
		{
			return checkTransCashBackTransCode(custType);
		}

		// 转帐银行退票
		if (transResult == TransOutResult.TRANS_CASH_BANK_BACK.getCode())
		{
			return checkTransCashBankBackTransCode(custType);
		}
		return null;
	}

	/**
	 * 获取内部转帐交易类型代码
	 * 
	 * @param transInnerType
	 *            业务类型
	 * @param custType
	 *            客户类型
	 * 
	 * @return 交易代码
	 */
	public static String getTransInnerCode(String transInnerType, int custType)
	{
		SystemLog.info("BS", "获取交易类型代码：getTransInnerCode()", "begin");
		if (StringUtils.isBlank(transInnerType) || custType <= 0)
		{
			SystemLog.debug("BS", "获取交易类型代码：getTransInnerCode()", "参数为空");
			throw new HsException(RespCode.PARAM_ERROR, "获取交易类型代码：参数为空");
		}

		// 积分转互生币
		if (transInnerType.equals(TransInnerType.PV_TO_HSB.getCode()))
		{
			return checkPvToHsbTransCode(custType);
		}

		// 互生币转货币
		if (transInnerType.equals(TransInnerType.HSB_TO_CURRENCY.getCode()))
		{
			return checkHsbToCurrencyTransCode(custType);
		}

		// 代兑互生币(企业互生币转消费者互生币)
		if (transInnerType.equals(TransInnerType.HSB_ENTERPRISE_TO_CUSTOMER.getCode()))
		{
			return TransType.C_HSB_P_HSB_RECHARGE.getCode();
		}
		return null;
	}

	/**
	 * 获取订单交易类型代码
	 * 
	 * @param orderType
	 *            业务类型
	 * @param custType
	 *            客户类型
	 * @param payChannel
	 *            支付方式
	 * @return 交易代码
	 */
	public static String getOrderTransCode(String orderType, int custType, int payChannel)
	{
		SystemLog.info("BS", "获取交易类型代码：getTransTypeCode()", "begin");
		if (StringUtils.isBlank(orderType) || custType <= 0 || payChannel <= 0)
		{
			SystemLog.debug("BS", "获取交易类型代码：getTransTypeCode()", "参数为空");
			throw new HsException(RespCode.PARAM_ERROR, "获取交易类型代码：参数为空");
		}
		// 申购工具订单
		if (orderType.equals(OrderType.BUY_TOOL.getCode()))
		{
			return checkToolTransCode(payChannel);
		}

		// 系统使用年费订单
		if (orderType.equals(OrderType.ANNUAL_FEE.getCode()))
		{
			return checkAnnualFeeTransCode(payChannel);
		}

		// 兑换互生币订单
		if (orderType.equals(OrderType.BUY_HSB.getCode()))
		{
			return checkBuyHsbTransCode(payChannel, custType);
		}

		// 个人补卡
		if (orderType.equals(OrderType.REMAKE_MY_CARD.getCode()) && custType == CustType.PERSON.getCode())
		{
			return checkRemakeMyCardTransCode(payChannel);
		}

		// 售后收费订单
		if (orderType.equals(OrderType.AFTER_SERVICE.getCode()))
		{
			return checkAfterServiceTransCode(payChannel);
		}

		// 重做卡收费订单
		if (orderType.equals(OrderType.REMAKE_BATCH_CARD.getCode()))
		{
			return checkRemakeBatchCardTransCode(payChannel);
		}

		// 个性卡样定制
		if (orderType.equals(OrderType.CARD_STYLE_FEE.getCode()) && custType == CustType.TRUSTEESHIP_ENT.getCode())
		{
			return checkCardStyleFeeTransCode(payChannel);
		}

		// 系统资源申购
		if (orderType.equals(OrderType.APPLY_PERSON_RESOURCE.getCode())
				&& custType == CustType.TRUSTEESHIP_ENT.getCode())
		{
			return checkSystemResourceBuyTransCode(payChannel);
		}

		SystemLog.info("BS", "获取交易类型代码：getTransTypeCode()", "end");
		return null;
	}

	/**
	 * 获取工具订单撤单交易类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月17日 下午12:06:47
	 * @param orderType
	 * @param payChannel
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String getToolOrderCancelTransCode(String orderType, int payChannel)
	{

		PayChannel channel = PayChannel.getPayChannel(payChannel);
		// 申购工具
		if (OrderType.BUY_TOOL.getCode().equals(orderType))
		{
			switch (channel)
			{
			case E_BANK_PAY:
				return TransType.C_INET_SALES_PAY_CANCEL.getCode();
			case QUICK_PAY:
				return TransType.C_INET_SALES_PAY_CANCEL.getCode();
			case MOBILE_PAY:
				return TransType.C_INET_SALES_PAY_CANCEL.getCode();
			case CARD_PAY:
				return TransType.C_INET_SALES_PAY_CANCEL.getCode();
			case HS_COIN_PAY:
				return TransType.C_HSB_SALES_PAY_CANCEL.getCode();
			case TRANSFER_REMITTANCE:
				return TransType.C_TEMP_SALES_PAY_CANCEL.getCode();
			default:
				break;
			}
		}
		// 企业重做卡
		if (OrderType.REMAKE_BATCH_CARD.getCode().equals(orderType))
		{
			switch (channel)
			{
			case E_BANK_PAY:
				return TransType.C_INET_REMAKE_CARD_CANCEL.getCode();
			case QUICK_PAY:
				return TransType.C_INET_REMAKE_CARD_CANCEL.getCode();
			case MOBILE_PAY:
				return TransType.C_INET_REMAKE_CARD_CANCEL.getCode();
			case CARD_PAY:
				return TransType.C_INET_REMAKE_CARD_CANCEL.getCode();
			case HS_COIN_PAY:
				return TransType.C_HSB_REMAKE_CARD_CANCEL.getCode();
			case TRANSFER_REMITTANCE:
				return TransType.C_TEMP_REMAKE_CARD_CANCEL.getCode();
			default:
				break;
			}
		}
		// 个人补卡
		if (OrderType.REMAKE_MY_CARD.getCode().equals(orderType))
		{
			switch (channel)
			{
			case E_BANK_PAY:
				return TransType.P_INET_SALES_PAY_CANCEL.getCode();
			case QUICK_PAY:
				return TransType.P_INET_SALES_PAY_CANCEL.getCode();
			case MOBILE_PAY:
				return TransType.P_INET_SALES_PAY_CANCEL.getCode();
			case CARD_PAY:
				return TransType.P_INET_SALES_PAY_CANCEL.getCode();
			case HS_COIN_PAY:
				return TransType.P_HSB_SALES_PAY_CANCEL.getCode();
			case TRANSFER_REMITTANCE:
				return null;
			default:
				break;
			}
		}
		// 系统资源
		if (OrderType.APPLY_PERSON_RESOURCE.getCode().equals(orderType))
		{
			switch (channel)
			{
			case E_BANK_PAY:
				return TransType.C_INET_BUY_RES_RANGE_CANCEL.getCode();
			case QUICK_PAY:
				return TransType.C_INET_BUY_RES_RANGE_CANCEL.getCode();
			case MOBILE_PAY:
				return TransType.C_INET_BUY_RES_RANGE_CANCEL.getCode();
			case CARD_PAY:
				return TransType.C_INET_BUY_RES_RANGE_CANCEL.getCode();
			case HS_COIN_PAY:
				return TransType.C_HSB_BUY_RES_RANGE_CANCEL.getCode();
			case TRANSFER_REMITTANCE:
				return TransType.C_TEMP_BUY_RES_RANGE_CANCEL.getCode();
			default:
				break;
			}
		}
		return null;
	}

	/**
	 * 根据交易码转换出冲正交易码
	 * 
	 * @param transCode
	 *            交易码
	 * @return 冲正交易码
	 */
	public static String getReverseCode(String transCode)
	{
		StringBuilder sb = new StringBuilder(transCode);
		return sb.replace(transCode.length() - 2, transCode.length() - 1, "1").toString();
	}

	/**
	 * 资源费分配交易码
	 * 
	 * @param payChannel
	 *            支付方式
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	private static String checkResFeeAllotTransCode(int payChannel, int custType, int resType)
	{
		// 成员企业
		if (custType == CustType.MEMBER_ENT.getCode())
		{
			// 网银支付
			if (payChannel == PayChannel.E_BANK_PAY.getCode())
			{
				return TransType.B_INET_PAY_RES_FEE.getCode();
			}
			// 临帐支付(即转帐汇款方式)
			if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
			{
				return TransType.B_TEMP_PAY_RES_FEE.getCode();
			}
		}
		// 托管企业
		if (custType == CustType.TRUSTEESHIP_ENT.getCode())
		{
			// 首段资源
			if (resType == ResType.FIRST_SECTION_RES.getCode())
			{
				// 网银支付
				if (payChannel == PayChannel.E_BANK_PAY.getCode())
				{
					return TransType.T_INET_PAY_FIRST_RES_FEE.getCode();
				}
				// 临帐支付(即转帐汇款方式)
				if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
				{
					return TransType.T_TEMP_PAY_FIRST_RES_FEE.getCode();
				}
			}
			// 创业资源
			if (resType == ResType.ENTREPRENEURSHIP_RES.getCode())
			{
				// 网银支付
				if (payChannel == PayChannel.E_BANK_PAY.getCode())
				{
					return TransType.T_INET_PAY_FOUND_RES_FEE.getCode();
				}
				// 临帐支付(即转帐汇款方式)
				if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
				{
					return TransType.T_TEMP_PAY_FOUND_RES_FEE.getCode();
				}
			}
			// 全部资源
			if (resType == ResType.ALL_RES.getCode())
			{
				// 网银支付
				if (payChannel == PayChannel.E_BANK_PAY.getCode())
				{
					return TransType.T_INET_PAY_WHOLE_RES_FEE.getCode();
				}
				// 临帐支付(即转帐汇款方式)
				if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
				{
					return TransType.T_TEMP_PAY_WHOLE_RES_FEE.getCode();
				}
			}
		}
		// 服务公司
		if (custType == CustType.SERVICE_CORP.getCode())
		{
			// 网银支付
			if (payChannel == PayChannel.E_BANK_PAY.getCode())
			{
				return TransType.S_INET_PAY_RES_FEE.getCode();
			}
			// 临帐支付(即转帐汇款方式)
			if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
			{
				return TransType.S_TEMP_PAY_RES_FEE.getCode();
			}
		}
		return null;
	}

	/**
	 * 转帐银行退票交易码
	 * 
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	private static String checkTransCashBankBackTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
		{
			// 个人转账银行退票退回
			return TransType.P_TRANS_BANK_REFUND.getCode();
		}
		// 企业
		if (custType == CustType.MEMBER_ENT.getCode() // 成员企业
				|| custType == CustType.TRUSTEESHIP_ENT.getCode() // 托管企业
				|| custType == CustType.SERVICE_CORP.getCode() // 服务公司
				|| custType == CustType.MANAGE_CORP.getCode())// 管理公司
		{
			// 企业转账银行退票退回
			return TransType.C_TRANS_BANK_REFUND.getCode();
		}
		return null;
	}

	/**
	 * 转帐失败交易码
	 * 
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	private static String checkTransCashBackTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
		{
			// 个人转账失败退回
			return TransType.P_TRANS_REFUND.getCode();
		}
		// 企业
		if (custType == CustType.MEMBER_ENT.getCode() // 成员企业
				|| custType == CustType.TRUSTEESHIP_ENT.getCode() // 托管企业
				|| custType == CustType.SERVICE_CORP.getCode() // 服务公司
				|| custType == CustType.MANAGE_CORP.getCode())// 管理公司
		{
			// 企业转账失败退回
			return TransType.C_TRANS_REFUND.getCode();
		}
		return null;
	}

	/**
	 * 转帐成功交易码
	 * 
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	private static String checkTransCashTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
		{
			// 个人转账转出
			return TransType.P_TRANS_CASH.getCode();
		}
		// 企业
		if (custType == CustType.MEMBER_ENT.getCode() // 成员企业
				|| custType == CustType.TRUSTEESHIP_ENT.getCode() // 托管企业
				|| custType == CustType.SERVICE_CORP.getCode() // 服务公司
				|| custType == CustType.MANAGE_CORP.getCode())// 管理公司
		{
			// 企业转账转出
			return TransType.C_TRANS_CASH.getCode();
		}
		return null;
	}

	/**
	 * 转帐预转出交易码
	 * 
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	private static String checkTransPreTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
		{
			// 个人转账转出
			return TransType.P_PRETRANS_CASH.getCode();
		}
		// 企业
		if (custType == CustType.MEMBER_ENT.getCode() // 成员企业
				|| custType == CustType.TRUSTEESHIP_ENT.getCode() // 托管企业
				|| custType == CustType.SERVICE_CORP.getCode() // 服务公司
				|| custType == CustType.MANAGE_CORP.getCode())// 管理公司
		{
			// 企业转账转出
			return TransType.C_PRETRANS_CASH.getCode();
		}
		return null;
	}

	/**
	 * 积分投资分红交易码
	 * 
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	private static String checkPointDividendTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
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
	private static String checkPointInvestTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
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
	private static String checkHsbToCurrencyTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
		{
			// 个人互生币转货币
			return TransType.P_HSB_TO_CASH.getCode();
		}
		// 企业
		if (custType == CustType.MEMBER_ENT.getCode() // 成员企业
				|| custType == CustType.TRUSTEESHIP_ENT.getCode() // 托管企业
				|| custType == CustType.SERVICE_CORP.getCode() // 服务公司
		)
		{
			// 企业互生币转货币
			return TransType.C_HSB_TO_CASH.getCode();
		}
		return null;
	}

	/**
	 * 积分转互生币交易码
	 * 
	 * @param custType
	 *            客户类型
	 * @return 交易代码
	 */
	private static String checkPvToHsbTransCode(int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
		{
			// 个人积分转互生币
			return TransType.P_POINT_TO_HSB.getCode();
		}
		// 企业
		if (custType == CustType.MEMBER_ENT.getCode() // 成员企业
				|| custType == CustType.TRUSTEESHIP_ENT.getCode() // 托管企业
				|| custType == CustType.SERVICE_CORP.getCode() // 服务公司
		)
		{
			// 企业积分转互生币
			return TransType.C_POINT_TO_HSB.getCode();
		}
		return null;
	}

	/**
	 * 定制卡样订单交易码
	 * 
	 * @param payChannel
	 *            支付方式
	 * @return 交易代码
	 */
	private static String checkCardStyleFeeTransCode(int payChannel)
	{
		// 互生币支付
		if (payChannel == PayChannel.HS_COIN_PAY.getCode())
		{
			return TransType.C_HSB_PRI_CARD_STYLE.getCode();
		}
		// 网银支付
		if (payChannel == PayChannel.E_BANK_PAY.getCode() || payChannel == PayChannel.QUICK_PAY.getCode()
				|| payChannel == PayChannel.MOBILE_PAY.getCode() || payChannel == PayChannel.CARD_PAY.getCode())
		{
			return TransType.C_INET_PRI_CARD_STYLE.getCode();
		}
		// 临帐支付(即转帐汇款方式)
		if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
		{
			return TransType.C_TEMP_PRI_CARD_STYLE.getCode();
		}
		return null;
	}

	/**
	 * 重做卡收费订单交易码
	 * 
	 * @param payChannel
	 *            支付方式
	 * @return 交易代码
	 */
	private static String checkRemakeBatchCardTransCode(int payChannel)
	{
		// 互生币支付
		if (payChannel == PayChannel.HS_COIN_PAY.getCode())
		{
			return TransType.C_HSB_REMAKE_CARD.getCode();
		}
		// 网银支付
		if (payChannel == PayChannel.E_BANK_PAY.getCode() || payChannel == PayChannel.QUICK_PAY.getCode()
				|| payChannel == PayChannel.MOBILE_PAY.getCode() || payChannel == PayChannel.CARD_PAY.getCode())
		{
			return TransType.C_INET_REMAKE_CARD.getCode();
		}
		// 临帐支付(即转帐汇款方式)
		if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
		{
			return TransType.C_TEMP_REMAKE_CARD.getCode();
		}
		return null;
	}

	/**
	 * 售后收费订单交易码
	 * 
	 * @param payChannel
	 *            支付方式
	 * @return 交易代码
	 */
	private static String checkAfterServiceTransCode(int payChannel)
	{
		// 互生币支付
		if (payChannel == PayChannel.HS_COIN_PAY.getCode().intValue())
		{
			return TransType.C_HSB_AFTER_SALES.getCode();
		}
		// 网银支付
		if (payChannel == PayChannel.E_BANK_PAY.getCode().intValue() || payChannel == PayChannel.QUICK_PAY.getCode()
				|| payChannel == PayChannel.MOBILE_PAY.getCode()|| payChannel == PayChannel.CARD_PAY.getCode())
		{
			return TransType.C_INET_AFTER_SALES.getCode();
		}
		// 临帐支付(即转帐汇款方式)
		if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode().intValue())
		{
			return TransType.C_TEMP_AFTER_SALES.getCode();
		}
		return null;
	}

	/**
	 * 个人补卡支付方式
	 * 
	 * @param payChannel
	 *            支付方式
	 * @return 交易代码
	 */
	private static String checkRemakeMyCardTransCode(int payChannel)
	{
		// 互生币支付
		if (payChannel == PayChannel.HS_COIN_PAY.getCode())
		{
			return TransType.P_HSB_SALES_PAY.getCode();
		}
		// 网银支付
		if (payChannel == PayChannel.E_BANK_PAY.getCode() || payChannel == PayChannel.QUICK_PAY.getCode()
				|| payChannel == PayChannel.MOBILE_PAY.getCode() || payChannel == PayChannel.CARD_PAY.getCode())
		{
			return TransType.P_INET_SALES_PAY.getCode();
		}
		// 临账支付只对企业使用
		// // 临帐支付(即转帐汇款方式)
		// if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
		// {
		// return TransType.P_TEMP_SALES_PAY.getCode();
		// }
		return null;
	}

	/**
	 * 工具支付方式
	 * 
	 * @param payChannel
	 *            支付方式
	 * @return 交易代码
	 */
	private static String checkToolTransCode(int payChannel)
	{
		// 互生币支付
		if (payChannel == PayChannel.HS_COIN_PAY.getCode())
		{
			return TransType.C_HSB_SALES_PAY.getCode();
		}
		// 网银支付
		if (payChannel == PayChannel.E_BANK_PAY.getCode() || payChannel == PayChannel.QUICK_PAY.getCode()
				|| payChannel == PayChannel.MOBILE_PAY.getCode() || payChannel == PayChannel.CARD_PAY.getCode())
		{
			return TransType.C_INET_SALES_PAY.getCode();
		}
		// 临帐支付(即转帐汇款方式)
		if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
		{
			return TransType.C_TEMP_SALES_PAY.getCode();
		}
		return null;
	}

	/**
	 * 系统资源申购
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午4:43:16
	 * @param payChannel
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	private static String checkSystemResourceBuyTransCode(int payChannel)
	{
		// 互生币支付
		if (payChannel == PayChannel.HS_COIN_PAY.getCode())
		{
			return TransType.C_HSB_BUY_RES_RANGE.getCode();
		}
		// 网银支付
		if (payChannel == PayChannel.E_BANK_PAY.getCode() || payChannel == PayChannel.QUICK_PAY.getCode()
				|| payChannel == PayChannel.MOBILE_PAY.getCode() || payChannel == PayChannel.CARD_PAY.getCode())
		{
			return TransType.C_INET_BUY_RES_RANGE.getCode();
		}
		// 临帐支付(即转帐汇款方式)
		if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
		{
			return TransType.C_TEMP_BUY_RES_RANGE.getCode();
		}
		return null;
	}

	/**
	 * 年费支付方式
	 * 
	 * @param payChannel
	 *            支付方式
	 * @return 交易代码
	 */
	private static String checkAnnualFeeTransCode(int payChannel)
	{
		// 互生币支付
		if (payChannel == PayChannel.HS_COIN_PAY.getCode())
		{
			return TransType.C_HSB_SALES_PAY.getCode();
		}
		// 网银支付
		if (payChannel == PayChannel.E_BANK_PAY.getCode())
		{
			return TransType.C_INET_PAY_ANNUAL_FEE.getCode();
		}
		// 临帐支付(即转帐汇款方式)
		if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode())
		{
			return TransType.C_TEMP_PAY_ANNUAL_FEE.getCode();
		}
		return null;
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
	private static String checkBuyHsbTransCode(int payChannel, int custType)
	{
		// 持卡人
		if (custType == CustType.PERSON.getCode())
		{
			// 网银支付
			if (payChannel == PayChannel.E_BANK_PAY.getCode().intValue())
			{
				return TransType.P_INET_HSB_RECHARGE.getCode();
			}
			// 货币支付
			if (payChannel == PayChannel.MONEY_PAY.getCode().intValue())
			{
				return TransType.P_INAL_HSB_RECHARGE.getCode();
			}
			// 转帐汇款
			if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode().intValue())
			{
				return TransType.P_TEMP_HSB_RECHARGE.getCode();
			}
		} else
		{
			// 网银支付
			if (payChannel == PayChannel.E_BANK_PAY.getCode().intValue())
			{
				return TransType.C_INET_HSB_RECHARGE.getCode();
			}
			// 货币支付
			if (payChannel == PayChannel.MONEY_PAY.getCode().intValue())
			{
				return TransType.C_INAL_HSB_RECHARGE.getCode();
			}
			// 转帐汇款
			if (payChannel == PayChannel.TRANSFER_REMITTANCE.getCode().intValue())
			{
				return TransType.C_TEMP_HSB_RECHARGE.getCode();
			}
		}
		return null;
	}
}
