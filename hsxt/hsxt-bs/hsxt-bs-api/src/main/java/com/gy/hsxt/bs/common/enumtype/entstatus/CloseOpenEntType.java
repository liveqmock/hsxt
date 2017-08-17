/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.entstatus;

/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.entstatus  
 * @ClassName: CloseOpenEntType 
 * @Description: 关开系统申请类型
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午3:05:03 
 * @version V1.0 
 

 */ 
public enum CloseOpenEntType {

    /** 关闭系统 **/
    CLOSE_SYS(1),

    /** 开启系统 **/
    OPEN_SYS(2);

    private int code;

    CloseOpenEntType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
