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
 * @ClassName: CertificateMainInfo
 * @Description: 证书主要信息
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午2:04:47
 * @version V1.0
 */
public class CertificateMainInfo implements Serializable {

    private static final long serialVersionUID = 2469722084851198428L;

    /** 证书ID **/
    private String certificateNo;

    /** 企业互生号(证书编号) **/
    private String entResNo;

    /** 企业名称 **/
    private String entCustName;

    /** 状态 **/
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
