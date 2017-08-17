/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service;

import com.gy.hsi.fs.common.exception.FsServerHandleException;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service
 * 
 *  File Name       : ISecureTokenService.java
 * 
 *  Creation Date   : 2015-05-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 安全令牌操作service接口类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IUserCenterBridgeService {

	/**
	 * [与用户中心对接] 验证安全令牌
	 * 
	 * @param optUserId
	 * @param token
	 * @param channel
	 * @return
	 * @throws FsServerHandleException 
	 */
	public boolean verifySecureToken(String optUserId, String token,
			String channel) throws FsServerHandleException;

	/**
	 * [与用户中心对接] 验证两个用户是否有管理关系
	 * 
	 * @param userId
	 * @param superiorUserId
	 * @return
	 */
	public boolean isManageRelationship(String userId, String superiorUserId);

	/**
	 * 是否为系统超级管理员
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isSystemSuperAdmin(String userId);
}
