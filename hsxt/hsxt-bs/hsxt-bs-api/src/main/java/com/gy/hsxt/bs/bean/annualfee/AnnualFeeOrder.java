/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.annualfee;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.order.Order;

import java.io.Serializable;

/**
 * 年费业务单详情
 *
 * @Package :com.gy.hsxt.bs.bean.annualfee
 * @ClassName : AnnualFeeOrder
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/20 14:57
 * @Version V3.0.0.0
 */
public class AnnualFeeOrder implements Serializable {

    private static final long serialVersionUID = -4679666263809539028L;
    /**
     * 业务订单
     */
    private Order order;

    /**
     * 年费区间-开始时间
     */
    private String areaStartDate;

    /**
     * 年费区间-结束时间
     */
    private String areaEndDate;

    /**
     * 网银支付回调地址
     */
    private String callbackUrl;

    /**
     *快捷支付签约号
     */
    private String bindingNo;

    /**
     * 短信验证码
     */
    private String smsCode;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getAreaStartDate() {
        return areaStartDate;
    }

    public void setAreaStartDate(String areaStartDate) {
        this.areaStartDate = areaStartDate;
    }

    public String getAreaEndDate() {
        return areaEndDate;
    }

    public void setAreaEndDate(String areaEndDate) {
        this.areaEndDate = areaEndDate;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getBindingNo() {
        return bindingNo;
    }

    public void setBindingNo(String bindingNo) {
        this.bindingNo = bindingNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public final Order bulidOrder() {
        Order order = new Order();
        this.setOrder(order);
        return order;
    }

    public static AnnualFeeOrder bulid(Order order) {
        AnnualFeeOrder annualFeeOrder = new AnnualFeeOrder();
        annualFeeOrder.setOrder(order);
        return annualFeeOrder;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
