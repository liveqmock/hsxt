/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.utils;

/**
 * 验证码类型枚举类
 * 
 * @Package: com.gy.hsxt.access.web.utils
 * @ClassName: VaildCodeType
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月29日 上午11:06:22
 * @company: gyist
 * @version V3.0.0
 */
public enum VaildCodeType {

	/** 文书验证码 **/
	DOC_VAILD("docCode"),

	/** 应用办理验证码 **/
	AAP_HANDLE("handleCode"),

	/** 应用查询验证码 **/
	APP_QUERY("queryCode"),

	/** 登录验证码 **/
	LOGIN("loginCode");

	private String code;

	VaildCodeType(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	/**
	 * 根据值查找对象
	 * 
	 * @param code
	 * @return
	 */
	public static VaildCodeType getVaildCodeType(String code)
	{
		VaildCodeType[] vaildCode = VaildCodeType.values();
		for (VaildCodeType vc : vaildCode)
		{
			if (vc.getCode().equals(code))
			{
				return vc;
			}
		}
		return null;
	}
}
