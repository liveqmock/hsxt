/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.bean.safetySet;

import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsResetTradePwdConsumer;

/***
 * 交易密码重置实体类
 * 
 * @Package: com.gy.hsxt.access.web.person.bean.safetySet
 * @ClassName: DealPwdReset
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-9 下午6:15:15
 * @version V1.0
 */
public class DealPwdReset extends DealPwdResetCheck {
    private static final long serialVersionUID = -4843254691822594503L;

    /**
     * 实名验证返回的token
     */
    private String checkResultToken;

    /**
     * 新交易密码
     */
    private String tradePwd;

    /**
     * 确认交易密码
     */
    private String tradePwdRe;

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getTradePwdRe() {
        return tradePwdRe;
    }

    public void setTradePwdRe(String tradePwdRe) {
        this.tradePwdRe = tradePwdRe;
    }

    /**
     * 验证交易密码数据
     * 
     * @return
     */
    public void checkData() throws HsException {

        RequestUtil.verifyParamsIsNotEmpty(
                new Object[]{this.tradePwd,RespCode.AS_PARAM_INVALID},
                new Object[]{this.tradePwdRe,RespCode.AS_PARAM_INVALID},
                new Object[]{this.checkResultToken,RespCode.AS_PARAM_INVALID},
                new Object[]{super.getPwdToken(),RespCode.AS_PARAM_INVALID}
                );
      
        // 验证两次新密码相等
        if (!this.tradePwd.equals(this.tradePwdRe))
        {
            throw new HsException(RespCode.PW_TRADE_PWD_NOT_EQUALS);
        }
    }

    /**
     * 创建交易密码重置参数类
     * 
     * @return
     */
    public AsResetTradePwdConsumer createARTPC() {
        AsResetTradePwdConsumer artpc = new AsResetTradePwdConsumer();
        artpc.setCustId(super.getCustId());
        artpc.setRandom(this.checkResultToken);
        artpc.setNewTraderPwd(this.tradePwd);
        artpc.setSecretKey(super.getPwdToken());
        artpc.setCerType("1");
        artpc.setCerNo(super.getIdNumber());
        artpc.setUserType(UserTypeEnum.CARDER.getType());
        return artpc;
    }

    /**
     * @return the checkResultToken
     */
    public String getCheckResultToken() {
        return checkResultToken;
    }

    /**
     * @param checkResultToken
     *            the checkResultToken to set
     */
    public void setCheckResultToken(String checkResultToken) {
        this.checkResultToken = checkResultToken;
    }
}
