/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

import com.gy.hsxt.common.constant.CustType;

/**
 * 资源段类型
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: ResType
 * @Description: 资源段类型
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午10:31:06
 * @version V1.0
 */
public enum ResType {

    /** 首段资源 **/
    FIRST_SECTION_RES(1),

    /** 创业资源 **/
    ENTREPRENEURSHIP_RES(2),

    /** 全部资源 **/
    ALL_RES(3),

    /** 正常成员企业 **/
    NORMAL_MEMBER_ENT(4),

    /** 免费成员企业 **/
    FREE_MEMBER_ENT(5);

    private int code;

    ResType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 校验类型
     * @param resType 类型
     * @return {@code boolean}
     */
    public static boolean checkType(Integer resType) {
        if(resType==null) return false;
        for (ResType type : values()) {
            if (resType == type.getCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检验启用资源类型
     * @param custType 客户类型
     * @param resType 资源类型
     * @return {@code boolean}
     */
    public static boolean checkResType(Integer custType, Integer resType) {
        if(custType==null) return false;
        if(!checkType(resType)) return false;
        boolean check = false;
        if (custType == CustType.MEMBER_ENT.getCode()) {// 成员企业
            check = (resType > 3);

        } else if (custType == CustType.TRUSTEESHIP_ENT.getCode()) {// 托管企业
            check = (resType <= 3);
        }
        return check;
    }

}
