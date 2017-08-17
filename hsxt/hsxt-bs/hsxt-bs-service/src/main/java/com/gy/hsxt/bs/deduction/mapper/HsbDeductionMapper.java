/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.deduction.mapper;

import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.bs.bean.deduction.HsbDeductionQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 互生币扣除mapper
 *
 * @Package : com.gy.hsxt.bs.deduction.mapper
 * @ClassName : HsbDeductionMapper
 * @Description : 互生币扣除mapper
 * @Author : chenm
 * @Date : 2016/3/25 17:15
 * @Version V3.0.0.0
 */
@Repository
public interface HsbDeductionMapper {

    /**
     * 保存互生币扣除申请
     *
     * @param hsbDeduction 实体
     * @return 影响条数
     */
    int insertBean(HsbDeduction hsbDeduction);

    /**
     * 分页查询互生币扣除申请列表
     *
     * @param hsbDeductionQuery 查询实体
     * @return 数据列表
     */
    List<HsbDeduction> selectBeanListPage(HsbDeductionQuery hsbDeductionQuery);


    /**
     * 分页查询互生币扣除申请的工单列表
     *
     * @param hsbDeductionQuery 查询实体
     * @return 数据列表
     */
    List<HsbDeduction> selectTaskListPage(HsbDeductionQuery hsbDeductionQuery);

    /**
     * 详情查询
     *
     * @param applyId 申请ID
     * @return 实体
     */
    HsbDeduction selectBeanById(@Param("applyId") String applyId);

    /**
     * 修改更新
     *
     * @param hsbDeduction 实体
     * @return 影响条数
     */
    int updateBean(HsbDeduction hsbDeduction);
}
