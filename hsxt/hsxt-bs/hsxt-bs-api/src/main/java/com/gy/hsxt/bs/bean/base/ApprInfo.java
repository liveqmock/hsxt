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

package com.gy.hsxt.bs.bean.base;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 通用审批信息对象
 * 
 * @Package: com.gy.hsxt.bs.bean.base
 * @ClassName: ApprInfo
 * @author: yangjianguo
 * @date: 2016-2-27 下午6:44:58
 * @version V1.0
 */
public class ApprInfo extends Operator {
    private static final long serialVersionUID = 8054969894071764427L;

    /**
     * 申请单编号
     */
    @NotEmpty(message = "申请编号不能为空")
    private String applyId;

    /**
     * 审批信息、审批意见等
     */
    private String apprRemark;

    /**
     * 审批结果 true：通过 false：驳回
     */
    @NotNull(message = "审批结果不能为空")
    private Boolean isPass;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApprRemark() {
        return apprRemark;
    }

    public void setApprRemark(String apprRemark) {
        this.apprRemark = apprRemark;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean isPass) {
        this.isPass = isPass;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
