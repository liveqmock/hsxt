/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.mapper;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkQuery;
import com.gy.hsxt.bs.bean.tempacct.TempOrderQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 临帐关联申请Mapper接口
 *
 * @Package : com.gy.hsxt.bs.tempacct.mapper
 * @ClassName : TempAcctLinkMapper
 * @Description : 临帐关联申请Mapper接口
 * @Author : liuhq
 * @Date : 2015-10-20 上午11:10:13
 * @Version V1.0
 */
@Repository
public interface TempAcctLinkMapper {
    /**
     * 创建 临帐关联申请
     *
     * @param tempAcctLink 临帐关联申请 实体类 非null
     * @return int
     */
    int insertBean(TempAcctLink tempAcctLink);

    /**
     * 分页查询临帐关联相关的业务订单
     *
     * @param tempOrderQuery 条件查询实体
     * @return 返回分页的数据列表
     */
    List<Order> selectTempOrderListPage(TempOrderQuery tempOrderQuery);

    /**
     * 根据业务订单编号查询关联申请
     *
     * @param applyId 申请编号
     * @return obj
     */
    TempAcctLink selectBeanById(@Param("applyId") String applyId);

    /**
     * 修改临帐关联申请
     *
     * @param tempAcctLink 临帐关联申请 实体类 非null
     */
    int updateBean(TempAcctLink tempAcctLink);

    /**
     * 查询临账记录相关联的业务订单
     *
     * @param debitId 临账编号
     * @return list
     */
    List<Order> selectTempOrderByDebitId(String debitId);

    /**
     * 更具查询实体查询临账关联申请
     *
     * @param tempAcctLinkQuery query
     * @return bean
     */
    TempAcctLink selectBeanByQuery(TempAcctLinkQuery tempAcctLinkQuery);

    /**
     * 分页查询临帐关联未复核的业务订单
     *
     * @param tempOrderQuery 查询实体
     * @return list
     */
    List<Order> selectTempTaskListPage(TempOrderQuery tempOrderQuery);

    /**
     * 查询临账关联申请信息详情
     *
     * @param orderNo 业务订单号
     * @param debitId 临账ID
     * @return {@code TempAcctLink}
     */
    TempAcctLink selectBeanDetail(@Param("orderNo") String orderNo, @Param("debitId") String debitId);

    /**
     * 查询单个临账的所有关联申请信息
     *
     * @param debitId 临账ID
     * @return {@link List < TempAcctLink >}
     */
    List<TempAcctLink> selectBeanListByDebitId(@Param("debitId") String debitId);
}
