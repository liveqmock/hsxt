/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.common.constant;


/***************************************************************************
 * <PRE>
 * Description 		: 互生卡挂失状态
 *
 * Project Name   	: hsxt-common 
 *
 * Package Name     : com.gy.hsxt.common.constant  
 *
 * File Name        : CardLossType 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-12-15 下午2:40:11
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-12-15 下午2:40:11
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/

public enum CardLossType {
    /** 启动 **/
    START_UP(1),
    
    /** 挂失 **/
    LOSS(2);
    private int code;

    CardLossType(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }
}
