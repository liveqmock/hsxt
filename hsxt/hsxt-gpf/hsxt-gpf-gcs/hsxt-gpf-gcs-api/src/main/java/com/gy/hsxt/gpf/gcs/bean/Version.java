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
 *  File Name       : Version.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 版本
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class Version implements Serializable {

    private static final long serialVersionUID = 4656833005527626823L;

    // 版本代码
    private String versionCode;

    // 记录版本号
    private long version;

    // 通知标识
    private boolean notifyFlag;

    public Version() {
    }

    public Version(String versionCode, Long version, boolean notifyFlag) {
        this.versionCode = versionCode;
        this.version = version;
        this.notifyFlag = notifyFlag;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isNotifyFlag() {
        return notifyFlag;
    }

    public void setNotifyFlag(boolean notifyFlag) {
        this.notifyFlag = notifyFlag;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
