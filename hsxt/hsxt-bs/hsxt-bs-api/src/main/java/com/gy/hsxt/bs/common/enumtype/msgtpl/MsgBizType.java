/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.common.enumtype.msgtpl;

/**
 * 消息适用业务类型枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype.msgtpl
 * @ClassName: MsgOptType
 * @Description:
 * 
 * @author: kongsl
 * @date: 2015-9-8 下午5:47:52
 * @version V1.0
 */
public enum MsgBizType {

    /**
     * 订单自提码
     */
    MSG_BIZ_TYPE100(100),
    /**
     * 手机短信验证码
     */
    MSG_BIZ_TYPE101(101),
    /**
     * 申报企业成功
     */
    MSG_BIZ_TYPE102(102),
    /**
     * 申报企业授权码短信
     */
    MSG_BIZ_TYPE103(103),
    /**
     * 意外伤害保障生效
     */
    MSG_BIZ_TYPE104(104),
    /**
     * 意外伤害保障撤单失效
     */
    MSG_BIZ_TYPE105(105),
    /**
     * 登录密码重置
     */
    MSG_BIZ_TYPE106(106),
    /**
     * 交易密码重置
     */
    MSG_BIZ_TYPE107(107),
    /**
     * 互生币账户告急
     */
    MSG_BIZ_TYPE108(108),
    /**
     * 互生币账户不足
     */
    MSG_BIZ_TYPE109(109),
    /**
     * 互生币账户偏少
     */
    MSG_BIZ_TYPE110(110),
    /**
     * 扣除年费成功
     */
    MSG_BIZ_TYPE111(111),
    /**
     * 扣除年费失败
     */
    MSG_BIZ_TYPE112(112),
    /**
     * 邮箱绑定验证
     */
    MSG_BIZ_TYPE113(113),
    /**
     * 密码找回验证
     */
    MSG_BIZ_TYPE114(114),
    /**
     * 符合互生免费医疗补贴计划
     */
    MSG_BIZ_TYPE115(115),
    /**
     * 申报服务公司进入办理期中
     */
    MSG_BIZ_TYPE116(116),
    /**
     * 交易密码重置授权码（申请驳回）
     */
    MSG_BIZ_TYPE117(117),
    /**
     * 交易密码重置授权码（申请通过）
     */
    MSG_BIZ_TYPE118(118),
    
    /**
     * 意外伤害保障过期失效
     */
    MSG_BIZ_TYPE119(119),

    ;

    private int code;

    MsgBizType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
