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
 *  File Name       : ResNoRoute.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 个人平台路由
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class ResNoRoute implements Serializable {

	private static final long serialVersionUID = 4664554131864905084L;

	/** 服务资源号前5位 */
	private String resNo;

	/** 平台代码  */
	private String platNo;

	/** 删除标识 */
	private boolean delFlag;

	/** 记录版本号 */
	private long version;

	public ResNoRoute() {
	}

	public ResNoRoute(String resNo) {
		this.resNo = resNo;
	}
	
	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getPlatNo() {
		return platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
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
