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
 * @ClassName: ContractContentInfo
 * @Description: 合同内容
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:44:57
 * @version V1.0
 */
public class ContractContentInfo implements Serializable {

    private static final long serialVersionUID = -7688268578758093979L;

    /** 申报ID **/
    private String applyId;

    /** 客户类型 **/
    private Integer custType;

    /** 盖章状态 **/
    private Integer sealStatus;

    /** 合同模板内容 **/
    private String templetContent;

    /** 合同占位符替换的内容 **/
    private String varContent;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    public String getTempletContent() {
        return templetContent;
    }

    public void setTempletContent(String templetContent) {
        this.templetContent = templetContent;
    }

    public String getVarContent() {
        return varContent;
    }

    public void setVarContent(String varContent) {
        this.varContent = varContent;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
