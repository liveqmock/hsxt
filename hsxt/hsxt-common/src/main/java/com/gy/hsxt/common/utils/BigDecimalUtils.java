/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.math.NumberUtils;

import com.gy.hsxt.common.constant.RespCode;

/**
 * BigDecimal工具类
 * 
 * @Package :com.gy.hsxt.common.utils
 * @ClassName : BigDecimalUtils
 * @Description :
 *              <p>
 *              BigDecimal 的加减乘除
 *              </p>
 * @Author : chenm
 * @Date : 2015/12/18 9:11
 * @Version V3.0.0.0
 */
public abstract class BigDecimalUtils {

    /**
     * 默认精度 6 位
     */
    private static final int DEFAULT_SCALE = 6;

    /**
     * 默认精度为6的加法并向上取值
     * 
     * @param s1
     *            被加数
     * @param s2
     *            加数
     * @return 结果
     */
    public static BigDecimal ceilingAdd(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return ceiling(b1.add(b2).toPlainString());
    }

    /**
     * 默认精度为6的加法并向下取值
     * 
     * @param s1
     *            被加数
     * @param s2
     *            加数
     * @return 结果
     */
    public static BigDecimal floorAdd(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return floor(b1.add(b2).toPlainString());
    }

    /**
     * 默认精度为6的减法并向上取值
     * 
     * @param s1
     *            被减数
     * @param s2
     *            减数
     * @return 结果
     */
    public static BigDecimal ceilingSub(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return ceiling(b1.subtract(b2).toPlainString());
    }

    /**
     * 默认精度为6的减法并向下取值
     * 
     * @param s1
     *            被减数
     * @param s2
     *            减数
     * @return 结果
     */
    public static BigDecimal floorSub(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return floor(b1.subtract(b2).toPlainString());
    }

    /**
     * 默认精度为6的乘法并向上取值
     * 
     * @param s1
     *            被乘数
     * @param s2
     *            乘数
     * @return 结果
     */
    public static BigDecimal ceilingMul(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return ceiling(b1.multiply(b2).toPlainString());
    }

    /**
     * 默认精度为6的乘法并向下取值
     * 
     * @param s1
     *            被乘数
     * @param s2
     *            乘数
     * @return 结果
     */
    public static BigDecimal floorMul(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return floor(b1.multiply(b2).toPlainString());
    }

    /**
     * 默认精度为6的除法并向上取值
     * 
     * @param s1
     *            被除数
     * @param s2
     *            除数
     * @return 结果
     */
    public static BigDecimal ceilingDiv(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.divide(b2, DEFAULT_SCALE, RoundingMode.CEILING);
    }

    /**
     * 默认精度为6的除法并向下取值
     * 
     * @param s1
     *            被除数
     * @param s2
     *            除数
     * @return 结果
     */
    public static BigDecimal floorDiv(String s1, String s2) {
        HsAssert.isTrue(NumberUtils.isNumber(s1), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        HsAssert.isTrue(NumberUtils.isNumber(s2), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.divide(b2, DEFAULT_SCALE, RoundingMode.FLOOR);
    }

    /**
     * 默认精度为6向上取值
     * 
     * @param s
     *            参数
     * @return 结果
     */
    public static BigDecimal ceiling(String s) {
        HsAssert.isTrue(NumberUtils.isNumber(s), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        return ceiling(s, DEFAULT_SCALE);
    }

    /**
     * 向上取值
     * 
     * @param s
     *            参数
     * @param scale
     *            精度值
     * @return 结果
     */
    public static BigDecimal ceiling(String s, int scale) {
        HsAssert.isTrue(NumberUtils.isNumber(s), RespCode.PARAM_ERROR, "字符参数[s]不是数字");
        BigDecimal b1 = new BigDecimal(s);
        BigDecimal b2 = new BigDecimal(1);
        return b1.divide(b2, scale, RoundingMode.CEILING);
    }

    /**
     * 默认精度为6 向下取值
     * 
     * @param s
     *            参数
     * @return 结果
     */
    public static BigDecimal floor(String s) {
        HsAssert.isTrue(NumberUtils.isNumber(s), RespCode.PARAM_ERROR, "字符参数[s]不是数字");
        return floor(s, DEFAULT_SCALE);
    }

    /**
     * 向下取值
     * 
     * @param s
     *            参数
     * @param scale
     *            精度值
     * @return 结果
     */
    public static BigDecimal floor(String s, int scale) {
        HsAssert.isTrue(NumberUtils.isNumber(s), RespCode.PARAM_ERROR, "字符参数[s]不是数字");
        BigDecimal b1 = new BigDecimal(s);
        BigDecimal b2 = new BigDecimal(1);
        return b1.divide(b2, scale, RoundingMode.FLOOR);
    }

    /**
     * 默认精度为6 的四舍五入
     * 
     * @param s
     *            参数
     * @return 结果
     */
    public static BigDecimal round(String s) {
        HsAssert.isTrue(NumberUtils.isNumber(s), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        return round(s, DEFAULT_SCALE);
    }

    /**
     * 四舍五入
     * 
     * @param s
     *            参数
     * @param len
     *            精度值
     * @return 结果
     */
    public static BigDecimal round(String s, int len) {
        HsAssert.isTrue(NumberUtils.isNumber(s), RespCode.PARAM_ERROR, "字符参数[s]不是数字类型");
        BigDecimal b1 = new BigDecimal(s);
        BigDecimal b2 = new BigDecimal(1);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 默认精度为6 的比较 结果等于0 为相等 结果大于0(正数) s1为大于s2 结果小于0(负数) s1为小于s2
     * 
     * @param s1
     *            被比数
     * @param s2
     *            比数
     * @return int
     */
    public static int compareTo(String s1, String s2) {
        return ceiling(s1).compareTo(ceiling(s2));
    }

}
