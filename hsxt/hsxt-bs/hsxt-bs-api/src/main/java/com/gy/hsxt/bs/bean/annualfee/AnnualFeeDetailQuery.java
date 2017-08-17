/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.annualfee;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 年费计费单查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.annualfee
 * @ClassName : AnnualFeeDetailQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/11 11:42
 * @Version V3.0.0.0
 */
public class AnnualFeeDetailQuery extends Query {

    private static final long serialVersionUID = 4799841990560852020L;

    /**
     * 企业互生号
     **/
    private String entResNo;
    /**
     * 企业客户号
     **/
    private String entCustId;
    /**
     * 企业名称
     **/
    private String entCustName;
    /**
     * 客户类型
     * <p/>
     * 4：服务公司 3：托管企业
     *
     * @see com.gy.hsxt.common.constant.CustType
     **/
    private Integer custType;
    /**
     * 是否缴费
     * <p/>
     * 0：否 1：是
     **/
    private Integer payStatus;
    /**
     * 人工缴纳或自动扣费时生成的订单编号
     **/
    private String orderNo;

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

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 构建查询实体
     *
     * @return 查询实体
     */
    public static AnnualFeeDetailQuery build() {
        return new AnnualFeeDetailQuery();
    }

}
