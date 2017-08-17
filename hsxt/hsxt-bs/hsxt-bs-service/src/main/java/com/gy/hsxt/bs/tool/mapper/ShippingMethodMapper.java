/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.ShippingMethod;

/**
 * 配送方式Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: ShippingMethodMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午5:27:44
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "shippingMethodMapper")
public interface ShippingMethodMapper {

	/**
	 * 插入配送方式
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午5:28:29
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertShippingMethod(ShippingMethod entity);

	/**
	 * 修改配送方式
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午5:29:05
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateShippingMethodById(ShippingMethod entity);

	/**
	 * 删除配送方式
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午5:29:37
	 * @param smId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int deleteShippingMethodById(String smId);

	/**
	 * 根据id查询配送方式
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午5:30:07
	 * @param smId
	 * @return
	 * @return : ShippingMethod
	 * @version V3.0.0
	 */
	public ShippingMethod selectShippingMeghodById(@Param("smId") String smId);

	/**
	 * 分页查询配送方式
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午5:31:27
	 * @param smName
	 * @return
	 * @return : List<ShippingMethod>
	 * @version V3.0.0
	 */
	public List<ShippingMethod> selectShippingMethodListPage(
			@Param("smName") String smName);

	/**
	 * 查询所有的配送方式
	 * 
	 * @Desc: 
	 * @author: likui
	 * @created: 2015年9月30日 下午5:31:58
	 * @return
	 * @return : List<ShippingMethod>
	 * @version V3.0.0
	 */
	public List<ShippingMethod> selectShippingMethodAll();
}
