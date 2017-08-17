/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gy.hsxt.common.utils.DateUtil;

/**
 * 调账申请
 * @author lixuan
 *
 */
public class TcCheckBalance implements Serializable {
	private static final long serialVersionUID = 6294074716713497291L;

	private String id;

    private String resNo;

    private String name;

    private String acctType;

    private BigDecimal amount;

    private Integer checkType;

    private String remark;

    private Date createDate;

    private Date updateDate;

    private Date balanceDate;

    private String creator;

    private Integer status;
    /** 客户类型  */
    private Integer custType;
    
    private String createDateString;
    
    private String balanceDateString;
    
    /**
     * 获取字符类型的创建日期
     * @return
     */
    public String getCreateDateString(){
    	createDateString = DateUtil.DateToString(createDate);
    	return createDateString;
    }
    
    
    /**
     * 获取字符型的调帐日期
     * @return
     */
    public String getBalanceDateString(){
    	balanceDateString = DateUtil.DateToString(balanceDate);
    	return balanceDateString;
    }
    
	/**
     * 调账主体的客户号
     */
    private String custId;
    
    /**
	 * @return 客户类型
	 */
	public Integer getCustType() {
		return custType;
	}

	/**
	 * @param 客户类型
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}

    /**
	 * @return the 调账主体的客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 调账主体的客户号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo == null ? null : resNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType == null ? null : acctType.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 调账状态
     * @author lixuan
     *
     */
    public enum TcCheckBalanceStatus{
    	WAIT_FIRST_CHECK(0),
    	FIRST_CHECK_SUCCESS(1),
    	SECOND_CHECK_SUCCESS(2),
    	FIRST_CHECK_FAIL(3),
    	SECOND_CHECK_FAIL(4);
    	int value;
    	
    	TcCheckBalanceStatus(int value){
    		this.value = value;
    	}
    	
    	public int getValue(){
    		return value;
    	}
    }
}