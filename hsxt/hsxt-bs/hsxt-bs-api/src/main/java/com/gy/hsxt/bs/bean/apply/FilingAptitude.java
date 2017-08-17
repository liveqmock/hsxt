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
 * @ClassName: FilingAptitude
 * @Description: 报备企业资质附件信息
 * 
 * @author: xiaofl
 * @date: 2015-8-31 下午4:55:33
 * @version V1.0
 */
public class FilingAptitude implements Serializable {

    private static final long serialVersionUID = -8167391142155033142L;

    /** 附件编号 */
    private String filingAptId;

    /** 申请编号 */
    private String applyId;

    /** 资质类型 */
    private Integer aptType;

    /** 资质名称 */
    private String aptName;

    /** 资质文件编号 */
    private String fileId;

    /** 创建日期 */
    private String createdDate;

    /** 创建者 */
    private String createdBy;

    /** 修改日期 */
    private String updateDate;

    /** 修改者 */
    private String updatedBy;

    public String getFilingAptId() {
        return filingAptId;
    }

    public void setFilingAptId(String filingAptId) {
        this.filingAptId = filingAptId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getAptType() {
        return aptType;
    }

    public void setAptType(Integer aptType) {
        this.aptType = aptType;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
