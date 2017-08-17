/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext;

import java.util.Date;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext
 * 
 *  File Name       : SimpleFileMetaDataDel.java
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
public class SimpleFileMetaDataDel {

	private Long id;
	private String fileId;
	private String fileStorageId;
	private String fileSuffix;
	private Boolean isAnonymous;
	private Byte fileStatus;
	private int failedCounts;
	private Date lastFailedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileStorageId() {
		return fileStorageId;
	}

	public void setFileStorageId(String fileStorageId) {
		this.fileStorageId = fileStorageId;
	}

	public Boolean getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public Byte getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Byte fileStatus) {
		this.fileStatus = fileStatus;
	}

	public int getFailedCounts() {
		return failedCounts;
	}

	public void setFailedCounts(int failedCounts) {
		this.failedCounts = failedCounts;
	}

	public Date getLastFailedDate() {
		return lastFailedDate;
	}

	public void setLastFailedDate(Date lastFailedDate) {
		this.lastFailedDate = lastFailedDate;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

}
