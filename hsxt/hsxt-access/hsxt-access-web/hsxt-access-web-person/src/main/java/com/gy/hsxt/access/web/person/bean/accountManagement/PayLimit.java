/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.bean.accountManagement;

import java.io.Serializable;

import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.bean.AbstractPersonBase;
import com.gy.hsxt.common.exception.HsException;

/***
 * 支付限额bean
 * 
 * @Package: com.gy.hsxt.access.web.person.bean.accountManagement
 * @ClassName: PayLimit
 * @Description: TODO 支付限额设置参数实体类
 * 
 * @author: wangjg
 * @date: 2016-4-15 下午2:36:11
 * @version V1.0
 */
public class PayLimit extends AbstractPersonBase implements Serializable {
    /**
     * 单笔支付限额开关
     */
    private String singleQuotaReSwitch;

    /**
     * 单笔支付限额
     */
    private String singleQuotaRe;

    /**
     * 每日支付限额开关
     */
    private String dayQuotaReSwitch;

    /**
     * 每日支付限额
     */
    private String dayQuota;

    /**
     * 交易密码
     */
    private String tradePwd;

    /**
     * 验证码
     */
    private String smsCode;

    /**
     * 验证空数据
     */
    public void checkEmpty() throws HsException {
        RequestUtil.checkParamIsNotEmpty(
                new Object[] { this.singleQuotaReSwitch, ASRespCode.PW_BUY_HSB_SINGLE_QUOTA_SWITCH_NULL },      //单笔支付限额开关
                new Object[] { this.dayQuotaReSwitch, ASRespCode.PW_BUY_HSB_DAY_QUOTA_SWITCH_NULL },            //每日支付限额开关
                new Object[] { this.tradePwd,  ASRespCode.PW_TRADEPWD_INVALID },                                //交易密码
                new Object[] { this.smsCode,  ASRespCode.VERIFICATION_CODE_INVALID }                            //验证码
                );

        // 开启单笔支付限额，验证支付额度格式
        if (this.singleQuotaReSwitch == "Y") {
            RequestUtil.verifyPositiveInteger(this.singleQuotaRe, ASRespCode.PW_BUY_HSB_SINGLE_QUOTA_ERROR);
        }

        // 开启每日支付限额，验证支付额度格式
        if (this.dayQuotaReSwitch == "Y") {
            RequestUtil.verifyPositiveInteger(this.dayQuota, ASRespCode.PW_BUY_HSB_DAY_QUOTA_ERROR);
        }
    }

    /**
     * @return the 单笔支付限额开关
     */
    public String getSingleQuotaReSwitch() {
        return singleQuotaReSwitch;
    }

    /**
     * @param 单笔支付限额开关
     *            the singleQuotaReSwitch to set
     */
    public void setSingleQuotaReSwitch(String singleQuotaReSwitch) {
        this.singleQuotaReSwitch = singleQuotaReSwitch;
    }

    /**
     * @return the 单笔支付限额
     */
    public String getSingleQuotaRe() {
        return singleQuotaRe;
    }

    /**
     * @param 单笔支付限额
     *            the singleQuotaRe to set
     */
    public void setSingleQuotaRe(String singleQuotaRe) {
        this.singleQuotaRe = singleQuotaRe;
    }

    /**
     * @return the 每日支付限额开关
     */
    public String getDayQuotaReSwitch() {
        return dayQuotaReSwitch;
    }

    /**
     * @param 每日支付限额开关
     *            the dayQuotaReSwitch to set
     */
    public void setDayQuotaReSwitch(String dayQuotaReSwitch) {
        this.dayQuotaReSwitch = dayQuotaReSwitch;
    }

    /**
     * @return the 每日支付限额
     */
    public String getDayQuota() {
        return dayQuota;
    }

    /**
     * @param 每日支付限额
     *            the dayQuota to set
     */
    public void setDayQuota(String dayQuota) {
        this.dayQuota = dayQuota;
    }

    /**
     * @return the 交易密码
     */
    public String getTradePwd() {
        return tradePwd;
    }

    /**
     * @param 交易密码
     *            the tradePwd to set
     */
    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    /**
     * @return the 验证码
     */
    public String getSmsCode() {
        return smsCode;
    }

    /**
     * @param 验证码
     *            the smsCode to set
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
