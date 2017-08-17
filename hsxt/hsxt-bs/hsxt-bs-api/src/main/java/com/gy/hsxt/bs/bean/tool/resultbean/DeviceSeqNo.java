/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 设备序列号Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: PosDeviceSeqNo
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月27日 上午9:05:45
 * @company: gyist
 * @version V3.0.0
 */
public class DeviceSeqNo implements Serializable {

	private static final long serialVersionUID = 674487774310923569L;

	/** 入库批次号 **/
	private String batchNo;

	/** 配置数量 **/
	private Integer configNum;

	/** 序列号 **/
	private List<String> seqNo;

	public DeviceSeqNo()
	{
		super();
	}

	public DeviceSeqNo(String batchNo, Integer configNum, List<String> seqNo)
	{
		super();
		this.batchNo = batchNo;
		this.configNum = configNum;
		this.seqNo = seqNo;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public Integer getConfigNum()
	{
		return configNum;
	}

	public void setConfigNum(Integer configNum)
	{
		this.configNum = configNum;
	}

	public List<String> getSeqNo()
	{
		return seqNo;
	}

	public void setSeqNo(List<String> seqNo)
	{
		this.seqNo = seqNo;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
