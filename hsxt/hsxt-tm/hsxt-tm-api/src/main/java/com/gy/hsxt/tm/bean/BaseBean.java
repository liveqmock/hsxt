/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 通用查询条件参数实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.base
 * @ClassName: BaseBean
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-25 上午10:45:20
 * @version V1.0
 */
public class BaseBean implements Serializable {
    private static final long serialVersionUID = 5592999551965150841L;

    /** 企业客户号 **/
    private String entCustId;

    /** 企业名称 **/
    private String entName;

    /** 操作员客户号 **/
    private String operatorId;

    /** 操作员名称 **/
    private String operatorName;

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
