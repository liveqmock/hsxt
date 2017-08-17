/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 工具类别Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ToolCategory
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:33:41
 * @company: gyist
 * @version V3.0.0
 */
public class ToolCategory implements Serializable {

	private static final long serialVersionUID = -4829228653995889191L;

	/**
	 * 工具代码 P_POS：POS机 POINT_MCR：积分刷卡器 CONSUME_MCR：消费刷卡器 TABLET：互生平板 P_CARD：互生卡
	 * NORMAL：普通工具，不需配置 GIFT：赠品，不需配置 SUPPORT：配套产品，不需配置
	 */
	private String categoryCode;

	/** 工具名称 **/
	private String categoryName;

	/** * 购买限制规则 0不可购买 1仅成员企业可 2仅托管企业可购买 3托管和成员企业可购买 4 不限制购买 **/
	private Integer buyRules;

	/** 单个客户购买总数限制 **/
	private Integer totalLimit;

	/** 是否需要配置 0：否 1：是 **/
	private Integer isNeedConfig;

	/** 备注 **/
	private String remark;

	public ToolCategory()
	{
		super();
	}

	public ToolCategory(String categoryCode, String categoryName,
			Integer buyRules, Integer totalLimit, Integer isNeedConfig,
			String remark)
	{
		super();
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.buyRules = buyRules;
		this.totalLimit = totalLimit;
		this.isNeedConfig = isNeedConfig;
		this.remark = remark;
	}

	public String getCategoryCode()
	{
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	public Integer getBuyRules()
	{
		return buyRules;
	}

	public void setBuyRules(Integer buyRules)
	{
		this.buyRules = buyRules;
	}

	public Integer getTotalLimit()
	{
		return totalLimit;
	}

	public void setTotalLimit(Integer totalLimit)
	{
		this.totalLimit = totalLimit;
	}

	public Integer getIsNeedConfig()
	{
		return isNeedConfig;
	}

	public void setIsNeedConfig(Integer isNeedConfig)
	{
		this.isNeedConfig = isNeedConfig;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
