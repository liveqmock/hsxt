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
 * @ClassName: AcceptStatus
 * @Description: 意向客户受理状态信息
 *
 * @author: xiaofl
 * @date: 2015-8-31 下午4:54:23
 * @version V1.0
 */
public class AcceptStatus implements Serializable {

    private static final long serialVersionUID = -3022468200772438920L;

    /** 状态 */
    private Integer status;

    /** 填报时间 */
    private String createdDate;

    /** 联系客户时间 */
    private String contactCustDate;

    /** 申报时间 */
    private String appEntDate;

    /** 申报成功时间 */
    private String appSuccessDate;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getContactCustDate() {
        return contactCustDate;
    }

    public void setContactCustDate(String contactCustDate) {
        this.contactCustDate = contactCustDate;
    }

    public String getAppEntDate() {
        return appEntDate;
    }

    public void setAppEntDate(String appEntDate) {
        this.appEntDate = appEntDate;
    }

    public String getAppSuccessDate() {
        return appSuccessDate;
    }

    public void setAppSuccessDate(String appSuccessDate) {
        this.appSuccessDate = appSuccessDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
