/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 互生卡信息Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: CardInfo
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:29:53
 * @company: gyist
 * @version V3.0.0
 */
public class CardInfo implements Serializable {

	private static final long serialVersionUID = -6360433259158052535L;

	/** 卡号(消费者互生号+暗码) **/
	private String cardId;

	/** 配置单号 **/
	private String confNo;

	/** 消费者互生号 **/
	private String perResNo;

	/** 暗码 **/
	private String darkCode;

	/** 初始化密码 **/
	private String initPwd;

	public CardInfo()
	{
		super();
	}

	public CardInfo(String cardId, String confNo, String perResNo,
			String darkCode, String initPwd)
	{
		super();
		this.cardId = cardId;
		this.confNo = confNo;
		this.perResNo = perResNo;
		this.darkCode = darkCode;
		this.initPwd = initPwd;
	}

	public String getCardId()
	{
		return cardId;
	}

	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public String getPerResNo()
	{
		return perResNo;
	}

	public void setPerResNo(String perResNo)
	{
		this.perResNo = perResNo;
	}

	public String getDarkCode()
	{
		return darkCode;
	}

	public void setDarkCode(String darkCode)
	{
		this.darkCode = darkCode;
	}

	public String getInitPwd()
	{
		return initPwd;
	}

	public void setInitPwd(String initPwd)
	{
		this.initPwd = initPwd;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
