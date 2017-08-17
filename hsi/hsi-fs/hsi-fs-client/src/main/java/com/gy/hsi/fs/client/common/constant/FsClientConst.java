/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.common.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.common.constant
 * 
 *  File Name       : FsClientConst.java
 * 
 *  Creation Date   : 2015-5-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : FS Client常量类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsClientConst {
	
	/** 文件系统客户端对应的服务端地址 **/
	public static final String KEY_SERVER_URL = "hsi.fs.client.server.url";

	/** 文件系统客户端请求服务端的最大超时时间[单位：毫秒] **/
	public static final String KEY_REQUEST_TIMEOUT = "hsi.fs.client.request.timeout";
	
	/** 子系统间内部共享文件操作授权"用户ID" **/
	public static final String KEY_INNERSHARE_USERID = "hsi.fs.client.innershare.userid";
	
	/** 子系统间内部共享文件操作授权"密码" **/
	public static final String KEY_INNERSHARE_PASSWORD = "hsi.fs.client.innershare.password";
	
}
