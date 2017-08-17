/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import com.gy.hsxt.ao.bean.BuyHsbCancel;

/**
 * 货币兑换互生币冲正mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: BuyHsbCancelMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-27 下午7:01:55
 * @version V3.0.0
 */
public interface BuyHsbCancelMapper {

    /**
     * 插入货币兑换互生币冲正
     * 
     * @param buyHsbCancel
     *            冲正信息
     * @return 成功记录数
     */
    public int insertBuyHsbCancel(BuyHsbCancel buyHsbCancel);

}
