/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.mapper;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrderQuery;
import com.gy.hsxt.bs.bean.order.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年费业务订单DAO接口
 *
 * @Package :com.gy.hsxt.bs.annualfee.mapper
 * @ClassName : AnnualFeeOrderMapper
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/11 10:38
 * @Version V3.0.0.0
 */
@Repository
public interface AnnualFeeOrderMapper {

    /**
     * 保存年费业务订单
     *
     * @param order 年费业务订单
     * @return int
     */
    int insertAnnualFeeOrder(Order order);

    /**
     * 根据客户号查询未付款的年费业务订单编号
     *
     * @param annualFeeOrderQuery 查询实体
     * @return orderNo
     */
    Order selectOrderForWaitPay(AnnualFeeOrderQuery annualFeeOrderQuery);

    /**
     * 更新订单的金额信息
     *
     * @param order 业务单
     * @return int
     */
    int updateAnnualFeeOrderForAmount(Order order);

    /**
     * 根据业务订单编号查询订单
     *
     * @param orderNo 订单编号
     * @return order
     */
    Order selectBeanById(@Param("orderNo") String orderNo);

    /**
     * 支付完成后修改年费业务单
     *
     * @param order 年费业务单
     * @return int
     */
    int updateBeanForPaid(Order order);

    /**
     * 分页查询年费业务单
     *
     * @param annualFeeOrderQuery 查询实体
     * @return list
     */
    List<Order> selectBeanListPage(AnnualFeeOrderQuery annualFeeOrderQuery);

    /**
     * 条件查询年费业务单列表
     *
     * @param annualFeeOrderQuery 查询实体
     * @return list
     */
    List<Order> selectListByQuery(AnnualFeeOrderQuery annualFeeOrderQuery);

}


