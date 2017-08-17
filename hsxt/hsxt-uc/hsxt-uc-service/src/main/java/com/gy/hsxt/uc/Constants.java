/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc;

/**
 * 常量类
 * 
 * @Package: com.gy.hsxt.uc  
 * @ClassName: Constants 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-25 下午3:15:53 
 * @version V1.0
 */
public class Constants {
    /**
	 * 绑定银行状态
	 */
    public final static String BIND_BANK_STATE = "1";
	/**
	 * 未绑定银行状态
	 */
    public final static String UNBIND_BANK_STATE = "0";
    /**
     * 通用状态：是
     */
    public final static String YES = "1";
    /**
     * 通用状态：否
     */
    public final static String NO = "0";
    
    /**
     * 常用GUID前缀，默认为1
     */
    public final static String GENERAL_GUID_PERFIX = "1";
    
    /**客户号首位数字(包含非持卡人、POS、刷卡器、平板、系统用户) add by tianxh  begin **/
    /**
     * 非持卡人
     */
    public final static String CUSTID_NOCARDER_PREFIX = "9";
    /**
     * POS
     */
    public final static String CUSTID_POS_PREFIX = "1";
    /**
     * 刷卡器
     */
    public final static String CUSTID_CARDREADER_PREFIX = "2";
    /**
     * 平板
     */
    public final static String CUSTID_PAD_PREFIX = "3";
    /**
     * 系统用户
     */
    public final static String CUSTID_SYS_PREFIX = "8";
    
    /**客户号首位数字(包含非持卡人、POS、刷卡器、平板、系统用户) add by tianxh  end **/
    
    /**用户类型  begin **/
    /**
     * 非持卡人用户类型
     */
    public final static String USER_TYPE_NOCARD_HOLDER = "1";
    
    /**
     * 操作员用户类型
     */
    public final static String USER_TYPE_OPERATOR = "3";
    /**
     * 企业用户类型
     */
    public final static String USER_TYPE_ENT = "4";
    
    /**
     * 持卡人用户类型
     */
    public final static String USER_TYPE_CARD_HOLDER = "2";
    
    /**
     * POS用户类型
     */
    public final static String USER_TYPE_POS = "pos";
    
    /**
     * 刷卡器用户类型
     */
    public final static String USER_TYPE_CARDREADER = "cardReader";
    
    /**
     * 平板用户类型
     */
    public final static String USER_TYPE_PAD = "pad";
    
    /**
     * 系统用户的用户类型
     */
    public final static String USER_TYPE_SYS = "sys";
    
    /**用户类型  end **/
    
    
    /*************************** 企业客户号类型常量 begin add by tianxh ******************************/
    
    /**
     * 持卡人
     */
    public final static String CARD_HOLDER = "1";
    
    /**
     * 成员企业
     */
    public final static String MEMBERS_ENT = "2";
    
    /**
     * 托管企业
     */
    public final static String HOSTING_ENT = "3";
    
    /**
     * 服务公司
     */
    public final static String SERVICE_COMPANY= "4";
    
    /**
     * 管理公司
     */
    public final static String MANAGE_COMPANY = "5";
    
    /**
     * 地区平台
     */
    public final static String REGION_PLATFORM = "6";
    
    /**
     * 总平台
     */
    public final static String CHIEF_PLATFORM = "7";
    
    /**
     * 其他地区平台
     */
    public final static String OTHER_REGION_PLATFORM = "8";
    
    /**
     * 操作员
     */
    public final static String OPERATOR = "11";
    
    /**
     * POS机
     */
    public final static String POS = "21";
    
    /**
     * 积分刷卡器
     */
    public final static String INTEGRAL_CARD_READER = "22";
    
    /**
     * 消费刷卡器
     */
    public final static String CONSUME_CARD_READER = "23";
    
    /**
     * 平板
     */
    public final static String PAD = "24";
    
    /**
     * 云台
     */
    public final static String CLOUD_DECK = "25";
    
    /**
     * 非持卡人
     */
    public final static String NOCARD_HOLDER = "51";
    
    /**
     * 短信发送人
     */
    public final static String SENDER_ENT = "互生系统平台";
    
    /**
     * 短信发送人
     */
    public final static String SENDER_CONSUMER = "互生消费积分管理平台";
    
    
    /**
     * 非互生格式化企业
     */
    public final static String NOT_INTERGROWTH_ENT = "52";
    
    /*************************** 企业客户号类型常量 end add by tianxh ******************************/
    
    /**
     * 
     */
    public final static String VERTICAL = "|";
}
