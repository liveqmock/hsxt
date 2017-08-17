/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.ApprBase;

/**
 * 互生卡卡样Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: CardStyle
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:30:10
 * @company: gyist
 * @version V3.0.0
 */
public class CardStyle extends ApprBase implements Serializable {

	private static final long serialVersionUID = 4846866425721707508L;

	/** 卡样id **/
	private String cardStyleId;

	/** 卡样名称 **/
	@NotEmpty(message = "卡样名称不能为空")
	private String cardStyleName;

	/** 制卡样缩略图 **/
	private String microPic;

	/** 制卡样源文件 **/
	private String sourceFile;

	/** 素材卡样缩略图 **/
	private String materialMicroPic;

	/** 素材卡样源文件 **/
	private String materialSourceFile;

	/** 是否个性卡false：标准卡 true：个性卡 **/
	private Boolean isSpecial;

	/** 是否默认false：否 true：是 **/
	private Boolean isDefault;

	/** 平台或托管互生号 **/
	@NotEmpty(message = "平台或托管互生号不能为空")
	private String entResNo;

	/** 平台或托管客户号 **/
	@NotEmpty(message = "平台或托管客户号不能为空")
	private String entCustId;

	/** 订单号 **/
	private String orderNo;

	/** 企业卡样确认文件 **/
	private String confirmFile;

	/** 卡样是否锁定 false：不是 true：是 **/
	private Boolean isLock;

	/** 是否确认 false：不是 true：是 **/
	private Boolean isConfirm;

	/** 启用状态 0未启用，1已启用，2已停用 **/
	private Integer enableStatus;

	/** 审批状态 0申请启用 1同意启用 2拒绝启用 3申请停用 4同意停用 5拒绝停用 **/
	private Integer status;

	public CardStyle()
	{
		super();
	}

	public CardStyle(String microPic, String sourceFile, String materialMicroPic, String materialSourceFile,
			String orderNo)
	{
		super();
		this.microPic = microPic;
		this.sourceFile = sourceFile;
		this.materialMicroPic = materialMicroPic;
		this.materialSourceFile = materialSourceFile;
		this.orderNo = orderNo;
	}

	public CardStyle(String orderNo, String confirmFile, Boolean isLock, Integer enableStatus, Integer status)
	{
		super();
		this.orderNo = orderNo;
		this.confirmFile = confirmFile;
		this.isLock = isLock;
		this.enableStatus = enableStatus;
		this.status = status;
	}

	public CardStyle(String cardStyleId, String cardStyleName, String microPic, String sourceFile, Boolean isSpecial,
			Boolean isDefault, String entResNo, String entCustId, String orderNo, String confirmFile, Boolean isLock,
			Integer enableStatus, Integer status)
	{
		super();
		this.cardStyleId = cardStyleId;
		this.cardStyleName = cardStyleName;
		this.microPic = microPic;
		this.sourceFile = sourceFile;
		this.isSpecial = isSpecial;
		this.isDefault = isDefault;
		this.entResNo = entResNo;
		this.entCustId = entCustId;
		this.orderNo = orderNo;
		this.confirmFile = confirmFile;
		this.isLock = isLock;
		this.enableStatus = enableStatus;
		this.status = status;
	}

	public String getCardStyleId()
	{
		return cardStyleId;
	}

	public void setCardStyleId(String cardStyleId)
	{
		this.cardStyleId = cardStyleId;
	}

	public String getCardStyleName()
	{
		return cardStyleName;
	}

	public void setCardStyleName(String cardStyleName)
	{
		this.cardStyleName = cardStyleName;
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

	public String getMaterialMicroPic()
	{
		return materialMicroPic;
	}

	public void setMaterialMicroPic(String materialMicroPic)
	{
		this.materialMicroPic = materialMicroPic;
	}

	public String getMaterialSourceFile()
	{
		return materialSourceFile;
	}

	public void setMaterialSourceFile(String materialSourceFile)
	{
		this.materialSourceFile = materialSourceFile;
	}

	public Boolean getIsSpecial()
	{
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial)
	{
		this.isSpecial = isSpecial;
	}

	public Boolean getIsDefault()
	{
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getConfirmFile()
	{
		return confirmFile;
	}

	public void setConfirmFile(String confirmFile)
	{
		this.confirmFile = confirmFile;
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

	public Integer getEnableStatus()
	{
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus)
	{
		this.enableStatus = enableStatus;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
