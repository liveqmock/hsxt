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
import com.gy.hsi.fs.common.constant.FsApiConstant.Value;
import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.constant.HttpRequestParam.DeleteFileRequestKey;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param
 * 
 *  File Name       : DeleteFileRequest.java
 * 
 *  Creation Date   : 2015年5月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : “删除文件”请求参数
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DeleteFileRequest extends ParentRequest {

	/** 是否批量删除 **/
	private boolean isBatchDel = false;

	public DeleteFileRequest() {
	}

	public DeleteFileRequest(HttpServletRequest request) {
		super(request);

		this.isBatchDel = String.valueOf(Value.VALUE_1).equals(
				request.getParameter(DeleteFileRequestKey.IS_BATCH_DEL));
	}

	public String[] getFileIdList() {
		if(StringUtils.isEmpty(fileId)) {
			return null;
		}
		
		// 批量删除操作
		if (isBatchDel()) {
			return this.fileId.split(FsConstant.COMMA_CHAR);
		}

		return new String[] { this.fileId };
	}

	public boolean isBatchDel() {
		return isBatchDel;
	}
}
