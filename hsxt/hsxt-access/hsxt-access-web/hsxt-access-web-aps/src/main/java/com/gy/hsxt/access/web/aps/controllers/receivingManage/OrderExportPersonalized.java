/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.receivingManage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;

/***
 * 订单记录导出
 * @Package: com.gy.hsxt.access.web.aps.controllers.receivingManage
 * @ClassName: OrderExportPersonalized
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-2-15 下午3:13:04
 * @version V1.0
 */
public class OrderExportPersonalized implements Serializable {
    private static final long serialVersionUID = 3123907828665597262L;

    /**
     * 订单收款记录
     */
    public List<PaymentManagementOrder> ocList;

    /**
     * 总行数
     */
    public int columnNum;

    /**
     * 列数
     */
    public int cellNum = 10;

    /**
     * 订单类型
     */
    public static Map<String, String> orderTypeMap = new HashMap<String, String>();
    static
    {
        orderTypeMap.put("100", "缴纳系统使用年费");
        orderTypeMap.put("101", "系统资源费");
        orderTypeMap.put("102", "兑换互生币");
        orderTypeMap.put("103", "新增申购工具");
        orderTypeMap.put("104", "售后工具维修费");
        orderTypeMap.put("105", "补办互生卡");
        orderTypeMap.put("106", "互生卡重新制作");
        orderTypeMap.put("107", "个性卡定制服务费");
        orderTypeMap.put("108", "缴纳积分预付款");
        orderTypeMap.put("109", "申报申购工具");
        orderTypeMap.put("110", "系统资源申购");
    }

    /**
     * 支付方式
     */
    public static Map<Integer, String> payChannel = new HashMap<Integer, String>();
    static
    {
        payChannel.put(100, "网银支付");
        payChannel.put(101, "手机网银支付");
        payChannel.put(102, "快捷支付");
        payChannel.put(200, "互生币支付");
        payChannel.put(300, "货币账户支付");
        payChannel.put(400, "转账汇款");
    }

    /**
     * 内容列
     */
    public Object[][] content;

    public OrderExportPersonalized() {

    }

    public OrderExportPersonalized(List<PaymentManagementOrder> _ocList) throws HsException {
        this.ocList = _ocList;

        this.initContent();
    }

    /**
     * 列标题
     */
    public String[] cellTitle = new String[] { "互生号", "付款单位", "订单号", "订单日期", " 订单类型", "金额", "折合应付本币(人民币)", "收款日期",
            "支付方式","银行流水号" };

    /**
     * 初始化导出内容
     * 
     * @throws HsException
     */
    void initContent() throws HsException {
        int num = 0;            //计数
        double orderAmount;     // 订单金额
        double exchangeRate=1d; // 折扣率
        
        //初始化列数、总行数
        this.columnNum = this.ocList.size();
        this.content = new Object[this.columnNum][this.cellNum];
        
        for (PaymentManagementOrder pmo : ocList)
        {
            // 折扣率
            if (!StringUtils.isEmpty(pmo.getExchangeRate())){
                exchangeRate = DoubleUtil.parseDouble(pmo.getExchangeRate());
            } else {
                exchangeRate = 1d;
            }
            
            if (null != pmo.getPayChannel() && pmo.getPayChannel() == 200) { // 互生币支付
                orderAmount = DoubleUtil.parseDouble(pmo.getOrderHsbAmount());
            } else {//货币支付
                orderAmount = DoubleUtil.parseDouble(pmo.getOrderCashAmount());
            }
            
            content[num][0] = pmo.getHsResNo();
            content[num][1] = pmo.getCustName();
            content[num][2] = pmo.getOrderNo();
            content[num][3] = pmo.getOrderTime();
            content[num][4] = orderTypeMap.get(pmo.getOrderType());
            content[num][7] = pmo.getPayTime();
            content[num][8] = payChannel.get(pmo.getPayChannel());
            content[num][9] = pmo.getBankTransNo();
            content[num][5] = CommonUtils.moneyFormat(orderAmount);
            content[num][6] = CommonUtils.moneyFormat(orderAmount * exchangeRate); // 折合人民币
            
            //计数
            num++;
        }
    }
}
