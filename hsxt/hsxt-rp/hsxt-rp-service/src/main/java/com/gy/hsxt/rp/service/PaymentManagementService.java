/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.rp.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.PaymentResult;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.order.OrderPaymentResult;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.constant.GPConstant;
import com.gy.hsxt.rp.api.IRPPaymentManagementService;
import com.gy.hsxt.rp.bean.ExcelExportData;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;
import com.gy.hsxt.rp.common.bean.PageContext;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.mapper.PaymentManagementOrderMapper;
import com.gy.hsxt.rp.util.ExcelUtil;

/**
 * 地区平台收款管理对外接口实现类
 * 
 * @Package: com.gy.hsxt.rp.service
 * @ClassName: PaymentManagementService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-3-1 下午6:24:24
 * @version V3.0.0
 */
@Service
public class PaymentManagementService implements IRPPaymentManagementService {

    /**
     * 支付系统：支付接口
     */
    @Autowired
    private IGPPaymentService gpPaymentService;

    /**
     * 帐户操作系统：兑换互生币接口
     */
    @Autowired
    private IAOExchangeHsbService aoExchangeHsbService;

    /**
     * 业务服务系统：订单接口
     */
    @Autowired
    private IBSOrderService bsOrderService;

    /**
     * 收款管理订单mapper
     */
    @Autowired
    PaymentManagementOrderMapper paymentManagementOrderMapper;

    /**
     * 查询业务订单列表
     * 
     * @param paymentManagementOrder
     *            收款管理订单实体类
     * @param page
     *            分页信息
     * @return 收款管理订单列表
     * @throws HsException
     * @see com.gy.hsxt.rp.api.IRPPaymentManagementService#getPaymentOrderList(com.gy.hsxt.rp.bean.PaymentManagementOrder)
     */
    @Override
    public PageData<PaymentManagementOrder> getPaymentOrderList(PaymentManagementOrder paymentManagementOrder, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "分页查询收款管理业务订单列表,params[" + paymentManagementOrder + "]");
        // 分页数据
        PageData<PaymentManagementOrder> paymentOrderListPage = null;
        // 设置分页信息
        PageContext.setPage(page);

        /**
         * 处理查询条件{
         */
        // 设置开始日期00:00:00结束日期59:59:59
        if (StringUtils.isNotBlank(paymentManagementOrder.getPayStartTime()) && StringUtils.isNotBlank(paymentManagementOrder.getPayEndTime()))
        {
            paymentManagementOrder.setPayQueryDate();
        }
        else
        {
            paymentManagementOrder.setQueryDate();
        }

        // 执行查询
        List<PaymentManagementOrder> paymentListOrder = paymentManagementOrderMapper.findPaymentOrderListPage(paymentManagementOrder);
        if (paymentListOrder != null && paymentListOrder.size() > 0)
        {
            // 为公用分页查询设置查询结果集
            paymentOrderListPage = new PageData<PaymentManagementOrder>(page.getCount(), paymentListOrder);
        }
        return paymentOrderListPage;
    }

    /**
     * 数据对帐
     * 
     * @param orderNos
     *            订单号列表
     * @return 对帐成功列表
     * @throws HsException
     * @see com.gy.hsxt.rp.api.IRPPaymentManagementService#dataReconciliation(java.util.List)
     */
    @Override
    public List<PaymentManagementOrder> dataReconciliation(List<String> orderNos) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "数据对帐,params[" + orderNos + "]");
        List<PaymentManagementOrder> paymentOrderList = null;
        if (orderNos != null)
        {
            paymentOrderList = new ArrayList<PaymentManagementOrder>();
            // 获取商户号
            String merId = PropertyConfigurer.getProperty("mer.id");

            try
            {
                // 调用支付系统支付结果查询接口
                List<PaymentOrderState> orderStateList = gpPaymentService.getPaymentOrderState(merId, orderNos);

                if (orderStateList != null && orderStateList.size() > 0)
                {
                    for (PaymentOrderState pos : orderStateList)
                    {
                        if (GPConstant.PaymentStateCode.PAY_SUCCESS == pos.getTransStatus())// 支付成功
                        {
                            // 如果订单号头两位为13则表示为AO的兑换互生币数据
                            if (pos.getOrderNo().substring(0, 2).equals("13"))
                            {
                                PaymentResult paymentResult = new PaymentResult();
                                BeanUtils.copyProperties(pos, paymentResult);
                                // 更新兑换互生币订单支付状态
                                aoExchangeHsbService.synPayStatus(paymentResult);

                            }
                            // 如果订单号头两位为11则表示为BS的业务订单数据
                            if (pos.getOrderNo().substring(0, 2).equals("11"))
                            {
                                OrderPaymentResult orderPaymentResult = new OrderPaymentResult();
                                BeanUtils.copyProperties(pos, orderPaymentResult);
                                // 更新业务订单支付状态
                                bsOrderService.synPayStatus(orderPaymentResult);
                            }
                            paymentOrderList.add(paymentManagementOrderMapper.findOrderInfo(pos.getOrderNo()));
                        }
                    }
                }
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), RespCode.UNKNOWN.getCode() + "数据对帐异常", e);
                throw new HsException(RespCode.UNKNOWN.getCode(), "数据对帐异常" + e);
            }
        }
        return paymentOrderList;
    }

    /**
     * 查询收款订单详情
     * 
     * @param orderNo
     *            订单编号
     * @return 收款订单详情
     * @throws HsException
     * @see com.gy.hsxt.rp.api.IRPPaymentManagementService#getPaymentOrderInfo(java.lang.String)
     */
    @Override
    public PaymentManagementOrder getPaymentOrderInfo(String orderNo) throws HsException {
        PaymentManagementOrder paymentOrderInfo = null;
        if (StringUtils.isNotBlank(orderNo))
        {
            paymentOrderInfo = paymentManagementOrderMapper.findOrderInfo(orderNo);
        }
        else
        {
            paymentOrderInfo = new PaymentManagementOrder();
        }
        return paymentOrderInfo;
    }

    /**
     * 导出收款管理订单
     * 
     * @param paymentManagementOrder
     *            收款管理订单实体类
     * @return 文件路径
     * @throws HsException
     * @see com.gy.hsxt.rp.api.IRPPaymentManagementService#exportPaymentOrderData(com.gy.hsxt.rp.bean.PaymentManagementOrder)
     */
    @Override
    public String exportPaymentOrderData(PaymentManagementOrder paymentManagementOrder) throws HsException {
        /**
         * 导出最大数量值
         */
        int exportMaxRow = 60000;

        String dirRoot = PropertyConfigurer.getProperty("dir.root");

        String newDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String timeDate = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date());

        //
        String excelFileName = "订单收款记录" + timeDate;

        // 文件地址
        String fileAddress = dirRoot + File.separator + "BS" + File.separator + "EXPORT" + File.separator + newDate + File.separator + excelFileName + ".xls";

        // 导出数据列名
        List<String[]> columNames = new ArrayList<String[]>();
        columNames.add(new String[] { "互生号/手机号", "付款单位", "订单号", "订单日期", "订单类型", "金额", "折合应付本币(人民币)", "收款日期", "支付方式", "银行流水号" });

        // 导出数据对应数据字段
        List<String[]> fieldNames = new ArrayList<String[]>();
        fieldNames.add(new String[] { "hsResNo", "custName", "orderNo", "orderTime", "orderTypeName", "orderAmount", "orderCashAmount", "payTime", "payChannelName", "bankTransNo" });

        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        // 设置开始日期00:00:00结束日期59:59:59
        if (StringUtils.isNotBlank(paymentManagementOrder.getPayStartTime()) && StringUtils.isNotBlank(paymentManagementOrder.getPayEndTime()))
        {
            paymentManagementOrder.setPayQueryDate();
        }
        else
        {
            paymentManagementOrder.setQueryDate();
        }
        try
        {
            // 执行查询
            List<PaymentManagementOrder> paymentListOrder = paymentManagementOrderMapper.findExportPaymentOrderList(paymentManagementOrder);
            if (paymentListOrder != null && paymentListOrder.size() > exportMaxRow)
            {
                // 导出数量上限
                HsAssert.isTrue(true, RespCode.UNKNOWN, "导出Excel超出最大条数");
            }
            map.put("订单收款记录", paymentListOrder);

            ExcelExportData setInfo = new ExcelExportData();
            setInfo.setDataMap(map);
            setInfo.setFieldNames(fieldNames);
            setInfo.setTitles(new String[] { "订单收款记录" });
            setInfo.setColumnNames(columNames);

            ExcelUtil.export2File(setInfo, fileAddress);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), RespCode.UNKNOWN.getCode() + ":导出订单收款记录Excel数据异常,param=" + JSON.toJSONString(paymentManagementOrder), e);
            throw new HsException(RespCode.UNKNOWN.getCode(), "导出订单收款记录Excel数据异常,param=" + JSON.toJSONString(paymentManagementOrder) + "\n" + e);
        }
        return fileAddress;
    }
}
