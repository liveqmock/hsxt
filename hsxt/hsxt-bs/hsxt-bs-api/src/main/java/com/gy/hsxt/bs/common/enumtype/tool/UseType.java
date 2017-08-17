/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 使用类型
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: UseType
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月26日 下午4:30:26
 * @company: gyist
 * @version V3.0.0
 */
public enum UseType {

	
	/** 领用 **/
	USED(1), 
	
	/** 报损 **/
	REPORTED(2);

	private int code;

	UseType(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
