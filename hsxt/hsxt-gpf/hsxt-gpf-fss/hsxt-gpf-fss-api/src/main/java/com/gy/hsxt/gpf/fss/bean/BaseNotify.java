/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @Package :com.gy.hsxt.gpf.fss.bean
 * @ClassName : BaseNotify
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/26 14:38
 * @Version V3.0.0.0
 */
public abstract class BaseNotify implements Serializable {

    private static final long serialVersionUID = 4864988005885785178L;
    /**
     * 通知来源平台
     */
    private String fromPlat;

    /**
     * 通知目的平台
     */
    private String toPlat;

    /**
     * 通知类别
     * @see com.gy.hsxt.gpf.fss.constant.FssNotifyType
     */
    private int notifyType;

    public String getFromPlat() {
        return fromPlat;
    }

    public void setFromPlat(String fromPlat) {
        this.fromPlat = fromPlat;
    }

    public String getToPlat() {
        return toPlat;
    }

    public void setToPlat(String toPlat) {
        this.toPlat = toPlat;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
