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
 *  File Name       : Unit.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 计量单位
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class Unit implements Serializable {

    private static final long serialVersionUID = 4848195214954467227L;

    // 语言代码
    private String languageCode;

    // 计量单位代码
    private String unitCode;

    // 计量单位名称
    private String unitName;

    // 删除标识
    private boolean delFlag;

    // 记录版本号
    private long version;

    public Unit() {
    }

    public Unit(String languageCode, String unitCode) {
        this.languageCode = languageCode;
        this.unitCode = unitCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
