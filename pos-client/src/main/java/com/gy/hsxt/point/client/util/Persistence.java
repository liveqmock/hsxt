package com.gy.hsxt.point.client.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName WritePOS
 * @package .Persistence.java
 * @className Persistence
 * @description 读写默认参数 参数表 用户名 端口 秘钥服务器IP 秘钥服务器端口 业务服务器IP 业务服务器端口
 * @author fandi
 * @createDate 2014-8-7 上午9:58:54
 * @updateUser lyh
 * @updateDate 2015-12-27 上午9:58:54
 * @updateRemark 说明本次修改内容
 * @version V3.0
 */
public class Persistence {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Persistence.class);
	private String userName;
	private String comPort = "COM1";
	private String clientServer = "";
	private int clientPort = 0;
	private String sslcaFilename = "";// 文件路径+名称
	private String sslcaPassword = "";
	private String sslcaCN = "";
	private boolean ifNew = true;
	private int ServerTimeOut;
	private int PosTimeOut;
	// 平台互生号
	private String resNo;
	// 打印文件临时路径
	private String printFilePath;

	/* 接入层项目名称 */
	private String webName;

	/* 默认访问前缀 */
	private String defaultCommon = "http://";

	private String loggerFilePath;

	private String logSwitch;

	private String pageNumber;

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the logSwitch
	 */
	public String getLogSwitch() {
		return logSwitch;
	}

	public String getPrintFilePath() {
		return printFilePath;
	}

	public void setPrintFilePath(String printFilePath) {
		this.printFilePath = printFilePath;
	}

	/**
	 * @param logSwitch
	 *            the logSwitch to set
	 */
	public void setLogSwitch(String logSwitch) {
		this.logSwitch = logSwitch;
	}

	/**
	 * @return the loggerFilePath
	 */
	public String getLoggerFilePath() {
		return loggerFilePath;
	}

	/**
	 * @param loggerFilePath
	 *            the loggerFilePath to set
	 */
	public void setLoggerFilePath(String loggerFilePath) {
		this.loggerFilePath = loggerFilePath;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public int getServerTimeOut() {
		return ServerTimeOut;
	}

	public void setServerTimeOut(int serverTimeOut) {
		ServerTimeOut = serverTimeOut;
	}

	public int getPosTimeOut() {
		return PosTimeOut;
	}

	public void setPosTimeOut(int posTimeOut) {
		PosTimeOut = posTimeOut;
	}

	private Properties propertie;

	/**
	 * 平台电话
	 */
	private String telephoneNo;
	/**
	 * 平台网址
	 */
	private String url;

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		if (telephoneNo.length() > ConstData.ENT_TEL_LEN)
			this.telephoneNo = telephoneNo.substring(0, ConstData.ENT_TEL_LEN);
		else
			this.telephoneNo = telephoneNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url.length() > ConstData.ENT_URL_LEN)
			this.url = url.substring(0, ConstData.ENT_URL_LEN);
		else
			this.url = url;
	}

	public String getClientServer() {
		return clientServer;
	}

	public int getClientPort() {
		return clientPort;
	}

	public boolean setClientPort(int Port) {
		if (Port > 0 && Port < 65536) {
			clientPort = Port;
			return true;
		}
		return false;
	}

	public boolean setClientServer(String server) {
		if (CheckIP(server)) {
			clientServer = server;
			return true;
		}
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComPort() {
		return comPort;
	}

	public void setComPort(String comPort) {
		this.comPort = comPort;
	}

	/**
	 * 域名和IP互相转换
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unused")
	private  String getIP(String name) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(name);
			return address.getHostAddress().toString();
		} catch (UnknownHostException e) {
			return "";
		}
	}

	/*
	 * 检查IP地址合法性 要求XXX.XXX.XXX.XXX
	 */
	private boolean CheckIP(String IP) {
		// 域名和IP转换成IP
		IP=this.getIP(IP);
		int k;
		int start = 0;
		// 第一段检查
		int end = IP.indexOf('.');
		if (end == -1 || end == start || end - start > 3)
			return false;
		for (k = start; k < end; k++) {
			if (IP.charAt(k) < '0' || IP.charAt(k) > '9')
				return false;
		}
		int part = Integer.parseInt(IP.substring(start, end));
		if (part > 255)
			return false;
		// 第二段检查
		start = end + 1;
		end = IP.indexOf('.', start);
		if (end == -1 || end == start || end - start > 3)
			return false;
		for (k = start; k < end; k++) {
			if (IP.charAt(k) < '0' || IP.charAt(k) > '9')
				return false;
		}
		part = Integer.parseInt(IP.substring(start, end));
		if (part > 255)
			return false;
		// 第三段检查
		start = end + 1;
		end = IP.indexOf('.', start);
		if (end == -1 || end == start || end - start > 3)
			return false;
		for (k = start; k < end; k++) {
			if (IP.charAt(k) < '0' || IP.charAt(k) > '9')
				return false;
		}
		part = Integer.parseInt(IP.substring(start, end));
		if (part > 255)
			return false;
		// 第四段检查
		start = end + 1;
		end = IP.length();
		if (end == start || end - start > 3)
			return false;
		for (k = start; k < end; k++) {
			if (IP.charAt(k) < '0' || IP.charAt(k) > '9')
				return false;
		}
		part = Integer.parseInt(IP.substring(start, end));
		if (part > 255)
			return false;
		return true;
	}

	public Persistence() {
		FileInputStream inputFile;
		propertie = new Properties();
		try {

			inputFile = new FileInputStream(ConstData.CONFIG_PATH
					+ ConstData.CONFIG_FILE);
			propertie.load(inputFile);
			inputFile.close();
			userName = propertie.getProperty("UserName", "");
			comPort = propertie.getProperty("ComPort", "COM1");
			clientServer = propertie.getProperty("ClientServer", "");
			clientPort = Integer.parseInt(propertie.getProperty("ClientPort",
					"0"));
			telephoneNo = propertie.getProperty("telephoneNo", "");
			url = propertie.getProperty("url", "");
			resNo = propertie.getProperty("resNO", "");
			sslcaFilename = propertie.getProperty("SslcaFilename", "");
			sslcaPassword = propertie.getProperty("SslcaPassword", "");
			sslcaCN = propertie.getProperty("SslcaCN", "");
			ServerTimeOut = Integer.parseInt(propertie.getProperty(
					"ServerTimeout",
					"" + String.valueOf(ConstData.TIME_OUT_SVR)));
			PosTimeOut = Integer.parseInt(propertie.getProperty("POSTimeout",
					"" + String.valueOf(ConstData.TIME_OUT)));
			webName = propertie.getProperty("webName", "");
			loggerFilePath = propertie.getProperty("PosLogger", "");
			logSwitch = propertie.getProperty("LogSwitch", "");
			printFilePath = propertie.getProperty("printFilePath");
			pageNumber = propertie.getProperty("PageNumber");
			// LogSwitch
			if (propertie.getProperty("IfNew").equals("0")) {
				ifNew = false;
			}
			LOGGER.info("UserName:" + userName);
			LOGGER.info("ComPort:" + comPort);
			LOGGER.info("ClientServer:" + clientServer);
			LOGGER.info("ClientPort:" + clientPort);
			LOGGER.info("telephoneNo:" + telephoneNo);
			LOGGER.info("url:" + url);
			LOGGER.info("SslcaFilename:" + sslcaFilename);
			LOGGER.info("SslcaPassword:" + sslcaPassword);
			LOGGER.info("SslcaCN:" + sslcaCN);
			LOGGER.info("ServerTimeout:" + ServerTimeOut);
			LOGGER.info("POSTimeout:" + PosTimeOut);
			LOGGER.info("IfNew:" + ifNew);
			LOGGER.info("printFilePath:" + printFilePath);
			LOGGER.info("pageNumber:" + pageNumber);
		} catch (Exception ex) {
			LOGGER.error("" + ex);
		}
	}

	/**
	 * 根据访问接入层和方法名，得到Web请求路径
	 * http://localhost:8080/hsxt-access-web-company/poslogin/login"
	 */
	public String getWebURL(String controller, String method) {
		String webUrl = defaultCommon + this.clientServer + ":"
				+ this.clientPort + "/" + this.webName + "/" + controller + "/"
				+ method;
		return webUrl;
	}

	public void Save() {
		FileOutputStream outputFile;
		try {
			outputFile = new FileOutputStream(ConstData.CONFIG_PATH
					+ ConstData.CONFIG_FILE);

			propertie.setProperty("UserName", userName);
			propertie.setProperty("ComPort", comPort);
			propertie.setProperty("ClientServer", clientServer);
			propertie.setProperty("ClientPort", "" + clientPort);
			propertie.setProperty("telephoneNo", telephoneNo);
			propertie.setProperty("url", url);
			propertie.setProperty("IfNew", ifNew ? "1" : "0");
			propertie.setProperty("resNO", resNo);
			// propertie.setProperty("printFilePath", printFilePath);
			propertie.store(outputFile, "");
			outputFile.close();
		} catch (IOException e) {
			LOGGER.error("" + e);

		}
	}

	public String getSslcaFilename() {
		return sslcaFilename;
	}

	public String getSslcaCN() {
		return sslcaCN;
	}

	public void setSslcaFilename(String sslcaFilename) {
		this.sslcaFilename = sslcaFilename;
	}

	public String getSslcaPassword() {
		return sslcaPassword;
	}

	public void setSslcaPassword(String sslcaPassword) {
		this.sslcaPassword = sslcaPassword;
	}

	public boolean ifNewVersion() {
		return ifNew;
	}

	public void SetIfNew(boolean ifNew) {
		this.ifNew = ifNew;
	}
}
