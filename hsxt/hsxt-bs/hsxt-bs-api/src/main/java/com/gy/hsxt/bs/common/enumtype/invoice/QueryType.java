/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.enumtype.invoice;

/**
 * 发票状态查询
 * @Package : com.gy.hsxt.bs.common.enumtype.invoice
 * @ClassName : QueryType
 * @Description : 发票状态查询
 *  0 -全部查询 1-审核查询 2-配送查询
 * @Author : chenm
 * @Date : 2016/1/19 12:42
 * @Version V3.0.0.0
 */
public enum QueryType {

    /**
     * 全部查询
     */
    FULL,

    /**
     * 审核查询
     */
    PENDING,

    /**
     * 配送查询
     */
    POSTING

}
