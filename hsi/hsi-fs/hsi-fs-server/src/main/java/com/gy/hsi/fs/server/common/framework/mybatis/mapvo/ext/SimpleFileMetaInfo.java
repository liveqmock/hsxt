/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext;

import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext
 * 
 *  File Name       : SimpleFileMetaInfo.java
 * 
 *  Creation Date   : 2015年5月20日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 简洁文件索引数据信息(为了提高性能, 只保留必要字段)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SimpleFileMetaInfo {

	private String fileStorageId;
	private String filePermission;
	private String ownerUserId;
	private String fileSuffix;
	private boolean isAnonymous;

	public String getFileStorageId() {
		return StringUtils.avoidNull(fileStorageId);
	}

	public void setFileStorageId(String fileStorageId) {
		this.fileStorageId = fileStorageId;
	}

	public String getFilePermission() {
		return StringUtils.avoidNull(filePermission);
	}

	public void setFilePermission(String filePermission) {
		this.filePermission = filePermission;
	}

	public String getOwnerUserId() {
		return StringUtils.avoidNull(ownerUserId);
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getFileSuffix() {
		return StringUtils.avoidNull(fileSuffix);
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public boolean isAnonymous() {
		return isAnonymous;
	}

	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

}
