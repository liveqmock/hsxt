/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.controllers.parent;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.fs.common.beans.HttpRespEnvelope;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.constant.FsApiConstant.FileOptResultCode;
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.constant.FsApiConstant.Value;
import com.gy.hsi.fs.common.constant.FsConstant;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.fs.common.exception.FsPermissionException;
import com.gy.hsi.fs.common.exception.FsSecureTokenException;
import com.gy.hsi.fs.common.exception.FsServerHandleException;
import com.gy.hsi.fs.common.utils.StringUtils;
import com.gy.hsi.fs.server.common.FsServerInitializer;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.OptUserType;
import com.gy.hsi.fs.server.common.constant.FsServerConstant.UploadMode;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLog;
import com.gy.hsi.fs.server.common.utils.ExceptionUtils;
import com.gy.hsi.fs.server.common.utils.FileIdHelper;
import com.gy.hsi.fs.server.service.ICommonParamsService;
import com.gy.hsi.fs.server.service.IFileMetaDataDelService;
import com.gy.hsi.fs.server.service.IFileMetaDataService;
import com.gy.hsi.fs.server.service.IOperationLogService;
import com.gy.hsi.fs.server.service.IPermissionRuleService;
import com.gy.hsi.fs.server.service.ITaobaoTfsClientService;
import com.gy.hsi.fs.server.service.IUserCenterBridgeService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.controler.parent
 * 
 *  File Name       : BasicController.java
 * 
 *  Creation Date   : 2015-05-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 抽象Controller类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class BasicController {
	/** 记录日志对象 **/
	protected final Logger logger = Logger.getLogger(this.getClass());

	@Resource
	protected IFileMetaDataService fileMetaDataService;

	@Resource
	protected IFileMetaDataDelService fileMetaDataDelService;

	@Resource
	protected IUserCenterBridgeService userCenterBridgeService;

	@Resource
	protected IPermissionRuleService permissionRuleService;

	@Resource
	protected ITaobaoTfsClientService taobaoTfsClientService;

	@Resource
	protected ICommonParamsService commonParamsService;

	@Resource
	protected IOperationLogService optLogService;

	@Resource
	protected FsServerInitializer fsServerInitializer;

	@Resource(name = "recordLogTaskExecutor")
	protected ThreadPoolTaskExecutor recordLogTaskExecutor;

	/**
	 * 构造函数
	 */
	public BasicController() {
	}

	/**
	 * do reponse action
	 * 
	 * @param response
	 * @param contentType
	 * @param content
	 * @throws IOException
	 */
	protected void doHttpResponse(HttpServletResponse response,
			String contentType, String content) {
		// 设置编码
		response.setCharacterEncoding(FsConstant.ENCODING_UTF8);

		// 流能够自动识别文件的类型
		response.setContentType(contentType);

		ServletOutputStream out = null;

		try {
			out = response.getOutputStream();

			if (null != content) {
				out.write(content.getBytes());
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			if (null != out) {
				try {
					out.flush();
				} catch (IOException e) {
				}
				
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// /**
	// * 暂时屏蔽, 不能既调用 response.getOutputStream()，又调用response.getWriter()
	// *
	// * @param response
	// * @param contentType
	// * @param content
	// */
	// protected void doHttpResponse(HttpServletResponse response,
	// String contentType, String content) {
	// // 设置编码
	// response.setCharacterEncoding(FsConstant.ENCODING_UTF8);
	//
	// // 流能够自动识别文件的类型
	// response.setContentType(contentType);
	//
	// PrintWriter pw = null;
	// ServletOutputStream out = null;
	//
	// try {
	// pw = response.getWriter();
	// pw.write(content);
	// } catch (Exception ex) {
	// logger.error("", ex);
	// } finally {
	// if (null != pw) {
	// pw.flush();
	// pw.close();
	// }
	// }
	// }

	/**
	 * 统一抛出FS处理异常
	 * 
	 * @throws FsServerHandleException
	 */
	protected void throwHandleException() throws FsServerHandleException {
		throw new FsServerHandleException(
				"The FS Server handle failed, please try again !");
	}

	/**
	 * 判断是否处理成功
	 * 
	 * @param respEnv
	 * @return
	 */
	protected boolean isHandleSuccess(HttpRespEnvelope respEnv) {
		// 判断是否处理成功
		boolean isSuccess = (null == respEnv) ? false
				: (FileOptResultCode.OPT_SUCCESS.getValue() == respEnv
						.getResultCode());

		return isSuccess;
	}

	/**
	 * 判断是否为迁移数据
	 * 
	 * @param ownerUserId
	 * @return
	 */
	protected boolean isTransferData(String ownerUserId) {
		return FsConstant.DEFAULT_TRANSFER_USER.equals(ownerUserId);
	}

	/**
	 * 使用权限矩阵校验权限
	 * 
	 * @param filePermission
	 * @param isAnonyUploaded
	 * @param optUserType
	 * @param ruleMatrixY
	 * @return
	 * @throws FsException
	 */
	protected boolean checkPermissionWithMatrix(FilePermission filePermission,
			boolean isAnonyUploaded, OptUserType optUserType, int ruleMatrixY)
			throws FsException {

		UploadMode uploadMode = isAnonyUploaded ? UploadMode.ANONY
				: UploadMode.REAL_NAME;

		String key = new StringBuilder().append(filePermission.getValue())
				.append(uploadMode.getValue()).append(optUserType.getValue())
				.toString();

		String value = fsServerInitializer.getPerssionRuleData(key);

		if (!StringUtils.isEmpty(value) && (ruleMatrixY < value.length())) {
			if (Value.VALUE_1 == value.charAt(ruleMatrixY)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 获取当前操作用户类型
	 * 
	 * @param currOptUserId
	 * @param ownerUserId
	 * @return
	 */
	protected OptUserType getOptUserType(String currOptUserId,
			String ownerUserId) {
		if (StringUtils.isEmpty(currOptUserId)) {
			return OptUserType.VISITOR;
		}

		if (currOptUserId.equals(ownerUserId) || isTransferData(ownerUserId)) {
			return OptUserType.OWNER;
		}

		return OptUserType.OTHER_LOGINER;
	}

	/**
	 * 验证安全令牌
	 * 
	 * @param optUserId
	 *            当前操作用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @return
	 * @throws FsException
	 */
	protected boolean checkSecureToken(String optUserId, String token,
			String channel) throws FsException {

		if (StringUtils.isEmpty(optUserId) || StringUtils.isEmpty(token)) {
			throw new FsSecureTokenException(
					"For this operation, you need to provide the user id and secure token!");
		}

		// 调用用户中心提供的dubbo接口, 进行安全令牌验证
		// 用户中心channel定义:
		// 1-网页接入;2-POS接入;3-刷卡器 接入方;4-移动APP接入;5-互生平板 接入;6-互生平台接入;7-IVR接入;8-第三方接入
		boolean success = userCenterBridgeService.verifySecureToken(optUserId,
				token, channel);

		if (!success) {
			throw new FsSecureTokenException(
					"The secure token for the user is illegal or expired !");
		}

		return true;
	}

	/**
	 * 验证上级用户安全令牌[这个是优化方案, 等有了实际需求再加进去]
	 * 
	 * @param optMgrUserId
	 *            当前操作的管理用户id
	 * @param token
	 *            安全令牌
	 * @param channel
	 *            渠道
	 * @param ownerUserId
	 *            文件所有者用户id
	 * @return
	 * @throws FsException
	 */
	@Deprecated
	protected boolean checkMgrSecureToken(String optMgrUserId, String token,
			String channel, String ownerUserId) throws FsException {

		if (StringUtils.isEmpty(optMgrUserId) || StringUtils.isEmpty(token)) {
			throw new FsSecureTokenException(
					"For this operation, you need to provide the user id and secure token!");
		}

		if (StringUtils.isEmpty(ownerUserId)) {
			throw new FsSecureTokenException(
					"For this operation, you need to provide the owner user id!");
		}

		// 调用用户中心提供的dubbo接口, 进行安全令牌验证
		if (!userCenterBridgeService.verifySecureToken(optMgrUserId, token,
				channel)) {
			throw new FsSecureTokenException(
					"Failed to check the user id and secure token, please provide valid user id and secure token !");
		}

		if (!userCenterBridgeService.isManageRelationship(optMgrUserId,
				ownerUserId)) {
			throw new FsPermissionException(
					"You don't have permission to do this operation  !");
		}

		return true;
	}

	/**
	 * 记录操作日志
	 * 
	 * @param fileId
	 * @param secureToken
	 * @param reqParam
	 * @param respEnv
	 * @param request
	 */
	protected void recordOptLog(final String fileId,
			final SecurityToken secureToken, final Object reqParam,
			final HttpRespEnvelope respEnv, final HttpServletRequest request) {
		
		// 为了保证性能, 只有失败才记录日志(且文件id合法、非匿名上传)
		if (isHandleSuccess(respEnv)) {
			return;
		}
				
		if (!StringUtils.isEmpty(fileId)) {
			if (!FileIdHelper.checkFileId(fileId)) {
				logger.info("文件id非法, 操作日志不入库, fileId=" + fileId);

				return;
			}
		}

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					String optUserId = secureToken.getUserId();
					String token = secureToken.getToken();
					String channel = secureToken.getChannel();

					TFsFileOperationLog log = new TFsFileOperationLog();
					log.setFileId(fileId);
					log.setFunctionId(reqParam.getClass().getSimpleName());
					log.setFunctionParam(JSONObject.toJSONString(reqParam));
					log.setOptIpAddr(request.getRemoteAddr());
					log.setOptUserId(optUserId);
					log.setSecureToken(token);
					log.setChannel(channel);
					log.setOptStatus(respEnv.getResultCode());
					log.setOptErrDesc(respEnv.getResultDesc());
					log.setOptErrDetail(ExceptionUtils
							.getStackTraceInfo(respEnv.fetchException()));
					log.setCreatedDate(new Date());

					optLogService.insertOperationLog(log);
				} catch (Exception e) {
					logger.info("记录日志发生异常：", e);
				}
			}
		};

		// 异步记录日志
		recordLogTaskExecutor.execute(runnable);
	}
}
