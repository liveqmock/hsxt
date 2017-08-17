/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.service.impl.parent;

import java.io.File;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.fs.client.common.constant.FsClientConst;
import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.exception.FsFileSizeOverLimitException;
import com.gy.hsi.fs.common.exception.FsFileTypeException;
import com.gy.hsi.fs.common.exception.FsParameterException;
import com.gy.hsi.fs.common.exception.FsPermissionException;
import com.gy.hsi.fs.common.exception.FsSecureTokenException;
import com.gy.hsi.fs.common.exception.FsServerHandleException;
import com.gy.hsi.fs.common.exception.FsServerNotAvailableException;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.service.impl.parent
 * 
 *  File Name       : ParentClientService.java
 * 
 *  Creation Date   : 2015年6月1日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : ParentClientService
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class ParentClientService {

	/** 文件服务器地址 **/
	private String fsServerUrl = null;

	/** 请求超时时间 **/
	private Integer timeout = null;

	/**
	 * 默认构造函数
	 */
	public ParentClientService() {
	}

	/**
	 * 文件系统客户端对应的服务端地址
	 * 
	 * @return
	 */
	protected String getFsServerUrl() {
		if (null == fsServerUrl) {
			fsServerUrl = HsPropertiesConfigurer
					.getProperty(FsClientConst.KEY_SERVER_URL);
		}

		return fsServerUrl;
	}

	/**
	 * 文件系统客户端请求服务端的最大超时时间[单位：毫秒]
	 * 
	 * @return
	 */
	protected int getTimeout() {
		if (null == timeout) {
			timeout = HsPropertiesConfigurer
					.getPropertyIntValue(FsClientConst.KEY_REQUEST_TIMEOUT);
		}

		return timeout;
	}

	/**
	 * 抛出文件系统异常
	 * 
	 * @param resp
	 * @param ex
	 * @return
	 */
	protected FsException getFsException(HttpRespEnvelope resp, Exception ex) {
		if (null == resp) {
			return new FsException(ex);
		}

		if (ex instanceof FsException) {
			return (FsException) ex;
		}

		try {
			this.checkRespone(resp);
		} catch (FsException e) {
			return e;
		}

		return new FsException(resp.getResultCode(), resp.getResultDesc());
	}

	/**
	 * 抛出文件系统异常
	 * 
	 * @param ex
	 * @return
	 */
	protected FsException getFsException(Exception ex) {
		if (ex instanceof FsException) {
			return (FsException) ex;
		}

		return new FsException(ex);
	}

	/**
	 * 抛出文件系统异常
	 * 
	 * @param resp
	 * @param clazz
	 * @return
	 * @throws FsServerHandleException
	 */
	protected Object parseRespData(HttpRespEnvelope resp, Class<?> clazz)
			throws FsServerHandleException {

		if ((null == resp) || (null == resp.getData())) {
			throw new FsServerHandleException(
					"The reponse data is null! Cause: Fs server handle exception.");
		}

		return JSONObject.parseObject(resp.getData().toString(), clazz);
	}

	/**
	 * 抛出文件系统异常
	 * 
	 * @param resp
	 * @return
	 * @throws FsException
	 */
	protected boolean checkRespone(HttpRespEnvelope resp) throws FsException {

		if (null == resp) {
			throw new FsServerHandleException(
					"The reponse is null! Cause: Fs server handle exception!");
		}

		int respCode = resp.getResultCode();

		if (respCode == FileOptResultCode.OPT_FAILED.getValue()) {
			throw new FsServerHandleException(resp.getResultDesc());
		}

		if (respCode == FileOptResultCode.PARAM_INVALID.getValue()) {
			throw new FsParameterException(resp.getResultDesc());
		}

		if (respCode == FileOptResultCode.PERMISSION_ERR.getValue()) {
			throw new FsPermissionException(resp.getResultDesc());
		}

		if (respCode == FileOptResultCode.SECURE_TOKEN_INVALID.getValue()) {
			throw new FsSecureTokenException(resp.getResultDesc());
		}

		if (respCode == FileOptResultCode.FILE_SIZE_OVER_LIMIT.getValue()) {
			throw new FsFileSizeOverLimitException(resp.getResultDesc());
		}

		if (respCode == FileOptResultCode.FILE_TYPE_ILLEGAL.getValue()) {
			throw new FsFileTypeException(resp.getResultDesc());
		}

		if (respCode == FileOptResultCode.FILE_NO_EXIST.getValue()) {
			throw new FsFileNotExistException(resp.getResultDesc());
		}

		if (respCode == FileOptResultCode.SERVER_NOT_AVAILABLE.getValue()) {
			throw new FsServerNotAvailableException(resp.getResultDesc());
		}

		if (respCode != FileOptResultCode.OPT_SUCCESS.getValue()) {
			throw new FsException(resp.getResultCode(), resp.getResultDesc());
		}

		return true;
	}

	/**
	 * 连接url
	 * 
	 * @param root
	 * @param subDirs
	 * @return
	 * @throws FsParameterException
	 */
	protected String assembleUrl(String root, String... subDirs)
			throws FsParameterException {
		if (StringUtils.isEmpty(root)) {
			throw new FsParameterException(
					"The FS server address is null! "
							+ "Please check if you have use HsPropertiesConfigurer(or its child class) to capture the value of property '"
							+ FsClientConst.KEY_SERVER_URL
							+ "' in properties file, which is configured in spring xml with '〈bean〉' label.");
		}

		root = root.replaceFirst("/*$", "").replaceFirst("\\\\*$", "");

		StringBuilder strBuild = new StringBuilder(root);

		for (String dir : subDirs) {
			if (StringUtils.isEmpty(dir)) {
				continue;
			}

			if (!dir.startsWith("/")) {
				strBuild.append("/");
			}

			strBuild.append(dir);
		}

		return strBuild.toString();
	}

	/**
	 * 解析文件名称(以逗号分隔)
	 * 
	 * @param files
	 * @return 以逗号分隔
	 */
	protected String formatFilenames(File[] files) {
		if (null == files) {
			return "";
		}

		String fileName;
		StringBuilder strBuilder = new StringBuilder();

		for (File file : files) {
			fileName = file.getName().replace(FsConstant.COMMA_CHAR,
					FsConstant.CN_COMMA_CHAR);

			strBuilder.append(fileName).append(FsConstant.COMMA_CHAR);
		}

		int len = strBuilder.length();

		if (0 < len) {
			strBuilder.replace(len - 1, len, "");
		}

		return strBuilder.toString();
	}

	/**
	 * 解析文件后缀(以逗号分隔)
	 * 
	 * @param fileSuffixes
	 * @param maxLen
	 * @return 以逗号分隔
	 */
	protected String formatFileSuffix(String[] fileSuffixes, int maxLen) {
		if (null == fileSuffixes) {
			return "";
		}

		int counts = 0;

		String _fileSuffix;

		StringBuilder strBuilder = new StringBuilder();

		for (String fileSuffix : fileSuffixes) {
			if (maxLen >= (++counts)) {
				_fileSuffix = fileSuffix.replace(FsConstant.COMMA_CHAR,
						FsConstant.CN_COMMA_CHAR);

				strBuilder.append(_fileSuffix).append(FsConstant.COMMA_CHAR);
			}
		}

		int len = strBuilder.length();

		if (0 < len) {
			strBuilder.replace(len - 1, len, "");
		}

		return strBuilder.toString();
	}
}
