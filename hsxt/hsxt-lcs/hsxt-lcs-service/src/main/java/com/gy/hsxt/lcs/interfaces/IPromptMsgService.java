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

import com.gy.hsxt.lcs.bean.PromptMsg;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IPromptMsgService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 提示信息接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IPromptMsgService {

	/**
	 * 查询提示信息
	 * 
	 * @param PromptMsg
	 * @return
	 */
	public PromptMsg queryPromptMsgWithPK(String languageCode,String promptCode);

	/**
	 * 插入或更新提示信息
	 * @param list
	 * @param version
	 * @return
	 */
	public int addOrUpdatePromptMsg(List<PromptMsg> list,Long version);

}
