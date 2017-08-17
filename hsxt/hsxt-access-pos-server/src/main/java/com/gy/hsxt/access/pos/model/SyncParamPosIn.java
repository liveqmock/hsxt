/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

/**
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: SyncParamPosIn 
 * @Description: 同步参数
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:10:55 
 * @version V1.0
 */
public class SyncParamPosIn{

	
	private String baseInfoVersion;//基础信息版本号 N4
	private String pointInfoVersion;//积分信息版本号 N4
	private String currencyInfoVersion;//货币信息版本号 N4
	private String countryCodeVersion;//国家代码版本号 N4
	private String entInfoVersion;//企业信息版本号 N4 （pos机3.0新增类别）kend
	//start--added by liuzh on 2016-06-24
	private String deductionVoucherVersion; //抵扣券版本号 N4 （pos机3.01新增类别）
	//end--added by liuzh on 2016-06-24
	
	
	public String getPointInfoVersion() {
		return pointInfoVersion;
	}
	public void setPointInfoVersion(String pointInfoVersion) {
		this.pointInfoVersion = pointInfoVersion;
	}
	public String getBaseInfoVersion() {
		return baseInfoVersion;
	}
	public void setBaseInfoVersion(String baseInfoVersion) {
		this.baseInfoVersion = baseInfoVersion;
	}
	public String getCurrencyInfoVersion() {
		return currencyInfoVersion;
	}
	public void setCurrencyInfoVersion(String currencyInfoVersion) {
		this.currencyInfoVersion = currencyInfoVersion;
	}
	public String getCountryCodeVersion() {
		return countryCodeVersion;
	}
	public void setCountryCodeVersion(String countryCodeVersion) {
		this.countryCodeVersion = countryCodeVersion;
	}
	public String getEntInfoVersion() {
		return entInfoVersion;
	}
	public void setEntInfoVersion(String entInfoVersion) {
		this.entInfoVersion = entInfoVersion;
	}
	public String getDeductionVoucherVersion() {
		return deductionVoucherVersion;
	}
	public void setDeductionVoucherVersion(String deductionVoucherVersion) {
		this.deductionVoucherVersion = deductionVoucherVersion;
	}


}
