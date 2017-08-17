/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.invoice;

/**
 * 平台发票
 *
 * @Package :com.gy.hsxt.bs.bean.invoice
 * @ClassName : InvoicePlat
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/16 14:39
 * @Version V3.0.0.0
 */
public class InvoicePlat extends Invoice {

    private static final long serialVersionUID = 7562087820555606654L;
    /**
     * 业务类型
     *
     * @see com.gy.hsxt.bs.common.enumtype.invoice.BizType
     */
    private String bizType;

    /**
     * 申请时间
     */
    private String applyTime;
    /**
     * 申请操作员
     */
    private String applyOperator;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyOperator() {
        return applyOperator;
    }

    public void setApplyOperator(String applyOperator) {
        this.applyOperator = applyOperator;
    }
}
