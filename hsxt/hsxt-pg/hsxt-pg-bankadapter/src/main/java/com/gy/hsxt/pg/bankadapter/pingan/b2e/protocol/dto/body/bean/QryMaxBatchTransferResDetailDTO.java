package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

/**
 * @author jbli
 *
 */
public class QryMaxBatchTransferResDetailDTO {
	// 银行流水号 C(14) 必输
	private String frontLogNo;
	// 单笔转账凭证号(批次中的流水号) C(20) 非必输 若上送，则返回按递增排序
	private String sThirdVoucher;
	// 客户自定义凭证号 C(20) 非必输 若客户上送则返回
	private String cstInnerFlowNo;
	// 货币类型 C(3) 必输
	private String ccyCode;
	// 转出账户开户网点名 C(60) 非必输
	private String outAcctBankName;
	// 转出账户 C(14) 必输
	private String outAcctNo;
	// 转入账户网点名称 C(60) 非必输
	private String inAcctBankName;
	// 转入账户 C(32) 必输
	private String inAcctNo;
	// 转入账户户名 C(60) 必输
	private String inAcctName;
	// 交易金额 C(13) 必输
	private String tranAmount;
	// 行内跨行标志 C(1) 必输 1：行内转账，0：跨行转账
	private String unionFlag;
	// 交易状态标志 C(2) 必输 20：成功30：失败其他为银行受理成功处理中
	private String stt;
	// 转账退票标志 C(1) 必输 0:未退票;1:退票
	private String isBack;
	// 支付失败、退票原因 C(20) 非必输
	private String backRem;
	// 银行处理结果 C(40) 必输 格式为：“六位代码:中文描述”。冒号为半角。如： 000000：内部转账交易成功 MA0103:没有满足条件纪录；
	private String yhcljg;
	// 转账加急标志 C(1) 必输 ‘1’—大额 ，等同Y ‘2’—小额”等同N Y：加急 N：不加急S：特急
	private String sysFlag;
	// 银行手续费 C(13) 转账手续费预算，实际手续费用以实际扣取的为准。
	private String fee;
	// 转账代码类型 C(4) 必输 4004：单笔转账；4014：单笔批量；4018：大批量转账4034：汇总批量
	private String transBsn;
	// 交易受理时间 C(14) 非必输 交易受理时间
	private String submitTime;
	// 记账日期 C(8) 非必输 主机记账日期
	private String accountDate;

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public String getsThirdVoucher() {
		return sThirdVoucher;
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

	private QryMaxBatchTransferResDetailDTO(Builder builder){
		this.frontLogNo = builder.frontLogNo;
		this.sThirdVoucher = builder.sThirdVoucher;
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
	
	public static class Builder{
		// 银行流水号 C(14) 必输
		private String frontLogNo = "";
		// 单笔转账凭证号(批次中的流水号) C(20) 非必输 若上送，则返回按递增排序
		private String sThirdVoucher = "";
		// 客户自定义凭证号 C(20) 非必输 若客户上送则返回
		private String cstInnerFlowNo = "";
		// 货币类型 C(3) 必输
		private String ccyCode  = "";
		// 转出账户开户网点名 C(60) 非必输
		private String outAcctBankName = "";
		// 转出账户 C(14) 必输
		private String outAcctNo = "";
		// 转入账户网点名称 C(60) 非必输
		private String inAcctBankName = "";
		// 转入账户 C(32) 必输
		private String inAcctNo = "";
		// 转入账户户名 C(60) 必输
		private String inAcctName = "";
		// 交易金额 C(13) 必输
		private String tranAmount = "";
		// 行内跨行标志 C(1) 必输 1：行内转账，0：跨行转账
		private String unionFlag = "";
		// 交易状态标志 C(2) 必输 20：成功30：失败其他为银行受理成功处理中
		private String stt = "";
		// 转账退票标志 C(1) 必输 0:未退票;1:退票
		private String isBack = "";
		// 支付失败、退票原因 C(20) 非必输
		private String backRem = "";
		// 银行处理结果 C(40) 必输 格式为：“六位代码:中文描述”。冒号为半角。如： 000000：内部转账交易成功 MA0103:没有满足条件纪录；
		private String yhcljg = "";
		// 转账加急标志 C(1) 必输 ‘1’—大额 ，等同Y ‘2’—小额”等同N Y：加急 N：不加急S：特急
		private String sysFlag = "";
		// 银行手续费 C(13) 转账手续费预算，实际手续费用以实际扣取的为准。
		private String fee = "";
		// 转账代码类型 C(4) 必输 4004：单笔转账；4014：单笔批量；4018：大批量转账4034：汇总批量
		private String transBsn = "";
		// 交易受理时间 C(14) 非必输 交易受理时间
		private String submitTime = "";
		// 记账日期 C(8) 非必输 主机记账日期
		private String accountDate = "";
		
		
		public Builder setFrontLogNo(String frontLogNo) {
			this.frontLogNo = frontLogNo;
			return this;
		}

		public Builder setsThirdVoucher(String sThirdVoucher) {
			this.sThirdVoucher = sThirdVoucher;
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
		
		public QryMaxBatchTransferResDetailDTO build(){
			return new QryMaxBatchTransferResDetailDTO(this);
		}

	}

}
