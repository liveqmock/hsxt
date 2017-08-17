package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.orm.BodyDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.PackageConstants;

/**
 * @author jbli
 * 1、继承BodyDTO
 * 2、注入bean实例名为bodyRes_4001
 * 3、与一般的构建器模式不同，仍保留有一个public的构造方法，主要是便于XStream反射生成对象
 */
@Component("bodyRes_4001")  
public class AccountBalanceResDTO  extends BodyDTO {
	
	public AccountBalanceResDTO(){}
	
	//账号 length <=14
	private String account ;
	//钞汇标志 C 钞户, R汇户,默认为C
	private String ccyType ;
	//货币类型  RMB 人民币,USD 美元，HKD 港币, 默认为RMB
	private String ccyCode ;
	//户名 <=60
	private String accountName ;
	//可用余额 定长报文：金额域: 13位(数量级为分)不足13为左补零 如0.99则表示为    0000000000099  XML格式的：以元为单位
	private String balance ;
	//账面余额 如为集团账户主/子账户，则为集团合约
	private String totalAmount ;
	//账户类型 1
	private String accountType ;
	//账户状态 1
	private String accountStatus ;
	//开户行名称
	private String bankName ;

	private AccountBalanceResDTO(Builder builder){
		 this.account = builder.account;
		 this.ccyType = builder.ccyType;
		 this.ccyCode = builder.ccyCode;
		 this.accountName = builder.accountName;
		 this.balance = builder.balance;
		 this.totalAmount = builder.totalAmount;
		 this.accountType = builder.accountType;
		 this.accountStatus = builder.accountStatus;
		 this.bankName = builder.bankName;		
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

	public String getAccountName() {
		return accountName;
	}

	public String getBalance() {
		return balance;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public String getBankName() {
		return bankName;
	}



	public static class Builder{
		public Builder(String account,String accountName){
			this.account = account;
			this.accountName = accountName;
		}
		
		//账号 length <=14
		private String account = "";
		//钞汇标志 C 钞户, R汇户,默认为C
		private String ccyType = "";
		//货币类型  RMB 人民币,USD 美元，HKD 港币, 默认为RMB
		private String ccyCode = "";
		//户名 <=60
		private String accountName = "";
		//可用余额 定长报文：金额域: 13位(数量级为分)不足13为左补零 如0.99则表示为    0000000000099  XML格式的：以元为单位
		private String balance = "";
		//账面余额 如为集团账户主/子账户，则为集团合约
		private String totalAmount = "";
		//账户类型 1
		private String accountType = "";
		//账户状态 1
		private String accountStatus = "";
		//开户行名称
		private String bankName = "";		
		
		public Builder setCcyType(String ccyType) {
			this.ccyType = ccyType;
			return this;
		}
		
		public Builder setCcyCode(String ccyCode) {
			this.ccyCode = ccyCode;
			return this;
		}

		public Builder setBalance(String balance) {
			this.balance = balance;
			return this;
		}

		public Builder setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
			return this;
		}

		public Builder setAccountType(String accountType) {
			this.accountType = accountType;
			return this;
		}

		public Builder setAccountStatus(String accountStatus) {
			this.accountStatus = accountStatus;
			return this;
		}

		public Builder setBankName(String bankName) {
			this.bankName = bankName;
			return this;
		}		
		
		/**
		 * 返回一个不带任何默认值的实例引用，用于解码
		 * @return
		 */
		public AccountBalanceResDTO build(){
			return  new AccountBalanceResDTO(this);
		}
	}

	@Override
	public String obj2xml() {
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", AccountBalanceResDTO.class);
		xStream.aliasField("account", AccountBalanceResDTO.class, "Account");
		xStream.aliasField("ccyType", AccountBalanceResDTO.class, "CcyType");
		xStream.aliasField("CcyCode", AccountBalanceResDTO.class, "ccyCode");
		xStream.aliasField("AccountName", AccountBalanceResDTO.class, "accountName");
		xStream.aliasField("Balance", AccountBalanceResDTO.class, "balance");
		xStream.aliasField("TotalAmount", AccountBalanceResDTO.class, "totalAmount");
		xStream.aliasField("AccountType", AccountBalanceResDTO.class, "accountType");
		xStream.aliasField("AccountStatus", AccountBalanceResDTO.class, "accountStatus");
		xStream.aliasField("BankName", AccountBalanceResDTO.class, "bankName");
		String returnValue =  PackageConstants.XML_SCHEMA_TITLE+xStream.toXML(this);
		return returnValue;
	}



	@Override
	public Object xml2obj(String strXml) {
		if(StringUtils.isEmpty(strXml)){
			return null;
		}
		xStream.omitField(BodyDTO.class, "xStream");
		xStream.alias("Result", AccountBalanceResDTO.class);
		xStream.aliasField("Account", AccountBalanceResDTO.class, "account");
		xStream.aliasField("CcyType", AccountBalanceResDTO.class, "ccyType");
		xStream.aliasField("CcyCode", AccountBalanceResDTO.class, "ccyCode");
		xStream.aliasField("AccountName", AccountBalanceResDTO.class, "accountName");
		xStream.aliasField("Balance", AccountBalanceResDTO.class, "balance");
		xStream.aliasField("TotalAmount", AccountBalanceResDTO.class, "totalAmount");
		xStream.aliasField("AccountType", AccountBalanceResDTO.class, "accountType");
		xStream.aliasField("AccountStatus", AccountBalanceResDTO.class, "accountStatus");
		xStream.aliasField("BankName", AccountBalanceResDTO.class, "bankName");
		return  xStream.fromXML(strXml);
	}	
	
	
	
	
	
	
	
	


	
}
