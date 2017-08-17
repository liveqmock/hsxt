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
package com.gy.hsxt.rp.bean;

import java.io.Serializable;

/**
 * 账户余额对象
 * @author 作者 : maocan
 * @ClassName: 类名:ReportsInvoiceValueStatisticsInfo
 * @Description: TODO
 * @createDate 创建时间: 2016-2-18 下午2:35:52
 * @version 1.0
 */
public class ReportsInvoiceValueStatisticsInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7054995706735741968L;

	/**	开始时间 */
	private   String       beginDate;
	
	/**	结束时间 */
	private   String       endDate;
	
	/** 汇率 */
	private	   String		exchangeRate;

	/**
	 * @return the 开始时间
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param 开始时间 the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the 结束时间
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 结束时间 the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 汇率
	 */
	public String getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param 汇率 the exchangeRate to set
	 */
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
}
