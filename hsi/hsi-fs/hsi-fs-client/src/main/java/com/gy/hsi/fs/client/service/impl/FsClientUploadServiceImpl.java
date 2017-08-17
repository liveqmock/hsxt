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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.fs.client.common.ParameterChecker;
import com.gy.hsi.fs.client.common.utils.HttpMultipartUtils;
import com.gy.hsi.fs.client.service.IFsClientService;
import com.gy.hsi.fs.client.service.impl.parent.ParentClientService;
import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.beans.param.UploadBatchResponse;
import com.gy.hsi.fs.common.beans.param.UploadResponse;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.HttpRequestParam.UploadRequestKey;
import com.gy.hsi.fs.common.constant.HttpUrlDirectory.FsUrlSubDirectory;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.utils.FileUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.service.impl
 * 
 *  File Name       : FsClientUploadServiceImpl.java
 * 
 *  Creation Date   : 2015年7月14日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 实现文件上传接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class FsClientUploadServiceImpl extends ParentClientService
		implements IFsClientService {

	private static ThreadPoolTaskExecutor delFileTaskExecutor = null;
	
	static {
		try {
			delFileTaskExecutor = new ThreadPoolTaskExecutor();
			
			delFileTaskExecutor.setCorePoolSize(10);
			delFileTaskExecutor.setMaxPoolSize(100);
			delFileTaskExecutor.initialize();
		} catch (Exception e) {
		}
	}

	@Override
	public String uploadFile(String localFileName, String fileSuffix,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException {
		ParameterChecker.checkUploadFile(localFileName, fileSuffix,
				filePermission, userId, token, channel);

		SecurityToken securityToken = new SecurityToken(userId, token, channel);

		return this.actionSingleUploadFile(localFileName, fileSuffix,
				filePermission, securityToken, null);
	}

	@Override
	public String uploadFile(String localFileName, String fileSuffix,
			FilePermission filePermission, SecurityToken securityToken)
			throws FsException {
		ParameterChecker.checkUploadFile(localFileName, fileSuffix,
				filePermission, securityToken);

		return this.actionSingleUploadFile(localFileName, fileSuffix,
				filePermission, securityToken, null);
	}

	@Override
	public String uploadFile(byte[] fileBytesData, String fileSuffix,
			FilePermission filePermission, String userId, String token,
			String channel) throws FsException {
		ParameterChecker.checkUploadFile(fileBytesData, fileSuffix,
				filePermission, userId, token, channel);

		SecurityToken securityToken = new SecurityToken(userId, token, channel);

		return this.actionSingleUploadFile(fileBytesData, fileSuffix,
				filePermission, securityToken, null);
	}

	@Override
	public String uploadFile(byte[] fileBytesData, String fileSuffix,
			FilePermission filePermission, SecurityToken securityToken)
			throws FsException {
		ParameterChecker.checkUploadFile(fileBytesData, fileSuffix,
				filePermission, securityToken);

		return this.actionSingleUploadFile(fileBytesData, fileSuffix,
				filePermission, securityToken, null);
	}

	@Override
	public boolean overrideUploadFile(String fileId, String localFileName,
			String fileSuffix, String userId, String token, String channel)
			throws FsException {
		ParameterChecker.checkOverrideUploadFile(fileId, localFileName,
				fileSuffix, userId, token, channel);

		return this.actionOverrideUploadFile(fileId, localFileName, fileSuffix,
				new SecurityToken(userId, token, channel), null);
	}

	@Override
	public boolean overrideUploadFile(String fileId, String localFileName,
			String fileSuffix, SecurityToken securityToken) throws FsException {
		ParameterChecker.checkOverrideUploadFile(fileId, localFileName,
				fileSuffix, securityToken);

		return this.actionOverrideUploadFile(fileId, localFileName, fileSuffix,
				securityToken, null);
	}

	@Override
	public boolean overrideUploadFile(String fileId, byte[] fileBytesData,
			String fileSuffix, String userId, String token, String channel)
			throws FsException {
		ParameterChecker.checkOverrideUploadFile(fileId, fileBytesData,
				fileSuffix, userId, token, channel);

		return this.actionOverrideUploadFile(fileId, fileBytesData, fileSuffix,
				new SecurityToken(userId, token, channel), null);
	}

	@Override
	public boolean overrideUploadFile(String fileId, byte[] fileBytesData,
			String fileSuffix, SecurityToken securityToken) throws FsException {
		ParameterChecker.checkOverrideUploadFile(fileId, fileBytesData,
				fileSuffix, securityToken);

		return this.actionOverrideUploadFile(fileId, fileBytesData, fileSuffix,
				securityToken, null);
	}

	@Override
	public String uploadPublicFile(String localFileName, String fileSuffix,
			String userId, String token, String channel, boolean byAnonymousMode)
			throws FsException {
		ParameterChecker.checkUploadPublicFile(localFileName, fileSuffix,
				userId, token, channel);

		Map<String, String> otherParams = new HashMap<String, String>(1);
		otherParams.put(UploadRequestKey.BY_ANONYMOUS_MODE,
				String.valueOf(byAnonymousMode));

		return this.actionSingleUploadFile(localFileName, fileSuffix,
				FilePermission.PUBLIC_READ, new SecurityToken(userId, token,
						channel), otherParams);
	}

	@Override
	public String uploadPublicFile(String localFileName, String fileSuffix,
			SecurityToken securityToken, boolean byAnonymousMode)
			throws FsException {
		ParameterChecker.checkUploadPublicFile(localFileName, fileSuffix,
				securityToken);

		Map<String, String> otherParams = new HashMap<String, String>(1);
		otherParams.put(UploadRequestKey.BY_ANONYMOUS_MODE,
				String.valueOf(byAnonymousMode));

		return this.actionSingleUploadFile(localFileName, fileSuffix,
				FilePermission.PUBLIC_READ, securityToken, otherParams);
	}

	@Override
	public String uploadPublicFile(byte[] fileBytesData, String fileSuffix,
			String userId, String token, String channel, boolean byAnonymousMode)
			throws FsException {
		ParameterChecker.checkUploadPublicFile(fileBytesData, fileSuffix,
				userId, token, channel);

		Map<String, String> otherParams = new HashMap<String, String>(1);
		otherParams.put(UploadRequestKey.BY_ANONYMOUS_MODE,
				String.valueOf(byAnonymousMode));

		return this.actionSingleUploadFile(fileBytesData, fileSuffix,
				FilePermission.PUBLIC_READ, new SecurityToken(userId, token,
						channel), otherParams);
	}

	@Override
	public String uploadPublicFile(byte[] fileBytesData, String fileSuffix,
			SecurityToken securityToken, boolean byAnonymousMode)
			throws FsException {
		ParameterChecker.checkUploadPublicFile(fileBytesData, fileSuffix,
				securityToken);

		Map<String, String> otherParams = new HashMap<String, String>(1);
		otherParams.put(UploadRequestKey.BY_ANONYMOUS_MODE,
				String.valueOf(byAnonymousMode));

		return this.actionSingleUploadFile(fileBytesData, fileSuffix,
				FilePermission.PUBLIC_READ, securityToken, otherParams);
	}

	@Override
	public List<String> uploadFileByBatch(String[] localFileNames,
			String[] fileSuffixes, FilePermission filePermission,
			String userId, String token, String channel) throws FsException {
		ParameterChecker.checkUploadFileByBatch(localFileNames, fileSuffixes,
				filePermission, userId, token, channel);

		SecurityToken securityToken = new SecurityToken(userId, token, channel);

		// 执行文件上传
		return this.actionBatchUploadFile(localFileNames, fileSuffixes,
				filePermission, securityToken, null);
	}

	@Override
	public List<String> uploadFileByBatch(String[] localFileNames,
			String[] fileSuffixes, FilePermission filePermission,
			SecurityToken securityToken) throws FsException {
		ParameterChecker.checkUploadFileByBatch(localFileNames, fileSuffixes,
				filePermission, securityToken);

		// 执行文件上传
		return this.actionBatchUploadFile(localFileNames, fileSuffixes,
				filePermission, securityToken, null);
	}

	@Override
	public List<String> uploadFileByBatch(List<byte[]> fileBytesDataList,
			List<String> fileSuffixList, FilePermission filePermission,
			String userId, String token, String channel) throws FsException {
		ParameterChecker.checkUploadFileByBatch(fileBytesDataList,
				fileSuffixList, filePermission, userId, token, channel);

		// 执行文件上传
		return this.actionBatchUploadFile(fileBytesDataList,
				fileSuffixList.toArray(new String[] {}), filePermission,
				new SecurityToken(userId, token, channel), null);
	}

	@Override
	public List<String> uploadFileByBatch(List<byte[]> fileBytesDataList,
			List<String> fileSuffixList, FilePermission filePermission,
			SecurityToken securityToken) throws FsException {
		ParameterChecker.checkUploadFileByBatch(fileBytesDataList,
				fileSuffixList, filePermission, securityToken);

		// 执行文件上传
		return this.actionBatchUploadFile(fileBytesDataList,
				fileSuffixList.toArray(new String[] {}), filePermission,
				securityToken, null);
	}

	/**
	 * 执行文件覆盖上传[文件路径方式]
	 * 
	 * @param fileId
	 * @param localFileName
	 * @param fileSuffix
	 * @param securityToken
	 * @param otherParams
	 * @return
	 * @throws FsException
	 */
	private boolean actionOverrideUploadFile(String fileId,
			String localFileName, String fileSuffix,
			SecurityToken securityToken, Map<String, String> otherParams)
			throws FsException {
		if (null == otherParams) {
			otherParams = new HashMap<String, String>(1);
		}

		// 对于覆盖上传一定要传递文件id
		otherParams.put(UploadRequestKey.FILE_ID, fileId);

		// 执行文件上传, 文件覆盖上传时, 不传递新的文件权限, 而使用默认的
		this.actionSingleUploadFile(localFileName, fileSuffix, null,
				securityToken, otherParams);

		return true;
	}

	/**
	 * 执行文件覆盖上传[字节方式]
	 * 
	 * @param fileId
	 * @param fileBytesData
	 * @param fileSuffix
	 * @param securityToken
	 * @param otherParams
	 * @return
	 * @throws FsException
	 */
	private boolean actionOverrideUploadFile(String fileId,
			byte[] fileBytesData, String fileSuffix,
			SecurityToken securityToken, Map<String, String> otherParams)
			throws FsException {
		if (null == otherParams) {
			otherParams = new HashMap<String, String>(1);
		}

		// 对于覆盖上传一定要传递文件id
		otherParams.put(UploadRequestKey.FILE_ID, fileId);

		// 执行文件上传, 文件覆盖上传时, 不传递新的文件权限, 而使用默认的
		this.actionSingleUploadFile(fileBytesData, fileSuffix, null,
				securityToken, otherParams);

		return true;
	}

	/**
	 * 执行文件上传[文件路径方式]
	 * 
	 * @param localFileName
	 * @param fileSuffix
	 * @param filePermission
	 * @param securityToken
	 * @param otherParams
	 * @return
	 * @throws FsException
	 */
	private String actionSingleUploadFile(String localFileName,
			String fileSuffix, FilePermission filePermission,
			SecurityToken securityToken, Map<String, String> otherParams)
			throws FsException {
		String[] localFileNames = new String[] { localFileName };
		String[] fileSuffixes = new String[] { fileSuffix };

		// 执行文件上传
		List<String> fileIds = this.actionBatchUploadFile(localFileNames,
				fileSuffixes, filePermission, securityToken, otherParams);

		if ((null != fileIds) && (0 < fileIds.size())) {
			return fileIds.get(0);
		}

		return null;
	}

	/**
	 * 执行文件上传[字节方式]
	 * 
	 * @param fileBytesData
	 * @param fileSuffix
	 * @param filePermission
	 * @param securityToken
	 * @param otherParams
	 * @return
	 * @throws FsException
	 */
	private String actionSingleUploadFile(byte[] fileBytesData,
			String fileSuffix, FilePermission filePermission,
			SecurityToken securityToken, Map<String, String> otherParams)
			throws FsException {
		List<byte[]> fileBytesDataList = new ArrayList<byte[]>(1);
		fileBytesDataList.add(fileBytesData);

		String[] fileSuffixes = new String[] { fileSuffix };

		// 执行文件上传
		List<String> fileIds = this.actionBatchUploadFile(fileBytesDataList,
				fileSuffixes, filePermission, securityToken, otherParams);

		if ((null != fileIds) && (0 < fileIds.size())) {
			return fileIds.get(0);
		}

		return null;
	}

	/**
	 * 执行文件批量上传[文件路径方式]
	 * 
	 * @param localFileNames
	 * @param fileSuffixes
	 * @param filePermission
	 * @param securityToken
	 * @param otherParams
	 * @return
	 * @throws FsException
	 */
	private List<String> actionBatchUploadFile(String[] localFileNames,
			String[] fileSuffixes, FilePermission filePermission,
			SecurityToken securityToken, Map<String, String> otherParams)
			throws FsException {
		File[] files = new File[localFileNames.length];

		for (int i = 0; i < localFileNames.length; i++) {
			files[i] = new File(localFileNames[i]);
		}

		return this.actionBatchUploadFile(files, fileSuffixes, filePermission,
				securityToken, true, otherParams);
	}

	/**
	 * 执行文件批量上传[字节方式]
	 * 
	 * @param fileBytesDataList
	 * @param fileSuffixes
	 * @param filePermission
	 * @param securityToken
	 * @param otherParams
	 * @return
	 * @throws FsException
	 */
	private List<String> actionBatchUploadFile(List<byte[]> fileBytesDataList,
			String[] fileSuffixes, FilePermission filePermission,
			SecurityToken securityToken, Map<String, String> otherParams)
			throws FsException {
		File[] files = new File[fileBytesDataList.size()];

		int index = 0;

		for (byte[] b : fileBytesDataList) {
			files[index] = FileUtils.getTempFile(b);

			index++;
		}

		// 对于通过字节方式传递, 文件名称是临时的
		return this.actionBatchUploadFile(files, fileSuffixes, filePermission,
				securityToken, false, otherParams);
	}

	/**
	 * 执行文件批量上传
	 * 
	 * @param files
	 * @param fileSuffixes
	 * @param filePermission
	 * @param securityToken
	 * @param isValidFileName
	 * @param otherParams
	 * @return
	 * @throws FsException
	 */
	private List<String> actionBatchUploadFile(File[] files,
			String[] fileSuffixes, FilePermission filePermission,
			SecurityToken securityToken, boolean isValidFileName,
			Map<String, String> otherParams) throws FsException {
		HttpRespEnvelope resp = null;

		try {
			boolean isBatchUpload = (2 <= files.length);

			// 站点路径
			String uploadSubdir = (isBatchUpload ? FsUrlSubDirectory.URL_UPLOAD_BATCH
					: FsUrlSubDirectory.URL_UPLOAD);

			// 文件权限
			String permission = (null == filePermission) ? "" : filePermission
					.name();

			// 如果是多文件上传,则以英文逗号隔开(如果存在英文逗号, 则替换为中文)
			String fileName = isValidFileName ? formatFilenames(files) : "";

			// 如果是多文件上传,则以英文逗号隔开(如果存在英文逗号, 则替换为中文)
			String fileSuffix = this.formatFileSuffix(fileSuffixes,
					files.length);

			String userId = securityToken.getUserId();
			String token = securityToken.getToken();
			String channel = securityToken.getChannel();

			String uploadUrl = this.assembleUrl(getFsServerUrl(), uploadSubdir);

			// 组装参数
			Map<String, String> textParams = new HashMap<String, String>(5);
			textParams.put(UploadRequestKey.FILE_NAME, fileName);
			textParams.put(UploadRequestKey.FILE_SUFFIX, fileSuffix);
			textParams.put(UploadRequestKey.FILE_PERMISSION, permission);
			textParams.put(UploadRequestKey.USER_ID, userId);
			textParams.put(UploadRequestKey.TOKEN, token);
			textParams.put(UploadRequestKey.CHANNEL, channel);
			textParams.put(UploadRequestKey.$IS_VALID_FILENAME,
					String.valueOf(isValidFileName));

			if ((null != otherParams) && (0 < otherParams.size())) {
				textParams.putAll(otherParams);
			}

			String resultJson = HttpMultipartUtils.doMultipartPost(uploadUrl,
					files, textParams, getTimeout());

			resp = JSONObject.parseObject(resultJson, HttpRespEnvelope.class);

			// 检查响应的对象
			super.checkRespone(resp);

			List<String> fileIds = new ArrayList<String>(2);

			// 批量上传
			if (isBatchUpload) {
				UploadBatchResponse respData = (UploadBatchResponse) this
						.parseRespData(resp, UploadBatchResponse.class);

				fileIds.addAll(respData.getFileIds());
			}
			// 单文件上传
			else {
				UploadResponse respData = (UploadResponse) this.parseRespData(
						resp, UploadResponse.class);

				fileIds.add(respData.getFileId());
			}

			return fileIds;
		} catch(FileNotFoundException e) {
			resp = new HttpRespEnvelope(
					FileOptResultCode.OPT_FAILED.getValue(),
					"The uploaded file is not found, please check if the FS client is wrong! For example, the setting of user.dir is error.");

			throw super.getFsException(resp, e);
		} catch (IOException e) {
			resp = new HttpRespEnvelope(
					FileOptResultCode.SERVER_NOT_AVAILABLE.getValue(),
					"The FS server is not available, please check if the FS server address is right! Yours is:"
							+ getFsServerUrl());

			throw super.getFsException(resp, e);
		} catch (Exception e) {
			throw super.getFsException(resp, e);
		} finally {
			this.cleanTempFile(files, isValidFileName);
		}
	}

	/**
	 * 清理上传时产生的临时文件
	 * 
	 * @param files
	 * @param isValidFileName
	 */
	private void cleanTempFile(final File[] files, boolean isValidFileName) {
		if ((null == files) || (0 >= files.length) || isValidFileName) {
			return;
		}

		// 上传成功后一定及时清理掉临时文件
		Runnable task = new Runnable() {
			@Override
			public void run() {
				for (File file : files) {
					try {
						FileUtils.deleteFile(file);
					} catch (Exception e) {
					}
				}
			}
		};

		try {
			if (null != delFileTaskExecutor) {
				delFileTaskExecutor.execute(task);
			} else {
				throw new NullPointerException("The object delFileTaskExecutor is null!");
			}
		} catch (RejectedExecutionException | NullPointerException ex) {
			task.run();
		}
	}
}
