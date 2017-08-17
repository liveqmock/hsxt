/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.fs.client.common.ParameterChecker;
import com.gy.hsi.fs.client.common.utils.HttpClientHelper;
import com.gy.hsi.fs.client.common.utils.HttpMultipartUtils;
import com.gy.hsi.fs.common.beans.FileMetaInfo;
import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsApiConstant.Value;
import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.constant.HttpRequestParam.DeleteFileRequestKey;
import com.gy.hsi.fs.common.constant.HttpRequestParam.DownloadRequestKey;
import com.gy.hsi.fs.common.constant.HttpRequestParam.SetPermissionRequestKey;
import com.gy.hsi.fs.common.constant.HttpRequestParam.ViewFileInfoRequestKey;
import com.gy.hsi.fs.common.constant.HttpUrlDirectory.FsUrlSubDirectory;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.utils.CommonFileIdUtils;
import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.service.impl
 * 
 *  File Name       : FsClientServiceImpl.java
 * 
 *  Creation Date   : 2015年5月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统客户端service接口实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service(value = "fsClientService")
public class FsClientServiceImpl extends FsClientUploadServiceImpl {

	public FsClientServiceImpl() {
	}

	@Override
	public boolean downloadPublicFile(String fileId, String localFileName)
			throws FsException {
		ParameterChecker.checkDownloadPublicFile(fileId, localFileName);

		return downloadFile(fileId, localFileName, null, null, null);
	}

	@Override
	public boolean downloadPublicFile(String fileId, OutputStream output)
			throws FsException {
		ParameterChecker.checkDownloadPublicFile(fileId, output);

		return downloadFile(fileId, output, null, null, null);
	}

	@Override
	public boolean downloadFile(String fileId, String localFileName,
			String userId, String token, String channel) throws FsException {
		ParameterChecker.checkDownloadFile(fileId, localFileName, userId,
				token, channel);

		String downloadUrl = this.assembleUrl(getFsServerUrl(),
				FsUrlSubDirectory.URL_DOWNLOAD, fileId);

		if (!StringUtils.isEmpty(token)) {
			downloadUrl = StringUtils.catString(downloadUrl, "?",
					DownloadRequestKey.USER_ID, "=", userId,
					DownloadRequestKey.TOKEN, "=", token,
					DownloadRequestKey.CHANNEL, "=", channel);
		}

		try {
			boolean success = HttpMultipartUtils.downloadFile(downloadUrl,
					localFileName, getTimeout());

			if (success) {
				return true;
			}

			throw new FsFileNotExistException(
					"Failed to download file! fileId=" + fileId);
		} catch (Exception ex) {
			throw super.getFsException(ex);
		}
	}

	@Override
	public boolean downloadFile(String fileId, String localFileName,
			SecurityToken securityToken) throws FsException {
		String userId = null;
		String token = null;
		String channel = null;

		if (null != securityToken) {
			userId = securityToken.getUserId();
			token = securityToken.getToken();
			channel = securityToken.getChannel();
		}

		return this.downloadFile(fileId, localFileName, userId, token, channel);
	}

	@Override
	public boolean downloadFile(String fileId, OutputStream output,
			String userId, String token, String channel) throws FsException {
		ParameterChecker.checkDownloadFile(fileId, output, userId, token,
				channel);

		String tempFilePath = this.assembleUrl(FsConstant.TEMP_FILE_ROOT_PATH,
				fileId);
		FileInputStream fis = null;

		try {
			if (downloadFile(fileId, tempFilePath, userId, token, channel)) {
				File file = new File(tempFilePath);
				fis = new FileInputStream(file);

				byte[] data = new byte[1024];

				int rs = 0;

				while (0 < (rs = fis.read(data, 0, rs))) {
					output.write(data, 0, rs);
				}
			}
		} catch (IOException e) {
			throw super.getFsException(e);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}

		return false;
	}

	@Override
	public boolean downloadFile(String fileId, OutputStream output,
			SecurityToken securityToken) throws FsException {
		String userId = null;
		String token = null;
		String channel = null;

		if (null != securityToken) {
			userId = securityToken.getUserId();
			token = securityToken.getToken();
			channel = securityToken.getChannel();
		}

		return this.downloadFile(fileId, output, userId, token, channel);
	}

	@Override
	public boolean deleteFile(String fileId, String userId, String token,
			String channel) throws FsException {
		ParameterChecker.checkDeleteFile(fileId, userId, token, channel);

		String httpUrl = this.assembleUrl(getFsServerUrl(),
				FsUrlSubDirectory.URL_DELETE_FILE, fileId);

		// 组装参数
		Map<String, String> reqParams = new HashMap<String, String>(5);
		reqParams.put(DeleteFileRequestKey.USER_ID, userId);
		reqParams.put(DeleteFileRequestKey.TOKEN, token);
		reqParams.put(DeleteFileRequestKey.CHANNEL, channel);

		try {
			// 响应的json
			String respJson = HttpClientHelper.doPost(httpUrl, reqParams,
					getTimeout());

			HttpRespEnvelope resp = JSONObject.parseObject(respJson,
					HttpRespEnvelope.class);

			// 检查响应的对象
			return super.checkRespone(resp);
		} catch (Exception e) {
			throw super.getFsException(e);
		}
	}

	@Override
	public boolean deleteFile(String fileId, SecurityToken securityToken)
			throws FsException {
		ParameterChecker.checkSecureToken(securityToken);

		String userId = securityToken.getUserId();
		String token = securityToken.getToken();
		String channel = securityToken.getChannel();

		return this.deleteFile(fileId, userId, token, channel);
	}

	@Override
	public boolean deleteFile(String[] fileIds, String userId, String token,
			String channel) throws FsException {
		ParameterChecker.checkDeleteFile(fileIds, userId, token, channel);

		String httpUrl = this.assembleUrl(getFsServerUrl(),
				FsUrlSubDirectory.URL_DELETE_FILE);

		// 组装参数
		Map<String, String> reqParams = new HashMap<String, String>(5);
		reqParams.put(DeleteFileRequestKey.IS_BATCH_DEL,
				String.valueOf(Value.VALUE_1));
		reqParams.put(DeleteFileRequestKey.FILE_ID,
				this.formatArray2Str(fileIds));
		reqParams.put(DeleteFileRequestKey.USER_ID, userId);
		reqParams.put(DeleteFileRequestKey.TOKEN, token);
		reqParams.put(DeleteFileRequestKey.CHANNEL, channel);

		try {
			// 响应的json
			String respJson = HttpClientHelper.doPost(httpUrl, reqParams,
					getTimeout());

			HttpRespEnvelope resp = JSONObject.parseObject(respJson,
					HttpRespEnvelope.class);

			// 检查响应的对象
			return super.checkRespone(resp);
		} catch (Exception e) {
			throw super.getFsException(e);
		}
	}

	@Override
	public boolean deleteFile(String[] fileIds, SecurityToken securityToken)
			throws FsException {
		ParameterChecker.checkSecureToken(securityToken);

		String userId = securityToken.getUserId();
		String token = securityToken.getToken();
		String channel = securityToken.getChannel();

		return this.deleteFile(fileIds, userId, token, channel);
	}

	@Override
	public FileMetaInfo viewFileInfo(String fileId, String userId,
			String token, String channel) throws FsException {
		ParameterChecker.checkViewFileInfo(fileId, userId, token, channel);

		String httpUrl = this.assembleUrl(getFsServerUrl(),
				FsUrlSubDirectory.URL_VIEW_FILE_INFO, fileId);

		// 组装参数
		Map<String, String> reqParams = new HashMap<String, String>(5);
		reqParams.put(ViewFileInfoRequestKey.USER_ID, userId);
		reqParams.put(ViewFileInfoRequestKey.TOKEN, token);
		reqParams.put(ViewFileInfoRequestKey.CHANNEL, channel);

		try {
			// 响应的json
			String respJson = HttpClientHelper.doPost(httpUrl, reqParams,
					getTimeout());

			HttpRespEnvelope resp = JSONObject.parseObject(respJson,
					HttpRespEnvelope.class);

			// 检查响应的对象
			super.checkRespone(resp);

			return (FileMetaInfo) this.parseRespData(resp, FileMetaInfo.class);
		} catch (Exception e) {
			throw super.getFsException(e);
		}
	}

	@Override
	public FileMetaInfo viewFileInfo(String fileId, SecurityToken securityToken)
			throws FsException {
		String userId = null;
		String token = null;
		String channel = null;

		if (null != securityToken) {
			userId = securityToken.getUserId();
			token = securityToken.getToken();
			channel = securityToken.getChannel();
		}

		return this.viewFileInfo(fileId, userId, token, channel);
	}

	@Override
	public boolean setFilePermission(String fileId,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException {
		ParameterChecker.checkSetFilePermission(fileId, filePermission, userId,
				token, channel);

		String httpUrl = this.assembleUrl(getFsServerUrl(),
				FsUrlSubDirectory.URL_SET_PERMISSION, fileId);

		// 组装参数
		Map<String, String> reqParams = new HashMap<String, String>(5);
		reqParams.put(SetPermissionRequestKey.USER_ID, userId);
		reqParams.put(SetPermissionRequestKey.TOKEN, token);
		reqParams.put(SetPermissionRequestKey.CHANNEL, channel);
		reqParams.put(SetPermissionRequestKey.FILE_PERMISSION,
				filePermission.getValue());

		try {
			// 响应的json
			String respJson = HttpClientHelper.doPost(httpUrl, reqParams,
					getTimeout());

			HttpRespEnvelope resp = JSONObject.parseObject(respJson,
					HttpRespEnvelope.class);

			// 检查响应的对象
			return super.checkRespone(resp);
		} catch (Exception e) {
			throw super.getFsException(e);
		}
	}

	@Override
	public boolean setFilePermission(String fileId,
			FilePermission filePermission, SecurityToken securityToken)
			throws FsException {
		ParameterChecker.checkSecureToken(securityToken);

		String userId = securityToken.getUserId();
		String token = securityToken.getToken();
		String channel = securityToken.getChannel();

		return this.setFilePermission(fileId, filePermission, userId, token,
				channel);
	}

	@Override
	public boolean isAnonyUploadedFile(String fileId) {
		return CommonFileIdUtils.isAnonyUploadedFile(fileId);
	}

	/**
	 * 字符数组转换为逗号分隔的字符串
	 * 
	 * @param array
	 * @return
	 */
	private String formatArray2Str(String[] array) {
		StringBuilder build = new StringBuilder();

		for (String str : array) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}

			build.append(str).append(FsConstant.COMMA_CHAR);
		}

		int len = build.length();

		if (0 < len) {
			build.replace(len - 1, len, "");
		}

		return build.toString();
	}
}