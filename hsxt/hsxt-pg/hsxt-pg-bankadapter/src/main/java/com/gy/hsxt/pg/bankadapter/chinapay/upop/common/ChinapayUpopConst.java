/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.common;

import java.util.HashMap;
import java.util.Map;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.common
 * 
 *  File Name       : ChinapayUpopConst.java
 * 
 *  Creation Date   : 2015年9月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付常量类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ChinapayUpopConst {
	/** 错误码缓存 **/
	private static final Map<String, String> ERR_CODE_CACHE = new HashMap<String, String>();

	/** 快捷支付[首次]接口版本号 **/
	public static final String PAY_FIRST_VERSION = "20070129";

	/** 快捷支付[非首次]接口版本号 **/
	public static final String PAY_SECOND_VERSION = "20070129";

	/** 快捷支付短信验证码发送接口版本号 **/
	public static final String SMS_CODE_VERSION = "20070129";

	/** 快捷支付网关号 **/
	public static final String UPOP_GATE_ID = "8619";
	
	/** 通用商户id **/
	public static final String PUB_MERID = "999999999999999";

	/** Upop借贷记标识：01：借记卡; 02：贷记卡; **/
	public static interface UpopCardType {
		/** 01：借记卡; **/
		public static final String DEBIT_CARD = "01";

		/** 02：贷记卡 **/
		public static final String CREDIT_CARD = "02";
	}

	/** Upop银行卡签约状态 **/
	public static interface UpopCardBindingStat {
		/** 00：签约成功; **/
		public static final String SUCCESS = "00";

		/** 01：签约状态未知; **/
		public static final String UNKNOWN = "01";

		/** 02：签约失败; **/
		public static final String FAILED = "02";

		/** 03：未签约; **/
		public static final String NEVER = "03";
	}

	/** Upop快捷支付交易子类型 **/
	public static interface UpopSubTransType {
		// 01：银行卡签约;02：短信验证码发送请求;03：签约支付;04：卡号签约并支付;
		/** 01：银行卡签约; **/
		public static final String CARD_BINDIND = "01";

		/** 02：短信验证码发送请求; **/
		public static final String SMS_CODE_SEND = "02";

		/** 03：快捷支付[非首次]; **/
		public static final String PAY_SECOND = "03";

		/** 04：快捷支付[首次], 卡号签约并支付; **/
		public static final String PAY_FIRST = "04";
	}

	/** Upop快捷支付交易状态：1001：交易成功; 1111：交易状态未知; 其余均为交易失败 **/
	public static interface UpopTransStatus {
		/** 1001：交易成功 **/
		public static final String TRANS_SUCCESS = "1001";

		/** 1111：交易状态未知 **/
		public static final String TRANS_UNKOWN = "1111";
	}

	/** 银联Upop操作返回码 **/
	public static enum UpopRespCode {

		OPT_SUCCESS("000", "操作成功"), 
		OPT_FAILED("001", "支付失败，原因不详"),

		E_002("002", "您输入的卡号无效"),
		E_003("003", "您的发卡银行不支持该商户，请更换其他银行卡"),
		E_006("006", "您的卡已经过期，请使用其他卡支付"), 
		E_011("011", "您卡上的余额不足"),
		E_014("014", "您的卡已过期或者是您输入的有效期不正确"), 
		E_015("015", "您输入的银行卡密码有误"), 
		E_018("018", "交易未通过，请尝试使用其他银联卡支付或联系95516"), 
		E_020("020", "您输入的转入卡卡号有误"), 
		E_021("021", "您输入的验证信息有误"), 
		E_025("025", "查找原始交易失败"), 
		E_030("030", "报文错误"), 
		E_031("031", "交易受限"), 
		E_032("032", "系统维护中"), 
		E_036("036", "交易金额超限"), 
		E_037("037", "原始金额错误"), 
		E_039("039", "您已连续多次输入错误密码"), 
		E_040("040", "您的银行卡暂不支持在线支付业务，请向您的银行咨询如何加办银联在线支付"), 
		E_041("041", "您的银行不支持快捷支付"), 
		E_042("042", "您的银行不支持小额支付"), 
		E_043("043", "您的银行不支持快捷支付"), 
		E_056("056", "您的银行卡所能进行的交易受限，详细请致电发卡行进行查询"), 
		E_057("057", "该银行卡未开通银联在线支付业务"), 
		E_060("060", "银行卡未开通快捷支付"), 
		E_061("061", "银行卡开通状态查询次数过多"), 
		E_071("071", "交易无效，无法完成"), 
		E_072("072", "无此交易"), 
		E_073("073", "扣款成功但交易超时"), 
		E_074("074", "对不起，该操作只能在交易当日进行"), 
		E_080("080", "内部错误"), 
		E_081("081", "可疑报文"), 
		E_082("082", "验签失败"), 
		E_083("083", "超时"), 
		E_084("084", "订单不存在"), 
		E_085("085", "不支持短信发送"), 
		E_086("086", "短信验证码错误"), 
		E_087("087", "您的短信发送过于频繁，请稍候再试"), 
		E_088("088", "您的短信发送累计过于频繁，请在5分钟后重试"), 
		E_089("089", "对不起，短信发送失败，请稍候再试"), 
		E_090("090", "请您登录工商银行网上银行或拨打95588进行后续认证操作"), 
		E_093("093", "请致电您的银行以确定您的个人客户基本信息中的相关信息设置正确"), 
		E_094("094", "重复交易"), 
		E_095("095", "您尚未在邮储银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通"), 
		E_097("097", "请致电您的银行以确定您的用户信息是否设置正确，并咨询是否已经开办银联在线支付"),

		E01("E01", "非法访问"), 
		E02("E02", "商户某些提交域为空"), 
		E03("E03", "商户某些提交域非法"), 
		E04("E04", "商户某些提交域长度错误"), 
		E05("E05", "不支持的网关号"), 
		E06("E06", "交易类型错误，目前只提供消费服务"), 
		E07("E07", "订单已被重复提交支付，本次支付已过期"), 
		E08("E08", "交易状态错误"), 
		E09("E09", "错误的交易子类型"), 
		E10("E10", "银行卡未签约"), 
		E11("E11", "银行卡签约状态不成功"), 
		E12("E12", "卡号开通状态查询失败"),
		E13("E13", "签约信息更新失败"), 
		E14("E14", "银行卡未在UPOP开通"), 
		E15("E15", "短信验证码发送失败"), 
		E16("E16", "商户请求非法"), 
		E17("E17", "超出流量控制范围"), 
		E18("E18", "非法的银行卡号"), 
		E19("E19", "商户数字签名非法"), 
		E20("E20", "错误的交易币种"), 
		E21("E21", "境外商户不允许境内交易接口"), 
		E22("E22", "交易未对该商户开放"), 
		E23("E23", "商户不存在"), 
		E24("E24", "商户已被关闭"), 
		E25("E25", "商户网关路由不存在"), 
		E26("E26", "商户网关路由已关闭"), 
		E27("E27", "签约信息不存在"), 
		E28("E28", "卡号未签约成功"), 
		E29("E29", "原交易不存在, 请首先调用获取短信验证码接口"), 
		E30("E30", "原交易状态为非初始状态"), 
		E31("E31", "原交易网关号错误"), 
		E32("E32", "交易金额与原交易金额不符"), 
		E33("E33", "签约支付失败"), 
		E34("E34", "非法的接口版本号"), 
		E35("E35", "非法的金额"), 
		E36("E36", "非法的订单号"), 
		E37("E37", "超过二级商户单笔金额上限"), 
		E38("E38", "系统异常"), 
		E39("E39", "超过二级商户总计金额上限"), 
		E40("E40", "非法的交易应答接收地址"), 
		E41("E41", "商户交易日期与银联系统日期相差太大"), 
		E42("E42", "相同订单号的交易已经成功处理"),
		E43("E43", "非法的交易日期"), 
		E44("E44", "商户私有域格式错误"), 
		E45("E45", "非法签约号"), 
		E46("E46", "卡号已经开通"), 
		E47("E47", "签约信息入库失败"), 
		E48("E48", "UPOP签约并支付页面跳转失败");

		private String code;
		private String desc;

		UpopRespCode(String errCode, String desc) {
			this.code = errCode;
			this.desc = desc;

			ERR_CODE_CACHE.put(errCode, desc);
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static String getDescByCode(String errCode) {
			String desc = ERR_CODE_CACHE.get(errCode);

			if (StringHelper.isEmpty(desc)) {
				desc = "失败原因不详! errCode=" + errCode;
			}

			return desc;
		}
		
		public static boolean containCode(String errCode) {
			return ERR_CODE_CACHE.containsKey(errCode);
		}

		public boolean valueEquals(String errCode) {
			return this.code.equals(errCode);
		}
	}
}
