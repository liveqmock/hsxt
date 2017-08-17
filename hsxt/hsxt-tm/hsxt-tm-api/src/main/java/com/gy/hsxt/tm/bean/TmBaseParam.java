/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 通用查询条件参数实体类
 * 
 * @Package: com.gy.hsxt.bs.bean.base
 * @ClassName: BaseBean
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-25 上午10:45:20
 * @version V1.0
 */
public class TmBaseParam implements Serializable {
    private static final long serialVersionUID = 5592999551965150841L;

    /** 开始日期 **/
    private String startDate;

    /** 截止时期 **/
    private String endDate;

    /** 互生号 **/
    private String hsResNo;

    /** 执行操作员 **/
    private String executor;

    /** 企业客户号 **/
    private String entCustId;

    /** 状态 **/
    private Integer status;

    /** 业务类型 **/
    private Integer bizType;

    /** 客户名称 **/
    private String custName;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
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

    public String getHsResNo() {
        return hsResNo;
    }

    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 设置查询的日期,开始日期为最小,结束日期为最大
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月14日 下午7:53:21
     * @param :
     * @return : void
     * @version V3.0.0
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
