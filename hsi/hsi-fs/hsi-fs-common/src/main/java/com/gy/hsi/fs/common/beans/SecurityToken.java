/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.beans;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.beans
 * 
 *  File Name       : SecurityToken.java
 * 
 *  Creation Date   : 2015年7月10日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 安全令牌
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SecurityToken {
	private String userId;
	private String token;
	private String channel;

	public SecurityToken(String userId, String token, String channel) {
		this.userId = userId;
		this.token = token;
		this.channel = channel;
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public String getChannel() {
		return channel;
	}

	public static SecurityToken build(String userId, String token,
			String channel) {
		return new SecurityToken(userId, token, channel);
	}
}
