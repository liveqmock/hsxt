/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsi.ds.job.beans;

import java.io.Serializable;

public class DubboComsumerConfig implements Serializable {

	private static final long serialVersionUID = 7658362167502951071L;

	/**
     * 应用名称
     */
    private String appName;
    
    /**
     * dubbo服务注册地址
     */
    private String regAddr;
    
    /**
     * 重试次数
     */
    private int refRetries;
    
    /**
     * 超时时间
     */
    private int refTimeout;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }

    public int getRefRetries() {
        return refRetries;
    }

    public void setRefRetries(int refRetries) {
        this.refRetries = refRetries;
    }

    public int getRefTimeout() {
        return refTimeout;
    }

    public void setRefTimeout(int refTimeout) {
        this.refTimeout = refTimeout;
    }    
}
