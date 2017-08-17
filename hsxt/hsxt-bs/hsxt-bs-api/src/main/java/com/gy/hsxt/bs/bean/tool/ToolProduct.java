/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.Operator;

/**
 * 工具产品Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: ToolProduct
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:34:30
 * @company: gyist
 * @version V3.0.0
 */
public class ToolProduct extends Operator implements Serializable {

    private static final long serialVersionUID = 2594221356046424904L;

    /** 产品编号 **/
    private String productId;

    /** 类别代码 **/
    @NotEmpty(message = "类别代码不能为空")
    private String categoryCode;

    /** 产品名称 **/
    @NotEmpty(message = "产品名称不能为空")
    private String productName;

    /** 缩略图 **/
    private String microPic;

    /** 单价 **/
    @NotEmpty(message = "产品单价不能为空")
    private String price;

    /** 单位 **/
    private String unit;

    /** 预警值 **/
    private Integer warningValue;

    /** 工具说明 **/
    private String description;

    /** 上下架状态 0未上架(已下架)，1已上架 **/
    private Integer enableStatus;

    /** 上下架审批状态 0价格待审批 1价格审批通过 2价格审批驳回 3下架待审批 4下架审批通过 5下架审批驳回 **/
    private Integer status;

    /** 最新状态时间 */
    private String lastStatusTime;

    /** 最新申请编号 */
    private String lastApplyId;

    /** 申报时资源费包含的工具数量 **/
    private Integer quantity;

    public ToolProduct() {
        super();
    }

    public ToolProduct(String productId, String categoryCode, String productName, String microPic, String price,
            String unit, Integer warningValue, String description, Integer enableStatus, Integer status) {
        super();
        this.productId = productId;
        this.categoryCode = categoryCode;
        this.productName = productName;
        this.microPic = microPic;
        this.price = price;
        this.unit = unit;
        this.warningValue = warningValue;
        this.description = description;
        this.enableStatus = enableStatus;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMicroPic() {
        return microPic;
    }

    public void setMicroPic(String microPic) {
        this.microPic = microPic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLastStatusTime() {
        return lastStatusTime;
    }

    public void setLastStatusTime(String lastStatusTime) {
        this.lastStatusTime = lastStatusTime;
    }

    public String getLastApplyId() {
        return lastApplyId;
    }

    public void setLastApplyId(String lastApplyId) {
        this.lastApplyId = lastApplyId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
