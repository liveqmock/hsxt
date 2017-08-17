/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.common.enumtype.apply.CerTempletTag;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: ContractContent.java
 * @Description: 证书内容
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午12:15:25
 * @version V1.0
 */
public class CertificateContent implements Serializable {

    private static final long serialVersionUID = -8777432353544024394L;

    /** 申报ID **/
    private String applyId;

    /** 盖章状态 **/
    private Integer sealStatus;

    /** 印章url **/
    private String sealFileUrl;

    /** 证书背景图片文件ID **/
    private String tempPicId;

    /** 证书模板文件ID **/
    private String tempFileId;

    /** 证书占位符内容 **/
    private String varContent;

    /** 证书类型 **/
    private int certificateType;

    /** 企业互生号 **/
    private String entResNo;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    public String getSealFileUrl() {
        return sealFileUrl;
    }

    public void setSealFileUrl(String sealFileUrl) {
        this.sealFileUrl = sealFileUrl;
    }

    public int getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(int certificateType) {
        this.certificateType = certificateType;
    }

    public String getTempPicId() {
        return tempPicId;
    }

    public void setTempPicId(String tempPicId) {
        this.tempPicId = tempPicId;
    }

    public String getTempFileId() {
        return tempFileId;
    }

    public void setTempFileId(String tempFileId) {
        this.tempFileId = tempFileId;
    }

    public String getVarContent() {
        return varContent;
    }

    public void setVarContent(String varContent) {
        this.varContent = varContent;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
