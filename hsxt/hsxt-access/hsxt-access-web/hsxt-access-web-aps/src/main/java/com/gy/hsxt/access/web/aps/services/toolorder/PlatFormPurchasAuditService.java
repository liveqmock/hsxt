/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.common.exception.HsException;

/**
 * 平台代购工具复核
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder  
 * @ClassName: PlatFormPurchasAuditService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-27 下午4:33:18 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface PlatFormPurchasAuditService extends IBaseService {
    
    /**
     * 根据代购订单编号查询代购订单
     * @param proxyOrderNo 代购订单编号
     * @return
     * @throws HsException
     */
    public ProxyOrder queryPlatProxyOrderById(String proxyOrderNo) throws HsException;
    
    /**
     * 审批代购订单
     * @param bean
     * @throws HsException
     */
    public void platProxyOrderAppr(ProxyOrder bean)throws HsException;

}
