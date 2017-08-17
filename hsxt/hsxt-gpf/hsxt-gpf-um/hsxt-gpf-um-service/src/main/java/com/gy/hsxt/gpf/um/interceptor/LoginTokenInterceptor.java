/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.interceptor;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.manager.SessionTokenManager;

import javax.annotation.Resource;

/**
 * 登录令牌拦截器
 *
 * @Package : com.gy.hsxt.gpf.um.interceptor
 * @ClassName : LoginTokenInterceptor
 * @Description : 登录令牌拦截器
 * @Author : chenm
 * @Date : 2016/1/30 15:43
 * @Version V3.0.0.0
 */
public class LoginTokenInterceptor extends AbstractLoginTokeInterceptor {


    /**
     * 令牌管理中心
     */
    @Resource
    private SessionTokenManager sessionTokenManager;


    /**
     * 校验登录令牌
     *
     * @param token 令牌
     * @return {@code boolean}
     * @throws HsException
     */
    @Override
    protected boolean checkToken(String token) throws HsException {
        return sessionTokenManager.checkToken(token);
    }

}
