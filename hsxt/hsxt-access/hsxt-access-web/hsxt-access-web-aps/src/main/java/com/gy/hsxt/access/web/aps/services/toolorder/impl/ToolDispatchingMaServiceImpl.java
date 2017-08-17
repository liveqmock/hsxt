/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.gy.hsxt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.ToolDispatchingMaService;
import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolSendService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class ToolDispatchingMaServiceImpl extends BaseServiceImpl implements ToolDispatchingMaService {

	@Autowired
	private IBSToolSendService ibSToolSendService;

	@Resource
	private UserCenterService userCenterService;

	@Override
	public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException
	{

		BaseParam param = new BaseParam();
		param.setRoleIds(userCenterService.getRoleIds((String) filterMap.get("custId")));
		param.setStartDate(filterMap.get("startDate").toString());
		param.setEndDate(filterMap.get("endDate").toString());
		param.setHsResNo(filterMap.get("hsResNo").toString());
		param.setHsCustName(filterMap.get("hsCustName").toString());
		param.setType(filterMap.get("type").toString());

		// 查询类型 2：新增配送 1：申购配送
		String selectType = filterMap.get("selectType").toString();
		PageData<ToolConfigPage> result = null;

		try
		{
			if ("2".equals(selectType))
			{
				result = ibSToolSendService.queryToolConfigDeliveryPage(param, page);
			} else
			{
				result = ibSToolSendService.queryToolConfigDeliveryAppPage(param, page);
			}
			return result == null || result.getCount() == 0 || result.getResult() == null ? null : result;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "调用BS分页查询失败", ex);
			return null;
		}
	}


	@Override
	public ShippingData queryShipingData(String orderType, String[] confNo) throws HsException
	{
		try
		{
			return ibSToolSendService.queryShipingData(orderType, confNo);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryShipingData", "调用BS查询失败", ex);
			return null;
		}
	}


	@Override
	public void addToolShipping(Shipping bean) throws HsException
	{
		try
		{
			ibSToolSendService.addToolShipping(bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "addToolShipping", "调用BS服务失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}


	@Override
	public String queryToolEnterByForFree(String productId, String whId)
	{
		try
		{
			return ibSToolSendService.queryToolEnterByForFree(productId, whId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryToolEnterByForFree", "调用BS查询失败", ex);
			return null;
		}
	}

	@Override
	public Shipping queryShippingById(String shippingId) throws HsException
	{
		try
		{
			return ibSToolSendService.queryShippingById(shippingId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryShippingById", "调用BS查询失败", ex);
			return null;
		}
	}

}
