/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.common.constant
 * 
 *  File Name       : UFResultCode.java
 * 
 *  Creation Date   : 2015年10月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置异常错误码定义 [17000 ~ 17999]
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface UFResultCode {

	/** 17000: 请求参数无效 **/
	public static final int PARAM_INVALID = 17000;

	/** 17001: 非跨地区平台报文,不得通过UF转发 **/
	public static final int REQUEST_MODE_ERR = 17001;

	/** 17100: 内部处理异常 **/
	public static final int SYS_INNERNAL_ERR = 17100;

	/** 17101: 查询路由目标地址失败 **/
	public static final int DEST_ADDRESS_QRY_FAILED = 17101;

	/** 17102: 没有找到路由目标地址 **/
	public static final int DEST_ADDRESS_NOT_FOUND = 17102;

	/** 17103: 没有找到目标业务子系统 **/
	public static final int DEST_SUBSYS_NOT_FOUND = 17103;

	/** 17200: 处理成功 - **/
	public static final int SUCCESS = 17200;

	/** 17201: 处理失败  - **/
	public static final int FAILED = 17201;

	/** 17202: 内部系统与综合前置通信失败 **/
	public static final int INNERNAL_COMM_ERR = 17202;

	/** 17203: 综合前置与目标平台综合前置通信失败 **/
	public static final int EXTERNAL_COMM_ERR = 17203;

	/** 17204: 该报文无法被目标平台综合前置识别 **/
	public static final int PACKET_NOT_RECOGNIZED = 17204;

	/** 17205: 该报文不属于目标平台 **/
	public static final int PACKET_ROUTER_DEST_ERR = 17205;

	/** 17206: 目标平台处理该报文时发生错误 **/
	public static final int DEST_PLATFORM_HANDLE_ERR = 17206;

}
