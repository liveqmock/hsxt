/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.apply;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 模版查询实体
 *
 * @Package : com.gy.hsxt.bs.bean.apply
 * @ClassName : TempletQuery
 * @Description : 模版查询实体
 * @Author : chenm
 * @Date : 2016/3/14 11:21
 * @Version V3.0.0.0
 */
public class TempletQuery extends Query {

    private static final long serialVersionUID = -1287507843328091172L;
    /**
     * 模版名称
     */
    private String templetName;

    /**
     * 模版类型
     *
     * @see com.gy.hsxt.bs.common.enumtype.apply.TempletType
     */
    private Integer templetType;

    /**
     * 企业类型
     *
     * @see com.gy.hsxt.common.constant.CustType
     */
    private Integer custType;

    /**
     * 审批状态 0-已启用 1-待启用 2-待启用复核 3-待启用复核
     *
     * @see com.gy.hsxt.bs.common.enumtype.apply.TempletStatus
     */
    private Integer apprStatus;

    /**
     * 启用资源类型
     *
     * @see com.gy.hsxt.bs.common.enumtype.apply.ResType
     */
    private Integer resType;

    public String getTempletName() {
        return templetName;
    }

    public void setTempletName(String templetName) {
        this.templetName = templetName;
    }

    public Integer getTempletType() {
        return templetType;
    }

    public void setTempletType(Integer templetType) {
        this.templetType = templetType;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(Integer apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }
}
