package com.gy.hsxt.pg.bankadapter.common.constants;

/**
 * @author jbli 保存各个银行相关的一些公用常量
 */
public class BanksConstants {

	/** 编码 **/
	public static final String DEFAULT_CHARSET = "UTF-8";
		
	/** ISO-8859-1编码 **/
	public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";
	
	/** 银行错误信息代码特征符 */
	public static final String BANK_ERRCODE_SYMBOL = "#BANK_ERRCODE_SYMBOL#";
	
	/** 银行错误信息代码特征符 */
	public static final String BANK_ERRMSG_SYMBOL = "#BANK_ERRMSG_SYMBOL#";
	
	/** 错误枚举值 **/
	public static enum ErrorCode {
		/** 5000：参数无效 **/
		INVALID_PARAM(5000),
		/** 5001：处理失败 **/
		FAILED(5001),
		/** 5100：记录已存在 **/
		DATA_EXIST(5100),
		/** 5101：记录不存在 **/
		DATA_NOT_EXIST(5101),
		/** 5102：访问过于频繁 **/
		TOO_FREQUENT(5102),
		/** 5103：已经处理 **/
		DUPLICATE_SUBMIT(5103),
		/** 5104：空报文异常, 这种情况需要重新发起查询, 以确认是否已经提交到银行 **/
		RESP_PACKET_ABNORMAL(5104),
		/** 5105：校验签名失败 **/
		FAILED_CHECK_SIGN(5105),
		/** 5106：银行内部程序故障 **/
		BANK_PROGRAM_FAULT(5106),
		/** 5107：银行内部通信异常 **/
		BANK_COMM_ABNORMAL(5107),
		/** 5108：银行系统繁忙 **/
		BANK_SYSTEM_BUSY(5108),
		/** 5109：银行交易异常扣手续费, 即:虽然操作失败,但是仍然有潜在扣手续费风险 **/
		BANK_ABNORMAL_FEE(5109),
		/** 5999： **/
		SOCKET_ERROR(5999);

		private int value;

		ErrorCode(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
		public boolean valueEquals(int value) {
			return (this.value == value);
		}
	}

	/** 银行缩写英文名称 pingan-平安银行, boc—中国银行, abc-农业银行, cmb-招商银行, icbc-工商银行, ccb-建设银行 */
	public enum BankName {
		// 平安银行
		pingan("pingan"),
		// 中国银行
		boc("boc"),
		// 农业银行
		abc("abc"),
		// 招商银行
		cmb("cmb"),
		// 工商银行
		icbc("icbc"),
		// 建设银行
		ccb("ccb");

		private String value;

		public String getValue() {
			return this.value;
		}

		private BankName(String value) {
			this.value = value;
		}
	}
}
