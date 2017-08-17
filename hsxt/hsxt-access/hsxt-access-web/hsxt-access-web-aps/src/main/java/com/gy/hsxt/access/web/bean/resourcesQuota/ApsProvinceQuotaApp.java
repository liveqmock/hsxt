/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.resourcesQuota;

/*import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;*/
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.quota.ProvinceQuotaApp;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.common.constant.RespCode;
/*import com.gy.hsxt.common.exception.HsException;*/

/***
 * 省级配额申请===地区平台
 * 
 * @Package: com.gy.hsxt.access.web.bean.resourcesQuota
 * @ClassName: ApsProvinceQuotaApp
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-25 上午11:47:38
 * @version V1.0
 */
public class ApsProvinceQuotaApp extends ProvinceQuotaApp {

    private static final long serialVersionUID = 2873892777776174514L;

    /**
     * 省级配额申请集合字符串
     */
   // private String quotaApply;

    /**
     * 省级配额申请集合
     */
   // private List<ApsProvinceQuotaApp> pqaList;

    /**
     * 客户名称
     */
    //private String custName;

    /**
     * 客户号
     */
    private String custId;
    
    /**
     * 申请企业
     */
    private String applyEntResNo;

    /**
     * 省份名称
     */
    //private String provinceName;

    /**
     * @return the 申请企业
     */
    public String getApplyEntResNo() {
        return applyEntResNo;
    }

    /**
     * @param 申请企业 the applyEntResNo to set
     */
    public void setApplyEntResNo(String applyEntResNo) {
        this.applyEntResNo = applyEntResNo;
    }

    /**
     * 验证数据
     */
    public void checkData() {
        // 循环验证
   /*     if (this.pqaList == null || this.pqaList.size() == 0)
        {
            throw new HsException(RespCode.APS_NOT_APPLY_DATA.getCode(), RespCode.APS_NOT_APPLY_DATA.getDesc());
        }
        for (ProvinceQuotaApp pqa : this.pqaList)
        {*/

        RequestUtil.verifyParamsIsNotEmpty(
            new Object[] { super.getEntResNo(), RespCode.APS_ENTRESNO_NOT_NULL },
            new Object[] { super.getProvinceNo(), RespCode.APS_PRO_CODE_NOT_NULL },
            new Object[] {super.getApplyType(), RespCode.APS_APPLY_TYPE_NOT_NULL },
            new Object[] {super.getApplyNum(), RespCode.APS_APPLY_NUM_NOT_NULL }
            );
        /*}*/

    }

    /**
     * 审批数据验证
     */
    public void apprCheckData() {
        RequestUtil.verifyParamsIsNotEmpty(
            new Object[] { super.getApplyId(), RespCode.APS_APPLYID_NOT_NULL},
            new Object[] { super.getApprNum(), RespCode.APS_APPNUM_NOT_NULL },
            new Object[] { super.getStatus(), RespCode.APS_APPLY_STATUS_NOT_NULL}
            );
    }

    /**
     * 设置默认数据
     */
    public void setDefaultData() {
        super.setStatus(ApprStatus.WAIT_APPR.getCode());
        super.setReqOperator(this.custId);
    }

  /*  public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }*/

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
        super.setExeCustId(this.custId);// 设置操作员
    }

 /*   public String getQuotaApply() {
        return quotaApply;
    }

    public void setQuotaApply(String quotaApply) throws UnsupportedEncodingException {
        this.quotaApply = quotaApply;

        // 1、对JSON字符串进行转码。2、在转换成集合对象
        if (!StringUtils.isEmpty(this.quotaApply))
        {
            this.setPqaList(JSON.parseArray(URLDecoder.decode(quotaApply, "UTF-8"), ApsProvinceQuotaApp.class));
        }

    }

    public List<ApsProvinceQuotaApp> getPqaList() {
        return pqaList;
    }

    public void setPqaList(List<ApsProvinceQuotaApp> pqaList) {
        this.pqaList = pqaList;
    }*/

  /*  public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }*/

}
