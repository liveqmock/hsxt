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

import com.gy.hsxt.uf.common.constant.UFResultCode;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.bean.packet
 * 
 *  File Name       : PacketHandleResult.java
 * 
 *  Creation Date   : 2015-10-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 报文发送结果响应
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PacketHandleResult implements Cloneable, Serializable {
	private static final long serialVersionUID = -4408873923040996400L;

	/** 处理结果响应码 **/
	private Integer resultCode;

	/** 处理结果描述信息 **/
	private String resultDesc;

	/** 异常对象 **/
	private Throwable cause;

	/** 处理结果响应数据 **/
	private Object resultData;

	/**
	 * 构造函数1
	 */
	public PacketHandleResult() {
		this(null, null, null);
	}

	/**
	 * 构造函数2
	 * 
	 * @param resultCode
	 * @param resultDesc
	 */
	public PacketHandleResult(Integer resultCode, String resultDesc) {
		this(resultCode, resultDesc, null);
	}

	/**
	 * 构造函数3
	 * 
	 * @param resultCode
	 * @param resultDesc
	 * @param cause
	 */
	public PacketHandleResult(Integer resultCode, String resultDesc,
			Throwable cause) {
		this.resultCode = adjustResultCode(resultCode, UFResultCode.SUCCESS);
		this.resultDesc = resultDesc;

		this.cause = cause;
	}

	/**
	 * 构造函数4
	 * 
	 * @param cause
	 */
	public PacketHandleResult(Throwable cause) {
		this(UFResultCode.FAILED, cause.getMessage(), cause);
	}

	/**
	 * 判断是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return (UFResultCode.SUCCESS == this.resultCode);
	}

	/**
	 * 取得响应码
	 * 
	 * @return
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	/**
	 * 取得响应描述信息
	 * 
	 * @return
	 */
	public String getResultDesc() {
		return resultDesc;
	}

	/**
	 * 设置结果响应描述信息
	 * 
	 * @param resultDesc
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	/**
	 * 取得异常对象
	 * 
	 * @return
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * 设置异常对象
	 * 
	 * @param cause
	 */
	public void cleanUpCause() {
		this.cause = null;
	}

	/**
	 * 取得结果响应数据对象
	 * 
	 * @return
	 */
	public Object getResultData() {
		return resultData;
	}

	/**
	 * 设置结果响应数据对象
	 * 
	 * @param resultData
	 */
	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	/**
	 * 设置异常错误信息
	 * 
	 * @param resultCode
	 * @param resultDesc
	 * @param cause
	 */
	public void putErrorInfo(Integer resultCode, String resultDesc,
			Throwable cause) {
		// 一旦调用这个方法, 没有成功的可能, 所以一定是失败
		if (UFResultCode.SUCCESS == resultCode) {
			resultCode = UFResultCode.FAILED;
		}

		this.resultCode = adjustResultCode(resultCode, UFResultCode.FAILED);
		this.resultDesc = resultDesc;
		this.cause = cause;
	}

	/**
	 * 设置异常错误信息
	 * 
	 * @param resultCode
	 * @param resultDesc
	 */
	public void putErrorInfo(Integer resultCode, String resultDesc) {
		this.putErrorInfo(resultCode, resultDesc, null);
	}

	/**
	 * 设置异常错误信息
	 * 
	 * @param cause
	 */
	public void putErrorInfo(Throwable cause) {
		this.putErrorInfo(UFResultCode.FAILED, cause.getMessage(), cause);
	}

	/**
	 * 克隆对象
	 */
	public Object clone() {
		PacketHandleResult o = null;

		try {
			o = (PacketHandleResult) super.clone();
		} catch (CloneNotSupportedException e) {
		}

		return o;
	}

	/**
	 * 防止传入空的响应码, 如果传递的是空的响应码, 则以默认值为准
	 * 
	 * @param resultCode
	 * @param defaultValue
	 * @return
	 */
	private Integer adjustResultCode(Integer resultCode, Integer defaultValue) {
		if (null == resultCode) {
			if (null == defaultValue) {
				return UFResultCode.SUCCESS;
			}

			return defaultValue;
		}

		return resultCode;
	}
}
