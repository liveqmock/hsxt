package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author jbli
 * 
 */
/**
 * @author Administrator
 *
 */
@Component("bodyRes_4013")
public class TradeRecordResDTO extends BodyDTO {

	public TradeRecordResDTO() {}

	// 账号
	private String acctNo;
	// 货币类型 结算币种
	private String ccyCode;
	// 数据结束标志
	private String isEnded;
	// 预留字段
	private String reserve;
	// 记录笔数
	private String pageRecCount;
	//记录集合 最多30笔
	private List<TradeRecordResDetailDTO> list;

	public String getAcctNo() {
		return acctNo;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public String getIsEnded() {
		return isEnded;
	}

	public String getReserve() {
		return reserve;
	}

	public String getPageRecCount() {
		return pageRecCount;
	}

	public List<TradeRecordResDetailDTO> getList() {
		return list;
	}

	private TradeRecordResDTO(Builder builder) {
		this.acctNo = builder.acctNo;
		this.ccyCode = builder.ccyCode;
		this.isEnded = builder.isEnded;
		this.reserve = builder.reserve;
		this.pageRecCount = builder.pageRecCount;
		this.list = builder.list;
	}
	
	public static class Builder {

		public Builder(String acctNo) {
			this.acctNo = acctNo;
		}

		// 账号
		private String acctNo;
		// 货币类型 结算币种
		private String ccyCode = "";
		// 数据结束标志
		private String isEnded = "";
		// 预留字段
		private String reserve = "";
		// 记录笔数
		private String pageRecCount = "";

		//记录集合 最多30笔
		private List<TradeRecordResDetailDTO> list = new ArrayList<TradeRecordResDetailDTO>();

		public Builder setAcctNo(String acctNo) {
			this.acctNo = acctNo;
			return this;
		}

		public Builder setCcyCode(String ccyCode) {
			this.ccyCode = ccyCode;
			return this;
		}

		public Builder setIsEnded(String isEnded) {
			this.isEnded = isEnded;
			return this;
		}

		public Builder setReserve(String reserve) {
			this.reserve = reserve;
			return this;
		}

		public Builder setPageRecCount(String pageRecCount) {
			this.pageRecCount = pageRecCount;
			return this;
		}

		public Builder setList(List<TradeRecordResDetailDTO> list) {
			this.list = list;
			return this;
		}
		
		public TradeRecordResDTO build(){
			return new TradeRecordResDTO(this);
		}
		
	}
	             
	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(TradeRecordResDTO.class, "list");
		xStream.alias("Result", TradeRecordResDTO.class);
		xStream.alias("list", TradeRecordResDetailDTO.class);
		xStream.aliasField("AcctNo", TradeRecordResDTO.class, "acctNo");
		xStream.aliasField("CcyCode", TradeRecordResDTO.class, "ccyCode");
		xStream.aliasField("IsEnded", TradeRecordResDTO.class, "isEnded");
		xStream.aliasField("Reserve", TradeRecordResDTO.class, "reserve");
		xStream.aliasField("PageRecCount", TradeRecordResDTO.class, "pageRecCount");

		xStream.aliasField("AcctDate", TradeRecordResDetailDTO.class, "acctDate");
		xStream.aliasField("TxTime", TradeRecordResDetailDTO.class, "txTime");
		xStream.aliasField("HostTrace", TradeRecordResDetailDTO.class, "hostTrace");
		xStream.aliasField("OutNode", TradeRecordResDetailDTO.class, "outNode");
		xStream.aliasField("OutBankNo", TradeRecordResDetailDTO.class, "outBankNo");
		
		xStream.aliasField("OutBankName", TradeRecordResDetailDTO.class, "outBankName");
		xStream.aliasField("OutAcctNo", TradeRecordResDetailDTO.class, "outAcctNo");
		xStream.aliasField("OutAcctName", TradeRecordResDetailDTO.class, "outAcctName");
		xStream.aliasField("TranAmount", TradeRecordResDetailDTO.class, "tranAmount");
		xStream.aliasField("InNode", TradeRecordResDetailDTO.class, "inNode");

		xStream.aliasField("InBankNo", TradeRecordResDetailDTO.class, "inBankNo");
		xStream.aliasField("InBankName", TradeRecordResDetailDTO.class, "inBankName");
		xStream.aliasField("InAcctNo", TradeRecordResDetailDTO.class, "inAcctNo");
		xStream.aliasField("InAcctName", TradeRecordResDetailDTO.class, "inAcctName");
		xStream.aliasField("DcFlag", TradeRecordResDetailDTO.class, "dcFlag");			
		
		xStream.aliasField("AbstractStr", TradeRecordResDetailDTO.class, "abstractStr");
		xStream.aliasField("VoucherNo", TradeRecordResDetailDTO.class, "voucherNo");
		xStream.aliasField("TranFee", TradeRecordResDetailDTO.class, "tranFee");
		xStream.aliasField("PostFee", TradeRecordResDetailDTO.class, "postFee");
		xStream.aliasField("AcctBalance", TradeRecordResDetailDTO.class, "acctBalance");
		
		xStream.aliasField("Purpose", TradeRecordResDetailDTO.class, "purpose");		
		xStream.aliasField("BstractStr_Desc", TradeRecordResDetailDTO.class, "bstractStr_Desc");	
		xStream.aliasField("CVoucherNo", TradeRecordResDetailDTO.class, "cVoucherNo");		
		
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}

		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(TradeRecordResDTO.class, "list");
		
		xStream.alias("Result", TradeRecordResDTO.class);
		xStream.alias("list", TradeRecordResDetailDTO.class);
		xStream.aliasField("AcctNo", TradeRecordResDTO.class, "acctNo");
		xStream.aliasField("CcyCode", TradeRecordResDTO.class, "ccyCode");
		xStream.aliasField("IsEnded", TradeRecordResDTO.class, "isEnded");
		xStream.aliasField("Reserve", TradeRecordResDTO.class, "reserve");
		xStream.aliasField("PageRecCount", TradeRecordResDTO.class, "pageRecCount");

		xStream.aliasField("AcctDate", TradeRecordResDetailDTO.class, "acctDate");
		xStream.aliasField("TxTime", TradeRecordResDetailDTO.class, "txTime");
		xStream.aliasField("HostTrace", TradeRecordResDetailDTO.class, "hostTrace");
		xStream.aliasField("OutNode", TradeRecordResDetailDTO.class, "outNode");
		xStream.aliasField("OutBankNo", TradeRecordResDetailDTO.class, "outBankNo");
		
		xStream.aliasField("OutBankName", TradeRecordResDetailDTO.class, "outBankName");
		xStream.aliasField("OutAcctNo", TradeRecordResDetailDTO.class, "outAcctNo");
		xStream.aliasField("OutAcctName", TradeRecordResDetailDTO.class, "outAcctName");
		xStream.aliasField("TranAmount", TradeRecordResDetailDTO.class, "tranAmount");
		xStream.aliasField("InNode", TradeRecordResDetailDTO.class, "inNode");

		xStream.aliasField("InBankNo", TradeRecordResDetailDTO.class, "inBankNo");
		xStream.aliasField("InBankName", TradeRecordResDetailDTO.class, "inBankName");
		xStream.aliasField("InAcctNo", TradeRecordResDetailDTO.class, "inAcctNo");
		xStream.aliasField("InAcctName", TradeRecordResDetailDTO.class, "inAcctName");
		xStream.aliasField("DcFlag", TradeRecordResDetailDTO.class, "dcFlag");			
		
		xStream.aliasField("AbstractStr", TradeRecordResDetailDTO.class, "abstractStr");
		xStream.aliasField("VoucherNo", TradeRecordResDetailDTO.class, "voucherNo");
		xStream.aliasField("TranFee", TradeRecordResDetailDTO.class, "tranFee");
		xStream.aliasField("PostFee", TradeRecordResDetailDTO.class, "postFee");
		xStream.aliasField("AcctBalance", TradeRecordResDetailDTO.class, "acctBalance");
		
		xStream.aliasField("Purpose", TradeRecordResDetailDTO.class, "purpose");		
		xStream.aliasField("BstractStr_Desc", TradeRecordResDetailDTO.class, "bstractStr_Desc");	
		xStream.aliasField("CVoucherNo", TradeRecordResDetailDTO.class, "cVoucherNo");		
		
		return  xStream.fromXML(strXml);
	}

}
