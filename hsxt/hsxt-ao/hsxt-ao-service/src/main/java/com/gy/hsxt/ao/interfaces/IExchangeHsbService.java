/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.interfaces;

import com.gy.hsxt.ao.bean.BuyHsb;

/**
 * 兑换互生币内部接口
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: IExchangeHsbService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-6-30 下午8:24:25
 * @version V1.0
 */
public interface IExchangeHsbService {

    public void updatePayModel(String transNo, int payModel);

    public BuyHsb findUnPayBuyHsb(String transNo);
}
