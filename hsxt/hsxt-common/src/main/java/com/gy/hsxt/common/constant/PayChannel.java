/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.common.constant;

/**
 * 支付方式的枚举定义
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: PayChannel
 * @Description: 支付方式的枚举定义
 * 
 * @author: kongsl
 * @date: 2016-1-13 上午11:22:49
 * @version V3.0.0
 */
public enum PayChannel {

    /**
     * 网银支付
     */
    E_BANK_PAY(100, "网银支付"),

    /**
     * 手机移动支付
     */
    MOBILE_PAY(101, "手机移动支付"),

    /**
     * 快捷支付
     */
    QUICK_PAY(102, "快捷支付"),

    /**
     * 银行卡支付
     */
    CARD_PAY(103, "银行卡支付"),

    /**
     * 快捷支付非首次
     */
    UNFIRST_QUICK_PAY(104, "快捷支付非首次"),

    /**
     * 互生币支付
     */
    HS_COIN_PAY(200, "互生币支付"),

    /**
     * 货币支付
     */
    MONEY_PAY(300, "货币支付"),

    /**
     * 转账汇款(临帐)
     */
    TRANSFER_REMITTANCE(400, "转账汇款(临帐)");

    private Integer code;

    /**
     * 描述
     */
    private String describe;

    PayChannel(Integer code, String describe) {
        this.code = code;
        this.setDescribe(describe);
    }

    public static Integer getCode(String describe) {
        for (PayChannel payChannel : PayChannel.values())
        {
            if (payChannel.getDescribe().equals(describe))
            {
                return payChannel.getCode();
            }
        }
        return null;
    }

    /**
     * 校验支付方式
     * 
     * @param code
     *            代码
     * @return boolean
     */
    public static boolean checkChannel(int code) {
        for (PayChannel channel : PayChannel.values())
        {
            if (channel.getCode() == code)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据值查找对象
     * 
     * @param code
     * @return
     */
    public static PayChannel getPayChannel(int code) {
        PayChannel[] payChannelEnums = PayChannel.values();
        for (PayChannel payChannelEnum : payChannelEnums)
        {
            if (payChannelEnum.getCode() == code)
            {
                return payChannelEnum;
            }
        }
        return null;
    }

    /**
     * 需要获取支付地址的方式
     * 
     * @Description:
     * @author: likui
     * @created: 2015年12月17日 下午2:11:41
     * @param code
     * @return
     * @return : boolean
     * @version V3.0.0
     */
    public static boolean isGainPayAddress(int code) {
        switch (valueOf(getPayChannel(code).name().toUpperCase()))
        {
        case E_BANK_PAY:
            return true;
        case QUICK_PAY:
            return true;
        case MOBILE_PAY:
            return true;
        case CARD_PAY:
            return true;
        default:
            return false;
        }
    }

    public static String getDescribe(int code) {
        for (PayChannel payChannel : PayChannel.values())
        {
            if (payChannel.getCode() == code)
            {
                return payChannel.getDescribe();
            }
        }
        return null;
    }

    /**
     * 统一记账的支付方式
     * 
     * @Description:
     * @author: likui
     * @created: 2015年12月25日 上午9:20:56
     * @param code
     * @return
     * @return : boolean
     * @version V3.0.0
     */
    public static boolean toolUnifyAcctPayChannel(int code) {
        switch (valueOf(getPayChannel(code).name().toUpperCase()))
        {
        case E_BANK_PAY:
            return true;
        case QUICK_PAY:
            return true;
        case MOBILE_PAY:
            return true;
        case TRANSFER_REMITTANCE:
            return true;
        case CARD_PAY:
            return true;
        default:
            return false;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
