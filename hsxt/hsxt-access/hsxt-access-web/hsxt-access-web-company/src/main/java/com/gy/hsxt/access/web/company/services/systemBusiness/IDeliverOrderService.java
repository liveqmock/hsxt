/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.common.exception.HsException;

/**
 * 发货单查询服务接口
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness  
 * @ClassName: IDeliverOrderService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-10-23 下午4:25:49 
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public interface IDeliverOrderService extends IBaseService {

    /**
     * 发货单确认收货
     * @param shipping
     * @throws HsException
     */
    public void toolAcceptConfirm(Shipping shipping) throws HsException;
}
