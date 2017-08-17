/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.PlatformPurchasToolService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class PlatformPurchasToolServiceImpl extends BaseServiceImpl implements PlatformPurchasToolService {

    @Autowired
    private IBSToolOrderService ibSToolOrderService;
    @Override
    public List<ToolProduct> queryMayBuyToolProduct(Integer custType) throws HsException {
        
    	try
		{
            return ibSToolOrderService.queryMayBuyToolProduct(custType);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryMayBuyToolProduct", "调用BS查询失败", ex);
			return null;
		}
    	
    }
    

    @Override
    public Integer queryMayBuyToolNum(String entCustId, String categoryCode) throws HsException {
        try
		{
            return ibSToolOrderService.queryMayBuyToolNum(entCustId, categoryCode);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryMayBuyToolNum", "调用BS查询购买工具数量失败", ex);
			return null;
		}
    }

    @Override
    public void addPlatProxyOrder(ProxyOrder bean) throws HsException {
        System.out.println("功能未实现..................");
        
    }

    @Override
    public List<CardStyle> queryBuyToolCardStyle(String entResNo) throws HsException {
    	try
		{
    		return ibSToolOrderService.queryEntSpecialCardStyle(entResNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryBuyToolCardStyle", "调用BS查询失败", ex);
			return null;
		}
    }

}
