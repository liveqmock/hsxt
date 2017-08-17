/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.beans;

import java.io.Serializable;

import com.gy.hsi.ds.job.beans.bo.JobStatus;

public class TmpJobStatus extends JobStatus implements Serializable {

	/** 自动生成的序列号 */
	private static final long serialVersionUID = 6219549366665155172L;

	private String fontJobServiceGroup;

	private String fontJobServiceGroupList;

	public String getFontJobServiceGroup() {
		return fontJobServiceGroup;
	}

	public void setFontJobServiceGroup(String fontJobServiceGroup) {
		this.fontJobServiceGroup = fontJobServiceGroup;
	}

	public String getFontJobServiceGroupList() {
		return fontJobServiceGroupList;
	}

	public void setFontJobServiceGroupList(String fontJobServiceGroupList) {
		this.fontJobServiceGroupList = fontJobServiceGroupList;
	}
}
