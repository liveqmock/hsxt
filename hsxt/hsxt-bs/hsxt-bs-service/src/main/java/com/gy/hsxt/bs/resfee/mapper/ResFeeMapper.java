/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.resfee.mapper;

import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bean.resfee.ResFeeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源费方案 Mapper接口
 *
 * @Package: com.gy.hsxt.bs.resfee.mapper
 * @ClassName: ResFeeMapper
 * @Description:
 *
 * @author: liuhq
 * @date: 2015-10-14 下午5:04:42
 * @version V1.0
 */
@Repository
public interface ResFeeMapper {
    /**
     * 创建 资源费方案
     *
     * @param resFee 实体类 非null
     * @return int
     */
    int createResFee(ResFee resFee);

    /**
     * 查询 资源费方案列表
     *
     * @param resFee 实体类 通过指定bean的属性进行条件查询
     * @return 返回按条件查询List数据列表
     */
    List<ResFee> queryResFeeList(ResFee resFee);

    /**
     * 获取 某一条资源费方案
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回一条资源费方案记录
     */
    ResFee getResFeeBean(@Param("resFeeId") String resFeeId);

    /**
     * 审批 资源费方案
     *
     * @param resFee 实体类 非null
     * @return 返回true成功，false or Exception失败
     */
    int apprResFee(ResFee resFee);

    /**
     * 查询 已启用的资源费方案
     *
     * @param toCustType    被申报企业客户类型
     * @param toBuyResRange 被申报企业购买资源段
     * @return 返回实体类
     */
    ResFee getEnableResFee(@Param(value = "toCustType") Integer toCustType, @Param(value = "toBuyResRange") Integer toBuyResRange);

    /**
     * 获取 某一条待审的资源费方案是否已经存在
     *
     * @param toCustType    被申报企业客户类型 非null
     * @param toBuyResRange 被申报企业购买资源段
     * @return 返回实体类
     */
    String getPendingStatus(@Param(value = "toCustType") Integer toCustType, @Param(value = "toBuyResRange") Integer toBuyResRange);

    /**
     * 查询资源费方案审核列表
     * @param resFeeQuery 查询条件
     * @return
     */
    List<ResFee> selectResFeeTaskList(ResFeeQuery resFeeQuery);
}
