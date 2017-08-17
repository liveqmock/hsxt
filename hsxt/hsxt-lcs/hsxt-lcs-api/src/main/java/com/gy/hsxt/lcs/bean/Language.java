/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-api
 * 
 *  Package Name    : com.gy.hsxt.lcs.bean
 * 
 *  File Name       : Language.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 语言
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class Language implements Serializable {

    private static final long serialVersionUID = 5634715666768087816L;

    // 语言代码
    private String languageCode;

    // 语言中文名
    private String chineseName;

    // 语言名称/语言本地名称
    private String localName;

    // 删除标识
    private boolean delFlag;

    // 记录版本号
    private long version;

    public Language() {
    }

    public Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
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
