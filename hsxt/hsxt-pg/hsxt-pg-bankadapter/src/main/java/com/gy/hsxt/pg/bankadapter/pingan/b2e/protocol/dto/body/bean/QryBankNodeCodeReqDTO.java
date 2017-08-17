package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author jbli
 *
 */
@Component("bodyReq_4017")
public class QryBankNodeCodeReqDTO  extends BodyDTO{
	
	public QryBankNodeCodeReqDTO(){}

	// 银行代码	Char(14)	Y	代码参考：商业银行-省市代码对照表(联行号查询交易使用).xls
	private String bankNo;
	// 银行名称	Char(30)	N
	private String bankName;
	// 网点名称关键字	Char(30)	Y
	private String keyWord;

	public String getBankNo() {
		return bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public String getKeyWord() {
		return keyWord;
	}

	private QryBankNodeCodeReqDTO(Builder builder) {
		this.bankNo = builder.bankNo;
		this.bankName = builder.bankName;
		this.keyWord = builder.keyWord;
	}
	
	public static class Builder{
		public Builder(String bankNo,String bankName,String keyWord){
			this.bankNo = bankNo;
			this.bankName = bankName;
			this.keyWord =keyWord;
		}
		
		// 银行代码
		private String bankNo;
		// 银行名称
		private String bankName;
		// 网点名称关键字
		private String keyWord;
		
		public QryBankNodeCodeReqDTO build(){
			return new QryBankNodeCodeReqDTO(this);
		}
		
	}	
	
	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", QryBankNodeCodeReqDTO.class);
		xStream.aliasField("BankNo", QryBankNodeCodeReqDTO.class, "bankNo");
		xStream.aliasField("BankName", QryBankNodeCodeReqDTO.class, "bankName");
		xStream.aliasField("KeyWord", QryBankNodeCodeReqDTO.class, "keyWord");
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", QryBankNodeCodeReqDTO.class);
		xStream.aliasField("BankNo", QryBankNodeCodeReqDTO.class, "bankNo");
		xStream.aliasField("BankName", QryBankNodeCodeReqDTO.class, "bankName");
		xStream.aliasField("KeyWord", QryBankNodeCodeReqDTO.class, "keyWord");
		return xStream.fromXML(strXml);
	}
}
