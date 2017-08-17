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
import com.gy.hsxt.gpf.gcs.bean.ErrorMsg;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.IErrorMsgService;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : ErrorMsgController.java
 * 
 *  Creation Date   : 2015-7-3
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 管理错误信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class ErrorMsgController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IErrorMsgService errorMsgService;
	
	@Autowired
	private ILanguageService languageService;
	
	
	@RequestMapping(value = "/errormsg/manage")
	public void manage() {
	}
	
	@RequestMapping(value = "errormsg/managePost")
	@ResponseBody
	public String managePost(String languageCode,String errorCode,String errorMsg,int pageSize, int pageNo) {
		try {
			
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			int errorCodeInt = 0;
			if(null != errorCode && !"".equals(errorCode)){
				errorCodeInt = Integer.valueOf(errorCode);
			}
			ErrorMsg errorMsgObj = new ErrorMsg(languageCode,errorCodeInt);
			errorMsgObj.setErrorMsg(errorMsg);
			List<ErrorMsg> list = errorMsgService.queryErrorMsg(errorMsgObj);
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
	
	@RequestMapping(value = "/errormsg/update")
	@ResponseBody
	public boolean update(String languageCode,String errorCode,String errorMsg) {
		try {
			int errorCodeInt = 0;
			if(null != errorCode && !"".equals(errorCode)){
				errorCodeInt = Integer.valueOf(errorCode);
			}
			ErrorMsg errorMsgObj = new ErrorMsg(languageCode,errorCodeInt);
			errorMsgObj.setErrorMsg(errorMsg);
			return errorMsgService.updateErrorMsg(errorMsgObj);
		} catch (Exception e) {
			logger.error("修改某个记录：", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/errormsg/add")
	@ResponseBody
	public int add(String languageCode,String errorCode,String errorMsg) {
		int errorCodeInt = 0;
		if(null != errorCode && !"".equals(errorCode)){
			errorCodeInt = Integer.valueOf(errorCode);
		}
		ErrorMsg errorMsgObj = new ErrorMsg(languageCode,errorCodeInt);
		errorMsgObj.setErrorMsg(errorMsg);
		
		int flag = 0;//0:不成功  1:成功  2:已存在
		try {
			if(errorMsgService.existErrorMsg(languageCode,errorCodeInt)){
				flag = 2;
			}else{
				flag =  errorMsgService.addErrorMsg(errorMsgObj);
			}
			
		} catch (Exception e) {
			logger.error("插入某个记录：", e);
		}
		return flag;
	}
	
	
	@RequestMapping(value = "/errormsg/del")
	@ResponseBody
	public boolean del(String languageCode,int errorCode) {
		try {
			return errorMsgService.deleteErrorMsg(languageCode,errorCode);
		} catch (Exception e) {
			logger.error("删除某个记录：", e);
		}
		return false;
	}

}
