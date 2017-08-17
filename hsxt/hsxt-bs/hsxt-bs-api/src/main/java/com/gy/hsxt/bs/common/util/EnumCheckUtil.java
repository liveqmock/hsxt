/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.util;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.gy.hsxt.common.constant.PayChannel;

/**
 * 枚举检查工具类型
 * 
 * @Package: com.gy.hsxt.bs.common.util
 * @ClassName: EnumCheck
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月16日 上午10:33:57
 * @company: gyist
 * @version V3.0.0
 */
public class EnumCheckUtil implements Serializable {

	private static final long serialVersionUID = 8742882547284132644L;

	private EnumCheckUtil()
	{
		super();
	}

	/**
	 * 判断枚举类是否包含值
	 * 
	 * 枚举类不包含属性或包含属性名未code
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月1日 上午10:43:39
	 * @param clazz
	 *            枚举类
	 * @param obj
	 *            枚举值
	 * @return
	 * @return : boolean true(包含),false(不包含)
	 * @version V3.0.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean checkEnumIncludeValue(Class clazz, Object obj)
	{
		return getEnumByCode(clazz, obj) != null;
	}

	/**
	 * 根据枚举值获取枚举项
	 * 
	 * 枚举类不包含属性或包含属性名未code
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月1日 上午10:49:47
	 * @param clazz
	 * @param obj
	 * @return
	 * @return : T
	 * @version V3.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T getEnumByCode(Class<T> clazz, Object obj)
	{
		Method method = null;
		try
		{
			Object[] ems = clazz.getEnumConstants();
			method = clazz.getMethod("getCode");
			if (null != method)
			{
				for (Object em : ems)
				{
					if (obj.toString().equals(method.invoke(em).toString()))
					{
						return (T) em;
					}
				}
			}
		} catch (Exception ex)
		{
			try
			{
				return (T) Enum.valueOf(clazz, obj.toString());
			} catch (Exception e)
			{
				return null;
			}
		}
		return null;
	}

	public static void main(String[] args)
	{
		System.out.println(String.valueOf(EnumCheckUtil.getEnumByCode(PayChannel.class, 100).name().toUpperCase()));
	}
}
