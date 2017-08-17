/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.constant
 * 
 *  File Name       : FsConfigContant.java
 * 
 *  Creation Date   : 2015年5月26日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统配置文件常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsConfigContant {

	/** 文件系统路由代码,用于文件自动分流 **/
	public static final String KEY_FS_SERVER_ROUTER_CODE = "fs.server.router.code";

	/** 临时调测开关, 文件系统是否调用uc进行token校验标志(0:暂时不调用uc; 1:调用uc,生产环境必须调用uc;) **/
	public static final String KEY_FS_SERVER_UC_CALL = "fs.server.hsxt.uc.call";

}
