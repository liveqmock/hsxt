/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 工具产品上下架审批记录
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ToolProductUpDown
 * @author: yangjianguo
 * @date: 2016-2-26 下午2:39:44
 * @version V1.0
 */
public class ToolProductUpDown implements Serializable {
    private static final long serialVersionUID = -6878335602902991228L;

    /**
     * 申请编号
     */
    private String applyId;

    /**
     * 工具产品编号
     */
    private String productId;

    /**
     * 申请类型：1：上架审批(包含价格调整)2：下架审批
     */
    private Integer applyType;

    /**
     * 工具旧的价格，价格调整时使用
     */
    private String oldPrice;

    /**
     * 申请上架价格，价格调整时是新价格，新增时是工具价格
     */
    private String applyPrice;

    /**
     * 申请下架时输入的下架原因
     */
    private String downReason;

    /**
     * 状态：0价格待审批 1价格审批通过 2价格审批驳回 3下架待审批 4下架审批通过 5下架审批驳回
     */
    private Integer status;

    /**
     * 状态时间
     */
    private String statusTime;

    /**
     * 申请操作员编号
     */
    private String reqOptId;

    /**
     * 申请操作员名称
     */
    private String reqOptName;

    /**
     * 申请时间
     */
    private String reqTime;

    /**
     * 审批操作员编号
     */
    private String apprOptId;

    /**
     * 审批操作员名称
     */
    private String apprOptName;

    /**
     * 审批时间
     */
    private String apprTime;

    /**
     * 审批信息
     */
    private String apprRemark;

    // 列表查询关联属性
    /**
     * 工具类别名称
     */
    private String toolCategoryName;

    /**
     * 工具产品名称
     */
    private String toolProductName;

    /**
     * 工具说明
     */
    private String toolDescription;

    /**
     * 工具单位
     */
    private String toolUnit;

    /**
     * 工具缩略图
     */
    private String toolMicroPic;

    /** 上下架状态 0未上架(已下架)，1已上架 **/
    private Integer enableStatus;

    /** 工具价格 **/
    private String price;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(String applyPrice) {
        this.applyPrice = applyPrice;
    }

    public String getDownReason() {
        return downReason;
    }

    public void setDownReason(String downReason) {
        this.downReason = downReason;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getReqOptId() {
        return reqOptId;
    }

    public void setReqOptId(String reqOptId) {
        this.reqOptId = reqOptId;
    }

    public String getReqOptName() {
        return reqOptName;
    }

    public void setReqOptName(String reqOptName) {
        this.reqOptName = reqOptName;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getApprOptId() {
        return apprOptId;
    }

    public void setApprOptId(String apprOptId) {
        this.apprOptId = apprOptId;
    }

    public String getApprOptName() {
        return apprOptName;
    }

    public void setApprOptName(String apprOptName) {
        this.apprOptName = apprOptName;
    }

    public String getApprTime() {
        return apprTime;
    }

    public void setApprTime(String apprTime) {
        this.apprTime = apprTime;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    public String getToolCategoryName() {
        return toolCategoryName;
    }

    public void setToolCategoryName(String toolCategoryName) {
        this.toolCategoryName = toolCategoryName;
    }

    public String getToolProductName() {
        return toolProductName;
    }

    public void setToolProductName(String toolProductName) {
        this.toolProductName = toolProductName;
    }

    public String getToolDescription() {
        return toolDescription;
    }

    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
    }

    public String getToolUnit() {
        return toolUnit;
    }

    public void setToolUnit(String toolUnit) {
        this.toolUnit = toolUnit;
    }

    public String getToolMicroPic() {
        return toolMicroPic;
    }

    public void setToolMicroPic(String toolMicroPic) {
        this.toolMicroPic = toolMicroPic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
