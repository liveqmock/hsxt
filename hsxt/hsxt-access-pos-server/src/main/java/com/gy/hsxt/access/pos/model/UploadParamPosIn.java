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
 * @ClassName: UploadParamPosIn 
 * @Description: 上传参数 积分比例
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:11:30 
 * @version V1.0
 */
public class UploadParamPosIn {

	private int pointRateCount;//积分比例个数

	private BigDecimal[] pointRates;//积分比例

	public UploadParamPosIn(int pointRateCount, BigDecimal[] pointRates) {
		this.pointRateCount = pointRateCount;
		this.pointRates = pointRates;
	}

	public BigDecimal[] getPointRates() {
		return pointRates;
	}

	public int getPointRateCount() {
		return pointRateCount;
	}

	public void setPointRateCount(int pointRateCount) {
		this.pointRateCount = pointRateCount;
	}

	public void setPointRates(BigDecimal[] pointRates) {
		this.pointRates = pointRates;
	}

}
