/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;
/**
 * POS积分比例信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsPosPointRateVersion 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-12-31 下午6:39:38 
 * @version V1.0
 */
public class AsPosPointRate implements Serializable{
	private static final long serialVersionUID = -5393816863765024732L;
	private String version = "0";
	private String[] rate;
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the rate
	 */
	public String[] getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(String[] rate) {
		this.rate = rate;
	}
	
	
}
