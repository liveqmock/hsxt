package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto;

import com.gy.hsxt.pg.bankadapter.common.utils.CommonEnumHelper.IBankAdapterEnum;

/**
 * @author jbli
 * 
 */
public class PackageConstants {

	// 包头固定长度为222个字节
	public static final int LENGTH_OF_HEADER = 222;

	// 附件包头固定长度为277个字节
	public static final int LENGTH_OF_ATTACHMENTHEADER = 277;

	// 附件个数最多9个
	public static final int MAX_ATTACHMENTS = 9;

	public static final String XML_SCHEMA_TITLE = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

	/** 平安银行响应码 **/
	public static enum PinganRespCode {
		/** 请求成功 **/
		SUCCESS("000000"),

		/** GW3002为异常状态, 可以使用查询状态指令重新查询状态 **/
		ABNORMAL_STATE("GW3002"),

		/** MA4045为异常状态, 重复的第三方流水号或凭证号 **/
		DUPLICATE_SERIAL_NUMBER("MA4045");
		
		private String value;

		PinganRespCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public boolean valueEquals(String anotherValue) {
			return this.value.equals(anotherValue);
		}

		/**
		 * 请求成功的响应码
		 * 
		 * @param respCode
		 * @return
		 */
		public static boolean isSuccess(String respCode) {
			return SUCCESS.valueEquals(respCode);
		}
		
		/**
		 * B2Bi通信异常(通讯异常-发送报文至后台服务)
		 * 
		 * @param respCode
		 * @return
		 */
		public static boolean isB2BiNetErr(String respCode) {
			return respCode.matches("^(AFE003)$");
		}

		/**
		 * 模糊的、模棱两可的响应码(在银行的中间环节通信异常, 但是不代表一定是交易失败了)
		 * 
		 * @param respCode
		 * @return
		 */
		public static boolean isAmbiguous(String respCode) {
			return respCode
					.matches("^(GW3002)|(AFE001)|(AFE002)|(AFE004)|(E00006)|(E00007)|(E00008)|(YQ9976)|(YQ9989)$");
		}

		/**
		 * 半死的响应码(银行内部程序处理时发生异常, 但是不代表交易时完全失败的)
		 * 
		 * @param respCode
		 * @return
		 */
		public static boolean isHalfDead(String respCode) {
			return respCode.matches("^(YQ9999)|(EBLN00)$");
		}
		
		/**
		 * 银行服务繁忙(系统忙，请稍后再提交)
		 * 
		 * @param respCode
		 * @return
		 */
		public static boolean isBankBusy(String respCode) {
			return respCode.matches("^(MA9112)|(SC6011)|(YQ9971)$");
		}
		
		/**
		 * 异常手续费[YQ9982-交易暂停, YQ9984-收款方户名输入错误]
		 * 
		 * @param respCode
		 * @return
		 */
		public static boolean isAbnormalFee(String respCode) {
			return respCode.matches("^(YQ9982)|(YQ9984)|(YQ9974)|(YQ9978)$");
		}
	}

	/**
	 * 钞汇标志 C 钞户, R汇户,默认为C
	 */
	public enum CcyType implements IBankAdapterEnum {
		CASH("C"), RATE("R");

		private String value;

		public String getValue() {
			return this.value;
		}

		private CcyType(String value) {
			this.value = value;
		}

		@Override
		public IBankAdapterEnum[] allValues() {
			return values();
		}
	}

	/**
	 * 货币类型 RMB 人民币,USD 美元，HKD 港币, 默认为RMB
	 */
	public enum CcyCode implements IBankAdapterEnum {
		RMB("RMB"), USD("USD"), HKD("HKD");

		private String value;

		public String getValue() {
			return this.value;
		}

		private CcyCode(String value) {
			this.value = value;
		}

		@Override
		public IBankAdapterEnum[] allValues() {
			return values();
		}
	}

	/**
	 * 数据结束标志
	 */
	public enum IsEnded {
		YES("Y"), NO("N");

		private String value;

		public String getValue() {
			return this.value;
		}

		private IsEnded(String value) {
			this.value = value;
		}
	}

	/**
	 * 报文类别编号 （报文命令字）
	 */
	public enum MsgCmdNO {
		DEFAULT("A00101"), ;

		private String value;

		public String getValue() {
			return this.value;
		}

		private MsgCmdNO(String value) {
			this.value = value;
		}
	}

	/**
	 * 报文编码 (报文字符集) 01：GBK缺省 02：UTF8 03：unicode 04：iso-8859-1 建议使用GBK编码
	 */
	public enum MsgCharSet {
		GBK("01"), UTF_8("02"), UNICODE("03"), ISO_8859_1("04");

		private String value;

		public String getValue() {
			return this.value;
		}

		private MsgCharSet(String value) {
			this.value = value;
		}
	}

	/**
	 * 通讯方式（通讯协议）01:tcpip 缺省 02：http 03：webservice 现在只支持：TCPIP接入
	 */
	public enum ProtocolType {
		TCPIP("01"), HTTP("02"), WEBSERVICE("03");

		private String value;

		public String getValue() {
			return this.value;
		}

		private ProtocolType(String value) {
			this.value = value;
		}
	}

	/**
	 * 服务类型 01请求 02应答
	 */
	public enum ServType {
		REQUEST("01"), RESPONSE("02");

		private String value;

		public String getValue() {
			return this.value;
		}

		private ServType(String value) {
			this.value = value;
		}
	}

	/**
	 * 后续包标志 0-结束包，1-还有后续包 同请求方流水号一起运作 请求方系统流水号要和第一次保持一致
	 */
	public enum NextPackageFlag {
		ISLAST("0"), ISNOTLAST("1");

		private String value;

		public String getValue() {
			return this.value;
		}

		private NextPackageFlag(String value) {
			this.value = value;
		}
	}

	/**
	 * 签名标识 0-不签名 1-签名 （填0，企业不管，由银行客户端完成）
	 */
	public enum IsSignatured {
		NO("0"), YES("1");

		private String value;

		public String getValue() {
			return this.value;
		}

		private IsSignatured(String value) {
			this.value = value;
		}
	}

	/**
	 * 签名数据包格式 0-裸签（填1，企业不管，由银行客户端完成） 1-PKCS7
	 */
	public enum SignatureDataPacketType {
		NAKED("0"), PKCS7("1");

		private String value;

		public String getValue() {
			return this.value;
		}

		private SignatureDataPacketType(String value) {
			this.value = value;
		}
	}

	/**
	 * 签名算法 RSA-SHA1
	 */
	public enum SignatureAlgorithm {
		DEFAULT_BLANK("            "), RSA("RSA-SHA1    ");

		private String value;

		public String getValue() {
			return this.value;
		}

		private SignatureAlgorithm(String value) {
			this.value = value;
		}
	}

	/**
	 * 获取文件方式 0:流 缺省 1：文件系统 2：FTP服务器 3：http下载
	 */
	public enum GetFileMethod {
		DEFAULT_IS_STREAM("0"), FILESYSTEM("1"), FTP("2"), HTTP("3");

		private String value;

		public String getValue() {
			return this.value;
		}

		private GetFileMethod(String value) {
			this.value = value;
		}
	}

	/**
	 * 交易码
	 */
	public enum TradeCode {
		// 系统探测
		DetectionSysReq("S001  "),
		// 企业账户余额查询
		AccountBalanceReq("4001  "),
		// 查询账户历史交易明细
		TradeDetailReq("4013  "),
		// 企业大批量资金划转
		MaxBatchTransfer("4018  "),
		// 批量转账指令查询
		QryMaxBatchTransfer("4015  "),
		// 银行联行号查询
		QryBankNodeCode("4017"),
		// 企业单笔资金划转
		SingleTransfer("4004  "),
		// 企业单笔资金划转状态查询
		QrySingleTransfer("4005  ");

		private String value;

		public String getValue() {
			return this.value;
		}

		private TradeCode(String value) {
			this.value = value;
		}
	}

	/**
	 * 整批转账加急标志 Y：加急 N：不加急 S：特急（汇总扣款模式不支持）
	 */
	public enum BSysFlag implements IBankAdapterEnum {
		NO("N"), YES("Y"), SPECITAL("S");

		private String value;

		public String getValue() {
			return this.value;
		}

		private BSysFlag(String value) {
			this.value = value;
		}

		@Override
		public IBankAdapterEnum[] allValues() {
			return values();
		}
	}

	/**
	 * 实时或落地标志(‘1’落地, ‘2’实时, 此字段赋值为2无意义，为2时，系统会判断是否应该落地；为1则直接落地处理。 默认为2)
	 */
	public enum RealFlag implements IBankAdapterEnum {
		TO_GROUND("1"), REAL_TIME("2");

		private String value;

		public String getValue() {
			return this.value;
		}

		private RealFlag(String value) {
			this.value = value;
		}

		@Override
		public IBankAdapterEnum[] allValues() {
			return values();
		}
	}

	/**
	 * 扣款类型 0：单笔扣划1：汇总扣划
	 */
	public enum PayType implements IBankAdapterEnum {
		// 单笔扣划
		SINGLE_PAY("0"),
		// 汇总扣划
		TOGETER_PAY("1");

		private String value;

		public String getValue() {
			return this.value;
		}

		private PayType(String value) {
			this.value = value;
		}

		@Override
		public IBankAdapterEnum[] allValues() {
			return values();
		}
	}

	/**
	 * 行内跨行标志 1：行内转账，0：跨行转账
	 */
	public enum UnionFlag implements IBankAdapterEnum {
		// 行内转账
		SAME_BANK("1"),
		// 跨行转账
		OTHER_BANK("0");

		private String value;

		public String getValue() {
			return this.value;
		}

		private UnionFlag(String value) {
			this.value = value;
		}

		@Override
		public IBankAdapterEnum[] allValues() {
			return values();
		}
	}

	/**
	 * 同城/异地标志“1”—同城 “2”—异地
	 */
	public enum AddrFlag implements IBankAdapterEnum {
		// 同城
		SAME_CITY("1"),
		// 异地
		OTHER_CITY("2");

		private String value;

		public String getValue() {
			return this.value;
		}

		private AddrFlag(String value) {
			this.value = value;
		}

		@Override
		public IBankAdapterEnum[] allValues() {
			return values();
		}
	}

	/**
	 * 查询类型 0或者空：全部；1:成功2:失败3:处理中4:退票
	 */
	public enum QueryType {
		// 全部
		ALL("0"),
		// 成功
		SUCCESS("1"),
		// 失败
		FAIL("2"),
		// 失败
		WORKING("3"),
		// 退票
		TICKET_BACK("4");

		private String value;

		public String getValue() {
			return this.value;
		}

		private QueryType(String value) {
			this.value = value;
		}
	}

	/**
	 * 记录结束标志 Y:无剩余记录 N:有剩余记录 符合当前查询条件的记录是否查询结束
	 */
	public enum IsEnd {
		// 无剩余记录
		YES("Y"),
		// 有剩余记录
		NO("N");

		private String value;

		public String getValue() {
			return this.value;
		}

		private IsEnd(String value) {
			this.value = value;
		}

		public boolean valueEquals(String anotherValue) {
			return this.value.equals(anotherValue);
		}
	}

	/**
	 * 转账退票标志, C(1), 必输 0:未退票; 1:退票;
	 */
	public enum IsBack {
		// 1:退票;
		YES("1"),
		// 0:未退票
		NO("0");

		private String value;

		public String getValue() {
			return this.value;
		}

		private IsBack(String value) {
			this.value = value;
		}

		public boolean valueEquals(String anotherValue) {
			return this.value.equals(anotherValue);
		}
	}

	/**
	 * 交易状态标志, C(2), 必输, 20：成功, 30：失败, 99:银行内部处理错误, 其他为银行受理成功处理中
	 */
	public enum Stt {
		// 20：成功
		SUCCESS("20"),
		// 30：失败
		FAILED("30"),
		// 99：银行内部通信故障或交易错误
		BANK_ERROR("99");

		private String value;

		public String getValue() {
			return this.value;
		}

		private Stt(String value) {
			this.value = value;
		}

		public boolean valueEquals(String anotherValue) {
			return this.value.equals(anotherValue);
		}
	}
	
	/**
	 * 批量转现状态, 20-成功, 30-失败, 0-为银行受理成功处理中
	 */
	public enum BatchStt {
		SUCESS(20), FAILED(30), PROCCESSING(0);

		private int value;

		public int getValue() {
			return this.value;
		}

		private BatchStt(int value) {
			this.value = value;
		}
	}
}
