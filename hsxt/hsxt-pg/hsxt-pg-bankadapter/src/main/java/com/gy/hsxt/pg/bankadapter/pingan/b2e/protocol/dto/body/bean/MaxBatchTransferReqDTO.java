package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.BSysFlag;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.CcyCode;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.PayType;

/**
 * @author jbli
 * 此转账接口只返回银行受理成功，转账结果必须调用4015接口查询
 * 此接口转账为异步处理，单批笔数限制为500，银行收到请求后，只返回上送的批次号，并告知批次接受成功与否。
 * 银行在10分钟内会定时进行转账处理，结果需要调用4015查询批次转账进度。建议企业使用4015查询的间隔为10分钟。
 */
@Component("bodyReq_4018")  
public class MaxBatchTransferReqDTO   extends BodyDTO{
	
	public MaxBatchTransferReqDTO(){};

	//批量转账凭证号	C(20)	必输	标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
	private String thirdVoucher;
	//总记录数	C(5)	必输	批量转账的笔数，TranMode=0时，笔数必须在10笔以内（包括10）；TranMode=1时，笔数不大于500笔；
	private String totalCts;	
	//总金额	C(13,2)	必输	如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
	private String totalAmt;	
	//批次摘要	C(30)	非必输	
	private String batchSummary;
	//整批转账加急标志	C(1) 	必输	Y：加急 
	private String bSysFlag;	
	//货币类型	C(3)	必输	
	private String ccyCode;	    
	//付款人账户	C(14)	必输	扣款账户
	private String outAcctNo;	
	//付款人名称	C(60)	必输	付款账户户名
	private String outAcctName;	
	//付款人地址	C(60)	非必输	建议填写付款账户的分行、网点名称
	private String outAcctAddr;	
	//扣款类型	C(1)	非必输，默认为0	0：单笔扣划
	private String payType;
	//为多条记录 标签名HOResultSet4018R
	private List<MaxBatchTransferReqDetailDTO> list;	

	private MaxBatchTransferReqDTO(Builder builder){
		this.thirdVoucher = builder.thirdVoucher;
		this.totalCts = builder.totalCts;
		this.totalAmt = builder.totalAmt;
		this.batchSummary = builder.batchSummary;
		this.bSysFlag = builder.bSysFlag;
		this.ccyCode = builder.ccyCode;
		this.outAcctNo = builder.outAcctNo;
		this.outAcctName = builder.outAcctName;		
		this.outAcctAddr = builder.outAcctAddr;
		this.payType = builder.payType;
		this.list = builder.list;
	}
	
	public String getThirdVoucher() {
		return thirdVoucher;
	}

	public String getTotalCts() {
		return totalCts;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public String getBatchSummary() {
		return batchSummary;
	}

	public String getBSysFlag() {
		return bSysFlag;
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

	public String getPayType() {
		return payType;
	}

	public List<MaxBatchTransferReqDetailDTO> getList() {
		return list;
	}



	public static class Builder{
		
		public Builder(String thirdVoucher, String totalAmt, String outAcctNo,
				String outAcctName, List<MaxBatchTransferReqDetailDTO> list) {
			this.thirdVoucher = thirdVoucher;
			this.totalCts = String.valueOf(list.size());
			this.totalAmt = totalAmt;
			this.outAcctNo = outAcctNo;
			this.outAcctName = outAcctName;
			this.list = list;
		}
		
		//批量转账凭证号	C(20)	必输	标示交易唯一性，同一客户上送的不可重复，建议格式：yyyymmddHHSS+8位系列
		private String thirdVoucher = "";
		//总记录数	C(5)	必输	批量转账的笔数，TranMode=0时，笔数必须在10笔以内（包括10）；TranMode=1时，笔数不大于500笔；
		private String totalCts = "";	
		//总金额	C(13,2)	必输	如为XML报文，则直接输入输出以元为单位的浮点数值，如2.50 (两元五角)
		private String totalAmt = "";	
		//批次摘要	C(30)	非必输	
		private String batchSummary = "";
		//整批转账加急标志	C(1) 	必输	Y：加急 
		private String bSysFlag = BSysFlag.NO.getValue();	
		//货币类型	C(3)	必输	
		private String ccyCode =CcyCode.RMB.getValue();	    
		//付款人账户	C(14)	必输	扣款账户
		private String outAcctNo = "";	
		//付款人名称	C(60)	必输	付款账户户名
		private String outAcctName = ""; 	
		//付款人地址	C(60)	非必输	建议填写付款账户的分行、网点名称
		private String outAcctAddr = "";	
		//扣款类型	C(1)	非必输，默认为0	0：单笔扣划
		private String payType = PayType.SINGLE_PAY.getValue();	   		
		//为多条记录 标签名HOResultSet4018R
		private List<MaxBatchTransferReqDetailDTO> list;			
		

		public Builder setBatchSummary(String batchSummary) {
			this.batchSummary = batchSummary;
			return this;
		}

		public Builder setBSysFlag(String bSysFlag) {
			this.bSysFlag = bSysFlag;
			return this;
		}

		public Builder setCcyCode(String ccyCode) {
			this.ccyCode = ccyCode;
			return this;
		}

		public Builder setOutAcctAddr(String outAcctAddr) {
			this.outAcctAddr = outAcctAddr;
			return this;
		}

		public Builder setPayType(String payType) {
			this.payType = payType;
			return this;
		}

		public Builder setList(List<MaxBatchTransferReqDetailDTO> list) {
			this.list = list;
			return this;
		}		
		
		public MaxBatchTransferReqDTO build(){
			return new MaxBatchTransferReqDTO(this);
		}
		
	}

	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(MaxBatchTransferReqDTO.class, "list");
		xStream.alias("Result", MaxBatchTransferReqDTO.class);
		xStream.alias("HOResultSet4018R", MaxBatchTransferReqDetailDTO.class);

		xStream.aliasField("ThirdVoucher", MaxBatchTransferReqDTO.class, "thirdVoucher");
		xStream.aliasField("totalCts", MaxBatchTransferReqDTO.class, "totalCts");
		xStream.aliasField("totalAmt", MaxBatchTransferReqDTO.class, "totalAmt");
		xStream.aliasField("BatchSummary", MaxBatchTransferReqDTO.class, "batchSummary");
		xStream.aliasField("BSysFlag", MaxBatchTransferReqDTO.class, "bSysFlag");
		
		xStream.aliasField("CcyCode", MaxBatchTransferReqDTO.class, "ccyCode");	
		xStream.aliasField("OutAcctNo", MaxBatchTransferReqDTO.class, "outAcctNo");
		xStream.aliasField("OutAcctName", MaxBatchTransferReqDTO.class, "outAcctName");	
		xStream.aliasField("OutAcctAddr", MaxBatchTransferReqDTO.class, "outAcctAddr");
		xStream.aliasField("PayType", MaxBatchTransferReqDTO.class, "payType");
		
		xStream.aliasField("SThirdVoucher",MaxBatchTransferReqDetailDTO.class,"sThirdVoucher");
		xStream.aliasField("CstInnerFlowNo",MaxBatchTransferReqDetailDTO.class,"cstInnerFlowNo");
		xStream.aliasField("InAcctBankNode",MaxBatchTransferReqDetailDTO.class,"inAcctBankNode");
		xStream.aliasField("InAcctRecCode",MaxBatchTransferReqDetailDTO.class,"inAcctRecCode");
		xStream.aliasField("InAcctNo",MaxBatchTransferReqDetailDTO.class,"inAcctNo");
		xStream.aliasField("InAcctName",MaxBatchTransferReqDetailDTO.class,"inAcctName");
		xStream.aliasField("InAcctBankName",MaxBatchTransferReqDetailDTO.class,"inAcctBankName");
		xStream.aliasField("InAcctProvinceCode",MaxBatchTransferReqDetailDTO.class,"inAcctProvinceCode");
		xStream.aliasField("InAcctCityName",MaxBatchTransferReqDetailDTO.class,"inAcctCityName");
		xStream.aliasField("TranAmount",MaxBatchTransferReqDetailDTO.class,"tranAmount");
		xStream.aliasField("UseEx",MaxBatchTransferReqDetailDTO.class,"useEx");
		xStream.aliasField("UnionFlag",MaxBatchTransferReqDetailDTO.class,"unionFlag");
		xStream.aliasField("AddrFlag",MaxBatchTransferReqDetailDTO.class,"addrFlag");
		xStream.aliasField("MainAcctNo",MaxBatchTransferReqDetailDTO.class,"mainAcctNo");
		
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}

		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(MaxBatchTransferReqDTO.class, "list");
		xStream.alias("Result", MaxBatchTransferReqDTO.class);
		xStream.alias("HOResultSet4018R", MaxBatchTransferReqDetailDTO.class);

		xStream.aliasField("ThirdVoucher", MaxBatchTransferReqDTO.class, "thirdVoucher");
		xStream.aliasField("totalCts", MaxBatchTransferReqDTO.class, "totalCts");
		xStream.aliasField("totalAmt", MaxBatchTransferReqDTO.class, "totalAmt");
		xStream.aliasField("BatchSummary", MaxBatchTransferReqDTO.class, "batchSummary");
		xStream.aliasField("BSysFlag", MaxBatchTransferReqDTO.class, "bSysFlag");
		
		xStream.aliasField("CcyCode", MaxBatchTransferReqDTO.class, "ccyCode");	
		xStream.aliasField("OutAcctNo", MaxBatchTransferReqDTO.class, "outAcctNo");
		xStream.aliasField("OutAcctName", MaxBatchTransferReqDTO.class, "outAcctName");	
		xStream.aliasField("OutAcctAddr", MaxBatchTransferReqDTO.class, "outAcctAddr");
		xStream.aliasField("PayType", MaxBatchTransferReqDTO.class, "payType");
		
		xStream.aliasField("SThirdVoucher",MaxBatchTransferReqDetailDTO.class,"sThirdVoucher");
		xStream.aliasField("CstInnerFlowNo",MaxBatchTransferReqDetailDTO.class,"cstInnerFlowNo");
		xStream.aliasField("InAcctBankNode",MaxBatchTransferReqDetailDTO.class,"inAcctBankNode");
		xStream.aliasField("InAcctRecCode",MaxBatchTransferReqDetailDTO.class,"inAcctRecCode");
		xStream.aliasField("InAcctNo",MaxBatchTransferReqDetailDTO.class,"inAcctNo");
		xStream.aliasField("InAcctName",MaxBatchTransferReqDetailDTO.class,"inAcctName");
		xStream.aliasField("InAcctBankName",MaxBatchTransferReqDetailDTO.class,"inAcctBankName");
		xStream.aliasField("InAcctProvinceCode",MaxBatchTransferReqDetailDTO.class,"inAcctProvinceCode");
		xStream.aliasField("InAcctCityName",MaxBatchTransferReqDetailDTO.class,"inAcctCityName");
		xStream.aliasField("TranAmount",MaxBatchTransferReqDetailDTO.class,"tranAmount");
		xStream.aliasField("UseEx",MaxBatchTransferReqDetailDTO.class,"useEx");
		xStream.aliasField("UnionFlag",MaxBatchTransferReqDetailDTO.class,"unionFlag");
		xStream.aliasField("AddrFlag",MaxBatchTransferReqDetailDTO.class,"addrFlag");
		xStream.aliasField("MainAcctNo",MaxBatchTransferReqDetailDTO.class,"mainAcctNo");
		
		return  xStream.fromXML(strXml);
	}


}
