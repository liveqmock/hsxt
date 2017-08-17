/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.entstatus;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.bs.bean.entstatus
 * @ClassName: PointActivityQueryParam
 * @Description: 积分活动查询参数
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:41:18
 * @version V1.0
 */
public class PointActivityQueryParam implements Serializable {

    private static final long serialVersionUID = 2439082273368825734L;

    /** 服务公司互生号 **/
    private String serResNo;

    /** 申请类别 **/
    private Integer applyType;

    /** 企业互生号 **/
    private String entResNo;

    /** 企业名称 **/
    private String entName;

    /** 联系人 **/
    private String linkman;

    /** 状态 **/
    private Integer status;

    /** 操作员客户号 **/
    private String optCustId;

    public String getSerResNo() {
        return serResNo;
    }

    public void setSerResNo(String serResNo) {
        this.serResNo = serResNo;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
