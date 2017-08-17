/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bp.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * 
 * POS机升级信息实体
 * @Package: com.gy.hsxt.bp.bean  
 * @ClassName: BusinessPosUpgrade 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-4-9 上午11:13:37 
 * @version V1.0
 */
public class BusinessPosUpgrade implements Serializable{

    private static final long serialVersionUID = -3352438874042030699L;

    /** POS机升级信息ID **/
    private String     posId;
    
    /** POS机型号 **/
    private String     posDeviceType;
    
    /** POS机器号 **/
    private String     posMachineNo;
    
    /** 是否更新标记: Y：是；N：否'  **/
    private String     isUpgrade;
    
    /** 版本是否强制更新标记 Y：是；N：否' **/
    private String     isForceUpgrade;
    
    /** 需更新的固件版本号 **/
    private String     posUpgradeVerNo;
    
    /** 固件版本文件的CRC校验码**/
    private String     fileCrcCode;
    
    /** 固件版本文件下载的http地址 **/
    private String  fileHttpUrl;
    
    /** 版本文件的字节数 **/
    private int  fileBytes;
    
    /** 创建时间 **/
    private Timestamp  createdDate;
    
    /** 开始时间 **/
    private String  beginDate;
    
    /** 结束时间 **/
    private String  endDate;
    
    

    /**
     * @return the POS机升级信息ID
     */
    public String getPosId() {
        return posId;
    }

    /**
     * @param POS机升级信息ID the posId to set
     */
    public void setPosId(String posId) {
        this.posId = posId;
    }

    /**
     * @return the 是否更新标记:Y：是；N：否'
     */
    public String getIsUpgrade() {
        return isUpgrade;
    }

    /**
     * @param 是否更新标记:Y：是；N：否' the isUpgrade to set
     */
    public void setIsUpgrade(String isUpgrade) {
        this.isUpgrade = isUpgrade;
    }

    /**
     * @return the 版本是否强制更新标记Y：是；N：否'
     */
    public String getIsForceUpgrade() {
        return isForceUpgrade;
    }

    /**
     * @param 版本是否强制更新标记Y：是；N：否' the isForceUpgrade to set
     */
    public void setIsForceUpgrade(String isForceUpgrade) {
        this.isForceUpgrade = isForceUpgrade;
    }

    /**
     * @return the pos机型号
     */
    public String getPosDeviceType() {
        return posDeviceType;
    }

    /**
     * @param pos机型号 the posDeviceType to set
     */
    public void setPosDeviceType(String posDeviceType) {
        this.posDeviceType = posDeviceType;
    }
    
    /**
     * @return the POS机器号
     */
    public String getPosMachineNo() {
        return posMachineNo;
    }

    /**
     * @param POS机器号 the posMachineNo to set
     */
    public void setPosMachineNo(String posMachineNo) {
        this.posMachineNo = posMachineNo;
    }

    /**
     * @return the 需更新的固件版本号
     */
    public String getPosUpgradeVerNo() {
        return posUpgradeVerNo;
    }

    /**
     * @param 需更新的固件版本号 the posUpgradeVerNo to set
     */
    public void setPosUpgradeVerNo(String posUpgradeVerNo) {
        this.posUpgradeVerNo = posUpgradeVerNo;
    }

    /**
     * @return the 固件版本文件的CRC校验码
     */
    public String getFileCrcCode() {
        return fileCrcCode;
    }

    /**
     * @param 固件版本文件的CRC校验码 the fileCrcCode to set
     */
    public void setFileCrcCode(String fileCrcCode) {
        this.fileCrcCode = fileCrcCode;
    }

    /**
     * @return the 固件版本文件下载的http地址
     */
    public String getFileHttpUrl() {
        return fileHttpUrl;
    }

    /**
     * @param 固件版本文件下载的http地址 the fileHttpUrl to set
     */
    public void setFileHttpUrl(String fileHttpUrl) {
        this.fileHttpUrl = fileHttpUrl;
    }

    /**
     * @return the 版本文件的字节数
     */
    public int getFileBytes() {
        return fileBytes;
    }

    /**
     * @param 版本文件的字节数 the fileBytes to set
     */
    public void setFileBytes(int fileBytes) {
        this.fileBytes = fileBytes;
    }

    /**
     * @return the 创建时间
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @param 创建时间 the createDate to set
     */
    public void setCreateDate(Timestamp createdDate) {
        this.createdDate = createdDate;
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
    
}
