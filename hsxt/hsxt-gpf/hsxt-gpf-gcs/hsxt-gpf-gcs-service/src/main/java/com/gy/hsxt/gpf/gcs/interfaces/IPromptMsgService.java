/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.PromptMsg;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : PromptMsgService.java
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
	 * 添加提示信息
	 * 
	 * @param PromptMsg
	 * @return
	 */
	@Transactional
	public int addPromptMsg(PromptMsg promptMsg);

	/**
	 * 删除提示信息，delFlag = 1
	 * 
	 * @param PromptMsg
	 * @return
	 */
	@Transactional
	public boolean deletePromptMsg(String languageCode,String promptCode);

	/**
	 * 修改提示信息
	 * 
	 * @param PromptMsg
	 * @return
	 */
	@Transactional
	public boolean updatePromptMsg(PromptMsg promptMsg);

	/**
	 * 查询提示信息
	 * 
	 * @param PromptMsg
	 * @return
	 */
	public PromptMsg queryPromptMsgWithPK(String languageCode,String promptCode);

	/**
	 * 查询提示信息
	 * 
	 * @param PromptMsg
	 * @return
	 */
	public List<PromptMsg> queryPromptMsg(PromptMsg promptMsg);

	/**
	 * 判断是否存一样的提示信息
	 * 
	 * @param PromptMsg
	 * @return
	 */
	public boolean existPromptMsg(String languageCode,String promptCode);

	/**
	 * 查询出版本号大于子平台的所有提示信息
	 * @param version
	 * @return
	 */
	public List<PromptMsg> queryPromptMsg4SyncData(Long version);
}
