package com.gy.hsi.fs.server.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.beans.param.SetPermissionRequest;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.HttpRequestParam.SetPermissionRequestKey;
import com.gy.hsi.fs.common.constant.HttpUrlDirectory.FsUrlKeywords;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.common.exception.FsParameterException;
import com.gy.hsi.fs.common.exception.FsPermissionException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.FunctionIndex;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.OptUserType;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaData;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaInfo;
import com.gy.hsi.fs.server.common.utils.FileIdHelper;
import com.gy.hsi.fs.server.controllers.parent.BasicController;

/***************************************************************************
 * <PRE>
 *  Project Name    : fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.controlers
 * 
 *  File Name       : SetPermissionController.java
 * 
 *  Creation Date   : 2015-05-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 接收并处理http文件权限相关请求
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping(FsUrlKeywords.FS)
public class SetPermissionController extends BasicController {

	/**
	 * 设置文件权限信息[绝不允许对外网开放！！！！！！]
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(FsUrlKeywords.SET_PERMISSION + "/**")
	public void handleSetPermissionRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpRespEnvelope respEnv = null;

		// 解析请求参数
		SetPermissionRequest reqParam = new SetPermissionRequest(request);

		try {
			// 进行设置权限处理
			respEnv = this.doHandleSetPermissionRequest(reqParam);
		} catch (Exception ex) {
			logger.debug("设置文件权限失败：", ex);

			respEnv = new HttpRespEnvelope(ex, reqParam);
		}

		super.doHttpResponse(response, "text/plain", respEnv.toJsonString(true));

		// 记录操作日志
		this.recordOptLog(reqParam, respEnv, request);
	}

	/**
	 * 处理申请安全令牌请求
	 * 
	 * @param reqParam
	 * @return
	 * @throws FsException
	 */
	private HttpRespEnvelope doHandleSetPermissionRequest(
			SetPermissionRequest reqParam) throws FsException {
		// 校验请求参数
		this.checkRequestParam(reqParam);

		// 文件id
		String fileId = reqParam.getFileId();

		{
			SimpleFileMetaInfo simpleFileMetaInfo = fileMetaDataService
					.querySimpleFileMetaInfo(fileId);

			this.checkPermission(simpleFileMetaInfo, reqParam.getUserId());
		}

		{
			String permissionValue = FilePermission.getValue(reqParam
					.getFilePermission());

			TFsFileMetaData fileMetaData = new TFsFileMetaData();
			fileMetaData.setFileId(fileId);
			fileMetaData.setFilePermission(permissionValue);
			
			// 对于迁移的数据特殊处理!!!!!
			if(isTransferData(fileMetaData.getOwnerUserId())) {
				fileMetaData.setOwnerUserId(reqParam.getUserId());
			}

			fileMetaDataService.updateFileMetaData(fileMetaData);
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
	private boolean checkRequestParam(SetPermissionRequest reqParam)
			throws FsException {
		// 文件id
		String fileId = reqParam.getFileId();
		String filePermission = reqParam.getFilePermission();

		if (StringUtils.isEmpty(fileId)) {
			throw new FsParameterException("The parameter '"
					+ SetPermissionRequestKey.FILE_ID + "' can't be null !");
		}

		// 匿名方式上传的文件不能删除文件
		if (FileIdHelper.isAnonyFileId(fileId)) {
			throw new FsPermissionException(
					"The file is uploaded by anonymous mode, which can't be set permission !");
		}

		if (StringUtils.isEmpty(filePermission)) {
			throw new FsParameterException("The parameter '"
					+ SetPermissionRequestKey.FILE_PERMISSION
					+ "' can't be null !");
		}

		if (!FilePermission.check(filePermission)) {
			throw new FsParameterException("The parameter '"
					+ SetPermissionRequestKey.FILE_PERMISSION
					+ "' input error !");
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
	 * @param optUserId
	 * @return
	 * @throws FsException
	 */
	private boolean checkPermission(SimpleFileMetaInfo simpleFileMetaInfo,
			String optUserId) throws FsException {
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
				optUserType, FunctionIndex.FILE_DELETE)) {
			// 匿名方式上传的文件不能删除文件
			if (isAnonyUploaded) {
				throw new FsPermissionException(
						"The file is uploaded by anonymous mode, which can't be set permission !");
			}

			if (optUserType != OptUserType.OWNER) {
				throw new FsPermissionException(
						"Only owner of the file can set its permission !");
			}

			throw new FsPermissionException(
					"You don't have permission to do this operation !");
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
	private void recordOptLog(SetPermissionRequest reqParam,
			HttpRespEnvelope respEnv, HttpServletRequest request) {
		SecurityToken securityToken = new SecurityToken(reqParam.getUserId(),
				reqParam.getToken(), reqParam.getChannel());

		super.recordOptLog(reqParam.getFileId(), securityToken, reqParam,
				respEnv, request);
	}
}
