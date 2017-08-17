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

import com.gy.hsxt.gpf.gcs.bean.PromptMsg;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : PromptMsgMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 提示信息mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("PromptMsgMapper")
public interface PromptMsgMapper {
	
	public int addPromptMsg(PromptMsg promptMsg);

	public boolean deletePromptMsg(PromptMsg promptMsg);

	public boolean updatePromptMsg(PromptMsg promptMsg);

	public PromptMsg queryPromptMsgWithPK(@Param("languageCode")String languageCode,@Param("promptCode")String promptCode);

	public String existPromptMsg(@Param("languageCode")String languageCode,@Param("promptCode")String promptCode);

	public List<PromptMsg> queryPromptMsgListPage(PromptMsg promptMsg);
	
	public List<PromptMsg> queryPromptMsg4SyncData(@Param("version")long version);

}