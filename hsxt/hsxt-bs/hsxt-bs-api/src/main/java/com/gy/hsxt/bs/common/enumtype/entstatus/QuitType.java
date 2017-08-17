/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus
 * @ClassName: QuitType
 * @Description: 成员企业注销、报停方式
 * 
 * @author: xiaofl
 * @date: 2015-9-10 下午3:17:43
 * @version V1.0
 */
public enum QuitType {
    
    /** 注销 **/
    APPLY_QUIT(1),
    
    /** 报停 **/
    FORCE_QUIT(2);

    private int code;

    QuitType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
