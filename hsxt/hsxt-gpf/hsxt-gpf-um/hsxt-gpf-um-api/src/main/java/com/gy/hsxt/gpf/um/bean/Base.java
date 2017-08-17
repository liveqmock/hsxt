/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Bean基类
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : Base
 * @Description : Bean基类
 * @Author : chenm
 * @Date : 2016/1/26 18:07
 * @Version V3.0.0.0
 */
public abstract class Base implements Serializable {

    private static final long serialVersionUID = 5876099155205833344L;

    /**
     * 描述
     */
    private String description;
    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间 格式： yyyy-MM-dd HH:mm:ss
     */
    private String createdDate;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间 格式： yyyy-MM-dd HH:mm:ss
     */
    private String updatedDate;

    /**
     * 删除标识 0-有效 1-无效
     */
    private boolean delFlag;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
