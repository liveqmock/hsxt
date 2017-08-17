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
 * @ClassName: CertificateBaseInfo
 * @Description: 证书基本信息
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午2:04:47
 * @version V1.0
 */
public class CertificateBaseInfo implements Serializable {

    private static final long serialVersionUID = -5978356924975068213L;

    /** 证书ID **/
    private String certificateNo;

    /** 企业互生号(证书编号) **/
    private String entResNo;

    /** 企业名称 **/
    private String entCustName;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String mobile;

    /** 盖章状态 **/
    private Integer sealStatus;

    /** 是否打印 **/
    private Boolean isPrint;

    /** 是否发放 **/
    private Boolean isSend;

    /** 打印时间 **/
    private String printDate;

    /** 唯一识别码(证书随机码) **/
    public String getRandomCode() {
        if (certificateNo != null && !certificateNo.isEmpty() && entResNo != null && !entResNo.isEmpty())
        {
            if (certificateNo.contains(entResNo))
            {
                return certificateNo.substring(11);
            }
            else
            {
                return certificateNo;
            }
        }
        return null;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /** 企业互生号(证书编号) **/
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

    public Integer getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(Integer sealStatus) {
        this.sealStatus = sealStatus;
    }

    public Boolean getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Boolean isPrint) {
        this.isPrint = isPrint;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
