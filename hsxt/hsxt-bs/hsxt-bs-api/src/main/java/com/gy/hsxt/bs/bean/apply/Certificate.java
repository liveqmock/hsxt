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
 * @ClassName: Certificate
 * @Description: 证书信息
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午2:04:47
 * @version V1.0
 */
public class Certificate implements Serializable {

    private static final long serialVersionUID = -5978356924975068213L;

    /** 证书唯一识别码 **/
    private String certificateNo;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业客户号 **/
    private String entCustId;

    /** 企业名称 **/
    private String entCustName;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String mobile;

    /** 申报ID **/
    private String applyId;

    /** 模板ID **/
    private String templetId;

    /** 模板占位符的替换内容 **/
    private String varContent;

    /** 证书类型 1:销售资格证 2:第三方证书 **/
    private Integer certificateType;

    /** 状态 0：待盖章 1：已生效 2：已失效 3：需重新盖章 **/
    private Integer status;

    // /** 印章数据 **/
    // private String sealData;

    /** 盖章操作员 **/
    private String sealOperator;

    /** 盖章时间 **/
    private String sealDate;

    // /** 盖章X坐标 **/
    // private Integer sealX;
    //
    // /** 盖章Y坐标 **/
    // private Integer sealY;

    /** 打印操作员 **/
    private String printOperator;

    /** 打印次数 **/
    private Integer printCount;

    /** 打印时间 **/
    private String printDate;

    /** 是否发放 1:已发放 0:未发放 **/
    private Boolean isSend;

    /** 发放次数 **/
    private Integer sendCount;

    /** 注册日期 **/
    private String regDate;

    /**
     * 证书对应模版
     */
    private Templet templet;

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
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

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    // public String getSealData() {
    // return sealData;
    // }
    //
    // public void setSealData(String sealData) {
    // this.sealData = sealData;
    // }

    public String getSealDate() {
        return sealDate;
    }

    public void setSealDate(String sealDate) {
        this.sealDate = sealDate;
    }

    // public Integer getSealX() {
    // return sealX;
    // }
    //
    // public void setSealX(Integer sealX) {
    // this.sealX = sealX;
    // }
    //
    // public Integer getSealY() {
    // return sealY;
    // }
    //
    // public void setSealY(Integer sealY) {
    // this.sealY = sealY;
    // }

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

    public String getVarContent() {
        return varContent;
    }

    public void setVarContent(String varContent) {
        this.varContent = varContent;
    }

    public String getSealOperator() {
        return sealOperator;
    }

    public void setSealOperator(String sealOperator) {
        this.sealOperator = sealOperator;
    }

    public String getPrintOperator() {
        return printOperator;
    }

    public void setPrintOperator(String printOperator) {
        this.printOperator = printOperator;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Templet getTemplet() {
        return templet;
    }

    public void setTemplet(Templet templet) {
        this.templet = templet;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
