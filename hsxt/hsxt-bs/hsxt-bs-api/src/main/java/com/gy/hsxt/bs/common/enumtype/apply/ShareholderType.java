/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: ShareholderType
 * @Description: 股东类型
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午10:22:13
 * @version V1.0
 */
public enum ShareholderType {

    /** NAP:自然人 **/
    NATURAL_PERSON(1),

    /** ENP:企业法人 **/
    BUSINESS_ENTITY(2),

    /** CMP:社会团体法人 **/
    MASS_ORGANIZATION_LEGAL_PERSON(3),

    /** PIP:事业法人 **/
    PUBLIC_INSTITUTION_LEGAL_PERSON(4),

    /** OTP:其他 **/
    OTHERS(5);

    private int code;

    ShareholderType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
