/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: FilingStatus
 * @Description: 报备状态
 * 
 * @author: xiaofl
 * @date: 2015-9-10 上午9:55:46
 * @version V1.0
 */
public enum FilingStatus {

    /** 待提交(草稿) **/
    WAIT_TO_SUBMIT(0),

    /** DRV:待审核 **/
    WAIT_TO_APPROVE(1),

    /** GYT:预约面谈 **/
    DATE_TO_INTERVIEW(2),

    /** GYB:预约培训 **/
    DATE_TO_TRAIN(3),

    /** WTP:通知打款 **/
    NOTIFY_TO_REMIT(4),

    /** FRP:报备通过 **/
    FILING_APP_PASS(5),

    /** FRR:报备驳回 **/
    FILING_APP_REJECT(6),

    /** DRR异议驳回 **/
    OPPOSE_REJECTED(7);

    private int code;

    FilingStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
