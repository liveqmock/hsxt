/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.toolorder.PlatFormPurchasAuditService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class PlatFormPurchasAuditServiceImpl extends BaseServiceImpl implements PlatFormPurchasAuditService {

    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        //com.gy.hsxt.bs.api.tool.IBSToolOrderService.queryPlatProxyOrderPage
        System.out.println("查询平台代购工具列表，互生号：" + filterMap.get("entResNo") + ",状态：" + filterMap.get("status"));
        System.out.println("功能未实现.............................");
        return null;
    }


    @Override
    public ProxyOrder queryPlatProxyOrderById(String proxyOrderNo) throws HsException {
        //com.gy.hsxt.bs. api.tool.IBSToolOrderService.queryPlatProxyOrderById
        System.out.println("功能未实现.............................");
        return null;
    }

    @Override
    public void platProxyOrderAppr(ProxyOrder bean) throws HsException {
        System.out.println("平台订购订单审核.......功能未实现");
        
    }
}
