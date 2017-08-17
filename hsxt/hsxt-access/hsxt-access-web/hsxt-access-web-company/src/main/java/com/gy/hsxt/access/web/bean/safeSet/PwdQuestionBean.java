/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeSet;

import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***
 * 密保问题实体维护类
 * 
 * @Package: com.gy.hsxt.access.web.bean.safeSet
 * @ClassName: PwdQuestionBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-27 下午4:41:03
 * @version V1.0
 */
public class PwdQuestionBean extends AsPwdQuestion {

	private static final long serialVersionUID = 346987390595388436L;

	/**
	 * 操作员客户号
	 */
	private String custId;
	/**
	 * 企业客户号
	 */
	private String entCustId;

	public String getCustId()
	{
		return custId;
	}

	public void setCustId(String custId)
	{
		this.custId = custId;
	}

	/**
	 * 数据验证
	 */
	public void validateEmptyData()
	{
		RequestUtil.verifyParamsIsNotEmpty(
			new Object[] { this.getAnswer(), ASRespCode.EW_RESTRICTIONS_INVALID },
			new Object[] { this.getQuestion(), ASRespCode.EW_RESTRICTIONS_ANSWER_INVALID });
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

}
