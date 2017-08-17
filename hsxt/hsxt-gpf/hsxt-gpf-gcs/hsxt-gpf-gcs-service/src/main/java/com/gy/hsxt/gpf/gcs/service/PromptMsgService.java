/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.PromptMsg;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IPromptMsgService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.PromptMsgMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : PromptMsgService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 提示信息接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("promptMsgService")
public class PromptMsgService implements IPromptMsgService {
	
	@Autowired
	private PromptMsgMapper promptMsgMapper;
	
	@Autowired
	private IVersionService veresionService;

	@Override
	@Transactional
	public int addPromptMsg(PromptMsg promptMsg) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_PROMPT_MSG);
		promptMsg.setVersion(version);
		return promptMsgMapper.addPromptMsg(promptMsg);
	}

	@Override
	@Transactional
	public boolean deletePromptMsg(String languageCode,String promptCode) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_PROMPT_MSG);
		PromptMsg promptMsg = new PromptMsg(languageCode,promptCode);
		promptMsg.setVersion(version);
		return promptMsgMapper.deletePromptMsg(promptMsg);
	}

	@Override
	@Transactional
	public boolean updatePromptMsg(PromptMsg promptMsg) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_PROMPT_MSG);
		promptMsg.setVersion(version);
		return promptMsgMapper.updatePromptMsg(promptMsg);
	}

	@Override
	public PromptMsg queryPromptMsgWithPK(String languageCode,String promptCode) {
		return promptMsgMapper.queryPromptMsgWithPK(languageCode,promptCode);
	}

	@Override
	public List<PromptMsg> queryPromptMsg(PromptMsg promptMsg) {
		String msg =promptMsg.getPromptMsg();
		if(null != msg && !"".equals(msg)){
			msg = msg.replaceAll("_", "/_").replaceAll("%", "/%");
			promptMsg.setPromptMsg(msg);
		}
		return promptMsgMapper.queryPromptMsgListPage(promptMsg);
	}

	@Override
	public boolean existPromptMsg(String languageCode,String promptCode) {
		boolean isExist = "1".equals(promptMsgMapper.existPromptMsg(languageCode,promptCode));
		return isExist;
	}

	@Override
	public List<PromptMsg> queryPromptMsg4SyncData(Long version) {
		return promptMsgMapper.queryPromptMsg4SyncData(version);
	}
}
