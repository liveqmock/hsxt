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

package com.gy.hsxt.bp.bean;

import java.io.Serializable;

/** 
 * @Package: com.gy.hsxt.bp.bean  
 * 客户业务参数對象
 * @ClassName: CustBusinessParam 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-20 下午4:13:37 
 * @version V1.0 
 

 */
public class BusinessCustParamItemsRedis implements Serializable{
  

	private static final long serialVersionUID = 7064413528808825564L;

	/** 客户参数ID **/
    private String      custItemsId;
	
	/** 客户全局编号 **/
	private String		 custId;
	
	/** 客户名称 **/
	private String		 custName;
	
	/** 互生号 **/
	private String		 hsResNo;
	
	/** 参数组编号 **/
	private String		 sysGroupCode;
	
	/** 参数项键名 **/
	private String		 sysItemsKey;
	
	/** 参数项名称 **/
    private String      sysItemsName;
    
    /** 参数项值 **/
    private String      sysItemsValue;
    
    /** 设置原因  **/
    private String      settingRemark;
    
    /** 参数到期日期 **/
    private String      dueDate;
    
    /** 激活状态: Y：是；N：否  **/
    private String      isActive;

    /**
	 * @return the 客户参数ID
	 */
	public String getCustItemsId() {
		return custItemsId;
	}

	/**
	 * @param 客户参数ID the custItemsId to set
	 */
	public void setCustItemsId(String custItemsId) {
		this.custItemsId = custItemsId;
	}

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
	 * @return the 参数组编号
	 */
	public String getSysGroupCode() {
		return sysGroupCode;
	}

	/**
	 * @param 参数组编号 the sysGroupCode to set
	 */
	public void setSysGroupCode(String sysGroupCode) {
		this.sysGroupCode = sysGroupCode;
	}

	/**
	 * @return the 参数项键名
	 */
	public String getSysItemsKey() {
		return sysItemsKey;
	}

	/**
	 * @param 参数项键名 the sysItemsKey to set
	 */
	public void setSysItemsKey(String sysItemsKey) {
		this.sysItemsKey = sysItemsKey;
	}

	/**
     * @return the 系统参数项名称
     */
    public String getSysItemsName() {
        return sysItemsName;
    }

    /**
     * @param 系统参数项名称 the sysItemsName to set
     */
    public void setSysItemsName(String sysItemsName) {
        this.sysItemsName = sysItemsName;
    }

    /**
     * @return the 系统参数项值
     */
    public String getSysItemsValue() {
        return sysItemsValue;
    }

    /**
     * @param 系统参数项值 the sysItemsValue to set
     */
    public void setSysItemsValue(String sysItemsValue) {
        this.sysItemsValue = sysItemsValue;
    }

	/**
     * @return the 设置原因
     */
    public String getSettingRemark() {
        return settingRemark;
    }

    /**
     * @param 设置原因 the settingRemark to set
     */
    public void setSettingRemark(String settingRemark) {
        this.settingRemark = settingRemark;
    }

    /**
	 * @return the 参数到期日期
	 */
	public String getDueDate() {
		return dueDate;
	}

	/**
	 * @param 参数到期日期 the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

    /**
     * @return the 激活状态:Y：是；N：否
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * @param 激活状态:Y：是；N：否 the isActive to set
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
	
	
}
