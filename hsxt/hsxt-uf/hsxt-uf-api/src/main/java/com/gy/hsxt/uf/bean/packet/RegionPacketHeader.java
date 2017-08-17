/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.bean.packet;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.common.constant.UFConstant;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.common.utils.UfUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.bean.packet
 * 
 *  File Name       : RegionPacketHeader.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 跨地区平台消息数据报文头
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RegionPacketHeader implements Serializable {
	private static final long serialVersionUID = -8432199071154659833L;

	/** 报文ID **/
	private String packetId;

	/** 目标平台ID **/
	private String destPlatformId;

	/** 目标子系统ID, 暂时无用 **/
	@SuppressWarnings("unused")
	@Deprecated
	private String destSubSysId;

	/** 目标互生资源号 **/
	private String destResNo;

	/** 目标业务代码 **/
	private String destBizCode;

	/** 来源业务子系统id **/
	private String srcSubsysId;

	/** 其他预留参数 **/
	private Map<String, Object> obligateArgs;

	/** 发送时间(业务子系统发起请求的时间) **/
	private Date sendDateTime = new Date();

	/**
	 * 私有构造函数
	 */
	private RegionPacketHeader() {
		// 初始化预留参数map对象
		this.obligateArgs = new HashMap<String, Object>(1);

		// 自动生成报文id
		this.packetId = UfUtils.generateUUID();

		// 设置来源业务子系统id
		this.srcSubsysId = UfPropertyConfigurer
				.getProperty(UFConstant.KEY_SYSTEM_ID);

		// 校验调用方业务子系统是否配置了system.id
		if (StringUtils.isEmpty(srcSubsysId)) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"严重错误：本地properties属性文件中尚未配置'" + UFConstant.KEY_SYSTEM_ID
							+ "'属性(即:业务子系统id), 此id是UF综合前置识别内部业务子系统的唯一凭证标识, 必须配置!!");
		}
	}

	public String getPacketId() {
		return packetId;
	}

	public String getDestPlatformId() {
		return destPlatformId;
	}

	public RegionPacketHeader setDestPlatformId(String destPlatformId) {
		this.destPlatformId = destPlatformId;
		
		// 校验destPlatformId和destResNo互斥
		this.checkExclusive(destPlatformId, destResNo);

		return this;
	}

	@SuppressWarnings("unused")
	private String getDestSubSysId() {
		// return destSubSysId;
		throw new IllegalAccessError(
				"The method is temporarily not supported !");
	}

	@SuppressWarnings("unused")
	private RegionPacketHeader setDestSubSysId(String destSubSysId) {
		this.destSubSysId = destSubSysId;
		// return this;
		throw new IllegalAccessError(
				"The method is temporarily not supported !");
	}

	public String getDestResNo() {
		return destResNo;
	}

	public RegionPacketHeader setDestResNo(String destResNo) {
		this.destResNo = destResNo;

		// 校验destPlatformId和destResNo互斥
		this.checkExclusive(destPlatformId, destResNo);

		return this;
	}

	public String getDestBizCode() {
		return destBizCode;
	}

	public RegionPacketHeader setDestBizCode(String destBizCode) {
		this.destBizCode = destBizCode;

		return this;
	}

	public String getSrcSubsysId() {
		return srcSubsysId;
	}

	public RegionPacketHeader addObligateArgs(String key, Object value) {
		this.obligateArgs.put(key, value);

		return this;
	}

	public RegionPacketHeader addObligateArgs(Map<String, Object> map) {
		this.obligateArgs.putAll(map);

		return this;
	}

	public Object getObligateArgsValue(String key) {
		return this.obligateArgs.get(key);
	}

	public Map<String, Object> copyObligateArgs() {
		Map<String, Object> targetMap = new HashMap<String, Object>(1);
		targetMap.putAll(this.obligateArgs);

		return targetMap;
	}

	public Date getSendDateTime() {
		return sendDateTime;
	}

	public RegionPacketHeader setSendDateTime(Date sendDateTime) {
		this.sendDateTime = sendDateTime;

		return this;
	}

	/**
	 * 创建报文头对象
	 * 
	 * @return
	 */
	public static RegionPacketHeader build() {
		return new RegionPacketHeader();
	}
	
	/**
	 * 校验destPlatformId和destResNo互斥
	 * 
	 * @param destPlatformId
	 * @param destResNo
	 */
	private void checkExclusive(String destPlatformId, String destResNo) {
		if (!StringUtils.isExistEmpty(destPlatformId, destResNo)) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"报文头RegionPacketHeader对象的destPlatformId、destResNo两个属性互斥, 只能设置其中之一有值!");
		}
	}
}
