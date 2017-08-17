/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 业务办理授权实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: BizTypeAuth
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:24:29
 * @version V3.0.0
 */
public class BizTypeAuth implements Serializable {
    private static final long serialVersionUID = 7041133179656087683L;

    /** 业务类型 **/
    private String bizType;

    /** 值班员编号 **/
    private String optCustId;

    /** 业务类型名称 **/
    private String bizTypeName;

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId == null ? null : optCustId.trim();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
