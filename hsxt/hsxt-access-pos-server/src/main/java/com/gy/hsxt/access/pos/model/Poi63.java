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
 * @ClassName: Poi63 
 * @Description: 积分业务使用。pos终端输出。
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:09:36 
 * @version V1.0
 */
public class Poi63 {
	/**
	 * 国际信用卡公司代码
	 */
	private String iccCode;
	/**
	 * 本次积分数
	 */
	private BigDecimal val;

	public Poi63(String iccCode, BigDecimal val) {
		this.iccCode = iccCode;
		this.val = val;
	}

	public String getIccCode() {
		return iccCode;
	}

    //public void setIccCode(String iccCode) {
    //    this.iccCode = iccCode;
    //}
    //
	public BigDecimal getVal() {
		return val;
	}

    //public void setVal(double val) {
    //    this.val = val;
    //}
    //
}
