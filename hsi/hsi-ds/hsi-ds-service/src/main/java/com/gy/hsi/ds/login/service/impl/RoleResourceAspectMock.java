package com.gy.hsi.ds.login.service.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsi.ds.common.thirds.ub.common.log.AopLogFactory;

/**
 * 判断用户是否具有请求方法的访问权
 */
@Aspect
public class RoleResourceAspectMock {
    protected static final Logger LOG = AopLogFactory.getLogger(RoleResourceAspectMock.class);

    @Pointcut(value = "execution(public * *(..))")
    public void anyPublicMethod() {
    }

    /**
     * 判断当前用户对访问的方法是否有权限
     *
     * @param pjp            方法
     * @param requestMapping 方法上的annotation
     *
     * @return
     *
     * @throws Throwable
     */
    @Around("anyPublicMethod() && @annotation(requestMapping)")
    public Object decideAccess(ProceedingJoinPoint pjp, RequestMapping requestMapping) throws Throwable {

        Object rtnOb = null;

        try {
            // 执行方法
            rtnOb = pjp.proceed();
        } catch (Throwable t) {
            LOG.info(t.getMessage());
            throw t;
        }

        return rtnOb;
    }

}
