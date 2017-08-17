/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;

public class BaseBean implements Serializable {

    private static final long serialVersionUID = -6641076250301618655L;

    /** 创建操作员编号 **/
    private String createdOptId;

    /** 创建操作员名称 **/
    private String createdOptName;

    /** 创建时间 **/
    private String createdDate;

    /** 更新操作员编号 **/
    private String updatedOptId;

    /** 更新操作员名称 **/
    private String updatedOptName;

    /** 更新时间 **/
    private String updatedDate;

    public String getCreatedOptId() {
        return createdOptId;
    }

    public void setCreatedOptId(String createdOptId) {
        this.createdOptId = createdOptId;
    }

    public String getCreatedOptName() {
        return createdOptName;
    }

    public void setCreatedOptName(String createdOptName) {
        this.createdOptName = createdOptName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = DateUtil.DateTimeToString(createdDate);
    }

    public String getUpdatedOptId() {
        return updatedOptId;
    }

    public void setUpdatedOptId(String updatedOptId) {
        this.updatedOptId = updatedOptId;
    }

    public String getUpdatedOptName() {
        return updatedOptName;
    }

    public void setUpdatedOptName(String updatedOptName) {
        this.updatedOptName = updatedOptName;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = DateUtil.DateTimeToString(updatedDate);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
