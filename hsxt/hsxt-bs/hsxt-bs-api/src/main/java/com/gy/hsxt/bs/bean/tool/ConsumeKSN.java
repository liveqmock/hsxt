/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 消费刷卡器KSN码Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ConsumeKSN
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:30:34
 * @company: gyist
 * @version V3.0.0
 */
public class ConsumeKSN implements Serializable {

	private static final long serialVersionUID = -3466122986034299520L;

	/** 设备客户号 **/
	private String deviceCustId;

	/** 设备序列号 **/
	private String deviceSeqNo;

	/** 批次号 **/
	private String batchNo;

	/** 刷卡器代码 **/
	private String consumeCode;

	/** KSN码1 **/
	private String ksnCodeY;

	/** KSN码2 **/
	private String ksnCodeE;

	/** KSN码3 **/
	private String ksnCodeS;

	/** 密文1 **/
	private String ciphertextY;

	/** 密文2 **/
	private String ciphertextE;

	/** 密文3 **/
	private String ciphertextS;

	/** 验证1 **/
	private String vaildY;

	/** 验证2 **/
	private String vaildE;

	/** 验证3 **/
	private String vaildS;

	public ConsumeKSN()
	{
		super();
	}

	public ConsumeKSN(String deviceCustId, String deviceSeqNo, String batchNo,
			String consumeCode, String ksnCodeY, String ksnCodeE,
			String ksnCodeS, String ciphertextY, String ciphertextE,
			String ciphertextS, String vaildY, String vaildE, String vaildS)
	{
		super();
		this.deviceCustId = deviceCustId;
		this.deviceSeqNo = deviceSeqNo;
		this.batchNo = batchNo;
		this.consumeCode = consumeCode;
		this.ksnCodeY = ksnCodeY;
		this.ksnCodeE = ksnCodeE;
		this.ksnCodeS = ksnCodeS;
		this.ciphertextY = ciphertextY;
		this.ciphertextE = ciphertextE;
		this.ciphertextS = ciphertextS;
		this.vaildY = vaildY;
		this.vaildE = vaildE;
		this.vaildS = vaildS;
	}

	public String getDeviceCustId()
	{
		return deviceCustId;
	}

	public void setDeviceCustId(String deviceCustId)
	{
		this.deviceCustId = deviceCustId;
	}

	public String getDeviceSeqNo()
	{
		return deviceSeqNo;
	}

	public void setDeviceSeqNo(String deviceSeqNo)
	{
		this.deviceSeqNo = deviceSeqNo;
	}

	public String getBatchNo()
	{
		return batchNo;
	}

	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	public String getConsumeCode()
	{
		return consumeCode;
	}

	public void setConsumeCode(String consumeCode)
	{
		this.consumeCode = consumeCode;
	}

	public String getKsnCodeY()
	{
		return ksnCodeY;
	}

	public void setKsnCodeY(String ksnCodeY)
	{
		this.ksnCodeY = ksnCodeY;
	}

	public String getKsnCodeE()
	{
		return ksnCodeE;
	}

	public void setKsnCodeE(String ksnCodeE)
	{
		this.ksnCodeE = ksnCodeE;
	}

	public String getKsnCodeS()
	{
		return ksnCodeS;
	}

	public void setKsnCodeS(String ksnCodeS)
	{
		this.ksnCodeS = ksnCodeS;
	}

	public String getCiphertextY()
	{
		return ciphertextY;
	}

	public void setCiphertextY(String ciphertextY)
	{
		this.ciphertextY = ciphertextY;
	}

	public String getCiphertextE()
	{
		return ciphertextE;
	}

	public void setCiphertextE(String ciphertextE)
	{
		this.ciphertextE = ciphertextE;
	}

	public String getCiphertextS()
	{
		return ciphertextS;
	}

	public void setCiphertextS(String ciphertextS)
	{
		this.ciphertextS = ciphertextS;
	}

	public String getVaildY()
	{
		return vaildY;
	}

	public void setVaildY(String vaildY)
	{
		this.vaildY = vaildY;
	}

	public String getVaildE()
	{
		return vaildE;
	}

	public void setVaildE(String vaildE)
	{
		this.vaildE = vaildE;
	}

	public String getVaildS()
	{
		return vaildS;
	}

	public void setVaildS(String vaildS)
	{
		this.vaildS = vaildS;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
