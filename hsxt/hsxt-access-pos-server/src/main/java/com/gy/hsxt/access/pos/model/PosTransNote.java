/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author kend
 *
 */
public class PosTransNote implements Serializable {

	private static final long serialVersionUID = 2562954210055698420L;
	
	private String posBizSeq;
	private BigDecimal transAmount;
	private String transDateTime;
	
	

	public PosTransNote(String posBizSeq) {
		this.setPosBizSeq(posBizSeq);
	}

	public PosTransNote(String originNo, BigDecimal transAmount) {
		this.setPosBizSeq(originNo);
		this.setTransAmount(transAmount);
	}
	
	public PosTransNote(String originNo, BigDecimal transAmount, String transDateTime) {
		this.setPosBizSeq(originNo);
		this.setTransAmount(transAmount);
		this.setTransDateTime(transDateTime);
	}

	public String getPosBizSeq() {
		return posBizSeq;
	}

	public void setPosBizSeq(String posBizSeq) {
		this.posBizSeq = posBizSeq;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}

	

	
}
