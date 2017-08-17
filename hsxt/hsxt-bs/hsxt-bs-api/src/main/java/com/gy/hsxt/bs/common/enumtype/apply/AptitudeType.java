/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: AptitudeType
 * @Description: 资质类型
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午10:12:30
 * @version V1.0
 */
public enum AptitudeType {

    /** FA:法人代表证件正面 **/
    LEGAL_REP_CRE_FACE(1),

    /** FB:法人代表证件反面 **/
    LEGAL_REP_CRE_BACK(2),

    /** Y:营业执照扫描件 **/
    BIZ_LICENSE_CRE(3),

    /** BAK:银行资金证明 **/
    BANK_ROLL_CRE(4),

    /** HSH：合作股东证明代码证明 **/
    SHARE_HOLDER_CRE(5),

    /** Z:组织机构代码证扫描件 **/
    ORGANIZER_CRE(6),

    /** S:税务登记证扫描件 **/
    TAXPAYER_CRE(7),
    
    /** 创业帮扶协议 **/
    VENTURE_BEFRIEND_PROTOCOL(8);

    private int code;

    AptitudeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 校验附件类型
     *
     * @param type 类型
     * @return {@code boolean}
     */
    public static boolean checkType(Integer type) {
        if (type == null) return false;
        for (AptitudeType aptitudeType : values()) {
            if (aptitudeType.getCode() == type) {
                return true;
            }
        }
        return false;
    }

}
