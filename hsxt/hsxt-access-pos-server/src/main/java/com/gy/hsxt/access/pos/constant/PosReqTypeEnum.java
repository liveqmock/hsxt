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

import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.util.CommonUtil;

/**
 * 
 * @Package: com.gy.hsxt.access.pos.constant
 * @ClassName: PosReqTypeEnum
 * @Description: Pos机发起的请求类型枚举类 包含了reqTypeId,序号前缀,中文名称，卡号获取方式，校验密码方式，是否mac校验，是否卡信息校验，是否登录校验，是否状态信息校验等
 * 
 * @author: weiyq
 * @date: 2015-12-10 下午12:31:03
 * @version V1.0
 */
public enum PosReqTypeEnum {

    REQ_POINT_ID("090021000061", "01", "消费积分",PosConstant.INPUT_WAY_MANUAL_CONTENT+PosConstant.INPUT_WAY_STRIPE_CONTENT,
            PosConstant.LOGIN_PIN+PosConstant.NO_PIN,true,true,true,true), 
    REQ_POINT_CANCEL_ID("090020000062", "02", "消费积分撤单",PosConstant.INPUT_WAY_MANUAL_CONTENT+PosConstant.INPUT_WAY_STRIPE_CONTENT,
            PosConstant.LOGIN_PIN,true,true,true,true), 
    REQ_HSB_PAY_CCY_ID("090221104063", "03", "互生币支付流通币",PosConstant.INPUT_WAY_STRIPE_CONTENT,
            PosConstant.TRADE_PIN,true,true,true,true), 
    REQ_HSB_PAY_DC_ID("090221104062", "03", "互生币支付定向",PosConstant.INPUT_WAY_STRIPE_CONTENT,
            PosConstant.TRADE_PIN,true,true,true,true), 
    REQ_HSB_PAY_CANCEL_ID("090420104064", "04", "互生币支付撤单",PosConstant.INPUT_WAY_MANUAL_CONTENT+PosConstant.INPUT_WAY_STRIPE_CONTENT,
            PosConstant.LOGIN_PIN,true,true,true,true), 
    REQ_HSB_PAY_RETURN_ID("090620104065", "05", "互生币支付退货",PosConstant.INPUT_WAY_MANUAL_CONTENT+PosConstant.INPUT_WAY_STRIPE_CONTENT,
            PosConstant.LOGIN_PIN,true,true,true,true), 
    
    //互生订单未完成
    REQ_HSORDER_CASH_PAY_ID("092221102069", "06", "互生订单现金支付","0",
            "1",false,false,false,false), 
    REQ_HSORDER_HSB_PAY_ID("092221104069", "07", "互生订单互生币支付","0",
            "0",false,false,false,false), 
    //互生订单未完成
    
    REQ_HSB_C_RECHARGE_ID("092440000070", "08", "代兑互生币",PosConstant.INPUT_WAY_MANUAL_CONTENT+PosConstant.INPUT_WAY_STRIPE_CONTENT,
            PosConstant.NO_PIN,true,true,true,true), 
    REQ_HSB_B_RECHARGE_ID("092602104071", "09", "兑换互生币",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.TRADE_PIN,true,true,true,true), 
    
    REQ_POINT_REVERSE_ID("040021000061", "00", "消费积分冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,true,false,true,true), 
    REQ_POINT_CANCEL_REVERSE_ID("040020000062", "00", "消费积分撤单冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,true,false,true,true), 
    
    REQ_POINT_DAY_SEARCH_ID("092070000022", "00", "当日订单查询","0",
            "0",true,false,true,true), 
    REQ_POINT_ORDER_SEARCH_ID("092071000003", "00", "单笔订单查询",PosConstant.INPUT_WAY_0_CONTENT,
            "0",true,false,true,true), 
    
    REQ_PARAM_SYNC_ID("0950720000", "00", "参数同步",PosConstant.INPUT_WAY_0_CONTENT,
            "0",false,false,false,true), 
    REQ_PARAM_UPLOAD_ID("0950730000", "00", "参数上传",PosConstant.INPUT_WAY_0_CONTENT,
            "0",false,false,false,true), 
    REQ_HSB_PAY_REVERSE_ID("040021104063", "00", "互生币支付冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,true,false,true,true), 
    REQ_HSB_PAY_CANCEL_REVERSE_ID("040020104064", "00", "互生币支付撤单冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,true,false,true,true), 
    REQ_HSB_PAY_RETURN_REVERSE_ID("040020104065", "00", "互生币支付退货冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,true,false,true,true), 
    REQ_HSORDER_CASH_PAY_REVERSE_ID("040021102069", "00", "互生订单现金支付冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,false,false,false,false), 
    REQ_HSORDER_HSB_PAY_REVERSE_ID("040021104069", "00", "互生订单互生币支付冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,false,false,false,false), 
    
    REQ_HSORDERS_SEARCH_ID("0920740000", "00", "互生订单列表查询","0",
            "0",true,false,false,true), 
    REQ_HSORDER_SEARCH_ID("0920750000", "00", "互生订单单笔查询","0",
            "0",true,false,false,true), 
    REQ_HSB_C_RECHARGE_REVERSE_ID("040040000070", "00", "代兑互生币冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,true,false,true,true), 
    REQ_HSB_B_RECHARGE_REVERSE_ID("040002104071", "00", "兑换互生币冲正",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,true,false,true,true), 
    REQ_BATCH_SETTLE_ID("050000", "00", "批结算",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,false,false,false,true), 
    REQ_BATCH_UPLOAD_ID("032000", "00", "批上传",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,false,false,false,true), 
    REQ_BALANCE_SEARCH_ID("0920311010", "00", "企业预付款账户余额","0",
            "0",true,false,true,true), 
    REQ_CARD_CHREQ_ID("010038000001", "00", "卡状态检查",PosConstant.INPUT_WAY_STRIPE_CONTENT,
            "0",true,true,true,true), 
    REQ_CARDNAME_SEARCH_ID("0920330000", "00","互生卡信息查询","0",
            "0",true,false,true,true), 
    REQ_SIGNIN_ID("080000", "00", "签到",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,false,false,false,true), 
    REQ_SIGNOFF_ID("082000", "00", "签退",PosConstant.INPUT_WAY_0_CONTENT,
            PosConstant.NO_PIN,false,false,false,true);

    /**
     * 请求类型码
     */
    private String reqType;
    /**
     * 请求流水号前缀
     */
    private String seqPrefix;
    /**
     * 请求名称
     */
    private String name;
    
    /**
     * 可能的输入方式
     * 0，未定义
     * 1，手输卡号
     * 2, 刷卡
     */
    private String inputWay;
    
    /**
     * 可能的密码校验方式
     * 0，交易密码
     * 1，登录密码
     * 2，无密码
     */
    private String pinWay;
    /**
     * 是否需要MAC校验
     */
    private boolean macCheck;
    /**
     * 是否需要卡信息校验
     */
    private boolean cardCheck;
    /**
     * 是否需要登录检查状态
     */
    private boolean signinCheck;
    /**
     * 状态检查（企业状态，POS机状态）
     */
    private boolean statusCheck;
    

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getSeqPrefix() {
        return seqPrefix;
    }

    public void setSeqPrefix(String seqPrefix) {
        this.seqPrefix = seqPrefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinWay() {
        return pinWay;
    }

    public void setPinWay(String pinWay) {
        this.pinWay = pinWay;
    }
    
    public String getInputWay() {
        return inputWay;
    }

    public void setInputWay(String inputWay) {
        this.inputWay = inputWay;
    }

    public boolean isMacCheck() {
        return macCheck;
    }

    public void setMacCheck(boolean macCheck) {
        this.macCheck = macCheck;
    }

    public boolean isCardCheck() {
        return cardCheck;
    }

    public void setCardCheck(boolean cardCheck) {
        this.cardCheck = cardCheck;
    }

    public boolean isSigninCheck() {
        return signinCheck;
    }

    public void setsigninCheck(boolean signinCheck) {
        this.signinCheck = signinCheck;
    }

    public boolean isStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(boolean statusCheck) {
        this.statusCheck = statusCheck;
    }

    public static String getNameByReqType(String reqType) {
        for (PosReqTypeEnum c : PosReqTypeEnum.values())
        {
            if (reqType.equalsIgnoreCase(c.getReqType()))
            {
                return c.getName();
            }
        }
        return null;
    }

    public static String getSeqPrefixByReqType(String reqType) throws PosException{
        for (PosReqTypeEnum c : PosReqTypeEnum.values())
        {
            if (reqType.equalsIgnoreCase(c.getReqType()))
            {
                return c.getSeqPrefix();
            }
        }
        CommonUtil.checkState(true, "请求报文交易类型reqId未做类型映射。", PosRespCode.REQUEST_PACK_FORMAT);
        return null;
    }

    

    private PosReqTypeEnum(String reqType, String seqPrefix, String name, String inputWay, String pinWay,
            boolean macCheck, boolean cardCheck, boolean signinCheck, boolean statusCheck) {
        this.reqType = reqType;
        this.seqPrefix = seqPrefix;
        this.name = name;
        this.inputWay = inputWay;
        this.pinWay = pinWay;
        this.macCheck = macCheck;
        this.cardCheck = cardCheck;
        this.signinCheck = signinCheck;
        this.statusCheck = statusCheck;
    }

    public static PosReqTypeEnum getTypeByTypeId(String reqTypeId){
        for (PosReqTypeEnum c : PosReqTypeEnum.values()) {
            if (reqTypeId.equalsIgnoreCase(c.getReqType())) {
                return c;
            }
        }
        return null;
    }

}
