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

package com.gy.hsxt.bp.client.common.bean;

/** 
 * 常量类
 * @Package: com.gy.hsxt.bp.common.bean  
 * @ClassName: Constants
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-25 下午5:04:57 
 * @version V1.0 
 

 */
public class BPConstants {

    /** 激活状态：Y **/
    public final static String IS_ACTIVE_YES = "Y";

    /** 未激活状态：N **/
    public final static String IS_ACTIVE_NOT = "N";

    /** 是公共参数：Y **/
    public final static String IS_PUBLIC_YES = "Y";

    /** 不是公共参数：N **/
    public final static String IS_PUBLIC_NOT = "N";

    /** 访问级别：0-不可见；如密码，证书等,1-可查看不可修改,2-可修改不可删除,3-可修改可删除 **/
    public final static int ACCESS_LEVEL[] = { 0, 1, 2, 3 };   
    
    /** 货币代码:币种   (000：互生币,001：积分,156：人民币)**/
    public final static String CURRENCY_CODE[] = {"000","001","156"};
    
    /** Redis服务器中业务参数系统标识 **/
    public final static String SYS_NAME = "HSXT_BP";
    
}
