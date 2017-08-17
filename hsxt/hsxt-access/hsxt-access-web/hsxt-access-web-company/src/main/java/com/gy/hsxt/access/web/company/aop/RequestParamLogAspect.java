/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.aop;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.interceptor.SystemControllerLog;
import com.gy.hsxt.common.constant.RespCode;

/***************************************************************************
 * <PRE>
 * Description 		: 切点类 
 * 
 * Project Name   	: hsxt-access-web-scs
 * 
 * Package Name     : com.gy.hsxt.access.web.scs.aop
 * 
 * File Name        : SystemLogAspect 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-21 下午8:04:20
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-21 下午8:04:20
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/

@Aspect
public class RequestParamLogAspect {

    // Controller层切点
    @Pointcut("execution(* com.gy.hsxt.access.web.scs.controllers.*.*Controller.*(..))")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     * 
     * @param joinPoint
     *            切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        // 参数声明
        Map map = null; // 获取所有的请求
        String methodName = null; // 请求ClassName
        String targetName = null;
        HttpServletRequest request = null; // 求情对象

        try
        {
            // 变量赋值
            targetName = joinPoint.getTarget().getClass().getName(); // 获取class名称
            methodName = joinPoint.getSignature().getName(); // 获取方法名称
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); // 获取当前请求
            map = request.getParameterMap(); // 获取所有的请求参数

            // 获取描述信息
            // description = this.getControllerMethodDescription(joinPoint);

            // 保存请求参数到日志中
            SystemLog.info(targetName, methodName, JSON.toJSONString(map));

        }
        catch (Exception e)
        {
            // 记录本地异常日志
            SystemLog.error("com.gy.hsxt.access.web.person.aop", "doBefore", "Request parameter logging error", e);

        }
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     * 
     * @param joinPoint
     *            切点
     */
    @AfterReturning(pointcut = "controllerAspect()", returning = "rtv")
    public void doAfter(JoinPoint joinPoint, Object rtv) {
        String methodName = null; // 请求ClassName
        String targetName = null;
        HttpRespEnvelope hre = null;
        try
        {
            // 变量赋值

            // 返回参数验证 所有Controlle处理方法出返回结果都一样
            if (rtv != null && rtv instanceof HttpRespEnvelope)
            {

                // 强制转换
                hre = (HttpRespEnvelope) rtv;
                if (hre != null)
                {
                    targetName = joinPoint.getTarget().getClass().getName(); // 获取class名称
                    methodName = joinPoint.getSignature().getName(); // 获取方法名称
                    // 正确放回结果
                    if (hre.getRetCode() == RespCode.AS_OPT_SUCCESS.getCode())
                    {
                        // 返回值记录日志
                        SystemLog.info(targetName, methodName, JSON.toJSONString(hre));
                    }
                    else
                    {
                        // 系统错误日志记录
                        Exception ex = hre.getException();
                        if(ex == null){
                            ex = new Exception();
                        }
                        SystemLog.error(targetName, methodName, JSON.toJSONString(hre), ex);
                    }
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            // 记录本地异常日志
            SystemLog.error("com.gy.hsxt.access.web.person.aop", "doAfter", "Request parameter logging error", e);

        }
        catch (Throwable e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param joinPoint
     *            切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        // 变量声明
        Class[] clazzs = null;
        String description = null;

        // 获取调用类的属性
        String methodName = joinPoint.getSignature().getName();// 获取方法名称
        String targetName = joinPoint.getTarget().getClass().getName();// 获取方法名称

        Object[] arguments = joinPoint.getArgs(); // 参数列表
        Class targetClass = Class.forName(targetName); // 根据类反射类
        Method[] methods = targetClass.getMethods(); // 获取名称

        // 遍历所有方法
        for (Method method : methods)
        {
            // 方法名称是否相等
            if (method.getName().equals(methodName))
            {

                clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length)
                {
                    // 获取描述信息
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     */
    public HttpRespEnvelope adminOptionContent(Object[] args, String mName) throws Exception {

        if (args == null)
        {
            return null;
        }

        // 遍历参数对象
        for (Object info : args)
        {

            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();

            // 遍历方法，判断get方法
            for (Method method : methods)
            {

                String methodName = method.getName();
                if (mName.equals(methodName))
                {
                }
                // 判断是不是get方法
                if (methodName.indexOf("get") == 0)
                {
                    continue;// 不处理
                }

                Object rsValue = null;
                try
                {

                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);

                    // 无返回值进入下次循环
                    if (rsValue == null)
                    {
                        continue;
                    }
                    if (rsValue instanceof HttpRespEnvelope)
                    {
                        return (HttpRespEnvelope) rsValue;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    continue;
                }

            }

        }

        return null;
    }

}
