/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.bean;

import java.io.Serializable;

import com.gy.hsxt.uf.common.constant.ConfigConst.StartClassPath;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.bean
 * 
 *  File Name       : DestPlatformAddress.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 目标地区平台地址信息
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DestPlatformAddress implements Serializable {
	private static final long serialVersionUID = -7030099567533259645L;

	private String ip;
	private String port;

	private String platformId;
	private String subsysId;

	public DestPlatformAddress() {
	}

	public DestPlatformAddress(String ip, String port, String subsysId,
			String platformId) {
		this.setIp(ip);
		this.setPort(formatPort(port));
		this.setPlatformId(platformId);
		this.setSubsysId(subsysId);
	}

	public DestPlatformAddress(String ip, String port) {
		this(ip, port, null, null);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public Integer getPortIntValue() {
		return StringUtils.str2Int(port);
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getSubsysId() {
		return subsysId;
	}

	public void setSubsysId(String subsysId) {
		this.subsysId = subsysId;
	}

	/**
	 * 获取端口号
	 * 
	 * @param port
	 * @return
	 */
	private String formatPort(String port) {
		int iPort = StringUtils.str2Int(port);

		if (0 >= iPort) {
			port = UfPropertyConfigurer.getProperty(StartClassPath.SERVER_PORT);
		}

		return port;
	}
}
