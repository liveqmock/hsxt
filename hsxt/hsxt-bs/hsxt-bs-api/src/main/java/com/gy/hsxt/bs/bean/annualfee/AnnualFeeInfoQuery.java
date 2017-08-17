/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.annualfee;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 年费查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.annualfee
 * @ClassName : AnnualFeeInfoQuery
 * @Description : 年费查询实体
 * @Author : chenm
 * @Date : 2015/12/23 18:23
 * @Version V3.0.0.0
 */
public class AnnualFeeInfoQuery extends Query {

    private static final long serialVersionUID = 1636278555015344196L;
    /**
     * 客户类型
     * <p/>
     * 4：服务公司 3：托管企业
     **/
    private Integer custType;
    /**
     * 企业名称
     **/
    private String entCustName;
    /**
     * 年费截止日期
     **/
    private String endLineDate;
    /**
     * 缴费提示日期
     **/
    private String warningDate;

    /**
     * 缴费区间-开始日期
     */
    private String areaStartDate;

    /**
     * 缴费区间-结束日期
     */
    private String areaEndDate;

    /**
     * 是否欠年费   0：否 1：是
     **/
    private Integer isArrear;
    /**
     * 年费状态变更日期
     * <p/>
     * 如果是当天变更了欠费状态或者年费截止日期的，需要同步到用户中心
     */
    private String statusChangeDate;

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getEndLineDate() {
        return endLineDate;
    }

    public void setEndLineDate(String endLineDate) {
        this.endLineDate = endLineDate;
    }

    public String getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(String warningDate) {
        this.warningDate = warningDate;
    }

    public String getAreaStartDate() {
        return areaStartDate;
    }

    public void setAreaStartDate(String areaStartDate) {
        this.areaStartDate = areaStartDate;
    }

    public String getAreaEndDate() {
        return areaEndDate;
    }

    public void setAreaEndDate(String areaEndDate) {
        this.areaEndDate = areaEndDate;
    }

    public Integer getIsArrear() {
        return isArrear;
    }

    public void setIsArrear(Integer isArrear) {
        this.isArrear = isArrear;
    }

    public String getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(String statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }
}
