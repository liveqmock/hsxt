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
import com.gy.hsxt.gpf.gcs.bean.Unit;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IUnitService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : UnitController.java
 * 
 *  Creation Date   : 2015-7-14
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 管理计量单位
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@Controller
public class UnitController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUnitService unitService;

	@Autowired
	private ILanguageService languageService;
	
	
	@RequestMapping(value = "/unit/manage")
	public void manage() {
	}
	
	@RequestMapping(value = "unit/managePost")
	@ResponseBody
	public String managePost(Unit unit,int pageSize, int pageNo) {
		try {
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			List<Unit> list = unitService.queryUnit(unit);
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
	
	@RequestMapping(value = "/unit/update")
	@ResponseBody
	public boolean update(Unit unit) {
		try {
			
			return unitService.updateUnit(unit);
		} catch (Exception e) {
			logger.error("修改某个记录：", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/unit/add")
	@ResponseBody
	public int add(Unit unit) {
		int flag = 0;//0:不成功  1:成功  2:已存在
		try {
			if(unitService.existUnit(unit.getLanguageCode(),unit.getUnitCode())){
				flag = 2;
			}else{
				flag =  unitService.addUnit(unit);
			}
			
		} catch (Exception e) {
			logger.error("插入某个记录：", e);
		}
		return flag;
	}
	
	@RequestMapping(value = "/unit/del")
	@ResponseBody
	public boolean del(String languageCode,String unitCode) {
		try {
			return unitService.deleteUnit(languageCode,unitCode);
		} catch (Exception e) {
			logger.error("删除某个记录：", e);
		}
		return false;
	}

}
