/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.bean.packet.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.bean.packet.data
 * 
 *  File Name       : RegionPacketDataHeader.java
 * 
 *  Creation Date   : 2015年10月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收到的跨地区平台数据
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RegionPacketDataHeader implements Serializable {
	private static final long serialVersionUID = -2766131677001613554L;

	/** 报文ID **/
	private String packetId;

	/** 来源平台ID **/
	private String srcPlatformId;

	/** 来源子系统ID **/
	private String srcSubsysId;

	/** 业务代码 **/
	private String bizCode;

	/** 其他预留参数 **/
	private Map<String, Object> obligateArgs;

	private RegionPacketDataHeader() {
		this.obligateArgs = new HashMap<String, Object>(1);
	}

	public String getPacketId() {
		return packetId;
	}

	public RegionPacketDataHeader setPacketId(String packetId) {
		this.packetId = packetId;

		return this;
	}

	public String getSrcPlatformId() {
		return srcPlatformId;
	}

	public RegionPacketDataHeader setSrcPlatformId(String srcPlatformId) {
		this.srcPlatformId = srcPlatformId;

		return this;
	}

	public String getSrcSubsysId() {
		return srcSubsysId;
	}

	public RegionPacketDataHeader setSrcSubsysId(String srcSubsysId) {
		this.srcSubsysId = srcSubsysId;

		return this;
	}

	public String getBizCode() {
		return bizCode;
	}

	public RegionPacketDataHeader setBizCode(String bizCode) {
		this.bizCode = bizCode;

		return this;
	}

	public RegionPacketDataHeader addObligateArgs(String key, Object value) {
		this.obligateArgs.put(key, value);

		return this;
	}

	public RegionPacketDataHeader addObligateArgs(Map<String, Object> map) {
		this.obligateArgs.putAll(map);

		return this;
	}

	public Object getObligateArgsValue(String key) {
		return this.obligateArgs.get(key);
	}

	public Map<String, Object> getObligateArgs() {
		return obligateArgs;
	}

	public static RegionPacketDataHeader build() {
		return new RegionPacketDataHeader();
	}
}
