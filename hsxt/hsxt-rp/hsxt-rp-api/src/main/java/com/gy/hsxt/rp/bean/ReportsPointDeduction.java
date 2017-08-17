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
 * 企业资源对象
 * @author 作者 : 毛粲
 * @ClassName: 类名:ReportsEnterprisResource
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午2:35:52
 * @version 1.0
 */
public class ReportsPointDeduction implements Serializable{

	
	private static final long serialVersionUID = -3061384064909376152L;

	/**	客户全局编号 */
	private   String       custId;
	
	/**	终端类型 */
	private   Integer       channelType;
	
	/** 终端编号 */
	private   String      equipmentNo;
	
	/**	操作员编号 */
	private   String       operNo;
	
	/**	消费积分扣除数 */
	private   String       ndetPoint;
	
	/**	消费积分撤单数 */
	private   String       cdetPoint;

	/**	统计日期 */
	private   String       statisticsDate;
	
	/**	开始时间 */
	private   String       beginDate;
	
	/**	结束时间 */
	private   String       endDate;

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
	 * @return the 终端类型
	 */
	public Integer getChannelType() {
		return channelType;
	}

	/**
	 * @param 终端类型 the channelType to set
	 */
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	/**
	 * @return the 终端编号
	 */
	public String getEquipmentNo() {
		return equipmentNo;
	}

	/**
	 * @param 终端编号 the equipmentNo to set
	 */
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	/**
	 * @return the 操作员编号
	 */
	public String getOperNo() {
		return operNo;
	}

	/**
	 * @param 操作员编号 the operNo to set
	 */
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	/**
	 * @return the 消费积分扣除数
	 */
	public String getNdetPoint() {
		return convert(ndetPoint);
	}

	/**
	 * @param 消费积分扣除数 the ndetPoint to set
	 */
	public void setNdetPoint(String ndetPoint) {
		this.ndetPoint = ndetPoint;
	}

	/**
	 * @return the 消费积分撤单数
	 */
	public String getCdetPoint() {
		return convert(cdetPoint);
	}

	/**
	 * @param 消费积分撤单数 the cdetPoint to set
	 */
	public void setCdetPoint(String cdetPoint) {
		this.cdetPoint = cdetPoint;
	}

	/**
	 * @return the 统计日期
	 */
	public String getStatisticsDate() {
		return statisticsDate;
	}

	/**
	 * @param 统计日期 the statisticsDate to set
	 */
	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
	
	 /**
	 * @return the 开始时间
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param 开始时间 the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
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
