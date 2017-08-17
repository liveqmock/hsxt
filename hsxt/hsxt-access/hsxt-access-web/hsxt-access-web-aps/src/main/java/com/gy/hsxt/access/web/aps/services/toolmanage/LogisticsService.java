/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.toolmanage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolSendService;
import com.gy.hsxt.bs.bean.tool.ShippingMethod;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className : LogisticsService.java
 * @description : 物流配送信息接口实现
 * @author : maocy
 * @createDate : 2016-01-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class LogisticsService extends BaseServiceImpl implements
		ILogisticsService {

	@Autowired
	private IBSToolSendService bsService;// 工具服务类

	/**
	 * 
	 * 方法名称：查询配送方式 方法描述：物流配送信息-查询配送方式
	 * 
	 * @param filterMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件
	 * @param page
	 *            分页属性
	 * @return
	 */
	public PageData<ShippingMethod> findScrollResult(Map filterMap,
			Map sortMap, Page page) throws HsException {
		try
		{
			return this.bsService.queryShippingMethodByPage(
					(String) filterMap.get("smName"), page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "调用BS分页查询配送方式失败", ex);
			return null;
		}
	}

	/**
	 * 
	 * 方法名称：添加配送方式 方法描述：物流配送信息-添加配送方式
	 * 
	 * @param bean
	 *            配送方式信息
	 * @throws HsException
	 */
	public void addShipping(ShippingMethod bean) throws HsException {
		try {
			this.bsService.addShippingMethod(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addShipping",
					"调用BS服务添加配送方式失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}

	}

	/**
	 * 
	 * 方法名称：修改配送方式 方法描述：物流配送信息-修改配送方式
	 * 
	 * @param bean
	 *            配送方式信息
	 * @throws HsException
	 */
	public void modifyShipping(ShippingMethod bean) throws HsException {
		try {
			this.bsService.modifyShippingMethod(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "modifyShipping",
					"调用BS服务修改配送方式 失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：删除配送方式 方法描述：物流配送信息-删除配送方式
	 * 
	 * @param smId
	 *            配送方式编号
	 * @throws HsException
	 */
	public void removeShipping(String smId) throws HsException {
		try {
			this.bsService.removeShippingMethod(smId);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "removeShipping",
					"调用BS服务删除配送方式失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	
	}

}