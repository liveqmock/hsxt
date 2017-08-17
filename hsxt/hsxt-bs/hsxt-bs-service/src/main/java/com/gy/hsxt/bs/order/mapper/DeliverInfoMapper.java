/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 收货信息Mapper DAO映射
 *
 * @Package: com.hsxt.bs.order.mapper
 * @ClassName: DeliverInfoMapper
 * @Description: TODO
 *
 * @author: kongsl
 * @date: 2015-10-9 上午9:52:54
 * @version V1.0
 */
@Repository("deliverInfoMapper")
public interface DeliverInfoMapper {

    /**
     * 插入收货信息
     *
     * @param deliverInfo
     *            收货信息数据
     * @throws HsException
     */
    public int insertDeliverInfo(DeliverInfo deliverInfo);

    public List<DeliverInfo> findDeliverInfoPageList();

    public DeliverInfo findDeliverByDeliverId(String deliverId);

}
