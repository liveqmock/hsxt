package com.gy.hsxt.gp.mapper.vo;

import java.util.Date;

import com.gy.hsxt.gp.common.utils.StringUtils;

public class TGpCheckAccountsFile {
	
	private Integer fileId;

	private String fileName;

	private String fileSavePath;

	private Integer fileStatus;

	private Integer checkType;

	private Date checkDate;

	private String ownerNode;

	private Date createdDate;

	private Date updatedDate;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = StringUtils.avoidNull(fileSavePath).trim();
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getOwnerNode() {
		return ownerNode;
	}

	public void setOwnerNode(String ownerNode) {
		this.ownerNode = ownerNode == null ? null : ownerNode.trim();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}