/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.ErrorMsg;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IErrorMsgService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 错误信息接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IErrorMsgService {

	/**
	 * 查询错误信息
	 * 
	 * @param errorMsg
	 * @return
	 */
	public ErrorMsg queryErrorMsgWithPK(String languageCode,int errorCode);

	/**
	 * 插入或更新错误信息
	 * @param list
	 * @param version
	 * @return
	 */
	public int addOrUpdateErrorMsg(List<ErrorMsg> list,Long version);

}
