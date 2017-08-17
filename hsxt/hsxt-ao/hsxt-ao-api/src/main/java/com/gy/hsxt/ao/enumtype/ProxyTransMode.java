/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.enumtype;

/**
 * 代兑方式枚举定义
 * 
 * @Package: com.gy.hsxt.ao.enumtype
 * @ClassName: ProxyTransMode
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-15 上午9:29:19
 * @version V3.0.0
 */
public enum ProxyTransMode {

    /** 本地企业本地消费者 **/
    LOCAL_ENT_TO_LOCAL_CUST(1),
    /** 本地企业异地消费者 **/
    LOCAL_ENT_TO_DIFF_CUST(2),
    /** 异地企业本地消费者 **/
    DIFF_ENT_TO_LOCAL_CUST(3), ;

    private Integer code;

    ProxyTransMode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
