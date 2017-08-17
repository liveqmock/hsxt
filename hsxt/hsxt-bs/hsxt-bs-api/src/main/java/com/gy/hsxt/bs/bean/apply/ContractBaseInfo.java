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
 * @ClassName: ContractBaseInfo
 * @Description: 合同基本信息
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午12:15:25
 * @version V1.0
 */
public class ContractBaseInfo implements Serializable {

    private static final long serialVersionUID = -8777432353544024394L;

    /** 合同ID **/
    private String contractNo;

    /** 企业互生号(合同编号) **/
    private String entResNo;

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

    /** 盖章状态 **/
    private Integer sealStatus;

    /** 打印次数 **/
    private Integer printCount;

    /**
     * 是否发送
     */
    private Boolean isSend;

    /** 发放次数 **/
    private Integer sendCount;

    /** 发放时间 **/
    private String sendDate;

    /** 唯一识别码(证书随机码) **/
    public String getRandomCode() {
        if (contractNo != null && !contractNo.isEmpty() && entResNo != null && !entResNo.isEmpty())
        {
            if (contractNo.contains(entResNo))
            {
                return contractNo.substring(11);
            }
            else
            {
                return contractNo;
            }
        }
        return null;
    }

    public String getContractNo() {
        return contractNo;
    }

    /** 企业互生号(合同编号) **/
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

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public Boolean getSend() {
        return isSend;
    }

    public void setSend(Boolean send) {
        isSend = send;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
