/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 业务类型实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: BizType
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:22:30
 * @version V3.0.0
 */
public class BizType implements Serializable {
    private static final long serialVersionUID = -7582380153934447493L;

    /** 业务类型 **/
    private String bizType;

    /** 业务类型名称 **/
    private String bizTypeName;

    /** 业务办理企业 **/
    private Integer bizEntCustType;

    public Integer getBizEntCustType() {
        return bizEntCustType;
    }

    public void setBizEntCustType(Integer bizEntCustType) {
        this.bizEntCustType = bizEntCustType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName == null ? null : bizTypeName.trim();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
