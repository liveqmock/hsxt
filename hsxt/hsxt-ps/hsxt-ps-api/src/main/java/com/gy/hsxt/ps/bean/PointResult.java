package com.gy.hsxt.ps.bean;

import java.io.Serializable;

/**
 * @description 积分返回结果实体类
 * @author chenhz
 * @createDate 2015-7-27 下午2:30:12
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class PointResult  extends Result  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -485741277837777543L;
	/** 消费者的积分 */
	private String perPoint;
	/** 企业的积分 */
	private String entPoint;
	/** 交易流水号 */
	private String transNo;
	/** 会计时间*/
	private String accountantDate;
	/** 返回结果代码 **/
	private int resultCode;
	/** 返回原定金金额 **/
	private String sourceEarnestAmount;
	/** 手机网银支付TN码 */
	private String tnTransNo;
	
	/** 短信验证码 */
    private String smsCode;
    
    /** 签约号 */
    private String bindingNo;
	
	/**
	 * 获取企业积分
	 * @return entPoint 积分
	 */
	public String getEntPoint() {
		return entPoint;
	}
	
	/**
	 * 设置企业积分
	 * @return entPoint 积分
	 */
	public void setEntPoint(String entPoint) {
		this.entPoint = entPoint;
	}
	
	/**
	 * 获取消费者的积分
	 * @return perPoint 积分
	 */
	public String getPerPoint()
	{
		return perPoint;
	}
	/**
	 * 设置消费者积分
	 * @param perPoint 积分
	 */
	public void setPerPoint(String perPoint)
	{
		this.perPoint = perPoint;
	}
	/**
	 * 获取交易流水号
	 * @return transNo 交易流水号
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * 设置交易流水号
	 * @param transNo 交易流水号
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	/**
	 * 获取会计时间
	 * @return accountantDate 会计时间
	 */
	public String getAccountantDate()
	{
		return accountantDate;
	}
	/**
	 * 设置会计时间
	 * @param accountantDate 会计时间
	 */
	public void setAccountantDate(String accountantDate)
	{
		this.accountantDate = accountantDate;
	}
	/**
	 * 获取返回结果代码
	 * @return resultCode 返回结果代码
	 */
	public int getResultCode()
	{
		return resultCode;
	}
	/**
	 * 设置返回结果代码
	 * @param resultCode 返回结果代码
	 */
	public void setResultCode(int resultCode)
	{
		this.resultCode = resultCode;
	}
	/**
	 * 获取返回原定金金额
	 * @return sourceEarnestAmount 返回原定金金额
	 */
	public String getSourceEarnestAmount() {
		return sourceEarnestAmount;
	}
	/**
	 * 设置返回原定金金额
	 * @param sourceEarnestAmount 返回原定金金额
	 */
	public void setSourceEarnestAmount(String sourceEarnestAmount) {
		this.sourceEarnestAmount = sourceEarnestAmount;
	}

    /**
     * @return the 手机网银支付TN码
     */
    public String getTnTransNo() {
        return tnTransNo;
    }

    /**
     * @param 手机网银支付TN码 the tnTransNo to set
     */
    public void setTnTransNo(String tnTransNo) {
        this.tnTransNo = tnTransNo;
    }
    
    /**
     * @return the 短信验证码
     */
    public String getSmsCode() {
        return smsCode;
    }

    /**
     * @param 短信验证码 the smsCode to set
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    /**
     * @return the 签约号
     */
    public String getBindingNo() {
        return bindingNo;
    }

    /**
     * @param 签约号 the bindingNo to set
     */
    public void setBindingNo(String bindingNo) {
        this.bindingNo = bindingNo;
    }
}
