/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.constant;

/**
 * 编码规则：两位业务组+两位流水号引擎实例+14位日期时间戳+顺序号 定义两位业务组编码
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: BizGroup
 * @Description:
 * 
 * @author: yangjianguo
 * @date: 2015-10-8 下午5:59:30
 * @version V1.0
 */
public final class BizGroup {

    public final static String PS = "10"; // 消费积分

    public final static String BS = "11"; // 业务服务

    public final static String TM = "12"; // 工单系统

    public final static String AO = "13"; // 帐户操作
    
    public final static String AC = "15"; // 账务流水
    
    public final static String BP = "16"; // 业务参数
    
    public final static String TC = "17"; // 调账流水
    
    public final static String WS = "18"; // 积分福利

}
