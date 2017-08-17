/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 *
 * Project Name   	: hsxt-access-web-scs 
 *
 * Package Name     : com.gy.hsxt.access.web.scs.interceptor  
 *
 * File Name        : SystemControllerLog 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-10-21 下午7:59:52
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-10-21 下午7:59:52
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/

/**
 * 自定义注解 拦截Controller
 */

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {

    String description() default "";

}