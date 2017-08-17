/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans.param;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsi.fs.common.beans.param.parent.ParentRequest;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param
 * 
 *  File Name       : ViewFileInfoRequest.java
 * 
 *  Creation Date   : 2015年5月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : “查看文件信息”请求参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ViewFileInfoRequest extends ParentRequest {

	public ViewFileInfoRequest() {
	}

	public ViewFileInfoRequest(HttpServletRequest request) {
		super(request);
	}
}
