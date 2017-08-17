/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.rp.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 收款管理订单实体类
 * 
 * @Package: com.gy.hsxt.rp.bean
 * @ClassName: PaymentManagementOrder
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-3-1 下午4:00:14
 * @version V3.0.0
 */
public class PaymentManagementOrder implements Serializable {

	private static final long serialVersionUID = -1178084759393652213L;

	/** 订单编号 **/
	private String orderNo;

	/** 客户号 **/
	private String custId;

	/** 互生号 **/
	private String hsResNo;

	/** 客户名称 **/
	private String custName;

	/** 客户类型 **/
	private Integer custType;

	/** 原业务编号 **/
	private String bizNo;

	/** 订单类型 **/
	private String orderType;

	/** 订单数量 **/
	private Integer quantity;

	/** 订单单位 **/
	private String orderUnit;

	/** 订单原始金额 **/
	private String orderOriginalAmount;

	/** 订单减免金额 **/
	private String orderDerateAmount;

	/** 订单货币金额 **/
	private String orderCashAmount;

	/** 订单货币币种 **/
	private String currencyCode;

	/** 订单互生币金额 **/
	private String orderHsbAmount;

	/** 货币转换比率 **/
	private String exchangeRate;

	/** 订单备注 **/
	private String orderRemark;

	/** 订单时间 **/
	private String orderTime;

	/** 订单渠道来源(受理方式) **/
	private Integer orderChannel;

	/** 订单操作员 **/
	private String orderOperator;

	/** 支付超时时间 **/
	private String payOvertime;

	/** 订单状态 **/
	private Integer orderStatus;

	/** 支付状态 **/
	private Integer payStatus;

	/** 支付方式 **/
	private Integer payChannel;

	/** 支付时间 **/
	private String payTime;

	/** 完成时间 **/
	private String finishdDate;

	/** 银行流水号 **/
	private String bankTransNo;

	/** 开始日期 **/
	private String startDate;

	/** 截止时期 **/
	private String endDate;

	/** 自定义查询条件 **/
	private String customWhere;

	/** 未支付状态 **/
	private String unPayStatus;

	/**
	 * 订单类型名称
	 */
	private String orderTypeName;

	/**
	 * 支付方式名称
	 */
	private String payChannelName;

	/**
	 * 金额
	 */
	private String orderAmount;

	/**
	 * 付款开始时间
	 */
	private String payStartTime;

	/**
	 * 付款结束时间
	 */
	private String payEndTime;

	public String getPayStartTime() {
		return payStartTime;
	}

	public void setPayStartTime(String payStartTime) {
		this.payStartTime = payStartTime;
	}

	public String getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(String payEndTime) {
		this.payEndTime = payEndTime;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getPayChannelName() {
		return payChannelName;
	}

	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}

	public String getUnPayStatus() {
		return unPayStatus;
	}

	public void setUnPayStatus(String unPayStatus) {
		this.unPayStatus = unPayStatus;
	}

	public String getBankTransNo() {
		return bankTransNo;
	}

	public void setBankTransNo(String bankTransNo) {
		this.bankTransNo = bankTransNo;
	}

	public String getCustomWhere() {
		return customWhere;
	}

	public void setCustomWhere(String customWhere) {
		this.customWhere = customWhere;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFinishdDate() {
		return finishdDate;
	}

	public void setFinishdDate(String finishdDate) {
		this.finishdDate = finishdDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getHsResNo() {
		return hsResNo;
	}

	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}

	public String getOrderOriginalAmount() {
		return orderOriginalAmount;
	}

	public void setOrderOriginalAmount(String orderOriginalAmount) {
		this.orderOriginalAmount = orderOriginalAmount;
	}

	public String getOrderDerateAmount() {
		return orderDerateAmount;
	}

	public void setOrderDerateAmount(String orderDerateAmount) {
		this.orderDerateAmount = orderDerateAmount;
	}

	public String getOrderCashAmount() {
		return orderCashAmount;
	}

	public void setOrderCashAmount(String orderCashAmount) {
		this.orderCashAmount = orderCashAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrderHsbAmount() {
		return orderHsbAmount;
	}

	public void setOrderHsbAmount(String orderHsbAmount) {
		this.orderHsbAmount = orderHsbAmount;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(Integer orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getOrderOperator() {
		return orderOperator;
	}

	public void setOrderOperator(String orderOperator) {
		this.orderOperator = orderOperator;
	}

	public String getPayOvertime() {
		return payOvertime;
	}

	public void setPayOvertime(String payOvertime) {
		this.payOvertime = payOvertime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	/**
	 * 设置查询的日期,开始日期为最小,结束日期为最大
	 */
	public void setQueryDate() {
		if (StringUtils.isNotBlank(getStartDate())) {
			setStartDate(DateUtil.getMinDateOfDayStr(getStartDate()));
		}
		if (StringUtils.isNotBlank(getEndDate())) {
			setEndDate(DateUtil.getMaxDateOfDayStr(getEndDate()));
		}
	}

	/**
	 * 设置支付查询的日期,开始日期为最小,结束日期为最大
	 */
	public void setPayQueryDate() {
		if (StringUtils.isNotBlank(getPayStartTime())) {
			setPayStartTime(DateUtil.getMinDateOfDayStr(getPayStartTime()));
		}
		if (StringUtils.isNotBlank(getPayEndTime())) {
			setPayEndTime(DateUtil.getMaxDateOfDayStr(getPayEndTime()));
		}
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
