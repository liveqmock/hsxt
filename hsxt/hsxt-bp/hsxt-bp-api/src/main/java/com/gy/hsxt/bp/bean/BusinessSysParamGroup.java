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
 * 系统参数组对象
 * @author 作者 : weixz
 * @ClassName: 类名:SysParamGroup
 * @Description: TODO
 * @createDate 创建时间: 2015-11-20 上午10:35:52
 * @version 1.0
 */
public class BusinessSysParamGroup implements Serializable{


    private static final long serialVersionUID = -7503846847000159560L;

    /** 系统参数组编号 **/
	private String     sysGroupCode;
	
	/** 系统参数组名称 **/
	private String     sysGroupName;
	
	/** 参数类型：是否公共，Y：是；N：否  **/
	private String     isPublic;
	
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
     * @return the 参数类型：是否公共，Y：是；N：否
     */
    public String getIsPublic() {
        return isPublic;
    }

    /**
     * @param 参数类型：是否公共，Y：是；N：否 the isPublic to set
     */
    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
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
