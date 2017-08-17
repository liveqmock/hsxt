/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** 
 * 
 * @Package: com.gy.hsxt.bs.apply.bean  
 * @ClassName: CertificateVar 
 * @Description: 证书替换内容
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:44:36 
 * @version V1.0 
 

 */ 
public class CertificateVar implements Serializable {

    private static final long serialVersionUID = -4135149297039892256L;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业客户号 **/
    private String entCustId;

    /** 企业名称 **/
    private String entCustName;

    /** 企业客户类型 **/
    private Integer custType;

    /** 资源类型： 1首段资源 2创业资源 3全部资源 备注：适用于托管企业 **/
    private Integer resType;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String mobile;

    /** 申报ID **/
    private String applyId;

    /** 模板占位符的替换内容 **/
    private String varContent;

    /** 模板ID **/
    private String templetId;

    /** 模板类型 **/
    private Integer templetType;

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getVarContent() {
        return varContent;
    }

    public void setVarContent(String varContent) {
        this.varContent = varContent;
    }

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    public Integer getTempletType() {
        return templetType;
    }

    public void setTempletType(Integer templetType) {
        this.templetType = templetType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
