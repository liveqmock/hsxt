/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.bean
 * @ClassName: CancelAccountParam
 * @Description: 销户参数
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:21:54
 * @version V1.0
 */
public class CancelAccountParam implements Serializable {

    private static final long serialVersionUID = -6533317858073856838L;

    /** 申请编号 **/
    private String applyId;

    /** 企业客户号 **/
    private String entCustId;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业名称 **/
    private String entCustName;

    /** 销户进度 **/
    private Integer progress;

    /** 操作员客户号 **/
    private String optCustId;

    /** 操作员名称 **/
    private String optCustName;

    /** 转账银行ID **/
    private String bankAcctId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getOptCustName() {
        return optCustName;
    }

    public void setOptCustName(String optCustName) {
        this.optCustName = optCustName;
    }

    public String getBankAcctId() {
        return bankAcctId;
    }

    public void setBankAcctId(String bankAcctId) {
        this.bankAcctId = bankAcctId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
