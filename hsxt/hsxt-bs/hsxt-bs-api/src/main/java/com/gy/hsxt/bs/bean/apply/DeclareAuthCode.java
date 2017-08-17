/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: DeclareAuthCode
 * @Description: 申报企业授权码信息
 * 
 * @author: xiaofl
 * @date: 2015-9-1 下午4:52:15
 * @version V1.0
 */
public class DeclareAuthCode implements Serializable {

    private static final long serialVersionUID = 733435067885354314L;

    /** 申请编号 */
    private String applyId;

    /** 申报单状态 **/
    private Integer appStatus;

    /** 企业互生号 */
    private String entResNo;

    /** 企业名称 */
    private String entName;

    /** 联系人 */
    private String linkman;

    /** 联系人手机 */
    private String linkmanMobile;

    /** 授权码 */
    private String authCode;

    /** 授权码有效期 */
    private String expireDate;

    /**
     * 授权码已发送次数
     */
    private int sendTimes;

    /**
     * 授权码最后发送时间
     */
    private String sendLastTime;

    /**
     * 适用资源类型
     * @see com.gy.hsxt.bs.common.enumtype.apply.ResType
     */
    private Integer toBuyResRange;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(int sendTimes) {
        this.sendTimes = sendTimes;
    }

    public String getSendLastTime() {
        return sendLastTime;
    }

    public void setSendLastTime(String sendLastTime) {
        this.sendLastTime = sendLastTime;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public Integer getToBuyResRange() {
        return toBuyResRange;
    }

    public void setToBuyResRange(Integer toBuyResRange) {
        this.toBuyResRange = toBuyResRange;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
