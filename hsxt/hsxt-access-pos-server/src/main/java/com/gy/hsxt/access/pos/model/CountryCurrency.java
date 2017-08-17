package com.gy.hsxt.access.pos.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***************************************************************************
 * <PRE>
 *  Project Name    : PosServer
 * 
 *  Package Name    : com.gy.point.model
 * 
 *  File Name       : Currency.java
 * 
 *  Creation Date   : 2014-6-11
 * 
 *  Author          : huangfuhua
 * 
 *  Purpose         : 国家货币
 *  
 * 
 *  History         : 
 * 
 * </PRE>
 ***************************************************************************/
public class CountryCurrency {
	
	private int countryCurrId;
	/**
	 * 货币代码序号 N2
	 */
	private int currencyCodeIndex;
	/**
	 * 货币代码 N3
	 */
	private String currencyId;
	/**
	 * 这里指货币英文名称 ANS10
	 */
	private String currencyCode;
	/**
	 * 货币版本号
	 */
	private int version;
	
	public CountryCurrency() {
	}

	public CountryCurrency(int currencyCodeIndex, String currencyId, String currencyCode) {
		this.currencyCodeIndex = currencyCodeIndex;
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
	}

	
	public int getCountryCurrId() {
		return countryCurrId;
	}

	public void setCountryCurrId(int countryCurrId) {
		this.countryCurrId = countryCurrId;
	}

	public int getCurrencyCodeIndex() {
		return currencyCodeIndex;
	}

	public void setCurrencyCodeIndex(int currencyCodeIndex) {
		this.currencyCodeIndex = currencyCodeIndex;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
