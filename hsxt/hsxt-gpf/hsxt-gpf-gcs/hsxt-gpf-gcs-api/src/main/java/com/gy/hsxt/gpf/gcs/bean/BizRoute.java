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

import com.alibaba.fastjson.JSON;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gl-api
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.bean
 * 
 *  File Name       : BizRoute.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 业务路由
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class BizRoute implements Serializable {

    private static final long serialVersionUID = 3145919653669891168L;

    // 业务代码
    private String bizCode;

    // 系统代码
    private String subSysCode;

    // 业务代码中文名
    private String bizNameCn;

    // 删除标识
    private boolean delFlag;

    // 记录版本号
    private long version;

    public BizRoute() {
    }

    public BizRoute(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getSubSysCode() {
        return subSysCode;
    }

    public void setSubSysCode(String subSysCode) {
        this.subSysCode = subSysCode;
    }

    public String getBizNameCn() {
        return bizNameCn;
    }

    public void setBizNameCn(String bizNameCn) {
        this.bizNameCn = bizNameCn;
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
