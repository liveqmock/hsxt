/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.validator;

import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Package :com.gy.hsxt.ps.validator
 * @ClassName : PsValidator
 * @Description : 验证类（校验类）
 * @Author : Martin.Cubbon
 * @Date : 2016/6/24 14:10
 * @Version V3.0.0.0
 */
public class PsValidator {

    public static  void rebateValidator(Point point, BusinessParamSearch businessParamSearch)throws HsException {

        if (StringUtils.isNotBlank(point.getOrderAmount())) {
            BigDecimal orderAmount = new BigDecimal(point.getOrderAmount());
            if (orderAmount.compareTo(BigDecimal.ZERO) == 1) {

                Map<String, BusinessSysParamItemsRedis> map = businessParamSearch.searchSysParamItemsByGroup(BusinessParam.DEDUCTION_VOUCHER.getCode());

                GeneralValidator.collectionNotNull(map, RespCode.PS_PARAM_ERROR, "抵扣券查询为空！");

                /** 抵扣券最大张数 **/
                BusinessSysParamItemsRedis deductionVoucherMaxNum = map.get(BusinessParam.DEDUCTION_VOUCHER_MAX_NUM.getCode());
                GeneralValidator.notNull(deductionVoucherMaxNum, RespCode.PS_PARAM_ERROR, "抵扣券最大张数不能为空");

                /** 抵扣券每张面额 **/
                BusinessSysParamItemsRedis deductionVoucherPerAmount = map.get(BusinessParam.DEDUCTION_VOUCHER_PER_AMOUNT.getCode());
                GeneralValidator.notNull(deductionVoucherPerAmount, RespCode.PS_PARAM_ERROR, "抵扣券每张面额不能为空");

                /** 抵扣券金额占消费金额比率**/
                BusinessSysParamItemsRedis deductionVoucherRate = map.get(BusinessParam.DEDUCTION_VOUCHER_RATE.getCode());
                GeneralValidator.notNull(deductionVoucherRate, RespCode.PS_PARAM_ERROR, "抵扣券金额占消费金额比率不能为空");

                String dvmn=deductionVoucherMaxNum.getSysItemsValue();
                GeneralValidator.notNull(dvmn, RespCode.PS_PARAM_ERROR, "抵扣券最大张数不能为空");
                /** 抵扣券最大张数 **/
                int DEDUCTION_VOUCHER_MAX_NUM = Integer.valueOf(dvmn);

                String dvpa=deductionVoucherPerAmount.getSysItemsValue();
                GeneralValidator.notNull(dvpa, RespCode.PS_PARAM_ERROR, "抵扣券每张面额不能为空");
                /** 抵扣券每张面额 **/
                BigDecimal DEDUCTION_VOUCHER_PER_AMOUNT = new BigDecimal(dvpa);

                String dvr=deductionVoucherRate.getSysItemsValue();
                GeneralValidator.notNull(dvr, RespCode.PS_PARAM_ERROR, "抵扣券金额占消费金额比率不能为空");
                /** 抵扣券金额占消费金额比率 **/
                BigDecimal DEDUCTION_VOUCHER_RATE = new BigDecimal(dvr);

                if (point.getDeductionVoucher() > DEDUCTION_VOUCHER_MAX_NUM) {
                    // 抛出 dubbo 异常
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DEDUCTION_VOUCHER_MAX_NUM.getCode(),"抵扣券大于："+DEDUCTION_VOUCHER_MAX_NUM);

                }
                if (Compute.mulCeiling(DEDUCTION_VOUCHER_PER_AMOUNT, BigDecimal.valueOf(point.getDeductionVoucher()), Constants.SURPLUS_TWO).compareTo(Compute.mulCeiling(orderAmount, DEDUCTION_VOUCHER_RATE, Constants.SURPLUS_TWO)) == 1) {
                    // 抛出 dubbo 异常
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_REBATE_AMOUNT_OVERSIZE.getCode(),"使用抵扣券过多，抵扣券不能超过金额的乘以积分比率："+DEDUCTION_VOUCHER_RATE+"后的值！");
                }
            }
        }
    }
}
