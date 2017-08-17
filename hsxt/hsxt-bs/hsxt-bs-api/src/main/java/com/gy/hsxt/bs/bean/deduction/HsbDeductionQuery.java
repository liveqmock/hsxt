/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.deduction;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 互生币扣除查询实体
 *
 * @Package : com.gy.hsxt.bs.bean.deduction
 * @ClassName : HsbDeductionQuery
 * @Description : 互生币扣除查询实体
 * @Author : chenm
 * @Date : 2016/3/25 16:39
 * @Version V3.0.0.0
 */
public class HsbDeductionQuery extends Query {

    private static final long serialVersionUID = 2862466353617082513L;

    /**
     * 企业互生号
     */
    private String entResNo;

    /**
     * 企业名称
     */
    private String entCustName;


    /**
     * 审批状态 0-扣款待复核 1-扣款成功 2-扣款失败
     */
    private Integer status;

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
