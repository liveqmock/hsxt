/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ProxyOrder;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.common.exception.HsException;

/**
 * 平台代购工具Service
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder  
 * @ClassName: PlatformPurchasToolService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-26 上午11:38:17 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface PlatformPurchasToolService extends IBaseService {
    
    /**
     * 查询可以购买的工具
     * @param custType   客户类型  见：com.gy.hsxt.common.constant.CustType
     * @return
     * @throws HsException
     */
    public List<ToolProduct> queryMayBuyToolProduct(Integer custType) throws HsException;
    
    /**
     * 查询工具类别可以购买数量
     * @param endCustId  企业客户号
     * @param categoryCode 
     * @return
     * @throws HsException
     */
    public Integer queryMayBuyToolNum(String entCustId,String categoryCode) throws HsException;
    
    /**
     * 添加平台代购订单
     * @param bean
     * @throws HsException
     */
    public void addPlatProxyOrder(ProxyOrder bean)throws HsException;
    
    /**
     * 查询申购工具时互生卡样
     * @return
     * @throws HsException
     */
    public List<CardStyle> queryBuyToolCardStyle(String entResNo)throws HsException;

}
