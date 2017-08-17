/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.bean
 * @ClassName: QuotaApp
 * @Description: 一级配额申请
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:53:11
 * @version V1.0
 */
public class QuotaAppBaseInfo implements Serializable {

    private static final long serialVersionUID = -1557978961299627761L;

    /** 申请编号 **/
    private String applyId;

    /** 管理公司互生号 **/
    private String entResNo;

    /** 管理公司名称 */
    private String entCustName;

    /** 平台代码 **/
    private String platNo;

    /** 平台名称 **/
    private String platName;

    /** 申请类型 **/
    private Integer applyType;

    /** 申请数量 **/
    private Integer applyNum;

    /** 申请时间 **/
    private String reqTime;

    /** 状态 **/
    private Integer status;

    /** 路由数据同步标识,默认false **/
    private Boolean routeSync = false;

    /** 配额分配同步标识,默认false **/
    private Boolean allotSync = false;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getRouteSync() {
        return routeSync;
    }

    public void setRouteSync(Boolean routeSync) {
        this.routeSync = routeSync;
    }

    public Boolean getAllotSync() {
        return allotSync;
    }

    public void setAllotSync(Boolean allotSync) {
        this.allotSync = allotSync;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
