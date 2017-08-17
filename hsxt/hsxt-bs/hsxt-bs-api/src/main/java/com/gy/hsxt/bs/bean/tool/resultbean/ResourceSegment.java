/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 资源段实体bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: ResourceSegment
 * @Description:
 * @author: likui
 * @date: 2016年4月14日 下午7:25:24
 * @company: gyist
 * @version V3.0.0
 */
public class ResourceSegment implements Serializable {

	private static final long serialVersionUID = 8717037646306155560L;

	/** 第几段 **/
	private int segmentCount;

	/** 段描述 **/
	private String segmentDesc;

	/** 购买状态 true:已购买 false:未购买 **/
	private boolean buyStatus;

	/** 段价格 **/
	private String segmentPrice;

	/** 卡数量 **/
	private String cardCount;

	public ResourceSegment()
	{
		super();
	}

	public ResourceSegment(int segmentCount, String segmentDesc, boolean buyStatus, String segmentPrice,
			String cardCount)
	{
		super();
		this.segmentCount = segmentCount;
		this.segmentDesc = segmentDesc;
		this.buyStatus = buyStatus;
		this.segmentPrice = segmentPrice;
		this.cardCount = cardCount;
	}

	public int getSegmentCount()
	{
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount)
	{
		this.segmentCount = segmentCount;
	}

	public String getSegmentDesc()
	{
		return segmentDesc;
	}

	public void setSegmentDesc(String segmentDesc)
	{
		this.segmentDesc = segmentDesc;
	}

	public boolean isBuyStatus()
	{
		return buyStatus;
	}

	public void setBuyStatus(boolean buyStatus)
	{
		this.buyStatus = buyStatus;
	}

	public String getSegmentPrice()
	{
		return segmentPrice;
	}

	public void setSegmentPrice(String segmentPrice)
	{
		this.segmentPrice = segmentPrice;
	}

	public String getCardCount()
	{
		return cardCount;
	}

	public void setCardCount(String cardCount)
	{
		this.cardCount = cardCount;
	}

	@Override
	public String toString()
	{
		return JSONObject.toJSONString(this);
	}
}
