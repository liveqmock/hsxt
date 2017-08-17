/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.resfee;

import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bean.resfee.ResFeeQuery;
import com.gy.hsxt.bs.bean.resfee.ResFeeRule;
import com.gy.hsxt.bs.bean.resfee.ResFeeTool;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 资源费 接口类
 *
 * @Package: com.gy.hsxt.bs.api.resfee
 * @ClassName: IResFeeService
 * @Description:
 *
 * @author: liuhq
 * @date: 2015-9-2 上午11:01:51
 * @version V1.0
 */
public interface IBSResFeeService {

    //=================== 资源费方案 开始 =====================

    /**
     * 创建 资源费方案
     *
     * @param resFee 实体类 非null
     * @return 返回true成功，false or Exception失败
     * @throws HsException
     */
    void createResFee(ResFee resFee) throws HsException;

    /**
     * 查询 资源费方案列表
     *
     * @param resFee 实体类 通过指定bean的属性进行条件查询
     * @return 返回按条件查询List数据列表
     * @throws HsException
     */
    List<ResFee> queryResFeeList(ResFee resFee) throws HsException;

    /**
     * 查询资源费方案审核列表
     *
     * @param resFeeQuery 查询条件
     * @return 返回按条件查询List数据列表
     * @throws HsException
     */
    List<ResFee> queryResFeeTaskList(ResFeeQuery resFeeQuery) throws HsException;

    /**
     * 获取 某一条资源费方案
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回一条资源费方案记录
     * @throws HsException
     */
    ResFee getResFeeBean(String resFeeId) throws HsException;

    /**
     * 审批 资源费方案
     *
     * @param resFee 实体类 非null
     * @return 返回true成功，false or Exception失败
     * @throws HsException
     */
    void apprResFee(ResFee resFee) throws HsException;


    //=================== 资源费分配规则 开始 =====================


    /**
     * 创建 资源费分配规则
     *
     * @param resFeeRule 实体类 非null
     * @return 返回true成功，false or Exception失败
     * @throws HsException
     */
    void createResFeeRule(ResFeeRule resFeeRule) throws HsException;

    /**
     * 查询 某一个资源费方案的分配规则
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     * @throws HsException
     */
    List<ResFeeRule> queryResFeeRuleList(String resFeeId)
            throws HsException;

    /**
     * 获取 某一条分配规则
     *
     * @param allocItemId 分配规则项编号 非null
     * @return 返回某一条分配规则记录
     * @throws HsException
     */
    ResFeeRule getResFeeRuleBean(String allocItemId)
            throws HsException;


    //=================== 资源费包含工具 开始 =====================


    /**
     * 创建 资源费包含工具
     *
     * @param resFeeTool 实体类 非null
     * @return 返回true成功，false or Exception失败
     * @throws HsException
     */
    void createResFeeTool(ResFeeTool resFeeTool) throws HsException;

    /**
     * 查询 某一个资源费包含工具
     *
     * @param resFeeId 资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     * @throws HsException
     */
    List<ResFeeTool> queryResFeeToolList(String resFeeId)
            throws HsException;

}
