/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.bm;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 再增值积分查询实体
 *
 * @Package : com.gy.hsxt.bs.bean.bm
 * @ClassName : BmlmQuery
 * @Description : 再增值积分查询实体
 * @Author : chenm
 * @Date : 2016/5/14 10:07
 * @Version V3.0.0.0
 */
public class BmlmQuery extends Query {

    private static final long serialVersionUID = -4309834284605393304L;
    /**
     * 企业互生号
     */
    private String resNo;
    /**
     * 企业客户号
     */
    private String custId;

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
