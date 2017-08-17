/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.bean.accountManage;

/**
 * 积分账户信息/积分转货币
 * 
 * @Package: com.gy.hsxt.access.web.bean.accountManage  
 * @ClassName: IntegralAccountInfo 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-10-8 下午6:27:50 
 * @version V3.0.0
 */
public class IntegralAccountInfo {
    /** 客户id **/
    private String custId;
	/** 积分账户余数 **/
	private String pointAcct;
	/** 可用积分数 **/
	private String usablePointNum;
	/** 昨日积分数 **/
	private String todaysNewPoint;
	/** 要转的积分数 **/
	private String pointNum;
	/** 转的当地币种 **/
	private String currency;
	/** 转换比率 **/
	private String rate;
	/** 积分和比率计算的金额 **/
	private String moneyNum;
	/** 交易密码 **/
	private String password;
	
	public String getPointAcct() {
		return pointAcct;
	}
	public void setPointAcct(String pointAcct) {
		this.pointAcct = pointAcct;
	}
	public String getUsablePointNum() {
		return usablePointNum;
	}
	public void setUsablePointNum(String usablePointNum) {
		this.usablePointNum = usablePointNum;
	}
	public String getTodaysNewPoint() {
		return todaysNewPoint;
	}
	public void setTodaysNewPoint(String todaysNewPoint) {
		this.todaysNewPoint = todaysNewPoint;
	}
    public String getCustId() {
        return custId;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getPointNum() {
        return pointNum;
    }
    public void setPointNum(String pointNum) {
        this.pointNum = pointNum;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getMoneyNum() {
        return moneyNum;
    }
    public void setMoneyNum(String moneyNum) {
        this.moneyNum = moneyNum;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
	
}
