/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolShippingPage;

/**
 * 发货单Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: ShippingMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午5:17:47
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "shippingMapper")
public interface ShippingMapper {

	/**
	 * 插入发货单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:18:53
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertShipping(Shipping entity);

	/**
	 * 修改发货单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:19:20
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateShippingById(Shipping entity);

	/**
	 * 分页查询新增申报发货单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:23:36
	 * @param bean
	 * @return
	 * @return : List<ToolShippingPage>
	 * @version V3.0.0
	 */
	public List<ToolShippingPage> selectShippingListPage(BaseParam bean);

	/**
	 * 根据id查询发货单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:24:22
	 * @param shippingId
	 * @return
	 * @return : Shipping
	 * @version V3.0.0
	 */
	public Shipping selectShippingById(String shippingId);

	/**
	 * 查询可以自动签收的发货单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月29日 下午5:28:37
	 * @param signDate
	 * @return : void
	 * @version V3.0.0
	 */
	public List<String> selectShippingAutoSign(String signDate);

	/**
	 * 批量签收发货单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月29日 下午6:02:09
	 * @param shippings
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int batchSignShipping(List<String> shippings);

	/**
	 * 分页查询售后发货单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月5日 下午2:14:41
	 * @param bean
	 * @return
	 * @return : List<ToolShippingPage>
	 * @version V3.0.0
	 */
	public List<ToolShippingPage> selectAfterShippingListPage(BaseParam bean);

}
