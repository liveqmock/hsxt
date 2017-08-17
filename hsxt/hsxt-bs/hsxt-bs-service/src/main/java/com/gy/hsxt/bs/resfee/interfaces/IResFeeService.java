/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.resfee.interfaces;

import java.util.List;

import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bean.resfee.ResFeeRule;
import com.gy.hsxt.bs.bean.resfee.ResFeeTool;
import com.gy.hsxt.common.exception.HsException;

public interface IResFeeService {
    /**
     * 查询 已启用的资源费方案
     *
     * @param toCustType    被申报企业客户类型 非null
     * @param toBuyResRange 被申报企业购买资源段 非null
     * @return
     * @throws HsException
     */
    ResFee getEnableResFee(Integer toCustType, Integer toBuyResRange) throws HsException;

    /**
     * 查询 某一个资源费方案的分配规则
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     * @throws HsException
     */
    List<ResFeeRule> queryResFeeRuleList(String resFeeId) throws HsException;

    /**
     * 查询 某一个资源费包含工具
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     * @throws HsException
     */
    List<ResFeeTool> queryResFeeToolList(String resFeeId) throws HsException;

    /**
     * 获取 某一条资源费方案
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回一条资源费方案记录
     * @throws HsException
     */
    ResFee getResFeeBean(String resFeeId) throws HsException;

}
