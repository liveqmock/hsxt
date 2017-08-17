package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author jbli
 */
@Component("bodyRes_4015")  
public class QryMaxBatchTransferResDTO  extends BodyDTO {
	
	public QryMaxBatchTransferResDTO(){}
	
	//成功笔数	C(5)	非必输	查询类型为“0全部”时返回
	private String successCts;
	//成功金额	C(13,2)	非必输	查询类型为“0全部”时返回
	private String successAmt;
	//失败笔数	C(5)	非必输	查询类型为“0全部”时返回
	private String faildCts;
	//失败金额	C(13,2)	非必输	查询类型为“0全部”时返回
	private String faildAmt;	
	//处理中笔数	C(5)	非必输	查询类型为“0全部”时返回
	private String processCts;	
	//处理中金额	C(13,2)	非必输	查询类型为“0全部”时返回
	private String processAmt;   	
	//批次总记录数	9(10)	必输	批次的记录总数
	private String totalCts; 
	//符合当前查询条件的笔数	9(10)	必输	符合当前查询条件的笔数
	private String cTotalCts;
	//当前页返回的记录数	9(10)	必输	当前分页返回的记录数
	private String pTotalCts;
	//记录结束标志	C(1)	必输	Y:无剩余记录N:有剩余记录 符合当前查询条件的记录是否查询结束
	private String isEnd;
	//批次状态	C(2)	非必输	20：成功, 30：失败,  其他为银行受理成功处理中
	private String batchSTT;
	//子批次状态描述	C(100)	非必输	格式: [子批次号]-[状态]-[错误码]-[错误信息];
	private String subBatchSTT;
	//记录集
	private List<QryMaxBatchTransferResDetailDTO> list;

	public String getSuccessCts() {
		return successCts;
	}

	public String getSuccessAmt() {
		return successAmt;
	}

	public String getFaildCts() {
		return faildCts;
	}

	public String getFaildAmt() {
		return faildAmt;
	}

	public String getProcessCts() {
		return processCts;
	}

	public String getProcessAmt() {
		return processAmt;
	}

	public String getTotalCts() {
		return totalCts;
	}

	public String getcTotalCts() {
		return cTotalCts;
	}

	public String getpTotalCts() {
		return pTotalCts;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public String getBatchSTT() {
		return batchSTT;
	}

	public String getSubBatchSTT() {
		return subBatchSTT;
	}

	public List<QryMaxBatchTransferResDetailDTO> getList() {
		return list;
	}

	private  QryMaxBatchTransferResDTO(Builder builder){
		this.successCts = builder.successCts;
		this.successAmt = builder.successAmt;
		this.faildCts = builder.faildCts;
		this.faildAmt = builder.faildAmt;
		this.processCts = builder.processCts;
		this.processAmt = builder.processAmt;
		this.totalCts = builder.totalCts;
		this.cTotalCts = builder.cTotalCts;
		this.pTotalCts = builder.pTotalCts;
		this.isEnd = builder.isEnd;
		this.batchSTT = builder.batchSTT;
		this.subBatchSTT = builder.subBatchSTT;
		this.list = builder.list;
	}
	
	public static class Builder{
		//成功笔数	C(5)	非必输	查询类型为“0全部”时返回
		private String successCts = "";
		//成功金额	C(13,2)	非必输	查询类型为“0全部”时返回
		private String successAmt = "";
		//失败笔数	C(5)	非必输	查询类型为“0全部”时返回
		private String faildCts = "";
		//失败金额	C(13,2)	非必输	查询类型为“0全部”时返回
		private String faildAmt = "";	
		//处理中笔数	C(5)	非必输	查询类型为“0全部”时返回
		private String processCts = "";	
		//处理中金额	C(13,2)	非必输	查询类型为“0全部”时返回
		private String processAmt = "";   	
		//批次总记录数	9(10)	必输	批次的记录总数
		private String totalCts = ""; 
		//符合当前查询条件的笔数	9(10)	必输	符合当前查询条件的笔数
		private String cTotalCts = "";
		//当前页返回的记录数	9(10)	必输	当前分页返回的记录数
		private String pTotalCts = "";
		//记录结束标志	C(1)	必输	Y:无剩余记录N:有剩余记录 符合当前查询条件的记录是否查询结束
		private String isEnd = "";
		//批次状态	C(2)	非必输	20：成功30：失败 其他为银行受理成功处理中
		private String batchSTT = "";
		//子批次状态描述	C(100)	非必输	格式: [子批次号]-[状态]-[错误码]-[错误信息];
		private String subBatchSTT = "";
		//记录集
		private List<QryMaxBatchTransferResDetailDTO> list;
		
		public void setSuccessCts(String successCts) {
			this.successCts = successCts;
		}



		public void setSuccessAmt(String successAmt) {
			this.successAmt = successAmt;
		}



		public void setFaildCts(String faildCts) {
			this.faildCts = faildCts;
		}



		public void setFaildAmt(String faildAmt) {
			this.faildAmt = faildAmt;
		}



		public void setProcessCts(String processCts) {
			this.processCts = processCts;
		}



		public void setProcessAmt(String processAmt) {
			this.processAmt = processAmt;
		}



		public void setTotalCts(String totalCts) {
			this.totalCts = totalCts;
		}



		public void setcTotalCts(String cTotalCts) {
			this.cTotalCts = cTotalCts;
		}



		public void setpTotalCts(String pTotalCts) {
			this.pTotalCts = pTotalCts;
		}



		public void setIsEnd(String isEnd) {
			this.isEnd = isEnd;
		}



		public void setBatchSTT(String batchSTT) {
			this.batchSTT = batchSTT;
		}



		public void setSubBatchSTT(String subBatchSTT) {
			this.subBatchSTT = subBatchSTT;
		}		


		public void setList(List<QryMaxBatchTransferResDetailDTO> list) {
			this.list = list;
		}
		
		public QryMaxBatchTransferResDTO build(){
			return new QryMaxBatchTransferResDTO(this);
		}
		
	}

	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(QryMaxBatchTransferResDTO.class, "list");
		xStream.alias("Result", QryMaxBatchTransferResDTO.class);
		xStream.alias("list", QryMaxBatchTransferResDetailDTO.class);

		xStream.aliasField("successCts ",QryMaxBatchTransferResDTO.class,"successCts ");
		xStream.aliasField("successAmt ",QryMaxBatchTransferResDTO.class,"successAmt ");
		xStream.aliasField("faildCts ",QryMaxBatchTransferResDTO.class,"faildCts ");
		xStream.aliasField("faildAmt ",QryMaxBatchTransferResDTO.class,"faildAmt ");
		xStream.aliasField("processCts ",QryMaxBatchTransferResDTO.class,"processCts ");
		xStream.aliasField("processAmt ",QryMaxBatchTransferResDTO.class,"processAmt ");
		xStream.aliasField("TotalCts ",QryMaxBatchTransferResDTO.class,"totalCts ");
		xStream.aliasField("CTotalCts",QryMaxBatchTransferResDTO.class,"cTotalCts");
		xStream.aliasField("PTotalCts",QryMaxBatchTransferResDTO.class,"pTotalCts");
		xStream.aliasField("IsEnd",QryMaxBatchTransferResDTO.class,"isEnd");
		xStream.aliasField("batchSTT ",QryMaxBatchTransferResDTO.class,"batchSTT ");
		xStream.aliasField("subBatchSTT",QryMaxBatchTransferResDTO.class,"subBatchSTT");
		
		xStream.aliasField("FrontLogNo",QryMaxBatchTransferResDetailDTO.class,"frontLogNo");
		xStream.aliasField("SThirdVoucher",QryMaxBatchTransferResDetailDTO.class,"sThirdVoucher");
		xStream.aliasField("CstInnerFlowNo",QryMaxBatchTransferResDetailDTO.class,"cstInnerFlowNo");
		xStream.aliasField("CcyCode",QryMaxBatchTransferResDetailDTO.class,"ccyCode");
		xStream.aliasField("OutAcctBankName",QryMaxBatchTransferResDetailDTO.class,"outAcctBankName");
		xStream.aliasField("OutAcctNo",QryMaxBatchTransferResDetailDTO.class,"outAcctNo");
		xStream.aliasField("InAcctBankName",QryMaxBatchTransferResDetailDTO.class,"inAcctBankName");
		xStream.aliasField("InAcctNo",QryMaxBatchTransferResDetailDTO.class,"inAcctNo");
		xStream.aliasField("InAcctName",QryMaxBatchTransferResDetailDTO.class,"inAcctName");
		xStream.aliasField("TranAmount",QryMaxBatchTransferResDetailDTO.class,"tranAmount");
		xStream.aliasField("UnionFlag",QryMaxBatchTransferResDetailDTO.class,"unionFlag");
		xStream.aliasField("Stt",QryMaxBatchTransferResDetailDTO.class,"stt");
		xStream.aliasField("IsBack",QryMaxBatchTransferResDetailDTO.class,"isBack");
		xStream.aliasField("BackRem",QryMaxBatchTransferResDetailDTO.class,"backRem");
		xStream.aliasField("Yhcljg",QryMaxBatchTransferResDetailDTO.class,"yhcljg");
		xStream.aliasField("SysFlag",QryMaxBatchTransferResDetailDTO.class,"sysFlag");
		xStream.aliasField("Fee",QryMaxBatchTransferResDetailDTO.class,"fee");
		xStream.aliasField("TransBsn",QryMaxBatchTransferResDetailDTO.class,"transBsn");
		xStream.aliasField("submitTime",QryMaxBatchTransferResDetailDTO.class,"submitTime");
		xStream.aliasField("AccountDate",QryMaxBatchTransferResDetailDTO.class,"accountDate");

		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}


	   /**		
		*下面这些字段在平安银行提供的《新银企平台接口文档设计v0.9.doc》文档中不存在，但是在2013年5月测试环境中有这些返回数据，
		*为了让Xstream默认忽略这些以后还会增加的，未定义的属性，让程序不抛出异常，在Xsteam实例的时候定制了一个方法，返回falshe。
		*xStream.omitField(QryMaxBatchTransferResDTO.class, "OrigThirdVoucher");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "InAcctBankNode");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "hostFlowNo");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "kType");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "dType");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "subBatchNo");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "ProxyPayName");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "ProxyPayAcc");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "ProxyPayBankName");
		*xStream.omitField(QryMaxBatchTransferResDetailDTO.class, "PayCode");
		*/		
		
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(QryMaxBatchTransferResDTO.class, "list");
		xStream.alias("Result", QryMaxBatchTransferResDTO.class);
		xStream.alias("list", QryMaxBatchTransferResDetailDTO.class);

		xStream.aliasField("successCts ",QryMaxBatchTransferResDTO.class,"successCts");
		xStream.aliasField("successAmt ",QryMaxBatchTransferResDTO.class,"successAmt");
		xStream.aliasField("faildCts ",QryMaxBatchTransferResDTO.class,"faildCts");
		xStream.aliasField("faildAmt ",QryMaxBatchTransferResDTO.class,"faildAmt");
		xStream.aliasField("processCts ",QryMaxBatchTransferResDTO.class,"processCts");
		xStream.aliasField("processAmt ",QryMaxBatchTransferResDTO.class,"processAmt");
		xStream.aliasField("TotalCts",QryMaxBatchTransferResDTO.class,"totalCts");
		xStream.aliasField("CTotalCts",QryMaxBatchTransferResDTO.class,"cTotalCts");
		xStream.aliasField("PTotalCts",QryMaxBatchTransferResDTO.class,"pTotalCts");
		xStream.aliasField("IsEnd",QryMaxBatchTransferResDTO.class,"isEnd");
		xStream.aliasField("batchSTT ",QryMaxBatchTransferResDTO.class,"batchSTT");
		xStream.aliasField("subBatchSTT",QryMaxBatchTransferResDTO.class,"subBatchSTT");
		
		xStream.aliasField("FrontLogNo",QryMaxBatchTransferResDetailDTO.class,"frontLogNo");
		xStream.aliasField("SThirdVoucher",QryMaxBatchTransferResDetailDTO.class,"sThirdVoucher");
		xStream.aliasField("CstInnerFlowNo",QryMaxBatchTransferResDetailDTO.class,"cstInnerFlowNo");
		xStream.aliasField("CcyCode",QryMaxBatchTransferResDetailDTO.class,"ccyCode");
		xStream.aliasField("OutAcctBankName",QryMaxBatchTransferResDetailDTO.class,"outAcctBankName");
		xStream.aliasField("OutAcctNo",QryMaxBatchTransferResDetailDTO.class,"outAcctNo");
		xStream.aliasField("InAcctBankName",QryMaxBatchTransferResDetailDTO.class,"inAcctBankName");
		xStream.aliasField("InAcctNo",QryMaxBatchTransferResDetailDTO.class,"inAcctNo");
		xStream.aliasField("InAcctName",QryMaxBatchTransferResDetailDTO.class,"inAcctName");
		xStream.aliasField("TranAmount",QryMaxBatchTransferResDetailDTO.class,"tranAmount");
		xStream.aliasField("UnionFlag",QryMaxBatchTransferResDetailDTO.class,"unionFlag");
		xStream.aliasField("Stt",QryMaxBatchTransferResDetailDTO.class,"stt");
		xStream.aliasField("IsBack",QryMaxBatchTransferResDetailDTO.class,"isBack");
		xStream.aliasField("BackRem",QryMaxBatchTransferResDetailDTO.class,"backRem");
		xStream.aliasField("Yhcljg",QryMaxBatchTransferResDetailDTO.class,"yhcljg");
		xStream.aliasField("SysFlag",QryMaxBatchTransferResDetailDTO.class,"sysFlag");
		xStream.aliasField("Fee",QryMaxBatchTransferResDetailDTO.class,"fee");
		xStream.aliasField("TransBsn",QryMaxBatchTransferResDetailDTO.class,"transBsn");
		xStream.aliasField("submitTime",QryMaxBatchTransferResDetailDTO.class,"submitTime");
		xStream.aliasField("AccountDate",QryMaxBatchTransferResDetailDTO.class,"accountDate");
		
		return  xStream.fromXML(strXml);
	}
	
	public static void main(String[] args) {
//		String strxml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Result><OrigThirdVoucher>guiyi20130901001</OrigThirdVoucher><batchSTT>20</batchSTT><successCts>2</successCts><successAmt>2.20</successAmt><faildCts>10</faildCts><faildAmt>11.00</faildAmt><processCts>3</processCts><processAmt>3.30</processAmt><TotalCts>15</TotalCts><CTotalCts>15</CTotalCts><PTotalCts>15</PTotalCts><IsEnd>Y</IsEnd><subBatchSTT></subBatchSTT><list><SThirdVoucher>000000015</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>账户已销户</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669447</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>11000456184901</InAcctNo><InAcctName>SHENFA000456184</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-CD5102账户已销户账户已销户</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20130915</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>000000014</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>账户已销户</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669446</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>11000456183001</InAcctNo><InAcctName>SHENFA000456183</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-CD5102账户已销户账户已销户</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20130915</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>000000013</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>账户已销户</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669445</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>11000456115701</InAcctNo><InAcctName>SHENFA000456115</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-CD5102账户已销户账户已销户</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20130915</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>000000012</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>账户已销户</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669444</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>11000456091801</InAcctNo><InAcctName>SHENFA000456091</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-CD5102账户已销户账户已销户</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20130915</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>000000011</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>账户已销户</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669443</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>11000456076701</InAcctNo><InAcctName>SHENFA000456076</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-CD5102账户已销户账户已销户</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20130915</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>000000010</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>验证收款方户名失败!</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669442</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>0292100346846</InAcctNo><InAcctName>平安测试84234</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-YQ9982收款方户名输入错误验证收款方户名失败!</Yhcljg><Fee>0.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20130915</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000009</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>验证收款方户名失败!</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669441</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>0012100756865</InAcctNo><InAcctName>平安测试02006</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-YQ9982收款方户名输入错误验证收款方户名失败!</Yhcljg><Fee>0.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20130915</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000008</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>40</Stt><IsBack>0</IsBack><BackRem></BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669440</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>中国工商银行股份有限公司</InAcctBankName><InAcctNo>102100099996</InAcctNo><InAcctName>102100099996</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>0</UnionFlag><Yhcljg>000000:交易受理成功正在处理中</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate></AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000007</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>40</Stt><IsBack>0</IsBack><BackRem></BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669439</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>招商银行福州分行东水支行</InAcctBankName><InAcctNo>308391026077</InAcctNo><InAcctName>308391026077</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>0</UnionFlag><Yhcljg>000000:交易受理成功正在处理中</Yhcljg><Fee>5.00</Fee><submitTime>20130915221052</submitTime><AccountDate></AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000006</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>帐户名不匹配</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669438</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>6222980024713523</InAcctNo><InAcctName>落单零一</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-582618582618帐户名不匹配</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20150111</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo>0068229</hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000005</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>20</Stt><IsBack>0</IsBack><BackRem>交易成功</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669437</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>11002873390701</InAcctNo><InAcctName>優比速包裹運送（ Ｖ｜ ）有 分公司</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>000000:内部转帐交易成功/外部转帐交易成功</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20150111</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo>0068229</hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000004</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>40</Stt><IsBack>0</IsBack><BackRem></BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669436</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>招商银行</InAcctBankName><InAcctNo>1234567890123</InAcctNo><InAcctName>招商银行测试户</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>0</UnionFlag><Yhcljg>000000:交易受理成功正在处理中</Yhcljg><Fee>5.00</Fee><submitTime>20130915221052</submitTime><AccountDate></AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo></hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000003</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>找不到该账号</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669435</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>8048101390454</InAcctNo><InAcctName>平安测试28103</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-EB4069账号不存在找不到该账号</Yhcljg><Fee>0.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20150111</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo>0068225</hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000002</SThirdVoucher><CstInnerFlowNo>sf3</CstInnerFlowNo><Stt>30</Stt><IsBack>0</IsBack><BackRem>找不到该账号</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669434</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>8048101390454</InAcctNo><InAcctName>平安测试28103</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>MA9112:转账失败-EB4069账号不存在找不到该账号</Yhcljg><Fee>0.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20150111</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo>0068225</hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list><list><SThirdVoucher>00000001</SThirdVoucher><CstInnerFlowNo>sf23324</CstInnerFlowNo><Stt>20</Stt><IsBack>0</IsBack><BackRem>交易成功</BackRem><SysFlag>Y</SysFlag><TransBsn>4018</TransBsn><FrontLogNo>58130915669433</FrontLogNo><CcyCode>RMB</CcyCode><OutAcctBankName></OutAcctBankName><OutAcctNo>11011781161701</OutAcctNo><InAcctBankName>深圳平安银行</InAcctBankName><InAcctNo>11003696301601</InAcctNo><InAcctName>SHENFA003696301</InAcctName><TranAmount>1.10</TranAmount><UnionFlag>1</UnionFlag><Yhcljg>000000:内部转帐交易成功/外部转帐交易成功</Yhcljg><Fee>4.00</Fee><submitTime>20130915221052</submitTime><AccountDate>20150111</AccountDate><InAcctBankNode></InAcctBankNode><hostFlowNo>0068225</hostFlowNo><kType>0</kType><dType>0</dType><subBatchNo>guiyi20130901001</subBatchNo><ProxyPayName></ProxyPayName><ProxyPayAcc></ProxyPayAcc><ProxyPayBankName></ProxyPayBankName><PayCode></PayCode></list></Result>";
//		QryMaxBatchTransferResDTO dto = new QryMaxBatchTransferResDTO();
//		QryMaxBatchTransferResDTO dto2 = (QryMaxBatchTransferResDTO)dto.xml2obj(strxml);	
	}
	

}
