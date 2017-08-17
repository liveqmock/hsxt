/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.resfee.mapper;

import com.gy.hsxt.bs.bean.resfee.ResFeeRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源费分配规则 Mapper接口
 *
 * @Package: com.gy.hsxt.bs.resfee.mapper
 * @ClassName: ResFeeRuleMapper
 * @Description:
 *
 * @author: liuhq
 * @date: 2015-10-14 下午5:04:05
 * @version V1.0
 */
@Repository
public interface ResFeeRuleMapper {
    /**
     * 创建 资源费分配规则
     *
     * @param resFeeRule 实体类 非null
     * @return 返回true成功，false or Exception失败
     */
    int createResFeeRule(ResFeeRule resFeeRule);

    /**
     * 查询 某一个资源费方案的分配规则
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     */
    List<ResFeeRule> queryResFeeRuleList(@Param("resFeeId") String resFeeId);

    /**
     * 获取 某一条分配规则
     *
     * @param allocItemId 分配规则项编号 非null
     * @return 返回某一条分配规则记录
     */
    ResFeeRule getResFeeRuleBean(@Param("allocItemId") String allocItemId);
}
