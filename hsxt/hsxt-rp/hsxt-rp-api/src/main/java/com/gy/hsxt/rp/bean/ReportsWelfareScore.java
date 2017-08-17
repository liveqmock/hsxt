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
 * 积分福利数对象
 * @author 作者 : maocan
 * @ClassName: 类名:ReportsWelfareScore
 * @Description: TODO
 * @createDate 创建时间: 2016-1-16 下午2:35:52
 * @version 1.0
 */
public class ReportsWelfareScore implements Serializable{


	private static final long serialVersionUID = -3107280748551442603L;


	/** 客户全局编号 **/
	private String     custId;
	
	/** 互生号  **/
	private String     hsResNo;
	
	/** 客户姓名 **/
	private String     perName;
	
	/** 消费积分 **/
    private String		consumeScore;
    
    /** 投资积分 **/
    private String		investScore;

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
	 * @return the 互生号
	 */
	public String getHsResNo() {
		return hsResNo;
	}

	/**
	 * @param 互生号 the hsResNo to set
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	/**
	 * @return the 客户姓名
	 */
	public String getPerName() {
		return perName;
	}

	/**
	 * @param 客户姓名 the perName to set
	 */
	public void setPerName(String perName) {
		this.perName = perName;
	}

	/**
	 * @return the 消费积分
	 */
	public String getConsumeScore() {
		return convert(consumeScore);
	}

	/**
	 * @param 消费积分 the consumeScore to set
	 */
	public void setConsumeScore(String consumeScore) {
		this.consumeScore = consumeScore;
	}

	/**
	 * @return the 投资积分
	 */
	public String getInvestScore() {
		return convert(investScore);
	}

	/**
	 * @param 投资积分 the investScore to set
	 */
	public void setInvestScore(String investScore) {
		this.investScore = investScore;
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
