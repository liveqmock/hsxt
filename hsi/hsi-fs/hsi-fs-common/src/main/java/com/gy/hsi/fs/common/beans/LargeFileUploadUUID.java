/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans;

import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans
 * 
 *  File Name       : LargeFileUploadUUID.java
 * 
 *  Creation Date   : 2015年5月30日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 标示大文件上传的唯一ID
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class LargeFileUploadUUID {
	private String uuid;

	private LargeFileUploadUUID(String uuid) {
		this.uuid = uuid;
	}

	public static LargeFileUploadUUID create() {
		return new LargeFileUploadUUID(StringUtils.randomUUID());
	}
	
	public static LargeFileUploadUUID create(String uuid) {
		return new LargeFileUploadUUID(uuid);
	}

	public String getValue() {
		return this.uuid;
	}
}
