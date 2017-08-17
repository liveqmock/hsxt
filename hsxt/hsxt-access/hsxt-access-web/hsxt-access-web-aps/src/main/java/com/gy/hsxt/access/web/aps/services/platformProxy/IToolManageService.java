/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.platformProxy;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.bs.bean.tool.EntResSegment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.resultbean.EntResource;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具管理service
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness
 * @ClassName: IToolManageService
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-13 下午5:14:19
 * @version V3.0.0
 */

@SuppressWarnings("rawtypes")
@Service
public interface IToolManageService extends IBaseService {

	/**
	 * 根据客户类型查询可购买的工具
	 * 
	 * @param custType
	 *            客户类型
	 * @param toolServie
	 *            工具服务类型 见com.gy.hsxt.common.constant.CustType
	 * @return List
	 * @throws HsException
	 */
	public List<ToolProduct> findMayBuyTools(Integer custType, String toolServie) throws HsException;

	/**
	 * 新增收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:17:05
	 * @param entCustId
	 * @param addr
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addDeliverAddress(String entCustId, String addr) throws HsException;

	/**
	 * 查询企业收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月8日 下午12:08:55
	 * @param entCustId
	 * @return
	 * @throws HsException
	 * @return : JSONArray
	 * @version V3.0.0
	 */
	public JSONArray findEntDeliverAddress(String entCustId) throws HsException;

	/**
	 * 查询某企业客户可以购买某种工具产品的数量
	 * 
	 * @param custId
	 *            企业客户id
	 * @param toolType
	 *            要买的产品类型
	 * @return 可以购买的数量
	 * @throws HsException
	 */
	public int findMayToolNum(String custId, String toolType) throws HsException;

	/**
	 * 提交申购订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月9日 下午12:22:26
	 * @param entCustId
	 *            客户号
	 * @param custEntName
	 *            客户名称
	 * @param custId
	 *            操作员客户号
	 * @param confs
	 *            配置单列表
	 * @param addr
	 *            收货地址
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult commitToolOrder(String entCustId, String custEntName, String custId, String confs,
			String addr, String operName) throws HsException;

	/**
	 * 系统资源购买下单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月15日 下午5:04:07
	 * @param entCustId
	 *            客户号
	 * @param custEntName
	 *            客户名称
	 * @param custId
	 *            操作员客户号
	 * @param product
	 *            工具
	 * @param addr
	 *            收货地址
	 * @param segmentId
	 *            资源段
	 * @param operName
	 *            创建人（系统当前登录人）姓名
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult commitToolOrderCard(String entCustId, String custEntName, String custId, String product,
			String addr, String segmentId, String operName) throws HsException;

	/**
	 * 分页查询企业工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午4:03:36
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<OrderEnt>
	 * @version V3.0.0
	 */
	public PageData<ProxyOrder> queryPlatProxyOrderPage(Map filterMap, Map sortMap, Page page);

	/**
	 * 查询工具订单详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午5:03:47
	 * @param orderNo
	 * @return
	 * @return : OrderBean
	 * @version V3.0.0
	 */
	public ProxyOrder queryPlatProxyOrderById(String orderNo);

	/**
	 * 平台代购订单审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午5:10:36
	 * @param order
	 * @return : void
	 * @version V3.0.0
	 */
	public void platProxyOrderAppr(ProxyOrder order) throws HsException;

	/**
	 * 定制互生卡样下单
	 * 
	 * @Description:
	 * @param entCustId
	 * @param custEntName
	 * @param custId
	 * @param operName
	 *            订单
	 * @return
	 */
	public ToolOrderResult addCardStyleFeeOrder(String entCustId, String custEntName, String custId, String operName);

	/**
	 * 查询企业可以购买的资源段
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 上午9:11:40
	 * @param entCustId
	 * @return
	 * @return : EntResource
	 * @version V3.0.0
	 */
	public EntResource queryEntResourceSegment(String entCustId);

	/**
	 * 查询企业可以购买的资源段(新)
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年6月24日 下午4:14:27
	 * @param entCustId
	 * @return
	 * @return : EntResource
	 * @version V3.0.0
	 */
	public EntResSegment queryEntResourceSegmentNew(String entCustId);

	/**
	 * 查询平台代购订单记录
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月20日 上午10:06:28
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<ProxyOrder>
	 * @version V3.0.0
	 */
	public PageData<ProxyOrder> queryPlatProxyOrderRecordPage(Map filterMap, Map sortMap, Page page);
}
