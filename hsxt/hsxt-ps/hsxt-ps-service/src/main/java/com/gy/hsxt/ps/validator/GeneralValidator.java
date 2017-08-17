/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.validator;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * @Package :com.gy.hsxt.ps.validator
 * @ClassName : GeneralValidator
 * @Description : 通用校验接口
 * @Author : Martin.Cubbon
 * @Date : 2016/3/24 17:50
 * @Version V3.0.0.0
 */
public class GeneralValidator {


    /**
     * 判断积分是否小于积分最小值
     *
     * @param transAmount 交易金额
     * @param pointRate  积分比率
     */
    public static void verifyMixPoint(BigDecimal transAmount, BigDecimal pointRate) throws HsException {

        // 计算企业应付积分
        BigDecimal entPoint = Compute.mulCeiling(transAmount, pointRate, Constants.SURPLUS_TWO);

        // 判断积分是否小于积分最小值
        if (entPoint.doubleValue() < Constants.MIN_POINT) {
            // 积分小于最小值则抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_POINT_NO_MIN.getCode(),
                    "积分小于最小值");
        }

    }


    /**
     * 判断交易类型第几位
     *
     * @param transType 交易金额
     * @param transWay  第几位代表的值
     * @param location  第几位
     */
    public static Boolean verifyTransType(String transType, String transWay, int location) {

        String[] str = transType.split("");
        if (str.length > 0) {
            if (str[location].equals(transWay)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否为空
     *
     * @param value 值
     * @param mgs   提示信息
     */
    public static Boolean verifyIsNotEmpty(String value, String mgs) throws HsException {

        if (StringUtils.isNotEmpty(value)) {
            return true;
        } else {
            // 抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(),
                    mgs);
        }
        return false;
    }

    /**
     * 判断对象是否为空
     *
     * @param o   值
     * @param mgs 提示信息
     */
    public static Boolean verifyObjectIsNotEmpty(Object o, String mgs) throws HsException {

        if (ObjectUtils.notEqual(o, null)) {
            return true;
        } else {
            // 抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(),
                    mgs);
        }
        return false;
    }


    /**
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(Object object, RespCode respCode, String message) throws HsException {
        if (object == null) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                    respCode.getCode(), message);
        }
    }

    /**
     * @param value   要校验的值
     * @param message 返回的提示信息
     * @throws IllegalArgumentException if the object is not {@code null}
     */
    public static void notNull(String value, RespCode respCode, String message) throws HsException {
        if (StringUtils.isBlank(value)) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                    respCode.getCode(), message);
        }
    }

    /**
     * @param object  要校验的值
     * @param message 返回的提示信息
     * @throws IllegalArgumentException if the object is not {@code null}
     */
    public static void collectionNotNull(Object object, RespCode respCode, String message) throws HsException {

        if (object == null) {
            throw new IllegalArgumentException("Unsupported object type: null");
        } else if (object instanceof Collection) {
            if (((Collection) object).isEmpty()) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                        respCode.getCode(), message);
            }
        } else if (object instanceof Map) {
            if (((Map) object).isEmpty()) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                        respCode.getCode(), message);
            }
        } else if (object instanceof Object[]) {
            if (((Object[]) object).length == 0) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                        respCode.getCode(), message);
            }
        } else if (object instanceof Iterator) {
            if (!((Iterator) object).hasNext()) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                        respCode.getCode(), message);
            }
        } else if (object instanceof Enumeration) {
            if (!((Enumeration) object).hasMoreElements()) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                        respCode.getCode(), message);
            }
        }
    }

}
