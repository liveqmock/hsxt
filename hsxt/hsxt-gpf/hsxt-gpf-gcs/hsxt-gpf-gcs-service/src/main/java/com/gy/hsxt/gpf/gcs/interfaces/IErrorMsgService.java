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

import com.gy.hsxt.gpf.gcs.bean.ErrorMsg;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : ErrorMsgService.java
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
	 * 添加错误信息
	 * 
	 * @param errorMsg
	 * @return
	 */
	@Transactional
	public int addErrorMsg(ErrorMsg errorMsg);

	/**
	 * 删除错误信息，delFlag = 1
	 * 
	 * @param errorMsg
	 * @return
	 */
	@Transactional
	public boolean deleteErrorMsg(String languageCode,int errorCode);

	/**
	 * 修改错误信息
	 * 
	 * @param errorMsg
	 * @return
	 */
	@Transactional
	public boolean updateErrorMsg(ErrorMsg errorMsg);

	/**
	 * 查询错误信息
	 * 
	 * @param errorMsg
	 * @return
	 */
	public ErrorMsg queryErrorMsgWithPK(String languageCode,int errorCode);

	/**
	 * 查询错误信息
	 * 
	 * @param errorMsg
	 * @return
	 */
	public List<ErrorMsg> queryErrorMsg(ErrorMsg errorMsg);

	/**
	 * 判断是否存一样的错误信息
	 * 
	 * @param errorMsg
	 * @return
	 */
	public boolean existErrorMsg(String languageCode,int errorCode);

	/**
	 * 查询出版本号大于子平台的所有错误信息
	 * @param version
	 * @return
	 */
	public List<ErrorMsg> queryErrorMsg4SyncData(Long version);
}
