package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author huke.zhang
 * 
 * '企业单笔资金划转[4004]'的响应结果对象
 */
@Component("bodyRes_4004")
public class SingleTransferResDTO extends BodyDTO {

	public SingleTransferResDTO() {
	}

	// 转账凭证号, C(20) 必输 同上送
	private String thirdVoucher;
	// 银行流水号, C(14) 必输 银行生成的转账流水
	private String frontLogNo;
	// 客户自定义凭证号, C(20) 非必输, 用于客户转账登记和内部识别，通过转账结果查询可以返回。银行不检查唯一性
	private String cstInnerFlowNo;
	// 货币类型, C(3) 必输
	private String ccyCode;
	// 付款人账户名称, C(60) 必输
	private String outAcctName;
	// 付款人账户, C(14) 必输
	private String outAcctNo;
	// 收款人开户行名称, C(60) 必输
	private String inAcctBankName;
	// 收款人账户, C(32) 必输
	private String inAcctNo;
	// 收款人账户户名, C(60) 必输
	private String inAcctName;
	// 交易金额, C(13) 必输
	private String tranAmount;
	// 行内跨行标志, C(1) 必输 1：行内转账，0：跨行转账
	private String unionFlag;
	// 手续费, C(13) 必输 转账手续费预算，实际手续费用以实际扣取的为准。
	private String fee1;
	// 邮电费, C(13) 非必输
	private String fee2;
	// 银行返回传票号, C(20) 转账成功后，银行返回传票号，等同交易明细返回的传票号。
	private String soaVoucher;
	// 银行返回流水号, C(10) 转账成功后，银行返回的流水号。
	private String hostFlowNo;
	// 转账短信通知手机号码, C(0) 格式为：“13412341123,12312341234”，多个手机号码之间使用半角逗号分隔.
	private String mobile;

	private SingleTransferResDTO(Builder builder) {
		this.thirdVoucher = builder.thirdVoucher;
		this.frontLogNo = builder.frontLogNo;
		this.cstInnerFlowNo = builder.cstInnerFlowNo;
		this.ccyCode = builder.ccyCode;
		this.outAcctName = builder.outAcctName;
		this.outAcctNo = builder.outAcctNo;
		this.inAcctBankName = builder.inAcctBankName;
		this.inAcctNo = builder.inAcctNo;
		this.inAcctName = builder.inAcctName;
		this.tranAmount = builder.tranAmount;
		this.unionFlag = builder.unionFlag;
		this.fee1 = builder.fee1;
		this.fee2 = builder.fee2;
		this.soaVoucher = builder.soaVoucher;
		this.hostFlowNo = builder.hostFlowNo;
		this.mobile = builder.mobile;
	}

	public String getThirdVoucher() {
		return thirdVoucher;
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

	public String getOutAcctName() {
		return outAcctName;
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

	public String getFee1() {
		return fee1;
	}

	public String getFee2() {
		return fee2;
	}

	public String getSoaVoucher() {
		return soaVoucher;
	}

	public String getHostFlowNo() {
		return hostFlowNo;
	}

	public String getMobile() {
		return mobile;
	}

	public static class Builder {
		public Builder() {
		}

		// 转账凭证号, C(20) 必输 同上送
		private String thirdVoucher;
		// 银行流水号, C(14) 必输 银行生成的转账流水
		private String frontLogNo;
		// 客户自定义凭证号, C(20) 非必输, 用于客户转账登记和内部识别，通过转账结果查询可以返回。银行不检查唯一性
		private String cstInnerFlowNo;
		// 货币类型, C(3) 必输
		private String ccyCode;
		// 付款人账户名称, C(60) 必输
		private String outAcctName;
		// 付款人账户, C(14) 必输
		private String outAcctNo;
		// 收款人开户行名称, C(60) 必输
		private String inAcctBankName;
		// 收款人账户, C(32) 必输
		private String inAcctNo;
		// 收款人账户户名, C(60) 必输
		private String inAcctName;
		// 交易金额, C(13) 必输
		private String tranAmount;
		// 行内跨行标志, C(1) 必输 1：行内转账，0：跨行转账
		private String unionFlag;
		// 手续费, C(13) 必输 转账手续费预算，实际手续费用以实际扣取的为准。
		private String fee1;
		// 邮电费, C(13) 非必输
		private String fee2;
		// 银行返回传票号, C(20) 转账成功后，银行返回传票号，等同交易明细返回的传票号。
		private String soaVoucher;
		// 银行返回流水号, C(10) 转账成功后，银行返回的流水号。
		private String hostFlowNo;
		// 转账短信通知手机号码, C(0) 格式为：“13412341123,12312341234”，多个手机号码之间使用半角逗号分隔.
		private String mobile;

		public Builder setThirdVoucher(String thirdVoucher) {
			this.thirdVoucher = thirdVoucher;
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

		public Builder setOutAcctName(String outAcctName) {
			this.outAcctName = outAcctName;
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

		public Builder setFee1(String fee1) {
			this.fee1 = fee1;
			return this;
		}

		public Builder setFee2(String fee2) {
			this.fee2 = fee2;
			return this;
		}

		public Builder setSoaVoucher(String soaVoucher) {
			this.soaVoucher = soaVoucher;
			return this;
		}

		public Builder setHostFlowNo(String hostFlowNo) {
			this.hostFlowNo = hostFlowNo;
			return this;
		}

		public Builder setMobile(String mobile) {
			this.mobile = mobile;
			return this;
		}

		/**
		 * 返回一个不带任何默认值的实例引用，用于解码
		 * 
		 * @return
		 */
		public SingleTransferResDTO build() {
			return new SingleTransferResDTO(this);
		}
	}

	@Override
	public String obj2xml() {
		this.preHandleAliasFields();

		String xml = PackageConstants.XML_SCHEMA_TITLE + xStream.toXML(this);
		
		return this.reFormatXmlStr(xml);
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
		xStream.alias("Result", SingleTransferResDTO.class);

		xStream.aliasField("ThirdVoucher", SingleTransferResDTO.class, "thirdVoucher");
		xStream.aliasField("FrontLogNo", SingleTransferResDTO.class, "frontLogNo");
		xStream.aliasField("CstInnerFlowNo", SingleTransferResDTO.class, "cstInnerFlowNo");
		xStream.aliasField("CcyCode", SingleTransferResDTO.class, "ccyCode");
		xStream.aliasField("OutAcctName", SingleTransferResDTO.class, "outAcctName");
		xStream.aliasField("OutAcctNo", SingleTransferResDTO.class, "outAcctNo");
		xStream.aliasField("InAcctBankName", SingleTransferResDTO.class, "inAcctBankName");
		xStream.aliasField("InAcctNo", SingleTransferResDTO.class, "inAcctNo");
		xStream.aliasField("InAcctName", SingleTransferResDTO.class, "inAcctName");
		xStream.aliasField("TranAmount", SingleTransferResDTO.class, "tranAmount");
		xStream.aliasField("UnionFlag", SingleTransferResDTO.class, "unionFlag");
		xStream.aliasField("Fee1", SingleTransferResDTO.class, "fee1");
		xStream.aliasField("Fee2", SingleTransferResDTO.class, "fee2");
		xStream.aliasField("SOA_VOUCHER", SingleTransferResDTO.class, "soaVoucher");
		xStream.aliasField("hostFlowNo", SingleTransferResDTO.class, "hostFlowNo");
		xStream.aliasField("Mobile", SingleTransferResDTO.class, "mobile");
	}
	
	private String reFormatXmlStr(String xml) {
		// 规避xStream bug
		return xml.replaceAll("SOA__VOUCHER>", "SOA_VOUCHER>");
	}
	
	public static void main(String[] args) {
		SingleTransferResDTO.Builder build = new SingleTransferResDTO.Builder();
		
		build.setCcyCode("1");
		build.setFee1("0.05");
		build.setFrontLogNo("001");
		build.setSoaVoucher("soaVoucher");
		build.setHostFlowNo("232322");
		
		System.out.println(build.build().obj2xml());
	}
}
