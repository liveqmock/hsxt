/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.enumtype.apply;

/** 
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.apply  
 * @ClassName: ResFeeChargeMode 
 * @Description: 资源费收费方式
 *
 * @author: xiaofl 
 * @date: 2015-12-15 下午3:03:38 
 * @version V1.0 
 

 */ 
public enum ResFeeChargeMode {

    /** 1：全额(默认) **/
    ALL(1),

    /** 2：全免 **/
    NONE(2),

    /** 3：暂欠 **/
    DEBT(3);

    private int code;

    ResFeeChargeMode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
