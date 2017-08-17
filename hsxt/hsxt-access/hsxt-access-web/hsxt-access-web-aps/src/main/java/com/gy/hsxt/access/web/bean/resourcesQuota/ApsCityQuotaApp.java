/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.resourcesQuota;

import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.quota.CityQuotaApp;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***
 * 市级配额申请Bean===地区平台
 * 
 * @Package: com.gy.hsxt.access.web.bean.resourcesQuota
 * @ClassName: ApsCityQuotaApp
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-25 上午11:49:33
 * @version V1.0
 */
public class ApsCityQuotaApp extends CityQuotaApp {
    private static final long serialVersionUID = -4494369512192287891L;

    /**
     * 客户号
     */
    private String custId;

    /**
     * 区域申请配额管理公司
     */
    private String quotaEntResNo;

    /**
     * 数据验证有效性
     */
    public void chekcBaseData() {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { this.quotaEntResNo, RespCode.APS_ENTRESNO_NOT_NULL },
                new Object[] { super.getProvinceNo(), RespCode.APS_PRO_CODE_NOT_NULL },
                new Object[] { super.getCityNo(), RespCode.APS_CITY_CODE_NOT_NULL },
                new Object[] { super.getApplyNum(), RespCode.APS_APPLY_NUM_NOT_NULL },
                new Object[] { super.getPopulation(), RespCode.APS_POPULATION_NOT_NULL }
        );

        // 申请数量必须大于0
        if (super.getApplyNum() < 1) {
            throw new HsException(RespCode.APS_APPLY_NUM_NOT_LESS);
        }
    }

    /**
     * 初始化验证
     */
    public void chekcInitData() {
        chekcBaseData();
    }

    /**
     * 修改验证
     */
    public void chekcUpdateData() {
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { super.getApplyType(), RespCode.APS_APPLY_TYPE_NOT_NULL });
    }

    /**
     * 审批验证
     */
    public void approveCheck() {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { super.getApplyId(), RespCode.APS_APPLYID_NOT_NULL },
                new Object[] { super.getStatus(), RespCode.APS_APPLY_STATUS_NOT_NULL },
                new Object[] { super.getApprNum(), RespCode.APS_APPNUM_NOT_NULL });
    }

    public String getQuotaEntResNo() {
        return quotaEntResNo;
    }

    public void setQuotaEntResNo(String quotaEntResNo) {
        this.quotaEntResNo = quotaEntResNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
        super.setExeCustId(this.custId);
    }
}
