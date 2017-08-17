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
 * @ClassName: QuotaAppParam
 * @Description: 配额查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:53:33
 * @version V1.0
 */
public class QuotaAppParam implements Serializable {

    private static final long serialVersionUID = 2224894798980853477L;

    /** 开始时间 **/
    private String startDate;

    /** 结束时间 **/
    private String endDate;

    /** 平台代码 **/
    private String platNo;

    /** 管理公司名称 **/
    private String entCustName;

    /** 状态 **/
    private Integer status;

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

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
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
