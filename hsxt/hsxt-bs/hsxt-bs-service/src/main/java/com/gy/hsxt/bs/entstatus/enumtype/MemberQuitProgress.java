/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.entstatus.enumtype;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.enumtype
 * @ClassName: MemberQuitProgress
 * @Description: 销户过程枚举
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:23:14
 * @version V1.0
 */
public enum MemberQuitProgress {

    /** 销户审批通过 **/
    APPR_PASS(0),
    /** 增值系统已销户 **/
    BM_ACCOUNT_CANCELED(1),
    /** 互生币已转成货币 **/
    HSB_TO_HB_COMPLETED(2),
    /** 银行转账已提交 **/
    BANK_TRANS_SUBMITTED(3),
    /** 用户中心已销户 **/
    UC_ACCOUNT_CANCELED(4),
    /** 互生号已释放 **/
    RES_NO_RELEASED(5);

    private Integer code;

    MemberQuitProgress(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
