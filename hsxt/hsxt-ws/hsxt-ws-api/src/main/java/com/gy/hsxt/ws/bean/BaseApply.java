/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.io.Serializable;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.common.WsErrorCode;

/**
 * 基本申请信息
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: BaseApply
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-4 下午4:02:56
 * @version V1.0
 */
public class BaseApply implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 客户ID **/
	private String custId;
	/** 互生号 **/
	private String hsResNo;
	/** 申请人姓名 **/
	private String proposerName;
	/** 申请人电话 **/
	private String proposerPhone;
	/** 申请人证件号码 **/
	private String proposerPapersNo;
	/** 申请日期 **/
	private String applyDate;
	/** 说明 **/
	private String explain;

	/**
	 * @return the 客户ID
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户ID
	 *            the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the 互生号
	 */
	public String getHsResNo() {
		return hsResNo;
	}

	/**
	 * @param 互生号
	 *            the hsResNo to set
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	/**
	 * @return the 申请人姓名
	 */
	public String getProposerName() {
		return proposerName;
	}

	/**
	 * @param 申请人姓名
	 *            the proposerName to set
	 */
	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	/**
	 * @return the 申请人电话
	 */
	public String getProposerPhone() {
		return proposerPhone;
	}

	/**
	 * @param 申请人电话
	 *            the proposerPhone to set
	 */
	public void setProposerPhone(String proposerPhone) {
		this.proposerPhone = proposerPhone;
	}

	/**
	 * @return the 申请人证件号码
	 */
	public String getProposerPapersNo() {
		return proposerPapersNo;
	}

	/**
	 * @param 申请人证件号码
	 *            the proposerPapersNo to set
	 */
	public void setProposerPapersNo(String proposerPapersNo) {
		this.proposerPapersNo = proposerPapersNo;
	}

	/**
	 * @return the 申请日期
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * @param 申请日期
	 *            the applyDate to set
	 */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * @return the 说明
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * @param 说明
	 *            the explain to set
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	public void basicValid() {
		if (isBlank(this.custId)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[custId]为空");
		}
		if (isBlank(this.hsResNo)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[hsResNo]为空");
		}
		if (isBlank(this.proposerName)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), "必传参数[proposerName]为空");
		}
		// if (isBlank(this.proposerPhone)) {
		// throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
		// "必传参数[proposerPhone]为空");
		// }
		// if (isBlank(this.proposerPapersNo)) {
		// throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(),
		// "必传参数[proposerPapersNo]为空");
		// }
	}
}
