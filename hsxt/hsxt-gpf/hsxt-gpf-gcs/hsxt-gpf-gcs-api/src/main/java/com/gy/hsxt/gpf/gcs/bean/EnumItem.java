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
 *  File Name       : EnumItem.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 枚举信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class EnumItem implements Serializable {

    private static final long serialVersionUID = 6117643267479609180L;

    // 语言代码
    private String languageCode;

    // 枚举组类型
    private String groupCode;

    // 枚举值代码
    private String itemCode;

    // 枚举值显示名
    private String itemName;

    // 枚举值内容
    private String itemValue;

    // 删除标识
    private boolean delFlag;

    // 记录版本号
    private long version;

    public EnumItem() {
    }

    public EnumItem(String languageCode, String groupCode, String itemCode) {
        this.languageCode = languageCode;
        this.groupCode = groupCode;
        this.itemCode = itemCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
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
