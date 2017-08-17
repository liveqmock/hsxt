/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.lcs.client.LcsClient;

/**
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: PosBaseAction
 * @Description: 提供公用属性和方法
 * 
 * @author: weiyq
 * @date: 2016-1-7 下午7:29:34
 * @version V1.0
 */
public class PosBaseAction {

    @Autowired
    public BusinessParamSearch businessParamSearch;
    
    @Autowired
    @Qualifier("lcsClient")
    public LcsClient lcsClient;

    /**
     * 积分比例下限
     */
    private BigDecimal pointRatMin;

    /**
     * 积分比例上限
     */
    private BigDecimal pointRatMax;
    
    /**
     * 免费成员企业积分比例下限
     */
    private BigDecimal freeEntPointRatMin;

    /**
     * 非实名注册卡单笔代兑下限
     */
    private BigDecimal reChargeNotAuthSingleMin;

    /**
     * 非实名注册卡单笔代兑上限
     */
    private BigDecimal reChargeNotAuthSingleMax;

    /**
     * 实名注册卡单笔代兑下限
     */
    private BigDecimal reChargeAuthSingleMin;

    /**
     * 实名注册卡单笔代兑上限
     */
    private BigDecimal reChargeAuthSingleMax;

    /**
     * 成员企业单币兑换互生币下限
     */
    private BigDecimal reChargeBSingleMin;

    /**
     * 成员企业单币兑换互生币上限
     */
    private BigDecimal reChargeBSingleMax;

    /**
     * 托管企业单币兑换互生币下限
     */
    private BigDecimal reChargeTSingleMin;

    /**
     * 托管企业单币兑换互生币上限
     */
    private BigDecimal reChargeTSingleMax;
    
    private String localCurrencyCode;

    public BigDecimal getPointRatMin() {
        if (pointRatMin == null)
        {
            pointRatMin = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.CONFIG_PARA.getCode(), BusinessParam.HS_POIT_RATE_MIN.getCode()).getSysItemsValue());
        }
        return pointRatMin;
    }

    public void setPointRatMin(BigDecimal pointRatMin) {
        this.pointRatMin = pointRatMin;
    }

    public BigDecimal getPointRatMax() {
        if (pointRatMax == null)
        {
            pointRatMax = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.CONFIG_PARA.getCode(), BusinessParam.HS_POIT_RATE_MAX.getCode()).getSysItemsValue());
        }
        return pointRatMax;
    }

    public void setPointRatMax(BigDecimal pointRatMax) {
        this.pointRatMax = pointRatMax;
    }

    public BigDecimal getReChargeNotAuthSingleMin() {
        if (reChargeNotAuthSingleMin == null)
        {
            reChargeNotAuthSingleMin = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN.getCode())
                    .getSysItemsValue());
        }
        return reChargeNotAuthSingleMin;
    }

    public void setReChargeNotAuthSingleMin(BigDecimal reChargeNotAuthSingleMin) {
        this.reChargeNotAuthSingleMin = reChargeNotAuthSingleMin;
    }

    public BigDecimal getReChargeNotAuthSingleMax() {
        if (reChargeNotAuthSingleMax == null)
        {
            reChargeNotAuthSingleMax = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX.getCode())
                    .getSysItemsValue());
        }
        return reChargeNotAuthSingleMax;
    }

    public void setReChargeNotAuthSingleMax(BigDecimal reChargeNotAuthSingleMax) {
        this.reChargeNotAuthSingleMax = reChargeNotAuthSingleMax;
    }

    public BigDecimal getReChargeAuthSingleMin() {
        if (reChargeAuthSingleMin == null)
        {
            reChargeAuthSingleMin = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN.getCode())
                    .getSysItemsValue());
        }
        return reChargeAuthSingleMin;
    }

    public void setReChargeAuthSingleMin(BigDecimal reChargeAuthSingleMin) {
        this.reChargeAuthSingleMin = reChargeAuthSingleMin;
    }

    public BigDecimal getReChargeAuthSingleMax() {
        if (reChargeAuthSingleMax == null)
        {
            reChargeAuthSingleMax = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX.getCode())
                    .getSysItemsValue());
        }
        return reChargeAuthSingleMax;
    }

    public void setReChargeAuthSingleMax(BigDecimal reChargeAuthSingleMax) {
        this.reChargeAuthSingleMax = reChargeAuthSingleMax;
    }

    public BigDecimal getReChargeBSingleMin() {
        if (reChargeBSingleMin == null)
        {
            reChargeBSingleMin = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.B_SINGLE_BUY_HSB_MIN.getCode())
                    .getSysItemsValue());
        }
        return reChargeBSingleMin;
    }

    public void setReChargeBSingleMin(BigDecimal reChargeBSingleMin) {
        this.reChargeBSingleMin = reChargeBSingleMin;
    }

    public BigDecimal getReChargeBSingleMax() {
        if (reChargeBSingleMax == null)
        {
            reChargeBSingleMax = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.B_SINGLE_BUY_HSB_MAX.getCode())
                    .getSysItemsValue());
        }
        return reChargeBSingleMax;
    }

    public void setReChargeBSingleMax(BigDecimal reChargeBSingleMax) {
        this.reChargeBSingleMax = reChargeBSingleMax;
    }

    public BigDecimal getReChargeTSingleMin() {
        if (reChargeTSingleMin == null)
        {
            reChargeTSingleMin = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.T_SINGLE_BUY_HSB_MIN.getCode())
                    .getSysItemsValue());
        }
        return reChargeTSingleMin;
    }

    public void setReChargeTSingleMin(BigDecimal reChargeTSingleMin) {
        this.reChargeTSingleMin = reChargeTSingleMin;
    }

    public BigDecimal getReChargeTSingleMax() {
        if (reChargeTSingleMax == null)
        {
            reChargeTSingleMax = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.EXCHANGE_HSB.getCode(), BusinessParam.T_SINGLE_BUY_HSB_MAX.getCode())
                    .getSysItemsValue());
        }
        return reChargeTSingleMax;
    }

    public void setReChargeTSingleMax(BigDecimal reChargeTSingleMax) {
        this.reChargeTSingleMax = reChargeTSingleMax;
    }

    public String getLocalCurrencyCode() {
        if( localCurrencyCode == null ){
            localCurrencyCode = lcsClient.getLocalInfo().getCurrencyNo();
        }
        return localCurrencyCode;
    }

    public void setLocalCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode;
    }

	public BigDecimal getFreeEntPointRatMin() {
		if (freeEntPointRatMin == null)
        {
			freeEntPointRatMin = new BigDecimal(businessParamSearch.searchSysParamItemsByCodeKey(
                    BusinessParam.CONFIG_PARA.getCode(), BusinessParam.HS_FREE_ENT_POIT_RATE_MIN.getCode()).getSysItemsValue());
        }
        return freeEntPointRatMin;
	}

	public void setFreeEntPointRatMin(BigDecimal freeEntPointRatMin) {
		this.freeEntPointRatMin = freeEntPointRatMin;
	}

}
