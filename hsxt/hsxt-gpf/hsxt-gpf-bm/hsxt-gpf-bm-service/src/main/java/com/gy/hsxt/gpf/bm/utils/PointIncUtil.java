package com.gy.hsxt.gpf.bm.utils;

import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsResNoUtils;

/**
 * 再增值积分工具类
 *
 * @Package : com.gy.hsxt.gpf.bm.utils
 * @ClassName : PointIncUtil
 * @Description : 再增值积分工具类
 * @Author : li.chang
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class PointIncUtil {
    /**
     * 计算再增值积分分配数
     *
     * @param pointREF 分配比参考数
     * @param baseVal  分配基数
     * @return String 返回类型
     */
    public static String getBmlmOutValue(String pointREF, String baseVal) {
        //积分参考值
        double ref = BigDecimalUtils.floor(pointREF, 2).doubleValue();
        //积分参考值不足10点，本月不于计算，月底同时归零
        if (ref < 10) return "0";
        //分配基数
        double base = BigDecimalUtils.floor(baseVal, 2).doubleValue();
        //百位分配比
        double basePercent = getPercent(pointREF);
        //再增值积分分配数
        double out = 0;
        //计算再增值积分分配数
        if (base > 0 && base < 1000) {
            out = basePercent * base;
        } else if (base >= 1000) {
            int iLen = 3;
            double A = base - (Math.pow(10, iLen) - 1);
            out = (Math.pow(10, iLen) - 1) * basePercent; // 百位
            int flag = 0; // 判断是否结束
            while (flag == 0) {
                iLen++;
                if (A < (Math.pow(10, iLen) - 1)) { // 百位数字
                    out = out + (A * basePercent) / Math.pow(10, iLen - 3);
                    flag = 1;
                } else if (A > (Math.pow(10, iLen) - 1)) { // 千位以后
                    out = out + ((Math.pow(10, iLen) - 1) * basePercent) / Math.pow(10, iLen - 3);
                    A = A - (Math.pow(10, iLen) - 1);
                }
            }
        }
        //小于0.01 清零
        if (out < 0.01) return "0";
        return BigDecimalUtils.floor(Double.toString(out),2).toPlainString(); // 返回结果
    }

    /**
     * 获取百位分配比
     *
     * @param pointREF 分配比参考数
     * @return 百位分配比
     */
    public static double getPercent(String pointREF) {
        double ref = BigDecimalUtils.floor(pointREF,2).doubleValue();
        double basePercent = 0.0;
        if (ref >= 10 && ref < 101) {
            basePercent = 0.025;
        } else if (ref >= 101 && ref < 301) {
            basePercent = 0.050;
        } else if (ref >= 301 && ref < 501) {
            basePercent = 0.075;
        } else if (ref >= 501) {
            basePercent = 0.100;
        }
        return basePercent;
    }

    /**
     * 筛选积分增值再增值的企业类型。不属于指定类型的公司不参与积分增值再增值。
     *
     * @param resNo 互生号
     * @return boolean
     */
    public static boolean allow(String resNo) {
        return HsResNoUtils.isServiceResNo(resNo) || HsResNoUtils.isTrustResNo(resNo) || HsResNoUtils.isMemberResNo(resNo);
    }
}
