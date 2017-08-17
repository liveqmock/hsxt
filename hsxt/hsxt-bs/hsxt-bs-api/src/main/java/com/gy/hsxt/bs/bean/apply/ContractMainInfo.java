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
 * @ClassName: ContractMainInfo
 * @Description: 合同主要信息
 * 
 * @author: xiaofl
 * @date: 2015-9-2 下午12:15:25
 * @version V1.0
 */
public class ContractMainInfo implements Serializable {

    private static final long serialVersionUID = -5779268578060547661L;

    /** 合同ID **/
    private String contractNo;

    /** 企业互生号(合同编号) **/
    private String entResNo;

    /** 企业名称 **/
    private String entCustName;

    /** 客户类型:2.成员企业 3.托管企业 4.服务公司 **/
    private Integer custType;

    /** 状态 **/
    private Integer status;

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

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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
