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
 * @ClassName: DeclareQueryParam
 * @Description: 申报查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:13:28
 * @version V1.0
 */
public class DeclareQueryParam implements Serializable {

    private static final long serialVersionUID = 6008577600812989645L;

    /** 企业类型 **/
    private Integer custType;

    /** 被申报者所管理公司互生号 **/
    private String manageResNo;

    /** 申报者互生号 **/
    private String declarerResNo;

    /** 操作员客户号 **/
    private String operatorCustId;

    /** 开始时间 **/
    private String startDate;

    /** 结束时间 **/
    private String endDate;

    /** 拟用互生号 **/
    private String resNo;

    /** 企业名称 **/
    private String entName;

    /** 状态 **/
    private Integer status;

    /** 联系人 */
    private String linkman;

    /** 联系人手机 */
    private String linkmanMobile;

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getManageResNo() {
        return manageResNo;
    }

    public void setManageResNo(String manageResNo) {
        this.manageResNo = manageResNo;
    }

    public String getDeclarerResNo() {
        return declarerResNo;
    }

    public void setDeclarerResNo(String declarerResNo) {
        this.declarerResNo = declarerResNo;
    }

    public String getOperatorCustId() {
        return operatorCustId;
    }

    public void setOperatorCustId(String operatorCustId) {
        this.operatorCustId = operatorCustId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
