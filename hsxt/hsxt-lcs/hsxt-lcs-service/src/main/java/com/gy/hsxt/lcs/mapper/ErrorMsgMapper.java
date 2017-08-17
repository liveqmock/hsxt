/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.lcs.bean.ErrorMsg;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
 * 
 *  File Name       : ErrorMsgMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 错误信息mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface ErrorMsgMapper {
	
	public ErrorMsg queryErrorMsgWithPK(@Param("languageCode")String languageCode,@Param("errorCode")int errorCode);

	public String existErrorMsg(@Param("languageCode")String languageCode,@Param("errorCode")int errorCode);

	public void batchUpdate(List<ErrorMsg> errorMsgListUpdate);

	public void batchInsert(List<ErrorMsg> errorMsgListAdd);
}
