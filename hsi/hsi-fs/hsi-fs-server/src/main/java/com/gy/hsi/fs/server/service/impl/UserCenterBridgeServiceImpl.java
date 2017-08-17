/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.fs.common.constant.FsApiConstant.Value;
import com.gy.hsi.fs.common.exception.FsServerHandleException;
import com.gy.hsi.fs.server.common.constant.FsConfigContant;
import com.gy.hsi.fs.server.service.IUserCenterBridgeService;
import com.gy.hsxt.uc.fs.api.common.IUCFsAuthService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service.impl
 * 
 *  File Name       : UserCenterBridgeServiceImpl.java
 * 
 *  Creation Date   : 2015年6月3日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 安全令牌service接口实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "userCenterBridgeService")
public class UserCenterBridgeServiceImpl implements IUserCenterBridgeService {
	/** 记录日志对象 **/
	private final Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private IUCFsAuthService ucFsAuthService;

	@Override
	public boolean verifySecureToken(String optUserId, String token,
			String channel) throws FsServerHandleException {

		// 调用用户中心标志
		String ucCallFlag = HsPropertiesConfigurer
				.getProperty(FsConfigContant.KEY_FS_SERVER_UC_CALL);

		// 调用用户中心, 进行登录校验
		if (!String.valueOf(Value.VALUE_0).equals(ucCallFlag)) {
			try {
				return ucFsAuthService.isLogin(optUserId, channel, token);
			} catch (Exception e) {
				String errMsg = "File permissions authentication failed!";
				logger.error(errMsg, e);

				throw new FsServerHandleException(errMsg, e);
			}
		}

		return true;
	}

	@Override
	public boolean isManageRelationship(String userId, String superiorUserId) {
		// TODO 调用用户中心提供的dubbo接口, 进行安全令牌验证
		// TODO 与用户中心对接

		return false;
	}

	@Override
	public boolean isSystemSuperAdmin(String userId) {
		// TODO 调用用户中心提供的dubbo接口, 进行安全令牌验证
		// TODO 与用户中心对接

		return false;
	}
}
