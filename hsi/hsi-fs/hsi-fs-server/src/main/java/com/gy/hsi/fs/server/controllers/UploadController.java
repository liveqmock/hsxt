package com.gy.hsi.fs.server.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.param.UploadBatchResponse;
import com.gy.hsi.fs.common.beans.param.UploadRequest;
import com.gy.hsi.fs.common.beans.param.UploadResponse;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.constant.HttpRequestParam.SetPermissionRequestKey;
import com.gy.hsi.fs.common.constant.HttpUrlDirectory.FsUrlKeywords;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.exception.FsFileSizeOverLimitException;
import com.gy.hsi.fs.common.exception.FsParameterException;
import com.gy.hsi.fs.common.exception.FsPermissionException;
import com.gy.hsi.fs.common.exception.FsServerHandleException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.constant.FsServerConstant;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.FunctionIndex;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.OptUserType;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaData;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLog;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaInfo;
import com.gy.hsi.fs.server.common.utils.ExceptionUtils;
import com.gy.hsi.fs.server.common.utils.FileIdHelper;
import com.gy.hsi.fs.server.controllers.parent.BasicController;

/***************************************************************************
 * <PRE>
 *  Project Name    : fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.controler
 * 
 *  File Name       : UploadController.java
 * 
 *  Creation Date   : 2015-05-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收来自外部系统的http文件上传请求
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping(FsUrlKeywords.FS)
public class UploadController extends BasicController {

	/** 上传文件临时缓冲目录 **/
	private static File tempUploadCaceDirectory;

	static {
		tempUploadCaceDirectory = new File(
				FsServerConstant.UPLOAD_FILE_TEMPCACHE_ROOT_DIR);
	}

	/**
	 * 单个或多个文件上传接口 [允许对外网开放 O_O]
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(FsUrlKeywords.UPLOAD + "/**")
	public void handleUploadRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.actionHandleUploadRequest(request, response, false);
	}

	/**
	 * 批量文件上传接口 [允许对外网开放 O_O]
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(FsUrlKeywords.UPLOAD_BATCH + "/**")
	public void handleBatchUploadRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.actionHandleUploadRequest(request, response, true);
	}

	/**
	 * 上传的逻辑处理
	 * 
	 * @param request
	 * @param response
	 * @param isBatch
	 * @throws Exception
	 */
	private void actionHandleUploadRequest(HttpServletRequest request,
			HttpServletResponse response, boolean isBatch) throws Exception {
		HttpRespEnvelope respEnv = null;
		List<UploadRequest> hookParameter = new ArrayList<UploadRequest>(2);

		try {
			respEnv = this.actionHandleUploadRequest(request, hookParameter);
		} catch (Exception e) {
			logger.error(isBatch ? "文件批量上传失败：" : "文件上传失败：", e);

			respEnv = new HttpRespEnvelope(e);
		}
		
		// 记录日志
		this.recordUploadOptLog(hookParameter, respEnv, request);

		boolean success = this.isHandleSuccess(respEnv);

		// 响应处理结果
		super.doHttpResponse(response, "text/plain",
				respEnv.toJsonString(!success));
	}

	/**
	 * 处理上传文件请求
	 * 
	 * @param request
	 * @param hookParameter
	 * @return
	 * @throws FsException
	 */
	private HttpRespEnvelope actionHandleUploadRequest(
			HttpServletRequest request, List<UploadRequest> hookParameter)
			throws FsException {
		String fileId = "";
		String token = ""; // 安全令牌

		List<String> uploadFileIds = new ArrayList<String>(2);

		if (true) {
			List<UploadRequest> uploadRequestParams = this
					.parseHttpUploadRequest(request);

			if (null != hookParameter) {
				hookParameter.addAll(uploadRequestParams);
			}

			// 校验请求参数
			this.checkRequestParam(uploadRequestParams);

			for (UploadRequest param : uploadRequestParams) {
				if (StringUtils.isEmpty(token)) {
					token = param.getToken();
				}

				// 进行上传并入库
				try {
					fileId = this.actionHandleUploadRequest(param);

					uploadFileIds.add(fileId);
				} catch (Exception e) {
					rollbackFailedUploadedFile(uploadFileIds);

					if (e instanceof FsException) {
						throw e;
					}

					break;
				}
			}

			if (0 >= uploadFileIds.size()) {
				throw new FsServerHandleException(
						"Failed to upload, try again please !");
			}
		}

		// 创建http响应的数据信封
		HttpRespEnvelope envelope = new HttpRespEnvelope();

		// 批量上传
		boolean isBatch = (2 <= uploadFileIds.size());

		if (!isBatch) {
			envelope.setData(new UploadResponse(uploadFileIds.get(0)));// 单个文件上传
		} else {
			envelope.setData(new UploadBatchResponse(uploadFileIds));// 批量文件上传
		}

		return envelope;
	}

	/**
	 * 回滚上传失败时已经被上传成功的文件
	 * 
	 * @param uploadFileIds
	 */
	private void rollbackFailedUploadedFile(List<String> uploadFileIds) {
		if (0 >= uploadFileIds.size()) {
			return;
		}

		for (String fileId : uploadFileIds) {
			try {
				fileMetaDataService.deleteFileByFileId(fileId);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 进行上传
	 * 
	 * @param param
	 * @return 文件id
	 * @throws FsException
	 */
	private String actionHandleUploadRequest(UploadRequest param)
			throws FsException {
		String fileStorageId = "";
		String secureToken = param.getToken();
		String fileId = param.getFileId();
		String ownerUserId = param.getUserId();

		// 是否为匿名上传
		boolean isByAnonymousMode = param.isByAnonymousMode();

		// 是否为覆盖上传
		boolean isOverrideUpload = !StringUtils.isEmpty(fileId);

		// 如果为覆盖上传
		if (isOverrideUpload) {
			return this.actionOverrideUpload(param);
		}

		// 如果是匿名上传, 不保存上传者id, 即：此文件将是无属主文件
		if (isByAnonymousMode) {
			ownerUserId = null;
		}

		// 上传小文件
		if (FsServerConstant.SMALL_FILE_SIZE_UPPER_LIMIT >= param
				.getFileSizeBytes()) {
			fileStorageId = taobaoTfsClientService.saveFile(
					param.getFileContentBytes(), param.getFileSuffix());
		}
		// 上传大文件
		else {
			String key = parseFileUploadUUID(param, ownerUserId, secureToken);

			fileStorageId = taobaoTfsClientService.saveLargeFile(
					param.getFileContentBytes(), param.getFileSuffix(), key);
		}

		// 如果文件存储id不为空, 则表示上传成功
		if (!StringUtils.isEmpty(fileStorageId)) {
			// 获得一个唯一的文件id
			fileId = this.getUniqueFileId(isByAnonymousMode, fileStorageId);

			// 将上传成功的文件元数据插入数据库表
			this.insertUploadFileMetaData2DB(param, fileId, fileStorageId,
					ownerUserId, isByAnonymousMode);

			return fileId;
		}

		throw new FsServerHandleException(
				"Failed to upload the file, caused by FS inner error!");
	}

	/**
	 * 进行'覆盖'上传
	 * 
	 * @param param
	 * @return 文件id
	 * @throws FsException
	 */
	private String actionOverrideUpload(UploadRequest param) throws FsException {
		String newFileStorageId = "";
		String oldFileStorageId = "";
		String secureToken = param.getToken();
		String fileId = param.getFileId();
		String optUserId = param.getUserId();

		SimpleFileMetaInfo fileMetaInfo = fileMetaDataService
				.querySimpleFileMetaInfo(fileId);
		oldFileStorageId = fileMetaInfo.getFileStorageId();

		// 校验权限
		this.checkPermissionForOverriding(fileMetaInfo, optUserId);

		// 上传小文件
		if (FsServerConstant.SMALL_FILE_SIZE_UPPER_LIMIT >= param
				.getFileSizeBytes()) {
			newFileStorageId = taobaoTfsClientService.saveFile(
					param.getFileContentBytes(), param.getFileSuffix());
		}
		// 上传大文件
		else {
			String key = parseFileUploadUUID(param, optUserId, secureToken);

			newFileStorageId = taobaoTfsClientService.saveLargeFile(
					param.getFileContentBytes(), param.getFileSuffix(), key);
		}

		// 如果文件存储id不为空, 则表示上传成功
		if (!StringUtils.isEmpty(newFileStorageId)) {
			// 从TFS中删除掉原有文件
			taobaoTfsClientService.deleteFile(oldFileStorageId,
					fileMetaInfo.getFileSuffix());

			// 将覆盖上传的数据入库
			this.updateFileMetaData2DB(param, fileId, newFileStorageId, fileMetaInfo.getOwnerUserId());

			return fileId;
		}

		throw new FsServerHandleException(
				"Failed to upload the file, caused by FS inner error!");
	}

	/**
	 * 将上传成功的文件元数据插入数据库表
	 * 
	 * @param param
	 * @param fileId
	 * @param fileStorageId
	 * @param ownerUserId
	 * @param isByAnonymousMode
	 * @return
	 * @throws FsException
	 */
	private boolean insertUploadFileMetaData2DB(UploadRequest param,
			String fileId, String fileStorageId, String ownerUserId,
			boolean isByAnonymousMode) throws FsException {
		// 入库
		try {
			String permission = isByAnonymousMode ? FilePermission.PUBLIC_READ
					.getValue() : FilePermission.getValue(param
					.getFilePermission());

			if (StringUtils.isEmpty(permission)) {
				permission = FilePermission.PUBLIC_READ.getValue();
			}

			// 文件元数据对象
			TFsFileMetaData fileMetaData = new TFsFileMetaData();
			fileMetaData.setFileId(fileId);
			fileMetaData.setFileName(param.getFileName());
			fileMetaData.setFileSuffix(param.getFileSuffix());
			fileMetaData.setFilePermission(permission);
			fileMetaData.setFileSizeBytes(param.getFileSizeBytes());
			fileMetaData.setFileStorageId(fileStorageId);
			fileMetaData.setOwnerUserId(ownerUserId);
			fileMetaData.setIsAnonymous(isByAnonymousMode);

			return fileMetaDataService.insertFileMetaData(fileMetaData);
		} catch (Exception e) {
			throw new FsServerHandleException(
					"Fail to upload the file, caused by FS inner error! detail:"
							+ e.getMessage(), e);
		}
	}

	/**
	 * 将上传成功的文件元数据插入数据库表
	 * 
	 * @param param
	 * @param fileId
	 * @param fileStorageId
	 * @param ownerUserId
	 * @return
	 * @throws FsException
	 */
	private boolean updateFileMetaData2DB(UploadRequest param, String fileId,
			String fileStorageId, String ownerUserId) throws FsException {
		// 入库
		try {
			// 文件元数据对象
			TFsFileMetaData fileMetaData = new TFsFileMetaData();
			fileMetaData.setFileId(fileId);
			fileMetaData.setFileName(param.getFileName());
			fileMetaData.setFileSuffix(param.getFileSuffix());
			fileMetaData.setFileSizeBytes(param.getFileSizeBytes());
			fileMetaData.setFileStorageId(fileStorageId);
			
			// 迁移数据特殊处理!!!!!!!
			if(isTransferData(ownerUserId)) {
				fileMetaData.setOwnerUserId(param.getUserId());
			}

			return fileMetaDataService.updateFileMetaData(fileMetaData);
		} catch (Exception e) {
			throw new FsServerHandleException(
					"Fail to upload the file, caused by FS inner error! detail:"
							+ e.getMessage(), e);
		}
	}

	/**
	 * 生成一个不重复的文件id[最多重试3次, 重复的概率非常小, 所以重试的几率也是很低的, 只是为了增加容错性]
	 * 
	 * @param isAnonymous
	 * @param fileStoreId
	 * @return
	 */
	private String getUniqueFileId(boolean isAnonymous, String fileStoreId) {
		String fileId = "";

		for (int i = 0; i < 3; i++) {
			fileId = isAnonymous ? FileIdHelper
					.generateAnonyFileId(fileStoreId) : FileIdHelper
					.generateSecureFileId();

			if (!fileMetaDataService.hasDumplicatedFileId(fileId)) {
				return fileId;
			}
		}

		// 做最后一次挣扎
		return (isAnonymous ? FileIdHelper.generateAnonyFileId(fileStoreId)
				: FileIdHelper.generateSecureFileId());
	}

	/**
	 * 从请求中解析出UploadRequest对象
	 * 
	 * @param request
	 * @return
	 * @throws FsException
	 */
	private List<UploadRequest> parseHttpUploadRequest(
			HttpServletRequest request) throws FsException {
		// 如果不是规范的文件上传multi-part form
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new FsException(
					"Illegal uploading request, the request form is not a multi-part one!");
		}

		ServletFileUpload upload = this.getServletFileUpload();
		List<UploadRequest> uploadRequestParams = null;

		try {
			uploadRequestParams = this.parse2FsUploadRequest(upload
					.parseRequest(request));
		} catch (FileUploadException e) {
			throw new FsServerHandleException(
					"Fail to upload the file, caused by FS inner error! detail:"
							+ e.getMessage(), e);
		}

		if ((null == uploadRequestParams) || (0 >= uploadRequestParams.size())) {
			throw new FsException("No file is uploaded !");
		}

		return uploadRequestParams;
	}

	/**
	 * 校验请求参数
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private boolean checkRequestParam(List<UploadRequest> uploadRequestParams)
			throws FsException {
		int uploadFileCounts = uploadRequestParams.size();

		UploadRequest reqParam = (0 < uploadFileCounts) ? uploadRequestParams
				.get(0) : null;

		if (null == reqParam) {
			throw new FsParameterException(
					"No file uploaded, caused by FS inner error!");
		}

		for (UploadRequest req : uploadRequestParams) {
			
			if (0 >= req.getFileSizeBytes()
					|| (null == req.getFileContentBytes())
					|| (0 >= req.getFileContentBytes().length)) {
				throw new FsFileSizeOverLimitException("You can't upload empty file!");
			}

			String fileSuffix = req.getFileSuffix();

			if (StringUtils.isEmpty(fileSuffix)) {
				continue;
			}

			if (fileSuffix.matches(".+(\\.){1,}.*")) {
				throw new FsParameterException("The file suffix '" + fileSuffix
						+ "' is invalid!");
			}
		}

		// 文件id
		String fileId = reqParam.getFileId();

		// 覆盖上传, 不能同时上传多个文件
		if (!StringUtils.isEmpty(fileId)) {
			if (2 <= uploadFileCounts) {
				throw new FsParameterException(
						"The override uploading can only upload one file at a time!");
			}
		}

		// 访问权限
		String filePermission = reqParam.getFilePermission();

		if (!StringUtils.isEmpty(filePermission)) {
			if (!FilePermission.check(filePermission)) {
				throw new FsParameterException("The parameter '"
						+ SetPermissionRequestKey.FILE_PERMISSION
						+ "' input error !");
			}
		}

		// 匿名上传不校验token
		if(!reqParam.isByAnonymousMode()) {
			// 检查token
			super.checkSecureToken(reqParam.getUserId(), reqParam.getToken(),
					reqParam.getChannel());
		}

		return true;
	}

	/**
	 * 校验权限
	 * 
	 * @param simpleFileMetaInfo
	 * @param optUserId
	 * @return
	 * @throws FsException
	 */
	private boolean checkPermissionForOverriding(
			SimpleFileMetaInfo simpleFileMetaInfo, String optUserId)
			throws FsException {
		// 文件不存在
		if (null == simpleFileMetaInfo) {
			throw new FsFileNotExistException("The file does not exist !");
		}

		// 文件权限
		FilePermission permission = FilePermission
				.getNameByValue(simpleFileMetaInfo.getFilePermission());

		// 是否匿名上传
		boolean isAnonyUploaded = simpleFileMetaInfo.isAnonymous();

		// 操作用户类型
		OptUserType optUserType = getOptUserType(optUserId,
				simpleFileMetaInfo.getOwnerUserId());

		// 使用权限矩阵校验权限
		if (!checkPermissionWithMatrix(permission, isAnonyUploaded,
				optUserType, FunctionIndex.FILE_OVERRIDE_UPLOAD)) {
			// 匿名方式上传的文件不能删除文件
			if (isAnonyUploaded) {
				throw new FsPermissionException(
						"The file is uploaded by anonymous mode, which can't be overridden !");
			}

			if (optUserType == OptUserType.VISITOR) {
				throw new FsPermissionException(
						"You can't do this operation by anonymous mode !");
			}

			if (permission == FilePermission.PRIVATE_READ_WRITE) {
				throw new FsPermissionException(
						"For the private permission file, only the file owner can override it !");
			}

			throw new FsPermissionException(
					"You don't have permission to do this operation, because which permission is '"
							+ permission + "' !");
		}

		return true;
	}

	/**
	 * 记录操作日志
	 * 
	 * @param hookParameter
	 * @param respEnv
	 * @param request
	 */
	private void recordUploadOptLog(final List<UploadRequest> hookParameter,
			final HttpRespEnvelope respEnv, final HttpServletRequest request) {

		// 为了保证性能, 只有下载失败才记录日志(且文件id合法、非匿名上传)
		if (isHandleSuccess(respEnv)) {
			return;
		}
		
		// 异步记录日志
		recordLogTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String fileId = null;

					// 大于1一个即为批量上传
					boolean isBatch = (1 < hookParameter.size());

					List<String> fileIds = this.parseFileIds(isBatch);

					int index = 0;

					for (UploadRequest reqParam : hookParameter) {
						if (index <= fileIds.size() - 1) {
							fileId = fileIds.get(index);
						}

						this.insertUploadOptLog(reqParam, fileId);
					}
				} catch (Exception e) {
				}
			}

			private void insertUploadOptLog(UploadRequest reqParam,
					String fileId) {
				if (null == reqParam) {
					return;
				}

				try {
					// 避免日志太长
					reqParam.clearFileContentBytes();

					String optUserId = reqParam.getUserId();
					String secureToken = reqParam.getToken();
					String channel = reqParam.getChannel();

					TFsFileOperationLog log = new TFsFileOperationLog();
					log.setFunctionId(UploadRequest.class.getSimpleName());
					log.setFunctionParam(JSONObject.toJSONString(reqParam));
					log.setOptIpAddr(request.getRemoteAddr());
					log.setOptUserId(optUserId);
					log.setSecureToken(secureToken);
					log.setChannel(channel);
					log.setFileId(fileId);

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

			private List<String> parseFileIds(boolean isBatch) {
				List<String> fileIds = new ArrayList<String>(2);

				if (isBatch) {
					UploadBatchResponse resp = (UploadBatchResponse) respEnv
							.getData();

					if (null != resp) {
						fileIds.addAll(resp.getFileIds());
					}
				} else {
					UploadResponse resp = (UploadResponse) respEnv.getData();

					if (null != resp) {
						fileIds.add(resp.getFileId());
					}
				}

				return fileIds;
			}
		});
	}

	/**
	 * 转换为UploadRequest
	 * 
	 * @param fileItems
	 * @return
	 */
	private List<UploadRequest> parse2FsUploadRequest(List<FileItem> fileItems) {
		if ((null == fileItems) || (0 >= fileItems.size())) {
			return null;
		}

		List<FileItem> formFields = new ArrayList<FileItem>(2);
		List<FileItem> fileFields = new ArrayList<FileItem>(2);

		for (FileItem item : fileItems) {
			if (item.isFormField()) {
				formFields.add(item);
			} else {
				fileFields.add(item);
			}
		}

		List<UploadRequest> requestList = new ArrayList<UploadRequest>(2);

		for (FileItem fileItem : fileFields) {
			requestList.add(new UploadRequest(formFields, fileItem));
		}

		return requestList;
	}

	/**
	 * 为该请求创建一个DiskFileItemFactory对象, 通过它来解析请求.
	 * 
	 * 执行解析后，所有的表单项目都保存在一个List中
	 * 
	 * @return
	 */
	private ServletFileUpload getServletFileUpload() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(FsServerConstant.UPLOAD_CACHE_SIZE_THRESHOLD); // 设置缓冲区大小，这里是4kb
		factory.setRepository(tempUploadCaceDirectory);// 设置缓冲区目录

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(FsConstant.ENCODING_UTF8);// 解决文件乱码问题
		upload.setSizeMax(-1);// 设置最大文件尺寸, -1表示不限大小

		return upload;
	}

	/**
	 * parseFileUploadUUID
	 * 
	 * @param param
	 * @param optUserId
	 * @param secureToken
	 * @return
	 */
	private String parseFileUploadUUID(UploadRequest param, String optUserId,
			String secureToken) {
		String fileUploadUUID = param.getFileUploadUUID();

		if (!StringUtils.isEmpty(fileUploadUUID)) {
			return optUserId + fileUploadUUID;
		}

		String fileId = param.getFileId();

		if (!StringUtils.isEmpty(fileId)) {
			return StringUtils.catString(fileId, optUserId, secureToken);
		}

		return StringUtils.catString(param.getFileName(), optUserId,
				secureToken);
	}
}
