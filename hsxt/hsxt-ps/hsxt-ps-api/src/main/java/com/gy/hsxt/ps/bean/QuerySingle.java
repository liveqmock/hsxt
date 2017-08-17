package com.gy.hsxt.ps.bean;

import java.io.Serializable;

/**
 * @description POS单笔查询
 * @author chenhz
 * @createDate 2015-8-18 上午10:19:53
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class QuerySingle implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6118656780604418161L;

    /** 原交易流水号 */
    private String sourceTransNo;

    /** 消费者互生号 */
    private String perResNo;

    /** 消费者客户号 */
    private String perCustId;

    /** 企业互生号 */
    private String entResNo;

    /** 企业客户号 */
    private String entCustId;

    /** 设备编号 */
    private String equipmentNo;
    
    /** 交易类型 */
    private String transTypes;
    
    /** 开始时间 */
    private String startDate;
    /** 结束时间 */
    private String endDate;

    /**
     * 获取原交易流水号
     * 
     * @return sourceTransNo 原交易流水号
     */
    public String getSourceTransNo() {
        return sourceTransNo;
    }

    /**
     * 设置原交易流水号
     * 
     * @param sourceTransNo
     *            原交易流水号
     */
    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

    /**
     * @return the 消费者互生号
     */
    public String getPerResNo() {
        return perResNo;
    }

    /**
     * @param 消费者互生号
     *            the perResNo to set
     */
    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    /**
     * @return the 消费者客户号
     */
    public String getPerCustId() {
        return perCustId;
    }

    /**
     * @param 消费者客户号
     *            the perCustId to set
     */
    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId;
    }

    /**
     * 获取互生号
     * 
     * @return entResNo 互生号
     */
    public String getEntResNo() {
        return entResNo;
    }

    /**
     * 设置互生号
     * 
     * @param entResNo
     *            互生号
     */
    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    /**
     * 获取企业客户号
     * 
     * @return entCustId 企业客户号
     */
    public String getEntCustId() {
        return entCustId;
    }

    /**
     * 设置企业客户号
     * 
     * @param entCustId
     *            企业客户号
     */
    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    /**
     * 获取设备编号
     * 
     * @return equipmentNo 设备编号
     */
    public String getEquipmentNo() {
        return equipmentNo;
    }

    /**
     * 设置设备编号
     * 
     * @param equipmentNo
     *            设备编号
     */
    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    /**
     * @return the 交易类型
     */
    public String getTransTypes() {
        return transTypes;
    }

    /**
     * @param 交易类型 the transTypes to set
     */
    public void setTransTypes(String transTypes) {
        this.transTypes = transTypes;
    }

    /**
     * @return the 开始时间
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param 开始时间 the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the 结束时间
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param 结束时间 the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
