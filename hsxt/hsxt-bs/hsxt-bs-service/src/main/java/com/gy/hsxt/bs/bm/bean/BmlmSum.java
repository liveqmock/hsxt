/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.bean;

import com.alibaba.fastjson.JSON;

/**
 * 服务公司申报数量统计
 *
 * @Package :com.gy.hsxt.bs.bm.bean
 * @ClassName : BmlmSum
 * @Description : 服务公司申报数量统计
 * @Author : chenm
 * @Date : 2015/11/10 12:29
 * @Version V3.0.0.0
 */
public class BmlmSum {

    /**
     * 客户号
     */
    private String custId;

    /**
     * 申报数量
     */
    private int sum;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
