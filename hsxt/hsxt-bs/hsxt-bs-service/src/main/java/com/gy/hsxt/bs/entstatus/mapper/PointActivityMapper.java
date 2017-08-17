/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.entstatus.PointActivity;
import com.gy.hsxt.bs.bean.entstatus.PointActivityBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PointActivityStatus;
import com.gy.hsxt.bs.entstatus.bean.PointActivityParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.mapper
 * @ClassName: PointActivityMapper
 * @Description: 积分活动Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午5:02:34
 * @version V1.0
 */
@Repository
public interface PointActivityMapper {

    /**
     * 创建积分活动申请
     * 
     * @param pointActivity
     *            积分活动申请
     */
    int createPointActivity(PointActivity pointActivity);

    /**
     * 查询积分活动申请
     * 
     * @param pointActivityParam
     *            查询参数
     * @return 积分活动申请列表
     */
    List<PointActivityBaseInfo> queryPointActivityListPage(PointActivityParam pointActivityParam);

    /**
     * 关联工单查询积分活动申请
     * 
     * @param pointActivityParam
     *            查询参数
     * @return 积分活动申请列表
     */
    List<PointActivityBaseInfo> queryPointActivity4ApprListPage(PointActivityParam pointActivityParam);

    /**
     * 更新积分活动申请状态
     * 
     * @param applyId
     *            申请编号
     * @param status
     *            状态
     * @param optCustId
     *            操作员
     */
    void updatePointActivityStatus(@Param("applyId") String applyId, @Param("status") Integer status,
            @Param("optCustId") String optCustId);

    /**
     * 查询积分活动详情
     * 
     * @param applyId
     *            申请编号
     * @return 积分活动详情
     */
    PointActivity queryPointActivityById(String applyId);

    /**
     * 根据企业客户号查询积分活动状态
     * 
     * @param entCustId
     *            企业客户号
     * @return 积分活动状态信息
     */
    PointActivityStatus queryPointActivityStatus(String entCustId);

    /**
     * 检查是否存在正在审批的积分活动申请
     * 
     * @param entCustId
     *            企业客户号
     * @return true存在，false不存
     */
    boolean existPointActivity(String entCustId);
}
