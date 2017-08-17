package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author huke.zhang
 * 
 * 企业单笔资金划转[4004]
 * 
 * <p>
 * 支持平安银行人民币账户行内转账（内部户、信用卡除外）、跨行转账、行内转入保证金账户。<br>
 * 转账落地情况：如果上送字段“实时或落地标志”为:
 * <br> 1，则直接落地；如果上送 “实时或落地标志”为空或者为
 * <br> 2，则根据转账金额大于10000000，或者跨行转账未上送 “收款人开户行行号”，则落地处理；
 *   如果企业在银企直连渠道设置单笔或者日累计限额，并且本次付款金额超过任一限额，则拒绝。
 *   </p>
 * <p>
 * <br>转账收费：
 * <br> 1:如果是同一企业下挂账号（包括托管账户）间转账不收手续费；
 * <br> 2：行内转账，系统自动检查收付账户是否为同城异地（上送的无作用）进行收费；
 * <br> 3：跨行转账，系统按照实际转账结果进行收费。
 * </p>
 * <br>行内转账：此接口返回交易成功，表示转账成功，对方实时到账；
 * <br> 跨行转账：此接口返回成功表示银行受理成功，并不表示扣账和到账成功，具体结果需要调用4005查询； 
 * <br>转账结果须调用4005或4015接口查询。
 * 
 * <br>SysFlag转账加急标，使用如下，不同的标志，到账时间不同，手续费也不同。
 */
@Component("bodyReq_4004")  
public class SingleTransferReqDTO  extends BodyDTO {
	
	public SingleTransferReqDTO(){};

	// 转账凭证号, C(20) 必输 标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
	private String thirdVoucher;
	// 客户自定义凭证号, C(20) 非必输 用于客户转账登记和内部识别，通过转账结果查询可以返回。银行不检查唯一性
	private String cstInnerFlowNo;
	// 货币类型, C(3) 必输 RMB-人民币
	private String ccyCode;
	// 付款人账户, C(14) 必输 扣款账户
	private String outAcctNo;
	// 付款人名称, C(60) 必输 付款账户户名
	private String outAcctName;
	// 付款人地址, C(60) 非必输 建议填写付款账户的分行、网点名称
	private String outAcctAddr;
	// 收款人开户行行号, C(12) 非必输 跨行转账建议必输。为人行登记在册的商业银行号
	private String inAcctBankNode;
	// 接收行行号, C(12) 非必输 建议同收款人开户行行号
	private String inAcctRecCode;
	// 收款人账户, C(32) 必输
	private String inAcctNo;
	// 收款人账户户名, C(60) 必输
	private String inAcctName;
	// 收款人开户行名称, C(60) 必输 建议格式：xxx银行
	private String inAcctBankName;
	// 收款账户银行开户省代码或省名称, C(10) 非必输 建议跨行转账输入；对照码参考“附录-省对照表”；也可输入“附录-省对照表”中的省名称。
	private String inAcctProvinceCode;
	// 收款账户开户市, C(12) 非必输 建议跨行转账输入；
	private String inAcctCityName;
	// 转出金额, C(13,2) 必输 如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
	private String tranAmount;
	// 转出金额大写, C(48) 非必须
	private String amountCode;
	// 资金用途, C(30) 非必输
	private String useEx;
	// 行内跨行标志, C(1) 必输 1：行内转账，0：跨行转账
	private String unionFlag;
	// 转账加急标志, C(1) 非必输 ‘1’—大额 ，等同Y, ‘2’—小额”等同N, Y：加急 N：普通S：特急,默认为N
	private String sysFlag;
	// 同城/异地标志, C(1) 必输, “1”—同城 “2”—异地
	private String addrFlag;
	// 实时或落地标志, C(1) 非必输 ‘1’落地，‘2’实时, 此字段赋值为2无意义，为2时，系统会判断是否应该落地；为1则直接落地处理。 默认为2
	private String realFlag;
	// 虚子账户, C(32) 非必须 代理付款：填写虚子账号；代理结算：填写主账号，子账号填写在OutAcctNo域。
	private String mainAcctNo;

	// 转账短信通知手机号码, C(100) 非必须 格式为： “13412341123,12312341234”，
	// 多个手机号码使用半角逗号分隔. 如果为空或者手机号码长度不足11位，就不发送。
	private String mobile;

	// 中登结算类型, C(1) 非必须 转入中登备付金必输：
	// 1：上海登记公司－资金清算业务
	// 2：上海登记公司－网下发行电子化业务
	// 3：深圳登记公司－网上业务
	// 4：深圳登记公司－网下IPO业务
	private String zdJType;
	// 资金类型, C(1) 非必须
	private String zdZType;
	// 备付金账号, C(20) 非必须
	private String zdBAcc;

	private SingleTransferReqDTO(Builder builder) {
		this.thirdVoucher = builder.thirdVoucher;
		this.cstInnerFlowNo = builder.cstInnerFlowNo;
		this.ccyCode = builder.ccyCode;
		this.outAcctNo = builder.outAcctNo;
		this.outAcctName = builder.outAcctName;
		this.outAcctAddr = builder.outAcctAddr;
		this.inAcctBankNode = builder.inAcctBankNode;
		this.inAcctRecCode = builder.inAcctRecCode;
		this.inAcctNo = builder.inAcctNo;
		this.inAcctName = builder.inAcctName;
		this.inAcctBankName = builder.inAcctBankName;
		this.inAcctProvinceCode = builder.inAcctProvinceCode;
		this.inAcctCityName = builder.inAcctCityName;
		this.tranAmount = builder.tranAmount;
		this.amountCode = builder.amountCode;
		this.useEx = builder.useEx;
		this.unionFlag = builder.unionFlag;
		this.sysFlag = builder.sysFlag;
		this.addrFlag = builder.addrFlag;
		this.realFlag = builder.realFlag;
		this.mainAcctNo = builder.mainAcctNo;
		this.mobile = builder.mobile;
		this.zdJType = builder.zdJType;
		this.zdZType = builder.zdZType;
		this.zdBAcc = builder.zdBAcc;
	}

	public String getThirdVoucher() {
		return thirdVoucher;
	}

	public String getCstInnerFlowNo() {
		return cstInnerFlowNo;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public String getOutAcctNo() {
		return outAcctNo;
	}

	public String getOutAcctName() {
		return outAcctName;
	}

	public String getOutAcctAddr() {
		return outAcctAddr;
	}

	public String getInAcctBankNode() {
		return inAcctBankNode;
	}

	public String getInAcctRecCode() {
		return inAcctRecCode;
	}

	public String getInAcctNo() {
		return inAcctNo;
	}

	public String getInAcctName() {
		return inAcctName;
	}

	public String getInAcctBankName() {
		return inAcctBankName;
	}

	public String getInAcctProvinceCode() {
		return inAcctProvinceCode;
	}

	public String getInAcctCityName() {
		return inAcctCityName;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public String getAmountCode() {
		return amountCode;
	}

	public String getUseEx() {
		return useEx;
	}

	public String getUnionFlag() {
		return unionFlag;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public String getAddrFlag() {
		return addrFlag;
	}

	public String getRealFlag() {
		return realFlag;
	}

	public String getMainAcctNo() {
		return mainAcctNo;
	}

	public String getMobile() {
		return mobile;
	}

	public String getZdJType() {
		return zdJType;
	}

	public String getZdZType() {
		return zdZType;
	}

	public String getZdBAcc() {
		return zdBAcc;
	}

	public static class Builder {
		public Builder(){
		}
		
		// 转账凭证号, C(20)	必输	标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
		private String thirdVoucher;
		// 客户自定义凭证号, C(20)	非必输	用于客户转账登记和内部识别，通过转账结果查询可以返回。银行不检查唯一性
		private String cstInnerFlowNo;
		// 货币类型, C(3)	必输	RMB-人民币
		private String ccyCode;
		// 付款人账户, C(14)	必输	扣款账户
		private String outAcctNo;
		// 付款人名称, C(60)	必输	付款账户户名
		private String outAcctName;
		// 付款人地址, C(60)	非必输	建议填写付款账户的分行、网点名称
		private String outAcctAddr;
		// 收款人开户行行号, C(12)	非必输	跨行转账建议必输。为人行登记在册的商业银行号
		private String inAcctBankNode;
		// 接收行行号, C(12)	非必输	建议同收款人开户行行号
		private String inAcctRecCode;
		// 收款人账户, C(32)	必输
		private String inAcctNo;
		// 收款人账户户名, C(60)	必输
		private String inAcctName;
		// 收款人开户行名称, C(60)	必输	建议格式：xxx银行
		private String inAcctBankName;	
		// 收款账户银行开户省代码或省名称, C(10)	非必输	建议跨行转账输入；对照码参考“附录-省对照表”；也可输入“附录-省对照表”中的省名称。
		private String inAcctProvinceCode;
		// 收款账户开户市, C(12)	非必输	建议跨行转账输入； 
		private String inAcctCityName;	
		// 转出金额, C(13,2)	必输	如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
		private String tranAmount;	
		// 转出金额大写, C(48)	非必须
		private String amountCode;	
		// 资金用途, C(30)	非必输
		private String useEx;
		// 行内跨行标志, C(1)	必输	1：行内转账，0：跨行转账
		private String unionFlag;
		// 转账加急标志, C(1) 	非必输	‘1’—大额 ，等同Y, ‘2’—小额”等同N, Y：加急 N：普通S：特急,默认为N
		private String sysFlag;
		// 同城/异地标志, C(1) 必输,	“1”—同城   “2”—异地
		private String addrFlag;
		// 实时或落地标志, C(1)  非必输	‘1’落地，‘2’实时, 此字段赋值为2无意义，为2时，系统会判断是否应该落地；为1则直接落地处理。 默认为2
		private String realFlag;
		// 虚子账户, C(32)	非必须	代理付款：填写虚子账号；代理结算：填写主账号，子账号填写在OutAcctNo域。
		private String mainAcctNo;
		
		// 转账短信通知手机号码, C(100)	非必须  格式为：	“13412341123,12312341234”，
		// 多个手机号码使用半角逗号分隔.	如果为空或者手机号码长度不足11位，就不发送。
		private String mobile;
		
		// 中登结算类型, C(1)	非必须	转入中登备付金必输：
		// 1：上海登记公司－资金清算业务
		// 2：上海登记公司－网下发行电子化业务
		// 3：深圳登记公司－网上业务
		// 4：深圳登记公司－网下IPO业务
		private String zdJType;
		// 资金类型, C(1) 非必须
		private String zdZType;
		// 备付金账号, C(20) 非必须
		private String zdBAcc;

		public Builder setThirdVoucher(String thirdVoucher) {
			this.thirdVoucher = thirdVoucher;
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

		public Builder setOutAcctNo(String outAcctNo) {
			this.outAcctNo = outAcctNo;
			return this;
		}

		public Builder setOutAcctName(String outAcctName) {
			this.outAcctName = outAcctName;
			return this;
		}

		public Builder setOutAcctAddr(String outAcctAddr) {
			this.outAcctAddr = outAcctAddr;
			return this;
		}

		public Builder setInAcctBankNode(String inAcctBankNode) {
			this.inAcctBankNode = inAcctBankNode;
			return this;
		}

		public Builder setInAcctRecCode(String inAcctRecCode) {
			this.inAcctRecCode = inAcctRecCode;
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

		public Builder setInAcctBankName(String inAcctBankName) {
			this.inAcctBankName = inAcctBankName;
			return this;
		}

		public Builder setInAcctProvinceCode(String inAcctProvinceCode) {
			this.inAcctProvinceCode = inAcctProvinceCode;
			return this;
		}

		public Builder setInAcctCityName(String inAcctCityName) {
			this.inAcctCityName = inAcctCityName;
			return this;
		}

		public Builder setTranAmount(String tranAmount) {
			this.tranAmount = tranAmount;
			return this;
		}

		public Builder setAmountCode(String amountCode) {
			this.amountCode = amountCode;
			return this;
		}

		public Builder setUseEx(String useEx) {
			this.useEx = useEx;
			return this;
		}

		public Builder setUnionFlag(String unionFlag) {
			this.unionFlag = unionFlag;
			return this;
		}

		public Builder setSysFlag(String sysFlag) {
			this.sysFlag = sysFlag;
			return this;
		}

		public Builder setAddrFlag(String addrFlag) {
			this.addrFlag = addrFlag;
			return this;
		}

		public Builder setRealFlag(String realFlag) {
			this.realFlag = realFlag;
			return this;
		}

		public Builder setMainAcctNo(String mainAcctNo) {
			this.mainAcctNo = mainAcctNo;
			return this;
		}

		public Builder setMobile(String mobile) {
			this.mobile = mobile;
			return this;
		}

		public Builder setZdJType(String zdJType) {
			this.zdJType = zdJType;
			return this;
		}

		public Builder setZdZType(String zdZType) {
			this.zdZType = zdZType;
			return this;
		}

		public Builder setZdBAcc(String zdBAcc) {
			this.zdBAcc = zdBAcc;
			return this;
		}

		public SingleTransferReqDTO build() {
			return new SingleTransferReqDTO(this);
		}
	}

	@Override
	public String obj2xml() {
		this.preHandleAliasFields();
				
		return PackageConstants.XML_SCHEMA_TITLE + xStream.toXML(this);
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}

		this.preHandleAliasFields();
		
		return  xStream.fromXML(strXml);
	}

	/**
	 * 字段名称预处理
	 */
	private void preHandleAliasFields() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", SingleTransferReqDTO.class);

		xStream.aliasField("ThirdVoucher", SingleTransferReqDTO.class, "thirdVoucher");
		xStream.aliasField("CstInnerFlowNo", SingleTransferReqDTO.class, "cstInnerFlowNo");
		xStream.aliasField("CcyCode", SingleTransferReqDTO.class, "ccyCode");
		xStream.aliasField("OutAcctNo", SingleTransferReqDTO.class, "outAcctNo");
		xStream.aliasField("OutAcctName", SingleTransferReqDTO.class, "outAcctName");
		xStream.aliasField("OutAcctAddr", SingleTransferReqDTO.class, "outAcctAddr");
		xStream.aliasField("InAcctBankNode", SingleTransferReqDTO.class, "inAcctBankNode");
		xStream.aliasField("InAcctRecCode", SingleTransferReqDTO.class, "inAcctRecCode");
		xStream.aliasField("InAcctNo", SingleTransferReqDTO.class, "inAcctNo");
		xStream.aliasField("InAcctName", SingleTransferReqDTO.class, "inAcctName");
		xStream.aliasField("InAcctBankName", SingleTransferReqDTO.class, "inAcctBankName");
		xStream.aliasField("InAcctProvinceCode", SingleTransferReqDTO.class, "inAcctProvinceCode");
		xStream.aliasField("InAcctCityName", SingleTransferReqDTO.class, "inAcctCityName");
		xStream.aliasField("TranAmount", SingleTransferReqDTO.class, "tranAmount");
		xStream.aliasField("AmountCode", SingleTransferReqDTO.class, "amountCode");
		xStream.aliasField("UseEx", SingleTransferReqDTO.class, "useEx");
		xStream.aliasField("UnionFlag", SingleTransferReqDTO.class, "unionFlag");
		xStream.aliasField("SysFlag", SingleTransferReqDTO.class, "sysFlag");
		xStream.aliasField("AddrFlag", SingleTransferReqDTO.class, "addrFlag");
		xStream.aliasField("RealFlag", SingleTransferReqDTO.class, "realFlag");
		xStream.aliasField("MainAcctNo", SingleTransferReqDTO.class, "mainAcctNo");
		xStream.aliasField("Mobile", SingleTransferReqDTO.class, "mobile");
		xStream.aliasField("zdJType", SingleTransferReqDTO.class, "zdJType");
		xStream.aliasField("zdZType", SingleTransferReqDTO.class, "zdZType");
		xStream.aliasField("zdBAcc", SingleTransferReqDTO.class, "zdBAcc");
	}
}