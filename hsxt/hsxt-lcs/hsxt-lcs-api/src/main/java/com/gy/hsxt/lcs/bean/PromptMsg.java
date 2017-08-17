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
 *  File Name       : PromptMsg.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 提示信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class PromptMsg implements Serializable {

	private static final long serialVersionUID = -3034182775870496470L;

	// 语言代码
	private String languageCode;

	// 提示信息代码
	private String promptCode;

	// 提示信息名称
	private String promptMsg;

	// 删除标识
	private boolean delFlag;

	// 记录版本号
	private long version;

	public PromptMsg() {
	}

	public PromptMsg(String languageCode, String promptCode) {
		this.languageCode = languageCode;
		this.promptCode = promptCode;
	}
	
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getPromptCode() {
		return promptCode;
	}

	public void setPromptCode(String promptCode) {
		this.promptCode = promptCode;
	}

	public String getPromptMsg() {
		return promptMsg;
	}

	public void setPromptMsg(String promptMsg) {
		this.promptMsg = promptMsg;
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
