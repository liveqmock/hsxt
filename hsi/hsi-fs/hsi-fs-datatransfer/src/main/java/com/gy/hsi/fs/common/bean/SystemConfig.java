package com.gy.hsi.fs.common.bean;

public class SystemConfig {
	private String tfsAddress;
	private String downloadLocalpath;

	private String ftpIp;
	private Integer ftpPort;

	private String ftpUsername;
	private String ftpPassword;

	private String ftpRootDir;

	private int maxPoolSize;

	public String getTfsAddress() {
		return tfsAddress;
	}

	public void setTfsAddress(String tfsAddress) {
		this.tfsAddress = tfsAddress;
	}

	public String getDownloadLocalpath() {
		return downloadLocalpath;
	}

	public void setDownloadLocalpath(String downloadLocalpath) {
		this.downloadLocalpath = downloadLocalpath;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public int getFtpPort() {
		
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getFtpRootDir() {
		return ftpRootDir;
	}

	public void setFtpRootDir(String ftpRootDir) {
		this.ftpRootDir = ftpRootDir;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

}
