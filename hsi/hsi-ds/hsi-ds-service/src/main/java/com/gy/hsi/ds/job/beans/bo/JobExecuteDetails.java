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
 * 业务执行情况详情表BO类
 * 
 * @Package: com.gy.hsi.ds.job.web.service.bo  
 * @ClassName: JobExecuteDetails 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月9日 上午11:48:24 
 * @version V3.0
 */
@Table(db = "", name = "T_DS_JOB_EXECUTE_DETAILS", keyColumn = Columns.DETAILS_ID)
public class JobExecuteDetails extends BaseObject<Long> {

    /**
     * 自动生成的序列号
     */
    private static final long serialVersionUID = 3486687305360401213L;
    
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
     * 当前执行批次id
     */
    @Column(value = Columns.EXECUTE_ID)
    private String executeId;
    
    /**
     * 当前执行方式
     */
    @Column(value = Columns.EXECUTE_METHOD)
    private Integer executeMethod;
    
    /**
     * 业务最后一次执行的状态0 成功 1 失败 -1 未执行 2 执行中
     */
    @Column(value = Columns.EXECUTE_STATUS)
    private Integer serviceState;
    
    /**
     * 对应上报状态的简要描述
     */
    @Column(value = Columns.EXECUTE_STATUS_DESC)
    private String desc;
    
    /**
     * 对应上报状态的时间
     */
    @Column(value = Columns.EXECUTE_STATUS_TIME)
    private Date statusDate;
    
    /**
     * 记录创建时间
     */
    @Column(value = Columns.CREATE_DATE)
    private Date createDate;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getExecuteId() {
		return executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public Integer getExecuteMethod() {
		return executeMethod;
	}

	public void setExecuteMethod(Integer executeMethod) {
		this.executeMethod = executeMethod;
	}

	@Override
    public String toString() {
        return "JobExecuteDetails [serviceName=" + serviceName + ", serviceGroup=" + serviceGroup + ", serviceState="
                + serviceState + ", desc=" + desc + ", statusDate=" + statusDate + "]";
    }
    

}
