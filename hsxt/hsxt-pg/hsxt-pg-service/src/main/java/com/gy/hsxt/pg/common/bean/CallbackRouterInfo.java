/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.bean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.bean
 * 
 *  File Name       : CallbackRouterInfo.java
 * 
 *  Creation Date   : 2015年9月24日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 银联回调路由信息
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class CallbackRouterInfo {

	/** 业务类型：具体定义参见com.gy.hsxt.pg.constant.PGConstant.ChannelProvider **/
	private String channelProvider;

	/** 业务类型：具体定义参见com.gy.hsxt.pg.constant.PGConstant.PayChannel **/
	private Integer payChannel;

	/** 商户类型：具体定义参见com.gy.hsxt.pg.constant.PGConstant.MerType **/
	private Integer merType;

	/** 业务类型：具体定义参见com.gy.hsxt.pg.common.constant.Constant.PGCallbackBizType **/
	private Integer bizType;

	/** 其他自定义：由调用方处理,只能是包含数字和字母的字符串, 不能是其他字符, 最大不能超过16字符 **/
	private String customizeType;

	public CallbackRouterInfo(String channelProvider, Integer payChannel,
			Integer merType, Integer bizType) {
		this(channelProvider, payChannel, merType, bizType, "");
	}

	public CallbackRouterInfo(String channelProvider, Integer payChannel,
			Integer merType, Integer bizType, String customizeType) {
		this.setChannelProvider(channelProvider);
		this.setPayChannel(payChannel);
		this.setMerType(merType);
		this.setBizType(bizType);
		this.setCustomizeType(customizeType);
	}

	public String getChannelProvider() {
		return channelProvider;
	}

	public void setChannelProvider(String channelProvider) {
		this.channelProvider = channelProvider;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public Integer getMerType() {
		return merType;
	}

	public void setMerType(Integer merType) {
		this.merType = merType;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getCustomizeType() {
		return customizeType;
	}

	public void setCustomizeType(String customizeType) {
		this.customizeType = customizeType;
	}

}
