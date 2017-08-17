/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 通用查询条件参数实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.base
 * @ClassName: BaseParam
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-25 上午10:45:20
 * @version V1.0
 */
public class BaseParam implements Serializable {
    private static final long serialVersionUID = 5592999551965150841L;

    /** 开始日期 **/
    private String startDate;

    /** 截止时期 **/
    private String endDate;

    /** 互生号 **/
    private String hsResNo;

    /** 经办人客户号 **/
    private String exeCustId;

    /** 客户类别 **/
    private Integer custType;

    /** 客户名称 **/
    private String custName;

    /** 操作员 **/
    private String operNo;

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

    public String getHsResNo() {
        return hsResNo;
    }

    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    public String getExeCustId() {
        return exeCustId;
    }

    public void setExeCustId(String exeCustId) {
        this.exeCustId = exeCustId;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo;
    }

    /**
     * 设置查询的日期,开始日期为最小,结束日期为最大
     */
    public void setQueryDate() {
        if (StringUtils.isNotBlank(getStartDate()))
        {
            setStartDate(DateUtil.getMinDateOfDayStr(getStartDate()));
        }
        if (StringUtils.isNotBlank(getEndDate()))
        {
            setEndDate(DateUtil.getMaxDateOfDayStr(getEndDate()));
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
