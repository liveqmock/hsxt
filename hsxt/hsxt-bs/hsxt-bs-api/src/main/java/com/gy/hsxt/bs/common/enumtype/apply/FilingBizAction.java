/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: FilingBizAction
 * @Description: 报备业务操作枚举类
 * 
 * @author: xiaofl
 * @date: 2015-12-28 下午3:42:23
 * @version V1.0
 */
public enum FilingBizAction {

    /** 报备提交 **/
    SUBMIT_FILING(1),
    /** 修改报备资料 **/
    MODIFY_FILING(2),
    /** 地区平台审批报备 **/
    PLAT_APPR_FILING(3),
    /** 地区平台审批异议报备 **/
    PLAT_APPR_DISAGREE_FILING(4);

    private int code;

    FilingBizAction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
