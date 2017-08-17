/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-api
 * 
 *  Package Name    : com.gy.hsxt.lcs.bean
 * 
 *  File Name       : ErrorMsg.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 错误信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class ErrorMsg implements Serializable {

	private static final long serialVersionUID = 3753192007646678767L;

	// 语言代码
	private String languageCode;

	// 错误信息代码
	private int errorCode;

	// 错误信息名称
	private String errorMsg;

	// 删除标识
	private boolean delFlag;

	// 记录版本号
	private long version;

	
	public ErrorMsg() {
	}
	public ErrorMsg(String languageCode, int errorCode) {
		this.languageCode = languageCode;
		this.errorCode = errorCode;
	}
	
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
