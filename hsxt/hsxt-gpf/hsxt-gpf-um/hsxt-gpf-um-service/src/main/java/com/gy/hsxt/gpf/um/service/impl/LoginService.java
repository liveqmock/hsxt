/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.api.IUMLoginService;
import com.gy.hsxt.gpf.um.bean.LoginInfo;
import com.gy.hsxt.gpf.um.bean.Operator;
import com.gy.hsxt.gpf.um.bean.OperatorQuery;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.manager.SessionTokenManager;
import com.gy.hsxt.gpf.um.service.ILoginService;
import com.gy.hsxt.gpf.um.service.IOperatorService;
import com.gy.hsxt.gpf.um.utils.UmUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登录业务层接口实现
 *
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : LoginService
 * @Description : 登录业务层接口实现
 * @Author : chenm
 * @Date : 2016/1/29 9:29
 * @Version V3.0.0.0
 */
@Service
public class LoginService implements ILoginService, IUMLoginService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(LoginService.class);

    /**
     * 操作员业务接口
     */
    @Resource
    private IOperatorService operatorService;

    /**
     * 会话令牌与登录信息管理中心
     */
    @Resource
    private SessionTokenManager sessionTokenManager;

    /**
     * 登录/签入
     *
     * @param operator  操作员
     * @param checkCode 是否校验验证码
     * @return {@code String}
     * @throws HsException
     */
    @Override
    public LoginInfo signIn(Operator operator, boolean checkCode) throws HsException {
        logger.info("========登录/签入的操作员[operator]:{}========", operator);
        HsAssert.notNull(operator, UMRespCode.UM_PARAM_NULL_ERROR, "登录/签入的操作员[operator]为null");
        HsAssert.hasText(operator.getLoginUser(), UMRespCode.UM_PARAM_EMPTY_ERROR, "登录用户名为空");
        HsAssert.hasText(operator.getLoginPwd(), UMRespCode.UM_PARAM_EMPTY_ERROR, "登录密码为空");

        if (checkCode) {
            HsAssert.isTrue(sessionTokenManager.checkCode(operator.getValidCode()), UMRespCode.UM_LOGIN_VALID_CODE_ERROR, "验证码过期或错误");
        }

        Operator operInDB = operatorService.queryBeanByQuery(OperatorQuery.bulid(operator));
        //校验用户名
        HsAssert.notNull(operInDB, UMRespCode.UM_LOGIN_USER_NOT_EXIST, "登录用户名不存在");
        //设置操作员ID
        operator.setOperatorId(operInDB.getOperatorId());
        //形成加密的密码
        String password = UmUtils.generatePwd(operator);
        //校验密码
        HsAssert.isTrue(operInDB.getLoginPwd().equals(password), UMRespCode.UM_LOGIN_PWD_ERROR, "登录密码错误");
        //验证成功之后，形成token
        String token = UmUtils.getToken(operInDB);
        //将登录信息放入登录信息管理中心
        LoginInfo loginInfo = LoginInfo.bulid(operInDB, token);
        //将token和登录信息放入管理中心
        sessionTokenManager.putToken(token, loginInfo);

        return loginInfo;
    }

    /**
     * 退出/签出
     *
     * @param token 登录令牌
     * @return {@code boolean}
     * @throws HsException
     */
    @Override
    public boolean signOut(String token) throws HsException {
        logger.info("======== 退出/签出的Token为:{}========", token);
        sessionTokenManager.removeTokenAndLoginInfo(token);
        return true;
    }

    /**
     * 校验登录令牌
     *
     * @param token 令牌
     * @return {@code boolean}
     * @throws HsException
     */
    @Override
    public boolean checkToken(String token) throws HsException {
        logger.info("======== 校验登录令牌的Token为:{}========", token);
        return sessionTokenManager.checkToken(token);
    }

    /**
     * 前端校验登录令牌
     *
     * @param token 登录令牌
     * @return {@code LoginInfo}
     * @throws HsException
     */
    @Override
    public LoginInfo proofToken(String token) throws HsException {
        logger.info("========前端校验登录令牌的Token为:{}========", token);
        //校验token
        boolean success = sessionTokenManager.checkToken(token);
        //返回登录信息
        return success ? sessionTokenManager.getLoginInfo(token) : null;
    }

    /**
     * 存储验证码
     *
     * @param code 验证码
     * @throws HsException
     */
    @Override
    public void storeValidCode(String code) throws HsException {
        sessionTokenManager.putCode(code);
    }
}
