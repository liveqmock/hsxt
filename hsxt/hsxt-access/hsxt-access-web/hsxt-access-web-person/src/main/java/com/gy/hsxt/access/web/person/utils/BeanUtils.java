package com.gy.hsxt.access.web.person.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName hsxt-access-web-person
 * @package com.gy.hsxt.access.web.person.utils.BeanUtils.java
 * @className BeanUtils
 * @description 一句话描述该类的功能
 * @author LiZhi Peter
 * @createDate 2015-8-14 下午12:12:17
 * @updateUser LiZhi Peter
 * @updateDate 2015-8-14 下午12:12:17
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class BeanUtils {
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(Class clazz, int index)
			throws IndexOutOfBoundsException {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];

	}
}
