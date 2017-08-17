/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.common;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @Package :com.gy.hsxt.ps.common
 * @ClassName : ValidateModelUtil
 * @Description : 验证model根据hibernate validator
 * @Author : gup.pengfei
 * @Date : 2015/11/3 15:52
 * @Version V3.0.0.0
 */
public class ValidateModelUtil
{

	private static Validator validator; // 它是线程安全的

	static
	{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 
	 * @Description: validateModel(验证model)
	 * @return String 返回类型
	 * @param obj
	 */
	public static String validateModel(Object obj) throws HsException
	{
		StringBuffer buffer = new StringBuffer();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		if (violations.size() == 0)
		{
			return Constants.SUCCESS_FLAG;
		} else
		{
			for (ConstraintViolation<Object> violation : violations)
			{
				buffer.append(violation.getPropertyPath() + ":" + violation.getMessage()).append(";");
			}
			new EsException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), buffer.toString());
			return Constants.FAIL_FLAG;
		}
	}

}
