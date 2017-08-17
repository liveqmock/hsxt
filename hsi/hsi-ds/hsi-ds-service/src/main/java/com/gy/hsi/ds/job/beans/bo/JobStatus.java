/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.beans.bo;

import java.util.Date;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
/**
 * 
 * 调度中心管理的所有业务表BO类
 * @Package: com.gy.hsi.ds.job.web.service.bo  
 * @ClassName: JobStatus 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月9日 上午11:40:27 
 * @version V3.0
 */
@Table(db = "", name = "T_DS_JOB_STATES", keyColumn = Columns.JOB_STATES_ID)
public class JobStatus extends BaseObject<Long> {

    /**
     * 自动生成的序列号
     */
    private static final long serialVersionUID = 6219549366665155172L;
    
    /**
     * 业务名
     */
    @Column(value = Columns.SERVICE_NAME)
    private String serviceName;
    
    /**
     * 业务所属的组，对应DUBBO服务GROUP
     */
    @Column(value = Columns.SERVICE_GROUP)
    private String serviceGroup;
    
    /**
     * dubbo服务的版本号
     */
    @Column(value = Columns.DUBBO_PROVIDER_VERSION)
    private String version;
    
    
    /**
     * 调用dubbo服务所带的参数，格式：key=value;key1=value1;key2=value2
     */
    @Column(value = Columns.SERVICE_PARA)
    private String servicePara;
    
    /**
     * 业务功能的简要描述
     */
    @Column(value = Columns.DESCRIPTION)
    private String desc;
    
    /**
     * 业务最后一次执行的状态0 成功 1 失败 -1 未执行
     */
    @Column(value = Columns.SERVICE_STATE)
    private Integer serviceState;
    
    /**
     * 是否已存在定时任务标识0 存在 1 不存在
     */
    @Column(value = Columns.HAS_TASK_FLAG)
    private Integer hasTaskFlag; 

    /**
     * 最后状态更新时间
     */
    @Column(value = Columns.STATE_UPDATE_TIME)
    private Date stateUpdateTime; 
    
    /**
     * 最后一次执行id
     */
    @Column(value = Columns.LAST_EXECUTE_ID)
    private String lastExecuteId;
    
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

    public Integer getServiceState() {
        return serviceState;
    }

    public void setServiceState(Integer serviceState) {
        this.serviceState = serviceState;
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

    public Integer getHasTaskFlag() {
        return hasTaskFlag;
    }

    public void setHasTaskFlag(Integer hasTaskFlag) {
        this.hasTaskFlag = hasTaskFlag;
    }

    public Date getStateUpdateTime() {
        return stateUpdateTime;
    }

    public void setStateUpdateTime(Date stateUpdateTime) {
        this.stateUpdateTime = stateUpdateTime;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public String getLastExecuteId() {
		return lastExecuteId;
	}

	public void setLastExecuteId(String lastExecuteId) {
		this.lastExecuteId = lastExecuteId;
	}

	@Override
    public String toString() {
        return "JobStatus [serviceName=" + serviceName + ", serviceGroup=" + serviceGroup + ", version=" + version
                + ", servicePara=" + servicePara + ", desc=" + desc + ", serviceState=" + serviceState
                + ", hasTaskFlag=" + hasTaskFlag + ", stateUpdateTime=" + stateUpdateTime + "]";
    }
}
