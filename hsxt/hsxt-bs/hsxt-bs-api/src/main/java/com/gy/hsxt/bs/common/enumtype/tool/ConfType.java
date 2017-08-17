/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 配置类型枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: ConfType
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:38:50
 * @company: gyist
 * @version V3.0.0
 */
public enum ConfType {

	/** 申报配置 **/
	APPLY_CONFIG(1),

	/** 新增配置 **/
	ADD_CONFIG(2),

	/** 售后配置**/
	AFTER_CONFIG(3);

	private int code;

	ConfType(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
