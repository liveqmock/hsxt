/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: LegalCreType
 * @Description: 法人证件类型
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午10:15:00
 * @version V1.0
 */
public enum LegalCreType {

    /** 身份证:IDC **/
    ID_CARD(1),

    /** 护照:PSP **/
    PASSPORT(2),

    /** BCT：营业执照 **/
    BIZ_LICENSE(3);

    private int code;

    LegalCreType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
