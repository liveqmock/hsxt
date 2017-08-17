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
import com.gy.hsxt.gpf.gcs.bean.EnumItem;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.IEnumItemService;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : EnumItemController.java
 * 
 *  Creation Date   : 2015-7-2
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 管理枚举信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class EnumItemController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IEnumItemService enumItemService;
	
	@Autowired
	private ILanguageService languageService;
	
	
	@RequestMapping(value = "/enumitem/manage")
	public void manage() {
	}

	
	
	@RequestMapping(value = "enumitem/managePost")
	@ResponseBody
	public String managePost(EnumItem enumItem,int pageSize, int pageNo) {
		try {
			
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			List<EnumItem> list = enumItemService.queryEnumItem(enumItem);
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
	
	@RequestMapping(value = "/enumitem/update")
	@ResponseBody
	public boolean update(EnumItem enumItem) {
		try {
			
			return enumItemService.updateEnumItem(enumItem);
		} catch (Exception e) {
			logger.error("修改某个记录：", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/enumitem/add")
	@ResponseBody
	public int add(EnumItem enumItem) {
		int flag = 0;//0:不成功  1:成功  2:已存在
		try {
			if(enumItemService.existEnumItem(enumItem.getLanguageCode(),enumItem.getGroupCode(),enumItem.getItemCode())){
				flag = 2;
			}else{
				flag =  enumItemService.addEnumItem(enumItem);
			}
			
		} catch (Exception e) {
			logger.error("插入某个记录：", e);
		}
		return flag;
	}
	
	
	@RequestMapping(value = "/enumitem/del")
	@ResponseBody
	public boolean del(String languageCode,String groupCode,String itemCode) {
		try {
			return enumItemService.deleteEnumItem(languageCode,groupCode,itemCode);
		} catch (Exception e) {
			logger.error("删除某个记录：", e);
		}
		return false;
	}
	
}
