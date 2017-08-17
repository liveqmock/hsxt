/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.annualfee;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 年费价格方案查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.annualfee
 * @ClassName : AnnualFeePriceQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/10 16:19
 * @Version V3.0.0.0
 */
public class AnnualFeePriceQuery extends Query {

    private static final long serialVersionUID = -1129204086238573732L;

    /**
     * 年费方案状态
     */
    private String status;

    /**
     * 企业类型
     */
    private Integer custType;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

}
