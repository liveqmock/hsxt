/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply  
 * @ClassName: ResFeeAllocMode 
 * @Description: 资源费分配方式
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午3:03:15 
 * @version V1.0 
 

 */ 
public enum ResFeeAllocMode {

    /** 1：正常分配(默认) **/
    ALLOCATE(1),

    /** 2：无分配 **/
    NO_ALLOCATE(2),

    /** 3：暂缓分配 **/
    ALLOCATE_LATER(3);

    private int code;

    ResFeeAllocMode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
