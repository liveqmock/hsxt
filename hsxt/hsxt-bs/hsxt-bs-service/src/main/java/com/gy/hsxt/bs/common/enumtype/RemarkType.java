/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype;

/**
 * 重发互生卡号码类型枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: RemarkType
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月5日 下午2:37:12
 * @company: gyist
 * @version V3.0.0
 */
public enum RemarkType {

	/** 个人补卡 **/
	PERSON_REMARK(1),
	
	/** 企业重做卡 **/
	ENT_REMARK(2);
		
	private int code;

	RemarkType(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
}
