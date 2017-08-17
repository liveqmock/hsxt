/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: FilingShareHolder
 * @Description: 报备企业股东信息
 * 
 * @author: xiaofl
 * @date: 2015-8-31 下午4:56:37
 * @version V1.0
 */
public class FilingShareHolder extends OptInfo implements Serializable {

    private static final long serialVersionUID = 3252085123236097102L;

    /** 报备股东编号 */
    private String filingShId;

    /** 申请编号 */
    private String applyId;

    /** 股东名称 */
    private String shName;

    /** 股东性质 */
    private Integer shType;

    /** 证件类型 */
    private Integer idType;

    /** 证件号码 */
    private String idNo;

    /** 联系电话 */
    private String phone;
    
    public String getFilingShId() {
        return filingShId;
    }

    public void setFilingShId(String filingShId) {
        this.filingShId = filingShId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName;
    }

    public Integer getShType() {
        return shType;
    }

    public void setShType(Integer shType) {
        this.shType = shType;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
