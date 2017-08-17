/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.PromptMsg;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IPromptMsgService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.PromptMsgMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
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
	public PromptMsg queryPromptMsgWithPK(String languageCode,String promptCode) {
		return promptMsgMapper.queryPromptMsgWithPK(languageCode,promptCode);
	}

	@Override
	@Transactional
	public int addOrUpdatePromptMsg(List<PromptMsg> list, Long version) {
		veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_PROMPT_MSG,version,true));
		List<PromptMsg> promptMsgListAdd = new ArrayList<PromptMsg>();
		List<PromptMsg> promptMsgListUpdate = new ArrayList<PromptMsg>();
		for(PromptMsg obj : list){
			if("1".equals(promptMsgMapper.existPromptMsg(obj.getLanguageCode(), obj.getPromptCode()))){
				promptMsgListUpdate.add(obj);
			}else{
				promptMsgListAdd.add(obj);
			}
		}
		
		if(promptMsgListUpdate.size()>0){
			promptMsgMapper.batchUpdate(promptMsgListUpdate);
		}
		
		if(promptMsgListAdd.size()>0){
			promptMsgMapper.batchInsert(promptMsgListAdd);
		}
		return 1;
	}
}
