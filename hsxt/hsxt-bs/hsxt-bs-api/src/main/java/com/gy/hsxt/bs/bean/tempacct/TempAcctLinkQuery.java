/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tempacct;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 临账关联查询实体
 *
 * @Package : com.gy.hsxt.bs.bean.tempacct
 * @ClassName : TempAcctLinkQuery
 * @Description : 临账关联查询实体
 * @Author : chenm
 * @Date : 2015/12/29 11:42
 * @Version V3.0.0.0
 */
public class TempAcctLinkQuery extends Query {

    private static final long serialVersionUID = -6945081585847463392L;
    /**
     * 订单编号
     **/
    private String orderNo;
    /**
     * 关联状态
     * <p/>
     * 0：待复核 1：复核通过 2：复核驳回
     *
     * @see com.gy.hsxt.bs.common.enumtype.tempacct.LinkStatus
     **/
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
