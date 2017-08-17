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
import com.gy.hsxt.gpf.gcs.bean.PointAssignRule;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.IPointAssignRuleService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : PointAssignRuleController.java
 * 
 *  Creation Date   : 2015-7-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 管理积分分配比例
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class PointAssignRuleController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IPointAssignRuleService pointAssignRuleService;
	
	@RequestMapping(value = "/pointrule/manage")
	public void manage() {
	}
	
	
	@RequestMapping(value = "pointrule/managePost")
	@ResponseBody
	public String managePost(PointAssignRule pointAssignRule,int pageSize, int pageNo) {
		try {
			
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			List<PointAssignRule> list = pointAssignRuleService.queryAssignRule(pointAssignRule);
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
	
	@RequestMapping(value = "/pointrule/update")
	@ResponseBody
	public boolean update(PointAssignRule pointAssignRule) {
		try {
			
			return pointAssignRuleService.updateAssignRule(pointAssignRule);
		} catch (Exception e) {
			logger.error("修改某个记录：", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/pointrule/add")
	@ResponseBody
	public int add(PointAssignRule pointAssignRule) {
		int flag = 0;//0:不成功  1:成功  2:已存在
		try {
			if(pointAssignRuleService.existAssignRule(pointAssignRule.getTargetType(),pointAssignRule.getActType())){
				flag = 2;
			}else{
				flag =  pointAssignRuleService.addAssignRule(pointAssignRule);
			}
			
		} catch (Exception e) {
			logger.error("插入某个记录：", e);
		}
		return flag;
	}
	
	
	@RequestMapping(value = "/pointrule/del")
	@ResponseBody
	public boolean del(String targetType,String actType) {
		try {
			return pointAssignRuleService.delAssignRule(targetType, actType);
		} catch (Exception e) {
			logger.error("删除某个记录：", e);
		}
		return false;
	}

}
