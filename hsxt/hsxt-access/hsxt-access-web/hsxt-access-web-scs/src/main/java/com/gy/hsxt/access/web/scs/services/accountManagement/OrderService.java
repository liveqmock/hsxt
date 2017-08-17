/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.accountManagement;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.exception.HsException;

/**
 * *************************************************************************
 * 
 * <PRE>
 * Description      : 订单管理的操作类
 * 
 * Project Name     : hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.accountManagement  
 * 
 * File Name        : BalanceService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-8 下午4:11:39
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-8 下午4:11:39
 * 
 * UpdateRemark     : 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ************************************************************************** 
 */
@Service
public class OrderService extends BaseServiceImpl implements IOrderService {
    //发布内部转帐服务
//    @Autowired
    private IBSOrderService bsOrderService;

    /**
     * 保存业务订单
     * @param transInner
     * @throws HsException 
     * @see com.gy.hsxt.access.web.scs.services.accountManagement.IOrderService#saveOrder(com.gy.hsxt.bs.bean.order.TransInner)
     */
    @Override
    public void saveOrder(Order order) throws HsException {
//        this.bsOrderService.saveOrder(order, "");
    }
  
  
    
    
  
   

}
