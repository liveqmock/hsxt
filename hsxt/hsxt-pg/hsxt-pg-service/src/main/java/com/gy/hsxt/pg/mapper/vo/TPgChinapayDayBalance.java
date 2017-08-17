package com.gy.hsxt.pg.mapper.vo;

import java.util.Date;

import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.common.utils.StringUtils;

public class TPgChinapayDayBalance {

	private String id;

	private String merId;

	private Integer payChannel;

	private String downloadUrl;

	private String fileName;

	private String fileSavePath;

	private Integer downloadStatus;

	private Integer downloadFailCounts;

	private Date balanceDate;

	private Date createdDate;

	private Date updatedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId == null ? null : merId.trim();
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = StringUtils.avoidNull(fileSavePath).trim();
	}

	public Integer getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(Integer downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public Integer getDownloadFailCounts() {
		return downloadFailCounts;
	}

	public void setDownloadFailCounts(Integer downloadFailCounts) {
		this.downloadFailCounts = downloadFailCounts;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public String getBalanceDateStrValue() {
		return DateUtils.dateToString(getBalanceDate(), "yyyyMMdd");
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
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