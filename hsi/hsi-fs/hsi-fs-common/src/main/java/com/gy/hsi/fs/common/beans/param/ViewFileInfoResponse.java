/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans.param;

import com.gy.hsi.fs.common.beans.FileMetaInfo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param
 * 
 *  File Name       : ViewFileInfoResponse.java
 * 
 *  Creation Date   : 2015年5月26日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : “查看文件信息”响应参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ViewFileInfoResponse extends FileMetaInfo {

	public ViewFileInfoResponse() {
	}

	public ViewFileInfoResponse(String fileId) {
		super(fileId);
	}
}
