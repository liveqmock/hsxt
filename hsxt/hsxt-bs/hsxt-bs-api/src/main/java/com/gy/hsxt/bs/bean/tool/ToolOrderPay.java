/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * 工具订单支付Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ToolOrderPay
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月17日 下午12:14:48
 * @company: gyist
 * @version V3.0.0
 */
public class ToolOrderPay implements Serializable {

	private static final long serialVersionUID = -8475121477914686957L;

	/** 订单号 **/
	@NotEmpty(message = "订单号不能为空")
	private String orderNo;

	/** 支付方式 **/
	@NotNull(message = "支付方式不能为空")
	private Integer payChannel;

	/** 回调跳转地址 **/
	private String jumpUrl;

	/** 快捷支付签约号 **/
	private String bindingNo;

	/** 快捷支付短信验证码 **/
	private String smsCode;

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public Integer getPayChannel()
	{
		return payChannel;
	}

	public void setPayChannel(Integer payChannel)
	{
		this.payChannel = payChannel;
	}

	public String getJumpUrl()
	{
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl)
	{
		this.jumpUrl = jumpUrl;
	}

	public String getBindingNo()
	{
		return bindingNo;
	}

	public void setBindingNo(String bindingNo)
	{
		this.bindingNo = bindingNo;
	}

	public String getSmsCode()
	{
		return smsCode;
	}

	public void setSmsCode(String smsCode)
	{
		this.smsCode = smsCode;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
