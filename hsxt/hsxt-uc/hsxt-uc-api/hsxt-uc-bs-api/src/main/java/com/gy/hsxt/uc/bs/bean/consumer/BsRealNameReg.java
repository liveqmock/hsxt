/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.bean.consumer;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Package: com.gy.hsxt.uc.bs.bean
 * @ClassName: BsRealNameRegInfo
 * @Description: 实名注册bean
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午3:50:29
 * @version V1.0
 */
public class BsRealNameReg implements Serializable {

	private static final long serialVersionUID = 26830670574929878L;

	private String custId;
	private String realName;
	private String certype;
	private String cerNo;
	private String countryCode;
	private String countryName;
	private String realNameStatus;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertype() {
		return certype;
	}
	public void setCertype(String certype) {
		this.certype = certype;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getRealNameStatus() {
		return realNameStatus;
	}
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}
	 public String toString(){
	        return JSONObject.toJSONString(this);
	    }
}
