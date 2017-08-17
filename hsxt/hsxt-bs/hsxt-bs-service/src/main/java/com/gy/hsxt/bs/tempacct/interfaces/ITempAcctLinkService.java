/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.interfaces;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tempacct.TempOrderQuery;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 临账关联业务接口
 *
 * @Package :com.gy.hsxt.bs.tempacct.interfaces
 * @ClassName : ITempAcctLinkService
 * @Description : 临账关联业务接口
 * @Author : chenm
 * @Date : 2015/12/21 15:49
 * @Version V3.0.0.0
 */
public interface ITempAcctLinkService extends IBaseService<TempAcctLink> {

    /**
     * 分页查询临帐关联相关的业务订单
     *
     * @param page           分页对象
     * @param tempOrderQuery 查询对象
     * @return list
     * @throws HsException
     */
    List<Order> queryTempOrderListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException;

    /**
     * 查询临账记录相关联的业务订单
     *
     * @param debitId 临账编号
     * @return list
     * @throws HsException
     */
    List<Order> queryTempOrderByDebitId(String debitId) throws HsException;

    /**
     * 根据订单编号查询对应的未审核临账关联申请
     *
     * @param orderNo 订单编号
     * @return bean
     * @throws HsException
     */
    TempAcctLink queryBeanByOrderNo(String orderNo) throws HsException;

    /**
     * 分页查询临帐关联未复核的业务订单
     *
     * @param page           分页对象 非null
     * @param tempOrderQuery 条件查询实体
     * @return 返回分页的数据列表
     * @throws HsException
     */
    List<Order> queryTempTaskListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException;

    /**
     * 查询临账关联申请信息详情
     *
     * @param orderNo 业务订单号
     * @param debitId 临账ID
     * @return {@code TempAcctLink}
     * @throws HsException
     */
    TempAcctLink queryBeanDetail(String orderNo, String debitId) throws HsException;

    /**
     * 查询单个临账的所有关联申请信息
     *
     * @param debitId 临账ID
     * @return {@link List < TempAcctLink >}
     * @throws HsException
     */
    List<TempAcctLink> queryBeanListByDebitId(String debitId) throws HsException;

    /**
     * 查询临账成功支付的订单详情
     *
     * @param orderNo 业务订单编号
     * @return {@code Order}
     * @throws HsException
     */
    Order queryTempSuccessOrderByOrderNo(String orderNo) throws HsException;
}