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
import java.sql.Timestamp;

/** 
 * 系统参数项对象
 * @Package: com.gy.hsxt.bp.bean  
 * @ClassName: SysParamItems 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-20 下午12:03:11 
 * @version V1.0 
 

 */
public class BusinessSysParamItems implements Serializable{

    
    private static final long serialVersionUID = -4108892770618994525L;

    /** 系统参数项编号 **/
    private String     sysItemsCode;
    
    /** 系统参数组编号 **/
    private String     sysGroupCode;
    
    /** 系统参数组名称 **/
    private String		sysGroupName;
    
    /** 系统参数项键 **/
    private String     sysItemsKey;
    
    /** 系统参数项键名称 **/
    private String     sysItemsName;
    
    /** 系统参数项值 **/
    private String     sysItemsValue;
    
    /** 访问级别：
        0-不可见；如密码，证书等
        1-可查看不可修改
        2-可修改不可删除
        3-可修改可删除
    **/
    private int        accessLevel;
    
    /** 激活状态: Y：是；N：否  **/
    private String     isActive;
    
    /** 创建者 **/
    private String     createdby;
    
    /** 创建时间 **/
    private String     createdDate;
       
    /** 更新者 **/
    private String    updatedby;
    
    /** 更新时间**/
    private String     updatedDate;
    
    /** 创建时间 **/
    private Timestamp  createdDateTim;
    
    /** 更新时间**/
    private Timestamp  updatedDateTim;

    
    
    /**
     * @return the 系统参数项编号
     */
    public String getSysItemsCode() {
        return sysItemsCode;
    }

    /**
     * @param 系统参数项编号 the sysItemsCode to set
     */
    public void setSysItemsCode(String sysItemsCode) {
        this.sysItemsCode = sysItemsCode;
    }

    /**
     * @return the 系统参数组编号
     */
    public String getSysGroupCode() {
        return sysGroupCode;
    }

    /**
     * @param 系统参数组编号 the sysGroupCode to set
     */
    public void setSysGroupCode(String sysGroupCode) {
        this.sysGroupCode = sysGroupCode;
    }

    /**
	 * @return the 系统参数组名称
	 */
	public String getSysGroupName() {
		return sysGroupName;
	}

	/**
	 * @param 系统参数组名称 the sysGroupName to set
	 */
	public void setSysGroupName(String sysGroupName) {
		this.sysGroupName = sysGroupName;
	}

	/**
     * @return the 系统参数项键名
     */
    public String getSysItemsKey() {
        return sysItemsKey;
    }

    /**
     * @param 系统参数项键名 the sysItemsKey to set
     */
    public void setSysItemsKey(String sysItemsKey) {
        this.sysItemsKey = sysItemsKey;
    }

    /**
     * @return the 系统参数项键名
     */
    public String getSysItemsName() {
        return sysItemsName;
    }

    /**
     * @param 系统参数项键名 the sysItemsName to set
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
     * @return the 访问级别：0-不可见；如密码，证书等1-可查看不可修改2-可修改不可删除3-可修改可删除
     */
    public int getAccessLevel() {
        return accessLevel;
    }

    /**
     * @param 访问级别：0-不可见；如密码，证书等1-可查看不可修改2-可修改不可删除3-可修改可删除 the accessLevel to set
     */
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
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

    /**
     * @return the 创建者
     */
    public String getCreatedby() {
        return createdby;
    }

    /**
     * @param 创建者 the createdby to set
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    /**
     * @return the 创建时间
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param 创建时间 the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the 更新者
     */
    public String getUpdatedby() {
        return updatedby;
    }

    /**
     * @param 更新者 the updatedby to set
     */
    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    /**
     * @return the 更新时间
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param 更新时间 the updatedDate to set
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the 创建时间
     */
    public Timestamp getCreatedDateTim() {
        return createdDateTim;
    }

    /**
     * @param 创建时间 the createdDateTim to set
     */
    public void setCreatedDateTim(Timestamp createdDateTim) {
        this.createdDateTim = createdDateTim;
    }

    /**
     * @return the 更新时间
     */
    public Timestamp getUpdatedDateTim() {
        return updatedDateTim;
    }

    /**
     * @param 更新时间 the updatedDateTim to set
     */
    public void setUpdatedDateTim(Timestamp updatedDateTim) {
        this.updatedDateTim = updatedDateTim;
    }
    
}
