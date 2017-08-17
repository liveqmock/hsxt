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
import com.gy.hsi.fs.common.constant.HttpRequestParam.SetPermissionRequestKey;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param
 * 
 *  File Name       : SetPermissionRequest.java
 * 
 *  Creation Date   : 2015年5月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : “设置文件权限”请求参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SetPermissionRequest extends ParentRequest {

	private String filePermission;

	public SetPermissionRequest() {
	}

	public SetPermissionRequest(HttpServletRequest request) {
		super(request);
		
		this.filePermission = request
				.getParameter(SetPermissionRequestKey.FILE_PERMISSION);
	}

	public String getFilePermission() {
		return filePermission;
	}

	public void setFilePermission(String filePermission) {
		this.filePermission = filePermission;
	}

}
