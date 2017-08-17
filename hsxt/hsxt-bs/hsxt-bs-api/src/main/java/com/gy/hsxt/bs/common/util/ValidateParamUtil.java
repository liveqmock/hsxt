/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.util;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 验证参数工具类 (hibernate validator)
 * 
 * @Package: com.gy.hsxt.bs.common.util
 * @ClassName: ValidateParamUtil
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:35:34
 * @company: gyist
 * @version V3.0.0
 */
public class ValidateParamUtil {

	private static Validator validator;

	static
	{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private ValidateParamUtil()
	{
		super();
	}

	/**
	 * 验证参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月18日 下午2:06:01
	 * @param obj
	 *            参数对象
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String validateParam(Object obj)
	{
		StringBuffer sb = new StringBuffer();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		if (null == violations || violations.size() == 0)
		{
			return "";
		}
		for (ConstraintViolation<Object> violation : violations)
		{
			sb.append(violation.getMessage()).append(";");
		}
		return sb.toString();
	}

	/**
	 * 验证集合对象
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月18日 下午2:05:44
	 * @param param
	 *            参数列表
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static <T> String validateListParam(List<T> param)
	{
		if (param == null || param.size() == 0)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Object obj : param)
		{
			Set<ConstraintViolation<Object>> violations = validator.validate(obj);
			if (null == violations || violations.size() == 0)
			{
				sb.append("");
				break;
			}
			for (ConstraintViolation<Object> violation : violations)
			{
				sb.append(violation.getMessage()).append(";");
			}
		}
		return sb.toString();
	}
}
