/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.bean.packet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uf.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.bean.packet
 * 
 *  File Name       : SecureRegionPacketHeader.java
 * 
 *  Creation Date   : 2015-10-24
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 跨地区平台报文头
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SecureRegionPacketHeader implements Serializable {
	private static final long serialVersionUID = 2688503429104874871L;

	/** 报文ID **/
	private String packetId;

	/** 源地区平台ID **/
	private String srcPlatformId;

	/** 源子系统ID **/
	private String srcSubsysId;

	/** 目标地区平台ID **/
	private String destPlatformId;

	/** 目标子系统ID **/
	private String destSubsysId;

	/** 目标业务代码 **/
	private String destBizCode;

	/** 数字签名(数字签名包含了报文头和报文体双面信息) **/
	private String digitSignature;

	/** 其他预留参数 **/
	private Map<String, Object> obligateArgs;

	private SecureRegionPacketHeader() {
		this.obligateArgs = new HashMap<String, Object>(1);
	}

	public String getPacketId() {
		return packetId;
	}

	public SecureRegionPacketHeader setPacketId(String packetId) {
		this.packetId = packetId;

		return this;
	}

	public String getSrcPlatformId() {
		return srcPlatformId;
	}

	public SecureRegionPacketHeader setSrcPlatformId(String srcPlatformId) {
		this.srcPlatformId = srcPlatformId;

		return this;
	}

	public String getSrcSubsysId() {
		return srcSubsysId;
	}

	public SecureRegionPacketHeader setSrcSubsysId(String srcSubsysId) {
		this.srcSubsysId = srcSubsysId;

		return this;
	}

	public String getDestPlatformId() {
		return destPlatformId;
	}

	public SecureRegionPacketHeader setDestPlatformId(String destPlatformId) {
		this.destPlatformId = destPlatformId;

		return this;
	}

	public String getDestSubsysId() {
		return destSubsysId;
	}

	public SecureRegionPacketHeader setDestSubsysId(String destSubsysId) {
		this.destSubsysId = destSubsysId;

		return this;
	}

	public String getDestBizCode() {
		return destBizCode;
	}

	public SecureRegionPacketHeader setDestBizCode(String destBizCode) {
		this.destBizCode = destBizCode;

		return this;
	}

	public String getDigitSignature() {
		return StringUtils.avoidNull(digitSignature);
	}

	public SecureRegionPacketHeader setDigitSignature(String digitSignature) {
		this.digitSignature = digitSignature;

		return this;
	}

	public SecureRegionPacketHeader addObligateArgs(String key, Object value) {
		this.obligateArgs.put(key, value);

		return this;
	}

	public SecureRegionPacketHeader addObligateArgs(Map<String, Object> map) {
		this.obligateArgs.putAll(map);

		return this;
	}

	public Object getObligateArgsValue(String key) {
		return this.obligateArgs.get(key);
	}

	public SecureRegionPacketHeader removeObligateArgs(String key) {
		this.obligateArgs.remove(key);

		return this;
	}

	public Map<String, Object> copyObligateArgs() {
		Map<String, Object> targetMap = new HashMap<String, Object>(1);
		targetMap.putAll(this.obligateArgs);

		return targetMap;
	}

	public static SecureRegionPacketHeader build() {
		return new SecureRegionPacketHeader();
	}

	@Override
	public String toString() {
		String obligateArgs = JSONObject.toJSONString(getObligateArgs());

		// 特别说明：字段顺序绝对不可以改变
		StringBuilder builder = new StringBuilder();
		builder.append(this.packetId);
		builder.append(this.srcPlatformId);
		builder.append(this.srcSubsysId);
		builder.append(this.destPlatformId);
		builder.append(this.destSubsysId);
		builder.append(this.destBizCode);
		builder.append(obligateArgs);

		return builder.toString();
	}

	private Map<String, Object> getObligateArgs() {
		return this.obligateArgs;
	}

}
