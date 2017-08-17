/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans.param;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param
 * 
 *  File Name       : UploadResponse.java
 * 
 *  Creation Date   : 2015年5月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 上传响应对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UploadResponse {
	private String fileId;

	public UploadResponse() {
	}

	public UploadResponse(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
