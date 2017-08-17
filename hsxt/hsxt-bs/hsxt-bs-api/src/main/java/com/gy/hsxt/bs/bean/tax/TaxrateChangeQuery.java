/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tax;

import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.enumtype.enttaxratechange.EntTaxrateChangeStatus;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 税率调整查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.tax
 * @ClassName : TaxrateChangeQuery
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/12/1 18:34
 * @Version V3.0.0.0
 */
public class TaxrateChangeQuery extends Query {

    private static final long serialVersionUID = 7253639057077397338L;
    /**
     * 企业互生号
     **/
    private String resNo;
    /**
     * 企业客户号
     **/
    private String custId;
    /**
     * 客户类型
     * <p/>
     * 2:成员3:托管4:服务5:管理
     **/
    private int custType;
    /**
     * 企业名称
     **/
    private String custName;
    /**
     * 申请状态
     * <p/>
     *  0-未审批 1-地区平台初审通过1 2-地区平台复核通过 3-初审驳回 4-复核驳回
     *
     * @see com.gy.hsxt.bs.common.enumtype.tax.TaxrateStatus
     **/
    private Integer status;
    /**
     * 启用日期 格式：yyyy-MM-dd
     **/
    private String enableDate;
    /**
     * 停用日期 格式：yyyy-MM-dd
     **/
    private String disableDate;

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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public int getCustType() {
        return custType;
    }

    public void setCustType(int custType) {
        this.custType = custType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(String enableDate) {
        this.enableDate = enableDate;
    }

    public String getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(String disableDate) {
        this.disableDate = disableDate;
    }

    /**
     * 校验审核状态类型
     *
     * @return this
     * @throws HsException
     */
    public TaxrateChangeQuery checkStatus() throws HsException {
        if (null != this.getStatus()) {
            //后校验是否符合审核状态类型
            HsAssert.isTrue(EntTaxrateChangeStatus.checkStatus(this.getStatus()), RespCode.PARAM_ERROR, "审核状态[status]类型错误");
        }
        return this;
    }

}
