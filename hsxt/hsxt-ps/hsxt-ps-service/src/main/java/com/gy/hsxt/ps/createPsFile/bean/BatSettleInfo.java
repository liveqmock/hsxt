package com.gy.hsxt.ps.createPsFile.bean;

import java.io.Serializable;

/**
 * 查询批结算返回实体类
 * 
 * @Package: com.gy.hsxt.ps.bean  
 * @ClassName: BatSettleInfo 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-3-22 下午3:34:20 
 * @version V1.0
 */
public class BatSettleInfo implements Serializable {

	private static final long serialVersionUID = 5519430871977429048L;

	/** 原始电商交易流水号 */
	private String sourceTransNo;
	
	/** 消费积分流水号 */
    private String misCenterLineNo;
    
    /** 企业互生号 */
    private String entHsResNo;
    
    /** 消费者互生号 */
    private String perHsResNo;
    
    /** 消费者客户号 */
    private String perCustId;
    
    /** 订单号 */
    private String orderNo;
    
    /**  业务类型   0：支付，1退款 */
    private String bussnessType;
    
    /** 结算状态 0:失败， 1：成功 */
    private String settleStatus;
    
    /** 结算时间  */
    private String settleTime;
    
    /** 备注 */
    private String remark;

    /**
     * @return the 原始电商交易流水号
     */
    public String getSourceTransNo() {
        return sourceTransNo;
    }

    /**
     * @param 原始电商交易流水号 the sourceTransNo to set
     */
    public void setSourceTransNo(String sourceTransNo) {
        this.sourceTransNo = sourceTransNo;
    }

    /**
     * @return the 消费积分流水号
     */
    public String getMisCenterLineNo() {
        return misCenterLineNo;
    }

    /**
     * @param 消费积分流水号 the misCenterLineNo to set
     */
    public void setMisCenterLineNo(String misCenterLineNo) {
        this.misCenterLineNo = misCenterLineNo;
    }

    /**
     * @return the 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param 订单号 the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the 业务类型0：支付，1退款
     */
    public String getBussnessType() {
        return bussnessType;
    }

    /**
     * @param 业务类型0：支付，1退款 the bussnessType to set
     */
    public void setBussnessType(String bussnessType) {
        this.bussnessType = bussnessType;
    }

    /**
     * @return the 结算状态0:失败，1：成功
     */
    public String getSettleStatus() {
        return settleStatus;
    }

    /**
     * @param 结算状态0:失败，1：成功 the settleStatus to set
     */
    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus;
    }

    /**
     * @return the 结算时间
     */
    public String getSettleTime() {
        return settleTime;
    }

    /**
     * @param 结算时间 the settleTime to set
     */
    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    /**
     * @return the 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param 备注 the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the 企业互生号
     */
    public String getEntHsResNo() {
        return entHsResNo;
    }

    /**
     * @param 企业互生号 the entHsResNo to set
     */
    public void setEntHsResNo(String entHsResNo) {
        this.entHsResNo = entHsResNo;
    }

    /**
     * @return the 消费者互生号
     */
    public String getPerHsResNo() {
        return perHsResNo;
    }

    /**
     * @param 消费者互生号 the perHsResNo to set
     */
    public void setPerHsResNo(String perHsResNo) {
        this.perHsResNo = perHsResNo;
    }

    /**
     * @return the 消费者客户号
     */
    public String getPerCustId() {
        return perCustId;
    }

    /**
     * @param 消费者客户号 the perCustId to set
     */
    public void setPerCustId(String perCustId) {
        this.perCustId = perCustId;
    }
	
    
	
}
