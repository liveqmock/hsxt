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
 *  Project Name    : hsxt-gpf-gcs-api
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.bean
 * 
 *  File Name       : RouteTarget.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : yangjianguo
 * 
 *  Purpose         : 跨平台路由目标
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class RouteTarget implements Serializable {

    private static final long serialVersionUID = -5825898704298731345L;

    /**
     * 目标平台综合前置IP
     */
    private String targetIP;

    /**
     * 目标平台综合前置端口
     */
    private String targetPort;

    /**
     * 目标平台子系统代码
     */
    private String targetSubsys;

    /** 平台代码 **/
    private String platNo;

    public RouteTarget() {
    }

    public RouteTarget(String targetIP, String targetPort, String targetSubsys, String platNo) {
        this.targetIP = targetIP;
        this.targetPort = targetPort;
        this.targetSubsys = targetSubsys;
        this.platNo = platNo;
    }

    public String getTargetIP() {
        return targetIP;
    }

    public void setTargetIP(String targetIP) {
        this.targetIP = targetIP;
    }

    public String getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(String targetPort) {
        this.targetPort = targetPort;
    }

    public String getTargetSubsys() {
        return targetSubsys;
    }

    public void setTargetSubsys(String targetSubsys) {
        this.targetSubsys = targetSubsys;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
