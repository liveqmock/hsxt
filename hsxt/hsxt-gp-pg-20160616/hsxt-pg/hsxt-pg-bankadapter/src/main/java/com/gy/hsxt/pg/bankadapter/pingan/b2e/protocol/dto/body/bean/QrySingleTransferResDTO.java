package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author huke.zhang
 * 
 * '单笔转账指令查询[4005]'的响应结果
 */
@Component("bodyRes_4005")
public class QrySingleTransferResDTO extends BodyDTO {

	public QrySingleTransferResDTO() {
	}
	
	// 转账凭证号, C(20) 必输
	private String origThirdVoucher;
	// 银行流水号, C(14) 必输
	private String frontLogNo;
	// 客户自定义凭证号, C(20) 非必输 客户上送则返回
	private String cstInnerFlowNo;
	// 货币类型, C(3) 必输
	private String ccyCode;
	// 转出账户开户网点名, C(60) 非必输
	private String outAcctBankName;
	// 转出账户, C(14) 必输
	private String outAcctNo;
	// 转入账户网点名称, C(60) 非必输
	private String inAcctBankName;
	// 转入账户, C(32) 必输
	private String inAcctNo;
	// 转入账户户名, C(60) 必输
	private String inAcctName;
	// 交易金额, C(13) 必输
	private String tranAmount;
	// 行内跨行标志, C(1) 必输 1：行内转账，0：跨行转账
	private String unionFlag;
	// 交易状态标志, C(2) 必输 20：成功,30：失败, 其他为银行受理成功处理中
	private String stt;
	// 转账退票标志, C(1) 非必输 0:未退票; 默认为0, 1:退票;
	private String isBack;
	// 支付失败或退票原因描述, C(20) 非必输 如果是超级网银则返回如下信息: RJ01对方返回：账号不存在,RJ02对方返回：账号、户名不符,
	// 大小额支付则返回失败描述
	private String backRem;
	// 银行处理结果, C(40) 必输 格式为：“六位代码:中文描述”。冒号为半角。如：000000：转账成功,处理中的返回(以如下返回开头)：
	// MA9111:交易正在受理中
	// 000000:交易受理成功待处理
	// 000000:交易处理中
	// 000000:交易受理成功处理中
	// 成功的返回：
	// 000000:转账交易成功
	// 其他的返回都为失败:
	// MA9112:转账失败
	private String yhcljg;
	// 转账加急标志, C(1) 必输 ‘1’—大额 ，等同Y ‘2’—小额”等同NY：加急 N：普通S：特急
	private String sysFlag;
	// 转账手续费, C(13) 必输
	private String fee;
	// 转账代码类型, C(4) 必输 4004：单笔转账；4014：单笔批量；4034：汇总批量
	private String transBsn;
	// 交易受理时间, C(14) 非必输 交易受理时间
	private String submitTime;
	// 记账日期, C(8) 非必输 主机记账日期
	private String accountDate;

	private QrySingleTransferResDTO(Builder builder) {
		this.origThirdVoucher = builder.origThirdVoucher;
		this.frontLogNo = builder.frontLogNo;
		this.cstInnerFlowNo = builder.cstInnerFlowNo;
		this.ccyCode = builder.ccyCode;
		this.outAcctBankName = builder.outAcctBankName;
		this.outAcctNo = builder.outAcctNo;
		this.inAcctBankName = builder.inAcctBankName;
		this.inAcctNo = builder.inAcctNo;
		this.inAcctName = builder.inAcctName;
		this.tranAmount = builder.tranAmount;
		this.unionFlag = builder.unionFlag;
		this.stt = builder.stt;
		this.isBack = builder.isBack;
		this.backRem = builder.backRem;
		this.yhcljg = builder.yhcljg;
		this.sysFlag = builder.sysFlag;
		this.fee = builder.fee;
		this.transBsn = builder.transBsn;
		this.submitTime = builder.submitTime;
		this.accountDate = builder.accountDate;
	}

	public String getOrigThirdVoucher() {
		return origThirdVoucher;
	}

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public String getCstInnerFlowNo() {
		return cstInnerFlowNo;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public String getOutAcctBankName() {
		return outAcctBankName;
	}

	public String getOutAcctNo() {
		return outAcctNo;
	}

	public String getInAcctBankName() {
		return inAcctBankName;
	}

	public String getInAcctNo() {
		return inAcctNo;
	}

	public String getInAcctName() {
		return inAcctName;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public String getUnionFlag() {
		return unionFlag;
	}

	public String getStt() {
		return stt;
	}

	public String getIsBack() {
		return isBack;
	}

	public String getBackRem() {
		return backRem;
	}

	public String getYhcljg() {
		return yhcljg;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public String getFee() {
		return fee;
	}

	public String getTransBsn() {
		return transBsn;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public static class Builder {
		public Builder() {
		}

		// 转账凭证号, C(20) 必输
		private String origThirdVoucher;
		// 银行流水号, C(14) 必输
		private String frontLogNo;
		// 客户自定义凭证号, C(20) 非必输 客户上送则返回
		private String cstInnerFlowNo;
		// 货币类型, C(3) 必输
		private String ccyCode;
		// 转出账户开户网点名, C(60) 非必输
		private String outAcctBankName;
		// 转出账户, C(14) 必输
		private String outAcctNo;
		// 转入账户网点名称, C(60) 非必输
		private String inAcctBankName;
		// 转入账户, C(32) 必输
		private String inAcctNo;
		// 转入账户户名, C(60) 必输
		private String inAcctName;
		// 交易金额, C(13) 必输
		private String tranAmount;
		// 行内跨行标志, C(1) 必输 1：行内转账，0：跨行转账
		private String unionFlag;
		// 交易状态标志, C(2) 必输 20：成功,30：失败, 其他为银行受理成功处理中
		private String stt;
		// 转账退票标志, C(1) 非必输 0:未退票; 默认为0, 1:退票;
		private String isBack;
		// 支付失败或退票原因描述, C(20) 非必输 如果是超级网银则返回如下信息: RJ01对方返回：账号不存在,RJ02对方返回：账号、户名不符,
		// 大小额支付则返回失败描述
		private String backRem;
		// 银行处理结果, C(40) 必输 格式为：“六位代码:中文描述”。冒号为半角。如：000000：转账成功,处理中的返回(以如下返回开头)：
		// MA9111:交易正在受理中
		// 000000:交易受理成功待处理
		// 000000:交易处理中
		// 000000:交易受理成功处理中
		// 成功的返回：
		// 000000:转账交易成功
		// 其他的返回都为失败:
		// MA9112:转账失败
		private String yhcljg;
		// 转账加急标志, C(1) 必输 ‘1’—大额 ，等同Y ‘2’—小额”等同NY：加急 N：普通S：特急
		private String sysFlag;
		// 转账手续费, C(13) 必输
		private String fee;
		// 转账代码类型, C(4) 必输 4004：单笔转账；4014：单笔批量；4034：汇总批量
		private String transBsn;
		// 交易受理时间, C(14) 非必输 交易受理时间
		private String submitTime;
		// 记账日期, C(8) 非必输 主机记账日期
		private String accountDate;

		public Builder setOrigThirdVoucher(String origThirdVoucher) {
			this.origThirdVoucher = origThirdVoucher;
			return this;
		}

		public Builder setFrontLogNo(String frontLogNo) {
			this.frontLogNo = frontLogNo;
			return this;
		}

		public Builder setCstInnerFlowNo(String cstInnerFlowNo) {
			this.cstInnerFlowNo = cstInnerFlowNo;
			return this;
		}

		public Builder setCcyCode(String ccyCode) {
			this.ccyCode = ccyCode;
			return this;
		}

		public Builder setOutAcctBankName(String outAcctBankName) {
			this.outAcctBankName = outAcctBankName;
			return this;
		}

		public Builder setOutAcctNo(String outAcctNo) {
			this.outAcctNo = outAcctNo;
			return this;
		}

		public Builder setInAcctBankName(String inAcctBankName) {
			this.inAcctBankName = inAcctBankName;
			return this;
		}

		public Builder setInAcctNo(String inAcctNo) {
			this.inAcctNo = inAcctNo;
			return this;
		}

		public Builder setInAcctName(String inAcctName) {
			this.inAcctName = inAcctName;
			return this;
		}

		public Builder setTranAmount(String tranAmount) {
			this.tranAmount = tranAmount;
			return this;
		}

		public Builder setUnionFlag(String unionFlag) {
			this.unionFlag = unionFlag;
			return this;
		}

		public Builder setStt(String stt) {
			this.stt = stt;
			return this;
		}

		public Builder setIsBack(String isBack) {
			this.isBack = isBack;
			return this;
		}

		public Builder setBackRem(String backRem) {
			this.backRem = backRem;
			return this;
		}

		public Builder setYhcljg(String yhcljg) {
			this.yhcljg = yhcljg;
			return this;
		}

		public Builder setSysFlag(String sysFlag) {
			this.sysFlag = sysFlag;
			return this;
		}

		public Builder setFee(String fee) {
			this.fee = fee;
			return this;
		}

		public Builder setTransBsn(String transBsn) {
			this.transBsn = transBsn;
			return this;
		}

		public Builder setSubmitTime(String submitTime) {
			this.submitTime = submitTime;
			return this;
		}

		public Builder setAccountDate(String accountDate) {
			this.accountDate = accountDate;
			return this;
		}

		/**
		 * 返回一个不带任何默认值的实例引用，用于解码
		 * 
		 * @return
		 */
		public QrySingleTransferResDTO build() {
			return new QrySingleTransferResDTO(this);
		}
	}

	@Override
	public String obj2xml() {
		this.preHandleAliasFields();

		return PackageConstants.XML_SCHEMA_TITLE + xStream.toXML(this);
	}

	@Override
	public Object xml2obj(String strXml) {
		if (StringUtils.isEmpty(strXml)) {
			return null;
		}

		this.preHandleAliasFields();

		return xStream.fromXML(strXml);
	}

	/**
	 * 字段名称预处理
	 */
	private void preHandleAliasFields() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", QrySingleTransferResDTO.class);

		xStream.aliasField("OrigThirdVoucher", QrySingleTransferResDTO.class, "origThirdVoucher");
		xStream.aliasField("FrontLogNo", QrySingleTransferResDTO.class, "frontLogNo");
		xStream.aliasField("CstInnerFlowNo", QrySingleTransferResDTO.class, "cstInnerFlowNo");
		xStream.aliasField("CcyCode", QrySingleTransferResDTO.class, "ccyCode");
		xStream.aliasField("OutAcctBankName", QrySingleTransferResDTO.class, "outAcctBankName");
		xStream.aliasField("OutAcctNo", QrySingleTransferResDTO.class, "outAcctNo");
		xStream.aliasField("InAcctBankName", QrySingleTransferResDTO.class, "inAcctBankName");
		xStream.aliasField("InAcctNo", QrySingleTransferResDTO.class, "inAcctNo");
		xStream.aliasField("InAcctName", QrySingleTransferResDTO.class, "inAcctName");
		xStream.aliasField("TranAmount", QrySingleTransferResDTO.class, "tranAmount");
		xStream.aliasField("UnionFlag", QrySingleTransferResDTO.class, "unionFlag");
		xStream.aliasField("Stt", QrySingleTransferResDTO.class, "stt");
		xStream.aliasField("IsBack", QrySingleTransferResDTO.class, "isBack");
		xStream.aliasField("BackRem", QrySingleTransferResDTO.class, "backRem");
		xStream.aliasField("Yhcljg", QrySingleTransferResDTO.class, "yhcljg");
		xStream.aliasField("SysFlag", QrySingleTransferResDTO.class, "sysFlag");
		xStream.aliasField("Fee", QrySingleTransferResDTO.class, "fee");
		xStream.aliasField("TransBsn", QrySingleTransferResDTO.class, "transBsn");
		xStream.aliasField("submitTime", QrySingleTransferResDTO.class, "submitTime");
		xStream.aliasField("AccountDate", QrySingleTransferResDTO.class, "accountDate");
	}
	
	public static void main(String[] args) {
		QrySingleTransferResDTO.Builder build = new QrySingleTransferResDTO.Builder();
		
		build.setCcyCode("1");
		build.setCstInnerFlowNo("0.05");
		build.setFrontLogNo("001");
		build.setOrigThirdVoucher("soaVoucher");
		build.setIsBack("232322");
		
		System.out.println(build.build().obj2xml());
	}
}
