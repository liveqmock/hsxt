package com.gy.hsi.fs.server.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.beans.param.ViewFileInfoRequest;
import com.gy.hsi.fs.common.beans.param.ViewFileInfoResponse;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileStatus;
import com.gy.hsi.fs.common.constant.HttpRequestParam.ViewFileInfoRequestKey;
import com.gy.hsi.fs.common.constant.HttpUrlDirectory.FsUrlKeywords;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.exception.FsParameterException;
import com.gy.hsi.fs.common.exception.FsPermissionException;
import com.gy.hsi.fs.common.exception.FsSecureTokenException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.FunctionIndex;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.OptUserType;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaData;
import com.gy.hsi.fs.server.controllers.parent.BasicController;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.controler
 * 
 *  File Name       : ViewFileInfoController.java
 * 
 *  Creation Date   : 2015-05-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收并处理“查看文件信息”的请求
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping(FsUrlKeywords.FS)
public class ViewFileInfoController extends BasicController {

	/**
	 * 查看文件信息 [包括：文件元数据、权限等] [绝不允许对外网开放！！！！！！]
	 * 
	 * 只有文件属主才可以查看文件信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(FsUrlKeywords.VIEW_FILE_INFO + "/**")
	public void handleViewFileInfoRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpRespEnvelope respEnv = null;

		// 解析请求参数
		ViewFileInfoRequest reqParam = new ViewFileInfoRequest(request);

		try {
			// 进行获取文件信息处理
			respEnv = this.doHandleViewFileInfoRequest(reqParam);
		} catch (Exception ex) {
			logger.debug("获取文件元信息失败：", ex);

			respEnv = new HttpRespEnvelope(ex, reqParam);
		}

		boolean success = this.isHandleSuccess(respEnv);

		super.doHttpResponse(response, "text/plain",
				respEnv.toJsonString(!success));

		// 记录操作日志
		this.recordOptLog(reqParam, respEnv, request);
	}

	/**
	 * 处理查看文件信息请求
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private HttpRespEnvelope doHandleViewFileInfoRequest(
			ViewFileInfoRequest reqParam) throws FsException {
		// --------------------------校验请求参数---------------------------
		this.checkRequestParam(reqParam);

		// 文件id
		String fileId = reqParam.getFileId();

		// 当前操作用户id
		String optUserId = reqParam.getUserId();

		TFsFileMetaData fileMetaInfo = fileMetaDataService
				.queryFileMetaData(fileId);

		// ---------------------------校验权限-----------------------------
		this.checkPermission(fileMetaInfo, reqParam);

		// 文件权限
		String filePermissionInDB = fileMetaInfo.getFilePermission();

		FilePermission permission = FilePermission
				.getNameByValue(filePermissionInDB);

		// 操作者是否为文件属主本人
		boolean isOwnerUserSelf = (null == optUserId) ? false : (optUserId
				.equals(fileMetaInfo.getOwnerUserId()));

		FileStatus fileStatus = FileStatus.getNameByValue(fileMetaInfo
				.getFileStatus());

		ViewFileInfoResponse respData = new ViewFileInfoResponse();
		{
			respData.setfileId(fileId);
			respData.setFileName(fileMetaInfo.getFileName());
			respData.setFileSuffix(fileMetaInfo.getFileSuffix());
			respData.setFilePermission(permission);
			respData.setFileSizeBytes(fileMetaInfo.getFileSizeBytes());
			respData.setIsAnonyUpload(fileMetaInfo.getIsAnonymous());
			respData.setUploadDate(fileMetaInfo.getCreatedDate());
			respData.setIsUploadedByMe(isOwnerUserSelf);
			respData.setFileStatus(fileStatus);
		}

		HttpRespEnvelope respEvn = new HttpRespEnvelope();
		respEvn.setData(respData);

		return respEvn;
	}

	/**
	 * 校验请求参数
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private boolean checkRequestParam(ViewFileInfoRequest reqParam)
			throws FsException {
		// 文件id
		String fileId = reqParam.getFileId();

		if (StringUtils.isEmpty(fileId)) {
			throw new FsParameterException("The parameter '"
					+ ViewFileInfoRequestKey.FILE_ID + "' can't be null !");
		}

		String userId = reqParam.getUserId();
		String token = reqParam.getToken();
		String channel = reqParam.getChannel();

		// 检查token, 如果userId为空则是游客
		this.checkSecureToken(userId, token, channel);

		return true;
	}

	@Override
	protected boolean checkSecureToken(String userId, String token,
			String channel) throws FsException {
		// 匿名上传的文件, 可以匿名查看文件信息, 即：userId为空也可以查看文件信息
		if (StringUtils.isEmpty(userId)) {
			return true;
		}

		// 用户id不为空, 但是token为空, 则不合法请求
		if (StringUtils.isEmpty(token)) {
			throw new FsSecureTokenException(
					"For this operation, you need to provide the secure token!");
		}

		if (StringUtils.isEmpty(channel)) {
			throw new FsSecureTokenException(
					"For this operation, you need to provide the request channel!");
		}

		return super.checkSecureToken(userId, token, channel);
	}

	/**
	 * 校验权限
	 * 
	 * @param simpleFileMetaInfo
	 * @param optUserId
	 * @return
	 * @throws FsException
	 */
	/**
	 * 校验权限
	 * 
	 * @param fileMetaInfo
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private boolean checkPermission(TFsFileMetaData fileMetaInfo,
			ViewFileInfoRequest reqParam) throws FsException {
		// 文件不存在
		if (null == fileMetaInfo) {
			throw new FsFileNotExistException("The file does not exist !");
		}

		// 文件权限
		FilePermission permission = FilePermission.getNameByValue(fileMetaInfo
				.getFilePermission());

		// 是否匿名上传
		boolean isAnonyUploaded = fileMetaInfo.getIsAnonymous();

		// 操作用户类型
		OptUserType optUserType = getOptUserType(reqParam.getUserId(),
				fileMetaInfo.getOwnerUserId());

		// 使用权限矩阵校验权限
		if (!checkPermissionWithMatrix(permission, isAnonyUploaded,
				optUserType, FunctionIndex.FILE_INFO_VIEW)) {
			if (permission == FilePermission.PRIVATE_READ_WRITE) {
				throw new FsPermissionException(
						"For the private permission file, only the file owner can view its information !");
			}

			// 非公共文件, 不能以游客方式查看文件信息
			if ((permission != FilePermission.PUBLIC_READ)
					&& (optUserType == OptUserType.VISITOR)) {
				throw new FsPermissionException(
						"For the non public permission file, you can't do this operation by anonymous mode !");
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
	 * @param reqParam
	 * @param respEnv
	 * @param request
	 */
	private void recordOptLog(ViewFileInfoRequest reqParam,
			HttpRespEnvelope respEnv, HttpServletRequest request) {
		SecurityToken securityToken = new SecurityToken(reqParam.getUserId(),
				reqParam.getToken(), reqParam.getChannel());

		super.recordOptLog(reqParam.getFileId(), securityToken, reqParam,
				respEnv, request);
	}
}
