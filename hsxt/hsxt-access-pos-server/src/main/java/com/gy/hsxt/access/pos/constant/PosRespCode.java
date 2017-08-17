/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.constant;

import com.gy.hsxt.common.constant.IRespCode;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.common.constant.RespCode;
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.constant  
 * @ClassName: PosRespCode 
 * @Description: 向pos机发送的应答码。00~99间的尽量不改（明确标记"不可改动"的严禁修改，pos需据此编码做判断）
 * 					可扩展字母型的任意定义，仅起到pos机界面提示作用。
 *
 * @author: guiyi149 
 * @date: 2015-11-10 下午12:09:42 
 * @version V1.0
 */
public enum PosRespCode implements IRespCode {

	SUCCESS(9000, "操作成功"), //编码不可改动
	
	REQUEST_PACK_PARAM_ERR(9003, "报文参数有误"),
	REQUEST_PACK_FORMAT(9004, "报文格式有误"),
	PIN_FORMAT(9005, "PIN格式错误"),
	MAC_CHECK(9006, "MAC校验失败"),

	INVALID_CARD_DEVICE(9011, "无效卡设备"),  //#未使用
	//无效卡号
	INVALID_CARD(9012, "无效卡"), 
//	INVALID_POSCODE(9013, "非法机器码"),
	INVALID_CMD(9014, "无效cmd"),				  
	INVALID_HSB_ORDER(9015, "无效互生币撤单流水号"),  //#未使用
	INVALID_CARD_LOSS(9016, "互生卡挂失申请中,暂停使用"),
	POSCODE_UNEXIST(9017, "该机器号未入库"),		//#未使用
	INVALID_HSB_ORDER_REFUND(9018, "无效互生币退货流水号"),  //#未使用
	
	
	NO_AREA_CENTER(9020, "不在本地区中心"),	//#未使用
	NO_OLD_RECORD(9021, "没有原记录"),     //#未使用
	QR_BILL_NO_TRADE(9022, "二维码交易单据尚未支付"),
	
	//start--modified by liuzh on 2016-05-30
	//NO_POS_ORDER(9023, "交易流水号不存在"),
	NO_POS_ORDER(9023, "不支持当前交易流水号查询"),
	//end--modified by liuzh on 2016-05-30
	
	//企业不能做卡积分
	NO_ENT_CARDPOINT(9024, "已停止积分活动！"), 
	NO_TRADEDATA(9025, "没有交易数据"),    //#未使用
	NO_FOUND_DEVICE(9026, "设备未找到"),
	NO_FOUND_ACCT(9027, "没有此账户"),
	NO_FOUND_ENT(9028, "没有此企业"),
	NO_FOUND_HSEC_ORDER(9029, "没有订单信息"),
//	ORDER_REVERSEING(9030,"订单正在冲正中，请稍后在试"),
	TREM_NO_ENT(9031,"该终端设备所属企业与登陆企业不匹配"),  //#未使用
	EARNEST_NOT_FOUND(9032, "没有可结算的定金单"),
	TEMPORARY_FORBIDDEN_FUNC(9033, "本功能暂不可用"), //#未使用
	
	//start--added by liuzh on 2016-06-06
	TRANS_PWD_NOT_SET(9034, "交易密码未设置"),
	TRADEPWD_USER_LOCKED(9035, "交易密码连续出错，已锁定"),
	//end--added by liuzh on 2016-06-06
	
	REPEAT_OPERATE(9041, "重复操作"),   //#未使用
	REPEAT_CANCLE(9042, "重复撤单，原交易已撤单或已退货"),
	REPEAT_REVERSE(9043, "重复冲正"), 
	REPEAT_POS_ORIGINNO(9044, "重复输入流水号"),   //#未使用
	REPEAT_BACK(9045, "重复退货，原交易已撤单或已退货"),
	REPEAT_CANCLE_BACK(9046, "重复撤单或退货"),    //#未使用
	//start--added by liuzh on 2016-05-17
	ISSETTLE_CANCELLATION(9047, "订单已结算,无法撤单"),
	//end--added by liuzh on 2016-05-17
	//start--modified by liuzh on 2016-05-11
	//OUT_NOREG_SINGLE_LIMIT(9050, "非实名注册用户超出可代兑金额限制"),
	OUT_NOREG_SINGLE_LIMIT(9050, "超出可代兑金额限制"),
	//end--modified by liuzh on 2016-05-11
	
	OUT_POINTRATE_SCOPE(9051, "超出积分比例范围"),
	OUT_PASS_TIMES(9052, "超出密码输入最大次数"),   //#未使用
	OUT_SINGLE_LIMIT(9053, "超出本次输入金额限制"), //#未使用 
	OUT_DAILY_LIMIT(9054, "超出当天可兑换金额限制"), //#未使用 
	CHECK_ENT_CANCEL(9055, "不能跨企业撤单"),     //#未使用
	CHECK_ORIGINNO(9056, "无效积分撤单流水号"),    //#未使用
	CHECK_SETTLE_FAIL(9057, "对账不平"), //编码不可改动
	CHECK_ORDER_STATUS(9058, "此订单无效"),     //#未使用
	CHECK_ISSETTLE(9059, "此批次已结算"),		 //#未使用
	
    CHECK_ASSUREOUTVALUE(9060, "积分金额验证出错"), //
    
    //start--modified by liuzh on 2016-05-30
	//CHECK_AMT_TOO_SMALL(9061, "积分金额太小，取消本次积分"), //
	CHECK_AMT_TOO_SMALL(9061, "退货积分金额小于0.1，无法进行本次操作"),
	//end--modified by liuzh on 2016-05-30
	
	CHECK_AMT_TOO_LARGE(9062, "积分金额太大，取消本次积分"), //#未使用
	CHECK_CARDORPASS_FAIL(9063, "卡或密码出错"), //
	CHECK_PERSON_INFO_FAIL(9064, "个人信息不完整"), //#未使用
	CHECK_ACCOUNT_FREEZE(9066, "此帐户冻结"), //
	CHECK_ACCOUNT_DEBTSTATUS(9067, "此帐户欠费"), //#未使用
	CHECK_ENT_STATUS_FAIL(9068, "企业状态异常，不可进行本交易"), //
	CHECK_PASS_FAIL(9069, "交易失败，密码出错"), //
	
	INSUF_BALANCE_CUST_CASH(9065, "消费者货币账户余额不足"), //#未使用
	//start--modified by liuzh on 2016-04-25
	//INSUF_BALANCE_CUST_HSB(9070, "消费者流通币余额不足"), //
	INSUF_BALANCE_CUST_HSB(9070, "消费者互生币账户余额不足"), //
	//end--modified by liuzh on 2016-04-25
	INSUF_BALANCE_ENT_CASH(9071, "企业货币账户余额不足"),
	INSUF_BALANCE_ENT_HSB(9072, "企业互生币账户余额不足"),
	AMT_LARGER_THAN_ORIG(9073, "输入金额超出原交易金额"),  //#未使用
	//start--modified by liuzh on 2016-04-29
	//CHECK_POINT_BALANCE_FAIL(9074, "个人积分账户余额不足"),
	CHECK_POINT_BALANCE_FAIL(9074, "消费者积分账户余额不足"),
	INSUF_BALANCE_PER_CASH(9075,"消费者货币账户余额不足"),
	//NO_HSB(9075, "互生币余额不足"), 
	//end--modified by liuzh on 2016-04-29
	RA_GREATER_THAN_TA(9076, "退货失败，退货额不能大于交易额"), 
	NOT_SUPPORT_CASH(9077, "餐饮尾款暂不支持现金支付"), 
	
	//start--added by liuzh on 2016-05-24
	ORDER_PAY_PROCESS(9078, "订单支付业务处理中"),
	ORDER_PAID(9079, "订单已付款"),	
	//end--added by liuzh on 2016-05-24
	
	//start--add by liuzh on 2016-04-25
	AO_DAILY_AMOUNT_MORE_THEN_MAX(9080, "兑换互生币金额超出单日最大限额"),
	AO_CHANGING_IMPORTENT_INFO(9081, "企业重要信息变更期，不可进行本交易"),	
	ENT_STATUS_OWE_FEE_INFO(9082, "企业欠年费，不可进行本交易"),	
	INSUF_BALANCE_ENT_POINT(9083, "企业积分账户余额不足"),
	//end--add by liuzh on 2016-04-25
	
//	CREATE_ORIGINNO_FAIL(9080, "生成流水号失败"), //

	/**
	 * 业务触发
	 */
	AGAIN_REVERSE(9084, "需要重新冲正"),//代码不可改动
	SYNC_PARAM(9085, "同步参数"), 
	AGAIN_SIGNIN(9086, "重新签到"), //不可改动
	MAC_AGAIN_SIGNIN(9087, "MAC校验3次不一致，请重新签到"), //不可改动
	//start--add by liuzh
	AC_REFUND_OVER(9088, "已经完全退款"),
	AC_REFUND_OVER_AVALIABLE(9089, "当前退款金额大于原单可退款金额"),
	//end--add by liuzh
	
	POS_STATUS(9090, "本工具已停止使用！"), 
	//start--modify by liuzh on 2016-04-21
	//POS_CENTER_FAIL(9091, "POS服务中心出现故障"),
	POS_CENTER_FAIL(9091, "POS服务暂不可用"),
	//end--modify by liuzh on 2016-04-21
	OTHER_SERVER_FAIL(9092, "其它服务异常！未作转换处理"),
	POS_FUNC_FAIL(9093, "本项功能暂不可用，请稍后再试"),
	CONSUMER_INFO_ABNORMITY(9094, "消费者信息异常"), //#未使用
	SYS_PERMISSION_FAIL(9096, "无系统权限"),     //#未使用
	SYS_DB_FAIL(9097, "数据库异常"),            //#未使用
	SYS_BUSY(9098, "系统繁忙"),                //#未使用
	TX_ALREADY_SUCCESS(9099, "交易已成功，无法冲正"),//不可改动
	
	/**
	 * 兑换代兑互生币
	 */
	HSB_PROXY_RECHARGE_MAX_AMT_ERROR("90R1","代兑金额超出单笔上限"),
	HSB_ENT_RECHARGE_MAX_TIMES_ADAY_ERROR("90R2","超出当日允许兑换次数"),
	
	/**
	 * 金额限制约束
	 */
	//start--modified by liuzh on 2016-05-18
	//HSB_PAY_MAX_OVER_ERROR("90P1","互生币单笔支付超限"),
	//HSB_PAY_DAY_MAX_OVER_ERROR("90P2","互生币支付每日限额超限"),
	//HSB_CONSUMER_PAYMENT_MAX("90P1", "互生币单笔支付超限"), 
	//HSB_CONSUMER_PAYMENT_DAY_MAX("90P2", "互生币支付每日超限"), 
	HSB_CONSUMER_PAYMENT_MAX("90P1", "当前支付金额超过您设置的单笔互生币支付限额"), 
	HSB_CONSUMER_PAYMENT_DAY_MAX("90P2", "单日支付累计金额超过您设置的每日互生币支付限额"), 
	//end--modified by liuzh on 2016-05-18
	HSB_PAY_ZERO_ERROR("90P3","互生币支付金额不能为0"),
	HSB_PAY_LESS_ERROR("90P4","互生币支付金额不能小于0.01"),//？能录入么 kend
	
	/**
	 * 二维码相关
	 */
	QR_TYPE_ERROR("90Q1", "二维码类型错误，请读取互生消费者身份二维码"),  //#未使用
	QR_ERROR("90Q2", "二维码错误，请读取互生消费者身份二维码"),
	//start--added by liuzh 0n 2016-05-30
	QR_TRANS_ERROR("90Q3", "二维码信息错误，请重新扫描"),
	QR_TRANS_DATA_ERROR("90Q4", "二维码信息错误"),
	//end--added by liuzh 0n 2016-05-30
	
	/**
	 * 定金业务相关
	 */
	EARNEST_SETTLE_FAIL("90E0", "定金结算失败，请联系互生客服"),
	//start--added by liuzh 0n 2016-05-30
	EARNEST_SETTLE_BACKATION("90E1", "定金交易流水号不支持退货操作"),
	//end--added by liuzh 0n 2016-05-30
	
	/**
	 * 操作提示
	 */
	TRADE_TYPE_MATCH_ERROR("90A0", "交易类型选择错误"),
	SINGLE_TRADE_INFO_ERROR("90A1", "该笔信息查询异常"),
	
	//start--added by liuzh on 2016-07-04
	/**
	 * 抵扣券业务相关
	 */
	DEDUCTION_NO_DEDUCTION_VOUCHER("90D0","无抵扣券"),
	DEDUCTION_VOUCHER_MAX_NUM("90D1","抵扣券张数超过限制的数量"),
	DEDUCTION_REBATE_AMOUNT_OVERSIZE("90D2","抵扣券金额过大"),	
	DEDUCTION_VOUCHER_NOT_ENOUGH("90D3","抵扣券余数不足"),
	//end--added by liuzh on 2016-07-04
	;
	
	//-----------------------------定义结束

	private int code; //返回代码
	/**
	 * 这个String使用得太频繁了，所以加上。
	 */
	private String codeStr;
	private String message;

	private PosRespCode(int code, String message) {
		this.code = code;
		this.codeStr = String.valueOf(code);
		this.message = message;
	}
	
	/**
	 * kend增加 使code支持字母
	 * @param codeStr
	 * @param message
	 */
	private PosRespCode(String codeStr, String message) {
		this.codeStr = codeStr;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public static PosRespCode getRespCodeById(int id) {
		for (PosRespCode c : PosRespCode.values()) {
			if (id == c.getCode()) {
				return c;
			}
		}
		return null;
	}

	public static String getRespCodeMessageById(int id) {
		for (PosRespCode c : PosRespCode.values()) {
			if (id == c.getCode()) {
				return c.getMessage();
			}
		}
		return null;
	}

	/*
	 * code支持字母 kend
	 */
	public static PosRespCode getRespCodeById(String codeStr) {
		for (PosRespCode c : PosRespCode.values()) {
			if (codeStr == c.getCodeStr()) {
				return c;
			}
		}
		return null;
	}
	/*
	 * code支持字母 kend
	 */
	public static String getRespCodeMessageById(String codeStr) {
		for (PosRespCode c : PosRespCode.values()) {
			if (codeStr.equals(c.getCodeStr())) {
				return c.getMessage();
			}
		}
		return null;
	}
	
	
	public String getMessage() {
		return message;
	}

    //public void setMessage(String message) {
    //    this.message = message;
    //}

	public String getCodeStr() {
		return codeStr;
	}
	
	@Override
    public String getDesc() {
	    return message;
    }
	
	/**
	 * 其它系统异常代码 转换成pos使用
	 * @param errorCode
	 * @return
	 * 已被PosRespCodeMap取代，测试类中未改，暂保留。
	 */
	@Deprecated
	public static PosRespCode codeConverts(int errorCode){
		
	    //uc
	    //企业信息
		if(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue() == errorCode){
			return NO_ENT_CARDPOINT;
			
			//客户信息
		}else if(ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue() == errorCode){
            return NO_FOUND_ACCT;
		}else if(ErrorCodeEnum.RES_NO_IS_NOT_FOUND.getValue() == errorCode){
		    return NO_FOUND_ACCT;
		}else if(ErrorCodeEnum.PWD_LOGIN_ERROR.getValue() == errorCode){
            return CHECK_CARDORPASS_FAIL;
        }else if(ErrorCodeEnum.PWD_TRADE_ERROR.getValue() == errorCode){
            return CHECK_PASS_FAIL;
        }else if(ErrorCodeEnum.HS_CARD_CODE_IS_WRONG.getValue() == errorCode){
            return INVALID_CARD;
        }else if(ErrorCodeEnum.HSCARD_IS_LOSS.getValue() == errorCode){
            return INVALID_CARD_LOSS;
            //设备信息
		}else if(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue() == errorCode){
			return AGAIN_SIGNIN;
		}else if(ErrorCodeEnum.DEVICE_IS_NOT_FOUND.getValue() == errorCode){
            return NO_FOUND_DEVICE;
		}else if(ErrorCodeEnum.DEVICE_NOT_SIGN.getValue() == errorCode){
            return AGAIN_SIGNIN;
		}else if(ErrorCodeEnum.POS_MATCH_MAC_FAILED.getValue() == errorCode){
	        return MAC_CHECK;
		}else if(RespCode.PS_DB_SQL_ERROR.getCode() == errorCode){
		    return POS_CENTER_FAIL;
		}else if(RespCode.PS_AC_ERROR.getCode() == errorCode){
		    return POS_CENTER_FAIL;
        }else if(RespCode.PS_NO_DATA_EXIST.getCode() == errorCode){
            return POS_CENTER_FAIL;
        }else if(RespCode.PS_DATA_EXIST.getCode() == errorCode){
            return POS_CENTER_FAIL;
        }else if(RespCode.PS_PARAM_ERROR.getCode() == errorCode){
            return POS_CENTER_FAIL;
        }else if(RespCode.PS_TRANSTYPE_ERROR.getCode() == errorCode){
            return POS_CENTER_FAIL;
        }else if(RespCode.PS_FILE_NOT_FOUND.getCode() == errorCode){
            return POS_CENTER_FAIL;
        }else if(RespCode.PS_HAS_THE_CANCELLATION.getCode() == errorCode){
            return REPEAT_CANCLE;
        }else if(RespCode.PS_HAS_BEEN_REVERSED.getCode() == errorCode){
            return REPEAT_REVERSE;
        }else if(RespCode.PS_ORDER_NOT_FOUND.getCode() == errorCode){
            return NO_FOUND_HSEC_ORDER;
        }else if(RespCode.PS_BATSETTLE_ERROR.getCode() == errorCode){
            return CHECK_SETTLE_FAIL;
        }else if(RespCode.PS_POINT_NO_MIN.getCode() == errorCode){
            return POS_CENTER_FAIL;
        }else{
		    
		    return OTHER_SERVER_FAIL;
		}
		
	}

    
	
}
