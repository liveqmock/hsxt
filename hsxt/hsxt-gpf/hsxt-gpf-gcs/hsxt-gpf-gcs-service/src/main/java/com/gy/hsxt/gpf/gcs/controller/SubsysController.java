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

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.gpf.gcs.bean.Subsys;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.ISubsysService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : SubsysController.java
 * 
 *  Creation Date   : 2015-7-3
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 系统代码管理控制器类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class SubsysController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	@Resource(name = "subsysService")
	private ISubsysService subsysService;

	/**
	 * 管理页面初始化
	 */
	@RequestMapping(value = "/subsys/manage")
	public void manage() {

	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param subsys实体类
	 *            必须，非null
	 * @param pageSize每页显示多少条
	 *            必须，非null
	 * @param pageNo当前页码
	 *            必须，非null
	 * 
	 * @return 返回JSON,数据为空返回空List<Subsys>，异常返回null
	 */
	@RequestMapping(value = "/subsys/managePost")
	@ResponseBody
	public String managePost(Subsys subsys, int pageSize, int pageNo) {
		try {
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			List<Subsys> list = subsysService.getSubsysListPage(subsys);
			int totalCount = page.getTotalResults();
			Map<String, Object> mp = new HashMap<>();
			mp.put("totalCount", totalCount);
			mp.put("TotalPages", page.getTotalPages());
			mp.put("list", list);
			return JSON.toJSONString(mp);
		} catch (Exception e) {
			LOG.error("获取记录列表异常：", e);
		}
		return null;
	}

	/**
	 * 更新某条记录
	 * 
	 * @param subsys实体类
	 *            必须，非null
	 * @return 返回String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/subsys/upadte")
	@ResponseBody
	public String upadte(Subsys subsys) {
		try {
			int y = subsysService.update(subsys);
			return y + "";
		} catch (Exception e) {
			LOG.error("修改某个记录：", e);
		}
		return 0 + "";
	}

	/**
	 * 插入记录
	 * 
	 * @param subsys
	 *            实体类 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/subsys/add")
	@ResponseBody
	public String add(Subsys subsys) {
		try {
			int y = subsysService.insert(subsys);
			return y + "";
		} catch (Exception e) {
			LOG.error("插入某个记录：", e);
		}
		return 0 + "";
	}

	/**
	 * 删除某条记录
	 * 
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/subsys/del")
	@ResponseBody
	public String del(String subsysCode) {
		try {
			int y = subsysService.delete(subsysCode);
			return y + "";
		} catch (Exception e) {
			LOG.error("删除某个记录：", e);
		}
		return 0 + "";
	}

	/**
	 * 获取下拉菜单列表
	 * 
	 * @return 返回List<Subsys>,数据为空返回空List<Subsys>，异常返回null
	 */
	@RequestMapping(value = "/country/getSubsysDropdownmenu")
	@ResponseBody
	public List<Subsys> getSubsysDropdownmenu() {
		try {
			List<Subsys> list = subsysService.getSubsysDropdownmenu();
			return list;
		} catch (Exception e) {
			LOG.error("获取记录列表异常：", e);
		}
		return null;
	}

}
