/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Poi48 
 * @Description: 积分信息（积分比例、积分金额）  pos终端输出
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:09:24 
 * @version V1.0
 */
public class Poi48 {
	/**
	 * 积分比例，4位，报文放大10000倍
	 */
	private BigDecimal rate;
	/**
	 * 积分金额，12位，11-12位是小数位     ,消费金额*积分比例    
	 */
	private BigDecimal amt;
	/**
	 * 积分：外币交易，外币币种金额（2.0中存在）
	 * 互生币：互生币金额
	 * 定金：互生币金额
	 * 命名有歧义
	 */
	private BigDecimal hsbAmount;
	
	
	/**
	 * 剩余积分额
	 */
	private BigDecimal surplusHsbAmount;
	
	/**
	 * 定金业务序列号 pos中心定义的 12位数字
	 */
	private String earnestSeq;

	
	//start--added by liuzh on 2016-06-22
	/**
	 * 实付金额
	 */
	private BigDecimal actualPayAmount;
		
	/**
	 * 抵扣券张数
	 */
	private int deductVoucherCount;	
	//end--added by liuzh on 2016-06-22
	
	public Poi48(BigDecimal rate, BigDecimal amt) {
		this.rate = rate;
		this.amt = amt;
	}

	public Poi48(BigDecimal rate, BigDecimal amt, BigDecimal hsbAmount) {
		this.rate = rate;
		this.amt = amt;
		this.hsbAmount = hsbAmount;
	}
	
	
	/**
	 * 预付定金，存放互生币计量的定金金额 
	 * kend
	 * @param hsbAmount
	 */
	public Poi48(BigDecimal hsbAmount) {
		this.hsbAmount = hsbAmount;
	}
	
	/**
	 * 定金结算，积分比例+积分额+互生币计量的交易额+定金交易序号  
	 * kend
	 * @param rate
	 * @param amt
	 * @param hsbAmount
	 * @param earnestSeq
	 */
	public Poi48(BigDecimal rate, BigDecimal amt, BigDecimal hsbAmount, String earnestSeq) {
		this.rate = rate;
		this.amt = amt;
		this.hsbAmount = hsbAmount;
		this.earnestSeq = earnestSeq;
	}
	
	
	public Poi48(BigDecimal rate, BigDecimal amt, BigDecimal hsbAmount, BigDecimal surplusHsbAmount, 
																					String earnestSeq) {
		this.rate = rate;
		this.amt = amt;
		this.hsbAmount = hsbAmount;
		this.surplusHsbAmount = surplusHsbAmount;
		this.earnestSeq = earnestSeq;
	}
	

	public BigDecimal getRate() {
		return rate;
	}

	//public void setRate(double rate) {
	//    this.rate = rate;
	//}
	//
	public BigDecimal getAmt() {
		return amt;
	}

	//public void setAmt(double amt) {
	//    this.amt = amt;
	//}
	public BigDecimal getHsbAmount() {
		return hsbAmount;
	}

//	public void setHsbAmount(double hsbAmount) {
//		this.hsbAmount = hsbAmount;
//	}
	
	public String getEarnestSeq() {
		return earnestSeq;
	}

	public BigDecimal getSurplusHsbAmount() {
		return surplusHsbAmount;
	}

	public void setSurplusHsbAmount(BigDecimal surplusHsbAmount) {
		this.surplusHsbAmount = surplusHsbAmount;
	}


//	public void setEarnestSeq(int earnestSeq) {
//		this.earnestSeq = earnestSeq;
//	}
	
	
	//start--added by liuzh on 2016-06-22	
	public Poi48() {
		
	}
	public BigDecimal getActualPayAmount() {
		return actualPayAmount;
	}

	public void setActualPayAmount(BigDecimal actualPayAmount) {
		this.actualPayAmount = actualPayAmount;
	}

	public int getDeductVoucherCount() {
		return deductVoucherCount;
	}

	public void setDeductVoucherCount(int deductVoucherCount) {
		this.deductVoucherCount = deductVoucherCount;
	}

	
	
	//end--added by liuzh on 2016-06-22
}
