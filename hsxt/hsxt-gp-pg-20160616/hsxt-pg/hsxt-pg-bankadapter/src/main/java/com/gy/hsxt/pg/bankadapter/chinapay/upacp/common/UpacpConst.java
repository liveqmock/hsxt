/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upacp.common;

import java.util.HashMap;
import java.util.Map;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upacp.common
 * 
 *  File Name       : UpacpConst.java
 * 
 *  Creation Date   : 2016年4月12日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 全渠道手机支付常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpacpConst {
	
	/** 错误码缓存 **/
	private static final Map<String, String> ERR_CODE_CACHE = new HashMap<String, String>();
	
	/** 接口版本5.0.0 */
	public static final String ACP_VERSION = "5.0.0";
	
	/** UPACP手机支付交易类型 **/
	public static interface UpacpBankTransType {
		/** UPACP查询交易 **/
		public static final String QUERY = "00";
		
		/** UPACP支付交易 **/
		public static final String PAY = "01";
		
		/** UPACP预授权 **/
		public static final String PRE_AUTH = "02";

		/** UPACP退款交易 **/
		public static final String REFUND = "04";
	}
	
	/** 银联UPACP手机支付响应码 **/
	public static enum UpacpRespCode {

		OPT_SUCCESS("00", "操作成功"),
		OPT_FAILED("01", "操作失败"),
		
		ERR_02("02", "系统未开放或暂时关闭，请稍后再试"),
		ERR_03("03", "交易通讯超时"), // 请发起查询交易
		ERR_04("04", "交易状态未明"), // 请发起查询交易
		ERR_05("05", "交易已受理"),  // 请发起查询交易
		ERR_10("10", "报文格式错误"),
		ERR_11("11", "验证签名失败"),
		ERR_12("12", "重复交易"),
		ERR_13("13", "报文交易要素缺失"),
		ERR_14("14", "批量文件格式错误"),
		ERR_30("30", "交易未通过，请尝试使用其他银联卡支付或联系95516"),
		ERR_31("31", "商户状态不正确"),
		ERR_32("32", "无此交易权限"),
		ERR_33("33", "交易金额超限"),
		ERR_34("34", "查无此交易"),
		ERR_35("35", "原交易状态不正确"),
		ERR_36("36", "与原交易信息不符"),
		ERR_37("37", "已超过最大查询次数或操作过于频繁"),
		ERR_38("38", "风险受限"),
		ERR_39("39", "交易不在受理时间范围内"),
		ERR_40("40", "绑定关系检查失败"),
		ERR_41("41", "批量状态不正确，无法下载"),
		ERR_42("42", "扣款成功但交易超过规定支付时间"),
		ERR_60("60", "交易失败，详情请咨询您的发卡行"),
		ERR_61("61", "输入的卡号无效，请确认后输入"),
		ERR_62("62", "交易失败，发卡银行不支持该商户，请更换其他银行卡"),
		ERR_63("63", "卡状态不正确"),
		ERR_64("64", "卡上的余额不足"),
		ERR_65("65", "输入的密码、有效期或CVN2有误，交易失败"),
		ERR_66("66", "持卡人身份信息或手机号输入不正确，验证失败"),
		ERR_67("67", "密码输入次数超限"),
		ERR_68("68", "您的银行卡暂不支持该业务，请向您的银行或95516咨询"),
		ERR_69("69", "您的输入超时，交易失败"),
		ERR_70("70", "交易已跳转，等待持卡人输入"),
		ERR_71("71", "动态口令或短信验证码校验失败"),
		ERR_72("72", "您尚未在卡所在银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通"),
		ERR_73("73", "支付卡已超过有效期"),
		ERR_74("74", "扣款成功，销账未知"),
		ERR_75("75", "扣款成功，销账失败"),
		ERR_76("76", "需要验密开通"),
		ERR_77("77", "银行卡未开通认证支付"),
		ERR_98("98", "文件不存在，操作失败"),
		ERR_99("99", "中国银联发生内部错误，具体原因不明");

		private String errCode;
		private String desc;

		UpacpRespCode(String errCode, String desc) {
			this.errCode = errCode;
			this.desc = desc;

			ERR_CODE_CACHE.put(errCode, desc);
		}

		public String getErrCode() {
			return errCode;
		}

		public String getDesc() {
			return desc;
		}

		public static String getDescByErrCode(String errCode) {
			String desc = ERR_CODE_CACHE.get(errCode);

			if (StringHelper.isEmpty(desc)) {
				desc = "失败原因不详! errCode=" + errCode;
			}

			return desc;
		}

		public boolean valueEquals(String errCode) {
			return this.errCode.equals(errCode);
		}
	}
}
