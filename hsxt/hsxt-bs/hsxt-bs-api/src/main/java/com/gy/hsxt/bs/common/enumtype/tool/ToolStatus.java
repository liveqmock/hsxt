/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.tool;

/**
 * 工具上下架状态枚举类
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.tool
 * @ClassName: ToolStatus
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:40:15
 * @company: gyist
 * @version V3.0.0
 */
public enum ToolStatus {
    /** 未上架(新增或已下架) **/
    DOWN(0),

    /** 已上架 **/
    UP(1),

    ;

    private int code;

    ToolStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
