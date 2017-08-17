/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.bean;

import java.io.Serializable;

import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;

/***
 * 获取PMK密钥实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.posInterface
 * @ClassName: PMKSecretKeyBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-24 下午2:55:51
 * @version V1.0
 */
public class PmkSecretKeyParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 平台系统给企业设备定义的4位数编号
     */
    private String deviceNo;

    /**
     * 机器码 厂家定义的编号
     */
    private String machineNo;

    /**
     * 是否为新版本, true:新版本 false:旧版本
     */
    private boolean isNewVersion;

    /**
     * 操作者客户号
     */
    private String operCustId;
    
    /**
     * 企业客户号
     */
    private String entCustId;
   
    /**
     * 配置编号
     */
    private String confNo;
    /**
     * 新设备编号，机器编号
     */
    private String newDeviceSeqNo;
    
    
    
    /**
	 * @return the 新设备编号，机器编号
	 */
	public String getNewDeviceSeqNo() {
		return newDeviceSeqNo;
	}

	/**
	 * @param 新设备编号，机器编号 the newDeviceSeqNo to set
	 */
	public void setNewDeviceSeqNo(String newDeviceSeqNo) {
		this.newDeviceSeqNo = newDeviceSeqNo;
	}

	/**
	 * @return the 配置编号
	 */
	public String getConfNo() {
		return confNo;
	}

	/**
	 * @param 配置编号 the confNo to set
	 */
	public void setConfNo(String confNo) {
		this.confNo = confNo;
	}

	/**
	 * @return 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
     * 验证数据有效性
     */
    public void checkData() {
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.entCustId, RespCode.APS_ENTRESNO_NOT_NULL.getCode(),
                RespCode.APS_ENTRESNO_NOT_NULL.getDesc() }, new Object[] { this.deviceNo,
                RespCode.APS_DEVICENO_NOT_NULL.getCode(), RespCode.APS_DEVICENO_NOT_NULL.getDesc() }, new Object[] {
                this.machineNo, RespCode.APS_MACHINENO_NOT_NULL.getCode(), RespCode.APS_MACHINENO_NOT_NULL.getDesc() },
                new Object[] { this.operCustId, RespCode.APS_OPERCUSTID_NOT_NULL.getCode(),
                        RespCode.APS_OPERCUSTID_NOT_NULL.getDesc() });
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public boolean isNewVersion() {
        return isNewVersion;
    }

    public void setNewVersion(boolean isNewVersion) {
        this.isNewVersion = isNewVersion;
    }

    public String getOperCustId() {
        return operCustId;
    }

    public void setOperCustId(String operCustId) {
        this.operCustId = operCustId;
    }

}
