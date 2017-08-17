package com.gy.hsxt.kafka.common.bean;


/**
 * 
 * @ClassName: SystemLogInfo 
 * @Description: 系统日志实体bean 
 * @author Lee 
 * @date 2015-7-1 下午5:34:58
 */ 
public class SysLogInfo implements java.io.Serializable{
	private long order;//用于日志排序
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8496099260356543902L;

	/**
	 * 主机名
	 */
	private String hostName;
	
	/**
	 * 主机IP地址
	 */
	private String ipAddress;
	/**
	 * 日志级别
	 */
	private String level;
	
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
	 * log日志信息
	 */
	private String logContent;
	
	/**
	 * 时间戳
	 */
	private String timeStamp;
	
	/**
	 * 告警级别
	 */
	private String monitorLevel;
	
	/**
	 * 异常信息
	 */
	private String exceptionMessage;
	
	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 邮箱
	 */
	private String email;
	
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
	
	public String getMonitorLevel() {
		return monitorLevel;
	}
	public void setMonitorLevel(String monitorLevel) {
		this.monitorLevel = monitorLevel;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
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
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
