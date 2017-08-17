/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.manager;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.LoginInfo;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 会话令牌与登录信息管理中心
 *
 * @Package : com.gy.hsxt.gpf.um.manager
 * @ClassName : SystemSessionManager
 * @Description : 会话令牌与登录信息管理中心
 * @Author : chenm
 * @Date : 2016/1/30 9:58
 * @Version V3.0.0.0
 */
@Service
public class SessionTokenManager {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(SessionTokenManager.class);

    /**
     * 令牌失效时长 单位为毫秒
     */
    @Value("${token.expire.time}")
    private long tokenExpireTime;

    /**
     * 验证码失效时长 单位为毫秒
     */
    @Value("${code.expire.time}")
    private long codeExpireTime;

    /**
     * 验证码前缀
     */
    private static final String UM_VALID_CODE_KEY = "um_valid_code_key_";

    /**
     * redis缓存
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 保存/更新会话令牌
     *
     * @param token 令牌
     */
    public void putToken(String token, LoginInfo loginInfo) {
        logger.debug("=====添加token[{}]，loginInfo[{}]======", token, loginInfo);
        HsAssert.notNull(loginInfo, UMRespCode.UM_PARAM_NULL_ERROR, "保存令牌时，登录信息为空");
        BoundValueOperations<String, String> tokenOperations = stringRedisTemplate.boundValueOps(token);
        tokenOperations.set(loginInfo.toString(), tokenExpireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 校验会话令牌有效性
     *
     * @param token 令牌
     * @return 有效性
     */
    public boolean checkToken(String token) {
        logger.debug("=====校验token[{}]======", token);
        if (StringUtils.isBlank(token)) return false;//未登录
        //获取操作
        BoundValueOperations<String, String> tokenOperations = stringRedisTemplate.boundValueOps(token);
        if (StringUtils.isNotEmpty(tokenOperations.get())) {//令牌有效
            //更新时间
            tokenOperations.expire(tokenExpireTime, TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }

    /**
     * 移除会话令牌和登录信息
     *
     * @param token 令牌
     */
    public void removeTokenAndLoginInfo(String token) {
        logger.debug("=====移除token[{}]======", token);
        //获取操作
        BoundValueOperations<String, String> tokenOperations = stringRedisTemplate.boundValueOps(token);
        tokenOperations.expire(0, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取登录信息
     *
     * @param token 登录令牌
     * @return {@code LoginInfo}
     */
    public LoginInfo getLoginInfo(String token) {
        //获取操作
        BoundValueOperations<String, String> tokenOperations = stringRedisTemplate.boundValueOps(token);
        HsAssert.hasText(tokenOperations.get(), UMRespCode.UM_LOGIN_TOKEN_EXPIRE, "令牌已失效");
        //返回登录信息
        return JSON.parseObject(tokenOperations.get(), LoginInfo.class);
    }

    /**
     * 添加验证码
     *
     * @param code 验证码
     */
    public void putCode(String code) {
        BoundValueOperations<String, String> valueOperations = stringRedisTemplate.boundValueOps(UM_VALID_CODE_KEY + code);
        valueOperations.set(code + System.currentTimeMillis(), codeExpireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @return {@code boolean}
     */
    public boolean checkCode(String code) {
        BoundValueOperations<String, String> valueOperations = stringRedisTemplate.boundValueOps(UM_VALID_CODE_KEY + code);
        if (StringUtils.isNotEmpty(valueOperations.get())) {
            valueOperations.expire(0, TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }
}
