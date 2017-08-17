/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.resfee;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * @Package : com.gy.hsxt.bs.bean.resfee
 * @ClassName : ResFeeQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/1/22 14:55
 * @Version V3.0.0.0
 */
public class ResFeeQuery extends Query {

    private static final long serialVersionUID = -2431064796079451326L;
    /**
     * 被申报企业客户类型
     * <p/>
     * 2成员企业 3托管企业 4服务公司
     * @see com.gy.hsxt.common.constant.CustType
     **/
    private int toCustType;
    /**
     * 被申报企业购买资源段
     * <p/>
     * 1首段资源 2创业资源 3全部资源 备注：适用于托管企业
     **/
    private int toBuyResRange;

    /**
     * 状态
     * <p/>
     * 0：待审批 1：已启用 2：审批驳回 3：已停用 备注:同一类型套餐只能有一个版本处于激活状态
     **/
    private Integer status;

    public int getToCustType() {
        return toCustType;
    }

    public void setToCustType(int toCustType) {
        this.toCustType = toCustType;
    }

    public int getToBuyResRange() {
        return toBuyResRange;
    }

    public void setToBuyResRange(int toBuyResRange) {
        this.toBuyResRange = toBuyResRange;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
