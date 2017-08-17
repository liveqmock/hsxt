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
import com.gy.hsxt.gpf.gcs.bean.BizRoute;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.IBizRouteService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service 
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : BizRouteController.java
 * 
 *  Creation Date   : 2015-7-2
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 管理业务路由
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class BizRouteController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IBizRouteService bizRouteService;
	
	@RequestMapping(value = "/bizroute/manage")
	public void manage() {
	}
	
	
	@RequestMapping(value = "bizroute/managePost")
	@ResponseBody
	public String managePost(BizRoute bizRoute,int pageSize, int pageNo) {
		try {
			
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			List<BizRoute> list = bizRouteService.queryBizRoute(bizRoute);
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
	
	@RequestMapping(value = "/bizroute/update")
	@ResponseBody
	public boolean update(BizRoute bizRoute) {
		try {
			
			return bizRouteService.updateBizRoute(bizRoute);
		} catch (Exception e) {
			logger.error("修改某个记录：", e);
		}
		return false;
	}
	
	@RequestMapping(value = "/bizroute/add")
	@ResponseBody
	public int add(BizRoute bizRoute) {
		int flag = 0;//0:不成功  1:成功  2:已存在
		try {
			if(bizRouteService.existBizRoute(bizRoute.getBizCode())){
				flag = 2;
			}else{
				flag =  bizRouteService.addBizRoute(bizRoute);
			}
			
		} catch (Exception e) {
			logger.error("插入某个记录：", e);
		}
		return flag;
	}
	
	
	@RequestMapping(value = "/bizroute/del")
	@ResponseBody
	public boolean del(String bizCode) {
		try {
			return bizRouteService.deleteBizRoute(bizCode);
		} catch (Exception e) {
			logger.error("删除某个记录：", e);
		}
		return false;
	}

}
