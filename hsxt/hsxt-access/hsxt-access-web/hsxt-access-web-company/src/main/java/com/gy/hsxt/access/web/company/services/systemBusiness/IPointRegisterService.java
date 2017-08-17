/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;

public interface IPointRegisterService extends IBaseService {

  
    /**
     * 网上积分登记
     * @param point 积分实体bean
     * @param pointRate 积分比例数字
     * @return PointResult 操作员客户号
     * @throws HsException
     */
	public PointResult pointRegister(Point point) throws HsException;
    
}
