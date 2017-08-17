/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: TempletStatus
 * @Description: 合同模板状态
 * 
 * @author: xiaofl
 * @date: 2015-9-10 下午2:13:40
 * @version V1.0
 */
public enum TempletType {

    /** 合同模板 **/
    CONTRACT(1),

    /** 第三方证书 **/
    THIRD_PARTY_CRE(2),

    /** 销售资格证 **/
    SALES_CRE(3);

    private int code;

    TempletType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
