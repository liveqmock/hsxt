/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.beans.vo;

/**
 * 业务VO类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.business.vo  
 * @ClassName: BusinessVo 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月13日 下午5:34:49 
 * @version V3.0
 */
public class BusinessVo {
    
    /**
     * 业务ID
     */
    private Long busId;
        
    /**
     * 业务名
     */
    private String serviceName;
    
    /**
     * 业务所属的组，对应DUBBO服务GROUP
     */
    private String serviceGroup;
    
    
    /**
     * 调用dubbo服务所带的参数，格式：key=value;key1=value1;key2=value2
     */
    private String servicePara;
    
    /**
     * 业务功能的简要描述
     */
    private String desc;
    
    /**
     * 业务最后一次执行的状态
     */
    private String serviceState;
    
    private String lastExcecuteId;
    
    /**
     * 是否已存在定时任务标识0 存在 1 不存在
     */
    private Integer hasTaskFlag; 

    /**
     * 状态最后更新时间
     */
    private String stateUpdateTime;
    
    /**
     * DUBBO服务版本
     */
    private String version;
    
    private String fontJobServiceGroup;
    
    private String postJobServiceGroup;
    
    public String getFontJobServiceGroup() {
		return fontJobServiceGroup;
	}

	public void setFontJobServiceGroup(String fontJobServiceGroup) {
		this.fontJobServiceGroup = fontJobServiceGroup;
	}
    
    public String getPostJobServiceGroup() {
		return postJobServiceGroup;
	}

	public void setPostJobServiceGroup(String postJobServiceGroup) {
		this.postJobServiceGroup = postJobServiceGroup;
	}

	public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(String serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public String getServicePara() {
        return servicePara;
    }

    public void setServicePara(String servicePara) {
        this.servicePara = servicePara;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getServiceState() {
        return serviceState;
    }

    public void setServiceState(String serviceState) {
        this.serviceState = serviceState;
    }

    public Integer getHasTaskFlag() {
        return hasTaskFlag;
    }

    public void setHasTaskFlag(Integer hasTaskFlag) {
        this.hasTaskFlag = hasTaskFlag;
    }

    public String getStateUpdateTime() {
        return stateUpdateTime;
    }

    public void setStateUpdateTime(String stateUpdateTime) {
        this.stateUpdateTime = stateUpdateTime;
    }    

    public String getLastExcecuteId() {
		return lastExcecuteId;
	}

	public void setLastExcecuteId(String lastExcecuteId) {
		this.lastExcecuteId = lastExcecuteId;
	}

	@Override
	public String toString() {
		return "BusinessVo [busId=" + busId + ", serviceName=" + serviceName
				+ ", serviceGroup=" + serviceGroup + ", servicePara="
				+ servicePara + ", desc=" + desc + ", serviceState="
				+ serviceState + ", lastExcecuteId=" + lastExcecuteId
				+ ", hasTaskFlag=" + hasTaskFlag + ", stateUpdateTime="
				+ stateUpdateTime + ", version=" + version
				+ ", fontJobServiceGroup=" + fontJobServiceGroup
				+ ", postJobServiceGroup=" + postJobServiceGroup + "]";
	}

}
