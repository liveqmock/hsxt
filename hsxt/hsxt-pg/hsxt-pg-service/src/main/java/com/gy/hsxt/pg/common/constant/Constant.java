/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.constant;

import java.util.HashMap;
import java.util.Map;

import com.gy.hsi.ds.common.contants.DSContants;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;
import com.gy.hsxt.pg.constant.PGConstant.MerType;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.constant
 * 
 *  File Name       : Constant.java
 * 
 *  Creation Date   : 2015年9月24日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 常量类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class Constant {

	/** utf-8编码 **/
	public static final String ENCODING_UTF8 = "UTF-8";

	/** ISO-8859-1编码 **/
	public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

	/** zk同步监听路径 **/
	public static final String ZK_ROOT_NODE = "/HSXT-PG";

	/** zk同步监听路径 **/
	public static final String ZK_SUB_NODE = "/SUB";
	
	/** zk同步监听路径(定时查询签约号) **/
	public static final String ZK_ROOT_NODE_BINDING = "/HSXT-PG-BINDINGNO";
	
	/** zk同步监听路径(定时查询签约号) **/
	public static final String ZK_SUB_NODE_BINDING = "/SUB-GET-BINDINGNO";

	/** 中国银联对账文件通知请求参数 **/
	public static final String PARAM_CP_NOTIFY_DOWLOAD = "download";

	/** 文件换行符 */
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/** 文件路径分隔符 */
	public static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	/** 调度执行日期 */
	public static final String BATCH_DATE = DSContants.BATCH_DATE;

	/** 对账文件日期 */
	public static final String ACC_FILE_DATE = "ACC_FILE_DATE";

	/** 响应给银联的下载对账文件通知 **/
	public static interface Reply2Chinapay {

		/** 接收到中国银联对账文件通知的响应：该值是由中国银联约定, 不得改动!! **/
		public static final String OK = "ChinapayOK";

		/** 接收到中国银联对账文件通知的响应：该值是由互生自行约定 **/
		public static final String ERR = "ChinapayError";
	}
	
	/** 响应给支付宝的通知 **/
	public static interface Reply2Alipay {

		/** 处理成功!! **/
		public static final String SUCCESS = "success";

		/** 处理失败 **/
		public static final String FAIL = "fail";
	}

	/** 是否为生产环境 **/
	public static interface ProductionEnv {
		/** 1-生产环境 **/
		public static final String TRUE = "1";

		/** 0-开发环境 **/
		public static final String FALSE = "0";
	}

	/** 支付网关对外URL特征符定义 **/
	// http://www.hsxt.com:80/站点名称/pg/cb/jmp/支付渠道/商户类型/业务类型/
	// http://www.hsxt.com:80/站点名称/pg/cb/ntf/支付渠道/商户类型/业务类型/
	public static interface PGUrlKeywords {
		public static final String PAY_GATEWAY = "p";
		public static final String INNER = "inner";
		public static final String CALLBACK = "c";
		public static final String JUMP = "j";
		public static final String NOTIFY = "n";

		public static final String UPOP_PAY_SECOND = "upop/paysecond";
		public static final String ALIPAY_DIRECT_PAY = "alipay/direct";
	}
	
	/** 保存到Session中的key: 阿里支付宝即时到账支付(/p/inner/a/alipay/direct) **/
	public static final String URL_ALIPAY_DIRECT_PAY = "/"
			+ PGUrlKeywords.PAY_GATEWAY + "/" + PGUrlKeywords.INNER + "/"
			+ PGChannelProvider.ALIPAY + "/" + PGUrlKeywords.ALIPAY_DIRECT_PAY;

	/** 保存到Session中的key: 中国银联快捷非首次支付(/p/inner/c/upop/paysecond) **/
	public static final String URL_UPOP_PAY_SECOND = "/"
			+ PGUrlKeywords.PAY_GATEWAY + "/" + PGUrlKeywords.INNER + "/"
			+ PGChannelProvider.CHINAPAY + "/" + PGUrlKeywords.UPOP_PAY_SECOND;

	/** 保存到Session中的key **/
	public static interface SessionKey {
		public static final String REQUEST_PROXY_DATA = "requestProxyData";
		public static final String ILLEGAL_REQ_ERROR = "illegalReqError";
		public static final String REQUEST_RESULT_DATA = "requestResultData";
	}

	/** 银联回调支付网关时的业务类型：支付 or退款? **/
	public static interface PGCallbackBizType {
		/** 支付回调 **/
		public static final int CALLBACK_PAY = 1;

		/** 退款回调 **/
		public static final int CALLBACK_REFUND = 2;
		
		/** 快捷独立签约回调 **/
		public static final int CALLBACK_BINDING = 3;
	}

	/** 重试类型枚举值定义 **/
	public static interface RetryBussinessType {
		/** 10:支付结果通知GP **/
		public static final int PAYMENT_NT = 10;

		/** 20:退款结果通知GP **/
		public static final int REFUND_NT = 20;

		/** 21:退款请求(银行) **/
		public static final int REFUND_REQ = 21;

		/** 30:提现结果回调(内部系统) **/
		public static final int TRANS_CASH_NT = 30;

		/** 31:批量提现结果回调(内部系统, 假批量, 实际也是一个个处理, 处理的需要而设置的常量) **/
		public static final int TRANS_CASH_BATCH_NT = 31;

		/** 40:提现请求(银行) **/
		public static final int TRANS_CASH_REQ = 40;

		/** 50:提现结果查询(银行) **/
		public static final int TRANS_CASH_RESULT_REQ = 50;

		/** 60:批量提现请求(银行) **/
		public static final int TRANS_CASH_BATCH_REQ = 60;

		/** 70:下载对账文件 */
		public static final int DOWNLOAD_BALANCE_REQ = 70;
	}

	/**
	 * 重试结果状态 0 - 成功 1 - 失败 2 - 放弃
	 */
	public static interface RetryStatus {
		/** 0 - 成功 **/
		public static final int SUCCESS = 0;

		/** 1 - 失败 **/
		public static final int FAILED = 1;

		/** 2 - 放弃 **/
		public static final int ABANDON = 2;
	}

	/** 文件状态 */
	public static interface FileStatus {

		/** 10 - 文件待创建 */
		public static final int READY = 10;

		/** 11 - 文件正在创建 */
		public static final int CREATING = 11;

		/** 20 - 文件创建成功 */
		public static final int SUCCESS = 20;

		/** 21 - 文件创建失败 */
		public static final int FAILED = 21;
	}

	/** 下载状态 */
	public static interface DownloadStatus {

		/** 0 - 待下载 */
		public static final int READY = 0;

		/** 1 - 成功 */
		public static final int SUCCESS = 1;

		/** 2 - 失败 */
		public static final int FAILED = 2;

		/** 3 - 验签失败 */
		public static final int VER_FAILED = 3;
	}

	/** 对账类型枚举值定义 **/
	public static interface GPCheckType {
		/** 100:支付对账 **/
		public static final int PAYMENT = 100;

		/** 200:提现对账 **/
		public static final int TRANS_CASH = 200;
	}

	/** 锁类型 **/
	public static interface LockBizType {
		/** 100:触发DS **/
		public static final int TRIGGER_DS = 100;
	}

	/** 金额精度常量 **/
	public static interface PGAmountScale {
		public static final int TWO = 2;
		public static final int SIX = 6;
	}
	
	/** 快捷绑定状态 **/
	public static interface PGQuickBindingStatus {
		public static final int UNKNOWN = -1;
		public static final int NON_BINDING = 0;
		public static final int IS_BINDING = 1;
	}

	/** 商户类型映射 **/
	public static class MerTypeMapping {
		static Map<Integer, String> MERTYPE_MAPPING = new HashMap<Integer, String>(
				4);
		static {
			MERTYPE_MAPPING.put(MerType.HSXT, "HSXT");
			MERTYPE_MAPPING.put(MerType.HSEC, "HSEC");
		}

		public static String getDesc(int merType) {
			return MERTYPE_MAPPING.get(merType);
		}
	}
}
