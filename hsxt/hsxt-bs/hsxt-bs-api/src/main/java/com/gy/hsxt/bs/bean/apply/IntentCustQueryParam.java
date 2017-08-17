/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.apply;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.apply
 * @ClassName: IntentCustQueryParam
 * @Description: 意向客户查询信息
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:23:22
 * @version V1.0
 */
public class IntentCustQueryParam implements Serializable {

    private static final long serialVersionUID = 3849768765655520245L;

    /** 服务公司互生号 */
    private String serEntResNo;

    /** 开始申请时间 */
    private String startDate;

    /** 结束申请时间 */
    private String endDate;

    /** 申报类别 */
    private Integer appType;

    /** 企业名称 */
    private String entCustName;

    /** 状态 */
    private Integer status;

    public String getSerEntResNo() {
        return serEntResNo;
    }

    public void setSerEntResNo(String serEntResNo) {
        this.serEntResNo = serEntResNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
