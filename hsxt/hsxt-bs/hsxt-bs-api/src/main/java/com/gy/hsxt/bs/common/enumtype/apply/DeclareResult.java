/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.bs.common.enumtype.apply;

/** 
 * @Description: 申报办理状态或结果
 *
 * @Package: com.gy.hsxt.bs.common.enumtype.apply  
 * @ClassName: DeclareResult 
 * @author: yangjianguo 
 * @date: 2016-4-19 下午12:25:24 
 * @version V1.0  
 */
public enum DeclareResult {
    /** 服务公司提交申报资料 **/
    SUBMIT_BY_SERVICE(0),

    /** 地区平台提交申报资料 **/
    SUBMIT_BY_PLAT(1),

    /** 提交资料复核修改 **/
    SERVICE_MODIFY(2),

    /** 申报资料复核通过 **/
    SERVICE_PASS(3),

    /** 申报资料复核驳回 **/
    SERVICE_REJECTED(4),

    /** 管理公司初审资料修改 **/
    MANAGE_APPR_MODIFY(5),

    /** 管理公司初审通过 **/
    MANAGE_APPR_PASS(6),

    /** 管理公司初审驳回 **/
    MANAGE_APPR_REJECTED(7),

    /** 管理公司复核资料修改 **/
    MANAGE_REVIEW_MODIFY(8),

    /** 管理公司复核通过 **/
    MANAGE_REVIEW_PASS(9),

    /** 管理公司复核驳回 **/
    MANAGE_REVIEW_REJECTED(10),

    /** 公告付款 **/
    PAYING_PLACARD(11),
    
    /** 失效 **/
    PAYING_EXPIRED(12),

    /** 地区平台审核修改资料 **/
    OPEN_SYS_MODIFY(13),

    /** 开启系统成功 **/
    OPEN_SYS_SUCCESS(14),

    /** 开启系统驳回 **/
    OPEN_SYS_REJECTED(15),
    
    ;

    private int code;

    DeclareResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
