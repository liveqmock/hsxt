package com.gy.hsxt.access.pos.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***************************************************************************
 * <PRE>
 *  Project Name    : PosServer
 * 
 *  Package Name    : com.gy.point.model
 * 
 *  File Name       : Country.java
 * 
 *  Creation Date   : 2014-6-11
 * 
 *  Author          : huangfuhua
 * 
 *  Purpose         : 国家
 * 
 * 
 *  History         : 
 * 
 * </PRE>
 ***************************************************************************/
public class Country {
	/**
	 * 国家代码序号 N3
	 */
	private int countryCodeIndex;
	/**
	 * 国家代码 N3
	 */
	private String countryCode;
	/**
	 * 操作码 N1
	 */
	private String operaCode;

	public Country() {
	}

	public Country(int countryCodeIndex, String countryCode, String operaCode) {
		this.countryCodeIndex = countryCodeIndex;
		this.countryCode = countryCode;
		this.operaCode = operaCode;
	}

	public int getCountryCodeIndex() {
		return countryCodeIndex;
	}

	public void setCountryCodeIndex(int countryCodeIndex) {
		this.countryCodeIndex = countryCodeIndex;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getOperaCode() {
		return operaCode;
	}

	public void setOperaCode(String operaCode) {
		this.operaCode = operaCode;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
