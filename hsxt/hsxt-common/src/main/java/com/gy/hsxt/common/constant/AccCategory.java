/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.common.constant;

/** 
 * 账户类型分类
 * @Package: com.gy.hsxt.common.constant  
 * @ClassName: AccCategory 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-10 上午9:39:53 
 * @version V1.0 
 

 */
public enum AccCategory {

    /** 积分账户类型 */
    ACC_TYPE_JF(1),

    /** 互生币账户类型*/
    ACC_TYPE_HSB(2),

    /** 货币账户类型 */
    ACC_TYPE_CNY(3),

    /** 地区平台银行货币存款/地区平台银行货币现金） */
    ACC_TYPE_PFCNY(5);
    
    private int code;

    AccCategory(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
