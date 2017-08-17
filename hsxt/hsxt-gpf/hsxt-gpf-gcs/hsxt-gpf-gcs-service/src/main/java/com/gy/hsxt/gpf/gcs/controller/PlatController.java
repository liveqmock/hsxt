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
import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.IPlatService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gl.global.controller
 * 
 *  File Name       : PlatController.java
 * 
 *  Creation Date   : 2015-7-3
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 平台代码管理控制器类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class PlatController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	@Resource(name = "platService")
	private IPlatService platService;

	/**
	 * 管理页面初始化
	 */
	@RequestMapping(value = "/plat/manage")
	public void manage() {

	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param plat实体类
	 *            必须，非null
	 * @param pageSize每页显示多少条
	 *            必须，非null
	 * @param pageNo当前页码
	 *            必须，非null
	 * 
	 * @return 返回JSON,数据为空返回空List<Plat>，异常返回null
	 */
	@RequestMapping(value = "/plat/managePost")
	@ResponseBody
	public String managePost(Plat plat, int pageSize, int pageNo) {
		try {
			// 创建分页对象
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			// 获取分页后的数据
			List<Plat> list = platService.getPlatListPage(plat);
			int totalCount = page.getTotalResults();
			Map<String, Object> mp = new HashMap<>();
			mp.put("totalCount", totalCount);
			mp.put("TotalPages", page.getTotalPages());
			mp.put("list", list);
			// mp转换成json
			return JSON.toJSONString(mp);
		} catch (Exception e) {
			LOG.error("获取记录列表异常：", e);
		}
		return null;
	}

	/**
	 * 更新某条记录
	 * 
	 * @param plat实体类
	 *            必须，非null
	 * @return 返回String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/plat/upadte")
	@ResponseBody
	public String upadte(Plat plat) {
		try {
			int y = platService.update(plat);
			return y + "";
		} catch (Exception e) {
			LOG.error("修改某个记录异常：", e);
		}
		return 0 + "";
	}

	/**
	 * 插入记录
	 * 
	 * @param plat
	 *            实体类 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/plat/add")
	@ResponseBody
	public String add(Plat plat) {
		try {
			int y = platService.insert(plat);
			return y + "";
		} catch (Exception e) {
			LOG.error("插入某个记录异常：", e);
		}
		return 0 + "";
	}

	/**
	 * 删除某条记录
	 * 
	 * @param platNo
	 *            平台代码 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/plat/del")
	@ResponseBody
	public String del(String platNo) {
		try {
			int y = platService.delete(platNo);
			return y + "";
		} catch (Exception e) {
			LOG.error("删除某个记录异常：", e);
		}
		return 0 + "";
	}

	/**
	 * 获取平台列表
	 * 
	 * @return 返回List<Plat>,数据为空返回空List<Plat>，异常返回null
	 */
	@RequestMapping(value = "/plat/getPlatDropdownmenu")
	@ResponseBody
	public List<Plat> findAllPlat() {
		try {
			List<Plat> list = platService.findAllPlat();
			return list;
		} catch (Exception e) {
			LOG.error("获取下拉菜单列表异常：", e);
		}
		return null;
	}

}
