package com.gy.hsxt.kafka.common.bean;



public class BizLogInfo {
	private long order;
	/**
	 * 时间戳
	 */
	private String timeStamp; //格式  yyyy-MM-dd hh:mm:ss
	/**
	 * 平台名
	 */
	private String platformName;
	
	/**
	 * 系统名
	 */
	private String systemName;
	
	/**
	 * 系统实例名
	 */
	private String systemInstanceName;
	
	/**
	 * 模块名
	 */
	private String moudleName;
	/**
	 * 模块下功能点名
	 */
	private String funName;
	
	/**
	 * 所有字段名
	 */
	private String columns;
	
	/**
	 * 所有字段值
	 */
	private String contents;

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemInstanceName() {
		return systemInstanceName;
	}

	public void setSystemInstanceName(String systemInstanceName) {
		this.systemInstanceName = systemInstanceName;
	}

	public String getMoudleName() {
		return moudleName;
	}

	public void setMoudleName(String moudleName) {
		this.moudleName = moudleName;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}


	
}
