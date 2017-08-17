package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.QueryType;

/**
 * @author jbli
 *
 */
@Component("bodyReq_4015")  
public class QryMaxBatchTransferReqDTO  extends BodyDTO {
	
	public QryMaxBatchTransferReqDTO(){}
	
	//批量转账凭证号	C(20)	必须	批量转账发起时上送的凭证号
	private String origThirdVoucher;	
	//查询类型	C(1)	非必须，默认为0	0或者空：全部；
	private String queryType;	        
	//每页笔数	C(10)	非必须，默认单次返回上限500笔	每次查询返回的笔数，用于分页查询。建议为50，最大500； 单批多次分页查询每页笔数必须保持一致。
	private String pageCts;	         
	//页码	C(10)	非必须，默认为1	当前查询的页码，用于分页查询。从1开始递增
	private String pageNo;	     
	//记录集合
	private List<QryMaxBatchTransferReqDetailDTO> list;
	
	public List<QryMaxBatchTransferReqDetailDTO> getList() {
		return list;
	}

	public String getOrigThirdVoucher() {
		return origThirdVoucher;
	}

	public String getQueryType() {
		return queryType;
	}

	public String getPageCts() {
		return pageCts;
	}

	public String getPageNo() {
		return pageNo;
	}

	private QryMaxBatchTransferReqDTO(Builder builder){
		this.origThirdVoucher = builder.origThirdVoucher;
		this.queryType = builder.queryType;
		this.pageCts = builder.pageCts;
		this.pageNo = builder.pageNo;
		this.list = builder.list;
	}
	
	public static class Builder{
		//批量转账凭证号	C(20)	必须	批量转账发起时上送的凭证号
		private String origThirdVoucher = "";	
		//查询类型	C(1)	非必须，默认为0	0或者空：全部；
		private String queryType = QueryType.ALL.getValue();	        
		//每页笔数	C(10)	非必须，默认单次返回上限500笔	每次查询返回的笔数，用于分页查询。建议为50，最大500； 单批多次分页查询每页笔数必须保持一致。
		private String pageCts = "500";	         
		//页码	C(10)	非必须，默认为1	当前查询的页码，用于分页查询。从1开始递增
		private String pageNo = "1";	    
		//记录集合
		private List<QryMaxBatchTransferReqDetailDTO> list;
		
		public Builder(String origThirdVoucher,List<QryMaxBatchTransferReqDetailDTO> list){
			this.origThirdVoucher = origThirdVoucher;
			this.list = list;
		}

		public Builder setQueryType(String queryType) {
			this.queryType = queryType;
			return this;
		}

		public Builder setPageCts(String pageCts) {
			this.pageCts = pageCts;
			return this;
		}

		public Builder setPageNo(String pageNo) {
			this.pageNo = pageNo;
			return this;
		}
		
		public QryMaxBatchTransferReqDTO build(){
			return new QryMaxBatchTransferReqDTO(this);
		}
		
	}

	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(QryMaxBatchTransferReqDTO.class, "list");
		xStream.alias("Result", QryMaxBatchTransferReqDTO.class);
		xStream.alias("HOResultSet4015R", QryMaxBatchTransferReqDetailDTO.class);

		xStream.aliasField("OrigThirdVoucher",QryMaxBatchTransferReqDTO.class,"origThirdVoucher");
		xStream.aliasField("QueryType",QryMaxBatchTransferReqDTO.class,"queryType");
		xStream.aliasField("PageCts",QryMaxBatchTransferReqDTO.class,"pageCts");
		xStream.aliasField("PageNo",QryMaxBatchTransferReqDTO.class,"pageNo");
		
		xStream.aliasField("SThirdVoucher",QryMaxBatchTransferReqDetailDTO.class,"sThirdVoucher");

		
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}

		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(QryMaxBatchTransferReqDTO.class, "list");
		xStream.alias("Result", QryMaxBatchTransferReqDTO.class);
		xStream.alias("HOResultSet4015R", QryMaxBatchTransferReqDetailDTO.class);

		xStream.aliasField("OrigThirdVoucher",QryMaxBatchTransferReqDTO.class,"origThirdVoucher");
		xStream.aliasField("QueryType",QryMaxBatchTransferReqDTO.class,"queryType");
		xStream.aliasField("PageCts",QryMaxBatchTransferReqDTO.class,"pageCts");
		xStream.aliasField("PageNo",QryMaxBatchTransferReqDTO.class,"pageNo");
		
		xStream.aliasField("SThirdVoucher",QryMaxBatchTransferReqDetailDTO.class,"sThirdVoucher");
		
		return  xStream.fromXML(strXml);
	}
	

}
