package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.CcyCode;

/**
 * @author jbli
 *
 */
@Component("bodyReq_4013")
public class TradeRecordlReqDTO  extends BodyDTO{
	
	public static final int DAY_OF_MILLIS = 24 * 60 * 60 * 1000;
	
	public TradeRecordlReqDTO(){}

	// 账号
	private String acctNo;
	// 币种
	private String ccyCode;
	// 开始日期
	private String beginDate;
	// 结束日期
	private String endDate;
	// 查询页码
	private String pageNo;
	// 预留字段
	private String reserve;

	public String getAcctNo() {
		return acctNo;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getPageNo() {
		return pageNo;
	}

	public String getReserve() {
		return reserve;
	}
	
	private TradeRecordlReqDTO(Builder builder) {
		this.acctNo = builder.accNo;
		this.ccyCode = builder.ccyCode;
		this.beginDate = builder.beginDate;
		this.endDate = builder.endDate;
		this.pageNo = builder.pageNo;
		this.reserve = builder.reserve;		
	}
	
	public static class Builder{
		public Builder(String accNo){
			this.accNo = accNo;
		}
		
		// 账号
		private String accNo = "";

		// 币种
		private String ccyCode = CcyCode.RMB.getValue();

		// 开始日期，默认前7天
		private String beginDate = StringHelper.getYYYYMMddDateFormat().format(System.currentTimeMillis()-7*DAY_OF_MILLIS);;

		// 结束日期，默认为当前时间
		private String endDate = StringHelper.getYYYYMMddDateFormat().format(System.currentTimeMillis());

		// 查询页码，默认为第1页
		private String pageNo = "1";

		// 预留字段
		private String reserve = "";		

		public Builder setCcyCode(String ccyCode) {
			this.ccyCode = ccyCode;
			return this;
		}

		public Builder setBeginDate(String beginDate) {
			this.beginDate = beginDate;
			return this;
		}

		public Builder setEndDate(String endDate) {
			this.endDate = endDate;
			return this;
		}

		public Builder setPageNo(String pageNo) {
			this.pageNo = pageNo;
			return this;
		}

		public Builder setReserve(String reserve) {
			this.reserve = reserve;
			return this;
		}
		
		public TradeRecordlReqDTO build(){
			return new TradeRecordlReqDTO(this);
		}
		
	}
	
	
	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", TradeRecordlReqDTO.class);
		xStream.aliasField("AcctNo", TradeRecordlReqDTO.class, "acctNo");
		xStream.aliasField("CcyCode", TradeRecordlReqDTO.class, "ccyCode");
		xStream.aliasField("BeginDate", TradeRecordlReqDTO.class, "beginDate");
		xStream.aliasField("EndDate", TradeRecordlReqDTO.class, "endDate");
		xStream.aliasField("PageNo", TradeRecordlReqDTO.class, "pageNo");
		xStream.aliasField("Reserve", TradeRecordlReqDTO.class, "reserve");
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", TradeRecordlReqDTO.class);
		xStream.aliasField("AcctNo", TradeRecordlReqDTO.class, "acctNo");
		xStream.aliasField("CcyCode", TradeRecordlReqDTO.class, "ccyCode");
		xStream.aliasField("BeginDate", TradeRecordlReqDTO.class, "beginDate");
		xStream.aliasField("EndDate", TradeRecordlReqDTO.class, "endDate");
		xStream.aliasField("PageNo", TradeRecordlReqDTO.class, "pageNo");
		xStream.aliasField("Reserve", TradeRecordlReqDTO.class, "reserve");
		return xStream.fromXML(strXml);
	}






}
