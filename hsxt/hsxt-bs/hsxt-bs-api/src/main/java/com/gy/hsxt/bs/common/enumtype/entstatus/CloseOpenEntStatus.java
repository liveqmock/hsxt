/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus  
 * @ClassName: CloseOpenEntStatus 
 * @Description: 关开系统状态
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午3:04:33 
 * @version V1.0 
 

 */ 
public enum CloseOpenEntStatus {

    /** 待审批 **/
    WAIT_TO_APPR(1),

    /** 通过 **/
    PASS(2),
    
    /** 驳回 **/
    REJECT(3);

    private int code;

    CloseOpenEntStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
