/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.pageparam;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.BaseParam;

/**
 * @Package: com.gy.hsxt.bs.bean.tool.pageparam
 * @ClassName: ToolProductVo
 * @Description: 工具查询参数VO
 * @author: likui
 * @date: 2015年9月28日 下午5:41:44
 * @company: gyist
 * @version V1.0
 */
public class ToolProductVo extends BaseParam implements Serializable {

    private static final long serialVersionUID = 208448121701477019L;

    /** 工具名称 **/
    private String productName;

    /** 工具类别 **/
    private String categoryCode;

    /** 工具状态 **/
    private Integer enableStatus;

    public ToolProductVo() {
        super();
    }

    public ToolProductVo(String productName, String categoryCode, Integer enableStatus) {
        super();
        this.productName = productName;
        this.categoryCode = categoryCode;
        this.enableStatus = enableStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
