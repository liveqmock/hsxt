package com.gy.hsi.fs.server.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.param.DownloadRequest;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsApiConstant.FsRespHeaderKey;
import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.constant.HttpRequestParam.DownloadRequestKey;
import com.gy.hsi.fs.common.constant.HttpUrlDirectory.FsUrlKeywords;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.exception.FsParameterException;
import com.gy.hsi.fs.common.exception.FsPermissionException;
import com.gy.hsi.fs.common.exception.FsSecureTokenException;
import com.gy.hsi.fs.common.exception.FsServerNotAvailableException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.FunctionIndex;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.OptUserType;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLog;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.FileName;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaInfo;
import com.gy.hsi.fs.server.common.utils.ExceptionUtils;
import com.gy.hsi.fs.server.common.utils.FileIdHelper;
import com.gy.hsi.fs.server.controllers.parent.BasicController;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.controlers
 * 
 *  File Name       : DownloadController.java
 * 
 *  Creation Date   : 2015-05-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收文件下载请求
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping(FsUrlKeywords.FS)
public class DownloadController extends BasicController {

	/**
	 * 文件下载接口 [允许对外网开放 O_O]
	 * 
	 * 访问地址： http://.../fs/download/文件id?userId=&token=&channel=
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(FsUrlKeywords.DOWNLOAD + "/**")
	public void handleDownloadRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpRespEnvelope respEnv = null;

		// 解析请求参数
		DownloadRequest reqParam = new DownloadRequest(request);

		try {
			// 设置返回结果状态, 注意一定要放到reponse响应的前面, 不要随意颠倒顺序
			response.setHeader(FsRespHeaderKey.RESULT_CODE, ""
					+ FileOptResultCode.OPT_SUCCESS.getValue());

			// 处理下载逻辑
			this.doHandleDownloadFileRequest(reqParam, response);
		} catch (Exception ex) {
			logger.debug("下载文件失败：", ex);

			respEnv = new HttpRespEnvelope(ex);

			response.setHeader(FsRespHeaderKey.RESULT_CODE,
					String.valueOf(respEnv.getResultCode()));
			response.setHeader(FsRespHeaderKey.RESULT_DESC,
					respEnv.getResultDesc());

			super.doHttpResponse(response, "text/plain",
					respEnv.toJsonString(true));
		} finally {
			// 判断是否处理失败
			boolean isFailed = (null == respEnv) ? false
					: (FileOptResultCode.OPT_SUCCESS.getValue() != respEnv.getResultCode());

			// 为了保证性能, 只有下载失败才记录日志(且文件id合法、非匿名上传)
			if (isFailed) {
				String fileId = reqParam.getFileId();

				if (!FileIdHelper.checkFileId(fileId)
						|| FileIdHelper.isAnonyFileId(fileId)) {
					return;
				}

				// 记录操作日志
				this.recordOptLog(reqParam, respEnv, request);
			}
		}
	}

	/**
	 * 处理下载文件请求
	 * 
	 * @param reqParam
	 * @param response
	 * @return
	 * @throws FsException
	 */
	private void doHandleDownloadFileRequest(DownloadRequest reqParam,
			HttpServletResponse response) throws FsException {
		// 校验请求参数
		this.checkRequestParam(reqParam);

		// 文件id
		String fileId = reqParam.getFileId();

		// 文件后缀
		String fileSuffix = reqParam.getFileSuffix();

		// 文件存储id
		String fileStorageId = "";

		// ------------- 对于公共权限文件, 也是匿名上传的文件(为了提高'匿名上传'的公共文件访问性能) ----
		if (FileIdHelper.isAnonyFileId(fileId)) {
			// 解析出文件存储id
			fileStorageId = FileIdHelper.parseAnonyFileStoreId(fileId);
		}
		// --------------- 对于受限权限文件, 去数据库进行权限校验 ----------------------------
		else {
			// 解析出文件存储id
			fileStorageId = this.getSecureFileStoreId(reqParam);
		}

		// 文件存储id不为空, 开始去TFS或HDFS下载文件
		if (!StringUtils.isEmpty(fileStorageId)) {
			// 设置编码
			response.setCharacterEncoding(FsConstant.ENCODING_UTF8);

			// 流能够自动识别文件的类型
			// response.setContentType("application/octet-stream");

			if (reqParam.isUseSavedFileName()) {
				response.setContentType("application/octet-stream");
				response.addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ getSavedFileNameByFileStorageId(fileStorageId, fileId));
			}

			boolean downloadSuccess = false;

			// 根据文件存储id去文件实体存放的系统下载文件
			try {
				downloadSuccess = taobaoTfsClientService.fetchFile(
						fileStorageId, fileSuffix, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (!downloadSuccess) {
				throw new FsServerNotAvailableException(
						"Fail to download, caused by FS inner error !");
			}

			// 重要, 勿删！！！！！！！
			return;
		}

		throw new FsFileNotExistException(
				"The file does not exist ! Your input is: fileId=" + fileId);
	}

	/**
	 * 校验请求参数
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private boolean checkRequestParam(DownloadRequest reqParam)
			throws FsException {
		// 文件id
		String fileId = reqParam.getFileId();

		if (StringUtils.isEmpty(fileId)) {
			throw new FsParameterException("The parameter '"
					+ DownloadRequestKey.FILE_ID + "' can't be null !");
		}

		if (!FileIdHelper.isAnonyFileId(fileId) && !isPublicReadFile(fileId)) {
			String token = reqParam.getToken();

			if (StringUtils.isEmpty(token)) {
				throw new FsSecureTokenException(
						"For the non public permission file, you need to provide the user id and secure token when you download it!");
			}

			// 检查token
			super.checkSecureToken(reqParam.getUserId(), reqParam.getToken(),
					reqParam.getChannel());
		}

		return true;
	}

	/**
	 * 获得非public文件的存储id
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private String getSecureFileStoreId(DownloadRequest reqParam)
			throws FsException {
		// 文件id
		String fileId = reqParam.getFileId();

		SimpleFileMetaInfo fileMetaInfoInDB = fileMetaDataService
				.querySimpleFileMetaInfo(fileId);

		// 校验权限
		this.checkPermission(fileMetaInfoInDB, reqParam);

		return fileMetaInfoInDB.getFileStorageId();
	}

	/**
	 * 校验权限
	 * 
	 * @param simpleFileMetaInfo
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private boolean checkPermission(SimpleFileMetaInfo simpleFileMetaInfo,
			DownloadRequest reqParam) throws FsException {
		// 文件不存在
		if (null == simpleFileMetaInfo) {
			throw new FsFileNotExistException("The file does not exist !");
		}

		String fileSuffix = reqParam.getFileSuffix();

		// 文件名称后缀与上传时指定的不一致
		if (!StringUtils.isEmpty(fileSuffix)) {

			// 数据库中保存的后缀, 如果上传时传递了文件后缀, 则下载时传递的文件后缀必须跟其一致
			String fileSuffixInDB = simpleFileMetaInfo.getFileSuffix();

			if (!StringUtils.isEmpty(fileSuffixInDB)
					&& !fileSuffixInDB.replaceAll("^\\.", "").equals(
							fileSuffix.replaceAll("^\\.", ""))) {
				throw new FsParameterException(
						"The file suffix is not consistent with saved when uploaded !");
			}
		}

		// 文件权限
		FilePermission permission = FilePermission
				.getNameByValue(simpleFileMetaInfo.getFilePermission());

		// 是否匿名上传
		boolean isAnonyUploaded = simpleFileMetaInfo.isAnonymous();

		// 操作用户类型
		OptUserType optUserType = getOptUserType(reqParam.getUserId(),
				simpleFileMetaInfo.getOwnerUserId());

		// 使用权限矩阵校验权限
		if (!checkPermissionWithMatrix(permission, isAnonyUploaded,
				optUserType, FunctionIndex.FILE_DOWNLOAD)) {
			if (permission == FilePermission.PRIVATE_READ_WRITE) {
				throw new FsPermissionException(
						"For the private permission file, only the file owner can download it !");
			}

			if ((permission == FilePermission.PROTECTED_READ)
					|| (permission == FilePermission.PROTECTED_READ_WRITE)) {
				throw new FsPermissionException(
						"For the protected permission file, you can't download it by anonymous mode !");
			}

			throw new FsPermissionException(
					"You don't have permission to download the file, because which permission is '"
							+ permission + "' !");
		}

		return true;
	}

	/**
	 * 是否为'公共可读'文件
	 * 
	 * @param fileId
	 * @return
	 * @throws FsParameterException
	 */
	private boolean isPublicReadFile(String fileId) throws FsException {
		// 文件id
		return fileMetaDataService.isPublicReadFile(fileId);
	}

	/**
	 * 获取文件上传时传递的文件名称
	 * 
	 * @param fileStorageId
	 * @param defaultFileName
	 * @return
	 */
	private String getSavedFileNameByFileStorageId(String fileStorageId,
			String defaultFileName) {
		FileName fileName = fileMetaDataService
				.queryFileNameByFileStorageId(fileStorageId);

		return (null != fileName) ? fileName.getFullFileName(defaultFileName)
				: defaultFileName;
	}

	/**
	 * 记录操作日志
	 * 
	 * @param fileId
	 * @param token
	 * @param optUserId
	 * @param reqParam
	 * @param respEnv
	 * @param request
	 */
	protected void recordOptLog(final DownloadRequest reqParam,
			final HttpRespEnvelope respEnv, final HttpServletRequest request) {
		// 文件id
		final String fileId = reqParam.getFileId();

		if (!FileIdHelper.checkFileId(fileId)) {
			logger.info("文件id非法,不记录日志 ！！");

			return;
		}

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					// 文件id
					String fileId = reqParam.getFileId();
					String optUserId = reqParam.getUserId();
					String secureToken = reqParam.getToken();
					String channel = reqParam.getChannel();

					TFsFileOperationLog log = new TFsFileOperationLog();
					log.setFileId(fileId);
					log.setFunctionId(reqParam.getClass().getSimpleName());
					log.setFunctionParam(JSONObject.toJSONString(reqParam));
					log.setOptIpAddr(request.getRemoteAddr());
					log.setOptUserId(optUserId);
					log.setSecureToken(secureToken);
					log.setChannel(channel);

					if (null != respEnv) {
						log.setOptStatus(respEnv.getResultCode());
						log.setOptErrDesc(respEnv.getResultDesc());
						log.setOptErrDetail(ExceptionUtils
								.getStackTraceInfo(respEnv.fetchException()));
					} else {
						log.setOptStatus(FileOptResultCode.OPT_SUCCESS
								.getValue());
					}

					optLogService.insertOperationLog(log);
				} catch (Exception e) {
				}
			}
		};

		// 异步记录日志
		recordLogTaskExecutor.execute(runnable);
	}
}
