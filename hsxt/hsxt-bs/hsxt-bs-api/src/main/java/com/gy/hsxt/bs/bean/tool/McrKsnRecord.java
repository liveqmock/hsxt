/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.Base;

/**
 * 刷卡器KSN记录
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: McrKsnRecord
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月23日 下午6:10:53
 * @company: gyist
 * @version V3.0.0
 */
public class McrKsnRecord extends Base implements Serializable {

	private static final long serialVersionUID = 731390147263840988L;

	/** 记录id **/
	private String recordId;

	/** 批次号 **/
	private String batchNo;

	/** 刷卡器类型 1: 积分刷卡器2: 消费刷卡器 **/
	private Integer mcrType;

	/** 数量 **/
	private Integer quantity;

	/** 入库状态 0:待入库 1:已入库 **/
	private Integer storeStatus;

	public McrKsnRecord()
	{
		super();
	}

	public McrKsnRecord(String recordId, String batchNo, Integer mcrType,
			Integer quantity, Integer storeStatus)
	{
		super();
		this.recordId = recordId;
		this.batchNo = batchNo;
		this.mcrType = mcrType;
		this.quantity = quantity;
		this.storeStatus = storeStatus;
	}

	public String getRecordId()
	{
		return recordId;
	}

	public void setRecordId(String recordId)
	{
		this.recordId = recordId;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public Integer getMcrType()
	{
		return mcrType;
	}

	public void setMcrType(Integer mcrType)
	{
		this.mcrType = mcrType;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public Integer getStoreStatus()
	{
		return storeStatus;
	}

	public void setStoreStatus(Integer storeStatus)
	{
		this.storeStatus = storeStatus;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
