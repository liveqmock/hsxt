/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.bean;

import java.io.Serializable;

import com.gy.hsxt.pg.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.bean
 * 
 *  File Name       : RequestProxyData.java
 * 
 *  Creation Date   : 2015-10-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : RequestProxyData
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RequestProxyData implements Serializable {
	private static final long serialVersionUID = -1692897612255664507L;
	
	private String formHtml;
	private String message;

	public RequestProxyData() {
	}
	
	public RequestProxyData(String formHtml, String message) {
		this.formHtml = formHtml;
		this.message = message;
	}

	public String getFormHtml() {
		return StringUtils.avoidNull(formHtml);
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}

	public String getMessage() {
		return StringUtils.avoidNull(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
