/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.Map;

import javax.annotation.Resource;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolorder
 * @className     : ToolDispatchingServiceImpl.java
 * @description   : 售后工具配送
 * @author        : maocy
 * @createDate    : 2016-03-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class ToolDispatchingService extends BaseServiceImpl implements IToolDispatchingService {

    @Autowired
    private IBSToolAfterService bsService;
    
    @Resource
    private UserCenterService userCenterService;
    
    /**
     * 
     * 方法名称：添加售后服务发货单
     * 方法描述：售后订单管理-添加售后服务发货单
     * @param bean 发货单
     * @return
     */
	public void addToolShippingAfter(Shipping bean) throws HsException {
		try
		{
			this.bsService.addToolShippingAfter(bean);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "openCard", "调用BS添加售后服务发货单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
     * 
     * 方法名称：查询发货单的数据
     * 方法描述：售后订单管理-查询发货单的数据
     * @param confNo 配置单编号列表
     * @return
     */
	public ShippingData findAfterShipingData(String[] confNo) throws HsException {
		try
		{
			return this.bsService.queryAfterShipingData(confNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "openCard", "调用BS查询发货单的数据失败", ex);
			return null;
		}
	}
	
	/**
     * 
     * 方法名称：分页查询售后工具配送配送单
     * 方法描述：售后订单管理-分页查询售后工具配送配送单
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
    	BaseParam params = new BaseParam();
		params.setRoleIds(userCenterService.getRoleIds((String) filterMap.get("custId")));
    	params.setStartDate((String) filterMap.get("startDate"));
    	params.setEndDate((String) filterMap.get("endDate"));
    	params.setHsResNo((String) filterMap.get("hsResNo"));
    	params.setHsCustName((String) filterMap.get("hsCustName"));
    	params.setType((String) filterMap.get("type"));
		try
		{
			return this.bsService.queryToolConfigShippingAfterPage(params, page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "openCard", "调用BS分页查询售后工具配送配送单失败", ex);
			return null;
		}
    }
}
