package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

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
@Component("bodyRes_4017")
public class QryBankNodeCodeResDTO extends BodyDTO {

	public QryBankNodeCodeResDTO() {}

	// 银行代码	Char(14)	Y
	private String bankNo;
	// 银行名称	Char(30)	N
	private String bankName;
	// 返回的记录数	9(3)	Y
	private String size;
	//记录集合 最多30笔
	private List<QryBankNodeCodeResDetailDTO> list;

	public String getBankNo() {
		return bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public String getSize() {
		return size;
	}

	public List<QryBankNodeCodeResDetailDTO> getList() {
		return list;
	}

	private QryBankNodeCodeResDTO(Builder builder) {
		this.bankNo = builder.bankNo;
		this.bankName = builder.bankName;
		this.size = builder.size;
		this.list = builder.list;
	}
	
	public static class Builder {

		public Builder(String bankNo) {
			this.bankNo = bankNo;
		}

		// 银行代码	Char(14)	Y
		private String bankNo;
		// 银行名称	Char(30)	N
		private String bankName;
		// 返回的记录数	9(3)	Y
		private String size;
		//记录集合 最多30笔
		private List<QryBankNodeCodeResDetailDTO> list;

		
		public Builder setBankName(String bankName) {
			this.bankName = bankName;
			return this;
		}

		public Builder setSize(String size) {
			this.size = size;
			return this;
		}

		public Builder setList(List<QryBankNodeCodeResDetailDTO> list) {
			this.list = list;
			return this;
		}
		
		public QryBankNodeCodeResDTO build(){
			return new QryBankNodeCodeResDTO(this);
		}
		
	}
	             
	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(QryBankNodeCodeResDTO.class, "list");
		xStream.alias("Result", QryBankNodeCodeResDTO.class);
		xStream.alias("list", QryBankNodeCodeResDetailDTO.class);
		
		xStream.aliasField("BankNo", QryBankNodeCodeResDTO.class, "bankNo");
		xStream.aliasField("BankName", QryBankNodeCodeResDTO.class, "bankName");
		xStream.aliasField("size", QryBankNodeCodeResDTO.class, "size");
		
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}

		xStream.omitField(BodyDTO.class, "xStream");
		xStream.addImplicitArray(QryBankNodeCodeResDTO.class, "list");
		
		xStream.alias("Result", QryBankNodeCodeResDTO.class);
		xStream.alias("list", QryBankNodeCodeResDetailDTO.class);
		
		xStream.aliasField("BankNo", QryBankNodeCodeResDTO.class, "bankNo");
		xStream.aliasField("BankName", QryBankNodeCodeResDTO.class, "bankName");
		xStream.aliasField("size", QryBankNodeCodeResDTO.class, "size");

		xStream.aliasField("NodeName", QryBankNodeCodeResDetailDTO.class, "nodeName");
		xStream.aliasField("NodeCode", QryBankNodeCodeResDetailDTO.class, "nodeCode");

		return  xStream.fromXML(strXml);
	}

}
