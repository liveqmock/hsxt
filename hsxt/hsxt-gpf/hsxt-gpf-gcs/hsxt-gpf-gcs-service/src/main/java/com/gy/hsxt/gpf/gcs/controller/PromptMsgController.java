/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gy.hsxt.gpf.gcs.bean.PromptMsg;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IPromptMsgService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : PromptMsgController.java
 * 
 *  Creation Date   : 2015-7-3
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 管理提示信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class PromptMsgController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPromptMsgService promptMsgService;

	@Autowired
	private ILanguageService languageService;
	
	
	@RequestMapping(value = "/promptmsg/manage")
	public void manage() {
	}
	
	@RequestMapping(value = "promptmsg/managePost")
	@ResponseBody
	public String managePost(PromptMsg promptMsg,int pageSize, int pageNo) {
		try {
			
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			List<PromptMsg> list = promptMsgService.queryPromptMsg(promptMsg);
			int totalCount = page.getTotalResults();
			Map<String, Object> map = new HashMap<>();
			map.put("totalCount", totalCount);
			map.put("TotalPages", page.getTotalPages());
			map.put("list", list);
			return JSON.toJSONString(map,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
		} catch (Exception e) {
			logger.error("获取记录列表异常：", e);
		}
		return null;
	}
	
	@RequestMapping(value = "/promptmsg/update")
	@ResponseBody
	public boolean update(PromptMsg promptMsg) {
		try {
			
			return promptMsgService.updatePromptMsg(promptMsg);
		} catch (Exception e) {
			logger.error("修改某个记录：", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/promptmsg/add")
	@ResponseBody
	public int add(PromptMsg promptMsg) {
		int flag = 0;//0:不成功  1:成功  2:已存在
		try {
			if(promptMsgService.existPromptMsg(promptMsg.getLanguageCode(),promptMsg.getPromptCode())){
				flag = 2;
			}else{
				flag =  promptMsgService.addPromptMsg(promptMsg);
			}
			
		} catch (Exception e) {
			logger.error("插入某个记录：", e);
		}
		return flag;
	}
	
	
	@RequestMapping(value = "/promptmsg/del")
	@ResponseBody
	public boolean del(String languageCode,String promptCode) {
		try {
			return promptMsgService.deletePromptMsg(languageCode,promptCode);
		} catch (Exception e) {
			logger.error("删除某个记录：", e);
		}
		return false;
	}

}
