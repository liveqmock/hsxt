/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.Map;

import com.gy.hsxt.bs.api.tool.IBSToolStockService;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.ToolOrderListService;
import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.OrderBean;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 工具订单Service
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder.impl
 * @ClassName: ToolOrderListServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月25日 下午4:27:44
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public class ToolOrderListServiceImpl extends BaseServiceImpl implements ToolOrderListService {

	/**
	 * BS工具订单Service
	 */
	@Autowired
	private IBSToolOrderService ibSToolOrderService;

	/**
	 * 用户中心Service
	 */
	@Autowired
	private UserCenterService userCenterService;

	@Autowired
	private IBSToolStockService bSToolStockService;// 仓库服务类

	/**
	 * 分页查询工具订单
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData<ToolOrderPage> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException
	{
		BaseParam param = new BaseParam();
		param.setStartDate(filterMap.get("startDate").toString());
		param.setEndDate(filterMap.get("endDate").toString());
		param.setOrderNo(filterMap.get("orderNo").toString());
		param.setHsResNo(filterMap.get("hsResNo").toString());
		Integer status = filterMap.get("orderState") == null || "".equals(filterMap.get("orderState").toString()) ? null
				: Integer.parseInt(filterMap.get("orderState").toString());
		param.setStatus(status);

		try
		{
			return ibSToolOrderService.queryToolOrderPlatPage(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "调用BS分页查询工具订单失败", ex);
			return null;
		}
	}

	/**
	 * 查询订单详情
	 * 
	 * @Description:
	 * @param id
	 * @return
	 * @throws HsException
	 */
	@Override
	public OrderBean findById(Object id) throws HsException
	{
		try
		{
			return ibSToolOrderService.queryOrderDetailByNo((String) id);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findById", "调用BS查询工具订单详情失败", ex);
			return null;
		}
	}

	/**
	 * 平台确认企业工具制作
	 * 
	 * @Description:
	 * @param orderNo
	 * @throws HsException
	 */
	@Override
	public void toolOrderConfirmMark(String orderNo) throws HsException
	{
		try
		{
			ibSToolOrderService.toolOrderConfirmMark(orderNo);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "toolOrderConfirmMark", "调用BS确认企业工具制作失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 平台关闭订单
	 * 
	 * @Description:
	 * @param orderNo
	 * @throws HsException
	 */
	@Override
	public void closeToolOrder(String orderNo) throws HsException
	{
		try
		{
			ibSToolOrderService.closeOrWithdrawalsToolOrder(orderNo);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "closeToolOrder", "调用BS关闭企业工具订单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/** 分页查询配置单
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/8 9:57
	 * @param: a
	 * @eturn: a
	 * @trows: a
	 * @copany: gyist
	 * @version V3.0.0
	 */
	@Override
	public PageData<ToolConfig> getToolConfigPage(Map filterMap, Map sortMap, Page page) {
		BaseParam param = new BaseParam();
		param.setStartDate(filterMap.get("startDate").toString());
		param.setEndDate(filterMap.get("endDate").toString());
		param.setHsResNo(filterMap.get("hsResNo").toString());
		param.setWhId(filterMap.get("type").toString());
		String platCustName = (String) filterMap.get("custName");
		if(StringUtils.isNotBlank(platCustName)&&!"0000".equals(platCustName))
		{
			param.setRoleIds(userCenterService.getRoleIds((String) filterMap.get("custId")));
		}
		try
		{
			return bSToolStockService.queryToolConfigPage(param, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "getToolConfigPage", "调用BS分页查询工具配置单失败", ex);
			return null;
		}
	}
}
