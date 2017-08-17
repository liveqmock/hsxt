/**
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 * <p>
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 工具跑批参数Bean
 * 
 * @Package: com.gy.hsxt.bs.tool.bean
 * @ClassName: ToolBatch
 * @Description:
 * @author: likui
 * @date: 2016/6/16 15:30
 * @company: gyist
 * @version V3.0.0
 */
public class ToolBatch implements Serializable {

	private static final long serialVersionUID = 8810723400578070501L;

	/** 签收天数 **/
	private int signDays;

	/** 返回结果 **/
	private Integer result;

	public ToolBatch()
	{
		super();
	};

	public ToolBatch(int signDays, Integer result)
	{
		super();
		this.signDays = signDays;
		this.result = result;
	}

	public int getSignDays()
	{
		return signDays;
	}

	public void setSignDays(int signDays)
	{
		this.signDays = signDays;
	}

	public Integer getResult()
	{
		return result;
	}

	public void setResult(Integer result)
	{
		this.result = result;
	}

	@Override
	public String toString()
	{
		return JSONObject.toJSONString(this);
	}
}
