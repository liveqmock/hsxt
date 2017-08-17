/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.utils;

import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 数字金额转换器
 *
 * @Package : com.gy.hsxt.bs.common.utils
 * @ClassName : AmountConverter
 * @Description : 数字金额转换器
 * @Author : chenm
 * @Date : 2016/6/6 16:48
 * @Version V3.0.0.0
 */
public abstract class AmountConverter {

    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
     * 要用到正则表达式
     *
     * @param amount 金额
     * @return 大写金额
     */
    public static String digitUppercase(String amount) {
        if(amount==null|| amount.trim().isEmpty()){
            amount = "0";
        }
        HsAssert.isTrue(NumberUtils.isNumber(amount), BSRespCode.BS_PARAMS_TYPE_ERROR,"数字金额大写转参数错误:"+amount);
        return digitUppercase(NumberUtils.toDouble(amount));
    }

    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
     * 要用到正则表达式
     *
     * @param n 金额
     * @return 大写金额
     */
    public static String digitUppercase(double n) {
        String fraction[] = {"角", "分"};
        String digit[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String unit[][] = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};

        String head = n < 0 ? "负" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }
}
