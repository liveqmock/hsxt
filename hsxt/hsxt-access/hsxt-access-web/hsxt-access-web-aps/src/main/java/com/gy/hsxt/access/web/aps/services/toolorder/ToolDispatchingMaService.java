/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具配送管理服务
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder  
 * @ClassName: ToolDispatchingService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-12-16 下午6:09:44 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface ToolDispatchingMaService extends IBaseService {

    /**
     * 工具配送管理查询页面 查看配送单
     * @param confNo 配置单编号列表
     * @param orderType 订单类型103：新增申购 101:申报申购  见枚举类:com.gy.hsxt.bs.common.enumtype.order.OrderType
     * @return
     * @throws HsException
     */
    public ShippingData queryShipingData(String orderType,String[] confNo) throws HsException;
    
    /**
     * 添加发货单
     * @param bean 发货单内容
     * @throws HsException
     */
    public void addToolShipping(Shipping bean)throws HsException;
    
    /**
     * 查询工具库存数量
     * @param productId
     * @param whId
     * @return
     */
    public String queryToolEnterByForFree(String productId,String whId);
    
    /**
     * 查询配置单数据
     * @param shippingId
     * @return
     */
    public Shipping queryShippingById(String shippingId)throws HsException;
}
