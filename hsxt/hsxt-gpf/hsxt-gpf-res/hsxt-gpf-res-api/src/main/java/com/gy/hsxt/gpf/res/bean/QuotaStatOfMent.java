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
 * @ClassName: StatQuotaOfMent
 * @Description: 管理公司配额统计
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:54:05
 * @version V1.0
 */
public class QuotaStatOfMent implements Serializable {

    private static final long serialVersionUID = 4477504767361121149L;

    /** 管理公司互生号 **/
    private String entResNo;

    /** 管理公司名称 **/
    private String entCustName;

    /** 管理公司初始配额 **/
    private int initQuota;

    /** 已分配服务公司数 **/
    private int assigned;

    /** 审批中服务公司数 **/
    private int approving;

    /** 可申请服务公司数 **/
    private int available;

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

    public int getInitQuota() {
        return initQuota;
    }

    public void setInitQuota(int initQuota) {
        this.initQuota = initQuota;
    }

    public int getAssigned() {
        return assigned;
    }

    public void setAssigned(int assigned) {
        this.assigned = assigned;
    }

    public int getApproving() {
        return approving;
    }

    public void setApproving(int approving) {
        this.approving = approving;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
