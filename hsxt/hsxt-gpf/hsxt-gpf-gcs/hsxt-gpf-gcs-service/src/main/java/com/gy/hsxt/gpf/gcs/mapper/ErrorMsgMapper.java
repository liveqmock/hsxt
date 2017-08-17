/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.ErrorMsg;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
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
@Repository("ErrorMsgMapper")
public interface ErrorMsgMapper {
	
	public int addErrorMsg(ErrorMsg errorMsg);

	public boolean deleteErrorMsg(ErrorMsg errorMsg);

	public boolean updateErrorMsg(ErrorMsg errorMsg);

	public ErrorMsg queryErrorMsgWithPK(@Param("languageCode")String languageCode,@Param("errorCode")int errorCode);

	public String existErrorMsg(@Param("languageCode")String languageCode,@Param("errorCode")int errorCode);

	public List<ErrorMsg> queryErrorMsgListPage(ErrorMsg errorMsg);
	
	public List<ErrorMsg> queryErrorMsg4SyncData(@Param("version")long version);
}
