
package com.gy.hsxt.access.pos.model;

/** 
 * @Description: 查询积分交易记录的请求

 * @author: liuzh 
 * @createDate: 2016年6月23日 下午4:00:48
 * @version V1.0 
 */

public class PointOrdersSearchReq {
	/**
	 * 最近可查询天数.
	 */
	private String lastDays;

	public String getLastDays() {
		return lastDays;
	}

	public void setLastDays(String lastDays) {
		this.lastDays = lastDays;
	}

	public PointOrdersSearchReq(String lastDays) {
		super();
		this.lastDays = lastDays;
	}

}


