/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.tool.resultbean.EntInfo;

/**
 * 发货单Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: Shipping
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:32:51
 * @company: gyist
 * @version V3.0.0
 */
public class Shipping implements Serializable {

	private static final long serialVersionUID = 2373119502645561451L;

	/** 发货单号 **/
	private String shippingId;

	/** 发货单类型1：申报工具发货 2：新增工具发货 3：售后工具发货 **/
	private Integer shippingType;

	/** 互生号 **/
	@NotEmpty(message = "互生号不能为空")
	private String hsResNo;

	/** 客户号 **/
	@NotEmpty(message = "客户号不能为空")
	private String custId;
	/** 客户名称 **/

	@NotEmpty(message = "客户名称不能为空")
	private String custName;

	/** 客户类型 1:持卡人2:成员3:托管 **/
	@NotNull(message = "客户类型不能为空")
	private Integer custType;

	/** 收货人 **/
	@NotEmpty(message = "收货人不能为空")
	private String receiver;

	/** 收货地址 **/
	@NotEmpty(message = "收货地址不能为空")
	private String receiverAddr;

	/** 邮编 **/
	private String postCode;

	/** 联系手机 **/
	@NotEmpty(message = "联系手机不能为空")
	private String mobile;

	/** 配送方式名称 **/
	private String smName;

	/** 快递编号 **/
	private String trackingNo;

	/** 快递费用 **/
	private String shippingFee;

	/** 发货状态 1：已发货 2：已签收 **/
	private Integer deliveryStatus;

	/** 发货人 **/
	@NotEmpty(message = "发货人 不能为空")
	private String consignor;

	/** 发货时间 **/
	private String deliveryTime;

	/** 发货说明 **/
	private String deliveryDesc;

	/** 签收人 * */
	private String signPeople;

	/** 签收时间 **/
	private String signTime;

	/** 签收说明 **/
	private String signDesc;

	/** 添加赠品和配套物品,查询时订单所有工具配置清单 **/
	private List<ToolConfig> configs;

	/** 主产品的配置清单编号 **/
	@NotNull(message = "主产品的配置清单编号不能为空")
	private String[] confNos;

	/** 售后服务清单 **/
	private List<AfterServiceDetail> details;

	/** 申报发货单包含的企业列表 **/
	private List<EntInfo> applyEntInfo;

	public Shipping()
	{
		super();
	}

	public Shipping(String shippingId, Integer shippingType, String hsResNo, String custId, String custName,
			Integer custType, String receiver, String receiverAddr, String postCode, String mobile, String smName,
			String trackingNo, String shippingFee, Integer deliveryStatus, String consignor, String deliveryTime,
			String deliveryDesc, String signPeople, String signTime, String signDesc, List<ToolConfig> configs,
			String[] confNos)
	{
		super();
		this.shippingId = shippingId;
		this.shippingType = shippingType;
		this.hsResNo = hsResNo;
		this.custId = custId;
		this.custName = custName;
		this.custType = custType;
		this.receiver = receiver;
		this.receiverAddr = receiverAddr;
		this.postCode = postCode;
		this.mobile = mobile;
		this.smName = smName;
		this.trackingNo = trackingNo;
		this.shippingFee = shippingFee;
		this.deliveryStatus = deliveryStatus;
		this.consignor = consignor;
		this.deliveryTime = deliveryTime;
		this.deliveryDesc = deliveryDesc;
		this.signPeople = signPeople;
		this.signTime = signTime;
		this.signDesc = signDesc;
		this.configs = configs;
		this.confNos = confNos;
	}

	public String getShippingId()
	{
		return shippingId;
	}

	public void setShippingId(String shippingId)
	{
		this.shippingId = shippingId;
	}

	public Integer getShippingType()
	{
		return shippingType;
	}

	public void setShippingType(Integer shippingType)
	{
		this.shippingType = shippingType;
	}

	public String getHsResNo()
	{
		return hsResNo;
	}

	public void setHsResNo(String hsResNo)
	{
		this.hsResNo = hsResNo;
	}

	public String getCustId()
	{
		return custId;
	}

	public void setCustId(String custId)
	{
		this.custId = custId;
	}

	public String getCustName()
	{
		return custName;
	}

	public void setCustName(String custName)
	{
		this.custName = custName;
	}

	public Integer getCustType()
	{
		return custType;
	}

	public void setCustType(Integer custType)
	{
		this.custType = custType;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public String getReceiverAddr()
	{
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr)
	{
		this.receiverAddr = receiverAddr;
	}

	public String getPostCode()
	{
		return postCode;
	}

	public void setPostCode(String postCode)
	{
		this.postCode = postCode;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getSmName()
	{
		return smName;
	}

	public void setSmName(String smName)
	{
		this.smName = smName;
	}

	public String getTrackingNo()
	{
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo)
	{
		this.trackingNo = trackingNo;
	}

	public String getShippingFee()
	{
		return shippingFee;
	}

	public void setShippingFee(String shippingFee)
	{
		this.shippingFee = shippingFee;
	}

	public Integer getDeliveryStatus()
	{
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus)
	{
		this.deliveryStatus = deliveryStatus;
	}

	public String getConsignor()
	{
		return consignor;
	}

	public void setConsignor(String consignor)
	{
		this.consignor = consignor;
	}

	public String getDeliveryTime()
	{
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime)
	{
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryDesc()
	{
		return deliveryDesc;
	}

	public void setDeliveryDesc(String deliveryDesc)
	{
		this.deliveryDesc = deliveryDesc;
	}

	public String getSignPeople()
	{
		return signPeople;
	}

	public void setSignPeople(String signPeople)
	{
		this.signPeople = signPeople;
	}

	public String getSignTime()
	{
		return signTime;
	}

	public void setSignTime(String signTime)
	{
		this.signTime = signTime;
	}

	public String getSignDesc()
	{
		return signDesc;
	}

	public void setSignDesc(String signDesc)
	{
		this.signDesc = signDesc;
	}

	public List<ToolConfig> getConfigs()
	{
		return configs;
	}

	public void setConfigs(List<ToolConfig> configs)
	{
		this.configs = configs;
	}

	public String[] getConfNos()
	{
		return confNos;
	}

	public void setConfNos(String[] confNos)
	{
		this.confNos = confNos;
	}

	public List<AfterServiceDetail> getDetails()
	{
		return details;
	}

	public void setDetails(List<AfterServiceDetail> details)
	{
		this.details = details;
	}

	public List<EntInfo> getApplyEntInfo()
	{
		return applyEntInfo;
	}

	public void setApplyEntInfo(List<EntInfo> applyEntInfo)
	{
		this.applyEntInfo = applyEntInfo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
