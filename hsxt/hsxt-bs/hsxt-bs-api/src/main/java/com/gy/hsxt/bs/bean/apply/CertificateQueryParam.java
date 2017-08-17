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
 * @ClassName: CertificateQueryParam 
 * @Description: 证书查询参数
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:33:49 
 * @version V1.0 
 

 */ 
public class CertificateQueryParam implements Serializable {

    private static final long serialVersionUID = -9062711844625458590L;

    /** 企业互生号 **/
    private String entResNo;

    /** 证书编号 **/
    private String certificateNo;

    /** 企业名称 **/
    private String entName;

    /** 打印状态 **/
    private Boolean printStatus;

    /** 发放状态 **/
    private Boolean sendStatus;

    /** 印章状态 **/
    private Integer sealStatus;

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public Boolean getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(Boolean printStatus) {
        this.printStatus = printStatus;
    }

    public Boolean getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Boolean sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
