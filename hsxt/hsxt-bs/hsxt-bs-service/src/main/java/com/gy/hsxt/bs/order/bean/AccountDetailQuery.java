/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.order.bean;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 记账分解查询实体
 *
 * @Package : com.gy.hsxt.bs.order.bean
 * @ClassName : AccountDetailQuery
 * @Description : 记账分解查询实体
 * @Author : chenm
 * @Date : 2016/1/13 15:25
 * @Version V3.0.0.0
 */
public class AccountDetailQuery extends Query {

    private static final long serialVersionUID = 3514502284083634505L;

    /**
     * 业务流水号--起始编号
     */
    private String startBizNo;

    /**
     * 业务流水号--结束编号
     */
    private String endBizNo;

    /**
     * 记账状态 true-已记账 false-未记账
     */
    private Boolean status;


    public String getStartBizNo() {
        return startBizNo;
    }

    public void setStartBizNo(String startBizNo) {
        this.startBizNo = startBizNo;
    }

    public String getEndBizNo() {
        return endBizNo;
    }

    public void setEndBizNo(String endBizNo) {
        this.endBizNo = endBizNo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
