/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: CloseOpenEnt
 * @Description: 关闭开启系统
 * 
 * @author: xiaofl
 * @date: 2015-11-30 上午11:01:01
 * @version V1.0
 */
public class CloseOpenEnt implements Serializable {

    private static final long serialVersionUID = 8848720784664060221L;

    /** 申请编号 */
    private String applyId;

    /** 申请类型 */
    private Integer applyType;

    /** 企业客户号 */
    private String entCustId;

    /** 企业互生号 */
    private String entResNo;

    /** 企业名称 */
    private String entCustName;

    /** 企业类型 */
    private Integer custType;

    /** 企业地址 */
    private String address;

    /** 联系人 */
    private String linkman;

    /** 联系电话 */
    private String mobile;

    /** 申请说明 **/
    private String reqRemark;

    /** 申请时间 **/
    private String reqTime;

    /** 申请操作员编号 **/
    private String reqOptCustId;

    /** 申请操作员名称 **/
    private String reqOptCustName;

    /** 状态 **/
    private Integer status;

    /** 复核意见 **/
    private String apprRemark;

    /** 复核时间 **/
    private String apprTime;

    /** 复核操作员编号 **/
    private String apprOptCustId;

    /** 复核操作员名称 **/
    private String apprOptCustName;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReqRemark() {
        return reqRemark;
    }

    public void setReqRemark(String reqRemark) {
        this.reqRemark = reqRemark;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getReqOptCustId() {
        return reqOptCustId;
    }

    public void setReqOptCustId(String reqOptCustId) {
        this.reqOptCustId = reqOptCustId;
    }

    public String getReqOptCustName() {
        return reqOptCustName;
    }

    public void setReqOptCustName(String reqOptCustName) {
        this.reqOptCustName = reqOptCustName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    public String getApprTime() {
        return apprTime;
    }

    public void setApprTime(String apprTime) {
        this.apprTime = apprTime;
    }

    public String getApprOptCustId() {
        return apprOptCustId;
    }

    public void setApprOptCustId(String apprOptCustId) {
        this.apprOptCustId = apprOptCustId;
    }

    public String getApprOptCustName() {
        return apprOptCustName;
    }

    public void setApprOptCustName(String apprOptCustName) {
        this.apprOptCustName = apprOptCustName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
