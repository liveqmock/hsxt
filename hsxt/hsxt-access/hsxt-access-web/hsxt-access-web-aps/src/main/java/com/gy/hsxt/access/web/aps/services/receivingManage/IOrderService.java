/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.receivingManage;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.reveivingManage.ApsOrderColoseBean;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkDebit;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***
 * 订单接口类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.receivingManage
 * @ClassName: IOrderService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-11 下午12:08:56
 * @version V1.0
 */
public interface IOrderService extends IBaseService {

    /**
     * 业务订单分页(包括 网银支付兑换互生币、货币支付兑换互生币)
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> businessAllOrderPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 根据订单号查找明细
     * 
     * @param orderNo
     * @return
     * @throws HsException
     */
    public PaymentManagementOrder getOrderDetail(String orderNo) throws HsException;

    /**
     * 关闭订单
     * 
     * @param aocb
     */
    public void closeOrder(ApsOrderColoseBean aocb)throws HsException;

    /**
     * 分页查询年费业务单
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> queryAnnualFeeOrderForPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 获取年费订单详情
     * 
     * @param orderNo
     * @return
     * @throws HsException
     */
    public AnnualFeeOrder queryAnnualFeeOrder(String orderNo) throws HsException;
    
    /**
     * 申报欠款单分页查询
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> queryDebtOrder(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 业务订单导出
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public String exportBusinessOrder(Map filterMap) throws HsException;
    
    /***
     * 获取临帐支付详情
     * @param orderNo
     * @return
     */
    public Order getTempAcctPayInfo(String orderNo);
    
    /***
     * 查询操作员信息
     * @param orderCustType 类型
     * @param orderOperator 操作员编号
     * @return
     */
    public AsOperator findOrderOperator(String orderCustType, String orderOperator);
}
