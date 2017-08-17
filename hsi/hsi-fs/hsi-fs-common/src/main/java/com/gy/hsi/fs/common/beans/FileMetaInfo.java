/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans;

import java.util.Date;

import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileStatus;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans
 * 
 *  File Name       : FileMetaInfo.java
 * 
 *  Creation Date   : 2015年6月3日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件元数据
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FileMetaInfo {
	private String fileId;

	private String fileName;
	private String fileSuffix;

	private FilePermission filePermission;
	private FileStatus fileStatus;

	private Long fileSizeBytes;
	private Boolean isAnonyUpload;
	private Boolean isUploadedByMe = false; // 是否为自己上传 的文件

	private Date uploadDate;

	public FileMetaInfo() {
	}

	public FileMetaInfo(String fileId) {
		this.fileId = fileId;
	}

	public String getfileId() {
		return fileId;
	}

	public void setfileId(String fileId) {
		this.fileId = fileId;
	}

	public FilePermission getFilePermission() {
		return filePermission;
	}

	public void setFilePermission(String filePermission) {
		this.filePermission = FilePermission.valueOf(filePermission);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public Long getFileSizeBytes() {
		return fileSizeBytes;
	}

	public void setFileSizeBytes(Long fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}

	public FileStatus getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(FileStatus fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Boolean getIsAnonyUpload() {
		return isAnonyUpload;
	}

	public void setIsAnonyUpload(Boolean isAnonyUpload) {
		this.isAnonyUpload = isAnonyUpload;
	}

	public Boolean getIsUploadedByMe() {
		return isUploadedByMe;
	}

	public void setIsUploadedByMe(Boolean isUploadedByMe) {
		this.isUploadedByMe = isUploadedByMe;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public void setFilePermission(FilePermission filePermission) {
		this.filePermission = filePermission;
	}

}
