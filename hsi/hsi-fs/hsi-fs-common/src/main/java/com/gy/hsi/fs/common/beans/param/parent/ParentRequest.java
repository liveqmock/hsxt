/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans.param.parent;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsi.fs.common.constant.HttpRequestParam.FsRequestKey;
import com.gy.hsi.fs.common.utils.CommonFileIdUtils;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans.param.parent
 * 
 *  File Name       : ParentRequest.java
 * 
 *  Creation Date   : 2015年5月27日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class ParentRequest {

	/** 文件id **/
	protected String fileId = null;

	/** 用户id **/
	protected String userId;

	/** 安全令牌 **/
	protected String token;

	/** 渠道 **/
	protected String channel;

	public ParentRequest() {
	}

	public ParentRequest(HttpServletRequest request) {
		this.userId = request.getParameter(FsRequestKey.USER_ID);
		this.token = request.getParameter(FsRequestKey.TOKEN);
		this.channel = request.getParameter(FsRequestKey.CHANNEL);

		this.initFileId(request);
	}

	public String getFileId() {
		return fileId;
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public String getChannel() {
		return channel;
	}

	/**
	 * 从url解析出文件id和后缀
	 * 
	 * @param request
	 * @return [文件id][文件后缀]
	 */
	protected String[] parseFileIdAndSuffix(HttpServletRequest request) {
		// 请求地址
		// String fullUrl = request.getRequestURL().toString();
		// fullUrl = fullUrl.replaceFirst("\\?.*", "");
		// fullUrl = fullUrl.replaceFirst("/$", "");
		// fullUrl = fullUrl.replaceFirst("(.*\\/)", "");
		// fullUrl = fullUrl.replaceFirst("(.*\\\\)", "");

		String fullUrl = request.getRequestURL().toString();
		fullUrl = fullUrl.replaceFirst("\\?.*", "");
		fullUrl = fullUrl.replaceFirst("/{1,}$", "");
		fullUrl = fullUrl.replaceFirst("(.*\\/fs\\/([^/]{1,}))(\\/{0,})", "");
		fullUrl = fullUrl.replaceFirst("(.*\\\\)", "");

		return CommonFileIdUtils.extractFileIdAndSuffix(fullUrl);
	}

	/**
	 * 初始化文件id
	 * 
	 * @param request
	 */
	private void initFileId(HttpServletRequest request) {
		this.fileId = request.getParameter(FsRequestKey.FILE_ID);

		if (StringUtils.isEmpty(fileId)) {
			String[] resultArry = parseFileIdAndSuffix(request);

			if (null != resultArry) {
				if (1 <= resultArry.length) {
					this.fileId = resultArry[0];
				}
			}
		}
	}

	public static void main(String[] args) {
		String fullUrl = "http://127.0.0.1:8080/fs-server/fs/deleteFile/00SNc546c9b9dAFF3702899281Kf.jpg";

		fullUrl = fullUrl.replaceFirst("\\?.*", "");
		fullUrl = fullUrl.replaceFirst("/{1,}$", "");
		fullUrl = fullUrl.replaceFirst("(.*\\/fs\\/([^/]{1,}))(\\/{0,})", "");
		fullUrl = fullUrl.replaceFirst("(.*\\\\)", "");

		String[] aa = CommonFileIdUtils.extractFileIdAndSuffix(fullUrl);

		System.out.println(aa[0]);
		System.out.println(aa[1]);
	}
}
