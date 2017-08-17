/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.api.deduction;

import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.bs.bean.deduction.HsbDeductionQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 互生币扣除接口
 *
 * @Package : com.gy.hsxt.bs.api.deduction
 * @ClassName : IBSHsbDeductionService
 * @Description : 互生币扣除接口
 * @Author : chenm
 * @Date : 2016/3/25 16:49
 * @Version V3.0.0.0
 */
public interface IBSHsbDeductionService {

    /**
     * 互生币扣除申请
     *
     * @param hsbDeduction 扣除实体
     * @return 申请ID
     * @throws HsException
     */
    String applyHsbDeduction(HsbDeduction hsbDeduction) throws HsException;


    /**
     * 条件分页查询互生币扣除申请列表
     *
     * @param page              分页对象
     * @param hsbDeductionQuery 查询对象
     * @return 申请列表
     * @throws HsException
     */
    PageData<HsbDeduction> queryHsbDeductionListPage(Page page, HsbDeductionQuery hsbDeductionQuery) throws HsException;


    /**
     * 条件分页查询互生币扣除申请的工单列表
     *
     * @param page              分页对象
     * @param hsbDeductionQuery 查询对象
     * @return 申请列表
     * @throws HsException
     */
    PageData<HsbDeduction> queryTaskListPage(Page page, HsbDeductionQuery hsbDeductionQuery) throws HsException;


    /**
     * 查询互生币扣除申请详情
     *
     * @param applyId 申请ID
     * @return 详情
     * @throws HsException
     */
    HsbDeduction queryDetailById(String applyId) throws HsException;


    /**
     * 复审互生币扣除申请
     *
     * @param hsbDeduction 扣除申请
     * @return 操作结果
     * @throws HsException
     */
    boolean apprHsbDeduction(HsbDeduction hsbDeduction) throws HsException;
}
