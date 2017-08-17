/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans.param;

import java.util.List;

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
public class UploadBatchResponse {

	private List<String> fileIds;

	public UploadBatchResponse() {
	}

	public UploadBatchResponse(List<String> fileIds) {
		this.fileIds = fileIds;
	}

	public List<String> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
	}

}
