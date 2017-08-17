/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: TradeType 
 * @Description: 报文60域 交易类型信息（终端类型码、批次号、网络管理信息码）
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:11:20 
 * @version V1.0
 */
public class TradeType {
	
	/**
	 * 终端类型码
	 */
	private String termTypeCode;
	
	/**
	 * 批次号
	 */
	private String batNo;
	
	/**
	 * 网络管理信息码 签到、签退、批结算、批上送
	 */
	private String netCode;


	public TradeType(String termTypeCode, String batNo, String netCode) {
		this.termTypeCode = termTypeCode;
		this.batNo = batNo;
		this.netCode = netCode;
	}

	public String getTermTypeCode() {
		return termTypeCode;
	}

	public void setTermTypeCode(String termTypeCode) {
		this.termTypeCode = termTypeCode;
	}

	public String getBatNo() {
		return batNo;
	}

	public void setBatNo(String batNo) {
		this.batNo = batNo;
	}

	public String getNetCode() {
		return netCode;
	}

	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}
}
