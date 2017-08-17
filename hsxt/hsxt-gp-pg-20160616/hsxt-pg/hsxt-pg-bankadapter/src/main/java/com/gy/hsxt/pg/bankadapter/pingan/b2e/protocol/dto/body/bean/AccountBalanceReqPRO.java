package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.CcyCode;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants.CcyType;


/**
 * @author jbli
 * 1、继承BodyDTO
 * 2、注入bean实例名为bodyReq_4001
 * 3、与一般的构建器模式不同，仍保留有一个public的构造方法，主要是便于XStream反射生成对象
 */
@Component("bodyReq_4001")  
public class AccountBalanceReqPRO  extends BodyDTO{
	
	public AccountBalanceReqPRO(){}
	
	//账号 length <=14
	private String account;
	//钞汇标志 C 钞户, R汇户,默认为C
	private String ccyType;
	//货币类型  RMB 人民币,USD 美元，HKD 港币, 默认为RMB
	private String ccyCode;
	
	private AccountBalanceReqPRO(Builder builder) {
		this.account = builder.account;
		this.ccyType = builder.ccyType;
		this.ccyCode = builder.ccyCode;
	}	
	
	public String getAccount() {
		return account;
	}
	
	public String getCcyType() {
		return ccyType;
	}
	
	public String getCcyCode() {
		return ccyCode;
	}
	
	public static class Builder{
		//账号 length <=14
		private String account = "";
		//钞汇标志 C 钞户, R汇户,默认为C
		private String ccyType = CcyType.CASH.getValue();
		//货币类型  RMB 人民币,USD 美元，HKD 港币, 默认为RMB
		private String ccyCode = CcyCode.RMB.getValue();

		public Builder setAccount(String account) {
			this.account = account;
			return this;
		}

		public Builder setCcyType(String ccyType) {
			this.ccyType = ccyType;
			return this;
		}

		public Builder setCcyCode(String ccyCode) {
			this.ccyCode = ccyCode;
			return this;
		}

		public Builder(String account){
			this.account = account;
		}
		
		/**
		 * 返回一个带有初始化默认值的实例引用，用于编码时简化参数的设置 
		 * 设置了默认的钞汇标志、货币类型
		 * @return
		 */
		public AccountBalanceReqPRO build(){
			return new AccountBalanceReqPRO(this);
		}

	}

	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", AccountBalanceReqPRO.class);
		xStream.aliasField("Account", AccountBalanceReqPRO.class, "account");
		xStream.aliasField("CcyType", AccountBalanceReqPRO.class, "ccyType");
		xStream.aliasField("CcyCode", AccountBalanceReqPRO.class, "ccyCode");
		
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}

	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", AccountBalanceReqPRO.class);
		xStream.aliasField("Account", AccountBalanceReqPRO.class, "account");
		xStream.aliasField("CcyType", AccountBalanceReqPRO.class, "ccyType");
		xStream.aliasField("CcyCode", AccountBalanceReqPRO.class, "ccyCode");
		return xStream.fromXML(strXml);
	}
	
}
