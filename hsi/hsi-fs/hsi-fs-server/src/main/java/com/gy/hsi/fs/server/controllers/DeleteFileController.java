package com.gy.hsi.fs.server.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.beans.param.DeleteFileRequest;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.HttpRequestParam.DeleteFileRequestKey;
import com.gy.hsi.fs.common.constant.HttpUrlDirectory.FsUrlKeywords;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.exception.FsParameterException;
import com.gy.hsi.fs.common.exception.FsPermissionException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.FunctionIndex;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.OptUserType;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaInfo;
import com.gy.hsi.fs.server.common.utils.FileIdHelper;
import com.gy.hsi.fs.server.controllers.parent.BasicController;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.controlers
 * 
 *  File Name       : DeleteFileController.java
 * 
 *  Creation Date   : 2015-05-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收并处理http文件相关请求
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping(FsUrlKeywords.FS)
public class DeleteFileController extends BasicController {

	public DeleteFileController() {
	}

	/**
	 * 删除文件[绝不允许对外网开放！！！！！！]
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(FsUrlKeywords.DELETE_FILE + "/**")
	public void handleSetPermissionRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpRespEnvelope respEnv = null;

		// 解析请求参数
		DeleteFileRequest reqParam = new DeleteFileRequest(request);

		try {
			// 进行删除处理逻辑
			respEnv = this.doHandleDeleteFileRequest(reqParam);
		} catch (Exception ex) {
			logger.debug("删除文件失败：", ex);

			respEnv = new HttpRespEnvelope(ex, reqParam);
		}

		super.doHttpResponse(response, "text/plain", respEnv.toJsonString(true));

		// 记录操作日志
		this.recordOptLog(reqParam, respEnv, request);
	}

	/**
	 * 处理删除文件请求
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private HttpRespEnvelope doHandleDeleteFileRequest(
			DeleteFileRequest reqParam) throws FsException {
		// 校验请求参数
		this.checkRequestParam(reqParam);

		// 文件id列表
		String[] fileIds = reqParam.getFileIdList();

		SimpleFileMetaInfo simpleFileMetaInfo = null;
		List<SimpleFileMetaInfo> delFileList = new ArrayList<SimpleFileMetaInfo>();

		// 集中鉴权
		for (String fileId : fileIds) {
			simpleFileMetaInfo = fileMetaDataService
					.querySimpleFileMetaInfo(fileId);

			// 校验权限
			this.checkPermission(simpleFileMetaInfo, fileId,
					reqParam.getUserId());

			delFileList.add(simpleFileMetaInfo);
		}

		// 集中删除操作
		for (String fileId : fileIds) {
			// 更新数据库记录(软删除, 将数据从一个表转移到另一张表, 然后定时任务扫描, 异步方式从TFS中把文件实际删除)
			fileMetaDataService.deleteFileByFileId(fileId);

			// 如果是匿名直接删除, 因为匿名不走数据库
			if (simpleFileMetaInfo.isAnonymous()) {
				fileMetaDataDelService.deleteFileFromTFSByFileId(fileId);
			}
		}

		return new HttpRespEnvelope();
	}

	/**
	 * 校验请求参数
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private boolean checkRequestParam(DeleteFileRequest reqParam)
			throws FsException {
		// 文件id列表
		String[] fileIds = reqParam.getFileIdList();

		if (StringUtils.isStrArrayEmpty(fileIds)) {
			throw new FsParameterException("The parameter '"
					+ DeleteFileRequestKey.FILE_ID + "' can't be null !");
		}

		// 检查token
		super.checkSecureToken(reqParam.getUserId(), reqParam.getToken(),
				reqParam.getChannel());

		return true;
	}

	/**
	 * 校验权限
	 * 
	 * @param simpleFileMetaInfo
	 * @param fileId
	 * @param optUserId
	 * @return
	 * @throws FsException
	 */
	private boolean checkPermission(SimpleFileMetaInfo simpleFileMetaInfo,
			String fileId, String optUserId) throws FsException {
		// 文件不存在
		if (null == simpleFileMetaInfo) {
			throw new FsFileNotExistException(
					"The file does not exist ! fileId=" + fileId);
		}

		// 是否匿名上传
		boolean isAnonyUploaded = simpleFileMetaInfo.isAnonymous();

		// 对于实名上传的文件, 文件属主是上级用户是可以删除的
		if (!isAnonyUploaded) {
			if (userCenterBridgeService.isManageRelationship(optUserId,
					simpleFileMetaInfo.getOwnerUserId())) {
				return true;
			}
		}
		// 对于匿名上传的文件, 只有系统超级管理员才可以删除
		else if (userCenterBridgeService.isSystemSuperAdmin(optUserId)) {
			return true;
		}

		// 文件权限
		FilePermission permission = FilePermission
				.getNameByValue(simpleFileMetaInfo.getFilePermission());

		// 操作用户类型
		OptUserType optUserType = getOptUserType(optUserId,
				simpleFileMetaInfo.getOwnerUserId());

		// 使用权限矩阵校验权限
		if (!checkPermissionWithMatrix(permission, isAnonyUploaded,
				optUserType, FunctionIndex.FILE_DELETE)) {
			// 匿名方式上传的文件不能删除文件
			if (isAnonyUploaded) {
				throw new FsPermissionException(
						"The file is uploaded by anonymous mode, which can't be deleted ! fileId="
								+ fileId);
			}

			if (optUserType == OptUserType.VISITOR) {
				throw new FsPermissionException(
						"You can't do this operation by anonymous mode !");
			}

			if (permission == FilePermission.PRIVATE_READ_WRITE) {
				throw new FsPermissionException(
						"For the private permission file, only the file owner can delete it ! fileId="
								+ fileId);
			}

			throw new FsPermissionException(
					"You don't have permission to delete the file, because which permission is '"
							+ permission + "' ! fileId=" + fileId);
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
	private void recordOptLog(DeleteFileRequest reqParam,
			HttpRespEnvelope respEnv, HttpServletRequest request) {
		
		// 为了保证性能, 只有失败才记录日志(且文件id合法、非匿名上传)
		if (isHandleSuccess(respEnv)) {
			return;
		}
				
		String userId = reqParam.getUserId();
		String token = reqParam.getToken();
		String channel = reqParam.getChannel();

		// 文件id列表
		String[] fileIds = reqParam.getFileIdList();

		if (null == fileIds) {
			return;
		}

		SecurityToken secureToken;

		for (String fileId : fileIds) {
			if (!FileIdHelper.checkFileId(fileId)) {
				return;
			}

			secureToken = new SecurityToken(userId, token, channel);

			super.recordOptLog(fileId, secureToken, reqParam, respEnv, request);
		}
	}
}
