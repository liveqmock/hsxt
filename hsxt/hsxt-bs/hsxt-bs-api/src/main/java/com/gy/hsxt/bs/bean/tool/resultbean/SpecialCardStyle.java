/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 个性卡样Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: SpecialCardStyle
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月7日 下午3:46:44
 * @company: gyist
 * @version V3.0.0
 */
public class SpecialCardStyle implements Serializable {

	private static final long serialVersionUID = -396010413032880098L;

	/** 卡样id **/
	private String cardStyleId;

	/** 订单号 **/
	private String orderNo;

	/** 客户号 **/
	private String custId;

	/** 互生号 **/
	private String entResNo;

	/** 客户名称 **/
	private String custName;

	/** 卡样费用 **/
	private String cardStyleFee;

	/** 订单支付状态 **/
	private Integer payStatus;

	/** 卡样是否锁定 false：不是 true：是 **/
	private Boolean isLock;

	/** 是否确认 false：不是 true：是 **/
	private Boolean isConfirm;

	/** 设计附件 **/
	private String mSourceFile;

	/** 制卡样缩略图 **/
	private String microPic;

	/** 制卡样源文件 **/
	private String sourceFile;

	/** 企业卡样确认文件 **/
	private String confirmFile;

	/** 卡样名称 **/
	private String cardStyleName;

	/** 申请时间 **/
	private String reqTime;

	/** 订单备注 **/
	private String orderRemark;

	public String getCardStyleId()
	{
		return cardStyleId;
	}

	public void setCardStyleId(String cardStyleId)
	{
		this.cardStyleId = cardStyleId;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getCustId()
	{
		return custId;
	}

	public void setCustId(String custId)
	{
		this.custId = custId;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public String getCardStyleFee()
	{
		return cardStyleFee;
	}

	public void setCardStyleFee(String cardStyleFee)
	{
		this.cardStyleFee = cardStyleFee;
	}

	public Integer getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(Integer payStatus)
	{
		this.payStatus = payStatus;
	}

	public Boolean getIsLock()
	{
		return isLock;
	}

	public void setIsLock(Boolean isLock)
	{
		this.isLock = isLock;
	}

	public Boolean getIsConfirm()
	{
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm)
	{
		this.isConfirm = isConfirm;
	}

	public String getmSourceFile()
	{
		return mSourceFile;
	}

	public void setmSourceFile(String mSourceFile)
	{
		this.mSourceFile = mSourceFile;
	}

	public String getMicroPic()
	{
		return microPic;
	}

	public void setMicroPic(String microPic)
	{
		this.microPic = microPic;
	}

	public String getSourceFile()
	{
		return sourceFile;
	}

	public void setSourceFile(String sourceFile)
	{
		this.sourceFile = sourceFile;
	}

	public String getConfirmFile()
	{
		return confirmFile;
	}

	public void setConfirmFile(String confirmFile)
	{
		this.confirmFile = confirmFile;
	}

	public String getCardStyleName()
	{
		return cardStyleName;
	}

	public void setCardStyleName(String cardStyleName)
	{
		this.cardStyleName = cardStyleName;
	}

	public String getReqTime()
	{
		return reqTime;
	}

	public void setReqTime(String reqTime)
	{
		this.reqTime = reqTime;
	}

	public String getOrderRemark()
	{
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark)
	{
		this.orderRemark = orderRemark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
