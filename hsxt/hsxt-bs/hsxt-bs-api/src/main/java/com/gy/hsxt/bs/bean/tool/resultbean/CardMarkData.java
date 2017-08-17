/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.Supplier;

/**
 * 卡制作数据Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: CardMarkData
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月19日 下午6:25:25
 * @company: gyist
 * @version V3.0.0
 */
public class CardMarkData implements Serializable {

	private static final long serialVersionUID = -5146009140030309498L;

	/** 订单号 **/
	private String orderNo;

	/** 订单类型 **/
	private String orderType;

	/** 配置单号 **/
	private String confNo;

	/** 最小消费者互生号 **/
	private String minPerResNo;

	/** 最大消费者互生号 **/
	private String maxPerResNo;

	/** 联系人 **/
	private String linkMan;

	/** 联系电话 **/
	private String phone;

	/** 卡样列表 **/
	private List<CardStyle> styles;

	/** 卡样 **/
	private CardStyle style;

	/** 供应商列表 **/
	private List<Supplier> suppliers;

	/** 供应商 **/
	private Supplier supplier;

	/** 供应商id **/
	private String supplierId;

	/** 卡样id **/
	private String cardStyleId;

	/** 批次号 **/
	private String batchNo;

	/** 确认文件 **/
	private String confirmFile;

	public CardMarkData()
	{
		super();
	}

	public CardMarkData(String orderNo, String confNo, String minPerResNo, String maxPerResNo, String linkMan,
			String phone, List<CardStyle> styles, CardStyle style, List<Supplier> suppliers, Supplier supplier)
	{
		super();
		this.orderNo = orderNo;
		this.confNo = confNo;
		this.minPerResNo = minPerResNo;
		this.maxPerResNo = maxPerResNo;
		this.linkMan = linkMan;
		this.phone = phone;
		this.styles = styles;
		this.style = style;
		this.suppliers = suppliers;
		this.supplier = supplier;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getMinPerResNo()
	{
		return minPerResNo;
	}

	public void setMinPerResNo(String minPerResNo)
	{
		this.minPerResNo = minPerResNo;
	}

	public String getMaxPerResNo()
	{
		return maxPerResNo;
	}

	public void setMaxPerResNo(String maxPerResNo)
	{
		this.maxPerResNo = maxPerResNo;
	}

	public String getLinkMan()
	{
		return linkMan;
	}

	public void setLinkMan(String linkMan)
	{
		this.linkMan = linkMan;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public List<CardStyle> getStyles()
	{
		return styles;
	}

	public void setStyles(List<CardStyle> styles)
	{
		this.styles = styles;
	}

	public CardStyle getStyle()
	{
		return style;
	}

	public void setStyle(CardStyle style)
	{
		this.style = style;
	}

	public List<Supplier> getSuppliers()
	{
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers)
	{
		this.suppliers = suppliers;
	}

	public Supplier getSupplier()
	{
		return supplier;
	}

	public void setSupplier(Supplier supplier)
	{
		this.supplier = supplier;
	}

	public String getSupplierId()
	{
		return supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getCardStyleId()
	{
		return cardStyleId;
	}

	public void setCardStyleId(String cardStyleId)
	{
		this.cardStyleId = cardStyleId;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public String getConfirmFile()
	{
		return confirmFile;
	}

	public void setConfirmFile(String confirmFile)
	{
		this.confirmFile = confirmFile;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
