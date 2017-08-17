/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: RealNameAuthBizAction
 * @Description: 实名认证业务操作枚举
 * 
 * @author: xiaofl
 * @date: 2015-12-29 下午4:56:29
 * @version V1.0
 */
public enum RealNameAuthBizAction {

    /** 实名认证申请提交 **/
    SUBMIT_APPLY(1),
    /** 实名认证申请初审修改 **/
    APPR_MODIFY(2),
    /** 实名认证申请初审通过 **/
    APPR_PASS(3),
    /** 实名认证申请初审驳回 **/
    APPR_REJECT(4),
    /** 实名认证申请复核修改 **/
    REVIEW_MODIFY(5),
    /** 实名认证申请复核通过 **/
    REVIEW_PASS(6),
    /** 实名认证申请复核驳回 **/
    REVIEW_REJECT(7),

    ;

    private int code;

    RealNameAuthBizAction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
