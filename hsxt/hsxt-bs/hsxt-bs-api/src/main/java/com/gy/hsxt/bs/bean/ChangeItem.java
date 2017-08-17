/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: ChangeItem
 * @Description: 变更项信息
 * 
 * @author: xiaofl
 * @date: 2015-9-9 上午9:52:41
 * @version V1.0
 */
public class ChangeItem implements Serializable {

    private static final long serialVersionUID = 7018694486551743518L;

    /** 变更的属性名 **/
    private String property;

    /** 变更前的值 **/
    private String oldValue;

    /** 变更后的值 **/
    private String newValue;

    public ChangeItem() {
    }

    public ChangeItem(String property, String oldValue, String newValue) {
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
