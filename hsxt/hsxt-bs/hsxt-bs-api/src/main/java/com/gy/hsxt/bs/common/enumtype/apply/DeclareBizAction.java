/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply
 * @ClassName: DeclareBizAction
 * @Description: 申报业务阶段枚举类
 * 
 * @author: xiaofl
 * @date: 2015-12-28 下午3:42:23
 * @version V1.0
 */
public enum DeclareBizAction {
    /**
     * 资料提交
     */
    SUBMIT(1),

    /**
     * 资料审核
     */
    APPROVED(2),
    
    /**
     * 办理期中
     */
    PAYING(3),
    /**
     * 开启系统时期
     */
    OPEN_SYS(4)
    
    ;

    private int code;

    DeclareBizAction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
