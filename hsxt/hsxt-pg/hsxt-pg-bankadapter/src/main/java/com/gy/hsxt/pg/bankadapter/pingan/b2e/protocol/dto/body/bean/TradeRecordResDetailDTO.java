package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;


/**
 * @author jbli
 * 
 */
public class TradeRecordResDetailDTO {
	
	// 主机记账日期
	private String acctDate;
	// 交易时间
	private String txTime;
	// 主机流水号
	private String hostTrace;
	// 付款方网点号
	private String outNode;
	// 付款方联行号
	private String outBankNo;
	// 付款行名称
	private String outBankName;
	// 付款方账号
	private String outAcctNo;
	// 付款方户名
	private String outAcctName;
	// 交易金额
	private String tranAmount;
	// 收款方网点号
	private String inNode;
	// 收款方联行号
	private String inBankNo;
	// 收款方行名
	private String inBankName;
	// 收款方账号
	private String inAcctNo;
	// 收款方户名
	private String inAcctName;
	// 借贷标志
	private String dcFlag;
	// 摘要，未翻译的摘要，如TRS
	private String abstractStr;
	// 凭证号
	private String voucherNo;
	// 手续费
	private String tranFee;
	// 邮电费
	private String postFee;
	// 账户余额
	private String acctBalance;
	// 用途，附言
	private String purpose;
	// 中文摘要，AbstractStr的中文翻译
	private String bstractStr_Desc;
	// 客户自定义凭证号
	private String cVoucherNo;


	public String getAcctDate() {
		return acctDate;
	}

	public String getTxTime() {
		return txTime;
	}

	public String getHostTrace() {
		return hostTrace;
	}

	public String getOutNode() {
		return outNode;
	}

	public String getOutBankNo() {
		return outBankNo;
	}

	public String getOutBankName() {
		return outBankName;
	}

	public String getOutAcctNo() {
		return outAcctNo;
	}

	public String getOutAcctName() {
		return outAcctName;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public String getInNode() {
		return inNode;
	}

	public String getInBankNo() {
		return inBankNo;
	}

	public String getInBankName() {
		return inBankName;
	}

	public String getInAcctNo() {
		return inAcctNo;
	}

	public String getInAcctName() {
		return inAcctName;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public String getAbstractStr() {
		return abstractStr;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public String getTranFee() {
		return tranFee;
	}

	public String getPostFee() {
		return postFee;
	}

	public String getAcctBalance() {
		return acctBalance;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getBstractStr_Desc() {
		return bstractStr_Desc;
	}

	public String getcVoucherNo() {
		return cVoucherNo;
	}
	
	private TradeRecordResDetailDTO(Builder builder) {
		this.acctDate = builder.acctDate;
		this.txTime = builder.txTime;
		this.hostTrace = builder.hostTrace;
		this.outNode = builder.outNode;
		this.outBankNo = builder.outBankNo;
		this.outBankName = builder.outBankName;
		this.outAcctNo = builder.outAcctNo;
		this.outAcctName = builder.outAcctName;
		this.tranAmount = builder.tranAmount;
		this.inNode = builder.inNode;
		this.inBankNo = builder.inBankNo;
		this.inBankName = builder.inBankName;
		this.inAcctNo = builder.inAcctNo;
		this.inAcctName = builder.inAcctName;
		this.dcFlag = builder.dcFlag;
		this.abstractStr = builder.abstractStr;
		this.voucherNo = builder.voucherNo;
		this.tranFee = builder.tranFee;
		this.postFee = builder.postFee;
		this.acctBalance = builder.acctBalance;
		this.purpose = builder.purpose;
		this.bstractStr_Desc = builder.bstractStr_Desc;
		this.cVoucherNo = builder.cVoucherNo;
	}	
	
	public static class Builder{
		
		// 主机记账日期
		private String acctDate = "";
		// 交易时间
		private String txTime = "";
		// 主机流水号
		private String hostTrace = "";
		// 付款方网点号
		private String outNode = "";
		// 付款方联行号
		private String outBankNo = "";
		// 付款行名称
		private String outBankName = "";
		// 付款方账号
		private String outAcctNo = "";
		// 付款方户名
		private String outAcctName = "";
		// 交易金额
		private String tranAmount = "";
		// 收款方网点号
		private String inNode = "";
		// 收款方联行号
		private String inBankNo = "";
		// 收款方行名
		private String inBankName = "";
		// 收款方账号
		private String inAcctNo = "";
		// 收款方户名
		private String inAcctName = "";
		// 借贷标志
		private String dcFlag = "";
		// 摘要，未翻译的摘要，如TRS
		private String abstractStr = "";
		// 凭证号
		private String voucherNo = "";
		// 手续费
		private String tranFee = "";
		// 邮电费
		private String postFee = "";
		// 账户余额
		private String acctBalance = "";
		// 用途，附言
		private String purpose = "";
		// 中文摘要，AbstractStr的中文翻译
		private String bstractStr_Desc = "";
		// 客户自定义凭证号
		private String cVoucherNo = "";		
		
		public Builder setAcctDate(String acctDate) {
			this.acctDate = acctDate;
			return this;
		}

		public Builder setTxTime(String txTime) {
			this.txTime = txTime;
			return this;
		}

		public Builder setHostTrace(String hostTrace) {
			this.hostTrace = hostTrace;
			return this;
		}

		public Builder setOutNode(String outNode) {
			this.outNode = outNode;
			return this;
		}

		public Builder setOutBankNo(String outBankNo) {
			this.outBankNo = outBankNo;
			return this;
		}

		public Builder setOutBankName(String outBankName) {
			this.outBankName = outBankName;
			return this;
		}

		public Builder setOutAcctNo(String outAcctNo) {
			this.outAcctNo = outAcctNo;
			return this;
		}

		public Builder setOutAcctName(String outAcctName) {
			this.outAcctName = outAcctName;
			return this;
		}

		public Builder setTranAmount(String tranAmount) {
			this.tranAmount = tranAmount;
			return this;
		}

		public Builder setInNode(String inNode) {
			this.inNode = inNode;
			return this;
		}

		public Builder setInBankNo(String inBankNo) {
			this.inBankNo = inBankNo;
			return this;
		}

		public Builder setInBankName(String inBankName) {
			this.inBankName = inBankName;
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

		public Builder setDcFlag(String dcFlag) {
			this.dcFlag = dcFlag;
			return this;
		}

		public Builder setAbstractStr(String abstractStr) {
			this.abstractStr = abstractStr;
			return this;
		}

		public Builder setVoucherNo(String voucherNo) {
			this.voucherNo = voucherNo;
			return this;
		}

		public Builder setTranFee(String tranFee) {
			this.tranFee = tranFee;
			return this;
		}

		public Builder setPostFee(String postFee) {
			this.postFee = postFee;
			return this;
		}

		public Builder setAcctBalance(String acctBalance) {
			this.acctBalance = acctBalance;
			return this;
		}

		public Builder setPurpose(String purpose) {
			this.purpose = purpose;
			return this;
		}

		public Builder setBstractStr_Desc(String bstractStr_Desc) {
			this.bstractStr_Desc = bstractStr_Desc;
			return this;
		}

		public Builder setcVoucherNo(String cVoucherNo) {
			this.cVoucherNo = cVoucherNo;
			return this;
		}		
		
		public TradeRecordResDetailDTO build(){
			return new TradeRecordResDetailDTO(this);
		}		
	}
	
	
}
