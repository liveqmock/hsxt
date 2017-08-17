/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 工具上下架审批状态枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: ToolApprStatus
 * @Description: 0价格待审批 1价格审批通过 2价格审批驳回 3下架待审批 4下架审批通过 5下架审批驳回
 * @author: likui
 * @date: 2015年9月29日 下午4:39:49
 * @company: gyist
 * @version V3.0.0
 */
public enum ToolApprStatus {

    /** 申请上架(价格待审批) **/
    APP_UP(0),

    /** 上架(价格审批通过) **/
    UP(1),

    /** 拒绝上架(价格审批驳回) **/
    NOT_UP(2),

    /** 申请下架(下架待审批) **/
    APP_DOWN(3),

    /** 下架 (下架审批通过 ) **/
    DOWN(4),

    /** 拒绝下架(下架审批驳回) **/
    NOT_DOWN(5);

    private int code;

    ToolApprStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
