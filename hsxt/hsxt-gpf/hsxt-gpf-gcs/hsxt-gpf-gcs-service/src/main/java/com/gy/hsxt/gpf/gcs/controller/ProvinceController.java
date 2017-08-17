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
import com.gy.hsxt.gpf.gcs.bean.Province;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.IProvinceService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gl.global.controller
 * 
 *  File Name       : ProvinceController.java
 * 
 *  Creation Date   : 2015-7-3
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 省份代码管理控制器类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class ProvinceController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Resource(name = "provinceService")
	private IProvinceService provinceService;

	/**
	 * 管理页面初始化
	 */
	@RequestMapping(value = "/province/manage")
	public void manage() {

	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param province实体类
	 *            必须，非null
	 * @param pageSize每页显示多少条
	 *            必须，非null
	 * @param pageNo当前页码
	 *            必须，非null
	 * 
	 * @return 返回JSON,数据为空返回空List<Province>，异常返回null
	 */
	@RequestMapping(value = "/province/managePost")
	@ResponseBody
	public String managePost(Province province, int pageSize, int pageNo) {
		try {
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			List<Province> list = provinceService.getProvinceListPage(province);
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
	 * 获取下拉菜单列表
	 * 
	 * @return 返回List<Province>,数据为空返回空List<Province>，异常返回null
	 */
	@RequestMapping(value = "/province/getProvinceDropdownmenu")
	@ResponseBody
	public List<Province> getProvinceDropdownmenu(String countryNo) {
		try {
			List<Province> list = provinceService.getProvinceDropdownmenu(countryNo);
			return list;
		} catch (Exception e) {
			LOG.error("获取记录列表异常：", e);
		}
		return null;
	}

	/**
	 * 更新某条记录
	 * 
	 * @param province实体类
	 *            必须，非null
	 * @return 返回String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/province/Upadte")
	@ResponseBody
	public String upadte(Province province) {
		try {
			int y = provinceService.update(province);
			return y + "";
		} catch (Exception e) {
			LOG.error("修改某个记录异常：", e);
		}
		return 0 + "";
	}

	/**
	 * 插入记录
	 * 
	 * @param province
	 *            实体类 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/province/add")
	@ResponseBody
	public String add(Province province) {
		try {
			int y = provinceService.insert(province);
			return y + "";
		} catch (Exception e) {
			LOG.error("插入某个记录异常：", e);
		}
		return 0 + "";
	}

	/**
	 * 删除某条记录
	 * 
	 * @param provinceNo
	 *           省份代码 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/province/del")
	@ResponseBody
	public String del(String provinceNo) {
		try {
			int y = provinceService.delete(provinceNo);
			return y + "";
		} catch (Exception e) {
			LOG.error("删除某个记录异常：", e);
		}
		return 0 + "";
	}
}
