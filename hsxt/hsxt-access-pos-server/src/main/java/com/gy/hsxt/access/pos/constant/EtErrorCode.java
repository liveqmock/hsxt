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

package com.gy.hsxt.access.pos.constant;

/**
 * 
 * @Package: com.gy.hsxt.access.pos.constant
 * @ClassName: EtErrorCode
 * @Description: TODO
 * 
 * @author: zhucy
 * @date: 2015-12-21 下午5:55:55
 * @version V1.0
 */
public enum EtErrorCode {
    SUCCESS(200, "成功"), 
    FAILE(201, "失败"), 
    ORDER_NOT_EXIST(780, "订单不存在"), 
    ENT_ERROR(219, "企业互生号为空"), 
    DATD_NOT_EXIST(204,"数据不存在"),
    BALANCE_NOT_ENOUGH(590,"账户余额不足"),
    //start-added by liuzh on 2016-04-26
    AO_DAILY_AMOUNT_MORE_THEN_MAX(43272,"兑换互生币金额超出单日最大限额"), //兑换互生币金额超出单日最大限额   
    //end-added by liuzh on 2016-04-26
    
    //start-added by liuzh on 2016-05-24
    //网上订单错误码
    NOT_SUPPORT_CASH(554,"暂不支持现金尾款支付"), //餐饮尾款支付 ,返回状态码
    ORDER_PAY_PROCESS(782,"订单支付业务处理中"),
    ORDER_PAID(791,"订单已付款"),
    USER_NOT_EXIST(803,"用户不存在"),    
    //end-added by liuzh on 2016-05-24
    ;
    private int errorCode;

    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private EtErrorCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
