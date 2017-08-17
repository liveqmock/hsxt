/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.externalapi;

import com.alibaba.fastjson.JSON;
import com.gy.hsec.external.api.UserService;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.common.PsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package :com.gy.hsxt.ps.externalapi
 * @ClassName : hsecexternalapi
 * @Description : 电商接口
 * @Author : Martin.Cubbon
 * @Date : 2016/6/24 14:44
 * @Version V3.0.0.0
 */
@Service
public class HsecExternalApi {

    //抵扣券
    @Autowired
    private UserService userService;


    /**
     * 使用抵扣券
     *
     * @param resourceNo        消费者资源号
     * @param companyResourceNo 企业资源号
     * @param lineNo            流水号
     * @param number            使用数量
     * @return ReturnResult 返回值
     */
    public void useCoupon(String resourceNo, String companyResourceNo, String lineNo, int number) throws HsException {
        ReturnResult result = null;
        int respCode = 0;
        SystemLog.debug(this.getClass().getName(), "useCoupon", "入参值，resourceNo："+resourceNo+"，companyResourceNo："+companyResourceNo+"，lineNo："+lineNo+"，number："+number);
        if (number > 0) {
            try {
                result = userService.useCoupon(resourceNo, companyResourceNo, lineNo, number);
                SystemLog.debug(this.getClass().getName(), "useCoupon", "使用抵扣券，调用后还回对象：" + JSON.toJSONString(result));
            } catch (Exception e) {
                // 抛出 异常
                PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.GL_CONNECT_ERROR.getCode(), e
                        .getMessage(), e);
            }
            if (result != null) {
                if (result.getRetCode() == 401) {
                    respCode = RespCode.PS_USER_RESOURCE_NULL_OR_PARAM_ERROR.getCode();
                }
                if (result.getRetCode() == 402) {
                    respCode = RespCode.PS_ENT_RESOURCE_NULL_OR_PARAM_ERROR.getCode();
                }
                if (result.getRetCode() == 403) {
                    respCode = RespCode.PS_TRANS_NO_NULL.getCode();
                }
                if (result.getRetCode() == 404) {
                    respCode = RespCode.PS_NUMBER_MUST_GREATER_THAN_ZERO.getCode();
                }
                if (result.getRetCode() == 405) {
                    respCode = RespCode.PS_NO_DEDUCTION_VOUCHER.getCode();
                }
                if (result.getRetCode() == 406) {
                    respCode = RespCode.PS_DEDUCTION_VOUCHER_NOT_ENOUGH.getCode();
                }
                if (result.getRetCode() == 201) {
                    respCode = RespCode.PS_USE_COUPON_FAIL.getCode();
                }
                SystemLog.debug(this.getClass().getName(), "useCoupon", "使用抵扣券，返回码：" + result.getRetCode());
                if (respCode != 0) {
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], respCode,
                            "使用抵扣券失败，错误码是：" + result.getRetCode());
                }
            }
        }

    }


    /**
     * 撤销已使用的抵扣券
     *
     * @param companyResourceNo 企业资源号
     * @param lineNo            流水号
     * @return ReturnResult 返回值
     */
    public void useCouponCancel(String companyResourceNo, String lineNo, int number) throws HsException {
        ReturnResult result = null;
        int respCode = 0;
        if (number > 0) {
            try {
                result = userService.useCouponCancel(companyResourceNo, lineNo);
            } catch (Exception e) {
                // 抛出 异常
                PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.GL_CONNECT_ERROR.getCode(), e
                        .getMessage(), e);
            }
            if (result != null) {
                if (result.getRetCode() == 402) {
                    respCode = RespCode.PS_ENT_RESOURCE_NULL_OR_PARAM_ERROR.getCode();
                }
                if (result.getRetCode() == 403) {
                    respCode = RespCode.PS_TRANS_NO_ERROR.getCode();
                }
                if (result.getRetCode() == 407) {
                    respCode = RespCode.PS_USER_DEDUCTION_VOUCHER_TRANS_NOT_EXIT.getCode();
                }
                SystemLog.debug(this.getClass().getName(), "useCouponCancel", "撤销已使用的抵扣券，返回码：" + result.getRetCode());
                if (respCode != 0) {
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], respCode,
                            "撤销已使用的抵扣券失败，错误码是：" + result.getRetCode());
                }
            }
        }
    }
}
