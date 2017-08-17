/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeSet;

import java.io.Serializable;

/***
 * 基础实体抽象类
 * 
 * @Package: com.gy.hsxt.access.web.bean
 * @ClassName: CompanyBase
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-2 下午3:12:40
 * @version V1.0
 */
public abstract class AbstractCompanyBase implements Serializable {
    private static final long serialVersionUID = 4838533629147991825L;

    /**
     * 互生号
     */
    protected String pointNo;

    /**
     * 客户名称
     */
    protected String custName;

    /**
     * 操作员客户号
     */
    protected String custId;

    /**
     * 企业客户号
     */
    protected String entCustId;

    /**
     * 用户登录token
     */
    protected String token;

    /**
     * 互生号
     */
    protected String resNo;

    /**
     * 企业管理号
     */
    protected String entResNo;

    /**
     * 随即token(用户中心随机生成)
     */
    protected String randomToken;

    /**
     * @return the 互生号
     */
    public String getPointNo() {
        return pointNo;
    }

    /**
     * @param 互生号 the pointNo to set
     */
    public void setPointNo(String pointNo) {
        this.pointNo = pointNo;
    }

    /**
     * @return the 客户名称
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param 客户名称 the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the 操作员客户号
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param 操作员客户号 the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * @return the 企业客户号
     */
    public String getEntCustId() {
        return entCustId;
    }

    /**
     * @param 企业客户号 the entCustId to set
     */
    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    /**
     * @return the 用户登录token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param 用户登录token the 用户登录token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the 互生号
     */
    public String getResNo() {
        return resNo;
    }

    /**
     * @param 互生号 the resNo to set
     */
    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    /**
     * @return the 企业管理号
     */
    public String getEntResNo() {
        return entResNo;
    }

    /**
     * @param 企业管理号 the entResNo to set
     */
    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    /**
     * @return the 随即token(用户中心随机生成)
     */
    public String getRandomToken() {
        return randomToken;
    }

    /**
     * @param 随即token(用户中心随机生成) the randomToken to set
     */
    public void setRandomToken(String randomToken) {
        this.randomToken = randomToken;
    }

}
