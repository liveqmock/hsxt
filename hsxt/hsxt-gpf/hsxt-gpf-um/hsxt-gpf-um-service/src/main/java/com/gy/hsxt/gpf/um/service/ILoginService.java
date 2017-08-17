/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.LoginInfo;
import com.gy.hsxt.gpf.um.bean.Operator;

/**
 * 登录业务层接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : ILoginService
 * @Description : 登录业务层接口
 * @Author : chenm
 * @Date : 2016/1/28 20:30
 * @Version V3.0.0.0
 */
public interface ILoginService {


    /**
     * 登录/签入
     *
     * @param operator 操作员
     * @param checkCode 是否校验验证码
     * @return {@code String}
     * @throws HsException
     */
    LoginInfo signIn(Operator operator,boolean checkCode) throws HsException;


    /**
     * 退出/签出
     *
     * @param token 登录令牌
     * @return {@code boolean}
     * @throws HsException
     */
    boolean signOut(String token) throws HsException;

    /**
     * 前端校验登录令牌
     *
     * @param token 登录令牌
     * @return {@code LoginInfo}
     * @throws HsException
     */
    LoginInfo proofToken(String token) throws HsException;

    /**
     * 存储验证码
     * @param code 验证码
     * @throws HsException
     */
    void storeValidCode(String code) throws HsException;
}
