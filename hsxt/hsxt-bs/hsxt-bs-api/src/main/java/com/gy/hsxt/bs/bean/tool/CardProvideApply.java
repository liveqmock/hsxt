/**
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
* <p>
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
*/
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 互生卡发放申请Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: CardProvideApply
 * @Description:
 * @author: likui
 * @date: 2016/6/23 16:55
 * @company: gyist
 * @version V3.0.0
 */
public class CardProvideApply implements Serializable {

	private static final long serialVersionUID = -8526275207973526743L;

	/** 申请编号 **/
	private String applyId;

	/** 申请订单号 **/
	private String appOrderNo;

	/** 卡数量 **/
	private String cardCount;

	/** 卡价格 **/
	private String cardPrice;

	/** 总价 **/
	private String totalAmount;

	/** 收货人 **/
	private String receiver;

	/** 收货人手机 **/
	private String mobile;

	/** 收货人地址 **/
	private String receiverAddr;

	/** 申请日期 **/
	private String applyDate;

	public CardProvideApply()
	{
		super();
	};

	public CardProvideApply(String applyId, String appOrderNo, String cardCount, String cardPrice, String totalAmount,
			String receiver, String mobile, String receiverAddr, String applyDate)
	{
		super();
		this.applyId = applyId;
		this.appOrderNo = appOrderNo;
		this.cardCount = cardCount;
		this.cardPrice = cardPrice;
		this.totalAmount = totalAmount;
		this.receiver = receiver;
		this.mobile = mobile;
		this.receiverAddr = receiverAddr;
		this.applyDate = applyDate;
	}

	public String getApplyId()
	{
		return applyId;
	}

	public void setApplyId(String applyId)
	{
		this.applyId = applyId;
	}

	public String getAppOrderNo()
	{
		return appOrderNo;
	}

	public void setAppOrderNo(String appOrderNo)
	{
		this.appOrderNo = appOrderNo;
	}

	public String getCardCount()
	{
		return cardCount;
	}

	public void setCardCount(String cardCount)
	{
		this.cardCount = cardCount;
	}

	public String getCardPrice()
	{
		return cardPrice;
	}

	public void setCardPrice(String cardPrice)
	{
		this.cardPrice = cardPrice;
	}

	public String getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getReceiverAddr()
	{
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr)
	{
		this.receiverAddr = receiverAddr;
	}

	public String getApplyDate()
	{
		return applyDate;
	}

	public void setApplyDate(String applyDate)
	{
		this.applyDate = applyDate;
	}

	@Override
	public String toString()
	{
		return JSONObject.toJSONString(this);
	}
}
