/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.bean;

import java.io.Serializable;

/**
 * 账户余额对象
 * @author 作者 : maocan
 * @ClassName: 类名:ReportsInvoiceValueStatistics
 * @Description: TODO
 * @createDate 创建时间: 2016-2-18 下午2:35:52
 * @version 1.0
 */
public class ReportsInvoiceValueStatistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8334619411456344819L;

	/** 客户全局编号 **/
	private String     custId;
	
	/** 客户名称 **/
	private String     custName;
	
	/** 开发票的业务类型 **/
	private String     bizType;
	
	/** 金额  **/
	private String     amount;
	
    /**
	 * @return the 客户全局编号
	 */
	public String getCustId() {
		return custId;
	}



	/**
	 * @param 客户全局编号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}



	/**
	 * @return the 客户名称
	 */
	public String getCustName() {
		return custName;
	}



	/**
	 * @param 客户名称 the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}



	/**
	 * @return the 开发票的业务类型
	 */
	public String getBizType() {
		return bizType;
	}



	/**
	 * @param 开发票的业务类型 the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}



	/**
	 * @return the 金额
	 */
	public String getAmount() {
		return convert(amount);
	}



	/**
	 * @param 金额 the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}



	/**
     * 小數位前补零
     * @param num
     * @return
     */
    private String convert(String num){
    	if(num != null)
        {
                String a = "";
                if(num.length()>1)
                {
                        a = num.substring(0, 1);
                }
                String b = "";
                if(num.length()>2)
                {
                        b = num.substring(0, 2);
                }
                if(".".equals(a))
                {
                	num = 0 + num;
                }
                if("-.".equals(b))
                {
                	num = a + 0 + num.substring(1);
                }
        }
    	return num;
    }
	
}
