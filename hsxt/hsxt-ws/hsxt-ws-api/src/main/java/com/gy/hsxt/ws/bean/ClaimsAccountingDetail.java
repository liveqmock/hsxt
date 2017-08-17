/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.bean;

import java.util.List;

/**
 * 理赔核算单详细信息
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: ClaimsAccountingDetail
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-14 下午5:01:04
 * @version V1.0
 */
public class ClaimsAccountingDetail extends ClaimsAccountingRecord {

	private static final long serialVersionUID = 1L;

	/** 理赔核算单 理赔明细列表 */
	private List<MedicalDetail> detailList;

	/**
	 * @return the 理赔核算单理赔明细列表
	 */
	public List<MedicalDetail> getDetailList() {
		return detailList;
	}

	/**
	 * @param 理赔核算单理赔明细列表
	 *            the detailList to set
	 */
	public void setDetailList(List<MedicalDetail> detailList) {
		this.detailList = detailList;
	}

}
