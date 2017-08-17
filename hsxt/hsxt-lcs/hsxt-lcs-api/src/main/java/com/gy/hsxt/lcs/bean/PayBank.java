/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.lcs.bean;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 银联支付列表
 * 
 * @Package: com.gy.hsxt.lcs.bean
 * @ClassName: PayBank
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-11-18 上午9:37:46
 * @version V1.0
 */
public class PayBank implements java.io.Serializable {

    private static final long serialVersionUID = -7550698254472931491L;

    /** 银行简码 */
    private String bankCode;

    /** 银行名称 */
    private String bankName;

    /** 支持卡种 */
    private String supportCard;

    /** 是否支持借记卡 */
    private boolean supportDebit;

    /** 是否支持贷记卡 */
    private boolean supportCredit;

    /**
     * @return the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode
     *            the bankCode to set
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName
     *            the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the supportCard
     */
    public String getSupportCard() {
        return supportCard;
    }

    /**
     * @param supportCard
     *            the supportCard to set
     */
    public void setSupportCard(String supportCard) {
        this.supportCard = supportCard;
    }

    public boolean isSupportDebit() {
        return supportDebit;
    }

    public void setSupportDebit(boolean supportDebit) {
        this.supportDebit = supportDebit;
    }

    public boolean isSupportCredit() {
        return supportCredit;
    }

    public void setSupportCredit(boolean supportCredit) {
        this.supportCredit = supportCredit;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
