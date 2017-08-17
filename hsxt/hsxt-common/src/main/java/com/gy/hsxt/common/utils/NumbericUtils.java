package com.gy.hsxt.common.utils;

import java.util.regex.Pattern;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @className NumbericUtils
 * @description 一句话描述该类的功能
 * @author lixuan
 * @createDate 2015-5-11 下午9:57:23
 * @updateUser lixuan
 * @updateDate 2015-5-11 下午9:57:23
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class NumbericUtils {

    /**
     * 判断是否是正整数
     * 
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        if (s != null && !"".equals(s.trim()))
        {
                Pattern pattern = Pattern.compile("[1-9]\\d*+(\\.[0]+)?");
            return pattern.matcher(s.trim()).matches();
        }
        else
            return false;
    }

    /**
     * 判断参数2是否能以整除以参数1
     * 
     * @param number1
     *            被除数
     * @param number2
     *            除数
     * @return
     */
    public static boolean isIntegerMultiples(Number number1, Number number2) {
        if (number1 != null && number2 != null)
        {
            if (number1.intValue() != 0 && number2.intValue() != 0)
            {
                if (number1.intValue() % number2.intValue() == 0)
                {
                    return true;
                }
            }

        }
        return false;
    }

   

    /***
     * 判断是否为数字(整数、小数)
     * 
     * @param str
     * @return 修改前：^[-+]?(([1-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$
     *         修改后：^[-+]?(([1-9]([0-9]+)?)([.]([0-9]+))?|([.]([0-9]+))?)$
     *         修改前无法识别整数部分的0 修改人：陈丽
     */
    public static boolean isNumeric(String str) {
        return str.matches("^[-+]?(([1-9]([0-9]+)?)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /***
     * 判断是否为正数(整数、小数)
     * 
     * @param str
     * @return
     */
    public static boolean isPlus(String str) {
        return str.matches("^[+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /***
     * 判断是否为负数(整数、小数)
     * 
     * @param str
     * @return
     */
    public static boolean isMinus(String str) {
        return str.matches("^-(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
}
