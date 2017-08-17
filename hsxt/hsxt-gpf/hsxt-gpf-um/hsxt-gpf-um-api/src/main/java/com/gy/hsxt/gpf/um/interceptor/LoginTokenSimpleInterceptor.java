/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.interceptor;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.api.IUMLoginService;

/**
 * 校验登录令牌的通用拦截器
 * @Package : com.gy.hsxt.gpf.um.interceptor
 * @ClassName : LoginTokenSimpleInterceptor
 * @Description : 校验登录令牌的通用拦截器
 * @Author : chenm
 * @Date : 2016/2/17 16:52
 * @Version V3.0.0.0
 */
public class LoginTokenSimpleInterceptor extends AbstractLoginTokeInterceptor {

    /**
     * 登录校验接口
     */
    private IUMLoginService loginService;

    /**
     * 含参构造函数
     * @param loginService 登录校验接口实现
     */
    public LoginTokenSimpleInterceptor(IUMLoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 校验登录令牌
     *
     * @param token 令牌
     * @return {@code boolean}
     * @throws HsException
     */
    @Override
    protected boolean checkToken(String token) throws HsException {
        return loginService.checkToken(token);
    }
}
