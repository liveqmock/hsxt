/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.tax;

/**
 * 纳税人类型
 *
 * @Package : com.gy.hsxt.bs.common.enumtype.tax
 * @ClassName : TaxpayerType
 * @Description : 纳税人类型
 * @Author : chenm
 * @Date : 2015/12/29 15:41
 * @Version V3.0.0.0
 */
public enum TaxpayerType {

    /**
     *小规模纳税人
     */
    SMALL,
    /**
     * 一般纳税人
     */
    NORMAL;

    /**
     * 检查参数是否符合条件
     *
     * @param code 状态码
     * @return boolean j检查结果
     */
    public static boolean check(int code) {
        boolean pass = false;
        for (TaxpayerType taxpayerType : TaxpayerType.values()) {
            if (taxpayerType.ordinal() == code) {
                pass = true;
                break;
            }
        }
        return pass;
    }

}
