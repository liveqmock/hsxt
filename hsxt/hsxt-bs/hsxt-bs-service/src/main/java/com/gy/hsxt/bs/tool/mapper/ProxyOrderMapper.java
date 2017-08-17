/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ProxyOrderDetail;

/**
 * 代购订单Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: ProxyOrderMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午4:54:21
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "proxyOrderMapper")
public interface ProxyOrderMapper {

	/**
	 * 插入平台代购
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午4:55:35
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertProxyOrder(ProxyOrder entity);

	/**
	 * 平台代购订单审批
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午4:56:33
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int proxyOrderAppr(ProxyOrder entity);

	/**
	 * 根据id查询平台代购订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午4:57:10
	 * @param proxyOrderNo
	 * @return
	 * @return : ProxyOrder
	 * @version V3.0.0
	 */
	public ProxyOrder selectProxyOrderById(String proxyOrderNo);

	/**
	 * 分页查询平台代购
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月13日 下午12:26:55
	 * @param param
	 * @return
	 * @return : List<ProxyOrder>
	 * @version V3.0.0
	 */
	public List<ProxyOrder> selectProxyOrderListPage(BaseParam param);

	/**
	 * 批量插入代购清单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:04:58
	 * @param params
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertProxyOrderList(Map<String, Object> params);

	/**
	 * 根据id查询代购清单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:05:47
	 * @param proxyOrderNo
	 * @return
	 * @return : List<ProxyOrderDetail>
	 * @version V3.0.0
	 */
	public List<ProxyOrderDetail> selectProxyOrderDetailById(String proxyOrderNo);

	/**
	 * 分页查询平台代购订单记录
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月20日 上午9:54:08
	 * @param param
	 * @return
	 * @return : List<ProxyOrder>
	 * @version V3.0.0
	 */
	public List<ProxyOrder> selectPlatProxyOrderRecordListPage(BaseParam param);
}
