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
 * 协议参数管理
 * @Package: com.gy.hsxt.bp.bean  
 * @ClassName: AgreeParamManager 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-24 下午3:05:48 
 * @version V1.0 
 

 */
public class BusinessAgree implements Serializable{

    
    private static final long serialVersionUID = 8114358009329506177L;

    /** 协议ID **/
    private String     agreeId;
    
    /** 协议代码 **/
    private String     agreeCode;
    
    /** 协议名称 **/
    private String     agreeName;
    
    /** 激活状态: Y：是；N：否  **/
    private String     isActive;
    
    /** 创建者 **/
    private String     createdby;
    
    /** 创建时间 **/
    private String     createdDate;
       
    /** 更新者 **/
    private String     updatedby;
    
    /** 更新时间**/
    private String     updatedDate;
    
    /** 创建时间 **/
    private Timestamp  createdDateTim;
    
    /** 更新时间**/
    private Timestamp  updatedDateTim;
    
    /**
     * @return the 协议ID
     */
    public String getAgreeId() {
        return agreeId;
    }

    /**
     * @param 协议ID the agreeId to set
     */
    public void setAgreeId(String agreeId) {
        this.agreeId = agreeId;
    }

    /**
     * @return the 协议代码
     */
    public String getAgreeCode() {
        return agreeCode;
    }

    /**
     * @param 协议代码 the agreeCode to set
     */
    public void setAgreeCode(String agreeCode) {
        this.agreeCode = agreeCode;
    }

    /**
     * @return the 协议名称
     */
    public String getAgreeName() {
        return agreeName;
    }

    /**
     * @param 协议名称 the agreeName to set
     */
    public void setAgreeName(String agreeName) {
        this.agreeName = agreeName;
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
