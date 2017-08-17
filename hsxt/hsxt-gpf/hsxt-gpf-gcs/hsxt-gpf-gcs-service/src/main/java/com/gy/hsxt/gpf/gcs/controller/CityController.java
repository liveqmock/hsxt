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
import com.gy.hsxt.gpf.gcs.bean.City;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.ICityService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : CityController.java
 * 
 *  Creation Date   : 2015-7-3
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 城市代码管理控制器类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class CityController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Resource(name = "cityService")
	private ICityService cityService;

	/**
	 * 管理页面初始化
	 */
	@RequestMapping(value = "/city/manage")
	public void manage() {

	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param City实体类
	 *            必须，非null
	 * @param pageSize每页显示多少条
	 *            必须，非null
	 * @param pageNo当前页码
	 *            必须，非null
	 * 
	 * @return 返回JSON,数据为空返回空List<City>，异常返回null
	 */
	@RequestMapping(value = "/city/managePost")
	@ResponseBody
	public String managePost(City city, int pageSize, int pageNo) {
		try {
			// 创建分页对象
			PageContext page = PageContext.getContext();
			page.setPageSize(pageSize);
			page.setPageNo(pageNo);
			// 获取分页后的数据
			List<City> list = cityService.getCityListPage(city);
			int totalCount = page.getTotalResults();
			Map<String, Object> mp = new HashMap<>();
			mp.put("totalCount", totalCount);
			mp.put("TotalPages", page.getTotalPages());
			mp.put("list", list);
			// mp转换成json
			return JSON.toJSONString(mp);
		} catch (Exception e) {
			LOG.error("获取数据分页列表异常：", e);
		}
		return null;
	}

	/**
	 * 更新某一条记录
	 * 
	 * @param City实体类
	 *            必须，非null
	 * @return 返回String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/city/Upadte")
	@ResponseBody
	public String upadte(City city) {
		try {
			int y = cityService.update(city);
			return "" + y;
		} catch (Exception e) {
			LOG.error("修改某个记录异常：", e);
		}
		return "0";
	}

	/**
	 * 插入记录
	 * 
	 * @param City
	 *            实体类 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/city/add")
	@ResponseBody
	public String add(City city) {
		try {
			Boolean bo = cityService.existCity(city.getCityNo(),
					city.getCountryNo(), city.getProvinceNo());
			if (!bo) {
				int y = cityService.insert(city);
				return "" + y;
			}
		} catch (Exception e) {
			LOG.error("插入某个记录异常：", e);
		}
		return "" + 0;
	}

	/**
	 * 删除某条记录
	 * 
	 * @param cityNo
	 *            城市代码 必须，非null
	 * @return 返回String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/city/del")
	@ResponseBody
	public String del(String cityNo) {
		try {
			int y = cityService.delete(cityNo);
			return "" + y;
		} catch (Exception e) {
			LOG.error("删除某个记录异常：", e);
		}
		return "" + 0;
	}
}
