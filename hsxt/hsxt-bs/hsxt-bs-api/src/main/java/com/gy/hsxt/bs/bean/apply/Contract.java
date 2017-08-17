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
 * @ClassName: Contract
 * @Description: 合同信息
 *
 * @author: xiaofl
 * @date: 2015-9-2 下午12:15:25
 * @version V1.0
 */
public class Contract implements Serializable {

    private static final long serialVersionUID = -8777432353544024394L;

    /** 合同唯一识别码 **/
    private String contractNo;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业客户号 **/
    private String entCustId;

    /** 企业名称 **/
    private String entCustName;

    /** 客户类型:2.成员企业 3.托管企业 4.服务公司 **/
    private Integer custType;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String mobile;

    /** 注册日期 **/
    private String regDate;

    /** 申报ID **/
    private String applyId;

    /** 合同模板ID **/
    private String templetId;

    /** 模板占位符的替换内容 **/
    private String varContent;

//    /** 印章数据 **/
//    private String sealData;
//
//    /** 盖章X坐标 **/
//    private Integer sealX;
//
//    /** 盖章Y坐标 **/
//    private Integer sealY;

    /** 状态:1:待盖章 2:已生效 3:已失效 4:重新盖章 **/
    private Integer status;

    /** 合同打印次数 **/
    private Integer printCount;

    /** 合同打印时间 **/
    private String printDate;

    /** 合同是否发放:1:已发放 0:未发放 **/
    private Boolean isSend;

    /** 发放次数 **/
    private Integer sendCount;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
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

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }


//    public String getSealData() {
//        return sealData;
//    }
//
//    public void setSealData(String sealData) {
//        this.sealData = sealData;
//    }
//
//    public Integer getSealX() {
//        return sealX;
//    }
//
//    public void setSealX(Integer sealX) {
//        this.sealX = sealX;
//    }
//
//    public Integer getSealY() {
//        return sealY;
//    }
//
//    public void setSealY(Integer sealY) {
//        this.sealY = sealY;
//    }

    public String getVarContent() {
        return varContent;
    }

    public void setVarContent(String varContent) {
        this.varContent = varContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
