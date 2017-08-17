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
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param
 * 
 *  File Name       : DownloadRequest.java
 * 
 *  Creation Date   : 2015年5月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 下载请求参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DownloadRequest extends ParentRequest {
	/** 文件后缀 **/
	protected String fileSuffix = null;

	/** 是否使用上传时使用的文件名称 **/
	private boolean useSavedFileName = false;

	public DownloadRequest() {
	}

	public DownloadRequest(HttpServletRequest request) {
		super(request);
		
		this.init(request);
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public boolean isUseSavedFileName() {
		return useSavedFileName;
	}

	/**
	 * 初始化
	 * 
	 * @param request
	 */
	private void init(HttpServletRequest request) {
		String[] resultArry = parseFileIdAndSuffix(request);

		if (null != resultArry) {
			if (2 <= resultArry.length) {
				this.fileSuffix = resultArry[1];
			}
		}

		this.useSavedFileName = StringUtils.str2Bool(request
				.getParameter("useSavedFileName"));
	}
}
