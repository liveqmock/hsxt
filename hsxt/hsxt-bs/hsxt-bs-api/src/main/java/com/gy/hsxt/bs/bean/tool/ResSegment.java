
/**
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 * <p>
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 企业资源段实体bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ResSegment
 * @Description:
 * @author: likui
 * @date: 2016/6/14 17:45
 * @company: gyist
 * @version V3.0.0
 */
public class ResSegment implements Serializable {

	private static final long serialVersionUID = 8717037646306144560L;

	/** 段id **/
	private String segmentId;

	/** 客户号 **/
	private String entCustId;

	/** 代购订单号 **/
	private String proxyOrderNo;

	/** 配置单 **/
	private String confNo;

	/** 第几段 **/
	private int segmentCount;

	/** 段描述 **/
	private String segmentDesc;

	/** 状态 1:已申购,2:已下单3:待购买 **/
	private String status;

	/** 段价格 **/
	private String segmentPrice;

	/** 卡数量 **/
	private String cardCount;

	/** 卡数量 **/
	private String startResNo;

	/** 卡数量 **/
	private String endResNo;

	public ResSegment()
	{
		super();
	}

	public ResSegment(String segmentId, String entCustId, String confNo, int segmentCount, String segmentDesc,
			String status, String segmentPrice, String cardCount, String startResNo, String endResNo)
	{
		this.segmentId = segmentId;
		this.entCustId = entCustId;
		this.confNo = confNo;
		this.segmentCount = segmentCount;
		this.segmentDesc = segmentDesc;
		this.status = status;
		this.segmentPrice = segmentPrice;
		this.cardCount = cardCount;
		this.startResNo = startResNo;
		this.endResNo = endResNo;
	}

	public ResSegment(String segmentId, String entCustId, int segmentCount, String segmentDesc, String status,
			String segmentPrice, String cardCount, String startResNo, String endResNo)
	{
		super();
		this.segmentId = segmentId;
		this.entCustId = entCustId;
		this.segmentCount = segmentCount;
		this.segmentDesc = segmentDesc;
		this.status = status;
		this.segmentPrice = segmentPrice;
		this.cardCount = cardCount;
		this.startResNo = startResNo;
		this.endResNo = endResNo;
	}

	public String getSegmentId()
	{
		return segmentId;
	}

	public void setSegmentId(String segmentId)
	{
		this.segmentId = segmentId;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getProxyOrderNo()
	{
		return proxyOrderNo;
	}

	public void setProxyOrderNo(String proxyOrderNo)
	{
		this.proxyOrderNo = proxyOrderNo;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
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

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
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

	public String getStartResNo()
	{
		return startResNo;
	}

	public void setStartResNo(String startResNo)
	{
		this.startResNo = startResNo;
	}

	public String getEndResNo()
	{
		return endResNo;
	}

	public void setEndResNo(String endResNo)
	{
		this.endResNo = endResNo;
	}

	@Override
	public String toString()
	{
		return JSONObject.toJSONString(this);
	}
}
