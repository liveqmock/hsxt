/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.resourcesQuota;

import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***
 * 
 * 申请配额Bean===地区平台
 * 
 * @Package: com.gy.hsxt.access.web.bean.resourcesQuota
 * @ClassName: ApsPlatQuotaApp
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-23 下午12:16:48
 * @version V1.0
 */
public class ApsPlatQuotaApp extends PlatQuotaApp {

    private static final long serialVersionUID = -4011377216937447074L;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 客户号
     */
     private String custId;

    /**
     * @return the 客户号
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param 客户号 the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * 11位数字管理公司互生号
     */
    private String applyEntResNo;

    /**
     * 管理公司名称
     */
    private String applyEntCustName;

    /**
     * 数据验证有效性
     */
    public void chekcData() {
        
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { this.applyEntResNo, RespCode.APS_ENTRESNO_NOT_NULL },
                new Object[] { super.getApplyNum(), RespCode.APS_APPLY_NUM_NOT_NULL }
                );

        // 申请配额数量不小于1
        if (super.getApplyNum() < 1){
            throw new HsException(RespCode.APS_APPLY_NUM_NOT_LESS);
        }
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the 11位数字管理公司互生号
     */
    public String getApplyEntResNo() {
        return applyEntResNo;
    }

    /**
     * @param 11位数字管理公司互生号 the applyEntResNo to set
     */
    public void setApplyEntResNo(String applyEntResNo) {
        this.applyEntResNo = applyEntResNo;
        super.setEntResNo(this.applyEntResNo);
    }

    /**
     * @return the 管理公司名称
     */

    public String getApplyEntCustName() {
        return applyEntCustName;
    }

    /**
     * @param 管理公司名称
     *            the applyEntCustName to set
     */

    public void setApplyEntCustName(String applyEntCustName) {
        this.applyEntCustName = applyEntCustName;
        super.setEntCustName(this.applyEntCustName);
    }

}
