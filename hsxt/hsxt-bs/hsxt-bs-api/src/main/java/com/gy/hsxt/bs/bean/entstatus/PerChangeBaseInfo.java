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
 * @ClassName: PerChangeBaseInfo
 * @Description: 消费者重要信息变更基本信息
 * 
 * @author: xiaofl
 * @date: 2015-9-6 下午2:49:43
 * @version V1.0
 */
public class PerChangeBaseInfo implements Serializable {

    private static final long serialVersionUID = 1377481709926184958L;

    /** 申请编号 **/
    private String applyId;

    /** 互生卡号 **/
    private String perResNo;

    /** 联系人姓名 **/
    private String linkman;

    /** 联系人手机 **/
    private String linkmanMobile;

    /** 变更信息 **/
    private String changeItem;

    /** 变更原因 **/
    private String changeReason;

    /** 申请时间 **/
    private String applyDate;

    /** 状态 **/
    private Integer status;

    /*** 状态日期 */
    private String statusDate;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getPerResNo() {
        return perResNo;
    }

    public void setPerResNo(String perResNo) {
        this.perResNo = perResNo;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getChangeItem() {
        return changeItem;
    }

    public void setChangeItem(String changeItem) {
        this.changeItem = changeItem;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
