/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-api
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.bean
 * 
 *  File Name       : PointAssignRule.java
 * 
 *  Creation Date   : 2015-7-21
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 积分分配比例
 * 
 * 
 *  History         : 2015/12/21 by yangjianguo 积分分配规则不由全局配置维护
 * 
 * </PRE>
 ***************************************************************************/
@Deprecated
public class PointAssignRule implements Serializable {

    private static final long serialVersionUID = 8511335763374336646L;

    // 分配对象 P:个人 T:托管 S:服务 M:管理
    private String targetType;

    // 账户类型 积分：10110 待分配积分：10111
    private String actType;

    // 分配比例
    private BigDecimal assignRate;

    // 分配方式 1:立即分配 2:日终分配3:日终合并分配
    private String assignMethod;

    // 删除标识
    private boolean delFlag;

    // 记录版本号
    private long version;

    public PointAssignRule() {
    }

    public PointAssignRule(String targetType, String actType) {
        this.targetType = targetType;
        this.actType = actType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public BigDecimal getAssignRate() {
        return assignRate;
    }

    public void setAssignRate(BigDecimal assignRate) {
        this.assignRate = assignRate;
    }

    public String getAssignMethod() {
        return assignMethod;
    }

    public void setAssignMethod(String assignMethod) {
        this.assignMethod = assignMethod;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
