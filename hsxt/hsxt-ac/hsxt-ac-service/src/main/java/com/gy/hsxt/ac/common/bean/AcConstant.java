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

package com.gy.hsxt.ac.common.bean;

/** 
 * 账务分录的常量类
 * @Package: com.gy.hsxt.ac.common.bean  
 * @ClassName: AcConstant 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-12-14 下午4:24:04 
 * @version V1.0 
 

 */
public class AcConstant {

    
    /** 客户类型分类：0 消费者类型，1 企业类型，2 平台类型 */
    public static final Integer CUST_TYPE_CATEGORY[] = { 0, 1, 2 };

    /** 账户状态：0、正常，许增许减，1、许增不许减.2 许减不许增。3 不许增不许减.4 永久关闭 */
    public static final Integer ACC_STATUS[] = { 0, 1, 2, 3, 4 };

    /** 0:正常的账务分录标识 ,非0： 冲正红冲的分录标识 */
    public static final Integer WRICKBACK_FLAG = 0;

    /** 账户类型标志位:0、正常，1、待清算/待结算，待分配（待增），2：预扣留（待减），释放，3：冻结,解冻 */
    public static final char ACC_TYPE_FLAG[] = { '0', '1', '2', '3' };

    /** 业务操作类型: 0 ：预留操作,1：释放操作，2：冻结操作,3:解冻操作 */
    public static final Integer OPERATE_TYPE[] = { 0, 1, 2, 3 };

    /** 按顺序检查账户余额，每次只能扣减一个账户 */
    public static final Integer ONLY_ONE_ACCT_DEDUCT_SEQ = 1;

    /** 按顺序检查账户余额，首先从第一个账户中扣减，如果第一个账户不足扣减，则从第二个账户继续扣减剩余部份，依此类推扣减 */
    public static final Integer ONLY_OR_COMBILE_ACCT_DEDUCT_SEQ = 2;
    
    /** 账户类型 */
    public static final String ACCTYPES[] = { "10110", "20110", "30110"};
    
    /** 账户可以为正 **/
    public static final  int ACCOUNT_PLUS = 1;
    
    /** 账户可以为负 **/
    public static final  int ACCOUNT_MINUS = 2;
    
    
    
    
    
    
    
    
    
    
}
