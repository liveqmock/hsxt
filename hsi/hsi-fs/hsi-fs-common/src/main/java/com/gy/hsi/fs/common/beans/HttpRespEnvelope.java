/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.response
 * 
 *  File Name       : HttpRespEnvelope.java
 * 
 *  Creation Date   : 2015年5月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : http响应数据信封
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HttpRespEnvelope {

	/** 结果返回码 **/
	private int resultCode = FileOptResultCode.OPT_SUCCESS.getValue();

	/** 结果描述 **/
	private String resultDesc = "Operation success.";

	/** 响应数据 **/
	private Object data = null;

	/** 响应数据 **/
	private Exception exception;

	public HttpRespEnvelope() {
	}

	public HttpRespEnvelope(int resultCode, String resultDesc) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}

	public HttpRespEnvelope(Exception ex) {
		this(ex, null);
	}

	public HttpRespEnvelope(Exception ex, Object reference) {
		String inputParam = ""; // formatReferParam(reference);

		if (ex instanceof FsException) {
			FsException fsEx = ((FsException) ex);

			this.setResultCode(fsEx.getErrorCode());
			this.setResultDesc(fsEx.getErrorMsg() + inputParam);
		} else {
			this.setResultCode(FileOptResultCode.OPT_FAILED.getValue());
			this.setResultDesc("The FS server internal processing error! Please report this message to us, thks."
					+ inputParam);
		}

		this.exception = ex;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toJsonString() {
		return JSONObject.toJSONStringWithDateFormat(this,
				"yyyy-MM-dd HH:mm:ss", SerializerFeature.QuoteFieldNames);
	}

	public String toJsonString(boolean deleteData) {
		String jsonString = toJsonString();

		JSONObject jsonObj = com.alibaba.fastjson.JSONObject
				.parseObject(jsonString);

		if (deleteData) {
			jsonObj.remove("data");
		}

		return jsonObj.toJSONString();
	}

	public Exception fetchException() {
		return this.exception;
	}

	/**
	 * 将输入对象转换为字符串
	 * 
	 * @param reqParam
	 * @return
	 */
	@SuppressWarnings("unused")
	private String formatReferParam(Object reqParam) {
		if (StringUtils.isEmpty(reqParam)) {
			return "";
		}

		String str;

		if (!(reqParam instanceof String)) {
			str = JSONObject.toJSONString(reqParam).replaceAll("\\{", "")
					.replaceAll("\\}", "").replaceAll("\\\":\\\"", "=")
					.replaceAll("\\\",\\\"", ", ").replaceAll("\\\"", "");
		} else {
			str = String.valueOf(reqParam);
		}

		return " <Your input is: " + str + ">";
	}
}
