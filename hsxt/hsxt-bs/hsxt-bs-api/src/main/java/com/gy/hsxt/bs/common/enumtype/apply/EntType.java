/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: EntType
 * @Description: 企业性质
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午9:29:07
 * @version V1.0
 */
public enum EntType {

    /** 私有企业 **/
    PRIVATE_ENT(1),

    /** 国有企业 **/
    STATE_OWNED_ENT(2),

    /** 合资企业 **/
    JOINT_ENT(3);

    private int code;

    EntType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
