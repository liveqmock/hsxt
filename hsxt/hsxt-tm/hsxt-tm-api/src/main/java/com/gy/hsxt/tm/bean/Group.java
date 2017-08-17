/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 值班组信息实体类
 * 
 * @Package: com.gy.hsxt.tm.bean
 * @ClassName: Group
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-9 下午4:27:07
 * @version V3.0.0
 */
public class Group implements Serializable {
    private static final long serialVersionUID = 3939319868990780691L;

    /** 值班组编号 **/
    private String groupId;

    /** 企业客户号 **/
    private String entCustId;

    /** 值班组名称 **/
    private String groupName;

    /** 值班组类型 **/
    private Integer groupType;

    /** 值班组是否开启 **/
    private Boolean opened;

    /** 值班员列表 **/
    private List<Operator> operators;

    /** 功能说明 **/
    private String description;

    /** 操作员编号 **/
    private String optCustId;

    public String getOptCustId() {
        return optCustId;
    }

    public void setOptCustId(String optCustId) {
        this.optCustId = optCustId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Boolean getOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
