/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.beans.bo;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
/**
 * 
 * 前置任务后置任务表处理BO类
 * @Package: com.gy.hsi.ds.job.web.service.bo  
 * @ClassName: FrontPostJob 
 * @Description: TODO
 *
 * @author: yangyp 
 * @date: 2015年10月9日 上午11:33:16 
 * @version V3.0
 */
@Table(db = "", name = "T_DS_FRONT_POST_JOB", keyColumn = Columns.FRONT_POST_ID)
public class FrontPostJob extends BaseObject<Long> {

    /**
     * 自动生成的序列号
     */
    private static final long serialVersionUID = 9003310802227649564L;
    
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
     * 前置后置标识
     * 0 前置任务
     * 1 后置任务
     */
    @Column(value = Columns.FRONT_POST_FLAG)
    private Integer frontPostFlag;
    
    /**
     * 前后置任务业务名称
     */
    @Column(value = Columns.FP_SERVICE_NAME)
    private String fpServiceName;
    
    /**
     * 前后置任务业务所属的组，对应DUBBO服务GROUP
     */
    @Column(value = Columns.FP_SERVICE_GROUP)
    private String fpServiceGroup ;

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

    public Integer getFrontPostFlag() {
        return frontPostFlag;
    }

    public void setFrontPostFlag(Integer frontPostFlag) {
        this.frontPostFlag = frontPostFlag;
    }

    public String getFpServiceName() {
        return fpServiceName;
    }

    public void setFpServiceName(String fpServiceName) {
        this.fpServiceName = fpServiceName;
    }

    public String getFpServiceGroup() {
        return fpServiceGroup;
    }

    public void setFpServiceGroup(String fpServiceGroup) {
        this.fpServiceGroup = fpServiceGroup;
    }

    @Override
    public String toString() {
        return "FrontPostJob [serviceName=" + serviceName + ", serviceGroup=" + serviceGroup + ", frontPostFlag="
                + frontPostFlag + ", fpServiceName=" + fpServiceName + ", fpServiceGroup=" + fpServiceGroup + "]";
    }
    
}
