package com.gy.hsxt.common.constant;
		
/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 *
 * Project Name   	: hsxt-common
 *
 * Package Name     : com.gy.hsxt.common.constant
 *
 * File Name        : IRespCode
 * 
 * Author           : guiyi149
 *
 * Create Date      : 2015-9-23 下午12:30:53  
 *
 * Update User      : guiyi149
 *
 * Update Date      : 2015-9-23 下午12:30:53
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
public interface IRespCode {
	
	/**
	 * 错误代码
	 * @return code
	 */
	int getCode();

	/**
	 * 枚举项名称
	 * @return name
	 */
	String name();
	
	/**
     * 描述信息
     * @return desc
     */
	String getDesc();

}

	